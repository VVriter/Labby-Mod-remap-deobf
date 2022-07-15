//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.core.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class BiomeModule extends SimpleModule
{
    @Override
    public String getDisplayName() {
        return "Biome";
    }
    
    @Override
    public String getDisplayValue() {
        if (LabyModCore.getMinecraft().getWorld() == null || LabyModCore.getMinecraft().getPlayer() == null || LabyModCore.getMinecraft().getPlayer().c() == null) {
            return "?";
        }
        return LabyModCore.getMinecraft().getBiome();
    }
    
    public void loadSettings() {
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "biome";
    }
    
    public String getDescription() {
        return "Shows the biome";
    }
    
    public int getSortingId() {
        return 5;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
