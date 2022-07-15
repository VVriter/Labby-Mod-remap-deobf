//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external;

import net.labymod.accountmanager.storage.loader.*;
import java.io.*;
import java.lang.reflect.*;
import net.labymod.accountmanager.storage.*;
import java.util.function.*;
import java.util.*;
import net.labymod.accountmanager.storage.account.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import com.google.gson.*;

public class ExternalAccountStorage extends JsonStorageImpl<ExternalAccounts, ExternalAccount>
{
    private final File file;
    private ExternalAccounts storage;
    
    public ExternalAccountStorage(final File file) {
        this.storage = new ExternalAccounts();
        this.file = file;
    }
    
    @Override
    protected void registerTypeAdapters(final GsonBuilder builder) {
        final Gson gson = new Gson();
        builder.registerTypeAdapter((Type)ExternalAccount.class, (Object)((json, type, context) -> {
            final JsonObject object = json.getAsJsonObject();
            if (object.has("refresh_token") || object.has("tokens")) {
                type = ExternalMicrosoftAccount.class;
            }
            return (ExternalAccount)gson.fromJson(json, type);
        }));
        builder.registerTypeAdapter((Type)ExternalAccount.class, (Object)((account, type, context) -> gson.toJsonTree((Object)account)));
    }
    
    public String getClientToken() {
        return this.storage.clientToken;
    }
    
    public void addAccount(final ExternalAccount account) {
        this.storage.accounts.put(account.getUUID(), account);
    }
    
    public void removeAccount(final ExternalAccount account) {
        this.storage.accounts.remove(account.getUUID());
    }
    
    public File getFile() {
        return this.file;
    }
    
    public boolean isLoaded() {
        return this.storage != null && this.storage.accounts != null;
    }
    
    public Collection<ExternalAccount> getAccounts() {
        return this.storage.accounts.values();
    }
    
    public Account getActiveAccount() {
        return null;
    }
    
    public StorageType getType() {
        return StorageType.EXTERNAL;
    }
    
    @Override
    protected Class<?> onJsonObjectClass() {
        return ExternalAccounts.class;
    }
    
    @Override
    protected void onStorageLoaded(final ExternalAccounts storage) {
        this.storage = ((storage == null) ? new ExternalAccounts() : storage);
    }
    
    @Override
    protected ExternalAccounts getStorage() {
        return this.storage;
    }
    
    public void refreshAccounts(final Consumer<Account> callback) {
        for (final ExternalAccount account : this.getAccounts()) {
            this.refreshAccount(account);
            callback.accept((Account)account);
        }
    }
    
    public void refreshAccount(final ExternalAccount account) {
        try {
            if (account.isPremium()) {
                if (account.isMicrosoft()) {
                    if (account.isAccessTokenExpired()) {
                        account.setSessionState(AccountSessionState.REFRESHING);
                        this.authentication.refreshMicrosoftSession((ExternalMicrosoftAccount)account);
                    }
                }
                else if (!this.authentication.isAccessTokenValid((Account)account)) {
                    account.setSessionState(AccountSessionState.REFRESHING);
                    this.authentication.refreshMojangSession(account);
                }
                account.setSessionState(AccountSessionState.VALID);
            }
            else {
                account.setSessionState(AccountSessionState.OFFLINE);
            }
        }
        catch (Throwable e) {
            account.setSessionState(AccountSessionState.EXPIRED);
        }
    }
}
