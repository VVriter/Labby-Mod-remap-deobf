//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external.model.token;

import com.google.gson.annotations.*;
import java.util.*;
import net.labymod.accountmanager.storage.account.*;

public class Token
{
    private String token;
    @SerializedName("expires_at")
    private long expiresAt;
    @SerializedName("additional_data")
    private Map<String, String> additionalData;
    
    public Token() {
        this(null, 0L);
    }
    
    public Token(final String accessToken, final long expiresAt) {
        this.additionalData = new HashMap<String, String>();
        this.token = accessToken;
        this.expiresAt = expiresAt;
    }
    
    public Token update(final TokenAccessor accessor) {
        this.token = accessor.getToken();
        this.expiresAt = accessor.getExpiresAt();
        return this;
    }
    
    public Token update(final String token, final long expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
        return this;
    }
    
    public boolean hasData(final String key) {
        return this.additionalData.containsKey(key);
    }
    
    public String getData(final String key) {
        return this.additionalData.get(key);
    }
    
    public Token setData(final String key, final String value) {
        this.additionalData.put(key, value);
        return this;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public long getExpiresAt() {
        return this.expiresAt;
    }
    
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expiresAt;
    }
}
