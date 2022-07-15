//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ServerAddressModule extends SimpleModule
{
    @Override
    public String getDisplayName() {
        return "IP";
    }
    
    @Override
    public String getDisplayValue() {
        return bib.z().C().b;
    }
    
    @Override
    public String getDefaultValue() {
        return "Unknown";
    }
    
    public boolean isShown() {
        return bib.z().C() != null;
    }
    
    public void loadSettings() {
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "server_address";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 9;
    }
}
