//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticDevilHorn extends CosmeticRenderer<CosmeticDevilHornData>
{
    public static final int ID = 12;
    private brs devilHorn;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 12;
        final int height = 8;
        final brs devilHorn = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
        devilHorn.a(-1.0f, 0.0f, -0.5f, 2, 3, 1, modelSize);
        devilHorn.a(-2.5f, 0.0f, 0.0f);
        devilHorn.g = 0.0f;
        devilHorn.h = 2.5f;
        final brs subHorn = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(6, 0);
        subHorn.a(-1.0f, 0.0f, -0.5f, 2, 3, 1, modelSize);
        subHorn.a(0.1f, 2.6f, -0.2f);
        subHorn.g = -0.1f;
        subHorn.h = 0.4f;
        devilHorn.a(subHorn);
        final brs tipHorn = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(6, 4);
        tipHorn.a(-1.0f, 0.0f, -0.5f, 1, 3, 1, modelSize);
        tipHorn.a(1.0f, 3.0f, -0.2f);
        tipHorn.g = -0.1f;
        tipHorn.h = 1.05f;
        subHorn.a(tipHorn);
        this.devilHorn = devilHorn;
    }
    
    public void setInvisible(final boolean invisible) {
        this.devilHorn.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticDevilHornData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.c(0.0f, -0.45f, 0.0f);
        bus.q();
        final brs devilHorn = this.bindTextureAndColor(cosmeticData.getColor(), ModTextures.COSMETIC_DEVIL_HORNS, this.devilHorn);
        for (int j = 0; j < 2; ++j) {
            devilHorn.a(scale);
            bus.b(-1.0f, 1.0f, 1.0f);
            LabyModCore.getRenderImplementation().cullFaceFront();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 12;
    }
    
    public String getCosmeticName() {
        return "Devil Horn";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.2f;
    }
    
    public static class CosmeticDevilHornData extends CosmeticData
    {
        private Color color;
        
        public CosmeticDevilHornData() {
            this.color = Color.RED;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public Color getColor() {
            return this.color;
        }
    }
}
