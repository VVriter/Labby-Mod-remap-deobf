//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage;

import net.labymod.accountmanager.authentication.*;
import java.util.function.*;
import java.util.*;
import net.labymod.accountmanager.storage.account.*;

public abstract class AbstractAccountStorage<T extends Account> implements AccountStorage<T>
{
    protected AccountAuthentication authentication;
    
    @Override
    public boolean isAuthenticationInitialized() {
        return this.authentication != null;
    }
    
    @Override
    public void initializeAuthentication() {
        this.authentication = (AccountAuthentication)new AsyncAccountAuthentication(this.getClientToken());
        this.loadKeyChain();
    }
    
    @Override
    public AccountAuthentication getAuthentication() {
        return this.authentication;
    }
    
    @Override
    public void refreshAccounts(final Consumer<Account> callback) {
        for (final T account : this.getAccounts()) {
            this.refreshAccount(account);
            callback.accept(account);
        }
    }
    
    private void refreshAccount(final T account) {
        if (!this.isAuthenticationInitialized()) {
            throw new RuntimeException("Authentication has to be initialized before refreshing an account");
        }
        try {
            if (account.isPremium()) {
                if (account.isMicrosoft()) {
                    account.setSessionState(account.isAccessTokenExpired() ? AccountSessionState.EXPIRED : AccountSessionState.VALID);
                }
                else {
                    this.authentication.updateMojangSessionState((Account)account);
                }
            }
            else {
                account.setSessionState(AccountSessionState.OFFLINE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            account.setSessionState(AccountSessionState.EXPIRED);
        }
    }
}
