//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.layers;

import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.*;

public class LayerCustomCape implements ccg<bua>
{
    private final cct playerRenderer;
    private CapeParticleRenderer capeParticleRenderer;
    
    public LayerCustomCape(final cct playerRendererIn) {
        this.capeParticleRenderer = new CapeParticleRenderer();
        this.playerRenderer = playerRendererIn;
    }
    
    public void doRenderLayer(final bua entitylivingbaseIn, final float var1, final float var2, final float partialTicks, final float var3, final float var4, final float var5, final float scale) {
        if (entitylivingbaseIn.a() && !entitylivingbaseIn.aX() && entitylivingbaseIn.a(aee.a)) {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            final User user = (entitylivingbaseIn == null) ? null : LabyMod.getInstance().getUserManager().getUser(entitylivingbaseIn.bm());
            if ((user != null && !user.canRenderMojangCape(entitylivingbaseIn)) || entitylivingbaseIn.q() == null || LabyModCore.getMinecraft().isWearingElytra((vg)entitylivingbaseIn)) {
                return;
            }
            this.playerRenderer.a(entitylivingbaseIn.q());
            bus.G();
            bus.c(0.0f, 0.0f, 0.125f);
            final double d0 = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
            final double d2 = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
            final double d3 = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
            final float f = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
            final double d4 = LabyModCore.getMath().sin(f * 3.1415927f / 180.0f);
            final double d5 = -LabyModCore.getMath().cos(f * 3.1415927f / 180.0f);
            float f2 = (float)d2 * 10.0f;
            f2 = LabyModCore.getMath().clamp_float(f2, -6.0f, 32.0f);
            float f3 = (float)(d0 * d4 + d3 * d5) * 100.0f;
            final float f4 = (float)(d0 * d5 - d3 * d4) * 100.0f;
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            if (f3 >= 180.0f) {
                f3 = 180.0f + (f3 - 180.0f) * 0.2f;
            }
            final float f5 = entitylivingbaseIn.bB + (entitylivingbaseIn.bC - entitylivingbaseIn.bB) * partialTicks;
            f2 += LabyModCore.getMath().sin((entitylivingbaseIn.I + (entitylivingbaseIn.J - entitylivingbaseIn.I) * partialTicks) * 6.0f) * 32.0f * f5;
            if (entitylivingbaseIn.aU()) {
                f3 += 50.0f;
            }
            boolean swap = LabyMod.getSettings().leftHand;
            final aip itemStack = LabyModCore.getMinecraft().getMainHandItem();
            final int itemId = (itemStack != null && itemStack.c() != null) ? ain.a(itemStack.c()) : 0;
            if (LabyMod.getSettings().swapBow && itemId == 261) {
                swap = !swap;
            }
            if ((swap && LabyModCore.getMinecraft().getItemInUseMaxCount() != 0 && itemId == 261) || (swap && LabyMod.getInstance().isHasLeftHand())) {
                swap = false;
            }
            if (swap) {
                bus.b(-1.0f, 1.0f, 1.0f);
            }
            bus.b(6.0f + f3 / 2.0f + f2, 1.0f, 0.0f, 0.0f);
            bus.b(f4 / 2.0f, 0.0f, 0.0f, 1.0f);
            bus.b(-f4 / 2.0f, 0.0f, 1.0f, 0.0f);
            bus.b(180.0f, 0.0f, 1.0f, 0.0f);
            if (entitylivingbaseIn.aU()) {
                bus.c(0.0f, 0.113f, 0.085f);
            }
            this.playerRenderer.h().b(0.0625f);
            if (swap) {
                bus.b(-1.0f, 1.0f, 1.0f);
            }
            this.capeParticleRenderer.render(user, entitylivingbaseIn, partialTicks);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.H();
        }
    }
    
    public boolean a() {
        return false;
    }
}
