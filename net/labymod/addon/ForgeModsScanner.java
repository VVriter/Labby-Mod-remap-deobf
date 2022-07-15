//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon;

import java.io.*;
import com.google.common.hash.*;
import com.google.common.io.*;
import com.google.gson.*;

public class ForgeModsScanner
{
    private static final ForgeModsScanner instance;
    private JsonArray jsonArray;
    
    public static ForgeModsScanner getInstance() {
        return ForgeModsScanner.instance;
    }
    
    public ForgeModsScanner() {
        this.jsonArray = new JsonArray();
        try {
            final File modsFolder = new File("mods");
            if (!modsFolder.exists()) {
                return;
            }
            final File[] list = modsFolder.listFiles();
            if (list != null) {
                for (final File file : list) {
                    if (file != null && !file.isDirectory()) {
                        if (file.getName().endsWith(".jar")) {
                            try {
                                final JsonObject entry = new JsonObject();
                                entry.addProperty("hash", "sha256:" + Files.hash(file, Hashing.sha256()).toString());
                                entry.addProperty("name", file.getName());
                                this.jsonArray.add((JsonElement)entry);
                            }
                            catch (Exception error) {
                                error.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception error2) {
            error2.printStackTrace();
        }
    }
    
    public JsonArray getJsonArray() {
        return this.jsonArray;
    }
    
    static {
        instance = new ForgeModsScanner();
    }
}
