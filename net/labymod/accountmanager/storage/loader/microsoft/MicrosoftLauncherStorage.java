//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft;

import net.labymod.accountmanager.storage.loader.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.*;
import net.labymod.accountmanager.storage.credentials.*;
import java.io.*;
import java.util.*;
import net.labymod.accountmanager.storage.account.*;
import net.labymod.accountmanager.storage.*;

public class MicrosoftLauncherStorage extends JsonStorageImpl<LauncherAccounts, LauncherAccount>
{
    protected final CredentialsAccessor credentialsAccessor;
    protected final File minecraftDirectory;
    private LauncherAccounts storage;
    
    public MicrosoftLauncherStorage(final CredentialsAccessor credentialsAccessor, final File minecraftDirectory) {
        this.credentialsAccessor = credentialsAccessor;
        this.minecraftDirectory = minecraftDirectory;
    }
    
    public File getFile() {
        return new File(this.minecraftDirectory, "launcher_accounts.json");
    }
    
    protected Class<?> onJsonObjectClass() {
        return LauncherAccounts.class;
    }
    
    protected LauncherAccounts getStorage() {
        return this.storage;
    }
    
    public boolean isLoaded() {
        return this.storage != null && this.storage.accounts != null;
    }
    
    protected void onStorageLoaded(final LauncherAccounts storage) {
        this.storage = storage;
    }
    
    public void loadKeyChain() {
        if (this.credentialsAccessor == null || this.storage == null) {
            return;
        }
        if (this.storage.accounts != null) {
            for (final Map.Entry<String, LauncherAccount> entry : this.storage.accounts.entrySet()) {
                final LauncherAccount account = entry.getValue();
                if (!account.isPremium()) {
                    account.setSessionState(AccountSessionState.VALIDATING);
                    try {
                        final String accessToken = this.credentialsAccessor.getValue(this.getType(), (String)entry.getKey());
                        if (accessToken == null) {
                            continue;
                        }
                        account.setAccessToken(accessToken);
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            this.storage.mojangClientToken = this.credentialsAccessor.getValue(this.getType(), "mojangClientToken");
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    public Collection<LauncherAccount> getAccounts() {
        return this.storage.accounts.values();
    }
    
    public String getClientToken() {
        return this.storage.mojangClientToken;
    }
    
    public Account getActiveAccount() {
        return (this.storage.activeAccountLocalId == null) ? null : ((Account)this.storage.accounts.get(this.storage.activeAccountLocalId));
    }
    
    public StorageType getType() {
        return StorageType.MICROSOFT;
    }
}
