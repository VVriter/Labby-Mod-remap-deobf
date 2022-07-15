//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.layer;

import net.minecraftforge.fml.relauncher.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class LayerArrowCustom implements ccg<vp>
{
    private final caa<?> renderer;
    
    public LayerArrowCustom(final caa<?> rendererIn) {
        this.renderer = rendererIn;
    }
    
    public void a(final vp entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final int i = entitylivingbaseIn.ck();
        if (i > 0) {
            final vg entity = (vg)new afa(entitylivingbaseIn.l, entitylivingbaseIn.p, entitylivingbaseIn.q, entitylivingbaseIn.r);
            final Random random = new Random(entitylivingbaseIn.S());
            bhz.a();
            for (int j = 0; j < i; ++j) {
                final brs modelrenderer = this.renderer.b().a(random);
                if (!modelrenderer.k) {
                    if (modelrenderer.l.size() != 0) {
                        bus.G();
                        final brq modelbox = modelrenderer.l.get(random.nextInt(modelrenderer.l.size()));
                        modelrenderer.c(0.0625f);
                        float f = random.nextFloat();
                        float f2 = random.nextFloat();
                        float f3 = random.nextFloat();
                        final float f4 = (modelbox.a + (modelbox.d - modelbox.a) * f) / 16.0f;
                        final float f5 = (modelbox.b + (modelbox.e - modelbox.b) * f2) / 16.0f;
                        final float f6 = (modelbox.c + (modelbox.f - modelbox.c) * f3) / 16.0f;
                        bus.c(f4, f5, f6);
                        f = f * 2.0f - 1.0f;
                        f2 = f2 * 2.0f - 1.0f;
                        f3 = f3 * 2.0f - 1.0f;
                        f *= -1.0f;
                        f2 *= -1.0f;
                        f3 *= -1.0f;
                        final float f7 = rk.c(f * f + f3 * f3);
                        entity.v = (float)(Math.atan2(f, f3) * 57.29577951308232);
                        entity.w = (float)(Math.atan2(f2, f7) * 57.29577951308232);
                        entity.x = entity.v;
                        entity.y = entity.w;
                        this.renderer.e().a(entity, 0.0, 0.0, 0.0, 0.0f, partialTicks, false);
                        bus.H();
                    }
                }
            }
            bhz.b();
        }
    }
    
    public boolean a() {
        return false;
    }
}
