//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMooseHat extends CosmeticRenderer<CosmeticMooseHatData>
{
    public static final int ID = 37;
    private brs moose;
    private brs nose;
    private brs eye;
    private brs antler;
    private brs flaps;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 40;
        final int height = 19;
        (this.moose = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-4.5f, -0.25f, -4.5f, 9, 1, 9);
        this.moose.a(-5.0f, -1.0f, -5.0f, 10, 1, 10);
        this.moose.a(-4.0f, -2.0f, -5.0f, 8, 1, 10);
        this.moose.a(-5.0f, -2.0f, -4.0f, 1, 1, 8);
        this.moose.a(4.0f, -2.0f, -4.0f, 1, 1, 8);
        this.moose.a(-3.5f, -4.0f, -3.5f, 7, 2, 7);
        (this.nose = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 11)).a(-2.0f, -2.0f, -2.0f);
        this.nose.a(0.0f, -1.5f, -7.5f, 4, 4, 4);
        this.nose.f = -0.2f;
        (this.eye = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-1.0f, -1.0f, -0.5f);
        this.eye.a(2.7f, -4.0f, -3.5f, 2, 2, 1);
        (this.antler = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 11)).a(-0.0f, -1.0f, -1.0f);
        this.antler.a(6.0f, -0.8f, 0.0f, 6, 2, 2);
        this.antler.a(10.0f, -5.8f, 0.0f, 2, 5, 2);
        this.antler.a(7.0f, -3.8f, 0.0f, 2, 3, 2);
        this.antler.h = -0.05f;
        (this.flaps = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(30, 0)).a(-4.5f, 0.5f, -0.0f);
        this.flaps.a(-0.1f, -0.5f, -2.0f, 1, 4, 4);
        this.flaps.h = 0.2f;
    }
    
    public void setInvisible(final boolean invisible) {
        this.moose.j = invisible;
        this.nose.j = invisible;
        this.eye.j = invisible;
        this.antler.j = invisible;
        this.flaps.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMooseHatData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bib.z().N().a(ModTextures.COSMETIC_MOOSE_HAT);
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
        this.nose.f = -0.2f - (float)motionY / 25.0f;
        bus.q();
        this.moose.a(scale);
        bus.G();
        bus.b(0.8f, 0.8f, 0.8f);
        this.nose.a(scale);
        bus.H();
        for (int i = 0; i < 2; ++i) {
            bus.G();
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            this.flaps.h = flapFallAnimation + Math.max(motionSub / 80.0f * ((i == 1) ? -1 : 1), -flapDefault) + flapDefault;
            this.flaps.f = motionAdd / 300.0f;
            this.eye.h = (float)motionY / -20.0f;
            this.eye.g = motionSub / 800.0f * ((i == 1) ? -1 : 1);
            this.antler.h = -0.05f + (float)motionY / -20.0f;
            this.antler.g = motionSub / 600.0f * ((i == 1) ? -1 : 1);
            this.eye.a(scale);
            this.flaps.a(scale);
            bus.G();
            bus.b(0.8f, 0.8f, 0.8f);
            this.antler.a(scale);
            bus.H();
            bus.H();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 37;
    }
    
    public String getCosmeticName() {
        return "Moose Hat";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.5f;
    }
    
    public static class CosmeticMooseHatData extends CosmeticData
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
