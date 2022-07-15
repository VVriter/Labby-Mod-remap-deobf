//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules.item;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.api.permissions.*;
import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ArrowAmountModule extends ItemModule
{
    private static final ain ARROW_ITEM;
    private int lastAmount;
    
    @Override
    public nf getPlaceholderTexture() {
        return ModTextures.ITEM_ARROW;
    }
    
    public boolean isShown() {
        int amount = 0;
        if (!LabyMod.getInstance().isInGame()) {
            return true;
        }
        for (final aip itemStack : LabyModCore.getMinecraft().getPlayer().bv.a) {
            if (itemStack != null && ain.a(itemStack.c()) == Material.ARROW.getId()) {
                amount += LabyModCore.getMinecraft().getStackSize(itemStack);
            }
        }
        this.lastAmount = amount;
        return LabyModCore.getMinecraft().getPlayer() != null && this.lastAmount != 0 && Permissions.isAllowed(Permissions.Permission.GUI_ITEM_HUD);
    }
    
    @Override
    public aip getItem() {
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return null;
        }
        if (this.lastAmount == 0) {
            return null;
        }
        return new aip(ArrowAmountModule.ARROW_ITEM, this.lastAmount);
    }
    
    @Override
    public boolean drawDurability() {
        return false;
    }
    
    public void loadSettings() {
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.ARROW);
    }
    
    public String getSettingName() {
        return "arrowcount";
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_ITEMS;
    }
    
    static {
        ARROW_ITEM = ain.c(Material.ARROW.getId());
    }
}
