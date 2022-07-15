//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWolfTail extends CosmeticRenderer<CosmeticWolfData>
{
    public static final int ID = 1;
    private brs wolfTail;
    private brs wolfTailPlayerSkin;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        for (int i = 0; i <= 1; ++i) {
            final boolean playerSkin = i != 0;
            final brs target = new ModelRendererHook((bqf)modelCosmetics);
            if (playerSkin) {
                target.a(56, 30);
            }
            else {
                target.b(8, 10);
                target.a(0, 0);
            }
            target.a(-1.0f, 0.0f, -1.0f, 2, 8, 2, modelSize);
            target.a(-0.2f, 10.0f, 3.0f);
            if (i == 0) {
                this.wolfTail = target;
            }
            else {
                this.wolfTailPlayerSkin = target;
            }
        }
    }
    
    public void setInvisible(final boolean invisible) {
        this.wolfTail.j = invisible;
        this.wolfTailPlayerSkin.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWolfData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        final brs targetModel = this.bindTextureAndColor(cosmeticData.getColor(), cosmeticData.isUseSkinTexture() ? null : ModTextures.COSMETIC_TAIL_WOLF, cosmeticData.isUseSkinTexture() ? this.wolfTailPlayerSkin : this.wolfTail);
        if (entityIn.aU()) {
            bus.c(0.0f, 0.2f, -0.25f);
            bus.b(45.0f, 45.0f, 0.0f, 0.0f);
        }
        else {
            bus.c(0.0f, 0.1f, -0.25f);
            bus.b(15.0f, 15.0f, 0.0f, 0.0f);
        }
        if (cosmeticData.isUseSkinTexture()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.d(1.0f, 1.0f, 1.0f);
        }
        float health = ((bua)entityIn).cd();
        if (health > 20.0f || Float.isNaN(health)) {
            health = 20.0f;
        }
        if (health < 0.0f) {
            health = 0.0f;
        }
        bus.c(0.0f, health / 80.0f, health / 50.0f * -1.0f);
        bus.b(health * 2.0f, health * 2.0f, 0.0f, 0.0f);
        targetModel.b(scale);
        bus.H();
    }
    
    public void setRotationAngles(final ModelCosmetics modelCosmetics, final float movementFactor, final float walkingSpeed, final float tickValue, final float var4, final float var5, final float var6, final vg entityIn) {
        this.wolfTail.g = LabyModCore.getMath().cos(movementFactor * 0.6662f) * 1.4f * walkingSpeed;
        this.wolfTail.f = walkingSpeed;
        this.wolfTailPlayerSkin.g = LabyModCore.getMath().cos(movementFactor * 0.6662f) * 1.4f * walkingSpeed;
        this.wolfTailPlayerSkin.f = walkingSpeed;
    }
    
    public int getCosmeticId() {
        return 1;
    }
    
    public String getCosmeticName() {
        return "Wolf tail";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticWolfData extends CosmeticData
    {
        private Color color;
        private boolean useSkinTexture;
        
        public CosmeticWolfData() {
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
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isUseSkinTexture() {
            return this.useSkinTexture;
        }
    }
}
