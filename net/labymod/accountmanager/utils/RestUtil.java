//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.utils;

import com.google.gson.*;
import java.nio.charset.*;
import javax.net.ssl.*;
import java.net.*;
import java.io.*;

public class RestUtil
{
    private static final Gson GSON;
    
    public static <T> T performGetJson(final String url, final String bearer, final Class<T> response) throws Exception {
        final String json = performGetJson(url, bearer);
        return (T)RestUtil.GSON.fromJson(json, (Class)response);
    }
    
    public static <T> T performPostFormRequest(final String url, final String content, final Class<T> response) throws Exception {
        final String json = performPostRequest(url, content, "application/x-www-form-urlencoded");
        return (T)RestUtil.GSON.fromJson(json, (Class)response);
    }
    
    public static String performPostJsonRequest(final String url, final String json) throws Exception {
        return performPostRequest(url, json, "application/json");
    }
    
    public static <T> T performPostObjectRequest(final String url, final Object request, final Class<T> response) throws Exception {
        final String json = performPostRequest(url, RestUtil.GSON.toJson(request), "application/json");
        return (T)RestUtil.GSON.fromJson(json, (Class)response);
    }
    
    private static String performPostRequest(final String url, final String content, final String contentType) throws Exception {
        final HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
        final byte[] data = content.getBytes(StandardCharsets.UTF_8);
        connection.setSSLSocketFactory(CertificateLoader.getSocketFactory());
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Content-Length", "" + data.length);
        connection.setDoOutput(true);
        try (final OutputStream outputStream = connection.getOutputStream()) {
            IOUtil.write(data, outputStream);
        }
        catch (SSLHandshakeException e) {
            throw new RuntimeException("Invalid certificate for " + url);
        }
        return readInputStream(connection);
    }
    
    public static String performGetJson(final String url, final String bearer) throws Exception {
        final HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
        connection.setSSLSocketFactory(CertificateLoader.getSocketFactory());
        if (bearer != null) {
            connection.addRequestProperty("Authorization", "Bearer " + bearer);
        }
        return readInputStream(connection);
    }
    
    public static <T> T performGetContract(final String url, final String hash, final String token, final Class<T> response) throws Exception {
        final HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
        connection.setSSLSocketFactory(CertificateLoader.getSocketFactory());
        connection.addRequestProperty("Authorization", "XBL3.0 x=" + hash + ";" + token);
        connection.addRequestProperty("x-xbl-contract-version", "2");
        connection.addRequestProperty("Accept", "application/json");
        final String json = readInputStream(connection);
        return (T)RestUtil.GSON.fromJson(json, (Class)response);
    }
    
    private static String readInputStream(final HttpURLConnection connection) throws IOException {
        try (final InputStream inputStream = connection.getInputStream()) {
            return IOUtil.toString(inputStream, StandardCharsets.UTF_8);
        }
        catch (IOException exception) {
            try (final InputStream errorStream = connection.getErrorStream()) {
                if (errorStream == null) {
                    throw new RuntimeException("Response code: " + connection.getResponseCode());
                }
                return IOUtil.toString(errorStream, StandardCharsets.UTF_8);
            }
        }
    }
    
    static {
        GSON = new Gson();
    }
}
