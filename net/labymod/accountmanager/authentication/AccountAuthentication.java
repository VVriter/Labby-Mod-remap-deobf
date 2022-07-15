//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication;

import net.labymod.accountmanager.authentication.microsoft.*;
import java.lang.reflect.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.exceptions.*;
import com.mojang.authlib.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import net.labymod.accountmanager.utils.*;
import net.labymod.accountmanager.authentication.microsoft.model.*;
import java.util.*;
import net.labymod.accountmanager.storage.loader.external.model.token.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.msa.token.*;
import net.labymod.accountmanager.authentication.microsoft.model.minecraft.*;
import net.labymod.accountmanager.storage.account.*;

public class AccountAuthentication
{
    private final YggdrasilAuthenticationService mojangService;
    private final MicrosoftAuthentication microsoftService;
    private Method checkTokenValidity;
    
    public AccountAuthentication(final String clientToken) {
        this.mojangService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, clientToken);
        this.microsoftService = new MicrosoftAuthentication();
        try {
            (this.checkTokenValidity = YggdrasilUserAuthentication.class.getDeclaredMethod("checkTokenValidity", (Class<?>[])new Class[0])).setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ExternalAccount authenticateMojang(final String username, final String password) throws AuthenticationException {
        final UserAuthentication authentication = this.mojangService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(username);
        authentication.setPassword(password);
        authentication.logIn();
        if (authentication.canPlayOnline()) {
            final GameProfile profile = authentication.getSelectedProfile();
            final ExternalAccount account = new ExternalAccount(profile.getId(), profile.getName(), authentication.getAuthenticatedToken());
            account.setAccessTokenExpiresAt(-1L);
            account.setSessionState(AccountSessionState.VALID);
            return account;
        }
        throw new AuthenticationException("Can't play online");
    }
    
    public void refreshMojangSession(final ExternalAccount account) throws AuthenticationException {
        final UserAuthentication authentication = this.mojangService.createUserAuthentication(Agent.MINECRAFT);
        final Map<String, Object> credentials = new HashMap<String, Object>();
        credentials.put("username", account.getUsername());
        credentials.put("accessToken", account.getAccessToken());
        authentication.loadFromStorage((Map)credentials);
        authentication.logIn();
        account.setAccessToken(authentication.getAuthenticatedToken());
        account.setUsername(authentication.getSelectedProfile().getName());
        account.setAccessTokenExpiresAt(-1L);
        account.setSessionState(AccountSessionState.VALID);
    }
    
    public ExternalMicrosoftAccount authenticateMicrosoft(final String code) throws Exception {
        final MicrosoftAccountResult result = this.microsoftService.authenticate(code);
        final ProfileResponse profile = result.getMinecraftProfile();
        if (profile.id == null) {
            throw new RuntimeException("Minecraft is not purchased on this Microsoft account");
        }
        final UUID uuid = UUIDUtil.getUUIDFromCompactUUID(profile.id);
        final String username = profile.name;
        final ExternalMicrosoftAccount account = new ExternalMicrosoftAccount(uuid, username, result);
        account.setSessionState(AccountSessionState.VALID);
        return account;
    }
    
    public void refreshMicrosoftSession(final ExternalMicrosoftAccount account) throws Exception {
        TokenChain chain = account.getTokens();
        if (account.refreshToken != null) {
            chain = new TokenChain();
            chain.getMicrosoftToken().setData("refreshToken", account.refreshToken);
            account.refreshToken = null;
        }
        final Token xsts = chain.getXstsToken();
        final Token xbl = chain.getXblToken();
        final Token microsoft = chain.getMicrosoftToken();
        if (xsts.hasExpired()) {
            if (xbl.hasExpired()) {
                if (microsoft.hasExpired()) {
                    final String refreshToken = chain.getMicrosoftToken().getData("refresh_token");
                    final MicrosoftAccountResult result = this.microsoftService.refresh(refreshToken);
                    if (result.getMinecraftProfile().id == null) {
                        throw new RuntimeException("Minecraft is not purchased on this Microsoft account");
                    }
                    account.updateProfile(result);
                    account.setSessionState(AccountSessionState.VALID);
                    return;
                }
                else {
                    final TokenData xblResult = this.microsoftService.getXBL(microsoft.getToken());
                    xbl.update(xblResult);
                }
            }
            final TokenData xstsResult = this.microsoftService.getXSTS(xbl.getToken(), "rp://api.minecraftservices.com/");
            if (xstsResult.notAfter == null) {
                throw new RuntimeException("Can't refresh XSTS token because XBL token is invalid");
            }
            xsts.update(xstsResult);
            xsts.setData("user_hash", xstsResult.displayClaims.xui[0].uhs);
        }
        final LoginResponse minecraftResult = this.microsoftService.getMinecraftAccess(xsts.getData("user_hash"), xsts.getToken());
        if (minecraftResult.getToken() == null) {
            throw new RuntimeException("Can't refresh Minecraft token because XSTS token is invalid");
        }
        account.setAccessToken(minecraftResult.getToken());
        account.setAccessTokenExpiresAt(minecraftResult.getExpiresAt());
    }
    
    public void updateMojangSessionState(final Account account) throws Exception {
        final boolean isValid = this.isAccessTokenValid(account);
        account.setSessionState(isValid ? AccountSessionState.VALID : AccountSessionState.EXPIRED);
    }
    
    public boolean isAccessTokenValid(final Account account) throws Exception {
        if (account.isMicrosoft()) {
            throw new RuntimeException("Can't validate access token of a Microsoft account");
        }
        final UserAuthentication authentication = this.mojangService.createUserAuthentication(Agent.MINECRAFT);
        final Map<String, Object> credentials = new HashMap<String, Object>();
        credentials.put("accessToken", account.getAccessToken());
        authentication.loadFromStorage((Map)credentials);
        return this.checkTokenValidity != null && (boolean)this.checkTokenValidity.invoke(authentication, new Object[0]);
    }
    
    public MicrosoftAuthentication getMicrosoftService() {
        return this.microsoftService;
    }
}
