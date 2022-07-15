//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.shoes;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticBunnyShoes extends CosmeticRenderer<CosmeticBunnyShoesData>
{
    public static final int ID = 38;
    private brs shoeRight;
    private brs shoeLeft;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 23;
        final int height = 26;
        final float earDistance = 1.5f;
        final brs shoeFront = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 20);
        shoeFront.a(-2.5f, 8.0f, -3.5f, 5, 4, 2, modelSize);
        shoeFront.a(0.0f, 1.0f, -1.0f);
        final brs bunnyEar = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(14, 20);
        bunnyEar.a(-0.0f, 0.0f, 0.0f, 1, 4, 1, modelSize);
        bunnyEar.a(earDistance, 9.5f, -3.0f);
        shoeFront.a(bunnyEar);
        final brs bunnyEar2 = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(14, 20);
        bunnyEar2.a(4.0f, 0.0f, 0.0f, 1, 4, 1, modelSize);
        bunnyEar2.a(5.0f - earDistance, 9.5f, -3.0f);
        shoeFront.a(bunnyEar2);
        final brs bunnyNose = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(15, 0);
        bunnyNose.a(-1.5f, 8.8f, -3.0f, 3, 2, 1, modelSize);
        bunnyNose.a(0.0f, 1.0f, -1.0f);
        shoeFront.a(bunnyNose);
        (this.shoeRight = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-2.5f, 8.0f, -2.5f, 5, 5, 5, modelSize);
        this.shoeRight.a(-1.9f, 12.0f, 0.0f);
        this.shoeRight.a(shoeFront);
        (this.shoeLeft = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 10)).a(-2.5f, 8.0f, -2.5f, 5, 5, 5, modelSize);
        this.shoeLeft.a(1.9f, 12.001f, 0.0f);
        this.shoeLeft.a(shoeFront);
    }
    
    public void setInvisible(final boolean invisible) {
        this.shoeRight.j = invisible;
        this.shoeLeft.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticBunnyShoesData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf textureDesign = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getBunnyShoesImageHandler().getResourceLocation((bua)entityIn);
        if (textureDesign == null) {
            return;
        }
        bus.G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bib.z().N().a(textureDesign);
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double yawSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double yawCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
        final float motionSub = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        if (motionAdd < 0.0f) {
            motionAdd = 0.0f;
        }
        if (motionAdd >= 130.0f) {
            motionAdd = 130.0f + (motionAdd - 180.0f) * 0.2f;
        }
        final List<brs> childs = (List<brs>)this.shoeRight.m.get(0).m;
        final brs ear1 = childs.get(0);
        final brs ear2 = childs.get(1);
        final float earMotionSub = -motionSub / 100.0f;
        final float earMotionAdd = motionAdd / 200.0f;
        ear1.h = (float)(earMotionSub + 3.141592653589793);
        ear2.h = (float)(earMotionSub + 3.141592653589793);
        ear1.f = earMotionAdd;
        ear2.f = earMotionAdd;
        bpx.a(modelCosmetics.j, this.shoeRight);
        bpx.a(modelCosmetics.k, this.shoeLeft);
        this.shoeRight.a(scale);
        bus.b(0.0, 0.0, 1.0E-4);
        this.shoeLeft.a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 38;
    }
    
    public String getCosmeticName() {
        return "Bunny Shoes";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticBunnyShoesData extends CosmeticData
    {
        private UserTextureContainer userTextureContainer;
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getBunnyShoesContainer();
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.userTextureContainer.setFileName(UUID.fromString(data[0]));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.LEG;
        }
    }
}
