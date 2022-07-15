//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.xboxlive;

import com.google.gson.annotations.*;

public class XBLRequest
{
    @SerializedName("Properties")
    public Properties properties;
    @SerializedName("RelyingParty")
    public String relyingParty;
    @SerializedName("TokenType")
    public String tokenType;
    
    public XBLRequest(final Properties properties, final String relyingParty, final String tokenType) {
        this.properties = properties;
        this.relyingParty = relyingParty;
        this.tokenType = tokenType;
    }
    
    public static class Properties
    {
        @SerializedName("AuthMethod")
        public String authMethod;
        @SerializedName("SiteName")
        public String siteName;
        @SerializedName("RpsTicket")
        public String accessToken;
        
        public Properties(final String authMethod, final String siteName, final String accessToken) {
            this.authMethod = authMethod;
            this.siteName = siteName;
            this.accessToken = "d=" + accessToken;
        }
    }
}
