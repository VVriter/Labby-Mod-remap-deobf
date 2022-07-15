//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.api;

import net.labymod.utils.*;
import net.labymod.support.util.*;
import java.util.*;
import java.net.*;
import net.labymod.utils.request.*;
import java.io.*;

public class DiscordLibraryProvider
{
    public static final String JNA_LIBRARY_NAME;
    private Consumer<File> callback;
    private File destination;
    private URL url;
    private boolean validOS;
    
    public DiscordLibraryProvider() {
        this.validOS = false;
        final OSUtil osUtil = OSUtil.getOS();
        Debug.log(Debug.EnumDebugMode.DISCORD, "Detected OS version: " + osUtil.name());
        if (osUtil == OSUtil.UNKNOWN || osUtil == OSUtil.UNIX) {
            return;
        }
        try {
            this.url = new URL(String.format("https://dl.labymod.net/latest/install/discord/%s.%s", osUtil.name().toLowerCase(Locale.ENGLISH), osUtil.getLibraryExtensionName()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.destination = new File(System.getProperty("java.library.path"), osUtil.getLibraryPrefix() + DiscordLibraryProvider.JNA_LIBRARY_NAME + "." + osUtil.getLibraryExtensionName());
        Debug.log(Debug.EnumDebugMode.DISCORD, "Discord lib destination: " + this.destination.getAbsolutePath());
        if (this.url != null) {
            this.validOS = true;
        }
    }
    
    public void execute(final Consumer<File> callback) {
        if (this.destination.exists()) {
            Debug.log(Debug.EnumDebugMode.DISCORD, "Discord library is already downloaded in natives: " + this.destination.getAbsolutePath());
            callback.accept(this.destination);
        }
        else {
            Debug.log(Debug.EnumDebugMode.DISCORD, "Downloading latest discord library: " + this.url.toString());
            this.callback = callback;
            this.download();
        }
    }
    
    public void download() {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Download latest discord library..");
        try {
            final FileOutputStream fos = new FileOutputStream(this.destination);
            DownloadServerRequest.writeBytesAsync(this.url.toString(), fos, new ServerStatus() {
                @Override
                public void success() {
                    DiscordLibraryProvider.this.callback.accept(DiscordLibraryProvider.this.destination);
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
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public boolean isValidOS() {
        return this.validOS;
    }
    
    static {
        JNA_LIBRARY_NAME = (OSUtil.isMac() ? "libdiscordRPC" : "discordRPC");
    }
}
