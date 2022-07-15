//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.java.model;

import java.util.*;
import net.labymod.accountmanager.utils.*;
import net.labymod.accountmanager.storage.*;
import net.labymod.accountmanager.storage.account.*;

public class AuthenticationDatabase extends AbstractAccount
{
    public String accessToken;
    public String username;
    public String displayName;
    public String userId;
    public String uuid;
    public Map<String, Profile> profiles;
    public Object properties;
    
    public AuthenticationDatabase(final int profilesFormat, final String accessToken, final String username, final String displayName, final String userId, final String uuid) {
        this.profiles = new HashMap<String, Profile>();
        this.accessToken = accessToken;
        this.username = username;
        if (profilesFormat >= 2) {
            this.profiles.put(uuid.replaceAll("-", ""), new Profile(displayName));
        }
        else {
            this.displayName = displayName;
            this.userId = userId;
            this.uuid = uuid;
        }
    }
    
    public boolean isMicrosoft() {
        return false;
    }
    
    public UUID getUUID() {
        return this.isNewFormat() ? UUIDUtil.getUUIDFromCompactUUID(this.profiles.keySet().iterator().next().replaceAll("-", "")) : UUID.fromString(this.uuid);
    }
    
    public boolean isPremium() {
        return this.accessToken != null && !this.accessToken.isEmpty();
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getUsername() {
        return this.isNewFormat() ? this.profiles.values().iterator().next().displayName : this.displayName;
    }
    
    public boolean isDemo() {
        if (this.isNewFormat()) {
            if (this.profiles == null) {
                return true;
            }
            if (this.profiles.isEmpty()) {
                return true;
            }
        }
        else if (this.uuid == null) {
            return true;
        }
        if (this.username != null) {
            return false;
        }
        return true;
    }
    
    public String getAvatarImage() {
        return null;
    }
    
    public boolean isNewFormat() {
        return this.uuid == null;
    }
    
    public StorageType getStorageType() {
        return StorageType.JAVA;
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
