//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.util.*;

public enum OSUtil
{
    WIN_32("dll", "windows", ""), 
    WIN_64("dll", "windows", ""), 
    MAC("dylib", "macos", "lib"), 
    UNIX("so", "linux", "lib"), 
    UNKNOWN((String)null, (String)null, (String)null);
    
    private static final String OS;
    private String libraryExtensionName;
    private String name;
    private String libraryPrefix;
    
    public static OSUtil getOS() {
        if (isMac()) {
            return OSUtil.MAC;
        }
        if (isUnix()) {
            return OSUtil.UNIX;
        }
        if (isWindows()) {
            return (System.getenv("ProgramFiles(x86)") != null) ? OSUtil.WIN_64 : OSUtil.WIN_32;
        }
        return OSUtil.UNKNOWN;
    }
    
    public String getLibraryFileName(final String name) {
        return String.format("%s%s-%s.%s", this.getLibraryPrefix(), name, this.is64() ? "64" : "32", this.getLibraryExtensionName());
    }
    
    public String getLibraryJarName(final String name, final String version) {
        return String.format("%s-%s-natives-%s-x%s.jar", name, version, this.name, this.is64() ? "64" : "32");
    }
    
    public boolean isLibrary(final String name) {
        return name.endsWith(this.libraryExtensionName);
    }
    
    private boolean is64() {
        return this != OSUtil.WIN_32;
    }
    
    public static boolean isWindows() {
        return OSUtil.OS.indexOf("win") >= 0;
    }
    
    public static boolean isMac() {
        return OSUtil.OS.indexOf("mac") >= 0;
    }
    
    public static boolean isUnix() {
        return OSUtil.OS.indexOf("nix") >= 0 || OSUtil.OS.indexOf("nux") >= 0 || OSUtil.OS.indexOf("aix") > 0;
    }
    
    public static boolean isSolaris() {
        return OSUtil.OS.indexOf("sunos") >= 0;
    }
    
    public String getLibraryExtensionName() {
        return this.libraryExtensionName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLibraryPrefix() {
        return this.libraryPrefix;
    }
    
    private OSUtil(final String libraryExtensionName, final String name, final String libraryPrefix) {
        this.libraryExtensionName = libraryExtensionName;
        this.name = name;
        this.libraryPrefix = libraryPrefix;
    }
    
    static {
        OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    }
}
