//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules.item;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import net.labymod.api.permissions.*;
import net.labymod.ingamegui.*;

public class LeggingsModule extends ItemModule
{
    @Override
    public nf getPlaceholderTexture() {
        return ModTextures.ITEM_LEGGINGS;
    }
    
    @Override
    public aip getItem() {
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return null;
        }
        return this.getItemInArmorSlot(1);
    }
    
    public void loadSettings() {
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.DIAMOND_LEGGINGS);
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getPlayer() != null && this.getItemInArmorSlot(1) != null && Permissions.isAllowed(Permissions.Permission.GUI_ARMOR_HUD);
    }
    
    public String getSettingName() {
        return "leggings";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_ITEMS;
    }
    
    public int getSortingId() {
        return 3;
    }
    
    @Override
    public boolean drawDurability() {
        return true;
    }
}
