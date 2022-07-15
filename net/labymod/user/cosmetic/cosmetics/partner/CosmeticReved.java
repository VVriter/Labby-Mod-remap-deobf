//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.partner;

import net.labymod.user.cosmetic.*;
import com.google.common.collect.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import java.util.*;

public class CosmeticReved extends CosmeticRenderer<CosmeticRevedData>
{
    public static final int ID = 30;
    private List<brs> framesRight;
    private List<brs> framesLeft;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 5;
        final int height = 12;
        final int totalFrames = 120;
        final boolean isSlim = modelCosmetics.i.l.get(0).d == 2.0f;
        this.framesRight = (List<brs>)Lists.newArrayList();
        this.framesLeft = (List<brs>)Lists.newArrayList();
        int slimSkinOffset = 0;
        for (int currentFrame = 0; currentFrame < totalFrames; ++currentFrame) {
            for (int sides = 0; sides < 2; ++sides) {
                final boolean rightSide = sides == 0;
                final brs modelFire = new ModelRendererHook((bqf)modelCosmetics).b(width * totalFrames, (width + height) * 2);
                modelFire.a(isSlim ? slimSkinOffset : (currentFrame * width), isSlim ? (height + width) : 0);
                modelFire.a(rightSide ? -5.0f : 5.0f, 12.0f, 0.0f);
                modelFire.a(rightSide ? (-2.6f - (isSlim ? 0 : 1)) : -1.4f, -0.72f, -2.5f, isSlim ? 4 : 5, 12, 5, modelSize);
                if (rightSide) {
                    this.framesRight.add(modelFire);
                }
                else {
                    this.framesLeft.add(modelFire);
                }
            }
            slimSkinOffset += width - currentFrame % 2;
        }
    }
    
    public void setInvisible(final boolean invisible) {
        for (final brs frame : this.framesRight) {
            frame.j = invisible;
        }
        for (final brs frame : this.framesLeft) {
            frame.j = invisible;
        }
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticRevedData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        final boolean fireOnRightHand = cosmeticData.isUseRightSide();
        final boolean coloredTexture = cosmeticData.isUseColoredTexture() && !cosmeticData.isRainbow();
        final List<brs> modelFire = fireOnRightHand ? this.framesRight : this.framesLeft;
        final int index = (int)(System.currentTimeMillis() / 70L % (modelFire.size() / 2 - 2)) * 2;
        final brs modelFireRing = modelFire.get(index);
        int color = cosmeticData.getColor().getRGB();
        if (cosmeticData.isRainbow()) {
            final long time = System.currentTimeMillis() % 10000L;
            color = Color.HSBtoRGB(time / 10000.0f, 1.0f, 1.0f);
        }
        this.bindTextureAndColor(color, coloredTexture ? ModTextures.COSMETIC_REVED_ARM_COLORED : ModTextures.COSMETIC_REVED_ARM, modelFireRing);
        bpx.a(fireOnRightHand ? modelCosmetics.h : modelCosmetics.i, modelFireRing);
        bus.m();
        bus.g();
        bus.b(770, 771);
        bus.a(516, 0.003921569f);
        final float size = 0.91f;
        bus.c(modelFireRing.c * scale, modelFireRing.d * scale, modelFireRing.e * scale);
        bus.b(size, size, size);
        bus.c(-modelFireRing.c * scale, -modelFireRing.d * scale, -modelFireRing.e * scale);
        modelFireRing.a(scale);
        bus.G();
        bus.c(modelFireRing.c * scale, modelFireRing.d * scale, modelFireRing.e * scale);
        bus.b(-0.25, -0.2, -0.2);
        for (final FireParticle particle : cosmeticData.getParticle()) {
            particle.render(entityIn, coloredTexture, firstRotationX, color);
        }
        bus.H();
        bus.f();
        bus.a(516, 0.1f);
        bus.l();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 30;
    }
    
    public String getCosmeticName() {
        return "Reved";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.0f;
    }
    
    public boolean hasLeftHandSupport() {
        return false;
    }
    
    public boolean isVisibleInFirstPerson(final CosmeticRevedData data, final boolean rightHand) {
        return rightHand ? data.isUseRightSide() : (!data.isUseRightSide());
    }
    
    public static class CosmeticRevedData extends CosmeticData
    {
        private Color color;
        private boolean useRightSide;
        private boolean useColoredTexture;
        private FireParticle[] particle;
        private boolean rainbow;
        
        public CosmeticRevedData() {
            this.color = Color.WHITE;
            this.useRightSide = false;
            this.useColoredTexture = true;
            this.particle = new FireParticle[0];
            this.rainbow = false;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.particle = new FireParticle[20];
            for (int i = 0; i < 20; ++i) {
                this.particle[i] = new FireParticle();
            }
            this.color = Color.decode("#" + data[0]);
            if (!this.color.equals(Color.WHITE)) {
                this.useColoredTexture = false;
            }
            if (data.length >= 2) {
                this.useRightSide = (Integer.parseInt(data[1]) == 1);
            }
            if (data.length >= 3 && Integer.parseInt(data[2]) == 1) {
                this.rainbow = true;
                this.useColoredTexture = true;
            }
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.ARM;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isUseRightSide() {
            return this.useRightSide;
        }
        
        public boolean isUseColoredTexture() {
            return this.useColoredTexture;
        }
        
        public FireParticle[] getParticle() {
            return this.particle;
        }
        
        public boolean isRainbow() {
            return this.rainbow;
        }
    }
    
    public static class FireParticle
    {
        private double x;
        private double y;
        private double z;
        private float rotZ;
        private long timestamp;
        private long lifetime;
        
        public void render(final vg entityIn, final boolean coloredTexture, final float firstRotationX, final int color) {
            final long alive = System.currentTimeMillis() - this.timestamp;
            final double percent = 100.0 / this.lifetime * alive - 50.0;
            final double fade = 255.0 - Math.min(255.0, percent * percent);
            bus.G();
            bus.g();
            bus.e();
            bus.m();
            bus.b(this.x, this.y - alive / 6000.0f, this.z);
            final bzf renderManager = bib.z().ac();
            if (entityIn instanceof bua) {
                final bua entity = (bua)entityIn;
                final float rotation = renderManager.e - entity.aP;
                if (entity != bib.z().aa()) {
                    bus.b(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
                }
                else if (bib.z().t.aw == 1) {
                    bus.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
                bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
            }
            bus.b(this.rotZ + alive / 100.0f, 0.0f, 0.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.d(1.0f, 1.0f, 1.0f);
            if (!coloredTexture) {
                final int red = color >> 16 & 0xFF;
                final int green = color >> 8 & 0xFF;
                final int blue = color >> 0 & 0xFF;
                GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, (float)fade / 255.0f);
            }
            bib.z().N().a(ModTextures.COSMETIC_REVED_FLAME);
            LabyMod.getInstance().getDrawUtils().drawTexture(0.0, 0.0, coloredTexture ? 0.0 : 127.0, 0.0, 127.0, 255.0, 0.05, 0.05);
            bus.H();
            if (alive > this.lifetime) {
                final Random random = LabyMod.getRandom();
                this.timestamp = System.currentTimeMillis();
                this.lifetime = (random.nextInt(4) + 1) * 1000;
                this.x = random.nextDouble() / 2.8;
                this.y = random.nextDouble();
                this.z = random.nextDouble() / 2.5;
                this.rotZ = random.nextFloat() * 360.0f;
            }
        }
    }
}
