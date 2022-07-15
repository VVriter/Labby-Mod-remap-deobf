//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head.masks;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMaskCover extends CosmeticRenderer<CosmeticMaskCoverData>
{
    public static final int ID = 31;
    private brs mask;
    private brs strap;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 20;
        final int height = 20;
        (this.mask = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-3.5f, -8.7f, -5.0f, 7, 9, 1, modelSize);
        this.mask.a(-4.5f, -7.7f, -5.0f, 1, 7, 1, modelSize);
        this.mask.a(3.5f, -7.7f, -5.0f, 1, 7, 1, modelSize);
        (this.strap = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-4.5f, -5.0f, -4.5f, 9, 1, 9, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.mask.j = invisible;
        this.strap.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMaskCoverData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(1.01f, 1.01f, 1.01f);
        this.bindTextureAndColor(cosmeticData.getMask(), ModTextures.COSMETIC_MASK_FACE, this.mask).a(scale);
        bus.b(1.05f, 1.05f, 1.05f);
        this.bindTextureAndColor(cosmeticData.getStrap(), ModTextures.COSMETIC_MASK_FACE, this.strap).a(scale);
        final nf location = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getCoverMaskImageHandler().getResourceLocation((bua)entityIn);
        if (location != null) {
            bus.c(0.0f, -0.0315f, -5.0f * scale + 0.01f);
            bus.I();
            bus.g();
            bib.z().N().a(location);
            LabyMod.getInstance().getDrawUtils().drawTexture(-scale * 4.0f, -scale * 7.5, 255.0, 255.0, scale * 8.0f, scale * 8.0f);
        }
    }
    
    public int getCosmeticId() {
        return 31;
    }
    
    public String getCosmeticName() {
        return "Cover Mask";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticMaskCoverData extends CosmeticData
    {
        private Color mask;
        private Color strap;
        private UserTextureContainer userTextureContainer;
        
        public CosmeticMaskCoverData() {
            this.mask = Color.WHITE;
            this.strap = Color.WHITE;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.mask = Color.decode("#" + data[0]);
            this.strap = Color.decode("#" + data[1]);
            this.userTextureContainer.setFileName(UUID.fromString(data[2]));
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getCoverMaskContainer();
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public Color getMask() {
            return this.mask;
        }
        
        public Color getStrap() {
            return this.strap;
        }
    }
}
