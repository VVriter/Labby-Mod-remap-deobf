//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.staff;

import net.labymod.user.cosmetic.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticMoehritz extends CosmeticRenderer<CosmeticMoehritzData>
{
    public static final int ID = 4;
    private boolean mc18;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        this.mc18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
    
    public void setInvisible(final boolean invisible) {
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticMoehritzData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        if (!this.mc18) {
            return;
        }
        bus.G();
        final bua player = (bua)entityIn;
        LabyModCore.getMinecraft().setSecondLayerBit(player, 6, (byte)0);
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.f();
        bus.l();
        bus.d();
        bus.b(-0.25, -0.5, -0.26);
        bus.a(0.0625, 0.0625, 1.0);
        bus.b(180.0f, 1.0f, 0.0f, 0.0f);
        bus.b(180.0f, 0.0f, 0.0f, 1.0f);
        bus.b(-8.0, 0.0, 0.004);
        int f = (int)(55.0f - (90.0f + entityIn.w));
        if (f < 0) {
            f = 0;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.b(0.0, 0.0, entityIn.aU() ? -0.008 : -0.011);
        draw.drawRect(1.899999976158142, 5.900000095367432, 6.099999904632568, 8.0, ModColor.toRGB(242 - f, 215 - f, 159 - f, 255));
        final int colorBrow = ModColor.toRGB(73, 33, 2, 255);
        final int colorEye = ModColor.toRGB(150, 0, 0, 255);
        final int colorMouth = ModColor.toRGB(30, 30, 30, 255);
        DrawUtils.a(1, 3, 3, 4, colorBrow);
        DrawUtils.a(5, 3, 7, 4, colorBrow);
        vg targetEntity = bib.z().aa();
        if (cosmeticData.targetPlayerUUID != null) {
            if (cosmeticData.lastTargetEntityCheck + 5000L < System.currentTimeMillis()) {
                cosmeticData.lastTargetEntityCheck = System.currentTimeMillis();
                cosmeticData.targetEntity = (vg)LabyModCore.getMinecraft().getWorld().b(cosmeticData.targetPlayerUUID);
            }
            if (cosmeticData.targetEntity != null) {
                targetEntity = cosmeticData.targetEntity;
            }
        }
        double eyeX = 2.0;
        double movement = LabyModCore.getMinecraft().calculateEyeMovement(entityIn, targetEntity);
        if (movement > 0.5) {
            movement = 0.5;
        }
        if (movement < -0.5) {
            movement = -0.5;
        }
        eyeX -= movement;
        final int eyeY = 4;
        for (int i = 0; i < 2; ++i) {
            draw.drawRect(eyeX - 0.5, eyeY, eyeX + 0.5, eyeY + 1, colorEye);
            eyeX += 4.0;
        }
        double smile = 0.0;
        if (targetEntity != null) {
            final double distanceSq = targetEntity.d(entityIn.p, entityIn.q, entityIn.r);
            smile += distanceSq / 50.0;
            if (smile > 1.0) {
                smile = 1.0;
            }
        }
        bus.b(0.0, 0.0, 0.002);
        draw.drawRect(2.0, 6.0 + smile, 3.0, 7.0 + smile, colorMouth);
        draw.drawRect(5.0, 6.0 + smile, 6.0, 7.0 + smile, colorMouth);
        draw.drawRect(3.0, 7.0 - smile, 5.0, 8.0 - smile, colorMouth);
        bus.f();
        bus.e();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 4;
    }
    
    public String getCosmeticName() {
        return "Moehritz";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticMoehritzData extends CosmeticData
    {
        protected UUID targetPlayerUUID;
        protected vg targetEntity;
        protected long lastTargetEntityCheck;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.targetPlayerUUID = UUID.fromString(data[0]);
        }
        
        @Override
        public void init(final User user) {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
    }
}
