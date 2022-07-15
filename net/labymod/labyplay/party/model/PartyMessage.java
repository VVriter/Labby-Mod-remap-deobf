//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.party.model;

import java.net.*;
import com.google.gson.*;
import java.util.*;
import net.labymod.main.*;
import java.beans.*;

public class PartyMessage
{
    private static final Gson GSON;
    private final String action;
    private final PartyMessageData[] data;
    
    public PartyMessage(final String action, final PartyMessageData[] data) {
        this.action = action;
        this.data = data;
    }
    
    public int getInt(final String key) throws ProtocolException {
        final JsonElement element = this.getElement(key);
        if (element == null) {
            throw new ProtocolException(String.format("Malformed JSON in Addon message / INT @ %s:%s", this.getAction(), key));
        }
        return element.getAsInt();
    }
    
    public UUID getUUID(final String key) throws ProtocolException {
        return UUID.fromString(this.getString(key));
    }
    
    public boolean getBoolean(final String key) throws ProtocolException {
        final JsonElement element = this.getElement(key);
        if (element == null) {
            throw new ProtocolException(String.format("Malformed JSON in Addon message / BOOL @ %s:%s", this.getAction(), key));
        }
        return element.getAsBoolean();
    }
    
    public String getString(final String key) throws ProtocolException {
        final String ret = this.getString(key, null);
        if (ret == null) {
            throw new ProtocolException(String.format("Malformed JSON in Addon message / String @ %s:%s", this.getAction(), key));
        }
        return ret;
    }
    
    public String getString(final String key, final String def) {
        final JsonElement element = this.getElement(key);
        return (element == null) ? def : element.getAsString();
    }
    
    public JsonElement getElement(final String key) {
        for (final PartyMessageData datum : this.data) {
            if (datum.key.equalsIgnoreCase(key)) {
                return datum.value;
            }
        }
        return null;
    }
    
    public String getAction() {
        return this.action;
    }
    
    public PartyMessageData[] getData() {
        return this.data;
    }
    
    static {
        GSON = new Gson();
    }
    
    public static class PartyMessageData
    {
        private String key;
        private JsonElement value;
        
        public PartyMessageData(final String key, final JsonElement value) {
            this.key = key;
            this.value = value;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public JsonElement getValue() {
            return this.value;
        }
    }
    
    public static class Builder
    {
        private final PartyActionTypes.Client action;
        private List<Pair> data;
        
        public Builder(final PartyActionTypes.Client action) {
            this.data = new LinkedList<Pair>();
            this.action = action;
        }
        
        public Builder putUUID(final String key, final UUID content) {
            this.data.add(new Pair(key, (JsonElement)new JsonPrimitive(content.toString())));
            return this;
        }
        
        public Builder putString(final String key, final String content) {
            this.data.add(new Pair(key, (JsonElement)new JsonPrimitive(content)));
            return this;
        }
        
        public Builder putInt(final String key, final int content) {
            this.data.add(new Pair(key, (JsonElement)new JsonPrimitive((Number)content)));
            return this;
        }
        
        public Builder putBoolean(final String key, final boolean content) {
            this.data.add(new Pair(key, (JsonElement)new JsonPrimitive(Boolean.valueOf(content))));
            return this;
        }
        
        public Builder setResource(final String key, final String... args) {
            this.data.add(new Pair("key", (JsonElement)new JsonPrimitive(key)));
            final JsonArray array = new JsonArray();
            for (final String arg : args) {
                array.add((JsonElement)new JsonPrimitive(arg));
            }
            return this.putArray("args", array);
        }
        
        public Builder putArray(final String key, final JsonArray array) {
            this.data.add(new Pair(key, (JsonElement)array));
            return this;
        }
        
        public PartyMessage build() {
            final PartyMessageData[] data = new PartyMessageData[this.data.size()];
            int i = 0;
            for (final Pair datum : this.data) {
                data[i] = new PartyMessageData(datum.getKey(), datum.getElement());
                ++i;
            }
            return new PartyMessage(this.action.getKey(), data);
        }
        
        public void send() {
            final PartyMessage partyMessage = this.build();
            final String json = PartyMessage.GSON.toJson((Object)partyMessage);
            System.out.println("[OUT] " + json);
            LabyMod.getInstance().getLabyModAPI().sendAddonMessage("party", json);
        }
        
        public static class Pair
        {
            private String key;
            private JsonElement element;
            
            public String getKey() {
                return this.key;
            }
            
            public JsonElement getElement() {
                return this.element;
            }
            
            @ConstructorProperties({ "key", "element" })
            public Pair(final String key, final JsonElement element) {
                this.key = key;
                this.element = element;
            }
        }
    }
}
