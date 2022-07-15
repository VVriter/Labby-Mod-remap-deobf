//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.java;

import net.labymod.accountmanager.storage.loader.*;
import net.labymod.accountmanager.storage.loader.java.model.*;
import java.io.*;
import java.util.*;
import net.labymod.accountmanager.storage.*;
import net.labymod.accountmanager.storage.account.*;
import com.google.gson.internal.*;

public class JavaProfileLauncherStorage extends JsonStorageImpl<LauncherProfiles, AuthenticationDatabase>
{
    private final File minecraftDirectory;
    private LauncherProfiles storage;
    
    public JavaProfileLauncherStorage(final File minecraftDirectory) {
        this.minecraftDirectory = minecraftDirectory;
    }
    
    public File getFile() {
        return new File(this.minecraftDirectory, "launcher_profiles.json");
    }
    
    @Override
    protected Class<?> onJsonObjectClass() {
        return LauncherProfiles.class;
    }
    
    public boolean isLoaded() {
        return this.storage != null && this.storage.authenticationDatabase != null;
    }
    
    @Override
    protected LauncherProfiles getStorage() {
        return this.storage;
    }
    
    @Override
    protected void onStorageLoaded(final LauncherProfiles storage) {
        this.storage = storage;
    }
    
    public Collection<AuthenticationDatabase> getAccounts() {
        return this.storage.authenticationDatabase.values();
    }
    
    public String getClientToken() {
        return this.storage.clientToken;
    }
    
    public StorageType getType() {
        return StorageType.JAVA;
    }
    
    public Account getActiveAccount() {
        if (this.storage.selectedUser == null) {
            return null;
        }
        String localId = null;
        if (this.storage.selectedUser instanceof String) {
            localId = (String)this.storage.selectedUser;
        }
        else if (this.storage.selectedUser instanceof LinkedTreeMap) {
            final LinkedTreeMap<String, String> selectedUser = (LinkedTreeMap<String, String>)this.storage.selectedUser;
            localId = (String)selectedUser.getOrDefault((Object)"account", (Object)null);
        }
        return (localId == null) ? null : ((Account)this.storage.authenticationDatabase.get(localId));
    }
}
