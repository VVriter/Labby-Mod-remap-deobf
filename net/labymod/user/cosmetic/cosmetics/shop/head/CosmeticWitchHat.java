//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import java.util.*;
import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWitchHat extends CosmeticRenderer<CosmeticWitchHatData>
{
    public static final int ID = 7;
    private static final HashMap<String, nf> flags;
    private brs witchHat;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.witchHat = new ModelRendererHook((bqf)modelCosmetics).b(40, 34)).a(-5.0f, -10.03125f, -5.0f);
        this.witchHat.a(0, 0).a(0.0f, 0.0f, 0.0f, 10, 2, 10);
        final brs modelrenderer = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        modelrenderer.a(1.75f, -4.0f, 2.0f);
        modelrenderer.a(0, 12).a(0.0f, 0.0f, 0.0f, 7, 4, 7);
        modelrenderer.f = -0.05235988f;
        modelrenderer.h = 0.02617994f;
        this.witchHat.a(modelrenderer);
        final brs modelrenderer2 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        modelrenderer2.a(1.75f, -4.0f, 2.0f);
        modelrenderer2.a(0, 23).a(0.0f, 0.0f, 0.0f, 4, 4, 4);
        modelrenderer2.f = -0.10471976f;
        modelrenderer2.h = 0.05235988f;
        modelrenderer.a(modelrenderer2);
        final brs modelrenderer3 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        modelrenderer3.a(1.75f, -2.0f, 2.0f);
        modelrenderer3.a(0, 31).a(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        modelrenderer3.f = -0.20943952f;
        modelrenderer3.h = 0.10471976f;
        modelrenderer2.a(modelrenderer3);
    }
    
    public void setInvisible(final boolean invisible) {
        this.witchHat.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWitchHatData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        nf bindedTexture = ModTextures.COSMETIC_WITCH;
        if (cosmeticData.getFlagTexture() != null) {
            if (!CosmeticWitchHat.flags.containsKey(cosmeticData.getFlagTexture())) {
                CosmeticWitchHat.flags.put(cosmeticData.getFlagTexture(), new nf("labymod/textures/cosmetics/flags/" + cosmeticData.getFlagTexture() + ".png"));
            }
            final nf loadedTexture = CosmeticWitchHat.flags.get(cosmeticData.getFlagTexture());
            if (loadedTexture != null) {
                bindedTexture = loadedTexture;
            }
        }
        bib.z().N().a(bindedTexture);
        bus.G();
        final float scaleDown = 0.995f;
        bus.b(scaleDown, scaleDown, scaleDown);
        if (entityIn.aU()) {
            bus.b(0.0, 0.1, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        this.witchHat.a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 7;
    }
    
    public String getCosmeticName() {
        return "Hat";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.5f;
    }
    
    static {
        flags = new HashMap<String, nf>();
    }
    
    public static class CosmeticWitchHatData extends CosmeticData
    {
        private String flagTexture;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.flagTexture = ((data[0] == null || data[0].isEmpty()) ? null : data[0].toLowerCase());
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public String getFlagTexture() {
            return this.flagTexture;
        }
    }
}
