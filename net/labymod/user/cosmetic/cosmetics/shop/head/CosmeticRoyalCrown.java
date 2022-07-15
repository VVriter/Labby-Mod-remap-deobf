//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticRoyalCrown extends CosmeticRenderer<CosmeticRoyalCrownData>
{
    public static final int ID = 28;
    private brs base;
    private brs diamond;
    private brs pillow;
    private brs scaffolding;
    private brs tip;
    
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
        this.pillow = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height);
        this.pillow.a(0, 7).a(-3.5f, 0.0f, -3.5f, 7, 2, 7, crownScale);
        this.scaffolding = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height);
        this.scaffolding.a(22, 0).a(0.5f, 0.0f, -0.5f, 3, 1, 1, crownScale);
        this.scaffolding.a(26, 2).a(-4.5f, 0.5f, -0.51f, 1, 1, 1, crownScale);
        this.tip = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height);
        this.tip.a(22, 4).a(-0.5f, -2.5f, -0.5f, 1, 3, 1, crownScale);
        this.tip.a(22, 2).a(-1.5f, -1.8f, -0.5f, 1, 1, 1, crownScale);
    }
    
    public void setInvisible(final boolean invisible) {
        this.base.j = invisible;
        this.diamond.j = invisible;
        this.pillow.j = invisible;
        this.scaffolding.j = invisible;
        this.tip.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticRoyalCrownData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        final float scaleUp = 1.085f;
        bus.b(scaleUp, scaleUp, scaleUp);
        if (entityIn.aU()) {
            final float m = entityIn.w * -7.0E-4f;
            bus.b(0.0, 0.06f - Math.abs(m) + 0.02, (double)m);
        }
        bus.G();
        final Color pillowColor = cosmeticData.getPillowColor();
        if (pillowColor == null) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            GL11.glColor4f(pillowColor.getRed() / 255.0f, pillowColor.getGreen() / 255.0f, pillowColor.getBlue() / 255.0f, 0.5f);
        }
        bus.c(0.0f, -0.55f, 0.0f);
        bib.z().N().a(ModTextures.COSMETIC_CROWN);
        this.pillow.a(0.0571f);
        bus.a(0.8, 0.8, 0.8);
        bus.b(0.0, -0.05, 0.0);
        this.pillow.a(0.0571f);
        bus.H();
        for (int i = 0; i < 4; ++i) {
            bus.G();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.b((float)(90 * i), 0.0f, 1.0f, 0.0f);
            bus.G();
            bus.b(0.0, -0.6753, 0.0);
            this.scaffolding.a(0.0571f);
            bus.a(0.5, 0.5, 0.5);
            this.tip.a(0.0571f);
            bus.H();
            bus.G();
            bus.b(0.0, -0.4753, 0.0);
            this.base.a(0.0571f);
            this.diamond.h = 0.8f;
            this.diamond.e = 0.6f;
            this.diamond.c = 0.4f;
            bus.c(-0.22f, 0.0f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.c(0.128f, 0.03f, 0.0f);
            if (cosmeticData.getKingOfItem() != null) {
                bus.q();
                if (i == 2) {
                    for (int d = 0; d < 3; ++d) {
                        if (d != 1 || i == 2) {
                            double scaleEmerald = 0.07000000029802322;
                            double distance = 4.199999809265137;
                            if (d == 1) {
                                scaleEmerald = 0.10000000149011612;
                                distance = 3.0;
                            }
                            LabyModCore.getRenderImplementation().cullFaceFront();
                            bus.G();
                            bus.a(scaleEmerald, -scaleEmerald, scaleEmerald);
                            bus.b(0.05999999865889549, -0.30000001192092896, distance);
                            if (d == 1) {
                                bus.c(0.0f, 0.2f, 0.0f);
                            }
                            bib.z().ae().a((vp)entityIn, cosmeticData.getKingOfItem(), bwc.b.i);
                            bib.z().N().a(ModTextures.COSMETIC_CROWN);
                            bus.H();
                        }
                        bus.c(0.088f, 0.0f, 0.0f);
                    }
                }
                LabyModCore.getRenderImplementation().cullFaceBack();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.d(1.0f, 1.0f, 1.0f);
            bus.H();
            bus.H();
        }
        bus.H();
    }
    
    public int getCosmeticId() {
        return 28;
    }
    
    public String getCosmeticName() {
        return "Royal Crown";
    }
    
    public float getNameTagHeight() {
        return 0.15f;
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticRoyalCrownData extends CosmeticData
    {
        private aip kingOfItem;
        private Color pillowColor;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.kingOfItem = new aip(ain.c(Integer.parseInt(data[0])));
            if (data.length > 1) {
                this.pillowColor = Color.decode("#" + data[1]);
            }
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public aip getKingOfItem() {
            return this.kingOfItem;
        }
        
        public Color getPillowColor() {
            return this.pillowColor;
        }
    }
}
