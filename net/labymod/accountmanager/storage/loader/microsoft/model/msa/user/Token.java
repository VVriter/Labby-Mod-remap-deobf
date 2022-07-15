//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa.user;

import com.google.gson.annotations.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.msa.token.*;

public class Token
{
    @SerializedName("MsaUserId")
    public String msaUserId;
    @SerializedName("HasSignInDisplayClaims")
    public boolean hasSignInDisplayClaims;
    @SerializedName("IdentityType")
    public String identityType;
    @SerializedName("Environment")
    public String environment;
    @SerializedName("Sandbox")
    public String sandbox;
    @SerializedName("TokenType")
    public String tokenType;
    @SerializedName("RelyingParty")
    public String relyingParty;
    @SerializedName("SubRelyingParty")
    public String subRelyingParty;
    @SerializedName("TokenData")
    public TokenData tokenData;
    
    public Token(final String msaUserId, final boolean hasSignInDisplayClaims, final String identityType, final String environment, final String sandbox, final String tokenType, final String relyingParty, final String subRelyingParty, final TokenData tokenData) {
        this.msaUserId = msaUserId;
        this.hasSignInDisplayClaims = hasSignInDisplayClaims;
        this.identityType = identityType;
        this.environment = environment;
        this.sandbox = sandbox;
        this.tokenType = tokenType;
        this.relyingParty = relyingParty;
        this.subRelyingParty = subRelyingParty;
        this.tokenData = tokenData;
    }
}
