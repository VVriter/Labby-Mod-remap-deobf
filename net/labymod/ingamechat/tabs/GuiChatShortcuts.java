//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tabs;

import net.labymod.ingamechat.*;
import net.labymod.ingamechat.tools.shortcuts.*;
import net.labymod.gui.elements.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.utils.*;
import net.labymod.utils.manager.*;
import java.util.*;
import java.io.*;

public class GuiChatShortcuts extends GuiChatCustom
{
    private final int windowWidth = 148;
    private final int windowHeight = 194;
    private Scrollbar scrollbar;
    private Shortcuts.Shortcut openShortcutComponent;
    private Shortcuts.Shortcut hoverShortcutComponent;
    private boolean hoverAddButton;
    private boolean hoverCancelButton;
    private boolean hoverSaveButton;
    private boolean hoverDeleteButton;
    private ModTextField fieldShortcut;
    private ModTextField fieldReplacement;
    private boolean canHighlightRed;
    
    public GuiChatShortcuts(final String defaultText) {
        super(defaultText);
        this.scrollbar = new Scrollbar(10);
        this.openShortcutComponent = null;
        this.hoverShortcutComponent = null;
        this.hoverAddButton = false;
        this.hoverCancelButton = false;
        this.hoverSaveButton = false;
        this.hoverDeleteButton = false;
        this.canHighlightRed = false;
    }
    
