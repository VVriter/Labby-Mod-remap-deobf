//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.account;

import com.google.gson.annotations.*;
import net.labymod.accountmanager.authentication.microsoft.*;
import java.text.*;

public abstract class AbstractAccount implements Account
{
    private transient AccountSessionState state;
    private transient long lastAccessTokenUpdated;
    @SerializedName("access_token_expires_at")
    private long accessTokenExpiresAt;
    
    public AbstractAccount() {
        this.state = AccountSessionState.UNKNOWN;
    }
    
    @Override
    public void setSessionState(final AccountSessionState state) {
        this.state = state;
    }
    
    @Override
    public boolean isAccessTokenExpired() {
        return this.accessTokenExpiresAt != -1L && this.accessTokenExpiresAt < System.currentTimeMillis();
    }
    
    @Override
    public AccountSessionState getSessionState() {
        return this.state;
    }
    
    @Override
    public long getLastAccessTokenUpdated() {
        return this.lastAccessTokenUpdated;
    }
    
    public void setAccessTokenExpiresAt(final long accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.lastAccessTokenUpdated = System.currentTimeMillis();
    }
    
    public void setAccessTokenExpiresAt(final String accessTokenExpiresAt) {
        try {
            final SimpleDateFormat format = accessTokenExpiresAt.contains(".") ? MicrosoftAuthentication.DATE_FORMAT_EXACT : MicrosoftAuthentication.DATE_FORMAT;
            final long time = format.parse(accessTokenExpiresAt).getTime();
            this.setAccessTokenExpiresAt(time);
        }
        catch (ParseException e) {
            this.accessTokenExpiresAt = -1L;
            e.printStackTrace();
        }
    }
}
