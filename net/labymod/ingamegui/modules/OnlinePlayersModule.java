//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.core.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class OnlinePlayersModule extends SimpleModule
{
    @Override
    public String getDisplayName() {
        return "Online";
    }
    
    @Override
    public String getDisplayValue() {
        return String.valueOf(LabyModCore.getMinecraft().getConnection().d().size());
    }
    
    @Override
    public String getDefaultValue() {
        return "0";
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getConnection() != null && LabyModCore.getMinecraft().getConnection().d() != null && !LabyModCore.getMinecraft().getConnection().d().isEmpty();
    }
    
    public void loadSettings() {
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "online_players";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 8;
    }
}
