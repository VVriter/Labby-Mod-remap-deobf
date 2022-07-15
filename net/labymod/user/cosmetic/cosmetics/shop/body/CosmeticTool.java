//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticTool extends CosmeticRenderer<CosmeticToolData>
{
    public static final int ID = 8;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
    }
    
    public void setInvisible(final boolean invisible) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticToolData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        if (cosmeticData.getItemStack() != null) {
            bus.G();
            bus.e();
            if (entityIn.aU()) {
                bus.b(30.0f, 1.0f, 0.0f, 0.0f);
            }
            bus.a(0.8, 0.8, 0.8);
            bus.b(180.0f, 0.0f, 1.0f, 0.0f);
            bus.b(0.0, 0.3, -0.22);
            bib.z().ae().a((vp)entityIn, cosmeticData.getItemStack(), bwc.b.i);
            bus.H();
        }
    }
    
    public int getCosmeticId() {
        return 8;
    }
    
    public String getCosmeticName() {
        return "Tool";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticToolData extends CosmeticData
    {
        private aip itemStack;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            final int mode = Integer.valueOf(data[0]);
            final int itemId = Integer.valueOf(data[1]);
            switch (mode) {
                case 0: {
                    this.itemStack = new aip(ain.c(itemId));
                    break;
                }
            }
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public aip getItemStack() {
            return this.itemStack;
        }
    }
}
