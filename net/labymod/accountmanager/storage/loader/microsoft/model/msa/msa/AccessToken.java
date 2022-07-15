//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa.msa;

import com.google.gson.annotations.*;

public class AccessToken
{
    @SerializedName("access_token")
    public String accessTokens;
    @SerializedName("xal_expires")
    public String xalExpires;
    public String scopes;
    
    public AccessToken(final String accessTokens, final String xalExpires, final String scopes) {
        this.accessTokens = accessTokens;
        this.xalExpires = xalExpires;
        this.scopes = scopes;
    }
}
