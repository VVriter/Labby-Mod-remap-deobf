//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.minecraft;

import net.labymod.accountmanager.storage.account.*;
import com.google.gson.annotations.*;

public class LoginResponse implements TokenAccessor
{
    public String username;
    public Object[] roles;
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("expires_in")
    public long expiresIn;
    
    @Override
    public String getToken() {
        return this.accessToken;
    }
    
    @Override
    public long getExpiresAt() {
        return System.currentTimeMillis() + this.expiresIn * 1000L;
    }
}
