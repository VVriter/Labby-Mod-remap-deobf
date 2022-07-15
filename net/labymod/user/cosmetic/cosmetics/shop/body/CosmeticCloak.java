//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticCloak extends CosmeticRenderer<CosmeticCloakData>
{
    public static final int ID = 0;
    private brs cloak;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 22;
        final int height = 17;
        (this.cloak = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(width, height).a(0, 0)).a(-5.0f, 0.0f, -1.0f, 10, 16, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.cloak.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticCloakData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final User user = userManager.getUser(entityIn.bm());
        final boolean mojangCapeVisible = user.canRenderMojangCape((bua)entityIn);
        final boolean wearingCape = ((bua)entityIn).a(aee.a);
        if (mojangCapeVisible || !wearingCape || LabyModCore.getMinecraft().isWearingElytra(entityIn)) {
            return;
        }
        final nf cloakLocation = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getCloakImageHandler().getResourceLocation((bua)entityIn);
        if (cloakLocation == null) {
            return;
        }
        bib.z().N().a(cloakLocation);
        bus.G();
        bus.c(0.0f, 0.0f, 0.125f);
        bus.r();
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double yawSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double yawCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float cloakRotation = (float)motionY * 10.0f;
        cloakRotation = LabyModCore.getMath().clamp_float(cloakRotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
        float motionSub = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        motionAdd = LabyModCore.getMath().clamp_float(motionAdd, 0.0f, 150.0f);
        motionSub = LabyModCore.getMath().clamp_float(motionSub, -20.0f, 20.0f);
        final float cameraMotionYaw = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
        cloakRotation += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * cameraMotionYaw;
        if (entitylivingbaseIn.aU()) {
            cloakRotation += 25.0f;
        }
        bus.b(6.0f + motionAdd / 2.0f + cloakRotation, 1.0f, 0.0f, 0.0f);
        bus.b(motionSub / 2.0f, 0.0f, 0.0f, 1.0f);
        bus.b(-motionSub / 2.0f, 0.0f, 1.0f, 0.0f);
        bus.b(180.0f, 0.0f, 1.0f, 0.0f);
        this.cloak.a(scale);
        bus.q();
        bus.H();
    }
    
    public void setRotationAngles(final ModelCosmetics modelCosmetics, final float movementFactor, final float walkingSpeed, final float tickValue, final float var4, final float var5, final float var6, final vg entityIn) {
        this.cloak.d = (entityIn.aU() ? -1.0f : 0.0f);
    }
    
    public int getCosmeticId() {
        return 0;
    }
    
    public String getCosmeticName() {
        return "Cloak";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticCloakData extends CosmeticData
    {
        @Override
        public boolean isEnabled() {
            return LabyMod.getSettings().cosmeticsCustomTextures;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
    }
}
