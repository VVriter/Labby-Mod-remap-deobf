//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head.masks;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMaskKawaii extends CosmeticRenderer<CosmeticMaskKawaiiData>
{
    public static final int ID = 34;
    private brs mask;
    private brs strap;
    private brs blankMask;
    private brs extendedMask;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 20;
        final int height = 20;
        (this.mask = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-4.5f, 0.0f, 0.0f, 9, 1, 1, modelSize);
        final brs modelBottom = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0);
        modelBottom.a(-4.5f, 1.0f, 0.0f, 9, 1, 1, modelSize);
        modelBottom.e = -0.3f;
        modelBottom.f = 0.31f;
        this.mask.a(modelBottom);
        final brs modelTop = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0);
        modelTop.a(-3.0f, -0.5f, -0.2f, 6, 2, 1, modelSize);
        this.mask.a(modelTop);
        (this.extendedMask = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-4.5f, -1.0f, 0.0f, 9, 1, 1, modelSize);
        (this.blankMask = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-2.0f, 0.0f, -0.4f, 4, 1, 1, modelSize);
        (this.strap = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-12.0f, 1.0f, 2.3f, 1, 1, 6, modelSize);
        this.strap.f = 0.1f;
        this.strap.g = 0.03f;
        this.strap.h = 0.0f;
    }
    
    public void setInvisible(final boolean invisible) {
        this.mask.j = invisible;
        this.strap.j = invisible;
        this.extendedMask.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMaskKawaiiData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final boolean extended = cosmeticData.isExtended();
        bus.G();
        bus.b(1.1f, 1.1f, 1.1f);
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(0.0, 0.1, -0.28);
        bus.G();
        bus.b(0.0, -0.1817, -0.0063);
        bus.a(0.83999, 1.0, 1.0);
        this.bindTextureAndColor(cosmeticData.getMask(), ModTextures.COSMETIC_MASK_FACE, this.mask);
        this.mask.a(scale);
        if (extended) {
            this.extendedMask.a(scale);
        }
        final nf location = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getKawaiiMaskImageHandler().getResourceLocation((bua)entityIn);
        if (location != null && cosmeticData.isUsingTexture()) {
            bib.z().N().a(location);
            final double k = 0.0625;
            bus.G();
            bus.b(-k * 3.0, -0.03150000050663948, -0.012520000338554382);
            bus.a(k, k, k);
            bus.I();
            bus.g();
            LabyMod.getInstance().getDrawUtils().drawTexture(0.0, 0.0, 255.0, 255.0, 6.0, 2.0);
            bus.H();
        }
        else {
            this.blankMask.a(scale);
        }
        bus.a(0.4, 0.4, 0.4);
        bus.b(0.0, 0.18, -0.08);
        bus.b(10.0f, 1.0f, 0.0f, 0.0f);
        this.bindTextureAndColor(cosmeticData.getStrap(), ModTextures.COSMETIC_MASK_FACE, this.mask);
        bus.q();
        for (int side = 0; side < 2; ++side) {
            for (int amount = 0; amount < 2; ++amount) {
                this.strap.a(scale);
                if (extended) {
                    bus.c(0.0f, -0.1f, 0.0f);
                }
                bus.b(1.0f, -1.0f, 1.0f);
                if (side == 0) {
                    LabyModCore.getRenderImplementation().cullFaceFront();
                }
                else {
                    LabyModCore.getRenderImplementation().cullFaceBack();
                }
            }
            bus.b(-1.0f, 1.0f, 1.0f);
            if (side == 0) {
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            else {
                LabyModCore.getRenderImplementation().cullFaceBack();
            }
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 34;
    }
    
    public String getCosmeticName() {
        return "Kawaii Mask";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticMaskKawaiiData extends CosmeticData
    {
        private Color mask;
        private Color strap;
        private boolean extended;
        private UserTextureContainer userTextureContainer;
        private boolean usingTexture;
        
        public CosmeticMaskKawaiiData() {
            this.mask = Color.WHITE;
            this.strap = Color.WHITE;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getKawaiiMaskContainer();
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.mask = Color.decode("#" + data[0]);
            this.strap = Color.decode("#" + data[1]);
            this.extended = (Integer.parseInt(data[2]) == 1);
            final boolean usingTexture = data.length > 3 && !data[3].equals("null");
            this.usingTexture = usingTexture;
            if (usingTexture) {
                this.userTextureContainer.setFileName(UUID.fromString(data[3]));
            }
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
        
        public boolean isExtended() {
            return this.extended;
        }
        
        public boolean isUsingTexture() {
            return this.usingTexture;
        }
    }
}
