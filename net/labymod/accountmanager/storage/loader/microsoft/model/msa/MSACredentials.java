//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa;

import com.google.gson.*;
import com.google.gson.internal.*;
import java.util.*;

public class MSACredentials
{
    public static final Gson GSON;
    public String activeUserXuid;
    public Map<String, LinkedTreeMap<String, String>> credentials;
    
    public MSACredentials() {
        this.credentials = new HashMap<String, LinkedTreeMap<String, String>>();
    }
    
    public <T> T get(final String xuid, final XALType key, final Class<T> clazz) {
        return this.get(xuid, key.getKey(), clazz);
    }
    
    public <T> T get(final String xuid, final String key, final Class<T> clazz) {
        final LinkedTreeMap<String, String> userCredentials = this.credentials.get(xuid);
        if (userCredentials == null) {
            return null;
        }
        final String json = (String)userCredentials.get((Object)key);
        if (json == null) {
            return null;
        }
        return (T)MSACredentials.GSON.fromJson(json, (Class)clazz);
    }
    
    public <T> void put(final String xuid, final XALType key, final T object) {
        this.put(xuid, key.getKey(), object);
    }
    
    public <T> void put(final String xuid, final String key, final T object) {
        LinkedTreeMap<String, String> userCredentials = this.credentials.get(xuid);
        if (userCredentials == null) {
            this.credentials.put(xuid, userCredentials = (LinkedTreeMap<String, String>)new LinkedTreeMap());
        }
        userCredentials.put((Object)key, (Object)MSACredentials.GSON.toJson((Object)object));
    }
    
    static {
        GSON = new Gson();
    }
}
