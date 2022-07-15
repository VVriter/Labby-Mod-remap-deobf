//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader;

import net.labymod.accountmanager.storage.*;
import com.google.gson.*;
import net.labymod.accountmanager.utils.*;
import net.labymod.accountmanager.storage.account.*;
import java.nio.charset.*;
import java.io.*;

public abstract class JsonStorageImpl<T, K extends Account> extends AbstractAccountStorage<K>
{
    protected final Gson gson;
    
    protected JsonStorageImpl() {
        final GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        this.registerTypeAdapters(builder);
        this.gson = builder.create();
    }
    
    protected void registerTypeAdapters(final GsonBuilder builder) {
    }
    
    protected abstract Class<?> onJsonObjectClass();
    
    protected abstract void onStorageLoaded(final T p0);
    
    protected abstract T getStorage();
    
    public void load() throws Exception {
        final File file = this.getFile();
        try {
            if (file.exists()) {
                String json;
                try (final FileInputStream input = new FileInputStream(file)) {
                    json = IOUtil.toString(input);
                }
                this.onStorageLoaded(this.gson.fromJson(json, (Class)this.onJsonObjectClass()));
                if (this.isLoaded()) {
                    this.setAllSessionStatesTo(AccountSessionState.UNKNOWN);
                }
            }
        }
        catch (Exception e) {
            if (!file.exists() || !file.delete()) {
                throw e;
            }
            e.printStackTrace();
        }
    }
    
    public void save() throws Exception {
        final File file = this.getFile();
        if (this.isLoaded()) {
            final String json = this.gson.toJson(this.getStorage());
            try (final FileOutputStream output = new FileOutputStream(file)) {
                IOUtil.write(json, output, StandardCharsets.UTF_8);
            }
        }
    }
}
