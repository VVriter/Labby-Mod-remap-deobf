//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication;

import java.util.concurrent.*;
import java.util.function.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import net.labymod.accountmanager.storage.account.*;

public class AsyncAccountAuthentication extends AccountAuthentication
{
    private final ExecutorService executor;
    
    public AsyncAccountAuthentication(final String clientToken) {
        super(clientToken);
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    public void authenticateMojangAsync(final String username, final String password, final Consumer<ExternalAccount> successCallback, final Consumer<Exception> exceptionConsumer) {
        this.executor.execute(() -> {
            try {
                successCallback.accept(this.authenticateMojang(username, password));
            }
            catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }
    
    public void authenticateMicrosoftAsync(final String code, final Consumer<ExternalAccount> successCallback, final Consumer<Exception> exceptionConsumer) {
        this.executor.execute(() -> {
            try {
                successCallback.accept(this.authenticateMicrosoft(code));
            }
            catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }
    
    public void refreshMojangSessionAsync(final ExternalAccount account, final Consumer<Exception> exceptionConsumer) {
        this.executor.execute(() -> {
            try {
                this.refreshMojangSession(account);
            }
            catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }
    
    public void refreshMicrosoftSessionAsync(final ExternalMicrosoftAccount account, final Consumer<Exception> exceptionConsumer) {
        this.executor.execute(() -> {
            try {
                this.refreshMicrosoftSession(account);
            }
            catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }
    
    public void refreshSessionsStateAsync(final Account account, final Consumer<Exception> exceptionConsumer) {
        this.executor.execute(() -> {
            try {
                this.updateMojangSessionState(account);
            }
            catch (Exception e) {
                exceptionConsumer.accept(e);
            }
        });
    }
}
