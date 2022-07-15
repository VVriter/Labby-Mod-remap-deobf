//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.util.jar.*;
import java.net.*;
import java.io.*;

public class QuickInstaller
{
    public static Attributes.Name MC_VERSION;
    
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            final Manifest manifest = getManifest(QuickInstaller.class);
            final String mcVersion = (String)manifest.getMainAttributes().get(QuickInstaller.MC_VERSION);
            if (mcVersion == null) {
                JOptionPane.showMessageDialog(null, "Can't find mc version in METAINF", "Quick Installer", 0);
                return;
            }
            final File minecraftDirectory = getWorkingDirectory();
            final File destination = new File(minecraftDirectory, "libraries/net/labymod/LabyMod/3_" + mcVersion + "/LabyMod-3_" + mcVersion + ".jar");
            final File source = new File(QuickInstaller.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Installed successfully!", "Quick Installer", 1);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Quick Installer", 0);
        }
    }
    
    public static Manifest getManifest(final Class<?> clz) {
        final String resource = "/" + clz.getName().replace(".", "/") + ".class";
        final String fullPath = clz.getResource(resource).toString();
        String archivePath = fullPath.substring(0, fullPath.length() - resource.length());
        if (archivePath.endsWith("\\WEB-INF\\classes") || archivePath.endsWith("/WEB-INF/classes")) {
            archivePath = archivePath.substring(0, archivePath.length() - "/WEB-INF/classes".length());
        }
        try (final InputStream input = new URL(archivePath + "/META-INF/MANIFEST.MF").openStream()) {
            return new Manifest(input);
        }
        catch (Exception e) {
            throw new RuntimeException("Loading MANIFEST for class " + clz + " failed!", e);
        }
    }
    
    public static File getWorkingDirectory() {
        return getWorkingDirectory("minecraft");
    }
    
    public static File getWorkingDirectory(final String applicationName) {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (getPlatform()) {
            case LINUX: {
                workingDirectory = new File(userHome, '.' + applicationName + '/');
                break;
            }
            case UNKNOWN: {
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            }
            case WINDOWS: {
                final String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                    break;
                }
                workingDirectory = new File(userHome, '.' + applicationName + '/');
                break;
            }
            case MACOS: {
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            }
            case SOLARIS: {
                final String applicationDataW = System.getenv("APPDATA");
                if (applicationDataW != null) {
                    workingDirectory = new File(applicationDataW, "." + applicationName + '/');
                    break;
                }
                workingDirectory = new File(userHome, '.' + applicationName + '/');
                break;
            }
            default: {
                workingDirectory = new File(userHome, applicationName + '/');
                break;
            }
        }
        if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        }
        return workingDirectory;
    }
    
    public static OS getPlatform() {
        final String osName = System.getProperty("os.name").toLowerCase();
        for (final OS os : OS.values()) {
            if (os != OS.UNKNOWN) {
                for (final String name : os.getOsNames()) {
                    if (osName.contains(name)) {
                        return os;
                    }
                }
            }
        }
        return OS.UNKNOWN;
    }
    
    static {
        QuickInstaller.MC_VERSION = new Attributes.Name("MC-VERSION");
    }
    
    public enum OS
    {
        LINUX(new String[] { "linux", "unix" }), 
        SOLARIS(new String[] { "solaris", "sunos" }), 
        WINDOWS(new String[] { "win" }), 
        MACOS(new String[] { "mac" }), 
        UNKNOWN(new String[0]);
        
        private String[] osNames;
        
        private OS(final String[] osNames) {
            this.osNames = osNames;
        }
        
        public String[] getOsNames() {
            return this.osNames;
        }
    }
}
