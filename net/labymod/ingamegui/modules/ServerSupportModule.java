//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.servermanager.*;
import net.labymod.ingamegui.moduletypes.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.main.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ServerSupportModule extends TextModule
{
    private List<Server.DisplayLine> lines;
    private boolean showKills;
    
    public ServerSupportModule() {
        this.lines = new ArrayList<Server.DisplayLine>();
    }
    
    @Override
    public String[] getKeys() {
        final ArrayList<String> list = new ArrayList<String>();
        for (final Server.DisplayLine line : this.lines) {
            list.add(line.getKey());
        }
        final String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }
    
    @Override
    public List<List<Text>> getTextValues() {
        final ArrayList<List<Text>> list = new ArrayList<List<Text>>();
        for (final Server.DisplayLine line : this.lines) {
            for (final Text word : line.getValues()) {
                if (word.isDefaultColor()) {
                    word.setColor(this.valueColor);
                }
            }
            list.add(line.getValues());
        }
        return list;
    }
    
    @Override
    public String[] getDefaultKeys() {
        return new String[] { "Server Support" };
    }
    
    @Override
    public List<List<Text>> getDefaultTextValues() {
        return Collections.singletonList(Collections.singletonList(Text.getText("No server found", -1)));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        final Server currentServer = LabyMod.getInstance().getServerManager().getCurrentServer();
        if (currentServer == null && !this.lines.isEmpty()) {
            this.lines.clear();
        }
        else if (currentServer != null) {
            final ArrayList<Server.DisplayLine> lines = new ArrayList<Server.DisplayLine>();
            currentServer.addModuleLines(lines);
            this.lines = lines;
        }
    }
    
    public boolean isShown() {
        return !this.lines.isEmpty();
    }
    
    public void loadSettings() {
        this.showKills = Boolean.valueOf(this.getAttribute("kills", "true"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.IRON_SWORD), "Show Kills", "kills"));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "server_support";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 10;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public boolean isShowKills() {
        return this.showKills;
    }
}
