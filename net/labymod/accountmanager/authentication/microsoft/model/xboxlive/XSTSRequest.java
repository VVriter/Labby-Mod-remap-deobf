//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.xboxlive;

import com.google.gson.annotations.*;

public class XSTSRequest
{
    @SerializedName("Properties")
    public Properties properties;
    @SerializedName("RelyingParty")
    public String relyingParty;
    @SerializedName("TokenType")
    public String tokenType;
    
    public XSTSRequest(final Properties properties, final String relyingParty, final String tokenType) {
        this.properties = properties;
        this.relyingParty = relyingParty;
        this.tokenType = tokenType;
    }
    
    public static class Properties
    {
        @SerializedName("SandboxId")
        public String sandboxId;
        @SerializedName("UserTokens")
        public String[] userTokens;
        
        public Properties(final String sandboxId, final String[] userTokens) {
            this.sandboxId = sandboxId;
            this.userTokens = userTokens;
        }
    }
}
