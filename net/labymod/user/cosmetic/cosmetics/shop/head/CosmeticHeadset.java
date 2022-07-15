//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticHeadset extends CosmeticRenderer<CosmeticHeadsetData>
{
    public static final int ID = 23;
    private brs earCup;
    private brs headBandSide;
    private brs headBandTop;
    private brs mic;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 18;
        final int height = 7;
        (this.earCup = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-1.5f, -1.5f, 0.0f, 3, 3, 1, modelSize);
        (this.headBandSide = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(8, 0)).a(-0.5f, -4.0f, 0.0f, 1, 3, 1, modelSize);
        (this.headBandTop = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 5)).a(-4.0f, 0.0f, -2.0f, 8, 1, 1, modelSize);
        (this.mic = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(12, 0)).a(-0.5f, -4.0f, 0.0f, 1, 4, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.earCup.j = invisible;
        this.headBandSide.j = invisible;
        this.headBandTop.j = invisible;
        this.mic.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticHeadsetData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bib.z().N().a(ModTextures.COSMETIC_HEADSET);
        bus.G();
        bus.b(1.2f, 1.2f, 1.2f);
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(0.0, -0.3, 0.1);
        final double distanceToMid = 0.21;
        final double earCupWidth = 0.1;
        final double earCupModuleScale = 0.6;
        final double headBandSideHight = -0.0317;
        bus.q();
        for (int i = -1; i < 2; i += 2) {
            bus.G();
            bus.b(90.0f, 0.0f, 1.0f, 0.0f);
            if (i == 1) {
                bus.b(1.0f, 1.0f, -1.0f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            bus.b(0.1, 0.1, distanceToMid);
            bus.G();
            bus.b(0.0, headBandSideHight, 0.0);
            this.headBandSide.a(scale);
            if (cosmeticData.isMic() && i == -1) {
                bus.b(0.028, -headBandSideHight + 0.05, 0.0);
                bus.a(0.8, 0.8, 0.8);
                bus.b(120.0f, 0.0f, 0.0f, 1.0f);
                this.headBandSide.a(scale);
                bus.a(0.65, 0.65, 0.65);
                bus.b(0.01, -0.37, 0.08);
                bus.b(-30.0f, 0.0f, 0.0f, 1.0f);
                bus.b(-60.0f, -1.0f, 0.0f, 0.0f);
                this.mic.a(scale);
            }
            bus.H();
            this.earCup.a(scale);
            bus.a(earCupModuleScale, earCupModuleScale, earCupModuleScale);
            bus.b(0.0, 0.0, earCupWidth);
            bus.b(45.0f, 0.0f, 0.0f, 1.0f);
            this.earCup.a(scale);
            bus.H();
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.G();
        bus.b(0.0, -0.1817, -0.0063);
        bus.a(0.83999, 1.0, 1.0);
        this.headBandTop.a(scale);
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 23;
    }
    
    public String getCosmeticName() {
        return "Headset";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticHeadsetData extends CosmeticData
    {
        private boolean mic;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.mic = (Integer.parseInt(data[0]) == 1);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
        
        public boolean isMic() {
            return this.mic;
        }
    }
}
