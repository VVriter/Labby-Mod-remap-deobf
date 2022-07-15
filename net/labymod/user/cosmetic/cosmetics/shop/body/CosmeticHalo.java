//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticHalo extends CosmeticRenderer<CosmeticHaloData>
{
    public static final int ID = 9;
    private brs halo;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.halo = new ModelRendererHook((bqf)modelCosmetics).b(14, 2)).a(-3.0f, -12.5f, -4.0f, 6, 1, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.halo.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticHaloData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        final float bounceAnimation = (float)Math.cos(tickValue / 10.0) / 20.0f;
        bus.b(firstRotationX + tickValue / 2.0f, 0.0f, 1.0f, 0.0f);
        bus.c(0.0f, bounceAnimation - (cosmeticData.isHat() ? 0.4f : 0.0f), 0.0f);
        bib.z().N().a(ModTextures.COSMETIC_HALO);
        bus.g();
        final brs targetModel = this.bindTextureAndColor(cosmeticData.getColor(), ModTextures.COSMETIC_HALO, this.halo);
        for (int i = 0; i < 4; ++i) {
            targetModel.a(scale);
            bus.b(90.0f, 0.0f, 1.0f, 0.0f);
        }
        bus.f();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 9;
    }
    
    public String getCosmeticName() {
        return "Halo";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.2f;
    }
    
    public static class CosmeticHaloData extends CosmeticData
    {
        private Color color;
        private boolean hat;
        
        public CosmeticHaloData() {
            this.color = Color.YELLOW;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) {
            this.color = Color.decode("#" + data[0]);
        }
        
        @Override
        public void completed(final User user) {
            final Map<Integer, CosmeticData> cosmetics = user.getCosmetics();
            this.hat = (cosmetics.containsKey(16) || cosmetics.containsKey(7) || cosmetics.containsKey(39) || cosmetics.containsKey(37) || cosmetics.containsKey(40));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isHat() {
            return this.hat;
        }
    }
}
