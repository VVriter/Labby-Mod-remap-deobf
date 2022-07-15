//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.io.*;

public class LauncherDirectoryUtils
{
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
