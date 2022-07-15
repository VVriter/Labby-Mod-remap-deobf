//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.gui.elements.*;
import net.labymod.ingamegui.enums.*;
import net.labymod.core.*;
import net.labymod.utils.manager.*;
import net.minecraftforge.fml.relauncher.*;
import java.lang.reflect.*;
import net.labymod.main.*;
import net.labymod.ingamegui.*;
import net.labymod.main.lang.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import java.io.*;

public class PreviewRenderer extends bir
{
    private static final boolean MC18;
    private static final nf widgetsTexPath;
    private static final aip previewItemStackSword;
    private static final aip previewItemStackFish;
    private static final CustomGuiButton dummyButton;
    private static PreviewRenderer instance;
    private bvd framebuffer;
    private Class<?> bindedClass;
    private boolean created;
    private boolean fastRenderConflict;
    
    public PreviewRenderer() {
        this.framebuffer = new bvd(bib.z().d, bib.z().e, true);
        this.created = false;
        PreviewRenderer.instance = this;
    }
    
    public static PreviewRenderer getInstance() {
        if (PreviewRenderer.instance == null) {
            PreviewRenderer.instance = new PreviewRenderer();
        }
        return PreviewRenderer.instance;
    }
    
    public void init(final Class<?> theClass) {
        this.bindedClass = theClass;
        if (!this.fastRenderConflict) {
            this.handleOFFastRender();
        }
        final int width = bib.z().d;
        final int height = bib.z().e;
        if (this.framebuffer.c != width || (this.framebuffer.d != height && !this.fastRenderConflict)) {
            this.framebuffer.b(bib.z().d, bib.z().e);
            this.created = false;
        }
    }
    
    public void createFrame() {
        if (this.framebuffer == null || bib.z().m == null || !bib.z().m.getClass().equals(this.bindedClass)) {
            return;
        }
        if (!this.fastRenderConflict) {
            this.framebuffer.a(true);
        }
    }
    
    private void setFirstFrameInBuffer() {
        if (this.created) {
            return;
        }
        this.created = true;
        bus.G();
        this.framebuffer.a(true);
        this.renderImage(true, 0, 0, LabyMod.getInstance().getDrawUtils().getScaledResolution().a(), LabyMod.getInstance().getDrawUtils().getScaledResolution().b());
        bib.z().b().a(true);
        bus.H();
    }
    
    public void kill() {
        if (!this.fastRenderConflict && this.framebuffer != null) {
            this.framebuffer.e();
        }
    }
    
    public void render(final EnumDisplayType displayType, final int left, final int top, final int right, final int bottom, final float partialTicks, final int mouseX, final int mouseY) {
        if (!this.fastRenderConflict) {
            this.setFirstFrameInBuffer();
        }
        this.renderImage(this.fastRenderConflict, left, top, right, bottom);
        if (displayType == EnumDisplayType.INGAME || displayType == EnumDisplayType.ESCAPE) {
            this.renderHotbar(left, top, right, bottom, partialTicks, mouseX, mouseY);
            this.renderPlayerStatsNew(left, top, right, bottom);
            this.renderExpBar(left, top, right, bottom);
        }
        if (displayType == EnumDisplayType.ESCAPE) {
            if (LabyMod.getSettings().guiBackground) {
                LabyMod.getInstance().getDrawUtils().drawRectangle(left, top, right, bottom, Integer.MIN_VALUE);
            }
            this.renderEscapeScreen(left, top, right, bottom, mouseX, mouseY);
        }
    }
    
