//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external.model.token;

import com.google.gson.annotations.*;
import net.labymod.accountmanager.authentication.microsoft.model.*;
import net.labymod.accountmanager.storage.account.*;
import net.labymod.accountmanager.authentication.microsoft.model.oauth.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.msa.token.*;

public class TokenChain
{
    @SerializedName("microsoft")
    private final Token microsoftToken;
    @SerializedName("xbl")
    private final Token xblToken;
    @SerializedName("xsts")
    private final Token xstsToken;
    
    public TokenChain() {
        this.microsoftToken = new Token();
        this.xblToken = new Token();
        this.xstsToken = new Token();
    }
    
    public void update(final MicrosoftAccountResult result) {
        final OAuthResponse microsoft = result.getOAuth();
        this.microsoftToken.update((TokenAccessor)microsoft);
        this.microsoftToken.setData("refresh_token", microsoft.refreshToken);
        this.xblToken.update((TokenAccessor)result.getXblAuth());
        final TokenData xsts = result.getMinecraftScope();
        this.xstsToken.update((TokenAccessor)xsts);
        this.xstsToken.setData("user_hash", xsts.displayClaims.xui[0].uhs);
    }
    
    public Token getMicrosoftToken() {
        return this.microsoftToken;
    }
    
    public Token getXblToken() {
        return this.xblToken;
    }
    
    public Token getXstsToken() {
        return this.xstsToken;
    }
    
    public boolean isEmpty() {
        return this.microsoftToken.getToken() == null;
    }
}
