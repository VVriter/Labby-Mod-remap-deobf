//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.core.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class EntityCountModule extends SimpleModule
{
    public static int renderedEntityCount;
    public static int totalEntityCount;
    private boolean showMaxEntityCount;
    
    public EntityCountModule() {
        this.showMaxEntityCount = true;
    }
    
    @Override
    public String getDisplayName() {
        return "Entities";
    }
    
    @Override
    public String getDisplayValue() {
        if (LabyModCore.getMinecraft().getWorld() == null || LabyModCore.getMinecraft().getPlayer() == null || LabyModCore.getMinecraft().getPlayer().c() == null) {
            return "?";
        }
        return EntityCountModule.renderedEntityCount + (this.showMaxEntityCount ? ("/" + EntityCountModule.totalEntityCount) : "");
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    public void loadSettings() {
        this.showMaxEntityCount = Boolean.valueOf(this.getAttribute("showMaxEntityCount", "true"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.MONSTER_EGG), "Show max entity count", "showMaxEntityCount"));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "entity_count";
    }
    
    public String getDescription() {
        return "Shows the count of rendered entities";
    }
    
    public int getSortingId() {
        return 5;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    static {
        EntityCountModule.renderedEntityCount = 0;
        EntityCountModule.totalEntityCount = 0;
    }
}
