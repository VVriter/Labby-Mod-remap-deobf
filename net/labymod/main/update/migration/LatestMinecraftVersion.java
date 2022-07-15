//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration;

import com.google.gson.*;
import net.labymod.main.update.*;
import java.util.*;

public class LatestMinecraftVersion
{
    public short[] minecraftVersionRaw;
    public String minecraftVersion;
    public String labymodVersion;
    public String mcUrl;
    public String url;
    public boolean isPlusOnly;
    
    public LatestMinecraftVersion(final short[] version, final JsonObject object) {
        this.minecraftVersionRaw = version;
        this.minecraftVersion = version[0] + "." + version[1] + "." + version[2];
        this.labymodVersion = object.get("version").getAsString();
        this.mcUrl = object.get("mcUrl").getAsString();
        this.url = (object.has("url") ? object.get("url").getAsString() : object.get("plusUrl").getAsString());
        this.isPlusOnly = object.has("plusUrl");
    }
    
    public static LatestMinecraftVersion from(final JsonObject object) {
        short[] latestVersion = null;
        JsonObject latestObject = null;
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            final short[] version = UpdateData.getShortVersionOfString(entry.getKey());
            if (latestVersion == null || LabyModUpdateChecker.isClientOutdated(latestVersion, version)) {
                latestVersion = version;
                latestObject = entry.getValue().getAsJsonObject();
            }
        }
        return new LatestMinecraftVersion(latestVersion, latestObject);
    }
}
