//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import java.util.*;
import com.google.gson.stream.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class PinManager
{
    private static final Gson GSON;
    private Map<UUID, Pin> pins;
    
    private PinManager() {
        this.pins = new HashMap<UUID, Pin>();
    }
    
    public void invalidatePinOf(final UUID uuid) {
        this.pins.remove(uuid);
    }
    
    public void update(final UUID uuid, final String pin, final long expiresAt) {
        this.pins.put(uuid, new Pin(pin, expiresAt));
        try {
            final FileWriter writer = new FileWriter("LabyMod/pins.json");
            PinManager.GSON.toJson((Object)this, (Appendable)writer);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasValidPin(final UUID uuid) {
        return this.getValidPinOf(uuid) != null;
    }
    
    public String getValidPinOf(final UUID uuid) {
        final Pin pin = this.pins.get(uuid);
        return (pin == null || pin.isExpired()) ? null : pin.getPin();
    }
    
    public static PinManager load() {
        if (new File("LabyMod/pins.json").exists()) {
            try (final JsonReader reader = new JsonReader((Reader)new FileReader("LabyMod/pins.json"))) {
                return (PinManager)PinManager.GSON.fromJson(reader, (Type)PinManager.class);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new PinManager();
    }
    
    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public static class Pin
    {
        private final String pin;
        private final long expiresAt;
        
        public Pin(final String pin, final long expiresAt) {
            this.pin = pin;
            this.expiresAt = expiresAt;
        }
        
        public String getPin() {
            return this.pin;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > this.expiresAt;
        }
    }
}
