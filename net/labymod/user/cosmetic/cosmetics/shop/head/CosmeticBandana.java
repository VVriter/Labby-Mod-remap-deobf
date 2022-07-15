//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticBandana extends CosmeticRenderer<CosmeticBandanaData>
{
    public static final int ID = 22;
    private brs front;
    private brs right;
    private brs left;
    private brs back;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 20;
        final int height = 16;
        final int heightPerSide = 100;
        (this.front = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, heightPerSide * 0)).a(0.0f, 0.0f, 0.0f, 9, 3, 1, modelSize);
        (this.right = new ModelRendererHook((bqf)modelCosmetics, heightPerSide * 1, 0).b(width, height).a(0, heightPerSide * 1)).a(0.0f, 0.0f, 0.0f, 9, 3, 1, modelSize);
        (this.left = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, heightPerSide * 2)).a(0.0f, 0.0f, 0.0f, 9, 3, 1, modelSize);
        (this.back = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, heightPerSide * 3)).a(0.0f, 0.0f, 0.0f, 9, 3, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.front.j = invisible;
        this.right.j = invisible;
        this.left.j = invisible;
        this.back.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticBandanaData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf bandanaLocation = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getBandanaImageHandler().getResourceLocation((bua)entityIn);
        if (bandanaLocation == null) {
            return;
        }
        bib.z().N().a(bandanaLocation);
        bus.G();
        if (entityIn.aU()) {
            bus.c(0.0f, 1.0f * scale, 0.0f);
        }
        bus.b((float)Math.toDegrees(modelCosmetics.e.g), 0.0f, 1.0f, 0.0f);
        bus.b((float)Math.toDegrees(modelCosmetics.e.f), 1.0f, 0.0f, 0.0f);
        if (cosmeticData.isUnderSecondLayer()) {
            final double scaling = 0.88;
            bus.a(scaling, scaling, scaling);
        }
        bus.G();
        bus.b(-0.292, -0.51, -0.318);
        bus.a(1.039, 1.0, 0.6);
        this.front.a(scale);
        bus.H();
        for (int i = 0; i < 2; ++i) {
            bus.G();
            bus.b(10.0f, -1.0f, 0.0f, 0.0f);
            bus.b(90.0f, 0.0f, 1.0f, 0.0f);
            bus.b(0.086, 0.05, 0.0);
            bus.b(-0.267, -0.5, ((i == 0) ? 1 : -1) * -0.28 - 0.015);
            if (i == 1) {
                bus.b(0.0, 0.0, 0.03);
                bus.b(180.0f, 0.0f, 1.0f, 0.0f);
                bus.b(-0.485, 0.0, 0.0);
            }
            bus.a(1.078, 1.0, 0.5);
            bus.b(-0.057, 0.0, 0.0);
            final brs model = (i == 0) ? this.right : this.left;
            model.a(scale);
            bus.H();
        }
        bus.G();
        bus.b(-0.293, -0.404, 0.29);
        bus.b(5.0f, -1.0f, 0.0f, 0.0f);
        bus.a(1.039, 1.0, 0.5);
        bus.b(180.0f, 0.0f, 1.0f, 0.0f);
        bus.b(-0.563, 0.0, 0.0);
        bus.b(0.0, 0.0, -0.06);
        this.back.a(scale);
        bus.H();
        bus.G();
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double yawSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double yawCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
        final float motionSub = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        if (motionAdd < 0.0f) {
            motionAdd = 0.0f;
        }
        if (motionAdd >= 130.0f) {
            motionAdd = 130.0f + (motionAdd - 180.0f) * 0.2f;
        }
        final float f4 = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
        rotation += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * f4;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.b(0.0, -0.28, 0.32);
        final double scaling2 = 0.06;
        bus.a(scaling2, scaling2, scaling2);
        bus.b(-1.0f, 1.0f, 1.0f);
        bus.G();
        bus.b(-0.5, -0.5, 0.2);
        draw.drawTexture(0.0, 0.0, 0.0, 64.0, 12.8, 16.0, 1.0, 1.0, 1.1f);
        bus.H();
        for (int t = 0; t < 2; ++t) {
            bus.G();
            bus.b(t * -1.3 + 0.15, 0.0, 0.0);
            bus.b(6.0f + motionAdd / 2.0f + rotation, 1.0f, 0.0f, 0.0f);
            bus.b(-motionSub / 2.0f, 0.0f, 0.0f, 1.0f);
            bus.b(motionSub / 2.0f, 0.0f, 1.0f, 0.0f);
            for (int j = 0; j < 3; ++j) {
                final double animation = 1.0 + motionAdd / 160.0;
                bus.b((float)(j * 4 * ((j % 2 == ((t == 0) ? 2 : 1)) ? 1 : -1)), 0.0f, 0.0f, 1.0f);
                bus.b(0.0, 0.0, j / 50.0);
                bus.b(j / -4.0 * ((t == 0) ? -1 : 1) * animation, j / 4.0 * animation, 0.0);
                bus.b(motionAdd * ((t == 0) ? -1 : 1) / 20.0f, 0.0f, 0.0f, 1.0f);
                bus.b(rotation * ((t == 0) ? -1 : 1), 0.0f, 0.0f, 1.0f);
                if (t == 1) {
                    draw.drawTexture(0.0, 0.0, 243.2, 0.0, 12.8, 16.0, 1.0, 1.0, 1.1f);
                }
                else {
                    draw.drawTexture(0.0, 0.0, 0.0, 0.0, 12.8, 16.0, 1.0, 1.0, 1.1f);
                }
            }
            bus.H();
        }
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 22;
    }
    
    public String getCosmeticName() {
        return "Bandana";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticBandanaData extends CosmeticData
    {
        private boolean underSecondLayer;
        
        @Override
        public boolean isEnabled() {
            return LabyMod.getSettings().cosmeticsCustomTextures;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.underSecondLayer = (Integer.parseInt(data[0]) == 1);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public boolean isUnderSecondLayer() {
            return this.underSecondLayer;
        }
    }
}
