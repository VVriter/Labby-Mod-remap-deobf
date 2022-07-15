//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticBackPack extends CosmeticRenderer<CosmeticBackPackData>
{
    public static final int ID = 20;
    private brs backPack;
    private brs primaryColors;
    private brs strap;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 38;
        final int height = 16;
        final brs backPack = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
        backPack.a(-3.5f, 0.0f, -0.5f, 7, 9, 2, modelSize);
        backPack.a(0.0f, 0.0f, 0.0f);
        final brs button = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(34, 0);
        button.a(-0.5f, 0.0f, -0.5f, 1, 2, 1, modelSize);
        button.a(0.0f, 2.2f, 1.7f);
        backPack.a(button);
        this.backPack = backPack;
        final brs primaryColors = new ModelRendererHook((bqf)modelCosmetics);
        primaryColors.a(0.0f, 5.8f, 1.9f);
        brs model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(18, 0);
        model.a(-3.5f, 0.0f, -0.5f, 7, 4, 1, modelSize);
        primaryColors.a(model);
        model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(18, 5);
        model.a(-3.5f, 0.0f, -0.5f, 7, 3, 1, modelSize);
        model.a(0.0f, -5.5f, -0.4f);
        primaryColors.a(model);
        model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 11);
        model.a(-4.0f, 0.0f, -0.5f, 8, 3, 2, modelSize);
        model.a(0.0f, 0.9f, -1.8f);
        primaryColors.a(model);
        this.primaryColors = primaryColors;
        final brs straps = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(34, 3);
        straps.a(-4.0f, 0.0f, -0.5f, 1, 3, 1, modelSize);
        straps.a(0.5f, -1.3f, -0.2f);
        model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(20, 12);
        model.a(-4.0f, 0.0f, -0.5f, 1, 1, 3, modelSize);
        model.a(0.1f, -0.3f, -2.2f);
        straps.a(model);
        this.strap = straps;
    }
    
    public void setInvisible(final boolean invisible) {
        this.backPack.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticBackPackData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.029999999329447746, -0.019999999552965164);
            bus.b(30.0f, 1.0f, 0.0f, 0.0f);
        }
        bus.c(0.0f, 0.05f, 0.2f);
        final brs backpackModel = this.bindTextureAndColor(cosmeticData.getSecondColor(), ModTextures.COSMETIC_BACKPACK, this.backPack);
        backpackModel.a(scale);
        final brs primaryColors = this.bindTextureAndColor(cosmeticData.getColor(), ModTextures.COSMETIC_BACKPACK, this.primaryColors);
        bus.a(0.9, 0.9, 0.9);
        primaryColors.a(scale);
        bus.q();
        for (int j = 0; j < 2; ++j) {
            this.strap.a(scale);
            bus.b(-1.0f, 1.0f, 1.0f);
            LabyModCore.getRenderImplementation().cullFaceFront();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 20;
    }
    
    public String getCosmeticName() {
        return "Backpack";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public static class CosmeticBackPackData extends CosmeticData
    {
        private Color color;
        private Color secondColor;
        
        public CosmeticBackPackData() {
            this.color = Color.RED;
            this.secondColor = new Color(127, 51, 0);
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
            if (data.length > 1) {
                this.secondColor = Color.decode("#" + data[1]);
            }
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public Color getSecondColor() {
            return this.secondColor;
        }
    }
}
