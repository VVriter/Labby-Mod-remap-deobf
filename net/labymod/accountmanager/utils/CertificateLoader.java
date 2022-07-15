//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.utils;

import java.security.cert.*;
import com.google.gson.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.security.*;
import java.nio.file.*;
import javax.net.ssl.*;

public class CertificateLoader
{
    private static final String URL_CERTIFICATES_ROOT = "https://dl.labymod.net/latest/certificates/";
    private static final String URL_CERTIFICATES_INDEX = "https://dl.labymod.net/latest/certificates/index.json";
    private static boolean initialize;
    private static SSLSocketFactory socketFactory;
    
    private static boolean isCertificateSupported() {
        final String javaVersion = System.getProperty("java.version");
        if (javaVersion != null && javaVersion.startsWith("1.8.0_")) {
            try {
                if (Integer.parseInt(javaVersion.substring("1.8.0_".length())) < 101) {
                    return false;
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public static SSLSocketFactory getSocketFactory() throws Exception {
        if (!CertificateLoader.initialize && !isCertificateSupported()) {
            load();
        }
        return CertificateLoader.socketFactory;
    }
    
    public static void load() throws Exception {
        CertificateLoader.initialize = true;
        final JsonObject index = RestUtil.performGetJson("https://dl.labymod.net/latest/certificates/index.json", null, JsonObject.class);
        final Map<String, Certificate> certificates = new HashMap<String, Certificate>();
        if (index.has("oauth")) {
            final JsonObject entry = index.getAsJsonObject("oauth");
            final JsonArray files = entry.getAsJsonArray("certificates");
            for (final JsonElement file : files) {
                certificates.putAll(loadUrl("https://dl.labymod.net/latest/certificates/" + file.getAsString()));
            }
        }
        init(certificates);
    }
    
    private static Map<String, Certificate> loadUrl(final String url) throws Exception {
        final InputStream inputStream = new URL(url).openStream();
        if (inputStream != null) {
            final Map<String, Certificate> certificates = loadInputStream(inputStream);
            inputStream.close();
            return certificates;
        }
        return new HashMap<String, Certificate>();
    }
    
    private static Map<String, Certificate> loadResource(final String fileName) throws Exception {
        final InputStream inputStream = CertificateLoader.class.getResourceAsStream(fileName);
        if (inputStream != null) {
            final Map<String, Certificate> certificates = loadInputStream(inputStream);
            inputStream.close();
            return certificates;
        }
        return new HashMap<String, Certificate>();
    }
    
    private static Map<String, Certificate> loadInputStream(final InputStream inputStream) throws Exception {
        if (isCertificateSupported()) {
            return new HashMap<String, Certificate>();
        }
        final KeyStore keyStoreResources = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStoreResources.load(inputStream, "supersecretpassword".toCharArray());
        final KeyStore keyStore;
        return Collections.list(keyStoreResources.aliases()).stream().collect(Collectors.toMap(a -> a, alias -> {
            try {
                return keyStore.getCertificate(alias);
            }
            catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }));
    }
    
    private static void init(final Map<String, Certificate> certificates) throws Exception {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        final Path path = Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts");
        keyStore.load(Files.newInputStream(path, new OpenOption[0]), "changeit".toCharArray());
        final KeyStore keyStore2;
        final Map<String, Certificate> jdkTrustStore = Collections.list(keyStore.aliases()).stream().collect(Collectors.toMap(a -> a, alias -> {
            try {
                return keyStore2.getCertificate(alias);
            }
            catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }));
        final KeyStore mergedTrustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        mergedTrustStore.load(null, new char[0]);
        for (final Map.Entry<String, Certificate> entry : jdkTrustStore.entrySet()) {
            mergedTrustStore.setCertificateEntry(entry.getKey(), entry.getValue());
        }
        for (final Map.Entry<String, Certificate> entry : certificates.entrySet()) {
            mergedTrustStore.setCertificateEntry(entry.getKey(), entry.getValue());
        }
        final TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init(mergedTrustStore);
        final SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, instance.getTrustManagers(), null);
        CertificateLoader.socketFactory = context.getSocketFactory();
    }
    
    static {
        CertificateLoader.initialize = false;
        CertificateLoader.socketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
    }
}
