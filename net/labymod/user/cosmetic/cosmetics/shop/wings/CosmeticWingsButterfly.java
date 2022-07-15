//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.wings;

import net.labymod.user.cosmetic.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWingsButterfly extends CosmeticRenderer<CosmeticWingsButterflyData>
{
    public static final int ID = 35;
    private brs wingMain;
    private brs wingSub;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 26;
        final int height = 16;
        (this.wingMain = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(0.0f, -1.5f, 0.0f);
        this.wingMain.a(-0.0f, -2.0f, -1.0f, 12, 7, 1);
        this.wingMain.q = 0.115f;
        this.wingMain.o = -0.02f;
        this.wingMain.h = -0.1f;
        (this.wingSub = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 8)).a(0.0f, 4.7f, 0.0f);
        this.wingSub.a(-0.0f, -2.0f, -0.5f, 12, 7, 1);
        this.wingSub.h = 0.1f;
        this.wingSub.f = 0.3f;
        this.wingSub.q = 0.115f;
        this.wingSub.o = 0.02f;
    }
    
    public void setInvisible(final boolean invisible) {
        this.wingMain.j = invisible;
        this.wingSub.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWingsButterflyData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bus.a(1.2, 1.2, 1.2);
        if (entityIn.aU()) {
            bus.b(0.0, -0.019999999552965164, 0.029999999329447746);
            bus.b(20.0f, 1.0f, 0.0f, 0.0f);
            final brs wingSub = this.wingSub;
            final brs wingMain = this.wingMain;
            final float n = -0.83f;
            wingMain.g = n;
            wingSub.g = n;
        }
        else {
            float idleAnimation = (float)Math.cos(tickValue / 13.0f) / 5.0f - 0.5f;
            float flyingAnimation = (float)Math.cos(tickValue / 1.0f) / 5.0f - 0.5f;
            cosmeticData.updateFadeAnimation(entityIn.z || System.currentTimeMillis() - cosmeticData.lastOnGround < 500L);
            idleAnimation *= cosmeticData.getOnGroundStrength();
            flyingAnimation *= cosmeticData.getAirStrength();
            final brs wingSub2 = this.wingSub;
            final brs wingMain2 = this.wingMain;
            final float n2 = idleAnimation + flyingAnimation - walkingSpeed / 7.0f;
            wingMain2.g = n2;
            wingSub2.g = n2;
        }
        if (entityIn.z) {
            cosmeticData.lastOnGround = System.currentTimeMillis();
        }
        if (cosmeticData.isCape() && ((bua)entityIn).a(aee.a)) {
            bus.c(0.0f, 0.0f, 0.037f);
            this.wingMain.c = 1.0f;
            this.wingSub.c = 1.0f;
        }
        else {
            this.wingMain.c = 0.0f;
            this.wingSub.c = 0.0f;
        }
        bus.r();
        for (int i = 0; i < 2; ++i) {
            bus.G();
            bus.e();
            bus.m();
            bus.b(770, 771);
            bus.a(516, 0.003921569f);
            if (i == 0) {
                final int mainColor = cosmeticData.getMainColor().getRGB();
                final int red = mainColor >> 16 & 0xFF;
                final int green = mainColor >> 8 & 0xFF;
                final int blue = mainColor >> 0 & 0xFF;
                GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 0.8f);
                bib.z().N().a(ModTextures.COSMETIC_WINGS_BUTTERFLY);
            }
            else {
                final int patternColor = cosmeticData.getPatternColor().getRGB();
                final int red = patternColor >> 16 & 0xFF;
                final int green = patternColor >> 8 & 0xFF;
                final int blue = patternColor >> 0 & 0xFF;
                GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 0.8f);
                bib.z().N().a(ModTextures.COSMETIC_WINGS_BUTTERFLY_OVERLAY);
            }
            this.wingMain.a(scale);
            this.wingSub.a(scale);
            bus.b(-1.0f, 1.0f, 1.0f);
            this.wingMain.a(scale);
            this.wingSub.a(scale);
            bus.H();
        }
        bus.q();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 35;
    }
    
    public String getCosmeticName() {
        return "Butterfly Wings";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticWingsButterflyData extends AnimatedCosmeticData
    {
        private Color mainColor;
        private Color patternColor;
        private boolean cape;
        protected long lastOnGround;
        
        public CosmeticWingsButterflyData() {
            this.mainColor = new Color(39, 131, 173);
            this.patternColor = new Color(255, 255, 255);
            this.lastOnGround = 0L;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.mainColor = Color.decode("#" + data[0]);
            if (data.length > 1) {
                this.patternColor = Color.decode("#" + data[1]);
            }
        }
        
        @Override
        public void completed(final User user) {
            final Map<Integer, CosmeticData> cosmetics = user.getCosmetics();
            this.cape = cosmetics.containsKey(0);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.WINGS;
        }
        
        public Color getMainColor() {
            return this.mainColor;
        }
        
        public Color getPatternColor() {
            return this.patternColor;
        }
        
        public boolean isCape() {
            return this.cape;
        }
    }
}
