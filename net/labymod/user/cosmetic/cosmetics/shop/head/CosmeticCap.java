//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticCap extends CosmeticRenderer<CosmeticCapData>
{
    public static final int ID = 19;
    private brs model;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final float capScale = modelSize;
        this.model = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(168, 180);
        this.model.f = (float)Math.toRadians(-90.0);
        this.model.a(0, 0).a(-4.0f, -4.0f, -6.0f, 8, 11, 1, capScale);
        this.model.a(18, 0).a(-4.0f, -4.0f, -8.0f, 8, 8, 2, capScale);
        this.model.a(38, 0).a(-4.0f, -3.0f, -9.001f, 8, 6, 1, capScale);
        this.model.a(38, 7).a(-3.0f, -4.0f, -9.001f, 6, 1, 1, capScale);
        this.model.a(38, 9).a(-3.0f, 3.0f, -9.001f, 6, 1, 1, capScale);
        this.model.a(56, 0).a(-3.0f, 7.0f, -6.0f, 6, 1, 1, capScale);
    }
    
    public void setInvisible(final boolean invisible) {
        this.model.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticCapData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf texture = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getCapImageHandler().getResourceLocation((bua)entityIn);
        if (texture == null) {
            return;
        }
        bus.G();
        if (entityIn.aU()) {
            bus.c(0.0f, 1.0f * scale, 0.0f);
        }
        bus.b((float)Math.toDegrees(modelCosmetics.e.g), 0.0f, 1.0f, 0.0f);
        bus.b((float)Math.toDegrees(modelCosmetics.e.f), 1.0f, 0.0f, 0.0f);
        bib.z().N().a(texture);
        final float scaleUp = 1.1252f;
        bus.b(scaleUp, scaleUp, scaleUp);
        if (cosmeticData.isSnapBack()) {
            bus.b(180.0f, 0.0f, 1.0f, 0.0f);
        }
        this.model.a(scale);
        final float scaleWolf = 0.3f;
        final float cutOff = 17.066668f;
        int color = cosmeticData.getLogoColor().getRGB();
        if (cosmeticData.isLogoRainbow()) {
            color = Color.HSBtoRGB(System.currentTimeMillis() % 5000L / 5000.0f, 0.8f, 0.8f);
        }
        this.bindTextureAndColor(color, texture, (brs)null);
        bus.b(0.0, -0.458, -0.251);
        bus.b(scaleWolf, scaleWolf, scaleWolf);
        LabyMod.getInstance().getDrawUtils().drawTexture(-0.2, -0.2, 0.0, cutOff, 256.0, 256.0f - cutOff, 0.4, 0.4);
        bus.e();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 19;
    }
    
    public String getCosmeticName() {
        return "Cap";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticCapData extends CosmeticData
    {
        private boolean snapBack;
        private UserTextureContainer userTextureContainer;
        private Color logoColor;
        private boolean logoRainbow;
        
        public CosmeticCapData() {
            this.snapBack = false;
            this.logoColor = Color.WHITE;
            this.logoRainbow = false;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.snapBack = (Integer.parseInt(data[0]) == 1);
            if (data[1].equals("1")) {
                this.userTextureContainer.setFileName(UUID.fromString("7a9c8635-d64f-47ee-a373-5faceffc1915"));
                return;
            }
            this.userTextureContainer.setFileName(UUID.fromString(data[1]));
            this.logoColor = Color.decode("#" + data[2]);
            this.logoRainbow = (Integer.parseInt(data[3]) == 1);
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getCapContainer();
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public boolean isSnapBack() {
            return this.snapBack;
        }
        
        public Color getLogoColor() {
            return this.logoColor;
        }
        
        public boolean isLogoRainbow() {
            return this.logoRainbow;
        }
    }
}
