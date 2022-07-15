//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update;

import net.labymod.main.update.migration.*;
import java.util.concurrent.*;
import net.labymod.support.util.*;
import net.labymod.main.*;
import net.labymod.utils.request.*;
import com.google.gson.*;
import java.util.*;

public class LabyModUpdateChecker
{
    public static final short[] CLIENT_VERSION;
    private static final JsonParser JSON_PARSER;
    private LatestMinecraftVersion latestMinecraftVersion;
    
    public LabyModUpdateChecker() {
        this.latestMinecraftVersion = null;
    }
    
    public CompletableFuture<UpdateData> getUpdateData() {
        final CompletableFuture<UpdateData> future = new CompletableFuture<UpdateData>();
        DownloadServerRequest.getStringAsync("https://dl.labymod.net/versions.json", new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                final JsonObject jsonObject = LabyModUpdateChecker.JSON_PARSER.parse(json).getAsJsonObject();
                try {
                    LabyModUpdateChecker.this.latestMinecraftVersion = LatestMinecraftVersion.from(jsonObject);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                final String newVersionString = getLatestVersion(jsonObject);
                if (newVersionString != null) {
                    final short[] latestVersion = UpdateData.getShortVersionOfString(newVersionString);
                    final UpdateData data = new UpdateData(newVersionString, latestVersion, LabyModUpdateChecker.this.isOutdated(latestVersion));
                    future.complete(data);
                }
                else {
                    Debug.log(Debug.EnumDebugMode.UPDATER, "Minecraft " + Source.ABOUT_MC_VERSION + " has no valid version entry");
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                Debug.log(Debug.EnumDebugMode.UPDATER, "Error while checking latest labymod version info: " + exception.getMessage());
            }
        });
        return future;
    }
    
    private static String getLatestVersion(final JsonObject jsonObject) {
        final Map<String, String> mcVerToLMVerMap = new HashMap<String, String>();
        for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final String mcVersion = entry.getKey();
            final JsonObject object = entry.getValue().getAsJsonObject();
            final JsonElement versionElement = object.get("version");
            if (versionElement != null && mcVersion != null) {
                mcVerToLMVerMap.put(mcVersion, versionElement.getAsString());
            }
        }
        return mcVerToLMVerMap.get(Source.ABOUT_MC_VERSION);
    }
    
    private boolean isOutdated(final short[] latestVersion) {
        return latestVersion != null && isClientOutdated(LabyModUpdateChecker.CLIENT_VERSION, latestVersion);
    }
    
    public static boolean isClientOutdated(final short[] clientVersion, final short[] latestVersion) {
        final short latestVersionMajor = latestVersion[0];
        final short latestVersionMinor = (short)((latestVersion.length > 1) ? latestVersion[1] : 0);
        final short latestVersionPatch = (short)((latestVersion.length > 2) ? latestVersion[2] : 0);
        final short clientVersionMajor = clientVersion[0];
        final short clientVersionMinor = clientVersion[1];
        final short clientVersionPatch = clientVersion[2];
        return latestVersionMajor > clientVersionMajor || (latestVersionMajor == clientVersionMajor && latestVersionMinor > clientVersionMinor) || (latestVersionMajor == clientVersionMajor && latestVersionMinor == clientVersionMinor && latestVersionPatch > clientVersionPatch);
    }
    
    public LatestMinecraftVersion getLatestMinecraftVersion() {
        return this.latestMinecraftVersion;
    }
    
    static {
        CLIENT_VERSION = UpdateData.getShortVersionOfString("3.9.41");
        JSON_PARSER = new JsonParser();
    }
}
