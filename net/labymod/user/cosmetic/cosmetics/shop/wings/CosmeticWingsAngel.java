//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.wings;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWingsAngel extends CosmeticRenderer<CosmeticAngelData>
{
    public static final int ID = 24;
    private brs model;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 30;
        final int height = 23;
        (this.model = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(0.0f, 0.0f, 0.0f, 9, 3, 1, modelSize);
        final brs featherLayer1 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 4);
        featherLayer1.a(0.0f, 1.0f, 0.0f);
        featherLayer1.a(-1.0f, 0.0f, -0.5f, 14, 4, 1);
        featherLayer1.h = -0.06f;
        this.model.a(featherLayer1);
        final brs test = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(18, 10);
        test.a(2.0f, -1.0f, 0.0f, 2, 1, 1, modelSize);
        this.model.a(test);
        final brs featherLayer2 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 9);
        featherLayer2.a(0.0f, 0.0f, 0.0f);
        featherLayer2.a(2.0f, 3.0f, -0.8f, 8, 4, 1);
        featherLayer2.h = -0.2f;
        featherLayer2.f = 0.2f;
        featherLayer1.a(featherLayer2);
        final brs featherLayer3 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 14);
        featherLayer3.a(0.0f, 0.0f, 0.0f);
        featherLayer3.a(0.0f, 1.0f, 0.0f, 14, 7, 1);
        featherLayer3.h = 0.5f;
        featherLayer2.a(featherLayer3);
    }
    
    public void setInvisible(final boolean invisible) {
        this.model.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticAngelData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf textureDesign = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getAngelWingsImageHandler().getResourceLocation((bua)entityIn);
        if (textureDesign == null) {
            return;
        }
        bus.G();
        bus.e();
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double motionSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double motionCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * motionSin + motionZ * motionCos) * 100.0f;
        final float motionSub = (float)(motionX * motionCos - motionZ * motionSin) * 100.0f;
        if (motionAdd < 0.0f) {
            motionAdd = 0.0f;
        }
        if (motionAdd >= 60.0f) {
            motionAdd = 60.0f + (motionAdd - 60.0f) * 0.2f;
        }
        final float f4 = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
        rotation += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * f4;
        if (entityIn.aU()) {
            bus.b(30.0f, 1.0f, 0.0f, 0.0f);
        }
        bus.c(0.0f, 0.05f, 0.0f);
        final bzf manager = bib.z().ac();
        if (manager != null) {
            bus.b(manager.f / 3.0f, 1.0f, 0.0f, 0.0f);
        }
        cosmeticData.updateFadeAnimation(entityIn.z);
        bus.r();
        bib.z().N().a(textureDesign);
        for (int i = -1; i <= 1; i += 2) {
            bus.G();
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
            }
            bus.b(0.1, 0.0, 0.13);
            final float walkShakeFloat = rotation / -260.0f;
            final float idleStrength = entityIn.aU() ? 100.0f : 30.0f;
            final float idleAnimationFloatCos = (float)(Math.cos(tickValue / 26.0f) / idleStrength);
            final float idleAnimationFloatSin = (float)(Math.sin(tickValue / 15.0f) / idleStrength);
            float idleAnimationFloatWithWalking = idleAnimationFloatCos - walkingSpeed / 5.0f;
            final float scretch = (cosmeticData.getFadeAnimation() + 1.0f) / 15.0f;
            final float onGroundStrength = cosmeticData.getOnGroundStrength();
            final float inAirStrength = cosmeticData.getAirStrength();
            idleAnimationFloatWithWalking *= onGroundStrength;
            idleAnimationFloatWithWalking -= inAirStrength * 0.1f;
            this.model.h = walkShakeFloat + idleAnimationFloatWithWalking * 2.0f - 0.3f;
            final brs feather1 = this.model.m.get(0);
            feather1.h = walkShakeFloat + idleAnimationFloatWithWalking / 3.0f + idleAnimationFloatCos - 0.1f + walkingSpeed / 10.0f + scretch / 2.0f;
            final brs feather2 = feather1.m.get(0);
            feather2.h = walkShakeFloat + idleAnimationFloatWithWalking / 3.0f + idleAnimationFloatCos - 0.2f + scretch;
            final brs feather3 = feather2.m.get(0);
            feather3.h = walkShakeFloat + idleAnimationFloatCos + 0.5f;
            if (entityIn.aU()) {
                bus.b(30.0f, 0.0f, 0.0f, 1.0f);
            }
            else {
                bus.b(6.0f + motionAdd / 2.0f * onGroundStrength + 20.0f * inAirStrength, 0.0f, -1.0f, 0.0f);
                float idleValue = (idleAnimationFloatSin * 500.0f - 10.0f) * (1.0f - walkingSpeed);
                idleValue *= onGroundStrength;
                bus.b(idleValue, 0.0f, 1.0f, 0.0f);
                float flyingValue = (float)Math.cos(tickValue / 2.8f) * 30.0f;
                flyingValue *= inAirStrength;
                bus.b(motionAdd / 3.0f * inAirStrength, 1.0f, 1.0f, 0.0f);
                bus.b(flyingValue / 5.0f, 1.0f, 0.0f, 0.0f);
                bus.b(flyingValue / 2.0f + 15.0f * inAirStrength, 0.0f, -1.0f, 0.0f);
            }
            bus.b(motionSub / 2.0f * ((i == 1) ? 1 : -1) * (onGroundStrength * 0.4f + 0.3f), 0.0f, 1.0f, 0.0f);
            bus.g();
            this.model.a(scale);
            bus.f();
            bus.H();
        }
        bus.q();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 24;
    }
    
    public String getCosmeticName() {
        return "AngelWings";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticAngelData extends AnimatedCosmeticData
    {
        private UserTextureContainer userTextureContainer;
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getAngelWingsContainer();
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.userTextureContainer.setFileName(UUID.fromString(data[0]));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.WINGS;
        }
    }
}
