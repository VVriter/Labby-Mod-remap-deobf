//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import java.util.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.opengl.*;

public class RenderImplementation extends bir implements RenderAdapter
{
    private bzw renderItem;
    private static nf inventoryBackground;
    private static final nf buttonTextures;
    
    public RenderImplementation() {
        this.renderItem = bib.z().ad();
    }
    
    public nf getOptionsBackground() {
        return bir.b;
    }
    
    public nf getInventoryBackground() {
        return RenderImplementation.inventoryBackground;
    }
    
    public nf getButtonsTexture() {
        return RenderImplementation.buttonTextures;
    }
    
    public nf getIcons() {
        return bir.d;
    }
    
    public void drawActivePotionEffects(final double guiLeft, final double guiTop, final nf inventoryBackground) {
        final double i = guiLeft - 124.0;
        double j = guiTop;
        final Collection<va> collection = (Collection<va>)LabyModCore.getMinecraft().getPlayer().ca();
        if (!collection.isEmpty()) {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.g();
            int l = 33;
            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }
            for (final va potioneffect : LabyModCore.getMinecraft().getPlayer().ca()) {
                final uz potion = potioneffect.a();
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                bib.z().N().a(inventoryBackground);
                this.b((int)i, (int)j, 0, 166, 140, 32);
                if (potion.c()) {
                    final int i2 = potion.d();
                    this.b((int)i + 6, (int)j + 7, 0 + i2 % 8 * 18, 198 + i2 / 8 * 18, 18, 18);
                }
                String s1 = cey.a(potion.a(), new Object[0]);
                if (potioneffect.c() == 1) {
                    s1 = s1 + " " + cey.a("enchantment.level.2", new Object[0]);
                }
                else if (potioneffect.c() == 2) {
                    s1 = s1 + " " + cey.a("enchantment.level.3", new Object[0]);
                }
                else if (potioneffect.c() == 3) {
                    s1 = s1 + " " + cey.a("enchantment.level.4", new Object[0]);
                }
                LabyModCore.getMinecraft().getFontRenderer().a(s1, (float)(i + 10.0 + 18.0), (float)(j + 6.0), 16777215);
                final String s2 = uz.a(potioneffect, 1.0f);
                LabyModCore.getMinecraft().getFontRenderer().a(s2, (float)(i + 10.0 + 18.0), (float)(j + 6.0 + 10.0), 8355711);
                j += l;
            }
        }
    }
    
    public void cullFaceBack() {
        bus.a(bus.i.b);
    }
    
    public void cullFaceFront() {
        bus.a(bus.i.a);
    }
    
    public void renderItemIntoGUI(final aip stack, final double x, final double y) {
        bib.z().ad().a(stack, (int)x, (int)y);
    }
    
    public void renderItemOverlayIntoGUI(final aip stack, final double xPosition, final double yPosition, final String text) {
        if (stack != null && ain.a(stack.c()) != 0) {
            if (stack.E() != 1 || text != null) {
                String s = (text == null) ? String.valueOf(stack.E()) : text;
                if (text == null && stack.E() < 1) {
                    s = ModColor.cl("c") + String.valueOf(stack.E());
                }
                bus.g();
                bus.j();
                bus.l();
                LabyMod.getInstance().getDrawUtils().drawString(s, xPosition + 19.0 - 2.0 - LabyModCore.getMinecraft().getFontRenderer().a(s), yPosition + 6.0 + 3.0);
                bus.f();
                bus.k();
            }
            if (stack.h()) {
                final int j = (int)Math.round(13.0 - stack.i() * 13.0 / stack.k());
                final int i = (int)Math.round(255.0 - stack.i() * 255.0 / stack.k());
                bus.g();
                bus.j();
                bus.z();
                bus.d();
                bus.l();
                this.drawItemTexture(xPosition + 2.0, yPosition + 13.0, 13.0, 2.0, 0, 0, 0, 255);
                this.drawItemTexture(xPosition + 2.0, yPosition + 13.0, 12.0, 1.0, (255 - i) / 4, 64, 0, 255);
                this.drawItemTexture(xPosition + 2.0, yPosition + 13.0, j, 1.0, 255 - i, i, 0, 255);
                bus.m();
                bus.e();
                bus.y();
                bus.f();
                bus.k();
            }
        }
    }
    
    public void renderEntity(final bzf renderManager, final vg entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final boolean p_147939_10_) {
        renderManager.a(entity, x, y, z, entityYaw, partialTicks, p_147939_10_);
    }
    
    private void drawItemTexture(final double x, final double y, final double z, final double offset, final int red, final int green, final int blue, final int alpha) {
        final WorldRendererAdapter worldRenderer = LabyModCore.getWorldRenderer();
        worldRenderer.begin(7, cdy.f);
        worldRenderer.pos(x + 0.0, y + 0.0, 0.0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + 0.0, y + offset, 0.0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + z, y + offset, 0.0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(x + z, y + 0.0, 0.0).color(red, green, blue, alpha).endVertex();
        bve.a().b();
    }
    
    public void renderLivingLabel(final vg entityIn, final String name, final double x, final double y, final double z, final int maxDistance) {
        final vg viewEntity = bib.z().aa();
        final double d0 = viewEntity.p - entityIn.p;
        final double d2 = viewEntity.q - entityIn.q;
        final double d3 = viewEntity.r - entityIn.r;
        final double distance = d0 * d0 + d2 * d2 + d3 * d3;
        if (distance <= maxDistance * maxDistance) {
            this.renderLivingLabel(entityIn, name, x, y, z);
        }
    }
    
    public void renderLivingLabel(final vg entityIn, final String name, final double x, final double y, final double z) {
        final float f = 1.6f;
        final float f2 = 0.016666668f * f;
        final bzf renderManager = bib.z().ac();
        final bip fontrenderer = renderManager.c();
        bus.G();
        bus.c((float)x + 0.0f, (float)y + entityIn.H + 0.5f, (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        bus.b(-renderManager.e, 0.0f, 1.0f, 0.0f);
        bus.b(renderManager.f, 1.0f, 0.0f, 0.0f);
        bus.b(-f2, -f2, f2);
        bus.g();
        bus.a(false);
        bus.j();
        bus.m();
        bus.a(770, 771, 1, 0);
        bus.z();
        final int labelX = fontrenderer.a(name) / 2;
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        worldrenderer.begin(7, cdy.f);
        worldrenderer.pos((double)(-labelX - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(-labelX - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(labelX + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(labelX + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        tessellator.b();
        bus.y();
        fontrenderer.a(name, -fontrenderer.a(name) / 2, 0, 553648127);
        bus.k();
        bus.a(true);
        fontrenderer.a(name, -fontrenderer.a(name) / 2, 0, -1);
        bus.f();
        bus.l();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.H();
    }
    
    public boolean canRenderName(final vg entity) {
        final bud clientPlayer = bib.z().h;
        final boolean visibleToClient = !entity.e((aed)clientPlayer);
        if (entity != clientPlayer) {
            final bhm team = entity.aY();
            final bhm clientTeam = clientPlayer.aY();
            if (team != null) {
                final bhm.b teamVisible = team.i();
                switch (teamVisible) {
                    case a: {
                        return visibleToClient;
                    }
                    case b: {
                        return false;
                    }
                    case c: {
                        return (clientTeam == null) ? visibleToClient : (team.a(clientTeam) && (team.h() || visibleToClient));
                    }
                    case d: {
                        return (clientTeam == null) ? visibleToClient : (!team.a(clientTeam) && visibleToClient);
                    }
                    default: {
                        return true;
                    }
                }
            }
        }
        return bib.w() && visibleToClient && !entity.aT();
    }
    
    static {
        RenderImplementation.inventoryBackground = new nf("textures/gui/container/inventory.png");
        buttonTextures = new nf("textures/gui/widgets.png");
    }
}
