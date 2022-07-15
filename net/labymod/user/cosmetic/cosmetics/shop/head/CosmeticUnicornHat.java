//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticUnicornHat extends CosmeticRenderer<CosmeticUnicornHatData>
{
    public static final int ID = 39;
    private brs head;
    private brs horn;
    private brs mane;
    private brs nose;
    private brs eye;
    private brs flaps;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 40;
        final int height = 23;
        (this.head = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-4.5f, -0.25f, -4.5f, 9, 1, 9);
        this.head.a(-5.0f, -1.0f, -5.0f, 10, 1, 10);
        this.head.a(-5.0f, -2.0f, -4.0f, 10, 1, 9);
        this.head.a(-4.0f, -2.0f, -5.0f, 8, 1, 1);
        this.head.a(-5.0f, -2.5f, -1.5f, 10, 1, 5);
        this.head.a(-4.0f, -4.0f, -3.2f, 8, 2, 8);
        this.head.a(-4.0f, -4.5f, -1.2f, 1, 1, 6);
        this.head.a(3.0f, -4.5f, -1.2f, 1, 1, 6);
        this.head.a(-4.0f, -5.5f, 2.8f, 1, 1, 2);
        this.head.a(3.0f, -5.5f, 2.8f, 1, 1, 2);
        (this.horn = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 11)).a(-2.0f, -2.0f, -2.0f);
        this.horn.a(1.5f, -7.0f, 0.0f, 1, 5, 1);
        (this.mane = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(4, 11)).a(-3.0f, -5.5f, -4.2f, 6, 2, 2);
        final brs maneTop = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(13, 11);
        maneTop.a(-3.0f, -5.0f, -2.2f, 6, 1, 7);
        this.mane.a(maneTop);
        final brs maneBack = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(17, 19);
        maneBack.a(-3.0f, -0.5f, 0.0f, 6, 1, 3);
        maneBack.a(0.001f, -5.0f, 4.8f);
        maneTop.a(maneBack);
        final brs maneBackBottom = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(17, 19);
        maneBackBottom.a(-3.0f, -1.0f, 0.0f, 6, 1, 3);
        maneBackBottom.a(0.001f, 0.0f, 1.5f);
        maneBack.a(maneBackBottom);
        (this.nose = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 15)).a(-2.0f, -2.0f, -2.0f);
        this.nose.a(0.5f, -1.0f, -5.0f, 3, 3, 5);
        (this.eye = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(32, 11)).a(-2.3f, -4.5f, -3.5f);
        this.eye.a(0.0f, 0.0f, 0.0f, 1, 2, 2);
        (this.flaps = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(30, 0)).a(-4.5f, -0.5f, 1.6f);
        this.flaps.a(-0.1f, -0.5f, -2.0f, 1, 4, 4);
        this.flaps.h = 0.2f;
    }
    
    public void setInvisible(final boolean invisible) {
        this.head.j = invisible;
        this.nose.j = invisible;
        this.eye.j = invisible;
        this.horn.j = invisible;
        this.mane.j = invisible;
        this.flaps.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticUnicornHatData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bib.z().N().a(ModTextures.COSMETIC_UNICORN_HAT);
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(0.0, -0.48125, 0.0);
        bus.b(2.0f, 1.0f, 0.0f, 0.0f);
        bus.a(1.03, 1.03, 1.03);
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
        final float rotationYaw = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
        rotation += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * rotationYaw;
        final float flapDefault = 0.2f;
        final float flapFallAnimation = Math.max((float)motionY, -flapDefault) / 7.0f;
        this.nose.f = -(float)motionY / 25.0f;
        this.horn.f = 0.2f - motionAdd / 500.0f;
        this.horn.h = -motionSub / 500.0f;
        this.horn.p = -flapFallAnimation / 10.0f;
        this.head.a(scale);
        this.nose.a(scale);
        this.horn.a(scale);
        final brs maneTop = this.mane.m.get(0);
        final brs maneBack = maneTop.m.get(0);
        final brs maneBottom = maneBack.m.get(0);
        bus.q();
        for (int i = 0; i < 2; ++i) {
            bus.G();
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            this.flaps.h = flapFallAnimation + Math.max(motionSub / 80.0f * ((i == 1) ? -1 : 1), -flapDefault) + flapDefault;
            this.flaps.f = motionAdd / 300.0f;
            this.eye.h = (float)motionY / 20.0f;
            this.eye.g = motionSub / 600.0f * ((i == 1) ? -1 : 1) - 1.5707964f;
            this.mane.g = motionSub / 800.0f;
            this.mane.f = -motionAdd / 1300.0f;
            maneTop.g = -this.mane.g;
            maneBack.h = motionSub / 600.0f;
            maneBack.f = -1.5707964f;
            maneBottom.g = -motionSub / 400.0f;
            this.eye.a(scale);
            this.flaps.a(scale);
            bus.H();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.G();
        bus.b(1.0f, 1.3f, 1.0f);
        bus.c(0.0f, 0.0625f, 0.0f);
        this.mane.a(scale);
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 39;
    }
    
    public String getCosmeticName() {
        return "Unicorn Hat";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.5f;
    }
    
    public static class CosmeticUnicornHatData extends CosmeticData
    {
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
    }
}
