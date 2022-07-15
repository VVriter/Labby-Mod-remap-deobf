//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMerchCrown extends CosmeticRenderer<CosmeticMerchCrownData>
{
    public static final int ID = 18;
    private brs base;
    private brs diamond;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 30;
        final int height = 16;
        final float crownScale = 0.02f;
        this.base = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height);
        this.base.a(4, 0).a(-4.0f, 0.0f, -5.0f, 8, 2, 1, crownScale);
        this.base.a(0, 0).a(-5.0f, -2.0f, -5.0f, 1, 4, 1, crownScale);
        this.base.a(0, 5).a(-4.0f, -1.0f, -5.0f, 1, 1, 1, crownScale);
        this.base.a(0, 5).a(3.0f, -1.0f, -5.0f, 1, 1, 1, crownScale);
        this.base.a(4, 5).a(-1.5f, -1.0f, -5.0f, 3, 1, 1, crownScale);
        this.base.a(0, 5).a(-0.5f, -2.0f, -5.0f, 1, 1, 1, crownScale);
        (this.diamond = new ModelRendererHook((bqf)modelCosmetics, 12, 5).b(width, height)).a(-0.5f, -0.0f, -6.0f, 1, 1, 1, crownScale);
        this.diamond.h = 0.8f;
        this.diamond.e = 0.5f;
        this.diamond.c = 0.4f;
    }
    
    public void setInvisible(final boolean invisible) {
        this.base.j = invisible;
        this.diamond.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMerchCrownData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        for (int i = 0; i < 4; ++i) {
            bus.G();
            bus.d(1.0f, 1.0f, 1.0f);
            bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
            bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
            final float scaleUp = 1.085f;
            bus.b(scaleUp, scaleUp, scaleUp);
            if (entityIn.aU()) {
                final float m = entityIn.w * -7.0E-4f;
                bus.b(0.0, 0.06f - Math.abs(m) + 0.02, (double)m);
            }
            bus.b((float)(90 * i), 0.0f, 1.0f, 0.0f);
            bus.b(0.0, -0.4753, 0.0);
            bib.z().N().a(ModTextures.COSMETIC_CROWN);
            this.base.a(0.0571f);
            this.diamond.h = 0.8f;
            this.diamond.e = 0.6f;
            this.diamond.c = 0.4f;
            bus.c(-0.22f, 0.0f, 0.0f);
            bus.d(1.0f, 1.0f, 1.0f);
            final Color color = cosmeticData.getDiamondColor();
            if (color != null) {
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
            }
            for (int d = 0; d < 3; ++d) {
                this.diamond.a(0.0561f);
                bus.c(0.218f, 0.0f, 0.0f);
            }
            GL11.glColor3d(1.0, 1.0, 1.0);
            bus.d(1.0f, 1.0f, 1.0f);
            bus.H();
        }
    }
    
    public int getCosmeticId() {
        return 18;
    }
    
    public String getCosmeticName() {
        return "Merch Crown";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticMerchCrownData extends CosmeticData
    {
        private Color diamondColor;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.diamondColor = Color.decode("#" + data[0]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public Color getDiamondColor() {
            return this.diamondColor;
        }
    }
}
