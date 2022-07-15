//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.partner;

import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.cosmetics.shop.head.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.user.*;
import java.awt.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticSnoxh extends CosmeticRenderer<CosmeticSnoxhData>
{
    public static final int ID = 32;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
    }
    
    public void setInvisible(final boolean invisible) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticSnoxhData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final int minColor = Math.min(cosmeticData.getBrightness(), 0);
        final int maxColor = cosmeticData.getBrightness();
        float animation = (minColor + (float)Math.abs(Math.cos(tickValue / 30.0f) * (maxColor - minColor))) / 255.0f;
        final int brightness = 2;
        final User user = LabyMod.getInstance().getUserManager().getUser(entityIn.bm());
        if (user.getCosmetics().containsKey(36)) {
            final CosmeticEyelids.CosmeticEyelidsData data = user.getCosmetics().get(36);
            animation *= (float)(1.0 - data.lastRenderedPercetage);
        }
        bus.G();
        bus.r();
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        if (entityIn.aU()) {
            double sneakRotate = entityIn.w / 70000.0;
            sneakRotate = Math.max(0.0, sneakRotate);
            bus.b(0.0, 0.062, 0.0);
            bus.b(0.0, sneakRotate, -sneakRotate);
        }
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.a(0.0625, 0.0625, 0.06);
        bus.b(-4.0, -8.0, -4.17);
        bib.z().N().a(ModTextures.COSMETIC_SNOXH_EYE);
        bus.m();
        bus.a(770, 772, 1, 1);
        bus.d();
        bus.j(7425);
        bus.z();
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        final int x = cosmeticData.getX();
        final int y = cosmeticData.getY();
        final int width = cosmeticData.getWidth();
        final int height = cosmeticData.getHeight();
        bib.z().o.h();
        bus.g();
        final boolean overlappingLeft = x + width - 1 == 3;
        for (int i = 0; i < 2; ++i) {
            for (int b = 0; b < brightness; ++b) {
                if (i == 0) {
                    if (!cosmeticData.isRightVisible()) {
                        continue;
                    }
                }
                else if (!cosmeticData.isLeftVisible()) {
                    continue;
                }
                this.renderGlowingBorder((i == 0) ? x : (8 - x - width), y, width, height, cosmeticData.getColor(), animation, overlappingLeft, i == 0);
            }
            bus.b(0.0, 0.0, -0.01);
        }
        bib.z().o.i();
        bus.y();
        bus.j(7424);
        bus.e();
        bus.l();
        bus.f();
        bus.q();
        bus.H();
    }
    
    private void renderGlowingBorder(final int x, final int y, final int width, final int height, final Color color, final float alpha, final boolean overlapping, final boolean isLeftSide) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        final float middleAlpha = 0.4f;
        final float radius = 0.6f;
        final float basis = 0.0f;
        final float middleLeft = x + basis;
        final float middleTop = y + basis;
        final float middleRight = x + width - basis;
        final float middleBottom = y + height - basis;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        worldrenderer.begin(7, cdy.i);
        this.drawGradient(middleLeft, middleTop - radius, 0.0f, middleRight, middleTop - radius, 0.0f, middleRight, middleTop, alpha, middleLeft, middleTop, alpha, r, g, b);
        this.drawGradient(middleRight, middleTop, alpha, middleRight + radius, middleTop, 0.0f, middleRight + radius, middleBottom, 0.0f, middleRight, middleBottom, alpha, r, g, b);
        this.drawGradient(middleLeft, middleTop, alpha, middleLeft - radius, middleTop, 0.0f, middleLeft - radius, middleBottom, 0.0f, middleLeft, middleBottom, alpha, r, g, b);
        this.drawGradient(middleLeft, middleBottom + radius, 0.0f, middleRight, middleBottom + radius, 0.0f, middleRight, middleBottom, alpha, middleLeft, middleBottom, alpha, r, g, b);
        this.drawGradient(middleLeft, middleTop, middleAlpha, middleRight, middleTop, middleAlpha, middleRight, middleBottom, middleAlpha, middleLeft, middleBottom, middleAlpha, r, g, b);
        final double cornerRadiusWidth = radius * Math.cos(Math.toRadians(45.0));
        final double cornerRadiusHeight = radius * Math.sin(Math.toRadians(45.0));
        final double lowCornerRadiusWidth = radius * Math.cos(Math.toRadians(22.5));
        final double lowCornerRadiusHeight = radius * Math.sin(Math.toRadians(22.5));
        final double highCornerRadiusWidth = radius * Math.cos(Math.toRadians(67.5));
        final double highCornerRadiusHeight = radius * Math.sin(Math.toRadians(67.5));
        Label_0548: {
            if (overlapping) {
                if (isLeftSide) {
                    if (!overlapping) {
                        break Label_0548;
                    }
                }
                else if (overlapping) {
                    break Label_0548;
                }
            }
            this.drawGradient(middleLeft - radius, middleTop, 0.0f, middleLeft - lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft, middleTop, alpha, r, g, b);
            this.drawGradient(middleLeft - cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleLeft, middleTop - radius, 0.0f, middleLeft, middleTop, alpha, r, g, b);
        }
        Label_0679: {
            if (overlapping) {
                if (isLeftSide) {
                    if (overlapping) {
                        break Label_0679;
                    }
                }
                else if (!overlapping) {
                    break Label_0679;
                }
            }
            this.drawGradient(middleRight + radius, middleTop, 0.0f, middleRight + lowCornerRadiusWidth, middleTop - lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight, middleTop, alpha, r, g, b);
            this.drawGradient(middleRight + cornerRadiusWidth, middleTop - cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleTop - highCornerRadiusHeight, 0.0f, middleRight, middleTop - radius, 0.0f, middleRight, middleTop, alpha, r, g, b);
        }
        Label_0810: {
            if (overlapping) {
                if (isLeftSide) {
                    if (!overlapping) {
                        break Label_0810;
                    }
                }
                else if (overlapping) {
                    break Label_0810;
                }
            }
            this.drawGradient(middleLeft - radius, middleBottom, 0.0f, middleLeft - lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft, middleBottom, alpha, r, g, b);
            this.drawGradient(middleLeft - cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleLeft - highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleLeft, middleBottom + radius, 0.0f, middleLeft, middleBottom, alpha, r, g, b);
        }
        Label_0941: {
            if (overlapping) {
                if (isLeftSide) {
                    if (overlapping) {
                        break Label_0941;
                    }
                }
                else if (!overlapping) {
                    break Label_0941;
                }
            }
            this.drawGradient(middleRight + radius, middleBottom, 0.0f, middleRight + lowCornerRadiusWidth, middleBottom + lowCornerRadiusHeight, 0.0f, middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight, middleBottom, alpha, r, g, b);
            this.drawGradient(middleRight + cornerRadiusWidth, middleBottom + cornerRadiusHeight, 0.0f, middleRight + highCornerRadiusWidth, middleBottom + highCornerRadiusHeight, 0.0f, middleRight, middleBottom + radius, 0.0f, middleRight, middleBottom, alpha, r, g, b);
        }
        tessellator.b();
    }
    
    private void drawGradient(final double x1, final double y1, final float a1, final double x2, final double y2, final float a2, final double x3, final double y3, final float a3, final double x4, final double y4, final float a4, final float r, final float g, final float b) {
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        worldrenderer.pos(x1, y1, 0.0).tex(1.0, 1.0).color(r, g, b, a1).endVertex();
        worldrenderer.pos(x2, y2, 0.0).tex(1.0, 1.0).color(r, g, b, a2).endVertex();
        worldrenderer.pos(x3, y3, 0.0).tex(1.0, 1.0).color(r, g, b, a3).endVertex();
        worldrenderer.pos(x4, y4, 0.0).tex(1.0, 1.0).color(r, g, b, a4).endVertex();
    }
    
    public int getCosmeticId() {
        return 32;
    }
    
    public String getCosmeticName() {
        return "Snoxh Eyes";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticSnoxhData extends CosmeticData
    {
        private int x;
        private int y;
        private int width;
        private int height;
        private Color color;
        private boolean leftVisible;
        private boolean rightVisible;
        private int brightness;
        
        public CosmeticSnoxhData() {
            this.x = 1;
            this.y = 4;
            this.width = 2;
            this.height = 1;
            this.color = Color.WHITE;
            this.leftVisible = true;
            this.rightVisible = true;
            this.brightness = 120;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            Exception exception = null;
            try {
                this.x = Integer.parseInt(data[0]);
                this.y = Integer.parseInt(data[1]);
                this.width = Integer.parseInt(data[2]);
                this.height = Integer.parseInt(data[3]);
                this.leftVisible = (Integer.parseInt(data[4]) == 1);
                this.rightVisible = (Integer.parseInt(data[5]) == 1);
                this.color = Color.decode("#" + data[6]);
                this.brightness = Integer.parseInt(data[7]);
            }
            catch (Exception e) {
                exception = e;
            }
            this.x = Math.min(this.x, 7);
            this.y = Math.min(this.y, 7);
            this.width = Math.min(this.width, 4);
            this.height = Math.min(this.height, 4);
            this.x = Math.max(this.x, 0);
            this.y = Math.max(this.y, 0);
            this.width = Math.max(this.width, 1);
            this.height = Math.max(this.height, 1);
            this.brightness = Math.min(this.brightness, 160);
            this.brightness = Math.max(this.brightness, 0);
            if (exception != null) {
                throw exception;
            }
        }
        
        @Override
        public void init(final User user) {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getY() {
            return this.y;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isLeftVisible() {
            return this.leftVisible;
        }
        
        public boolean isRightVisible() {
            return this.rightVisible;
        }
        
        public int getBrightness() {
            return this.brightness;
        }
    }
}
