//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import net.labymod.main.lang.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import java.io.*;
import net.labymod.main.*;
import java.util.*;

public class GuiTags extends blk
{
    private blk lastScreen;
    private Scrollbar scrollbar;
    private String selectedTag;
    private String hoveredTag;
    private boolean addTagScreen;
    private bja buttonEdit;
    private bja buttonRemove;
    
    public GuiTags(final blk lastScreen) {
        this.scrollbar = new Scrollbar(29);
        this.selectedTag = null;
        this.hoveredTag = null;
        this.addTagScreen = false;
        this.lastScreen = lastScreen;
    }
    
    public void b() {
        super.b();
        this.scrollbar.init();
        this.scrollbar.setPosition(this.l / 2 + 102, 34, this.l / 2 + 106, this.m - 32 - 3);
        this.scrollbar.setSpeed(10);
        this.n.add(this.buttonRemove = new bja(1, this.l / 2 - 120, this.m - 26, 75, 20, LanguageManager.translate("button_remove")));
        this.n.add(this.buttonEdit = new bja(2, this.l / 2 - 37, this.m - 26, 75, 20, LanguageManager.translate("button_edit")));
        this.n.add(new bja(3, this.l / 2 + 120 - 75, this.m - 26, 75, 20, LanguageManager.translate("button_add")));
        this.n.add(new bja(6, this.l / 2 - 105, 8, 20, 20, "<"));
    }
    
    public void m() {
        super.m();
        TagManager.save();
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 1: {
                final blk lastScreen = bib.z().m;
                bib.z().a((blk)new bkq((bkp)new bkp() {
                    public void a(final boolean result, final int id) {
                        if (result) {
                            TagManager.getConfigManager().getSettings().getTags().remove(GuiTags.this.selectedTag);
                        }
                        bib.z().a(lastScreen);
                    }
                }, LanguageManager.translate("warning_delete"), ModColor.cl("c") + this.selectedTag, 1));
                break;
            }
            case 2: {
                bib.z().a((blk)new GuiTagsAdd(this, this.selectedTag));
                break;
            }
            case 3: {
                bib.z().a((blk)new GuiTagsAdd(this, null));
                break;
            }
            case 6: {
                bib.z().a(this.lastScreen);
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        if (this.addTagScreen) {
            return;
        }
        this.hoveredTag = null;
        double yPos = 35.0 + this.scrollbar.getScrollY() + 3.0;
        final Map<String, String> tags = TagManager.getConfigManager().getSettings().getTags();
        for (final String tag : tags.keySet()) {
            this.drawEntry(tag, tags.get(tag), yPos, mouseX, mouseY);
            yPos += 29.0;
        }
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 31);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(this.m - 32, this.m);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(31.0, 0.0, this.l);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(this.m - 32, 0.0, this.l);
        LabyMod.getInstance().getDrawUtils().drawCenteredString(LanguageManager.translate("title_tags"), this.l / 2, 15.0);
        bib.z().N().a(new nf("labymod/textures/settings/settings/tags.png"));
        LabyMod.getInstance().getDrawUtils().drawTexture(this.l / 2 + 105 - 25, 8.0, 256.0, 256.0, 20.0, 20.0);
        this.scrollbar.update(tags.size());
        this.scrollbar.draw();
        this.buttonEdit.l = (this.selectedTag != null);
        this.buttonRemove.l = (this.selectedTag != null);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    private void drawEntry(final String key, final String value, final double y, final int mouseX, final int mouseY) {
        final int x = this.l / 2 - 100;
        final boolean hovered = mouseX > x && mouseX < x + 200 && mouseY > y && mouseY < y + 24.0 && mouseX > 32 && mouseY < this.m - 32;
        if (hovered) {
            this.hoveredTag = key;
        }
        final int borderColor = (this.selectedTag == key) ? ModColor.toRGB(240, 240, 240, 240) : Integer.MIN_VALUE;
        final int backgroundColor = hovered ? ModColor.toRGB(50, 50, 50, 120) : ModColor.toRGB(30, 30, 30, 120);
        a(x - 5, (int)y - 4, x + 200, (int)y + 24, backgroundColor);
        LabyMod.getInstance().getDrawUtils().drawRectBorder(x - 5, (int)y - 4, x + 200, (int)y + 24, borderColor, 1.0);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        final int headSize = 20;
        LabyMod.getInstance().getDrawUtils().drawPlayerHead(key, x, (int)y, headSize);
        LabyMod.getInstance().getDrawUtils().drawString(key, x + headSize + 5, y + 1.0);
        String tagValue = value.replaceAll("&", "§");
        tagValue = LabyMod.getInstance().getDrawUtils().trimStringToWidth(tagValue, 200 - headSize - 10);
        LabyMod.getInstance().getDrawUtils().drawString(tagValue, x + headSize + 5, y + 10.0 + 1.0);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (this.hoveredTag != null) {
            this.selectedTag = this.hoveredTag;
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, state);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
}
