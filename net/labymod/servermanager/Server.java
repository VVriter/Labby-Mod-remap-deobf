//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.servermanager;

import com.google.gson.*;
import net.labymod.api.*;
import net.labymod.main.*;
import net.labymod.api.events.*;
import net.labymod.ingamegui.modules.*;
import net.labymod.ingamegui.*;
import net.labymod.ingamegui.moduletypes.*;
import java.util.*;
import net.labymod.api.permissions.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import io.netty.buffer.*;
import net.labymod.core.*;
import java.beans.*;

public abstract class Server
{
    protected JsonParser jsonParser;
    private String name;
    private String[] addressNames;
    protected int kills;
    private JsonObject config;
    private LabyModAddon bindedAddon;
    
    public Server(final String name, final String... addressNames) {
        this.jsonParser = new JsonParser();
        this.name = name;
        this.addressNames = addressNames;
        LabyMod.getInstance().getEventManager().register((TabListEvent)new TabListEvent() {
            public void onUpdate(final TabListEvent.Type type, final String formattedText, final String unformattedText) {
                if (!LabyMod.getInstance().getServerManager().isServer(Server.this.getClass())) {
                    return;
                }
                try {
                    Server.this.handleTabInfoMessage(type, formattedText, unformattedText);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        LabyMod.getInstance().getEventManager().register((PluginMessageEvent)new PluginMessageEvent() {
            public void receiveMessage(final String channelName, final gy packetBuffer) {
                try {
                    Server.this.handlePluginMessage(channelName, packetBuffer);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        final Map<String, JsonObject> list = LabyMod.getInstance().getServerManager().getServerConfigManager().getSettings().getServerConfigs();
        if (list.keySet().contains(name)) {
            this.config = list.get(name);
        }
        else {
            this.config = new JsonObject();
            LabyMod.getInstance().getServerManager().getConfig().getServerConfigs().put(name, this.config);
            LabyMod.getInstance().getServerManager().getServerConfigManager().save();
        }
        this.initConfig(this.config);
        this.loadConfig();
    }
    
    public abstract void onJoin(final bse p0);
    
    public abstract ChatDisplayAction handleChatMessage(final String p0, final String p1) throws Exception;
    
    public abstract void handlePluginMessage(final String p0, final gy p1) throws Exception;
    
    public abstract void handleTabInfoMessage(final TabListEvent.Type p0, final String p1, final String p2) throws Exception;
    
    public void reset() {
        this.kills = 0;
    }
    
    public void draw() {
    }
    
    public void addModuleLines(final List<DisplayLine> lines) {
        if (this.kills != 0) {
            final ServerSupportModule serverSupportModule = (ServerSupportModule)Module.getModuleByClass((Class)ServerSupportModule.class);
            if (serverSupportModule != null && serverSupportModule.isShowKills()) {
                lines.add(new DisplayLine("Kills", Collections.singletonList(ColoredTextModule.Text.getText(String.valueOf(this.kills)))));
            }
        }
    }
    
    public void loopSecond() {
    }
    
    public boolean isAllowed(final Permissions.Permission permission) {
        return permission.isDefaultEnabled();
    }
    
    @Deprecated
    protected void initConfig(final JsonObject config) {
    }
    
    public void loadConfig() {
    }
    
    public void saveConfig() {
        LabyMod.getInstance().getServerManager().getConfig().getServerConfigs().put(this.name, this.config);
        LabyMod.getInstance().getServerManager().getServerConfigManager().save();
    }
    
    public abstract void fillSubSettings(final List<SettingsElement> p0);
    
    public void bindAddon(final LabyModAddon labyModAddon) {
        this.bindedAddon = labyModAddon;
        if (this.bindedAddon != null) {
            this.fillSubSettings(this.bindedAddon.getSubSettings());
        }
    }
    
    public boolean getBooleanAttribute(final String attribute, final boolean defaultValue) {
        if (!this.config.has(attribute)) {
            this.config.addProperty(attribute, Boolean.valueOf(defaultValue));
        }
        return this.config.get(attribute).getAsBoolean();
    }
    
    public void setBooleanAttribute(final String attribute, final boolean value) {
        this.config.addProperty(attribute, Boolean.valueOf(value));
    }
    
    public void sendPluginMessage(final String channelName, final Consumer<gy> packetBufferConsumer) {
        final gy packetBuffer = new gy(Unpooled.buffer());
        packetBufferConsumer.accept(packetBuffer);
        LabyModCore.getMinecraft().sendPluginMessage(channelName, packetBuffer);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getAddressNames() {
        return this.addressNames;
    }
    
    public JsonObject getConfig() {
        return this.config;
    }
    
    public LabyModAddon getBindedAddon() {
        return this.bindedAddon;
    }
    
    public static class DisplayLine
    {
        private String key;
        private List<ColoredTextModule.Text> values;
        
        @ConstructorProperties({ "key", "values" })
        public DisplayLine(final String key, final List<ColoredTextModule.Text> values) {
            this.key = key;
            this.values = values;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public List<ColoredTextModule.Text> getValues() {
            return this.values;
        }
    }
}
