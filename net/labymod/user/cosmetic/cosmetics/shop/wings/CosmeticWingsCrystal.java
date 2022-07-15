//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.wings;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWingsCrystal extends CosmeticRenderer<CosmeticWingsCrystalData>
{
    public static final int ID = 13;
    private brs model;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 30;
        final int height = 24;
        (this.model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 8)).a(-0.0f, 1.0f, 0.0f);
        this.model.a(0.0f, -3.0f, 0.0f, 14, 7, 1);
        final brs model = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 16);
        model.a(-0.0f, 0.0f, 0.2f);
        model.a(0.0f, -3.0f, 0.0f, 14, 7, 1);
        this.model.a(model);
        final brs model2 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
        model2.a(-0.0f, 0.0f, 0.2f);
        model2.a(0.0f, -3.0f, 0.0f, 14, 7, 1);
        model.a(model2);
    }
    
    public void setInvisible(final boolean invisible) {
        this.model.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWingsCrystalData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final float animation = (float)Math.cos(tickValue / 10.0f) / 20.0f - 0.03f - walkingSpeed / 20.0f;
        final brs firstModel = this.model.m.get(0);
        final brs secondModel = firstModel.m.get(0);
        this.model.h = animation * 3.0f;
        firstModel.h = animation / 2.0f;
        secondModel.h = animation / 2.0f;
        this.model.g = -0.3f - walkingSpeed / 3.0f;
        this.model.f = 0.3f;
        bus.G();
        bus.a(1.6, 1.6, 1.0);
        bus.b(0.0, 0.05000000074505806, 0.05000000074505806);
        if (entityIn.aU()) {
            bus.b(0.0, -0.07999999821186066, 0.029999999329447746);
            bus.b(20.0f, 1.0f, 0.0f, 0.0f);
            this.model.h = 0.8f;
            firstModel.h = 0.0f;
            secondModel.h = 0.0f;
        }
        else {
            final bzf manager = bib.z().ac();
            if (manager != null) {
                bus.b(manager.f / 3.0f, 1.0f, 0.0f, 0.0f);
            }
        }
        final Color color = cosmeticData.getColor();
        bus.r();
        for (int i = -1; i <= 1; i += 2) {
            bus.G();
            bus.c(1.0f, 1.0f, 1.0f, 0.3f);
            bus.a(false);
            bus.m();
            bus.b(770, 771);
            bus.a(516, 0.003921569f);
            bus.g();
            bib.z().N().a(ModTextures.COSMETIC_WINGS_CRYSTAL);
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
            }
            bus.b(0.05, 0.0, 0.0);
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
            if (i == 1 && cosmeticData.getSecondColor() != null) {
                final Color secondColor = cosmeticData.getSecondColor();
                GL11.glColor4f(secondColor.getRed() / 255.0f, secondColor.getGreen() / 255.0f, secondColor.getBlue() / 255.0f, 0.5f);
            }
            this.model.a(scale);
            bus.l();
            bus.a(516, 0.1f);
            bus.H();
            bus.a(true);
        }
        bus.q();
        bus.f();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 13;
    }
    
    public String getCosmeticName() {
        return "Crystal Wings";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticWingsCrystalData extends CosmeticData
    {
        public long lastParticle;
        private Color color;
        private Color secondColor;
        
        public CosmeticWingsCrystalData() {
            this.color = new Color(255, 255, 255);
            this.secondColor = null;
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
            return EnumLegacyCosmeticType.WINGS;
        }
        
        public long getLastParticle() {
            return this.lastParticle;
        }
        
        public void setLastParticle(final long lastParticle) {
            this.lastParticle = lastParticle;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public Color getSecondColor() {
            return this.secondColor;
        }
    }
}
