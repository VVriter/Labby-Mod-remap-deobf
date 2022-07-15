//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa.token;

import net.labymod.accountmanager.storage.account.*;
import com.google.gson.annotations.*;
import net.labymod.accountmanager.authentication.microsoft.*;

public class TokenData implements TokenAccessor
{
    @SerializedName("Token")
    public String token;
    @SerializedName("NotAfter")
    public String notAfter;
    @SerializedName("IssueInstant")
    public String issueInstant;
    @SerializedName("ClientAttested")
    public boolean clientAttested;
    @SerializedName("DisplayClaims")
    public DisplayClaims displayClaims;
    
    public TokenData(final String token, final String notAfter, final String issueInstant, final boolean clientAttested, final DisplayClaims displayClaims) {
        this.token = token;
        this.notAfter = notAfter;
        this.issueInstant = issueInstant;
        this.clientAttested = clientAttested;
        this.displayClaims = displayClaims;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public long getExpiresAt() {
        try {
            return MicrosoftAuthentication.DATE_FORMAT_EXACT.parse(this.notAfter).getTime();
        }
        catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }
}
