//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api;

import net.labymod.settings.elements.*;
import com.google.gson.*;
import net.labymod.utils.manager.*;
import java.util.*;
import net.labymod.addon.*;
import java.io.*;

public abstract class LabyModAddon
{
    private List<SettingsElement> subSettings;
    private JsonObject config;
    private ConfigManager<AddonConfig> configManager;
    public About about;
    public LabyModAPI api;
    
    public LabyModAddon() {
        this.subSettings = new ArrayList<SettingsElement>();
    }
    
    public abstract void onEnable();
    
    @Deprecated
    public void onDisable() {
    }
    
    public abstract void loadConfig();
    
    protected abstract void fillSettings(final List<SettingsElement> p0);
    
    public void init(final String addonName, final UUID uuid) {
        this.about = new About(uuid, addonName);
        this.configManager = new ConfigManager<AddonConfig>(new File(AddonLoader.getConfigDirectory(), addonName + ".json"), AddonConfig.class);
        this.config = this.configManager.getSettings().getConfig();
        this.loadConfig();
        this.fillSettings(this.subSettings);
        this.about.loaded = true;
    }
    
    public void saveConfig() {
        if (this.configManager != null) {
            this.configManager.save();
        }
    }
    
    public LabyModAPI getApi() {
        return this.api;
    }
    
    public void onRenderPreview(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public void onMouseClickedPreview(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public List<SettingsElement> getSubSettings() {
        return this.subSettings;
    }
    
    public JsonObject getConfig() {
        return this.config;
    }
}