    private void renderImage(final boolean forceDefaultImage, final int left, final int top, final int right, final int bottom) {
        final int minWidth = Math.min(left, right);
        final int minHeight = Math.min(top, bottom);
        final int maxWidth = Math.max(left, right);
        final int maxHeight = Math.max(top, bottom);
        final bve tessellator = bve.a();
        final WorldRendererAdapter vertexBuffer = LabyModCore.getWorldRenderer();
        bus.z();
        bus.l();
        final float[] color = { 0.0f, 0.0f, 0.0f, 0.5f };
        vertexBuffer.begin(7, cdy.f);
        vertexBuffer.pos((double)(minWidth - 1), (double)(maxHeight + 1), -90.0).color(color[0], color[1], color[2], color[3]).endVertex();
        vertexBuffer.pos((double)(maxWidth + 1), (double)(maxHeight + 1), -90.0).color(color[0], color[1], color[2], color[3]).endVertex();
        vertexBuffer.pos((double)(maxWidth + 1), (double)(minHeight - 1), -90.0).color(color[0], color[1], color[2], color[3]).endVertex();
        vertexBuffer.pos((double)(minWidth - 1), (double)(minHeight - 1), -90.0).color(color[0], color[1], color[2], color[3]).endVertex();
        tessellator.b();
        if (LabyMod.getInstance().isInGame() && !forceDefaultImage) {
            this.framebuffer.c();
            bus.y();
            vertexBuffer.begin(7, cdy.i);
            vertexBuffer.pos((double)minWidth, (double)maxHeight, -90.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexBuffer.pos((double)maxWidth, (double)maxHeight, -90.0).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexBuffer.pos((double)maxWidth, (double)minHeight, -90.0).tex(1.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexBuffer.pos((double)minWidth, (double)minHeight, -90.0).tex(0.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            tessellator.b();
        }
        else {
            bib.z().N().a(ModTextures.SETTINGS_MODULE_EDITOR_BG);
            bus.y();
            LabyMod.getInstance().getDrawUtils().drawTexture(left, top, 0.0, 0.0, 255.0, 255.0, right - left, bottom - top);
        }
    }
    
    private void renderHotbar(final int left, final int top, final int right, final int bottom, final float partialTicks, final int mouseX, final int mouseY) {
        aed entityplayer = (aed)bib.z().aa();
        if (!(entityplayer instanceof aed)) {
            entityplayer = null;
        }
        bus.e();
        bus.m();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bib.z().N().a(PreviewRenderer.widgetsTexPath);
        final int i = (right - left) / 2;
        final float f = this.e;
        this.e = -90.0f;
        this.b(left + i - 91, bottom - 22, 0, 0, 182, 22);
        this.b(left + i - 91 - 1 + ((entityplayer == null) ? 0 : entityplayer.bv.d) * 20, bottom - 22 - 1, 0, 22, 24, 22);
        this.e = f;
        bus.D();
        bus.m();
        bus.a(770, 771, 1, 0);
        bhz.c();
        for (int j = 0; j < 9; ++j) {
            final int k = (right - left) / 2 - 90 + j * 20 + 2;
            final int l = bottom - 16 - 3;
            this.renderHotbarItem(j, left + k, l, partialTicks, entityplayer, mouseX, mouseY);
        }
        bus.j();
        bhz.a();
        bus.E();
        bus.l();
    }
    
    private void renderHotbarItem(final int index, final int xPos, final int yPos, final float partialTicks, final aed entity, final int mouseX, final int mouseY) {
        aip itemstack = null;
        if (entity == null) {
            itemstack = ((index == 0) ? PreviewRenderer.previewItemStackSword : ((index == 8) ? PreviewRenderer.previewItemStackFish : null));
        }
        else {
            itemstack = LabyModCore.getMinecraft().getItem(entity.bv, index);
        }
        if (itemstack != null) {
            final float f = LabyModCore.getMinecraft().getAnimationsToGo(itemstack) - partialTicks;
            if (f > 0.0f) {
                bus.G();
                final float f2 = 1.0f + f / 5.0f;
                bus.c((float)(xPos + 8), (float)(yPos + 12), 0.0f);
                bus.b(1.0f / f2, (f2 + 1.0f) / 2.0f, 1.0f);
                bus.c((float)(-(xPos + 8)), (float)(-(yPos + 12)), 0.0f);
            }
            bus.k();
            bib.z().ad().b(itemstack, xPos, yPos);
            bus.j();
            if (f > 0.0f) {
                bus.H();
            }
            bib.z().ad().a(LabyModCore.getMinecraft().getFontRenderer(), itemstack, xPos, yPos);
            if (index == 8 && entity == null && mouseX > xPos && mouseX < xPos + 16 && mouseY > yPos && mouseY < yPos + 16) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, "Puffi");
            }
        }
    }
    
    private void renderPlayerStatsNew(final int left, final int top, final int right, final int bottom) {
        bib.z().N().a(LabyModCore.getRenderImplementation().getIcons());
        bus.e();
        float healthUpdateCounter = 0.0f;
        int lastPlayerHealth = 0;
        float lastSystemTime = 0.0f;
        float playerHealth = 0.0f;
        final float updateCounter = 0.0f;
        final int i = 20;
        final boolean flag = healthUpdateCounter > (long)updateCounter && (healthUpdateCounter - (long)updateCounter) / 3.0f % 2.0f == 1.0f;
        lastSystemTime = (float)bib.I();
        healthUpdateCounter = (float)(long)(updateCounter + 10.0f);
        if (bib.I() - lastSystemTime > 1000.0f) {
            playerHealth = (float)i;
            lastPlayerHealth = i;
            lastSystemTime = (float)bib.I();
        }
        playerHealth = (float)i;
        final int j = lastPlayerHealth;
        final boolean flag2 = false;
        final int k = 20;
        final int l = 20;
        final float saturation = 0.0f;
        final int i2 = left + (right - left) / 2 - 91;
        final int j2 = left + (right - left) / 2 + 91;
        final int k2 = top + bottom - top - 39;
        final float f = 20.0f;
        final float f2 = 0.0f;
        final int l2 = LabyModCore.getMath().ceiling_float_int((f + f2) / 2.0f / 10.0f);
        final int i3 = Math.max(10 - (l2 - 2), 3);
        final int j3 = k2 - (l2 - 1) * i3 - 10;
        float f3 = f2;
        final int k3 = 20;
        final int l3 = -1;
        for (int i4 = 0; i4 < 10; ++i4) {
            if (k3 > 0) {
                final int j4 = i2 + i4 * 8;
                if (i4 * 2 + 1 < k3) {
                    this.b(j4, j3, 34, 9, 9, 9);
                }
                if (i4 * 2 + 1 == k3) {
                    this.b(j4, j3, 25, 9, 9, 9);
                }
                if (i4 * 2 + 1 > k3) {
                    this.b(j4, j3, 16, 9, 9, 9);
                }
            }
        }
        for (int i5 = LabyModCore.getMath().ceiling_float_int((f + f2) / 2.0f) - 1; i5 >= 0; --i5) {
            final int j5 = 16;
            int k4 = 0;
            if (flag) {
                k4 = 1;
            }
            final int l4 = LabyModCore.getMath().ceiling_float_int((i5 + 1) / 10.0f) - 1;
            final int i6 = i2 + i5 % 10 * 8;
            int j6 = k2 - l4 * i3;
            if (i5 == l3) {
                j6 -= 2;
            }
            final int k5 = 0;
            this.b(i6, j6, 16 + k4 * 9, 9 * k5, 9, 9);
            if (flag) {
                if (i5 * 2 + 1 < j) {
                    this.b(i6, j6, j5 + 54, 9 * k5, 9, 9);
                }
                if (i5 * 2 + 1 == j) {
                    this.b(i6, j6, j5 + 63, 9 * k5, 9, 9);
                }
            }
            if (f3 > 0.0f) {
                if (f3 == f2 && f2 % 2.0f == 1.0f) {
                    this.b(i6, j6, j5 + 153, 9 * k5, 9, 9);
                }
                else {
                    this.b(i6, j6, j5 + 144, 9 * k5, 9, 9);
                }
                f3 -= 2.0f;
            }
            else {
                if (i5 * 2 + 1 < i) {
                    this.b(i6, j6, j5 + 36, 9 * k5, 9, 9);
                }
                if (i5 * 2 + 1 == i) {
                    this.b(i6, j6, j5 + 45, 9 * k5, 9, 9);
                }
            }
        }
        for (int k6 = 0; k6 < 10; ++k6) {
            final int i7 = k2;
            final int l5 = 16;
            int j7 = 0;
            if (flag2) {
                j7 = 1;
            }
            final int i8 = j2 - k6 * 8 - 9;
            this.b(i8, i7, 16 + j7 * 9, 27, 9, 9);
            if (flag2) {
                if (k6 * 2 + 1 < l) {
                    this.b(i8, i7, l5 + 54, 27, 9, 9);
                }
                if (k6 * 2 + 1 == l) {
                    this.b(i8, i7, l5 + 63, 27, 9, 9);
                }
            }
            if (k6 * 2 + 1 < k) {
                this.b(i8, i7, l5 + 36, 27, 9, 9);
            }
            if (k6 * 2 + 1 == k) {
                this.b(i8, i7, l5 + 45, 27, 9, 9);
            }
        }
        final int l6 = 300;
        for (int k7 = LabyModCore.getMath().ceiling_double_int((l6 - 2) * 10.0 / 300.0), i9 = LabyModCore.getMath().ceiling_double_int(l6 * 10.0 / 300.0) - k7, l7 = 0; l7 < k7 + i9; ++l7) {
            if (l7 < k7) {
                this.b(j2 - l7 * 8 - 9, j3, 16, 18, 9, 9);
            }
            else {
                this.b(j2 - l7 * 8 - 9, j3, 25, 18, 9, 9);
            }
        }
    }
    
    public void renderExpBar(final int left, final int top, final int right, final int bottom) {
        bib.z().N().a(LabyModCore.getRenderImplementation().getIcons());
        final int i = 1;
        final float experience = 0.7f;
        final int experienceLevel = 42;
        if (i > 0) {
            final int j = 182;
            final int k = (int)(experience * (j + 1));
            final int l = bottom - 32 + 3;
            this.b(left + (right - left) / 2 - 91, l, 0, 64, j, 5);
            if (k > 0) {
                this.b(left + (right - left) / 2 - 91, l, 0, 69, k, 5);
            }
        }
        if (experienceLevel > 0) {
            final int k2 = 8453920;
            final String s = "" + experienceLevel;
            final int l2 = left + (right - left - LabyModCore.getMinecraft().getFontRenderer().a(s)) / 2;
            final int i2 = bottom - 31 - 4;
            LabyModCore.getMinecraft().getFontRenderer().a(s, l2 + 1, i2, 0);
            LabyModCore.getMinecraft().getFontRenderer().a(s, l2 - 1, i2, 0);
            LabyModCore.getMinecraft().getFontRenderer().a(s, l2, i2 + 1, 0);
            LabyModCore.getMinecraft().getFontRenderer().a(s, l2, i2 - 1, 0);
            LabyModCore.getMinecraft().getFontRenderer().a(s, l2, i2, k2);
        }
    }
    
    public void renderEscapeScreen(final int left, final int top, final int right, final int bottom, final int mouseX, final int mouseY) {
        final int width = right - left;
        final int height = bottom - top;
        final int i = -16;
        final bib mc = bib.z();
        final String disconnectString = cey.a(mc.D() ? "menu.disconnect" : "menu.returnToMenu", new Object[0]);
        final boolean shareToLanEnabled = mc.E() && !mc.F().a();
        this.renderDummyButton(disconnectString, left + width / 2 - 100, top + height / 4 + 120 + i, 200, true, mouseX, mouseY);
        this.renderDummyButton(cey.a("menu.returnToGame", new Object[0]), left + width / 2 - 100, top + height / 4 + 24 + i, 200, true, mouseX, mouseY);
        this.renderDummyButton(cey.a("menu.options", new Object[0]), left + width / 2 - 100, top + height / 4 + 96 + i, 98, true, mouseX, mouseY);
        this.renderDummyButton(cey.a("menu.shareToLan", new Object[0]), left + width / 2 + 2, top + height / 4 + 96 + i, 98, shareToLanEnabled, mouseX, mouseY);
        this.renderDummyButton(PreviewRenderer.MC18 ? cey.a("gui.achievements", new Object[0]) : "Achievements", left + width / 2 - 100, top + height / 4 + 48 + i, 98, true, mouseX, mouseY);
        this.renderDummyButton(cey.a("gui.stats", new Object[0]), left + width / 2 + 2, top + height / 4 + 48 + i, 98, true, mouseX, mouseY);
        this.a(LabyModCore.getMinecraft().getFontRenderer(), cey.a("menu.game", new Object[0]), left + width / 2, 40 + top, 16777215);
        DrawUtils.a(left + 5, top + 5, right - 5, top + 5 + 20, Integer.MIN_VALUE);
        this.c(LabyModCore.getMinecraft().getFontRenderer(), "<tab buttons>", left + 5 + 5, top + 11, ModColor.toRGB(140, 140, 140, 255));
    }
    
    public void renderDummyButton(final String displayText, final int x, final int y, final int width, final boolean enabled, final int mouseX, final int mouseY) {
        PreviewRenderer.dummyButton.j = displayText;
        PreviewRenderer.dummyButton.setPosition(x, y, x + width, y + 20);
        PreviewRenderer.dummyButton.setEnabled(enabled);
        LabyModCore.getMinecraft().drawButton((bja)PreviewRenderer.dummyButton, mouseX, mouseY);
    }
    
    private void handleOFFastRender() {
        try {
            final Field fieldOfFastRender = ReflectionHelper.findField(bid.class, "ofFastRender");
            try {
                this.fastRenderConflict = fieldOfFastRender.getBoolean(bib.z().t);
                if (this.fastRenderConflict) {
                    bib.z().a((blk)new FastRenderConflict());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        widgetsTexPath = new nf("textures/gui/widgets.png");
        previewItemStackSword = Material.IRON_SWORD.createItemStack();
        previewItemStackFish = Material.RAW_FISH.createItemStack(1, 3);
        dummyButton = new CustomGuiButton(0, 0, 0, (String)null);
    }
    
    public static class FastRenderConflict extends blk
    {
        private BooleanElement ofFastRenderElement;
        private bja buttonRestartGame;
        private blk lastScreen;
        
        public FastRenderConflict() {
            this.lastScreen = bib.z().m;
            Module.setCurrentModuleGui((ModuleGui)null);
        }
        
        public void b() {
            this.n.clear();
            final int divY = this.m / 2 - 40 + 50;
            this.n.add(new bja(0, this.l / 2 - 100, divY + 30, 90, 20, LanguageManager.translate("button_cancel")));
            this.n.add(this.buttonRestartGame = new bja(1, this.l / 2 + 10, divY + 30, 90, 20, LanguageManager.translate("button_restart")));
            final String buttonString = LanguageManager.translate("button_continue_anyway") + " >>";
            this.n.add(new bja(2, this.l - LabyMod.getInstance().getDrawUtils().getStringWidth(buttonString) - 10, this.m - 25, 120, 20, buttonString));
            try {
                final Field fieldOfFastRender = ReflectionHelper.findField(bid.class, "ofFastRender");
                final boolean enabled = fieldOfFastRender.getBoolean(bib.z().t);
                this.ofFastRenderElement = new BooleanElement("FastRender", new ControlElement.IconData(Material.IRON_PICKAXE), (Consumer)new Consumer<Boolean>() {
                    @Override
                    public void accept(final Boolean accepted) {
                        try {
                            fieldOfFastRender.setAccessible(true);
                            fieldOfFastRender.setBoolean(bib.z().t, accepted);
                            bib.z().t.b();
                            bib.z().b().a(bib.z().d, bib.z().e);
                            if (bib.z().o != null) {
                                bib.z().o.a(bib.z().d, bib.z().e);
                            }
                            FastRenderConflict.this.buttonRestartGame.l = !accepted;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, enabled);
                this.buttonRestartGame.l = !enabled;
            }
            catch (Exception error) {
                error.printStackTrace();
            }
            super.b();
        }
        
        public void a(final int mouseX, final int mouseY, final float partialTicks) {
            this.c(0);
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            if (this.ofFastRenderElement == null) {
                draw.drawCenteredString(ModColor.cl("c") + LanguageManager.translate("optifine_fast_render_manual_title"), this.l / 2, this.m / 2 - 50);
                draw.drawCenteredString(LanguageManager.translate("optifine_fast_render_manual_explanation"), this.l / 2, this.m / 2 - 50 + 10);
                draw.drawCenteredString(LanguageManager.translate("optifine_fast_render_manual_path"), this.l / 2, this.m / 2 - 50 + 30);
            }
            else {
                final int divY = this.m / 2 - 40;
                draw.drawCenteredString(ModColor.cl("c") + LanguageManager.translate("optifine_fast_render_auto_tile"), this.l / 2, divY);
                draw.drawCenteredString(LanguageManager.translate("optifine_fast_render_auto_explanation"), this.l / 2, divY + 10);
                draw.drawCenteredString(LanguageManager.translate("optifine_fast_render_auto_request"), this.l / 2, divY + 30);
                final int centerPosX = this.l / 2;
                final int centerPosY = divY + 55;
                final int elementWidth = 200;
                final int elementHeight = 22;
                this.ofFastRenderElement.draw(centerPosX - elementWidth / 2, centerPosY - elementHeight / 2, centerPosX + elementWidth / 2, centerPosY + elementHeight / 2, mouseX, mouseY);
            }
            super.a(mouseX, mouseY, partialTicks);
        }
        
        protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            if (this.ofFastRenderElement != null) {
                this.ofFastRenderElement.mouseClicked(mouseX, mouseY, mouseButton);
            }
            super.a(mouseX, mouseY, mouseButton);
        }
        
        protected void a(final bja button) throws IOException {
            super.a(button);
            switch (button.k) {
                case 0: {
                    if (this.lastScreen instanceof LabyModModuleEditorGui) {
                        final LabyModModuleEditorGui lastScreen = (LabyModModuleEditorGui)this.lastScreen;
                        bib.z().a(lastScreen.getLastScreen());
                    }
                    else {
                        bib.z().a((blk)(LabyMod.getInstance().isInGame() ? null : new blr()));
                    }
                    Module.setCurrentModuleGui((ModuleGui)null);
                    break;
                }
                case 1: {
                    bib.z().n();
                    break;
                }
                case 2: {
                    bib.z().a(this.lastScreen);
                    break;
                }
            }
        }
    }
}
