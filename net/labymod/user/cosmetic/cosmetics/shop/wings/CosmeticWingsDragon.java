//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.wings;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWingsDragon extends CosmeticRenderer<CosmeticWingsData>
{
    public static final int ID = 2;
    private brs wing;
    private brs wingTip;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        modelCosmetics.a("body.scale", 220, 53);
        modelCosmetics.a("body.body", 0, 0);
        modelCosmetics.a("wingtip.bone", 112, 136);
        modelCosmetics.a("wing.skin", -56, 88);
        modelCosmetics.a("wing.bone", 112, 88);
        modelCosmetics.a("wingtip.skin", -56, 144);
        final int bw = modelCosmetics.s;
        final int bh = modelCosmetics.t;
        modelCosmetics.s = 256;
        modelCosmetics.t = 256;
        (this.wing = new ModelRendererHook((bqf)modelCosmetics, "wing")).a(-12.0f, 5.0f, 2.0f);
        this.wing.a("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.a("skin", -56.0f, 0.0f, 2.0f, 56, 1, 56);
        (this.wingTip = new ModelRendererHook((bqf)modelCosmetics, "wingtip")).a(-56.0f, 0.0f, 0.0f);
        this.wingTip.a("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.a("skin", -56.0f, 0.0f, 2.0f, 56, 1, 56);
        this.wing.a(this.wingTip);
        modelCosmetics.s = bw;
        modelCosmetics.t = bh;
    }
    
    public void setInvisible(final boolean invisible) {
        this.wing.j = invisible;
        this.wingTip.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWingsData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final double movement = Math.abs(entityIn.s + entityIn.u);
        final float rotationTick = walkingSpeed + ((entityIn.z && !entityIn.aV()) ? tickValue : (tickValue * 12.0f + (float)movement + walkingSpeed)) / 100.0f;
        final int wingsScale = 25;
        bus.G();
        bib.z().N().a(ModTextures.COSMETIC_ENDER_DRAGON);
        bus.a(0.0012 * (wingsScale + 75), 0.0012 * (wingsScale + 75), 0.0012 * (wingsScale + 75));
        bus.b(0.0, -0.3, 1.1);
        bus.b(50.0f, -50.0f, 0.0f, 0.0f);
        bus.d(1.0f, 1.0f, 1.0f);
        final Color color = cosmeticData.getWingsColor();
        if (color != null) {
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
        }
        for (int i = 0; i < 2; ++i) {
            bus.q();
            final float rotation = rotationTick * 3.1415927f * 2.0f;
            this.wing.f = 0.125f - (float)Math.cos(rotation) * 0.2f;
            this.wing.g = 0.25f;
            this.wing.h = (float)(Math.sin(rotation) + 1.225) * 0.3f;
            this.wingTip.h = -(float)(Math.sin(rotation + 2.0f) + 0.5) * 0.75f;
            this.wing.a(scale);
            bus.b(-1.0f, 1.0f, 1.0f);
            if (i == 0) {
                LabyModCore.getRenderImplementation().cullFaceFront();
                if (cosmeticData.getSecondColor() != null) {
                    final Color secondColor = cosmeticData.getSecondColor();
                    GL11.glColor4f(secondColor.getRed() / 255.0f, secondColor.getGreen() / 255.0f, secondColor.getBlue() / 255.0f, 0.5f);
                }
            }
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 2;
    }
    
    public String getCosmeticName() {
        return "Dragon Wings";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticWingsData extends CosmeticData
    {
        private long flying;
        private boolean direction;
        private float lastTick;
        private Color wingsColor;
        private Color secondColor;
        
        public CosmeticWingsData() {
            this.flying = -1L;
            this.wingsColor = Color.WHITE;
            this.secondColor = null;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.wingsColor = Color.decode("#" + data[0]);
            if (data.length > 1) {
                this.secondColor = Color.decode("#" + data[1]);
            }
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.WINGS;
        }
        
        public long getFlying() {
            return this.flying;
        }
        
        public void setFlying(final long flying) {
            this.flying = flying;
        }
        
        public boolean isDirection() {
            return this.direction;
        }
        
        public void setDirection(final boolean direction) {
            this.direction = direction;
        }
        
        public float getLastTick() {
            return this.lastTick;
        }
        
        public void setLastTick(final float lastTick) {
            this.lastTick = lastTick;
        }
        
        public Color getWingsColor() {
            return this.wingsColor;
        }
        
        public Color getSecondColor() {
            return this.secondColor;
        }
    }
}
