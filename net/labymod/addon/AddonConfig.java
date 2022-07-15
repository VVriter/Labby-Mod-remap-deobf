//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon;

import com.google.gson.*;

public class AddonConfig
{
    private JsonObject config;
    
    public AddonConfig() {
        this.config = new JsonObject();
    }
    
    public JsonObject getConfig() {
        return this.config;
    }
}
