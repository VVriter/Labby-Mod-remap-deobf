//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft;

import java.text.*;
import net.labymod.accountmanager.authentication.microsoft.model.*;
import net.labymod.accountmanager.authentication.microsoft.model.oauth.*;
import net.labymod.accountmanager.utils.*;
import net.labymod.accountmanager.authentication.microsoft.model.xboxlive.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.msa.token.*;
import net.labymod.accountmanager.authentication.microsoft.model.minecraft.*;

public class MicrosoftAuthentication
{
    public static final SimpleDateFormat DATE_FORMAT_EXACT;
    public static final SimpleDateFormat DATE_FORMAT;
    public static final int REDIRECT_PORT = 8086;
    public static final String AZURE_CLIENT_ID = "27843883-6e3b-42cb-9e51-4f55a700601e";
    public static final String OAUTH_SCOPE = "XboxLive.signin%20offline_access";
    public static final String GRANT_TYPE_AUTH = "authorization_code";
    public static final String GRANT_TYPE_REFRESH = "refresh_token";
    public static final String REDIRECT_URL;
    public static final String PARTY_MINECRAFT_SERVICE = "rp://api.minecraftservices.com/";
    private static final String URL_OAUTH_ACCESS_TOKEN = "https://login.live.com/oauth20_token.srf";
    private static final String URL_XBL_AUTH = "https://user.auth.xboxlive.com/user/authenticate";
    private static final String URL_XSTS_AUTH = "https://xsts.auth.xboxlive.com/xsts/authorize";
    private static final String URL_AVATAR = "https://profile.xboxlive.com/users/xuid(%s)/profile/settings?settings=DisplayPic,PublicGamerpic";
    private static final String URL_LOGIN_WITH_XBOX = "https://api.minecraftservices.com/authentication/login_with_xbox";
    private static final String URL_STORE = "https://api.minecraftservices.com/entitlements/mcstore";
    private static final String URL_PROFILE = "https://api.minecraftservices.com/minecraft/profile";
    private StateCallback callback;
    
    public MicrosoftAuthentication() {
        this(stateId -> {});
    }
    
    public MicrosoftAuthentication(final StateCallback callback) {
        this.callback = callback;
    }
    
    public void setCallback(final StateCallback callback) {
        this.callback = callback;
    }
    
    public MicrosoftAccountResult authenticate(final String code) throws Exception {
        final OAuthResponse oAuth = this.getOAuthAccess(code);
        if (oAuth.error != null && !oAuth.error.isEmpty()) {
            throw new RuntimeException(oAuth.error + ": " + oAuth.errorDescription);
        }
        return this.authenticate(oAuth);
    }
    
    public MicrosoftAccountResult refresh(final String accessToken) throws Exception {
        final OAuthResponse oAuth = this.refreshOAuthAccess(accessToken);
        if (oAuth.error != null && !oAuth.error.isEmpty()) {
            throw new RuntimeException(oAuth.error + ": " + oAuth.errorDescription);
        }
        return this.authenticate(oAuth);
    }
    
    public MicrosoftAccountResult authenticate(final OAuthResponse oAuth) throws Exception {
        final TokenData xblAuth = this.getXBL(oAuth.accessToken);
        final TokenData minecraftScope = this.getXSTS(xblAuth.token, "rp://api.minecraftservices.com/");
        final LoginResponse minecraftAPILogin = this.getMinecraftAccess(minecraftScope);
        final ProfileResponse mcProfile = this.getMinecraftProfile(minecraftAPILogin.accessToken);
        final TokenData xboxScope = this.getXSTS(xblAuth.token, "http://xboxlive.com");
        final XBoxProfile xbBoxProfile = this.getXBoxProfile(xboxScope);
        return new MicrosoftAccountResult(mcProfile, xbBoxProfile, oAuth, minecraftAPILogin, xblAuth, xboxScope, minecraftScope);
    }
    
    public OAuthResponse getOAuthAccess(final String code) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_OAUTH_TOKEN);
        final String payload = String.format("client_id=%s&code=%s&grant_type=%s&redirect_uri=%s", "27843883-6e3b-42cb-9e51-4f55a700601e", code, "authorization_code", MicrosoftAuthentication.REDIRECT_URL);
        return RestUtil.performPostFormRequest("https://login.live.com/oauth20_token.srf", payload, OAuthResponse.class);
    }
    
    public OAuthResponse refreshOAuthAccess(final String refreshToken) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_OAUTH_TOKEN);
        final String payload = String.format("client_id=%s&refresh_token=%s&grant_type=%s&redirect_uri=%s&scope=%s", "27843883-6e3b-42cb-9e51-4f55a700601e", refreshToken, "refresh_token", MicrosoftAuthentication.REDIRECT_URL, "XboxLive.signin%20offline_access");
        return RestUtil.performPostFormRequest("https://login.live.com/oauth20_token.srf", payload, OAuthResponse.class);
    }
    
    public TokenData getXBL(final String accessToken) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_XBL);
        final XBLRequest request = new XBLRequest(new XBLRequest.Properties("RPS", "user.auth.xboxlive.com", accessToken), "http://auth.xboxlive.com", "JWT");
        return RestUtil.performPostObjectRequest("https://user.auth.xboxlive.com/user/authenticate", request, TokenData.class);
    }
    
    public TokenData getXSTS(final String xblToken, final String party) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_XSTS);
        final XSTSRequest request = new XSTSRequest(new XSTSRequest.Properties("RETAIL", new String[] { xblToken }), party, "JWT");
        return RestUtil.performPostObjectRequest("https://xsts.auth.xboxlive.com/xsts/authorize", request, TokenData.class);
    }
    
    public LoginResponse getMinecraftAccess(final TokenData xsts) throws Exception {
        return this.getMinecraftAccess(xsts.displayClaims.xui[0].uhs, xsts.token);
    }
    
    public LoginResponse getMinecraftAccess(final String userHash, final String xstsToken) throws Exception {
        if (this.callback != null) {
            this.callback.onStateChange(MicrosoftAuthState.GET_MC_TOKEN);
        }
        final LoginRequest request = new LoginRequest("XBL3.0 x=" + userHash + ";" + xstsToken);
        return RestUtil.performPostObjectRequest("https://api.minecraftservices.com/authentication/login_with_xbox", request, LoginResponse.class);
    }
    
    public ProfileResponse getMinecraftProfile(final String mcAccessToken) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_MC_PROFILE);
        return RestUtil.performGetJson("https://api.minecraftservices.com/minecraft/profile", mcAccessToken, ProfileResponse.class);
    }
    
    public XBoxProfile getXBoxProfile(final TokenData xstsResponse) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_XBOX_PROFILE);
        final XUI xui = xstsResponse.displayClaims.xui[0];
        return RestUtil.performGetContract(String.format("https://profile.xboxlive.com/users/xuid(%s)/profile/settings?settings=DisplayPic,PublicGamerpic", xui.xid), xui.uhs, xstsResponse.token, XBoxProfile.class);
    }
    
    public StoreResponse getMinecraftPurchase(final String accessToken) throws Exception {
        this.callback.onStateChange(MicrosoftAuthState.GET_PURCHASE);
        return RestUtil.performGetJson("https://api.minecraftservices.com/entitlements/mcstore", accessToken, StoreResponse.class);
    }
    
    static {
        DATE_FORMAT_EXACT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        REDIRECT_URL = String.format("http://localhost:%s", 8086);
    }
}
