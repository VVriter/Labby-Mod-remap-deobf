//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.servermanager;

import net.labymod.utils.manager.*;
import net.labymod.api.permissions.*;
import java.io.*;
import net.labymod.ingamegui.*;
import com.google.common.reflect.*;
import net.labymod.api.*;
import net.labymod.settings.elements.*;
import net.labymod.addon.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.labymod.support.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class ServerManager
{
    private final ConfigManager<ServerConfig> serverConfigManager;
    private final ServerConfig config;
    private final List<Server> servers;
    private final Map<Permissions.Permission, Boolean> permissionMap;
    private Server currentServer;
    private bse prevServer;
    private long lastSecond;
    
    public ServerManager() {
        this.servers = new ArrayList<Server>();
        this.permissionMap = new HashMap<Permissions.Permission, Boolean>();
        this.lastSecond = 0L;
        this.serverConfigManager = new ConfigManager<ServerConfig>(new File("LabyMod/", "servers.json"), ServerConfig.class);
        this.config = this.serverConfigManager.getSettings();
    }
    
    public void init() {
        try {
            for (final ClassPath.ClassInfo classInfo : ClassPath.from(Module.class.getClassLoader()).getTopLevelClassesRecursive("net.labymod.servermanager.servers")) {
                final Server server = classInfo.load().newInstance();
                final LabyModAddon dummyAddon = new LabyModAddon() {
                    public void onEnable() {
                        ServerManager.this.registerServerSupport(this, server);
                    }
                    
                    public void onDisable() {
                    }
                    
                    public void loadConfig() {
                    }
                    
                    protected void fillSettings(final List<SettingsElement> subSettings) {
                    }
                };
                dummyAddon.onEnable();
                dummyAddon.init(server.getName(), UUID.randomUUID());
                AddonLoader.getAddons().add(dummyAddon);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void registerServerSupport(final LabyModAddon labyModAddon, final Server server) {
        server.bindAddon(labyModAddon);
        this.servers.add(server);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        final bse currentServerData = bib.z().C();
        if (currentServerData != null && LabyModCore.getMinecraft().getConnection() != null && this.prevServer != currentServerData) {
            this.prevServer = currentServerData;
            this.updateServer(currentServerData.b);
            if (this.currentServer != null) {
                this.currentServer.onJoin(currentServerData);
            }
            LabyMod.getInstance().onJoinServer(currentServerData);
        }
        else if (currentServerData == null && this.prevServer != null) {
            this.reset();
            this.updateServer(null);
            this.prevServer = null;
            Debug.log(Debug.EnumDebugMode.MINECRAFT, "Disconnected from server");
        }
        if (this.lastSecond < System.currentTimeMillis()) {
            this.lastSecond = System.currentTimeMillis() + 1000L;
            if (this.currentServer != null) {
                this.currentServer.loopSecond();
            }
        }
    }
    
    public ChatDisplayAction handleChatMessage(final String clean, final String formatted) {
        try {
            return (this.currentServer == null) ? ChatDisplayAction.NORMAL : this.currentServer.handleChatMessage(clean, formatted);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ChatDisplayAction.NORMAL;
        }
    }
    
    public void updateServer(final String ip) {
        if (ip == null) {
            this.currentServer = null;
            return;
        }
        for (final Server server : this.servers) {
            for (final String addresses : server.getAddressNames()) {
                if (ip.toLowerCase().contains(addresses.toLowerCase())) {
                    this.currentServer = server;
                    return;
                }
            }
        }
    }
    
    public void reset() {
        this.permissionMap.clear();
        for (final Server server : this.servers) {
            server.reset();
        }
    }
    
    public void draw() {
        if (this.currentServer == null) {
            return;
        }
        this.currentServer.draw();
    }
    
    public boolean isServer(final Class<? extends Server> serverClass) {
        return this.currentServer != null && this.currentServer.getClass().equals(serverClass);
    }
    
    public boolean isAllowed(final Permissions.Permission permission) {
        if (bib.z().E()) {
            return true;
        }
        if (this.currentServer == null) {
            Boolean allowed = this.permissionMap.get(permission);
            if (allowed == null) {
                allowed = permission.isDefaultEnabled();
            }
            return allowed;
        }
        return this.currentServer.isAllowed(permission);
    }
    
    public ConfigManager<ServerConfig> getServerConfigManager() {
        return this.serverConfigManager;
    }
    
    public ServerConfig getConfig() {
        return this.config;
    }
    
    public List<Server> getServers() {
        return this.servers;
    }
    
    public Map<Permissions.Permission, Boolean> getPermissionMap() {
        return this.permissionMap;
    }
    
    public Server getCurrentServer() {
        return this.currentServer;
    }
    
    public void setPrevServer(final bse prevServer) {
        this.prevServer = prevServer;
    }
}
