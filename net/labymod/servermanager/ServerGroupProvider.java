//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.servermanager;

import java.lang.reflect.*;
import net.labymod.servermanager.group.*;
import com.google.gson.*;
import net.labymod.utils.request.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;
import com.google.gson.reflect.*;

public class ServerGroupProvider
{
    private static final Type GROUP_MAP_TYPE;
    private final Gson gson;
    private final Map<String, ServerGroup> availableGroups;
    
    public ServerGroupProvider() {
        this.gson = new Gson();
        this.availableGroups = new HashMap<String, ServerGroup>();
        this.loadGroups();
    }
    
    private void loadGroups() {
        DownloadServerRequest.getJsonObjectAsync("https://dl.labymod.net/server_groups.json", new ServerResponse<JsonElement>() {
            @Override
            public void success(final JsonElement result) {
                final JsonObject obj = result.getAsJsonObject();
                final Map<String, ServerGroup> groups = (Map<String, ServerGroup>)ServerGroupProvider.this.gson.fromJson(obj.get("server_groups"), ServerGroupProvider.GROUP_MAP_TYPE);
                if (groups != null) {
                    ServerGroupProvider.this.availableGroups.putAll(groups);
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                ServerGroupProvider.this.availableGroups.clear();
            }
        });
    }
    
    public ServerGroup detectGroup() {
        return this.detectGroup(LabyMod.getInstance().getCurrentServerData());
    }
    
    public ServerGroup detectGroup(final ServerData data) {
        if (data == null) {
            return null;
        }
        for (final ServerGroup group : this.availableGroups.values()) {
            if (group.matches(data)) {
                return group;
            }
        }
        return null;
    }
    
    static {
        GROUP_MAP_TYPE = new TypeToken<Map<String, ServerGroup>>() {}.getType();
    }
}
