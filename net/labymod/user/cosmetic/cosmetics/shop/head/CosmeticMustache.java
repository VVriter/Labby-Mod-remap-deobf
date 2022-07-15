//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMustache extends CosmeticRenderer<CosmeticMustacheData>
{
    public static final int ID = 43;
    private brs base;
    private brs horn;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 8;
        final int height = 4;
        (this.base = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(0.0f, -0.5f, 0.0f);
        this.base.a(-0.0f, -0.5f, -0.3f, 2, 1, 1);
        this.base.h = 0.2f;
        (this.horn = new ModelRendererHook((bqf)modelCosmetics)).a(-0.6f, 1.0f, 0.0f);
        brs last = this.horn;
        for (int i = 0; i <= 3; ++i) {
            final double scale = 1.0 - (i + 1) * 0.08;
            final ModelRendererHook part = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 2);
            part.a(0.0f, -0.5f, -0.5f, 2, 1, 1);
            part.a(2.0f, 0.0f, 0.0f);
            part.setHook(new Consumer<ModelRendererHook>() {
                @Override
                public void accept(final ModelRendererHook accepted) {
                    bus.a(scale, scale, scale);
                    accepted.renderSuper();
                }
            });
            last.a((brs)part);
            last = part;
        }
    }
    
    public void setInvisible(final boolean invisible) {
        this.base.j = invisible;
        this.horn.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMustacheData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bib.z().N().a(ModTextures.COSMETIC_MUSTACHE);
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.c(0.0f, 0.0f, -0.25f);
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        brs next = this.horn;
        for (int i = 0; i <= 4; ++i) {
            next.h = -0.7f - (float)motionY / 30.0f * i;
            if (i == 1) {
                next.h = 1.0707964f - (float)motionY / 30.0f;
                next.g = 0.2f;
            }
            if (next.m != null) {
                next = next.m.get(0);
            }
        }
        final Color color = cosmeticData.getColor();
        if (color != null) {
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
        }
        final double mustacheHornScale = 0.8;
        bus.G();
        bus.a(mustacheHornScale, mustacheHornScale, mustacheHornScale);
        bus.c(0.0f, -cosmeticData.getY() / 16.0f, 0.0f);
        bus.q();
        for (int j = 0; j < 2; ++j) {
            bus.G();
            if (j == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
                bus.c(0.0f, 0.0f, 0.005f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            this.horn.a(scale);
            this.base.a(scale);
            bus.H();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 43;
    }
    
    public String getCosmeticName() {
        return "Mustache";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticMustacheData extends CosmeticData
    {
        private Color color;
        private int y;
        
        public CosmeticMustacheData() {
            this.color = Color.WHITE;
            this.y = 0;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
            this.y = Math.min(8, Math.max(0, Integer.parseInt(data[1])));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public int getY() {
            return this.y;
        }
    }
}
