//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model;

import net.labymod.accountmanager.storage.*;
import java.util.*;
import net.labymod.accountmanager.utils.*;
import net.labymod.accountmanager.storage.account.*;

public class LauncherAccount extends AbstractAccount
{
    public transient String accessToken;
    public String type;
    public MinecraftProfile minecraftProfile;
    public String accessTokenExpiresAt;
    public String avatar;
    private transient StorageType storageType;
    
    public LauncherAccount() {
        this.storageType = StorageType.MICROSOFT;
    }
    
    public boolean isMicrosoft() {
        return this.type != null && this.type.equals("Xbox");
    }
    
    public UUID getUUID() {
        return (this.minecraftProfile == null || this.minecraftProfile.id == null) ? null : UUIDUtil.getUUIDFromCompactUUID(this.minecraftProfile.id);
    }
    
    public boolean isPremium() {
        return this.accessToken != null && this.accessToken.length() > 10;
    }
    
    public boolean isAccessTokenExpired() {
        this.setAccessTokenExpiresAt(this.accessTokenExpiresAt);
        return super.isAccessTokenExpired();
    }
    
    public String getAvatarImage() {
        return this.avatar;
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getUsername() {
        return this.minecraftProfile.name;
    }
    
    public boolean isDemo() {
        return this.minecraftProfile == null || this.minecraftProfile.id == null;
    }
    
    public StorageType getStorageType() {
        return this.storageType;
    }
    
    public void setStorageType(final StorageType storageType) {
        this.storageType = storageType;
    }
    
    public boolean equals(final Object obj) {
        if (obj instanceof Account) {
            return ((Account)obj).getUUID().equals(this.getUUID());
        }
        return super.equals(obj);
    }
    
    public int hashCode() {
        return this.getUUID().hashCode();
    }
}
