//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external.model;

import net.labymod.accountmanager.storage.account.*;
import java.util.*;
import com.google.gson.annotations.*;
import net.labymod.accountmanager.storage.*;

public class ExternalAccount extends AbstractAccount
{
    private final UUID uuid;
    private String username;
    @SerializedName("access_token")
    private String accessToken;
    
    public ExternalAccount(final UUID uuid, final String name, final String accessToken) {
        this.uuid = uuid;
        this.username = name;
        this.accessToken = accessToken;
    }
    
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public StorageType getStorageType() {
        return StorageType.EXTERNAL;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getAvatarImage() {
        return null;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public boolean isDemo() {
        return this.uuid == null || this.username == null;
    }
    
    public boolean isMicrosoft() {
        return false;
    }
    
    public boolean isPremium() {
        return !this.isDemo() && this.accessToken != null && this.accessToken.length() > 10;
    }
}
