//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model;

import net.labymod.accountmanager.authentication.microsoft.model.xboxlive.*;
import net.labymod.accountmanager.authentication.microsoft.model.oauth.*;
import net.labymod.accountmanager.authentication.microsoft.model.minecraft.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.msa.token.*;

public class MicrosoftAccountResult
{
    private String xuid;
    private String gamerTag;
    private String avatarUrl;
    private String accessToken;
    private String accessTokenExpiresAt;
    private ProfileResponse minecraftProfile;
    private XBoxProfile xboxProfile;
    private OAuthResponse oAuth;
    private LoginResponse minecraftAPILogin;
    private TokenData xblAuth;
    private TokenData xboxScope;
    private TokenData minecraftScope;
    
    public MicrosoftAccountResult(final ProfileResponse minecraftProfile, final XBoxProfile xboxProfile, final OAuthResponse oAuth, final LoginResponse minecraftAPILogin, final TokenData xblAuth, final TokenData xboxScope, final TokenData minecraftScope) {
        this.minecraftProfile = minecraftProfile;
        this.xboxProfile = xboxProfile;
        this.oAuth = oAuth;
        this.minecraftAPILogin = minecraftAPILogin;
        this.xblAuth = xblAuth;
        this.xboxScope = xboxScope;
        this.minecraftScope = minecraftScope;
        final XUI xui = xboxScope.displayClaims.xui[0];
        this.xuid = xui.xid;
        this.gamerTag = xui.gtg;
        this.avatarUrl = xboxProfile.profileUsers[0].getSettingById("PublicGamerpic");
        this.accessToken = minecraftAPILogin.accessToken;
        this.accessTokenExpiresAt = minecraftScope.notAfter;
    }
    
    public MicrosoftAccountResult(final String xuid, final String gamerTag, final String avatarUrl, final String accessToken, final String accessTokenExpiresAt, final ProfileResponse minecraftProfile, final XBoxProfile xboxProfile, final OAuthResponse oAuth, final LoginResponse minecraftAPILogin, final TokenData xblAuth, final TokenData xboxScope, final TokenData minecraftScope) {
        this.xuid = xuid;
        this.gamerTag = gamerTag;
        this.avatarUrl = avatarUrl;
        this.accessToken = accessToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.minecraftProfile = minecraftProfile;
        this.xboxProfile = xboxProfile;
        this.oAuth = oAuth;
        this.minecraftAPILogin = minecraftAPILogin;
        this.xblAuth = xblAuth;
        this.xboxScope = xboxScope;
        this.minecraftScope = minecraftScope;
    }
    
    public String getXuid() {
        return this.xuid;
    }
    
    public String getGamerTag() {
        return this.gamerTag;
    }
    
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getAccessTokenExpiresAt() {
        return this.accessTokenExpiresAt;
    }
    
    public ProfileResponse getMinecraftProfile() {
        return this.minecraftProfile;
    }
    
    public XBoxProfile getXboxProfile() {
        return this.xboxProfile;
    }
    
    public OAuthResponse getOAuth() {
        return this.oAuth;
    }
    
    public LoginResponse getMinecraftAPILogin() {
        return this.minecraftAPILogin;
    }
    
    public TokenData getXblAuth() {
        return this.xblAuth;
    }
    
    public TokenData getXboxScope() {
        return this.xboxScope;
    }
    
    public TokenData getMinecraftScope() {
        return this.minecraftScope;
    }
}
