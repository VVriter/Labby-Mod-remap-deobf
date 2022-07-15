//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import java.awt.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticAntlers extends CosmeticRenderer<CosmeticAntlersData>
{
    public static final int ID = 10;
    private brs antler;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.antler = new ModelRendererHook((bqf)modelCosmetics).b(4, 8)).a(-3.0f, -7.5f, -1.0f);
        this.antler.a(0.0f, 0.0f, 0.0f, 1, 5, 1);
        this.antler.f = 3.1415927f;
        this.antler.h = -0.8f;
        final brs firstTip = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        firstTip.a(0.0f, 5.0f, 0.0f);
        firstTip.a(0.0f, 0.0f, 0.0f, 1, 5, 1);
        firstTip.h = -0.6f;
        firstTip.f = -0.2f;
        this.antler.a(firstTip);
        final brs secondTip = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        secondTip.a(0.0f, 3.0f, 0.0f);
        secondTip.a(0.0f, 0.0f, 0.0f, 1, 5, 1);
        secondTip.h = 0.2f;
        secondTip.f = 0.4f;
        this.antler.a(secondTip);
        final brs firstHook = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        firstHook.a(0.0f, 5.0f, 0.0f);
        firstHook.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        firstHook.h = -0.8f;
        firstHook.f = 0.4f;
        firstTip.a(firstHook);
        final brs secondHook = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        secondHook.a(0.0f, 5.0f, 0.0f);
        secondHook.a(0.0f, 0.0f, 0.0f, 1, 3, 1);
        secondHook.h = -0.8f;
        secondHook.f = -0.4f;
        secondTip.a(secondHook);
        final brs mainHook = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        mainHook.a(0.0f, 2.0f, 0.0f);
        mainHook.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        mainHook.h = -0.6f;
        mainHook.f = -0.2f;
        this.antler.a(mainHook);
        final brs besideHook = new ModelRendererHook((bqf)modelCosmetics).b(4, 8);
        besideHook.a(0.0f, 2.0f, 0.0f);
        besideHook.a(0.0f, 0.0f, 0.0f, 1, 2, 1);
        besideHook.h = 0.6f;
        besideHook.f = 0.2f;
        secondTip.a(besideHook);
    }
    
    public void setInvisible(final boolean invisible) {
        this.antler.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticAntlersData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        final brs antler = this.bindTextureAndColor(Color.WHITE, ModTextures.COSMETIC_ANTLER, this.antler);
        for (int i = 0; i < 2; ++i) {
            bus.q();
            antler.a(scale);
            bus.b(-1.0f, 1.0f, 1.0f);
            if (i == 0) {
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 10;
    }
    
    public String getCosmeticName() {
        return "Antlers";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.5f;
    }
    
    public static class CosmeticAntlersData extends CosmeticData
    {
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
    }
}
