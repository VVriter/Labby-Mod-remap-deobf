//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticRednose extends CosmeticRenderer<CosmeticRedNoseData>
{
    public static final int ID = 6;
    private brs nose;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.nose = new ModelRendererHook((bqf)modelCosmetics).b(8, 4)).a(-1.0f, -4.0f, -5.3f);
        this.nose.a(0, 0).a(0.0f, 0.0f, 0.0f, 2, 2, 2);
    }
    
    public void setInvisible(final boolean invisible) {
        this.nose.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticRedNoseData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        final brs targetModel = this.bindTextureAndColor((Color)null, ModTextures.COSMETIC_REDNOSE, this.nose);
        bus.c(0.0f, cosmeticData.getOffset() / 100.0f, 0.0f);
        targetModel.a(scale);
        bus.b(1.02f, 1.02f, 1.02f);
        bus.c(0.0f, 0.004f, 0.0f);
        bus.e();
        bus.m();
        bus.g();
        bus.a(770, 772, 1, 1);
        bus.j(7425);
        final float shine = (float)Math.cos(tickValue / 10.0f) / 3.0f + 0.6f;
        bus.c(1.0f, 0.0f, 0.0f, shine);
        targetModel.a(scale);
        bus.f();
        bus.l();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 6;
    }
    
    public String getCosmeticName() {
        return "Red nose";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public static class CosmeticRedNoseData extends CosmeticData
    {
        private int offset;
        
        public CosmeticRedNoseData() {
            this.offset = 0;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.offset = Integer.parseInt(data[0]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public int getOffset() {
            return this.offset;
        }
    }
}
