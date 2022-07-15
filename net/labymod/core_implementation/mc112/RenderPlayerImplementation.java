//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core_implementation.mc112.layer.*;
import net.labymod.user.cosmetic.layers.*;
import net.labymod.mojang.*;
import net.labymod.main.*;
import net.labymod.core_implementation.mc112.util.*;
import net.labymod.utils.manager.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import net.labymod.user.*;
import net.labymod.user.group.*;
import net.labymod.core.*;

public class RenderPlayerImplementation implements RenderPlayerAdapter
{
    public String[] getSkinMapNames() {
        return new String[] { "skinMap", "l", "skinMap" };
    }
    
    public ccg[] getLayerRenderers(final cct renderPlayer) {
        return new ccg[] { (ccg)new LayerBipedArmorCustom((caa)renderPlayer), (ccg)new ccc((caa)renderPlayer), (ccg)new LayerArrowCustom((caa)renderPlayer), (ccg)new cbv(renderPlayer), (ccg)new LayerCustomCape(renderPlayer), (ccg)new cbw((caa)renderPlayer), (ccg)new cbu(renderPlayer.h().e), (ccg)new cca(renderPlayer.e()) };
    }
    
    public void renderName(final RenderPlayerHook.RenderPlayerCustom renderPlayer, final bua entity, final double x, double y, final double z) {
        if (LabyMod.isForge()) {
            ForgeBridge.preRenderLivingSpecials(renderPlayer, entity, x, y, z);
        }
        final boolean canRender = bib.w() && !entity.e((aed)bib.z().h) && entity.bJ() == null;
        if (renderPlayer.canRenderTheName(entity) || (entity == renderPlayer.e().c && LabyMod.getSettings().showMyName && canRender)) {
            final double distance = entity.c(renderPlayer.e().c.c());
            final float f = entity.aU() ? 32.0f : 64.0f;
            if (distance < f * f) {
                final User user = (entity instanceof aed) ? LabyMod.getInstance().getUserManager().getUser(entity.bm()) : null;
                final float maxNameTagHeight = (user == null || !LabyMod.getSettings().cosmetics) ? 0.0f : user.getMaxNameTagHeight();
                String username = entity.i_().d();
                bus.a(516, 0.1f);
                final String tagName = TagManager.getTaggedMessage(username);
                if (tagName != null) {
                    username = tagName;
                }
                y += maxNameTagHeight;
                final bip fontrenderer = renderPlayer.d();
                if (entity.aU()) {
                    bus.G();
                    bus.c((float)x, (float)y + entity.H + 0.5f - (entity.l_() ? (entity.H / 2.0f) : 0.0f) + maxNameTagHeight, (float)z);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    bus.b(-renderPlayer.e().e, 0.0f, 1.0f, 0.0f);
                    bus.b(renderPlayer.e().f, 1.0f, 0.0f, 0.0f);
                    bus.b(-0.02666667f, -0.02666667f, 0.02666667f);
                    bus.c(0.0f, 9.374999f, 0.0f);
                    bus.g();
                    bus.a(false);
                    bus.m();
                    bus.z();
                    bus.a(770, 771, 1, 0);
                    final int i = fontrenderer.a(username) / 2;
                    final bve tessellator = bve.a();
                    final buk worldrenderer = tessellator.c();
                    worldrenderer.a(7, cdy.f);
                    worldrenderer.b((double)(-i - 1), -1.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                    worldrenderer.b((double)(-i - 1), 8.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                    worldrenderer.b((double)(i + 1), 8.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                    worldrenderer.b((double)(i + 1), -1.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                    tessellator.b();
                    bus.y();
                    bus.a(true);
                    fontrenderer.a(username, -fontrenderer.a(username) / 2, 0, 553648127);
                    bus.f();
                    bus.l();
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.H();
                }
                else {
                    final LabyGroup labyGroup = user.getGroup();
                    if (user.getSubTitle() != null) {
                        bus.G();
                        final double size = user.getSubTitleSize();
                        bus.b(0.0, -0.2 + size / 8.0, 0.0);
                        this.renderLivingLabelCustom(renderPlayer, (vg)entity, user.getSubTitle(), x, y, z, 64, (float)size);
                        y += size / 6.0;
                        bus.H();
                    }
                    renderPlayer.renderLabel(entity, x, y - (entity.l_() ? (entity.H / 2.0f) : 0.0), z, username, 0.02666667f, distance);
                    if (distance < 100.0) {
                        final bhk scoreboard = entity.dn();
                        final bhg scoreobjective = scoreboard.a(2);
                        if (scoreobjective != null) {
                            y += LabyMod.getInstance().getDrawUtils().getFontRenderer().a * 1.15f * 0.02666667f;
                        }
                    }
                    if (labyGroup != null && labyGroup.getDisplayType() == EnumGroupDisplayType.BESIDE_NAME) {
                        bus.G();
                        final float fixedPlayerViewX = renderPlayer.e().f * ((bib.z().t.aw == 2) ? -1 : 1);
                        bus.c((float)x, (float)y + entity.H + 0.5f - (entity.l_() ? (entity.H / 2.0f) : 0.0f), (float)z);
                        bus.b(-renderPlayer.e().e, 0.0f, 1.0f, 0.0f);
                        bus.b(fixedPlayerViewX, 1.0f, 0.0f, 0.0f);
                        bus.b(-0.02666667f, -0.02666667f, 0.02666667f);
                        bus.g();
                        final double pos = -fontrenderer.a(username) / 2 - 2 - 8;
                        labyGroup.renderBadge(pos, -0.5, 8.0, 8.0, false);
                        bus.f();
                        bus.H();
                    }
                    if (tagName != null) {
                        bus.G();
                        bus.c((float)x, (float)y + entity.H + 0.5f - (entity.l_() ? (entity.H / 2.0f) : 0.0f), (float)z);
                        bus.b(-renderPlayer.e().e, 0.0f, 1.0f, 0.0f);
                        bus.b(renderPlayer.e().f, 1.0f, 0.0f, 0.0f);
                        bus.b(-0.01666667f, -0.01666667f, 0.01666667f);
                        bus.c(0.0f, entity.aU() ? 17.0f : 2.0f, 0.0f);
                        bus.g();
                        bus.m();
                        fontrenderer.a("\u270e", 5 + (int)(fontrenderer.a(username) * 0.8), 0, ModColor.toRGB(255, 255, 0, 255));
                        bus.l();
                        bus.f();
                        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                        bus.H();
                    }
                    if (labyGroup != null && labyGroup.getDisplayType() == EnumGroupDisplayType.ABOVE_HEAD && !entity.cP()) {
                        bus.G();
                        final double size = 0.5;
                        bus.a(size, size, size);
                        bus.b(0.0, 2.0, 0.0);
                        this.renderLivingLabelCustom(renderPlayer, (vg)entity, labyGroup.getDisplayTag(), x / size, (y - (entity.l_() ? (entity.H / 2.0f) : 0.0) + 0.3) / size, z / size, 10);
                        bus.H();
                    }
                }
            }
        }
        if (LabyMod.isForge()) {
            ForgeBridge.postRenderLivingSpecials(renderPlayer, entity, x, y, z);
        }
    }
    
    protected void renderLivingLabelCustom(final RenderPlayerHook.RenderPlayerCustom renderPlayer, final vg entityIn, final String str, final double x, final double y, final double z, final int maxDistance) {
        this.renderLivingLabelCustom(renderPlayer, entityIn, str, x, y, z, maxDistance, 1.6f);
    }
    
    protected void renderLivingLabelCustom(final RenderPlayerHook.RenderPlayerCustom renderPlayer, final vg entityIn, final String str, final double x, final double y, final double z, final int maxDistance, final float size) {
        final double d0 = entityIn.c(renderPlayer.e().c.c());
        if (d0 <= maxDistance * maxDistance) {
            final bip fontrenderer = renderPlayer.d();
            final float f1 = 0.016666668f * size;
            bus.G();
            bus.c((float)x + 0.0f, (float)y + entityIn.H + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            bus.b(-renderPlayer.e().e, 0.0f, 1.0f, 0.0f);
            bus.b(renderPlayer.e().f, 1.0f, 0.0f, 0.0f);
            bus.b(-f1, -f1, f1);
            bus.g();
            bus.a(false);
            bus.j();
            bus.m();
            bus.a(770, 771, 1, 0);
            final bve tessellator = bve.a();
            final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
            int i = 0;
            if (str.equals("deadmau5")) {
                i = -10;
            }
            final int j = fontrenderer.a(str) / 2;
            bus.z();
            worldrenderer.begin(7, cdy.f);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.b();
            bus.y();
            fontrenderer.a(str, -fontrenderer.a(str) / 2, i, 553648127);
            bus.k();
            bus.a(true);
            fontrenderer.a(str, -fontrenderer.a(str) / 2, i, -1);
            bus.f();
            bus.l();
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.H();
        }
    }
    
    public RenderPlayerHook.RenderPlayerCustom getRenderPlayer(final bzf renderManager, final boolean slim) {
        return new RenderPlayerHook.RenderPlayerCustom(renderManager, slim) {
            @Override
            public boolean canRenderTheName(final bua entity) {
                return super.a((vp)entity);
            }
            
            @Override
            public void renderLabel(final bua entityIn, final double x, final double y, final double z, final String string, final float height, final double distance) {
                super.a(entityIn, x, y, z, string, distance);
            }
            
            public void renderName(final bua entity, final double x, final double y, final double z) {
                LabyModCore.getRenderPlayerImplementation().renderName((RenderPlayerHook.RenderPlayerCustom)this, entity, x, y, z);
            }
        };
    }
}