    public void b() {
        super.b();
        this.fieldShortcut = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 0, 10);
        this.fieldReplacement = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 0, 10);
        this.fieldShortcut.setEnableBackgroundDrawing(false);
        this.fieldReplacement.setEnableBackgroundDrawing(false);
        this.fieldShortcut.setMaxStringLength(120);
        this.fieldReplacement.setMaxStringLength(120);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int margin = 2;
        final int chatHeight = 16;
        final int width = draw.getWidth();
        this.getClass();
        final int x = width - 148 - margin;
        final int height = draw.getHeight();
        this.getClass();
        final int y = height - 194 - chatHeight;
        final int n = x;
        final int n2 = y;
        final int n3 = x;
        this.getClass();
        final int n4 = n3 + 148;
        final int n5 = y;
        this.getClass();
        DrawUtils.a(n, n2, n4, n5 + 194, Integer.MIN_VALUE);
        draw.drawString(LanguageManager.translate("ingame_chat_tab_shortcut"), x, y - 10);
        this.hoverShortcutComponent = null;
        this.hoverDeleteButton = false;
        final List<Shortcuts.Shortcut> shortcuts = LabyMod.getInstance().getChatToolManager().getShortcuts();
        this.scrollbar.setPosition(draw.getWidth() - margin - 1, y + margin, draw.getWidth() - margin, draw.getHeight() - chatHeight - margin);
        this.scrollbar.update(shortcuts.size());
        final int listPadding = 2;
        final int listX = x + listPadding;
        double listY = y + listPadding + this.scrollbar.getScrollY();
        for (final Shortcuts.Shortcut shortcutComponent : shortcuts) {
            if (listY >= y && listY < draw.getHeight() - chatHeight - listPadding) {
                final DrawUtils drawUtils = draw;
                final double left = listX - listPadding;
                final double top = listY;
                final int n6 = listX - listPadding;
                this.getClass();
                final boolean hover = drawUtils.drawRect(mouseX, mouseY, left, top, n6 + 148, listY + 10.0, 0, ModColor.toRGB(100, 200, 200, 100));
                final String shortcut = shortcutComponent.getShortcut();
                draw.drawString(shortcut, listX, listY + 1.0);
                boolean b = false;
                Label_0374: {
                    if (hover) {
                        final int n7 = listX - listPadding;
                        this.getClass();
                        if (mouseX > n7 + 148 - 10) {
                            b = true;
                            break Label_0374;
                        }
                    }
                    b = false;
                }
                final boolean hoverDeleteButton = b;
                if (hover) {
                    final DrawUtils drawUtils2 = draw;
                    final String string = ModColor.cl(hoverDeleteButton ? 'c' : '4') + "\u2718";
                    final int n8 = listX - listPadding;
                    this.getClass();
                    drawUtils2.drawRightString(string, n8 + 148, listY + 1.0);
                }
                if (hover) {
                    this.hoverShortcutComponent = shortcutComponent;
                    this.hoverDeleteButton = hoverDeleteButton;
                }
            }
            listY += 10.0;
        }
        this.scrollbar.draw();
        if (this.openShortcutComponent == null) {
            final int buttonSize = 14;
            final int buttonMargin = 2;
            this.hoverAddButton = draw.drawRect(mouseX, mouseY, (this.hoverAddButton ? ModColor.cl('a') : "") + "+", x - buttonSize - buttonMargin, y, x - buttonMargin, y + buttonSize, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.hoverSaveButton = false;
            this.hoverCancelButton = false;
        }
        else {
            final int editorWidth = 130;
            final int editorMargin = 2;
            final int editorPadding = 2;
            int editorYIndex = y + editorPadding;
            final int n9 = x - editorWidth - editorMargin;
            final int n10 = y;
            final int n11 = x - editorMargin;
            final int n12 = y;
            this.getClass();
            DrawUtils.a(n9, n10, n11, n12 + 194, Integer.MIN_VALUE);
            draw.drawString(LanguageManager.translate("chat_shortcut_shortcut") + ":", x - editorWidth + editorPadding - editorMargin, editorYIndex);
            editorYIndex += 10;
            this.fieldShortcut.width = editorWidth - editorPadding * 2;
            this.fieldShortcut.xPosition = x - editorWidth + editorPadding - editorMargin;
            this.fieldShortcut.yPosition = editorYIndex + 1;
            final int fieldShortcutColor = (this.canHighlightRed && this.fieldShortcut.getText().isEmpty()) ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE;
            DrawUtils.a(x - editorWidth + editorPadding - editorMargin, editorYIndex, x - editorPadding - editorMargin, editorYIndex + 10, fieldShortcutColor);
            this.fieldShortcut.drawTextBox();
            editorYIndex += 15;
            draw.drawString(LanguageManager.translate("chat_shortcut_replacement") + ":", x - editorWidth + editorPadding - editorMargin, editorYIndex);
            editorYIndex += 10;
            this.fieldReplacement.width = editorWidth - editorPadding * 2;
            this.fieldReplacement.xPosition = x - editorWidth + editorPadding - editorMargin;
            this.fieldReplacement.yPosition = editorYIndex + 1;
            final int fieldReplacementColor = (this.canHighlightRed && this.fieldReplacement.getText().isEmpty()) ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE;
            DrawUtils.a(x - editorWidth + editorPadding - editorMargin, editorYIndex, x - editorPadding - editorMargin, editorYIndex + 10, fieldReplacementColor);
            this.fieldReplacement.drawTextBox();
            editorYIndex += 13;
            if (mouseX > this.fieldReplacement.xPosition && mouseX < this.fieldReplacement.xPosition + this.fieldReplacement.width && mouseY > this.fieldReplacement.yPosition && mouseY < this.fieldReplacement.yPosition + this.fieldReplacement.height) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, "%s = Username");
            }
            final int buttonHeight = 12;
            final DrawUtils drawUtils3 = draw;
            final String translate = LanguageManager.translate("button_cancel");
            final double left2 = x - editorWidth + editorPadding - editorMargin;
            final int n13 = y;
            this.getClass();
            final double top2 = n13 + 194 - editorPadding - buttonHeight;
            final double right = x - editorWidth + editorPadding - editorMargin + editorWidth / 2 - editorPadding;
            final int n14 = y;
            this.getClass();
            this.hoverCancelButton = drawUtils3.drawRect(mouseX, mouseY, translate, left2, top2, right, n14 + 194 - editorPadding, Integer.MAX_VALUE, ModColor.toRGB(200, 100, 100, 200));
            final DrawUtils drawUtils4 = draw;
            final String translate2 = LanguageManager.translate("button_save");
            final double left3 = x - editorWidth + editorPadding - editorMargin + editorWidth / 2 + editorPadding;
            final int n15 = y;
            this.getClass();
            final double top3 = n15 + 194 - editorPadding - buttonHeight;
            final double right2 = x - editorMargin - editorPadding;
            final int n16 = y;
            this.getClass();
            this.hoverSaveButton = drawUtils4.drawRect(mouseX, mouseY, translate2, left3, top3, right2, n16 + 194 - editorPadding, Integer.MAX_VALUE, ModColor.toRGB(100, 200, 100, 200));
            this.hoverAddButton = false;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (this.hoverAddButton && mouseButton == 0) {
            this.openShortcutComponent = new Shortcuts.Shortcut("", "");
            this.canHighlightRed = false;
            this.fieldShortcut.setText("");
            this.fieldReplacement.setText("");
        }
        if (this.hoverShortcutComponent != null && mouseButton == 0) {
            if (this.hoverDeleteButton) {
                final List<Shortcuts.Shortcut> shortcuts = LabyMod.getInstance().getChatToolManager().getShortcuts();
                shortcuts.remove(this.hoverShortcutComponent);
                if (this.hoverShortcutComponent == this.openShortcutComponent) {
                    this.openShortcutComponent = null;
                }
                LabyMod.getInstance().getChatToolManager().saveTools();
            }
            else {
                this.openShortcutComponent = this.hoverShortcutComponent;
                this.canHighlightRed = false;
                this.fieldShortcut.setText(this.openShortcutComponent.getShortcut());
                this.fieldReplacement.setText(this.openShortcutComponent.getReplacement());
            }
        }
        if (this.hoverCancelButton && mouseButton == 0) {
            this.openShortcutComponent = null;
        }
        if (this.hoverSaveButton && mouseButton == 0) {
            final String shortcut = this.fieldShortcut.getText();
            final String replacement = this.fieldReplacement.getText();
            if (shortcut.isEmpty() || replacement.isEmpty()) {
                this.canHighlightRed = true;
                return;
            }
            this.canHighlightRed = false;
            this.openShortcutComponent.setShortcut(this.fieldShortcut.getText());
            this.openShortcutComponent.setReplacement(this.fieldReplacement.getText());
            final List<Shortcuts.Shortcut> shortcuts2 = LabyMod.getInstance().getChatToolManager().getShortcuts();
            if (!shortcuts2.contains(this.openShortcutComponent)) {
                shortcuts2.add(this.openShortcutComponent);
            }
            this.openShortcutComponent = null;
            LabyMod.getInstance().getChatToolManager().saveTools();
        }
        if (this.openShortcutComponent != null) {
            this.fieldShortcut.mouseClicked(mouseX, mouseY, mouseButton);
            this.fieldReplacement.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.openShortcutComponent != null) {
            this.fieldShortcut.textboxKeyTyped(typedChar, keyCode);
            this.fieldReplacement.textboxKeyTyped(typedChar, keyCode);
            if (keyCode == 15) {
                if (this.fieldShortcut.isFocused()) {
                    this.fieldShortcut.setFocused(false);
                    this.fieldReplacement.setFocused(true);
                }
                else {
                    this.fieldShortcut.setFocused(true);
                    this.fieldReplacement.setFocused(false);
                }
            }
            if (keyCode == 1) {
                super.a(typedChar, keyCode);
            }
        }
        else {
            super.a(typedChar, keyCode);
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    public void e() {
        if (this.openShortcutComponent != null) {
            this.fieldShortcut.updateCursorCounter();
            this.fieldReplacement.updateCursorCounter();
        }
    }
}
