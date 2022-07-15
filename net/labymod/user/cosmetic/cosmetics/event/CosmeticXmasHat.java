//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.event;

import net.labymod.user.cosmetic.*;
import net.labymod.core.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticXmasHat extends CosmeticRenderer<CosmeticXmasHatData>
{
    public static final int ID = 16;
    private static float LOCAL_XMAS_YAW;
    private static float LOCAL_XMAS_TICK_VALUE;
    private static float LOCAL_XMAS_FPS_VALUE;
    private brs xmasHat;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.xmasHat = new ModelRendererHook((bqf)modelCosmetics).b(40, 34)).a(-5.0f, -10.03125f, -5.0f);
        this.xmasHat.a(0, 0).a(0.0f, 0.0f, 0.0f, 10, 2, 10);
        final brs xmasLayer2 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer2.a(4.0f, -2.7f, 4.0f);
        xmasLayer2.a(0, 12).a(-3.0f, 0.0f, -3.0f, 8, 3, 8);
        xmasLayer2.h = 0.1f;
        this.xmasHat.a(xmasLayer2);
        final brs xmasLayer3 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer3.a(1.0f, -1.7f, 1.0f);
        xmasLayer3.a(0, 12).a(-3.0f, 0.0f, -3.0f, 6, 2, 6);
        xmasLayer3.h = 0.1f;
        xmasLayer2.a(xmasLayer3);
        final brs xmasLayer4 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer4.a(1.0f, -2.0f, 0.0f);
        xmasLayer4.a(0, 12).a(-1.0f, 0.0f, -2.0f, 4, 4, 4);
        xmasLayer4.h = 0.6f;
        xmasLayer3.a(xmasLayer4);
        final brs xmasLayer5 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer5.a(2.0f, -3.0f, 0.0f);
        xmasLayer5.a(0, 12).a(-2.0f, 1.4f, -1.5f, 3, 2, 3);
        xmasLayer5.h = 0.2f;
        xmasLayer4.a(xmasLayer5);
        final brs xmasLayer6 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer6.a(0.0f, 0.0f, 0.0f);
        xmasLayer6.a(0, 12).a(-0.5f, 0.5f, -1.0f, 4, 2, 2);
        xmasLayer6.h = -0.4f;
        xmasLayer5.a(xmasLayer6);
        final brs xmasLayer7 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer7.a(0.0f, 0.0f, 0.0f);
        xmasLayer7.a(0, 12).a(3.5f, -0.5f, -0.5f, 3, 1, 1);
        xmasLayer7.h = 0.8f;
        xmasLayer6.a(xmasLayer7);
        final brs xmasLayer8 = new ModelRendererHook((bqf)modelCosmetics).b(40, 34);
        xmasLayer8.a(0.0f, 0.0f, 0.0f);
        xmasLayer8.a(0, 0).a(5.0f, -1.2f, -1.0f, 2, 2, 2);
        xmasLayer8.h = 0.05f;
        xmasLayer7.a(xmasLayer8);
    }
    
    public void setInvisible(final boolean invisible) {
        this.xmasHat.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticXmasHatData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final bua clientPlayer = (bua)entityIn;
        bib.z().N().a(ModTextures.COSMETIC_XMAS);
        bus.G();
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(0.95f, 0.95f, 0.95f);
        final bua entity = (bua)entityIn;
        final double d0 = entity.bE + (entity.bH - entity.bE) * partialTicks - (entity.m + (entity.p - entity.m) * partialTicks);
        final double d2 = entity.bF + (entity.bI - entity.bF) * partialTicks - (entity.n + (entity.q - entity.n) * partialTicks);
        final double d3 = entity.bG + (entity.bJ - entity.bG) * partialTicks - (entity.o + (entity.r - entity.o) * partialTicks);
        final float ft = entity.aO + (entity.aN - entity.aO) * partialTicks;
        final double d4 = LabyModCore.getMath().sin(ft * 3.1415927f / 180.0f);
        final double d5 = -LabyModCore.getMath().cos(ft * 3.1415927f / 180.0f);
        float k1 = (float)d2 * 10.0f;
        k1 = LabyModCore.getMath().clamp_float(k1, -40.0f, 12.0f);
        final float f2t = (float)(d0 * d4 + d3 * d5) * 100.0f;
        final float pitch = clientPlayer.y + (clientPlayer.w - clientPlayer.y) * partialTicks;
        final float zFloat = (90.0f - Math.abs(pitch)) / 100.0f;
        float walkFloat = (f2t > 120.0f) ? 120.0f : f2t;
        float fallFloat = (float)((d2 / 3.0 > 0.7) ? -0.7 : (-d2 / 3.0));
        final float shakingFloat = (float)Math.cos(tickValue / 10.0f) / 40.0f;
        final float bf = ((aed)entity).J - ((aed)entity).I;
        final float bf2 = -(((aed)entity).J + bf * partialTicks);
        final float bf3 = ((aed)entity).bB + (((aed)entity).bC - ((aed)entity).bB) * partialTicks;
        walkFloat += Math.abs(LabyModCore.getMath().cos(bf2 * 3.1415927f - 0.2f) * bf3) * 70.0f;
        float centrifugalPoint = 0.0f;
        if (entity == LabyModCore.getMinecraft().getPlayer()) {
            final float speedValue = 3.0f / (bib.af() + 1);
            if (CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE < CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE) {
                CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE += speedValue;
            }
            if (CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE > CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE) {
                CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE -= speedValue;
            }
            final float centrifugal = -CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE;
            centrifugalPoint = centrifugal / -1.5f;
            fallFloat += centrifugal;
        }
        final brs ch1 = this.xmasHat.m.get(0);
        final brs ch2 = ch1.m.get(0);
        ch2.g = pitch / 300.0f - walkFloat / 200.0f;
        ch2.h = 0.1f + fallFloat / 2.0f;
        final brs ch3 = ch2.m.get(0);
        ch3.g = pitch / 200.0f;
        ch2.h = 0.1f + fallFloat / 4.0f;
        final brs ch4 = ch3.m.get(0);
        ch4.g = pitch / 100.0f - walkFloat / 100.0f;
        final brs ch5 = ch4.m.get(0);
        ch5.h = fallFloat;
        ch5.g = shakingFloat;
        ch5.d = centrifugalPoint;
        final brs ch6 = ch5.m.get(0);
        ch6.h = zFloat - 0.3f;
        ch6.d = 3.0f - zFloat * 4.0f;
        final brs ch7 = ch6.m.get(0);
        ch7.h = shakingFloat / -2.0f;
        ch7.g = shakingFloat / 4.0f;
        this.xmasHat.a(scale);
        bus.H();
    }
    
    public void onTick() {
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return;
        }
        final UUID uuid = LabyMod.getInstance().getPlayerUUID();
        final User user = LabyMod.getInstance().getUserManager().getUser(uuid);
        final boolean hasXmasCos = user.hasCosmeticById(16);
        if (!hasXmasCos) {
            return;
        }
        final float pos = LabyModCore.getMinecraft().getPlayer().v;
        if (pos != CosmeticXmasHat.LOCAL_XMAS_YAW) {
            CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE += Math.abs(pos - CosmeticXmasHat.LOCAL_XMAS_YAW) / 190.0f;
        }
        if (CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE > 0.0f) {
            CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE -= 0.15f;
        }
        if (CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE > 1.0f) {
            CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE = 1.0f;
        }
        if (CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE < 0.0f) {
            CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE = 0.0f;
        }
        CosmeticXmasHat.LOCAL_XMAS_YAW = pos;
    }
    
    public int getCosmeticId() {
        return 16;
    }
    
    public String getCosmeticName() {
        return "Xmas Hat";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public float getNameTagHeight() {
        return 0.5f;
    }
    
    static {
        CosmeticXmasHat.LOCAL_XMAS_YAW = 0.0f;
        CosmeticXmasHat.LOCAL_XMAS_TICK_VALUE = 0.0f;
        CosmeticXmasHat.LOCAL_XMAS_FPS_VALUE = 0.0f;
    }
    
    public static class CosmeticXmasHatData extends CosmeticData
    {
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.HAT;
        }
    }
}
