//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.servermanager;

import com.google.gson.*;
import java.util.*;

public class ServerConfig
{
    private Map<String, JsonObject> serverConfigs;
    
    public ServerConfig() {
        this.serverConfigs = new HashMap<String, JsonObject>();
    }
    
    public Map<String, JsonObject> getServerConfigs() {
        return this.serverConfigs;
    }
}
