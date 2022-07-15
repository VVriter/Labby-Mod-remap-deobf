//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager;

import java.io.*;
import java.util.concurrent.*;
import net.labymod.accountmanager.storage.credentials.*;
import java.util.function.*;
import net.labymod.accountmanager.storage.account.*;

public class AsyncAccountManager extends AccountManager
{
    private final ExecutorService executor;
    
    public AsyncAccountManager(final File file) {
        super(file);
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    public AsyncAccountManager(final File file, final CredentialsAccessor accessor, final File minecraftDirectory) {
        super(file, accessor, minecraftDirectory);
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    public void loadAsync(final Runnable... callback) {
        int length;
        int i = 0;
        Runnable runnable;
        this.executor.execute(() -> {
            try {
                this.load();
                for (length = callback.length; i < length; ++i) {
                    runnable = callback[i];
                    runnable.run();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void refreshExternalSessionsAsync(final Consumer<Account> accountCallback, final Runnable... callback) {
        int length;
        int i = 0;
        Runnable runnable;
        this.executor.execute(() -> {
            try {
                this.refreshExternalSessions((Consumer)accountCallback);
                for (length = callback.length; i < length; ++i) {
                    runnable = callback[i];
                    runnable.run();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void refreshLauncherSessionsAsync(final Consumer<Account> accountCallback, final Runnable... callback) {
        int length;
        int i = 0;
        Runnable runnable;
        this.executor.execute(() -> {
            try {
                this.refreshLauncherSessions((Consumer)accountCallback);
                for (length = callback.length; i < length; ++i) {
                    runnable = callback[i];
                    runnable.run();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void saveAsync(final Runnable... callback) {
        int length;
        int i = 0;
        Runnable runnable;
        this.executor.execute(() -> {
            try {
                this.save();
                for (length = callback.length; i < length; ++i) {
                    runnable = callback[i];
                    runnable.run();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
