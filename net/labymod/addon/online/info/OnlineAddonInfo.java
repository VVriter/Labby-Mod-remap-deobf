//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon.online.info;

import java.util.*;
import java.io.*;
import net.labymod.addon.*;

public class OnlineAddonInfo extends AddonInfo
{
    protected String hash;
    protected boolean enabled;
    protected boolean includeInJar;
    protected boolean restart;
    protected boolean verified;
    protected String mcVersion;
    protected int[] sorting;
    
    public OnlineAddonInfo(final UUID uuid, final String name, final int version, final String hash, final String author, final String description, final int category, final boolean enabled, final boolean includeInJar, final boolean restart, final boolean verified, final String mcVersion, final int[] sorting) {
        super(uuid, name, version, author, description, category);
        this.hash = hash;
        this.enabled = enabled;
        this.includeInJar = includeInJar;
        this.restart = restart;
        this.verified = verified;
        this.mcVersion = mcVersion;
        this.sorting = sorting;
    }
    
    public String getDownloadURL() {
        return String.format("https://dl.labymod.net/latest/?file=%s&a=1", this.uuid);
    }
    
    public String getImageURL() {
        return String.format("https://dl.labymod.net/latest/addons/%s/icon.png", this.uuid);
    }
    
    public File getFileDestination() {
        return new File(AddonLoader.getAddonsDirectory(), this.name + ".jar");
    }
    
    public String getHash() {
        return this.hash;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public boolean isIncludeInJar() {
        return this.includeInJar;
    }
    
    public boolean isRestart() {
        return this.restart;
    }
    
    public boolean isVerified() {
        return this.verified;
    }
    
    public String getMcVersion() {
        return this.mcVersion;
    }
    
    public int[] getSorting() {
        return this.sorting;
    }
    
    public enum AddonActionState
    {
        INSTALL, 
        INSTALL_REVOKE, 
        UNINSTALL, 
        UNINSTALL_REVOKE, 
        ERROR;
    }
}
