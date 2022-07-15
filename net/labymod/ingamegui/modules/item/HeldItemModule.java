//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules.item;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.api.permissions.*;
import net.labymod.ingamegui.*;

public class HeldItemModule extends ItemModule
{
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.DIAMOND_SWORD);
    }
    
    public String getSettingName() {
        return "held_item";
    }
    
    @Override
    public nf getPlaceholderTexture() {
        return ModTextures.ITEM_SWORD;
    }
    
    @Override
    public aip getItem() {
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return null;
        }
        final aip itemStack = LabyModCore.getMinecraft().getMainHandItem();
        final int id = (itemStack == null) ? 0 : ain.a(itemStack.c());
        return (id == 0) ? null : itemStack;
    }
    
    public boolean isShown() {
        final boolean flag = LabyModCore.getMinecraft().getPlayer() != null && LabyModCore.getMinecraft().getMainHandItem() != null;
        if (flag) {
            final aip itemStack = LabyModCore.getMinecraft().getMainHandItem();
            final int id = ain.a(itemStack.c());
            return id != 0 && Permissions.isAllowed(Permissions.Permission.GUI_ITEM_HUD);
        }
        return false;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_ITEMS;
    }
    
    public void loadSettings() {
    }
    
    public int getSortingId() {
        return 0;
    }
    
    @Override
    public boolean drawDurability() {
        return true;
    }
}
