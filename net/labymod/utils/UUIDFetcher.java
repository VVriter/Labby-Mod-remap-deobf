//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.net.*;
import java.io.*;
import com.mojang.util.*;
import net.labymod.ingamechat.namehistory.*;
import com.google.gson.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class UUIDFetcher
{
    public static final long FEBRUARY_2015 = 1422748800000L;
    private static Gson gson;
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static final String DATE_URL = "https://api.minecraftservices.com/minecraft/profile/namechange";
    private static Map<String, UUID> uuidCache;
    private static Map<UUID, String> nameCache;
    private static ExecutorService pool;
    public String name;
    public UUID id;
    public long changedToAt;
    
    public static void getUUID(final String name, final Consumer<UUID> action) {
        UUIDFetcher.pool.execute(new Runnable() {
            @Override
            public void run() {
                action.accept((Object)UUIDFetcher.getUUID(name));
            }
        });
    }
    
    public static UUID getUUID(final String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }
    
    public static void getUUIDAt(final String name, final long timestamp, final Consumer<UUID> action) {
        UUIDFetcher.pool.execute(new Runnable() {
            @Override
            public void run() {
                action.accept((Object)UUIDFetcher.getUUIDAt(name, timestamp));
            }
        });
    }
    
    public static void getDate(final String token, final Consumer<String> action) {
        UUIDFetcher.pool.execute(new Runnable() {
            @Override
            public void run() {
                action.accept((Object)UUIDFetcher.getDate(token));
            }
        });
    }
    
    public static String getDate(final String token) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL("https://api.minecraftservices.com/minecraft/profile/namechange").openConnection();
            connection.setReadTimeout(5000);
            connection.addRequestProperty("Authorization", "Bearer " + token);
            final JsonObject data = (JsonObject)UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)JsonObject.class);
            return data.get("createdAt").getAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static UUID getUUIDAt(String name, final long timestamp) {
        name = name.toLowerCase();
        if (UUIDFetcher.uuidCache.containsKey(name)) {
            return UUIDFetcher.uuidCache.get(name);
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", name, timestamp / 1000L)).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher data = (UUIDFetcher)UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher.class);
            if (data == null) {
                return null;
            }
            UUIDFetcher.uuidCache.put(name, data.id);
            UUIDFetcher.nameCache.put(data.id, data.name);
            return data.id;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getCorrectUsername(String name, final long timestamp) {
        name = name.toLowerCase();
        if (UUIDFetcher.uuidCache.containsKey(name) && UUIDFetcher.nameCache.containsKey(UUIDFetcher.uuidCache.get(name))) {
            return UUIDFetcher.nameCache.get(UUIDFetcher.uuidCache.get(name));
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", name, timestamp / 1000L)).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher data = (UUIDFetcher)UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher.class);
            if (data == null) {
                return null;
            }
            UUIDFetcher.uuidCache.put(name, data.id);
            UUIDFetcher.nameCache.put(data.id, data.name);
            return data.name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void getName(final UUID uuid, final Consumer<String> action) {
        UUIDFetcher.pool.execute(new Runnable() {
            @Override
            public void run() {
                action.accept((Object)UUIDFetcher.getName(uuid));
            }
        });
    }
    
    public static void getCorrectUsername(final String name, final Consumer<String> action) {
        UUIDFetcher.pool.execute(new Runnable() {
            @Override
            public void run() {
                action.accept((Object)UUIDFetcher.getCorrectUsername(name, System.currentTimeMillis()));
            }
        });
    }
    
    public static String getName(final UUID uuid) {
        if (UUIDFetcher.nameCache.containsKey(uuid)) {
            return UUIDFetcher.nameCache.get(uuid);
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher[] nameHistory = (UUIDFetcher[])UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher[].class);
            final UUIDFetcher currentNameData = nameHistory[nameHistory.length - 1];
            UUIDFetcher.uuidCache.put(currentNameData.name.toLowerCase(), uuid);
            UUIDFetcher.nameCache.put(uuid, currentNameData.name);
            return currentNameData.name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static NameHistory getHistory(final UUID uuid) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher[] nameHistory = (UUIDFetcher[])UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher[].class);
            return new NameHistory(uuid, nameHistory);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        UUIDFetcher.gson = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
        UUIDFetcher.uuidCache = new HashMap<String, UUID>();
        UUIDFetcher.nameCache = new HashMap<UUID, String>();
        UUIDFetcher.pool = Executors.newCachedThreadPool();
    }
}
