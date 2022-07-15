//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa.msa;

import com.google.gson.annotations.*;
import net.labymod.accountmanager.authentication.microsoft.model.oauth.*;
import net.labymod.accountmanager.authentication.microsoft.*;
import java.util.*;

public class MSA
{
    @SerializedName("user_id")
    public String userId;
    @SerializedName("refresh_token")
    public String refreshToken;
    public String foci;
    @SerializedName("access_tokens")
    public AccessToken[] accessTokens;
    
    public MSA(final OAuthResponse oAuth) {
        this.userId = oAuth.userId;
        this.refreshToken = oAuth.refreshToken;
        this.foci = oAuth.foci;
        final long expireTimestamp = System.currentTimeMillis() + oAuth.expiresIn * 1000L;
        final String date = MicrosoftAuthentication.DATE_FORMAT_EXACT.format(new Date(expireTimestamp));
        this.accessTokens = new AccessToken[] { new AccessToken(oAuth.accessToken, date, oAuth.scope.toLowerCase()) };
    }
}
