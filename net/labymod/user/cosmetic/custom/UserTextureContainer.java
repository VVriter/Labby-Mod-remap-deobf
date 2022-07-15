//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom;

import java.util.*;

public class UserTextureContainer
{
    private final int cosmeticId;
    private String directory;
    private UUID fileName;
    private String resolvedURL;
    private boolean loaded;
    
    public UserTextureContainer(final String directory) {
        this(-1, directory, null);
    }
    
    public UserTextureContainer(final int cosmeticId, final String directory) {
        this(cosmeticId, directory, null);
    }
    
    public UserTextureContainer(final String directory, final UUID fileName) {
        this.resolvedURL = null;
        this.loaded = false;
        this.cosmeticId = -1;
        this.directory = directory;
        this.fileName = fileName;
    }
    
    public UserTextureContainer(final int cosmeticId, final String directory, final UUID fileName) {
        this.resolvedURL = null;
        this.loaded = false;
        this.cosmeticId = cosmeticId;
        this.directory = directory;
        this.fileName = fileName;
    }
    
    public void validateTexture(final CosmeticImageHandler handler) {
        if (this.loaded || this.resolvedURL == null) {
            return;
        }
        this.loaded = true;
        handler.loadUserTexture(this.fileName, this.resolvedURL);
    }
    
    public void unload() {
        this.loaded = false;
    }
    
    public void resolved() {
        if (this.fileName != null) {
            if (this.directory == null) {
                this.resolvedURL = String.format("https://dl.labymod.net/cosmetics/%s/textures/%s.png", this.cosmeticId, this.fileName.toString());
            }
            else {
                this.resolvedURL = String.format("https://dl.labymod.net/textures/%s", this.directory + "/" + this.fileName.toString());
            }
            this.unload();
        }
    }
    
    public void setFileName(final UUID fileName) {
        this.fileName = fileName;
    }
    
    public UUID getFileName() {
        return this.fileName;
    }
    
    public String getResolvedURL() {
        return this.resolvedURL;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
}
