//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import net.labymod.utils.texture.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;
import java.util.*;
import com.mojang.authlib.*;

public class DrawUtils extends bir
{
    private bib mc;
    public bip fontRenderer;
    private bit scaledResolution;
    private final bqv humanoidHead;
    private PlayerSkinTextureCache playerSkinTextureCache;
    
    public DrawUtils() {
        this.humanoidHead = (bqv)new bpw();
        this.playerSkinTextureCache = new PlayerSkinTextureCache();
        this.mc = bib.z();
        this.scaledResolution = new bit(this.mc);
        this.fontRenderer = ((LabyModCore.getCoreAdapter() == null) ? null : LabyModCore.getMinecraft().getFontRenderer());
    }
    
    public bip getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void setFontRenderer(final bip fontRenderer) {
        this.fontRenderer = fontRenderer;
    }
    
    public void bindTexture(final nf resourceLocation) {
        bib.z().N().a(resourceLocation);
    }
    
    public PlayerSkinTextureCache getPlayerSkinTextureCache() {
        return this.playerSkinTextureCache;
    }
    
    public void bindTexture(final String resourceLocation) {
        this.bindTexture(new nf(resourceLocation));
    }
    
    public double getCustomScaling() {
        double factor;
        for (factor = 1.0 + LabyMod.getSettings().moduleEditorZoom * 0.03; bib.z().d / factor < 320.0; factor -= 0.1) {}
        while (bib.z().e / factor < 240.0) {
            factor -= 0.1;
        }
        return factor;
    }
    
    public int getWidth() {
        return this.scaledResolution.a();
    }
    
    public int getHeight() {
        return this.scaledResolution.b();
    }
    
    public void setScaledResolution(final bit scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
    
    public bit getScaledResolution() {
        return this.scaledResolution;
    }
    
    public void drawString(final String text, final double x, final double y) {
        this.fontRenderer.a(text, (float)x, (float)y, 16777215, true);
    }
    
    public void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        LabyModCore.getMinecraft().getFontRenderer().a(text, (float)x, (float)y, color);
    }
    
    public void drawRightString(final String text, final double x, final double y) {
        this.drawString(text, x - this.getStringWidth(text), y);
    }
    
    public void drawRightStringWithShadow(final String text, final int x, final int y, final int color) {
        LabyModCore.getMinecraft().getFontRenderer().a(text, (float)(x - this.getStringWidth(text)), (float)y, color);
    }
    
    public void drawCenteredString(final String text, final double x, final double y) {
        this.drawString(text, x - this.getStringWidth(text) / 2, y);
    }
    
    public void drawString(final String text, final double x, final double y, final double size) {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawString(text, x / size, y / size);
        GL11.glPopMatrix();
    }
    
    public void drawCenteredString(final String text, final double x, final double y, final double size) {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawCenteredString(text, x / size, y / size);
        GL11.glPopMatrix();
    }
    
    public void drawRightString(final String text, final double x, final double y, final double size) {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawString(text, x / size - this.getStringWidth(text), y / size);
        GL11.glPopMatrix();
    }
    
    public void drawItem(final aip item, final double xPosition, final double yPosition, final String value) {
        bhz.c();
        bus.q();
        if (item.u()) {
            bus.k();
            this.renderItemIntoGUI(item, xPosition, yPosition);
            bus.j();
        }
        else {
            this.renderItemIntoGUI(item, xPosition, yPosition);
        }
        this.renderItemOverlayIntoGUI(item, xPosition, yPosition, value);
        bus.j();
        bus.g();
    }
    
    public void renderItemIntoGUI(final aip stack, final double x, final double y) {
        LabyModCore.getRenderImplementation().renderItemIntoGUI(stack, x, y);
    }
    
    private void renderItemOverlayIntoGUI(final aip stack, final double xPosition, final double yPosition, final String text) {
        LabyModCore.getRenderImplementation().renderItemOverlayIntoGUI(stack, xPosition, yPosition, text);
    }
    
    public int getStringWidth(final String text) {
        return this.fontRenderer.a(text);
    }
    
