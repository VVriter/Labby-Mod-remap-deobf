//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticHalloween extends CosmeticRenderer<CosmeticHalloweenData>
{
    public static final int ID = 15;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
    }
    
    public void setInvisible(final boolean invisible) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticHalloweenData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bus.e();
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.a(0.8, 0.8, 0.8);
        aip item = null;
        int itemId = 0;
        switch (cosmeticData.getEnumHalloweenType()) {
            case AXE_RIGHT: {
                itemId = 258;
                bus.b(-120.0f, 0.0f, 0.0f, 1.0f);
                bus.b(40.0f, 0.0f, 1.0f, 0.0f);
                bus.b(0.4, -0.4, 0.3);
                break;
            }
            case PICKAXE_TOP: {
                itemId = 257;
                bus.b(-30.0f, 0.0f, 0.0f, 1.0f);
                bus.b(-30.0f, 0.0f, 1.0f, 0.0f);
                bus.b(0.2, -0.8, -0.22);
                break;
            }
            case AXE_LEFT: {
                itemId = 258;
                bus.b(70.0f, 0.0f, 0.0f, 1.0f);
                bus.b(-30.0f, 0.0f, 1.0f, 0.0f);
                bus.b(-0.3, -0.7, -0.02);
                break;
            }
            case AXE_TOP: {
                itemId = 292;
                bus.b(-50.0f, 0.0f, 0.0f, 1.0f);
                bus.b(70.0f, 0.0f, 1.0f, 0.0f);
                bus.b(0.2, -0.6, 0.4);
                break;
            }
            case ARROW_UPPER_LEFT_BACK_CORNER: {
                itemId = 262;
                bus.b(-180.0f, 0.0f, 0.0f, 1.0f);
                bus.b(-100.0f, 0.0f, 1.0f, 0.0f);
                bus.b(30.0f, 1.0f, 0.0f, 0.0f);
                bus.b(0.1, 0.3, -0.5);
                break;
            }
            case ARROW_DIAGONALLY: {
                itemId = 262;
                bus.b(-50.0f, 0.0f, 0.0f, 1.0f);
                bus.b(0.1, -0.1, 0.0);
                bus.b(-0.19, -0.19, 0.0);
                item = new aip(ain.c(itemId));
                if (item != null && item.c() != null) {
                    bib.z().ae().a((vp)entityIn, item, bwc.b.a);
                }
                bus.b(0.38, 0.38, 0.0);
                break;
            }
        }
        if (itemId != 0) {
            if (item == null) {
                item = new aip(ain.c(itemId));
            }
            if (item != null && item.c() != null) {
                bib.z().ae().a((vp)entityIn, item, bwc.b.a);
            }
        }
        bus.H();
    }
    
    public int getCosmeticId() {
        return 15;
    }
    
    public String getCosmeticName() {
        return "Halloween";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticHalloweenData extends CosmeticData
    {
        private EnumHalloweenType enumHalloweenType;
        
        public CosmeticHalloweenData() {
            this.enumHalloweenType = EnumHalloweenType.AXE_RIGHT;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.enumHalloweenType = EnumHalloweenType.values()[Integer.parseInt(data[0])];
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public EnumHalloweenType getEnumHalloweenType() {
            return this.enumHalloweenType;
        }
        
        public enum EnumHalloweenType
        {
            AXE_RIGHT, 
            PICKAXE_TOP, 
            AXE_LEFT, 
            AXE_TOP, 
            ARROW_UPPER_LEFT_BACK_CORNER, 
            ARROW_DIAGONALLY;
        }
    }
}
