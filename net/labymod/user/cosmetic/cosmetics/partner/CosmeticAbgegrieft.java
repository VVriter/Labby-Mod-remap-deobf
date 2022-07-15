//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.partner;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticAbgegrieft extends CosmeticRenderer<CosmeticAbgegrieftData>
{
    public static final int ID = 29;
    private brs modelBelt;
    private brs modelBuckle;
    private brs modelLogo;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 28;
        final int height = 12;
        final brs modelBelt = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
        modelBelt.a(-4.5f, 9.5f, -2.5f, 9, 2, 5, modelSize);
        modelBelt.a(0.0f, 0.0f, 0.0f);
        modelBelt.f = 0.0f;
        modelBelt.g = 0.0f;
        modelBelt.h = 0.0f;
        final brs modelBuckle = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 7);
        modelBuckle.a(-1.5f, 9.5f, -2.6f, 3, 2, 1, modelSize);
        modelBuckle.a(0.0f, 0.0f, 0.0f);
        modelBuckle.f = 0.0f;
        modelBuckle.g = 0.0f;
        modelBuckle.h = 0.0f;
        final brs modelLogo = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(8, 7);
        modelLogo.a(0.0f, 0.0f, 0.0f, 2, 1, 1, modelSize);
        modelLogo.a(2.0f, 0.5f, 0.0f, 1, 1, 1, modelSize);
        modelLogo.a(-1.0f, 0.5f, 0.0f, 1, 4, 1, modelSize);
        modelLogo.a(0.0f, 4.0f, 0.0f, 2, 1, 1, modelSize);
        modelLogo.a(2.0f, 2.5f, 0.0f, 1, 2, 1, modelSize);
        modelLogo.a(1.0f, 2.5f, 0.0f, 1, 1, 1, modelSize);
        this.modelBelt = modelBelt;
        this.modelBuckle = modelBuckle;
        this.modelLogo = modelLogo;
    }
    
    public void setInvisible(final boolean invisible) {
        this.modelBelt.j = invisible;
        this.modelBuckle.j = invisible;
        this.modelLogo.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticAbgegrieftData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bus.f();
        bus.b(0.0, 0.03, 0.0);
        if (entityIn.aU()) {
            bus.c(0.0f, -0.12f, -0.08f);
            bus.b(30.0f, 1.0f, 0.0f, 0.0f);
        }
        bus.G();
        bus.a(0.97, 0.9, 0.99);
        bus.b(0.0, 0.075, 0.0);
        final brs modelBelt = this.bindTextureAndColor(cosmeticData.getColorBelt(), ModTextures.COSMETIC_ABGEGRIEFT, this.modelBelt);
        modelBelt.a(scale);
        bus.H();
        bus.G();
        bus.a(1.2, 1.05, 1.0);
        bus.b(0.0, -0.03, 0.0);
        final brs modelBuckle = this.bindTextureAndColor(cosmeticData.getColorBuckle(), ModTextures.COSMETIC_ABGEGRIEFT, this.modelBuckle);
        modelBuckle.a(scale);
        bus.a(0.9, 1.1, 1.0);
        bus.b(0.0, -0.06, 1.0E-4);
        modelBuckle.a(scale);
        bus.H();
        bus.G();
        final double size = 0.32;
        bus.a(size, size, size);
        bus.b(-0.07 / size, 0.605 / size, -0.185 / size);
        final brs modelLogo = this.bindTextureAndColor(cosmeticData.getColorLogo(), ModTextures.COSMETIC_ABGEGRIEFT, this.modelLogo);
        modelLogo.k = false;
        modelLogo.a(scale);
        bus.b(0.1 / size, 0.0, 0.0);
        modelLogo.a(scale);
        modelLogo.k = true;
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 29;
    }
    
    public String getCosmeticName() {
        return "Abgegrieft";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public static class CosmeticAbgegrieftData extends CosmeticData
    {
        private Color colorBelt;
        private Color colorBuckle;
        private Color colorLogo;
        
        public CosmeticAbgegrieftData() {
            this.colorBelt = new Color(137, 77, 55);
            this.colorBuckle = new Color(225, 205, 104);
            this.colorLogo = new Color(225, 205, 104);
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.colorBelt = Color.decode("#" + data[0]);
            this.colorBuckle = Color.decode("#" + data[1]);
            this.colorLogo = Color.decode("#" + data[2]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public Color getColorBelt() {
            return this.colorBelt;
        }
        
        public Color getColorBuckle() {
            return this.colorBuckle;
        }
        
        public Color getColorLogo() {
            return this.colorLogo;
        }
    }
}
