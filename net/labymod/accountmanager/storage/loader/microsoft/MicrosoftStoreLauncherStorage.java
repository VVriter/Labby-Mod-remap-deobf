//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft;

import net.labymod.accountmanager.storage.credentials.*;
import java.io.*;
import net.labymod.accountmanager.storage.loader.microsoft.model.*;
import net.labymod.accountmanager.storage.*;
import java.util.*;

public class MicrosoftStoreLauncherStorage extends MicrosoftLauncherStorage
{
    public MicrosoftStoreLauncherStorage(final CredentialsAccessor credentialsAccessor, final File minecraftDirectory) {
        super(credentialsAccessor, minecraftDirectory);
    }
    
    public File getFile() {
        return new File(this.minecraftDirectory, "launcher_accounts_microsoft_store.json");
    }
    
    public void load() throws Exception {
        super.load();
        if (this.isLoaded()) {
            for (final LauncherAccount account : this.getAccounts()) {
                account.setStorageType(StorageType.MICROSOFT_STORE);
            }
        }
    }
    
    public StorageType getType() {
        return StorageType.MICROSOFT_STORE;
    }
}
