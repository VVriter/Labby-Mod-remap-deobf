//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon;

import net.labymod.api.*;
import net.labymod.support.util.*;
import net.labymod.utils.request.*;
import net.labymod.core.asm.*;
import net.labymod.main.*;
import java.util.jar.*;
import java.util.zip.*;
import com.google.gson.*;
import java.io.*;
import net.labymod.settings.elements.*;
import java.util.*;

public class LabyModOFAddon extends LabyModAddon
{
    public static final Map<String, UUID> OPTIFINE_VERSIONS;
    private static final File FILE_OF_HANDLER;
    private static final JsonParser parser;
    private static File OPTIFINE_FORGE;
    public static boolean INSTALL;
    public static String INSTALLED_VERSION;
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void init(final String addonName, final UUID uuid) {
        this.about = new About(uuid, addonName);
        this.about.loaded = true;
        LabyModOFAddon.INSTALLED_VERSION = ((addonName == null) ? null : addonName.replaceAll("_", ""));
    }
    
    public static void downloadOFHandler(final boolean async) {
        if (LabyModOFAddon.FILE_OF_HANDLER.exists()) {
            LabyModOFAddon.FILE_OF_HANDLER.delete();
        }
        if (!LabyModOFAddon.FILE_OF_HANDLER.exists()) {
            try {
                LabyModOFAddon.FILE_OF_HANDLER.getParentFile().mkdirs();
                final FileOutputStream fos = new FileOutputStream(LabyModOFAddon.FILE_OF_HANDLER);
                if (async) {
                    DownloadServerRequest.writeBytesAsync("https://dl.labymod.net/latest/install/ofhandler.jar", fos, new ServerStatus() {
                        @Override
                        public void success() {
                            Debug.log(Debug.EnumDebugMode.UPDATER, "Downloaded of handler https://dl.labymod.net/latest/install/ofhandler.jar");
                        }
                        
                        @Override
                        public void failed(final RequestException exception) {
                            exception.printStackTrace();
                        }
                        
                        @Override
                        public void close() throws Exception {
                            fos.close();
                        }
                    });
                }
                else {
                    DownloadServerRequest.writeBytes("https://dl.labymod.net/latest/install/ofhandler.jar", fos);
                    fos.close();
                }
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    
    public static void executeOfHandler(final boolean installOptifine) {
        final boolean isVanillaForge = !LabyModCoreMod.isForge();
        try {
            Debug.log(Debug.EnumDebugMode.ADDON, installOptifine ? (isVanillaForge ? "Installing optifine for vanillaforge" : "Uninstall optifine for vanillaforge") : (isVanillaForge ? "Installing optifine for forge" : "Uninstall optifine for forge"));
            if (isVanillaForge) {
                if (!LabyModOFAddon.FILE_OF_HANDLER.exists()) {
                    Debug.log(Debug.EnumDebugMode.ADDON, "OfHandler not found:" + LabyModOFAddon.FILE_OF_HANDLER.getAbsolutePath());
                    return;
                }
                if (Source.RUNNING_JAR == null) {
                    Debug.log(Debug.EnumDebugMode.ADDON, "Can't execute ofhandler: Running jar not found!");
                    return;
                }
                if (!Source.RUNNING_JAR.getName().endsWith(".jar")) {
                    Debug.log(Debug.EnumDebugMode.ADDON, "Can't execute ofhandler: " + Source.RUNNING_JAR.getAbsolutePath() + " is not a jar file!");
                    return;
                }
                executeOfHandlerDirect(installOptifine, Source.ABOUT_MC_VERSION, Source.RUNNING_JAR);
                Debug.log(Debug.EnumDebugMode.ADDON, "OfHandler executed!");
            }
            else if (installOptifine) {
                final File optifineMods = new File("mods/", "optifine.jar");
                final File optifineTemp = new File("LabyMod/ofhandler/", "optifine.jar");
                Debug.log(Debug.EnumDebugMode.ADDON, "Copy " + optifineTemp.getAbsolutePath() + " to " + optifineMods.getAbsolutePath());
                if (optifineTemp.exists()) {
                    optifineTemp.renameTo(optifineMods);
                }
                new File("LabyMod/ofhandler/").delete();
            }
            else if (LabyModOFAddon.OPTIFINE_FORGE != null) {
                if (!LabyModOFAddon.FILE_OF_HANDLER.exists()) {
                    Debug.log(Debug.EnumDebugMode.ADDON, "OfHandler not found:" + LabyModOFAddon.FILE_OF_HANDLER.getAbsolutePath());
                    return;
                }
                final String javaHome = System.getProperty("java.home");
                final String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
                final List<String> arguments = new ArrayList<String>();
                arguments.add(javaBin);
                arguments.add("-jar");
                arguments.add(LabyModOFAddon.FILE_OF_HANDLER.getAbsolutePath());
                arguments.add("del");
                arguments.add(LabyModOFAddon.OPTIFINE_FORGE.getAbsolutePath());
                final StringBuilder debug = new StringBuilder();
                for (final String arg : arguments) {
                    debug.append(arg).append(" ");
                }
                Debug.log(Debug.EnumDebugMode.ADDON, "Execute ofhandler:" + debug.toString());
                final ProcessBuilder pb = new ProcessBuilder(arguments);
                pb.start();
                Debug.log(Debug.EnumDebugMode.ADDON, "OfHandler executed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void executeOfHandlerDirect(final boolean installOptifine, final String mcVersion, final File runningJar) throws Exception {
        final String javaHome = System.getProperty("java.home");
        final String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        final List<String> arguments = new ArrayList<String>();
        arguments.add(javaBin);
        arguments.add("-jar");
        arguments.add(LabyModOFAddon.FILE_OF_HANDLER.getAbsolutePath());
        arguments.add(String.valueOf(installOptifine));
        arguments.add(mcVersion);
        arguments.add(runningJar.getAbsolutePath());
        final StringBuilder debug = new StringBuilder();
        for (final String arg : arguments) {
            debug.append(arg).append(" ");
        }
        Debug.log(Debug.EnumDebugMode.ADDON, "Execute ofhandler:" + debug.toString());
        final ProcessBuilder pb = new ProcessBuilder(arguments);
        pb.start();
    }
    
    public static void addOptifineVersion() {
        if (LabyModOFAddon.FILE_OF_HANDLER.getParentFile().exists()) {
            LabyModOFAddon.FILE_OF_HANDLER.getParentFile().delete();
        }
        if (LabyModCoreMod.isForge()) {
            final File modsFolder = new File("mods/");
            if (!modsFolder.exists()) {
                return;
            }
            for (final File mod : modsFolder.listFiles()) {
                if (mod.getName().endsWith(".jar")) {
                    try {
                        final JarFile jarFile = new JarFile(mod);
                        if (jarFile.getJarEntry("changelog.txt") == null || jarFile.getJarEntry("buildof.txt") == null) {
                            jarFile.close();
                        }
                        else {
                            addOptifineUsingJarFile(jarFile);
                            LabyModOFAddon.OPTIFINE_FORGE = mod;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static void addOptifineByVanillaForge(final String version) {
        if (LabyModOFAddon.FILE_OF_HANDLER.getParentFile().exists()) {
            LabyModOFAddon.FILE_OF_HANDLER.getParentFile().delete();
        }
        try {
            Debug.log(Debug.EnumDebugMode.ADDON, "Detected game version: " + version);
            if (version == null || version.isEmpty()) {
                Debug.log(Debug.EnumDebugMode.ADDON, "Can't check installed optifine version: version is not present!");
                return;
            }
            final File runningJar = new File("versions/" + version + "/" + version + ".jar");
            if (!runningJar.exists()) {
                Debug.log(Debug.EnumDebugMode.ADDON, "Can't check installed optifine version: running jar file " + runningJar.getAbsolutePath() + " doesn't exists!");
                return;
            }
            Source.RUNNING_JAR = runningJar;
            final JarFile jarFile = new JarFile(runningJar);
            if (jarFile.getJarEntry("changelog.txt") == null || jarFile.getJarEntry("buildof.txt") == null) {
                Debug.log(Debug.EnumDebugMode.ADDON, "Optifine is not installed");
                jarFile.close();
                return;
            }
            addOptifineUsingJarFile(jarFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addOptifineUsingJarFile(final JarFile jarFile) throws Exception {
        String fileContent = null;
        String changeLogContents = getStringByInputStream(jarFile.getInputStream(jarFile.getJarEntry("changelog.txt")));
        changeLogContents = changeLogContents.replace("\r", "");
        for (final String line : changeLogContents.split("\n")) {
            if (line.startsWith("OptiFine ")) {
                final String version = line.split("OptiFine ")[1].split("_")[0];
                if (version != null && !version.isEmpty()) {
                    if (LabyModOFAddon.OPTIFINE_VERSIONS.containsKey(version)) {
                        fileContent = "{\"uuid\":\"" + LabyModOFAddon.OPTIFINE_VERSIONS.get(version) + "\",\"name\":\"" + line + "\"}";
                        break;
                    }
                }
            }
        }
        jarFile.close();
        if (fileContent == null) {
            return;
        }
        final JsonObject object = (JsonObject)LabyModOFAddon.parser.parse(fileContent);
        String name = null;
        UUID uuid = null;
        if (!object.has("name")) {
            if (jarFile != null) {
                Debug.log(Debug.EnumDebugMode.ADDON, "Name not set in " + jarFile.getName());
                jarFile.close();
            }
            return;
        }
        name = object.get("name").getAsString();
        if (object.has("uuid")) {
            uuid = UUID.fromString(object.get("uuid").getAsString());
            Debug.log(Debug.EnumDebugMode.ADDON, "Optifine is installed!");
            final LabyModOFAddon ofAddon = new LabyModOFAddon();
            ofAddon.init(name, uuid);
            AddonLoader.getAddons().add(ofAddon);
            return;
        }
        if (jarFile != null) {
            Debug.log(Debug.EnumDebugMode.ADDON, "UUID not set in " + name);
            jarFile.close();
        }
    }
    
    public static String getStringByInputStream(final InputStream inputStream) {
        final StringBuilder sb = new StringBuilder();
        try {
            int ch;
            while ((ch = inputStream.read()) != -1) {
                sb.append((char)ch);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    @Override
    public void loadConfig() {
    }
    
    @Override
    protected void fillSettings(final List<SettingsElement> subSettings) {
    }
    
    static {
        OPTIFINE_VERSIONS = new HashMap<String, UUID>() {
            {
                this.put("1.8.9", UUID.fromString("2cc09032-995f-4b57-a2a1-f1399addbb21"));
                this.put("1.12.2", UUID.fromString("7d62bffd-fe3f-4667-8200-e8decb384fa0"));
            }
        };
        FILE_OF_HANDLER = new File("LabyMod/ofhandler/", "ofhandler.jar");
        parser = new JsonParser();
        LabyModOFAddon.OPTIFINE_FORGE = null;
        LabyModOFAddon.INSTALL = false;
        LabyModOFAddon.INSTALLED_VERSION = null;
    }
}
