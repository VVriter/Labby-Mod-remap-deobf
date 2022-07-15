//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import net.labymod.core.asm.*;
import net.labymod.main.*;
import javax.imageio.*;
import java.lang.reflect.*;
import java.io.*;
import net.labymod.utils.*;

public class CustomLoadingScreen
{
    private static CustomLoadingScreen instance;
    private bib mc;
    private nf resourceMojangLogo;
    private nf resourceLabymodBanner;
    private cdr textureManager;
    private DrawUtils drawUtils;
    private int renderedFrames;
    private String[] steps;
    
    public static void renderInstance() {
        if (CustomLoadingScreen.instance == null) {
            CustomLoadingScreen.instance = new CustomLoadingScreen(bib.z(), bib.z().N());
        }
        CustomLoadingScreen.instance.drawSplashScreen();
    }
    
    public CustomLoadingScreen(final bib mc, final cdr textureManager) {
        this.renderedFrames = 0;
        this.mc = mc;
        this.textureManager = textureManager;
        this.drawUtils = new DrawUtils();
        final bip fontRenderer = new bip(mc.t, new nf("textures/font/ascii.png"), textureManager, false);
        fontRenderer.a(mc.e());
        fontRenderer.b(mc.Q().b());
        ((cen)mc.O()).a((ceq)fontRenderer);
        this.drawUtils.setFontRenderer(fontRenderer);
        this.loadResource();
    }
    
    private void loadResource() {
        try {
            final Class<?> clazz = this.mc.getClass();
            final Field field = clazz.getDeclaredField(LabyModTransformer.getMappingImplementation().getMCDefaultResourcePack());
            field.setAccessible(true);
            final ceg mcDefaultResourcePack = (ceg)field.get(this.mc);
            final InputStream inputstreamMojang = mcDefaultResourcePack.a(ModTextures.TITLE_MOJANG_BANNER);
            this.resourceMojangLogo = this.textureManager.a("logo", new cdg(ImageIO.read(inputstreamMojang)));
            final InputStream inputstreamLabyModBanner = mcDefaultResourcePack.a(ModTextures.TITLE_LABYMOD_BANNER_SPLASH);
            this.resourceLabymodBanner = this.textureManager.a("logo_lm_banner", new cdg(ImageIO.read(inputstreamLabyModBanner)));
            final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/minecraft/labymod/data/steps.titles");
            final DataInputStream dis = new DataInputStream(inputStream);
            this.steps = new String[dis.readInt()];
            for (int i = 0; i < this.steps.length; ++i) {
                final byte[] bytes = new byte[dis.readInt()];
                dis.read(bytes);
                this.steps[i] = new String(bytes);
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public void drawSplashScreen() {
        ++this.renderedFrames;
        if (this.steps == null) {
            return;
        }
        final int width = this.drawUtils.getWidth();
        final int height = this.drawUtils.getHeight();
        bus.n(5889);
        bus.F();
        bus.a(0.0, (double)width, (double)height, 0.0, 1000.0, 3000.0);
        bus.n(5888);
        bus.F();
        bus.c(0.0f, 0.0f, -2000.0f);
        bus.g();
        bus.p();
        bus.j();
        bus.y();
        bus.e();
        bus.m();
        this.drawUtils.drawRectangle(0, 0, width, height, -1);
        this.textureManager.a(this.resourceMojangLogo);
        bir.a((width - 256) / 2, (height - 256) / 2, 0.0f, 0.0f, 256, 256, 256.0f, 256.0f);
        final double percent = 100.0 / this.steps.length * this.renderedFrames;
        this.drawProgressbar(width / 2.0 - 100.0, height / 2 + 30, 200.0, 12.0, percent);
        this.textureManager.a(this.resourceLabymodBanner);
        final double bannerWidth = 140.0;
        final double bannerHeight = bannerWidth / 4.0;
        bir.a((int)(width / 2.0 - bannerWidth / 2.0), (int)(height / 2.0 + 45.0 + 10.0), 0.0f, 0.0f, (int)bannerWidth, (int)bannerHeight, (float)(int)bannerWidth, (float)(int)bannerHeight);
        final String text = (this.renderedFrames < this.steps.length) ? this.steps[this.renderedFrames] : "";
        this.drawUtils.getFontRenderer().a(text, (width - this.drawUtils.getStringWidth(text)) / 2, height / 2 + 45, ModColor.toRGB(0, 0, 0, 255));
        try {
            this.mc.i();
        }
        catch (NullPointerException error) {
            error.printStackTrace();
        }
        bus.m();
        bus.e();
    }
    
    private void drawProgressbar(final double x, final double y, final double width, final double height, double percent) {
        if (percent > 100.0) {
            percent = 100.0;
        }
        this.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y + height + 1.0, ModColor.toRGB(0, 0, 0, 255));
        this.drawRect(x, y, x + width, y + height, ModColor.toRGB(200, 200, 200, 255));
        this.drawRect(x, y, (int)(x + width / 100.0 * percent), y + height, ModColor.toRGB(0, 143, 232, 255));
        this.drawUtils.drawCenteredString(ModColor.cl("f") + (int)percent + "%", x + width / 2.0, y + height / 2.0 - 4.0);
    }
    
    private void drawRect(final double left, final double top, final double right, final double bottom, final int color) {
        bir.a((int)left, (int)top, (int)right, (int)bottom, color);
    }
}
