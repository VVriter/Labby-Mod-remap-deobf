//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic;

import java.awt.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;

public abstract class CosmeticRenderer<CosmeticData>
{
    public abstract int getCosmeticId();
    
    public abstract String getCosmeticName();
    
    public abstract boolean isOfflineAvailable();
    
    public abstract void addModels(final ModelCosmetics p0, final float p1);
    
    public abstract void setInvisible(final boolean p0);
    
    @Deprecated
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks, final boolean firstPersonContext) {
        this.render(modelCosmetics, entityIn, cosmeticData, scale, movementFactor, walkingSpeed, tickValue, firstRotationX, secondRotationX, partialTicks);
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public void setRotationAngles(final ModelCosmetics modelCosmetics, final float movementFactor, final float walkingSpeed, final float tickValue, final float var4, final float var5, final float var6, final vg entityIn) {
    }
    
    public void onTick() {
    }
    
    public void onRenderWorld() {
    }
    
    protected brs bindTextureAndColor(final Color color, final nf resourceLocation, final brs model) {
        return this.bindTextureAndColor((color == null) ? 0 : color.getRGB(), resourceLocation, model);
    }
    
    protected brs bindTextureAndColor(final int color, final nf resourceLocation, final brs model) {
        final int red = color >> 16 & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color >> 0 & 0xFF;
        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 1.0f);
        if (resourceLocation != null) {
            bib.z().N().a(resourceLocation);
        }
        return model;
    }
    
    protected void rotateToPlayersCamera(final vg entityIn, final boolean rotateX, final boolean rotateY) {
        final bzf renderManager = bib.z().ac();
        if (entityIn instanceof bua) {
            final bua entity = (bua)entityIn;
            final float entityYaw = entity.aO + (entity.aN - entity.aO) * LabyMod.getInstance().getPartialTicks();
            bus.b(-entityYaw, 0.0f, 1.0f, 0.0f);
            if (rotateY) {
                bus.b(renderManager.e, 0.0f, 1.0f, 0.0f);
            }
            if (rotateX) {
                final float fixedPlayerViewX = renderManager.f * ((bib.z().t.aw == 2) ? -1 : 1);
                bus.b(fixedPlayerViewX, 1.0f, 0.0f, 0.0f);
            }
            bus.b(180.0f, 0.0f, 1.0f, 0.0f);
        }
    }
    
    public boolean isVisibleInFirstPerson(final CosmeticData data, final boolean rightHand) {
        return false;
    }
    
    public boolean hasLeftHandSupport() {
        return true;
    }
}