    public void drawRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        bus.m();
        bus.z();
        bus.a(770, 771, 1, 0);
        bus.c(f4, f5, f6, f3);
        worldrenderer.begin(7, cdy.e);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.b();
        bus.y();
        bus.l();
    }
    
    public boolean drawRect(final int mouseX, final int mouseY, final double left, final double top, final double right, final double bottom, final int color, final int hoverColor) {
        final boolean hover = mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
        this.drawRect(left, top, right, bottom, hover ? hoverColor : color);
        return hover;
    }
    
    public boolean drawRect(final int mouseX, final int mouseY, final String displayString, final double left, final double top, final double right, final double bottom, final int color, final int hoverColor) {
        final boolean hover = mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
        this.drawRect(left, top, right, bottom, hover ? hoverColor : color);
        this.drawCenteredString(displayString, left + (right - left) / 2.0, top + (bottom - top) / 2.0 - 4.0);
        return hover;
    }
    
    public void drawRectangle(final int left, final int top, final int right, final int bottom, final int color) {
        a(left, top, right, bottom, color);
    }
    
    public void drawGradientShadowTop(final double y, final double left, final double right) {
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        final int i1 = 4;
        bus.m();
        bus.a(770, 771, 0, 1);
        bus.d();
        bus.j(7425);
        bus.y();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(left, y + i1, 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(right, y + i1, 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(right, y, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(left, y, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
        tessellator.b();
        bus.j(7424);
        bus.e();
        bus.l();
    }
    
    public void drawGradientShadowBottom(final double y, final double left, final double right) {
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        final int i1 = 4;
        bus.m();
        bus.a(770, 771, 0, 1);
        bus.d();
        bus.j(7425);
        bus.y();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(left, y, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(right, y, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(right, y - i1, 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(left, y - i1, 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
        tessellator.b();
        bus.j(7424);
        bus.e();
        bus.l();
    }
    
    public void drawGradientShadowLeft(final double x, final double top, final double bottom) {
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        final int i1 = 4;
        bus.m();
        bus.a(770, 771, 0, 1);
        bus.d();
        bus.j(7425);
        bus.y();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(x + i1, bottom, 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(x + i1, top, 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(x, top, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(x, bottom, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
        tessellator.b();
        bus.j(7424);
        bus.e();
        bus.l();
    }
    
    public void drawGradientShadowRight(final double x, final double top, final double bottom) {
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        final int i1 = 4;
        bus.m();
        bus.a(770, 771, 0, 1);
        bus.d();
        bus.j(7425);
        bus.y();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(x, bottom, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(x, top, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(x - i1, top, 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos(x - i1, bottom, 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
        tessellator.b();
        bus.j(7424);
        bus.e();
        bus.l();
    }
    
    public void drawIngameBackground() {
        this.a(0, 0, this.getWidth(), this.getHeight(), -1072689136, -804253680);
    }
    
    public void drawAutoDimmedBackground(final double d) {
        if (LabyMod.getInstance().isInGame()) {
            this.drawIngameBackground();
        }
        else {
            this.drawDimmedBackground((int)d);
        }
    }
    
    public void drawAutoDimmedBackground(final int left, final int top, final int right, final int bottom) {
        if (LabyMod.getInstance().isInGame()) {
            this.a(left, top, right, bottom, -1072689136, -804253680);
        }
        else {
            this.drawDimmedOverlayBackground(left, top, right, bottom);
        }
    }
    
    public void drawBackground(final int tint) {
        this.drawBackground(tint, 0.0, 64);
    }
    
    public void drawDimmedBackground(final int scroll) {
        this.drawBackground(0, -scroll, 32);
    }
    
    public void drawBackground(final int tint, final double scrolling, final int brightness) {
        bus.g();
        bus.p();
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(0.0, (double)this.getHeight(), 0.0).tex(0.0, (this.getHeight() + scrolling) / 32.0 + tint).color(brightness, brightness, brightness, 255).endVertex();
        worldrenderer.pos((double)this.getWidth(), (double)this.getHeight(), 0.0).tex((double)(this.getWidth() / 32.0f), (this.getHeight() + scrolling) / 32.0 + tint).color(brightness, brightness, brightness, 255).endVertex();
        worldrenderer.pos((double)this.getWidth(), 0.0, 0.0).tex((double)(this.getWidth() / 32.0f), tint + scrolling / 32.0).color(brightness, brightness, brightness, 255).endVertex();
        worldrenderer.pos(0.0, 0.0, 0.0).tex(0.0, tint + scrolling / 32.0).color(brightness, brightness, brightness, 255).endVertex();
        tessellator.b();
    }
    
    public void drawOverlayBackground(final int startY, final int endY) {
        final int endAlpha = 255;
        final int startAlpha = 255;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos(0.0, (double)endY, 0.0).tex(0.0, (double)(endY / 32.0f)).color(64, 64, 64, endAlpha).endVertex();
        worldrenderer.pos((double)(0 + this.getWidth()), (double)endY, 0.0).tex((double)(this.getWidth() / 32.0f), (double)(endY / 32.0f)).color(64, 64, 64, endAlpha).endVertex();
        worldrenderer.pos((double)(0 + this.getWidth()), (double)startY, 0.0).tex((double)(this.getWidth() / 32.0f), (double)(startY / 32.0f)).color(64, 64, 64, startAlpha).endVertex();
        worldrenderer.pos(0.0, (double)startY, 0.0).tex(0.0, (double)(startY / 32.0f)).color(64, 64, 64, startAlpha).endVertex();
        tessellator.b();
    }
    
    public void drawDimmedOverlayBackground(final int left, final int top, final int right, final int bottom) {
        bus.g();
        bus.p();
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        final float f = 32.0f;
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos((double)left, (double)bottom, 0.0).tex((double)(left / f), (double)(bottom / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0).tex((double)(right / f), (double)(bottom / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0).tex((double)(right / f), (double)(top / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0).tex((double)(left / f), (double)(top / f)).color(32, 32, 32, 255).endVertex();
        tessellator.b();
        bus.m();
        bus.a(770, 771, 0, 1);
        bus.d();
        bus.j(7425);
        bus.j();
    }
    
    public void drawOverlayBackground(final int startX, final int startY, final int width, final int endY) {
        this.drawOverlayBackground(startX, startY, width, endY, 64);
    }
    
    public void drawOverlayBackground(final int startX, final int startY, final int width, final int endY, final int brightness) {
        final int endAlpha = 255;
        final int startAlpha = 255;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        this.mc.N().a(LabyModCore.getRenderImplementation().getOptionsBackground());
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        worldrenderer.begin(7, cdy.i);
        worldrenderer.pos((double)startX, (double)endY, 0.0).tex(0.0, (double)(endY / 32.0f)).color(brightness, brightness, brightness, endAlpha).endVertex();
        worldrenderer.pos((double)(startX + width), (double)endY, 0.0).tex((double)(width / 32.0f), (double)(endY / 32.0f)).color(brightness, brightness, brightness, endAlpha).endVertex();
        worldrenderer.pos((double)(startX + width), (double)startY, 0.0).tex((double)(width / 32.0f), (double)(startY / 32.0f)).color(brightness, brightness, brightness, startAlpha).endVertex();
        worldrenderer.pos((double)startX, (double)startY, 0.0).tex(0.0, (double)(startY / 32.0f)).color(brightness, brightness, brightness, startAlpha).endVertex();
        tessellator.b();
    }
    
    public void drawTexturedModalRect(final double x, final double y, final double textureX, final double textureY, final double width, final double height) {
        this.b((int)x, (int)y, (int)textureX, (int)textureY, (int)width, (int)height);
    }
    
    public void drawTexturedModalRect(final double left, final double top, final double right, final double bottom) {
        final double textureX = 0.0;
        final double textureY = 0.0;
        final double x = left;
        final double y = top;
        final double width = right - left;
        final double height = bottom - top;
        final float f = 0.00390625f;
        final float f2 = 0.00390625f;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        worldrenderer.begin(7, cdy.g);
        worldrenderer.pos(x + 0.0, y + height, (double)this.e).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(x + width, y + height, (double)this.e).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(x + width, y + 0.0, (double)this.e).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        worldrenderer.pos(x + 0.0, y + 0.0, (double)this.e).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        tessellator.b();
    }
    
    public void drawTexture(final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight, final float alpha) {
        GL11.glPushMatrix();
        final double sizeWidth = maxWidth / imageWidth;
        final double sizeHeight = maxHeight / imageHeight;
        GL11.glScaled(sizeWidth, sizeHeight, 0.0);
        if (alpha <= 1.0f) {
            bus.e();
            bus.m();
            bus.c(1.0f, 1.0f, 1.0f, alpha);
        }
        this.drawTexturedModalRect(x / sizeWidth, y / sizeHeight, x / sizeWidth + imageWidth, y / sizeHeight + imageHeight);
        if (alpha <= 1.0f) {
            bus.d();
            bus.l();
        }
        GL11.glPopMatrix();
    }
    
    public void drawRawTexture(final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        GL11.glPushMatrix();
        final double sizeWidth = maxWidth / imageWidth;
        final double sizeHeight = maxHeight / imageHeight;
        GL11.glScaled(sizeWidth, sizeHeight, 0.0);
        this.drawTexturedModalRect(x / sizeWidth, y / sizeHeight, x / sizeWidth + imageWidth, y / sizeHeight + imageHeight);
        GL11.glPopMatrix();
    }
    
    public void drawTexture(final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        this.drawTexture(x, y, imageWidth, imageHeight, maxWidth, maxHeight, 1.0f);
    }
    
    public void drawTexture(final double x, final double y, final double texturePosX, final double texturePosY, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight, final float alpha) {
        GL11.glPushMatrix();
        final double sizeWidth = maxWidth / imageWidth;
        final double sizeHeight = maxHeight / imageHeight;
        GL11.glScaled(sizeWidth, sizeHeight, 0.0);
        if (alpha <= 1.0f) {
            bus.e();
            bus.m();
            bus.c(1.0f, 1.0f, 1.0f, alpha);
        }
        this.drawUVTexture(x / sizeWidth, y / sizeHeight, texturePosX, texturePosY, x / sizeWidth + imageWidth - x / sizeWidth, y / sizeHeight + imageHeight - y / sizeHeight);
        if (alpha <= 1.0f) {
            bus.d();
            bus.l();
        }
        GL11.glPopMatrix();
    }
    
    public void drawTexture(final double x, final double y, final double texturePosX, final double texturePosY, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        this.drawTexture(x, y, texturePosX, texturePosY, imageWidth, imageHeight, maxWidth, maxHeight, 1.0f);
    }
    
    private void drawUVTexture(final double x, final double y, final double textureX, final double textureY, final double width, final double height) {
        final float f = 0.00390625f;
        final float f2 = 0.00390625f;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        worldrenderer.begin(7, cdy.g);
        worldrenderer.pos(x + 0.0, y + height, (double)this.e).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(x + width, y + height, (double)this.e).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f2)).endVertex();
        worldrenderer.pos(x + width, y + 0.0, (double)this.e).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        worldrenderer.pos(x + 0.0, y + 0.0, (double)this.e).tex((double)((float)(textureX + 0.0) * f), (double)((float)(textureY + 0.0) * f2)).endVertex();
        tessellator.b();
    }
    
    public static void drawEntityOnScreen(final int x, final int y, final int size, final float mouseX, final float mouseY, final int rotationX, final int rotationY, final int rotationZ, final vp entity) {
        bus.h();
        bus.G();
        bus.c((float)x, (float)y, 100.0f);
        bus.b(-size - 30.0f, size + 30.0f, (float)size);
        bus.b(180.0f, 0.0f, 0.0f, 1.0f);
        final float var5 = entity.aO;
        final float var6 = entity.aN;
        final float var7 = entity.v;
        final float var8 = entity.w;
        final float var9 = entity.aQ;
        final float var10 = entity.aP;
        bus.b(135.0f, 0.0f, 1.0f, 0.0f);
        bhz.b();
        bus.b(-135.0f + rotationX, 0.0f, 1.0f, 0.0f);
        bus.b((float)rotationY, 1.0f, 0.0f, 0.0f);
        bus.b((float)rotationZ, 0.0f, 0.0f, 1.0f);
        bus.b(-(float)Math.atan(mouseY / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        entity.aN = (float)Math.atan(mouseX / 40.0f) * 20.0f;
        entity.v = (float)Math.atan(mouseX / 40.0f) * 40.0f;
        entity.w = -(float)Math.atan(mouseY / 40.0f) * 20.0f;
        entity.aP = entity.v;
        entity.aQ = entity.v;
        entity.aO = entity.aN;
        bus.c(0.0f, 0.0f, 0.0f);
        final bzf var11 = bib.z().ac();
        var11.a(180.0f);
        var11.a(false);
        LabyModCore.getRenderImplementation().renderEntity(var11, (vg)entity, 0.0, (rotationY == 0) ? 0.0 : -1.0, 0.0, 0.0f, 1.0f, false);
        var11.a(true);
        entity.aN = var6;
        entity.v = var7;
        entity.w = var8;
        entity.aO = var5;
        entity.aQ = var9;
        entity.aP = var10;
        bus.H();
        bhz.a();
        bus.E();
        bus.g(cii.r);
        bus.z();
        bus.g(cii.q);
    }
    
    public String trimStringToWidth(final String text, final int width) {
        if (text == null) {
            return text;
        }
        return this.fontRenderer.a(text, width, false);
    }
    
    public List<String> listFormattedStringToWidth(final String str, int wrapWidth) {
        if (wrapWidth < 10) {
            wrapWidth = 10;
        }
        return (List<String>)this.fontRenderer.c(str, wrapWidth);
    }
    
    public List<String> listFormattedStringToWidth(final String str, final int wrapWidth, final int maxLines) {
        final List<String> list = this.listFormattedStringToWidth(str, wrapWidth);
        if (list.size() < maxLines) {
            return list;
        }
        final List<String> output = new ArrayList<String>();
        int count = 0;
        for (final String line : list) {
            ++count;
            output.add(line);
            if (count >= maxLines) {
                break;
            }
        }
        return output;
    }
    
    public void drawHoveringText(final int x, final int y, final String... textLines) {
        if (textLines.length != 0) {
            bus.E();
            bhz.a();
            bus.g();
            bus.j();
            int i = 0;
            for (final String s : textLines) {
                final int j = this.fontRenderer.a(s);
                if (j > i) {
                    i = j;
                }
            }
            int l1 = x + 7;
            int i2 = y - 12;
            int k = 8;
            if (textLines.length > 1) {
                k += 2 + (textLines.length - 1) * 10;
            }
            if (i2 < 5) {
                i2 = 5;
            }
            if (l1 + i > this.getWidth()) {
                l1 -= 12 + i;
            }
            if (i2 + k + 6 > this.getHeight()) {
                i2 = this.getHeight() - k - 6;
            }
            this.e = 300.0f;
            final int m = -267386864;
            this.a(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, m, m);
            this.a(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, m, m);
            this.a(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, m, m);
            this.a(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, m, m);
            this.a(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, m, m);
            final int i3 = 1347420415;
            final int j2 = (i3 & 0xFEFEFE) >> 1 | (i3 & 0xFF000000);
            this.a(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i3, j2);
            this.a(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i3, j2);
            this.a(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i3, i3);
            this.a(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j2, j2);
            for (int k2 = 0; k2 < textLines.length; ++k2) {
                final String s2 = textLines[k2];
                this.fontRenderer.a(s2, (float)l1, (float)i2, -1);
                if (k2 == 0) {
                    i2 += 2;
                }
                i2 += 10;
            }
            this.e = 0.0f;
        }
    }
    
    public void drawHoveringTextBoxField(final int x, final int y, final int width, final int height) {
        this.e = 300.0f;
        final int color1 = -267386864;
        this.a(x - 3, y - 4, x + width + 3, y - 3, color1, color1);
        this.a(x - 3, y + height + 3, x + width + 3, y + height + 4, color1, color1);
        this.a(x - 3, y - 3, x + width + 3, y + height + 3, color1, color1);
        this.a(x - 4, y - 3, x - 3, y + height + 3, color1, color1);
        this.a(x + width + 3, y - 3, x + width + 4, y + height + 3, color1, color1);
        final int color2 = 1347420415;
        final int color3 = (color2 & 0xFEFEFE) >> 1 | (color2 & 0xFF000000);
        this.a(x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, color2, color3);
        this.a(x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, color2, color3);
        this.a(x - 3, y - 3, x + width + 3, y - 3 + 1, color2, color2);
        this.a(x - 3, y + height + 2, x + width + 3, y + height + 3, color3, color3);
        this.e = 0.0f;
        bus.j();
    }
    
    public void drawPlayerHead(nf resourceLocation, final int x, final int y, final int size) {
        if (resourceLocation == null) {
            resourceLocation = cef.a(UUID.randomUUID());
        }
        bus.e();
        bib.z().N().a(resourceLocation);
        a(x, y, 8.0f, 8.0f, 8, 8, size, size, 64.0f, 64.0f);
        a(x, y, 40.0f, 8.0f, 8, 8, size, size, 64.0f, 64.0f);
    }
    
    public void drawPlayerHead(final GameProfile gameProfile, final int x, final int y, final int size) {
        final nf resourceLocation = this.playerSkinTextureCache.getSkinTexture(gameProfile);
        this.drawPlayerHead(resourceLocation, x, y, size);
    }
    
    public void drawPlayerHead(final String username, final int x, final int y, final int size) {
        final nf resourceLocation = this.playerSkinTextureCache.getSkinTexture(username);
        this.drawPlayerHead(resourceLocation, x, y, size);
    }
    
    public void drawPlayerHead(final UUID uuid, final int x, final int y, final int size) {
        final nf resourceLocation = this.playerSkinTextureCache.getSkinTexture(uuid);
        this.drawPlayerHead(resourceLocation, x, y, size);
    }
    
    @Deprecated
    public void drawMinotarHead(final GameProfile gameProfile, final int x, final int y, final int size) {
        this.drawPlayerHead(gameProfile, x, y, size);
    }
    
    public void drawRectBorder(final double left, final double top, final double right, final double bottom, final int color, final double thickness) {
        this.drawRect(left + thickness, top, right - thickness, top + thickness, color);
        this.drawRect(right - thickness, top, right, bottom, color);
        this.drawRect(left + thickness, bottom - thickness, right - thickness, bottom, color);
        this.drawRect(left, top, left + thickness, bottom, color);
    }
    
    public void drawImageUrl(final String url, final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        this.drawDynamicImageUrl(url, url, x, y, imageWidth, imageHeight, maxWidth, maxHeight);
    }
    
    public void drawDynamicImageUrl(final String identifier, final String url, final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        final nf resourceLocation = LabyMod.getInstance().getDynamicTextureManager().getTexture(identifier, url);
        bib.z().N().a(resourceLocation);
        LabyMod.getInstance().getDrawUtils().drawTexture(x, y, imageWidth, imageHeight, maxWidth, maxHeight);
    }
    
    public void renderSkull(final GameProfile gameProfile) {
        final bqv modelbase = this.humanoidHead;
        final nf resourceSkin = this.playerSkinTextureCache.getSkinTexture(gameProfile);
        if (resourceSkin != null) {
            bib.z().N().a(resourceSkin);
            bus.G();
            bus.r();
            bus.D();
            bus.e();
            bus.b(-1.0f, 1.0f, 1.0f);
            bus.c(0.0f, 0.2f, 0.0f);
            modelbase.a((vg)null, 0.0f, 0.0f, 0.0f, 180.0f, 0.0f, 0.0625f);
            bus.H();
        }
    }
}
