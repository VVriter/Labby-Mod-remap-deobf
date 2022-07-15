//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.core.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticRabbit extends CosmeticRenderer<CosmeticRabbitData>
{
    public static final int ID = 11;
    private brs rabbitEar;
    private brs rabbitEarPlayerSkin;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        for (int i = 0; i <= 1; ++i) {
            final boolean playerSkin = i != 0;
            final brs target = new ModelRendererHook((bqf)modelCosmetics);
            if (playerSkin) {
                target.a(24, 0);
            }
            else {
                target.b(6, 7);
            }
            target.a(0.0f, 0.0f, 0.0f, 2, 6, 1, modelSize);
            target.d = -6.0f;
            if (i == 0) {
                this.rabbitEar = target;
            }
            else {
                this.rabbitEarPlayerSkin = target;
            }
        }
    }
    
    public void setInvisible(final boolean invisible) {
        this.rabbitEar.j = invisible;
        this.rabbitEarPlayerSkin.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticRabbitData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        final double rescale = 1.15;
        bus.b(0.0, -0.44, -0.1);
        bus.a(rescale, rescale, rescale);
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
        float motionSub = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        if (motionAdd < 0.0f) {
            motionAdd = 0.0f;
        }
        if (motionAdd >= 130.0f) {
            motionAdd = 130.0f + (motionAdd - 180.0f) * 0.2f;
        }
        final float f4 = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
        rotation += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * f4;
        final brs targetModel = this.bindTextureAndColor(cosmeticData.getColor(), cosmeticData.isUseSkinTexture() ? null : ModTextures.COSMETIC_RABBIT, cosmeticData.isUseSkinTexture() ? this.rabbitEarPlayerSkin : this.rabbitEar);
        final double distanceToMid = 0.09;
        final boolean hat = cosmeticData.isHat();
        if (hat) {
            motionSub /= 5.0f;
        }
        bus.q();
        for (int i = -1; i < 2; i += 2) {
            bus.G();
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            bus.b(-distanceToMid * 2.0, 0.0, 0.0);
            bus.b(6.0f - motionAdd / 3.0f, 1.0f, 0.0f, 0.0f);
            bus.b(-motionSub / 4.0f * -i + rotation / 8.0f, 0.0f, 0.0f, 1.0f);
            if (entityIn.aU()) {
                bus.b(Math.abs(entityIn.w) / -5.0f, 0.0f, 1.0f, 1.0f);
            }
            if (hat) {
                bus.b(-30.0f, 0.0f, 0.0f, 1.0f);
            }
            targetModel.a(scale);
            bus.H();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 11;
    }
    
    public String getCosmeticName() {
        return "Rabbit Ears";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.2f;
    }
    
    public static class CosmeticRabbitData extends CosmeticData
    {
        private Color color;
        private boolean hat;
        private boolean useSkinTexture;
        
        public CosmeticRabbitData() {
            this.color = Color.WHITE;
            this.useSkinTexture = false;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
            this.useSkinTexture = (Integer.parseInt(data[1]) == 1);
        }
        
        @Override
        public void completed(final User user) {
            final Map<Integer, CosmeticData> cosmetics = user.getCosmetics();
            this.hat = (cosmetics.containsKey(16) || cosmetics.containsKey(7));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isHat() {
            return this.hat;
        }
        
        public boolean isUseSkinTexture() {
            return this.useSkinTexture;
        }
    }
}
