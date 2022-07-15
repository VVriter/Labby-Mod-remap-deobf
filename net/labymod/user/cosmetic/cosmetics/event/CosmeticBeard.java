//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import java.awt.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticBeard extends CosmeticRenderer<CosmeticBeardData>
{
    public static final int ID = 5;
    private brs beard;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int beardWidth = 7;
        final int partLength = 3;
        (this.beard = new ModelRendererHook((bqf)modelCosmetics).b(40, 34)).a(0.0f, -2.0f, -5.0f);
        this.beard.a(0, 0).a(-beardWidth / 2.0f, 0.0f, 0.0f, beardWidth, 1, 1);
        final brs rightBeardCorner = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        rightBeardCorner.a(beardWidth / -2.0f + 2.0f, 1.0f, 0.0f);
        rightBeardCorner.a(0, 0).a(0.0f, 0.0f, 0.0f, 1, 3, 1);
        rightBeardCorner.h = 1.5707964f;
        this.beard.a(rightBeardCorner);
        final brs leftBeardCorner = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        leftBeardCorner.a(beardWidth / 2.0f - 2.0f, 1.0f, 0.0f);
        leftBeardCorner.a(0, 0).a(0.0f, 0.0f, 0.0f, 3, 1, 1);
        this.beard.a(leftBeardCorner);
        final brs beardFront = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        beardFront.a(0.0f, 1.3f, -0.3f);
        beardFront.a(0, 0).a(beardWidth / -2.0f, 0.0f, 0.0f, beardWidth, 2, 2);
        this.beard.a(beardFront);
        final brs firstPart = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        firstPart.a(0.0f, 2.0f, 0.0f);
        firstPart.a(0, 0).a((beardWidth - 2) / -2.0f, 0.0f, 0.0f, beardWidth - 2, partLength, 2);
        beardFront.a(firstPart);
        final brs secondPart = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        secondPart.a(0.0f, (float)partLength, 0.0f);
        secondPart.a(0, 0).a((beardWidth - 3) / -2.0f, 0.0f, 0.0f, beardWidth - 3, partLength, 2);
        firstPart.a(secondPart);
        final brs thirdPart = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        thirdPart.a(0.0f, (float)partLength, 0.0f);
        thirdPart.a(0, 0).a((beardWidth - 4) / -2.0f, 0.0f, 0.0f, beardWidth - 4, partLength, 2);
        secondPart.a(thirdPart);
    }
    
    public void setInvisible(final boolean invisible) {
        this.beard.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticBeardData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final bua clientPlayer = (bua)entityIn;
        final brs beard = this.bindTextureAndColor(Color.WHITE, ModTextures.COSMETIC_XMAS, this.beard);
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.b(0.95f, 0.95f, 0.95f);
        final brs beardFront = beard.m.get(2);
        final brs firstPart = beardFront.m.get(0);
        final brs secondPart = firstPart.m.get(0);
        final brs thirdPart = secondPart.m.get(0);
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
        float pitch = clientPlayer.y + (clientPlayer.w - clientPlayer.y) * partialTicks;
        final float bendLimit = 30.0f;
        if (pitch < -bendLimit) {
            pitch = -bendLimit;
        }
        beardFront.f = pitch / -50.0f;
        final float strength = 200.0f;
        final float bend = (pitch < 0.0f) ? (pitch / -50.0f) : 0.0f;
        firstPart.h = motionSub / strength;
        secondPart.h = motionSub / strength;
        thirdPart.h = motionSub / strength;
        firstPart.f = motionAdd / strength;
        secondPart.f = motionAdd / strength;
        thirdPart.f = motionAdd / strength;
        firstPart.e = pitch / 400.0f - bend;
        secondPart.e = pitch / 400.0f;
        thirdPart.e = pitch / 400.0f + bend;
        firstPart.k = false;
        secondPart.k = false;
        thirdPart.k = false;
        switch (cosmeticData.getLength()) {
            case 0: {
                firstPart.k = true;
                break;
            }
            case 1: {
                secondPart.k = true;
                break;
            }
            case 2: {
                thirdPart.k = true;
                break;
            }
        }
        beard.a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 5;
    }
    
    public String getCosmeticName() {
        return "Beard";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public static class CosmeticBeardData extends CosmeticData
    {
        private int length;
        
        public CosmeticBeardData() {
            this.length = 3;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.length = Integer.parseInt(data[0]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public int getLength() {
            return this.length;
        }
    }
}
