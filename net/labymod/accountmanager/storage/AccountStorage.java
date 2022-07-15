//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage;

import java.io.*;
import net.labymod.accountmanager.authentication.*;
import java.util.function.*;
import net.labymod.accountmanager.storage.account.*;
import java.util.*;

public interface AccountStorage<T extends Account>
{
    File getFile();
    
    boolean isLoaded();
    
    void load() throws Exception;
    
    default void loadKeyChain() {
    }
    
    void save() throws Exception;
    
    Collection<T> getAccounts();
    
    Account getActiveAccount();
    
    StorageType getType();
    
    String getClientToken();
    
    boolean isAuthenticationInitialized();
    
    void initializeAuthentication();
    
    AccountAuthentication getAuthentication();
    
    void refreshAccounts(final Consumer<Account> p0);
    
    default void setAllSessionStatesTo(final AccountSessionState state) {
        for (final Account account : this.getAccounts()) {
            account.setSessionState(state);
        }
    }
}
