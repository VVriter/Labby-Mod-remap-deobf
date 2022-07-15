//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticFlower extends CosmeticRenderer<CosmeticFlowerData>
{
    public static final int ID = 25;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
    }
    
    public void setInvisible(final boolean invisible) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticFlowerData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        if (cosmeticData.getItemStack() != null) {
            bus.G();
            bus.d(1.0f, 1.0f, 1.0f);
            if (entityIn.aU()) {
                bus.c(0.0f, 0.07f, 0.0f);
            }
            bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
            bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
            bus.b(180.0f, 1.0f, 0.0f, 0.0f);
            bus.b(90.0f, 0.0f, 1.0f, 0.0f);
            bus.a(0.4, 0.4, 0.4);
            bus.b(0.2, 0.9, 0.72);
            bus.b(70.0f, 0.0f, 0.0f, -1.0f);
            bus.b(20.0f, 1.0f, 1.0f, 1.0f);
            for (int i = 0; i <= 1; ++i) {
                bib.z().ae().a((vp)entityIn, cosmeticData.getItemStack(), bwc.b.i);
                bus.b(90.0f, 0.0f, 1.0f, 0.0f);
            }
            bus.H();
        }
    }
    
    public int getCosmeticId() {
        return 25;
    }
    
    public String getCosmeticName() {
        return "Flower";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticFlowerData extends CosmeticData
    {
        private aip itemStack;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            final int itemId = Integer.valueOf(data[0]);
            this.itemStack = new aip(ain.c(38), 1, itemId);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public aip getItemStack() {
            return this.itemStack;
        }
    }
}
