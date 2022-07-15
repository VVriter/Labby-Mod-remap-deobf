//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.oauth;

import net.labymod.accountmanager.storage.account.*;
import com.google.gson.annotations.*;

public class OAuthResponse implements TokenAccessor
{
    public String error;
    @SerializedName("error_description")
    public String errorDescription;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("expires_in")
    public long expiresIn;
    public String scope;
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("user_id")
    public String userId;
    public String foci;
    
    @Override
    public String getToken() {
        return this.accessToken;
    }
    
    @Override
    public long getExpiresAt() {
        return System.currentTimeMillis() + this.expiresIn * 1000L;
    }
}
