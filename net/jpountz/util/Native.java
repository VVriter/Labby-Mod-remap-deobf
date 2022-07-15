//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.util;

import java.io.*;

public enum Native
{
    private static boolean loaded;
    
    private static String arch() {
        return System.getProperty("os.arch");
    }
    
    private static OS os() {
        final String osName = System.getProperty("os.name");
        if (osName.contains("Linux")) {
            return OS.LINUX;
        }
        if (osName.contains("Mac")) {
            return OS.MAC;
        }
        if (osName.contains("Windows")) {
            return OS.WINDOWS;
        }
        if (osName.contains("Solaris") || osName.contains("SunOS")) {
            return OS.SOLARIS;
        }
        throw new UnsupportedOperationException("Unsupported operating system: " + osName);
    }
    
    private static String resourceName() {
        final OS os = os();
        final String packagePrefix = Native.class.getPackage().getName().replace('.', '/');
        return "/" + packagePrefix + "/" + os.name + "/" + arch() + "/liblz4-java." + os.libExtension;
    }
    
    public static synchronized boolean isLoaded() {
        return Native.loaded;
    }
    
    public static synchronized void load() {
        if (Native.loaded) {
            return;
        }
        try {
            System.loadLibrary("lz4-java");
            Native.loaded = true;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            final String resourceName = resourceName();
            final InputStream is = Native.class.getResourceAsStream(resourceName);
            if (is == null) {
                throw new UnsupportedOperationException("Unsupported OS/arch, cannot find " + resourceName + ". Please try building from source.");
            }
            try {
                final File tempLib = File.createTempFile("liblz4-java", "." + os().libExtension);
                FileOutputStream out = new FileOutputStream(tempLib);
                try {
                    final byte[] buf = new byte[4096];
                    while (true) {
                        final int read = is.read(buf);
                        if (read == -1) {
                            break;
                        }
                        out.write(buf, 0, read);
                    }
                    try {
                        out.close();
                        out = null;
                    }
                    catch (IOException ex) {}
                    System.load(tempLib.getAbsolutePath());
                    Native.loaded = true;
                }
                finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    }
                    catch (IOException ex2) {}
                    if (tempLib != null && tempLib.exists()) {
                        if (!Native.loaded) {
                            tempLib.delete();
                        }
                        else {
                            tempLib.deleteOnExit();
                        }
                    }
                }
            }
            catch (IOException e) {
                throw new ExceptionInInitializerError("Cannot unpack liblz4-java");
            }
        }
    }
    
    static {
        Native.loaded = false;
    }
    
    private enum OS
    {
        WINDOWS("win32", "so"), 
        LINUX("linux", "so"), 
        MAC("darwin", "dylib"), 
        SOLARIS("solaris", "so");
        
        public final String name;
        public final String libExtension;
        
        private OS(final String name, final String libExtension) {
            this.name = name;
            this.libExtension = libExtension;
        }
    }
}
