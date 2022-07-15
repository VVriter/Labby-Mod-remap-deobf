//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.wings;

import net.labymod.user.cosmetic.*;
import net.labymod.utils.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWingsSteampunk extends CosmeticRenderer<CosmeticWingsSteamPunkData>
{
    public static final int ID = 14;
    private brs model;
    private float lastAnimationTick;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 26;
        final int height = 22;
        final float layerOffset = 0.5f;
        final brs bone3 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
        bone3.a(-0.5f, -0.5f, layerOffset * 2.0f, 1, 10, 1);
        bone3.a(1.0f, 2.0f, 0.0f);
        final brs bone3Sub = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(8, 0);
        bone3Sub.a(-0.5f, -1.5f, layerOffset * 2.0f, 1, 5, 1);
        bone3Sub.a(0.0f, 9.0f, 0.0f);
        bone3Sub.a(bone3);
        final ModelRendererHook bone3Gear = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 0);
        bone3Gear.a(-2.0f, -2.0f, layerOffset * 2.0f + 0.1f, 4, 4, 1);
        bone3Gear.a(0.0f, 0.0f, 1.0f);
        bone3Gear.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                bus.m();
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                bone3Gear.renderSuper();
                bus.l();
            }
        });
        bone3Sub.a((brs)bone3Gear);
        final brs bone4 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(22, 11);
        bone4.a(-0.5f, -0.5f, layerOffset - 0.1f, 1, 10, 1);
        bone4.a(0.0f, 3.0f, 0.0f);
        bone4.a(bone3Sub);
        final brs bone5 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(12, 0);
        bone5.a(-0.5f, -0.5f, layerOffset, 1, 4, 1);
        bone5.a(0.0f, 0.0f, 0.0f);
        bone5.a(bone4);
        final ModelRendererHook bone1Gear = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(8, 6);
        bone1Gear.a(-1.0f, -1.0f, layerOffset * 3.0f + 0.5f, 2, 2, 1);
        bone1Gear.a(0.0f, 3.0f, 0.0f);
        bone1Gear.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                bus.m();
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                bone1Gear.renderSuper();
                bus.l();
            }
        });
        bone5.a((brs)bone1Gear);
        final brs stick1 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 11);
        stick1.a(-0.5f, -0.5f, layerOffset * 1.0f, 1, 10, 1);
        stick1.a(0.0f, 9.0f, 0.0f);
        bone4.a(stick1);
        final brs stick2 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(4, 11);
        stick2.a(-0.5f, -0.5f, layerOffset * 1.0f + 0.1f, 1, 9, 1);
        stick2.a(0.0f, 9.0f, 0.0f);
        bone4.a(stick2);
        final brs stick3 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(8, 11);
        stick3.a(-0.5f, -0.5f, layerOffset * 1.0f + 0.2f, 1, 8, 1);
        stick3.a(0.0f, 9.0f, 0.0f);
        bone4.a(stick3);
        final ModelRendererHook textile1 = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 5);
        textile1.a(-0.5f, -0.5f, layerOffset * 1.0f - 0.1f, 4, 8, 1);
        textile1.a(-3.8f, 2.0f, 0.0f);
        textile1.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                CosmeticWingsSteampunk.this.renderTextile(model, 1);
            }
        });
        bone3.a((brs)textile1);
        final ModelRendererHook textile2 = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 5);
        textile2.a(-0.5f, -0.5f, layerOffset * 1.0f - 0.1f, 4, 8, 1);
        textile2.a(-3.8f, 2.0f, 0.0f);
        textile2.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                CosmeticWingsSteampunk.this.renderTextile(model, 2);
            }
        });
        stick1.a((brs)textile2);
        final ModelRendererHook textile3 = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 5);
        textile3.a(-0.5f, -0.5f, layerOffset * 1.0f - 0.1f, 4, 8, 1);
        textile3.a(-3.8f, 2.0f, 0.0f);
        textile3.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                CosmeticWingsSteampunk.this.renderTextile(model, 3);
            }
        });
        stick2.a((brs)textile3);
        final ModelRendererHook textile4 = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(16, 5);
        textile4.a(-0.5f, -0.5f, layerOffset * 1.0f - 0.1f, 4, 8, 1);
        textile4.a(-3.8f, 2.0f, 0.0f);
        textile4.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook model) {
                CosmeticWingsSteampunk.this.renderTextile(model, 4);
            }
        });
        stick3.a((brs)textile4);
        final brs connection = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(12, 11);
        connection.a(0.5f, 0.0f, layerOffset * -2.0f, 1, 10, 1);
        connection.a(0.0f, 1.5f, 0.0f);
        bone5.a(connection);
        this.model = bone5;
    }
    
    public void setInvisible(final boolean invisible) {
        this.model.j = invisible;
    }
    
    private void renderTextile(final ModelRendererHook model, final int index) {
        final boolean fogEnabled = GL11.glGetBoolean(2912);
        bus.G();
        bus.p();
        bus.d();
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.a(false);
        bib.z().N().a(ModTextures.VOID);
        double heightMain = 0.0;
        double heightOff = 0.0;
        double distance = 0.0;
        float zMain = 0.07f;
        final float zOff = 0.07f;
        final float transparency = (1.0f - this.lastAnimationTick) / 2.0f;
        switch (index) {
            case 1: {
                heightMain = 0.6000000238418579;
                heightOff = 0.4000000059604645;
                distance = this.lastAnimationTick / 3.0 - 0.32;
                zMain = 0.09f;
                break;
            }
            case 2: {
                heightMain = 0.6000000238418579;
                heightOff = 0.4300000071525574;
                distance = this.lastAnimationTick / 3.0 - 0.32;
                break;
            }
            case 3: {
                heightMain = 0.5299999713897705;
                heightOff = 0.4099999964237213;
                distance = this.lastAnimationTick / 4.0 - 0.26;
                break;
            }
            case 4: {
                heightMain = 0.4699999988079071;
                heightOff = 0.46000000834465027;
                distance = this.lastAnimationTick / 2.6 - 0.42;
                break;
            }
        }
        GL11.glBegin(4);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, transparency);
        if (index == 1) {
            GL11.glVertex3f(-0.06f, -0.0f, zMain);
        }
        else {
            GL11.glVertex3f(0.0f, 0.0f, zMain);
        }
        GL11.glVertex3d(distance, heightOff, (index == 4) ? 0.05000000074505806 : ((double)zOff));
        GL11.glVertex3d(0.0, heightMain, (double)zMain);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        bib.z().N().a(ModTextures.COSMETIC_WINGS_STEAMPUNK);
        bus.a(true);
        bus.y();
        bus.l();
        bus.I();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.e();
        if (fogEnabled) {
            bus.o();
        }
        bus.H();
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWingsSteamPunkData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        float animation = 1.0f - cosmeticData.getAnimationTick(walkingSpeed / 1.3f) + (float)Math.cos(movementFactor / 2.0f) / 15.0f * walkingSpeed - 0.1f;
        if (entityIn.aU()) {
            animation -= 0.3f;
        }
        this.lastAnimationTick = animation;
        this.model.f = 0.4f;
        this.model.g = 0.0f;
        this.model.h = animation - 1.0f;
        final brs connection = this.model.m.get(2);
        connection.f = 0.6f;
        connection.g = -0.4f;
        connection.h = -animation * 1.5f - 1.2f;
        final brs bone2 = this.model.m.get(0);
        bone2.f = 0.5f;
        bone2.g = -0.4f;
        bone2.h = -animation * 1.4f - 1.5f;
        final brs gear1 = this.model.m.get(1);
        gear1.h = animation;
        final brs bone3 = bone2.m.get(0);
        bone3.h = animation * 1.8f * 1.2f + 1.8f - 1.0f;
        final brs gear2 = bone3.m.get(1);
        gear2.h = animation;
        final brs stick1 = bone2.m.get(1);
        stick1.h = (animation * 0.9f + 2.1f) * 2.0f - 3.0f;
        final brs stick2 = bone2.m.get(2);
        stick2.h = (animation * 0.6f + 2.4f) * 2.0f - 3.0f;
        final brs stick3 = bone2.m.get(3);
        stick3.h = (animation * 0.3f + 2.7f) * 2.0f - 3.0f;
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, -0.009999999776482582, -0.0);
            bus.b(20.0f, 1.0f, 0.0f, 0.0f);
        }
        bib.z().N().a(ModTextures.COSMETIC_WINGS_STEAMPUNK);
        bus.c(1.0f, 1.0f, 1.0f, 0.4f);
        bus.c(0.0f, 0.1f, 0.1f);
        bus.b(0.8f, 0.8f, 0.8f);
        bus.y();
        bus.l();
        bus.I();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.e();
        bus.r();
        final Color color = cosmeticData.getColor();
        for (int i = -1; i <= 1; i += 2) {
            bus.G();
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
            }
            bus.b(0.1, 0.0, 0.0);
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
            if (i == 1 && cosmeticData.getSecondColor() != null) {
                final Color secondColor = cosmeticData.getSecondColor();
                GL11.glColor4f(secondColor.getRed() / 255.0f, secondColor.getGreen() / 255.0f, secondColor.getBlue() / 255.0f, 0.5f);
            }
            this.model.a(scale);
            bus.H();
        }
        bus.q();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 14;
    }
    
    public String getCosmeticName() {
        return "SteampunkWings";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticWingsSteamPunkData extends CosmeticData
    {
        public long lastWalking;
        private Color color;
        private Color secondColor;
        
        public CosmeticWingsSteamPunkData() {
            this.color = new Color(60, 60, 60);
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
        
        public float getAnimationTick(final float walkingSpeed) {
            final long fadeOutAnimationTick = this.lastWalking - System.currentTimeMillis();
            if (walkingSpeed > 0.7) {
                this.lastWalking = System.currentTimeMillis() + 2500L;
            }
            final float fadeOut = 0.00136f * (this.lastWalking - System.currentTimeMillis());
            if (fadeOutAnimationTick < 500L && fadeOutAnimationTick >= 0L) {
                return Math.max(fadeOut, walkingSpeed);
            }
            if (fadeOutAnimationTick <= 0L || walkingSpeed >= 0.7) {
                return walkingSpeed;
            }
            if (fadeOutAnimationTick < 500L) {
                return fadeOut;
            }
            return 0.7f + (float)Math.cos((fadeOutAnimationTick + 900L) / 360.0f) / 20.0f;
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.WINGS;
        }
        
        public long getLastWalking() {
            return this.lastWalking;
        }
        
        public void setLastWalking(final long lastWalking) {
            this.lastWalking = lastWalking;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public Color getSecondColor() {
            return this.secondColor;
        }
    }
}
