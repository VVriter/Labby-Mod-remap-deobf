//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tools;

import java.io.*;
import net.labymod.utils.manager.*;
import net.labymod.ingamechat.tools.filter.*;
import net.labymod.ingamechat.tools.shortcuts.*;
import net.labymod.ingamechat.tools.playermenu.*;
import net.minecraftforge.common.*;
import net.labymod.ingamechat.tools.autotext.*;
import net.labymod.ingamechat.*;
import java.util.*;

public class ChatToolManager
{
    private final File fileAutoText;
    private final File fileFilters;
    private final File fileShortcuts;
    private final File filePlayerMenu;
    private ConfigManager<AutoTextKeyBinds> configAutoText;
    private ConfigManager<Filters> configFilters;
    private ConfigManager<Shortcuts> configShortcuts;
    private ConfigManager<PlayerMenu> configPlayerMenu;
    
    public ChatToolManager() {
        this.fileAutoText = new File("LabyMod/", "autotext.json");
        this.fileFilters = new File("LabyMod/", "filters.json");
        this.fileShortcuts = new File("LabyMod/", "shortcuts.json");
        this.filePlayerMenu = new File("LabyMod/", "playermenu.json");
    }
    
    public void initTools() {
        this.configAutoText = new ConfigManager<AutoTextKeyBinds>(this.fileAutoText, AutoTextKeyBinds.class);
        this.configFilters = new ConfigManager<Filters>(this.fileFilters, Filters.class);
        this.configShortcuts = new ConfigManager<Shortcuts>(this.fileShortcuts, Shortcuts.class);
        this.configPlayerMenu = new ConfigManager<PlayerMenu>(this.filePlayerMenu, PlayerMenu.class);
        MinecraftForge.EVENT_BUS.register(new AutoTextListener());
        IngameChatManager.INSTANCE.updateRooms();
    }
    
    public void saveTools() {
        this.configAutoText.save();
        this.configFilters.save();
        this.configShortcuts.save();
        this.configPlayerMenu.save();
        IngameChatManager.INSTANCE.updateRooms();
    }
    
    public List<AutoTextKeyBinds.AutoText> getAutoTextKeyBinds() {
        return (List<AutoTextKeyBinds.AutoText>)this.configAutoText.getSettings().getAutoTextKeyBinds();
    }
    
    public List<Filters.Filter> getFilters() {
        return this.configFilters.getSettings().getFilters();
    }
    
    public List<Shortcuts.Shortcut> getShortcuts() {
        return this.configShortcuts.getSettings().getShortcuts();
    }
    
    public List<PlayerMenu.PlayerMenuEntry> getPlayerMenu() {
        return this.configPlayerMenu.getSettings().getPlayerMenuEntries();
    }
}
