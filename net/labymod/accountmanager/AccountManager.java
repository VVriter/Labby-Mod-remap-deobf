//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager;

import net.labymod.accountmanager.storage.*;
import net.labymod.accountmanager.storage.loader.external.*;
import java.io.*;
import net.labymod.accountmanager.storage.credentials.*;
import net.labymod.accountmanager.storage.loader.microsoft.*;
import net.labymod.accountmanager.storage.loader.java.*;
import java.util.function.*;
import net.labymod.accountmanager.storage.account.*;
import java.util.*;

public class AccountManager
{
    private final Map<StorageType, AccountStorage<? extends Account>> launcherStorages;
    private final ExternalAccountStorage storage;
    
    public AccountManager(final File file, final CredentialsAccessor accessor, final File minecraftDirectory) {
        this(file);
        this.launcherStorages.put(StorageType.MICROSOFT_STORE, new MicrosoftStoreLauncherStorage(accessor, minecraftDirectory));
        this.launcherStorages.put(StorageType.MICROSOFT, new MicrosoftLauncherStorage(accessor, minecraftDirectory));
        this.launcherStorages.put(StorageType.JAVA, new JavaProfileLauncherStorage(minecraftDirectory));
    }
    
    public AccountManager(final File file) {
        this.launcherStorages = new HashMap<StorageType, AccountStorage<? extends Account>>();
        this.storage = new ExternalAccountStorage(file);
    }
    
    public void load() throws Exception {
        this.storage.load();
        this.storage.initializeAuthentication();
        for (final AccountStorage<? extends Account> storage : this.launcherStorages.values()) {
            try {
                storage.load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void refreshExternalSessions() {
        this.refreshExternalSessions(account -> {});
    }
    
    public void refreshExternalSessions(final Consumer<Account> callback) {
        if (!this.storage.isLoaded()) {
            return;
        }
        this.storage.setAllSessionStatesTo(AccountSessionState.VALIDATING);
        this.storage.refreshAccounts(callback);
    }
    
    public void refreshLauncherSessions() {
        this.refreshLauncherSessions(account -> {});
    }
    
    public void refreshLauncherSessions(final Consumer<Account> callback) {
        for (final AccountStorage<? extends Account> storage : this.launcherStorages.values()) {
            if (!storage.isLoaded()) {
                continue;
            }
            if (!storage.isAuthenticationInitialized()) {
                storage.initializeAuthentication();
            }
            storage.setAllSessionStatesTo(AccountSessionState.VALIDATING);
        }
        for (final AccountStorage<? extends Account> storage : this.launcherStorages.values()) {
            if (!storage.isLoaded()) {
                continue;
            }
            storage.refreshAccounts(callback);
        }
    }
    
    public void save() throws Exception {
        this.storage.save();
    }
    
    public Account[] getAccounts() {
        final List<Account> accounts = new ArrayList<Account>();
        for (final AccountStorage<? extends Account> storage : this.launcherStorages.values()) {
            if (!storage.isLoaded()) {
                continue;
            }
            accounts.addAll(storage.getAccounts());
        }
        accounts.addAll(this.storage.getAccounts());
        final Account[] array = new Account[accounts.size()];
        accounts.toArray(array);
        return array;
    }
    
    public Collection<StorageType> getLauncherStorageTypes() {
        return this.launcherStorages.keySet();
    }
    
    public AccountStorage<? extends Account> getLauncherStorageOf(final StorageType type) {
        if (type == StorageType.EXTERNAL) {
            return this.storage;
        }
        return this.launcherStorages.get(type);
    }
    
    public ExternalAccountStorage getStorage() {
        return this.storage;
    }
}
