//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tabs;

import net.labymod.ingamechat.*;
import net.labymod.gui.elements.*;
import net.labymod.ingamechat.tools.autotext.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.io.*;
import org.lwjgl.input.*;
import net.labymod.main.lang.*;
import java.util.*;
import net.labymod.utils.*;

public class GuiChatAutoText extends GuiChatCustom
{
    private Scrollbar scrollbar;
    private AutoTextKeyBinds.AutoText selectedAutoText;
    private bje textFieldMessage;
    private bje textFieldAutoTextKeyCode;
    private bje textFieldAutoTextServerAddress;
    private boolean markMessageRed;
    private boolean markKeybindRed;
    private boolean canScroll;
    
    public GuiChatAutoText(final String defaultText) {
        super(defaultText);
        this.scrollbar = new Scrollbar(15);
        this.markMessageRed = false;
        this.markKeybindRed = false;
    }
    
    public void b() {
        super.b();
        this.scrollbar.setPosition(this.l - 6, this.m - 196, this.l - 5, this.m - 20);
        this.scrollbar.update(LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds().size());
        this.scrollbar.setSpeed(10);
        this.scrollbar.setEntryHeight(10.0);
        this.textFieldMessage = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldAutoTextKeyCode = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldAutoTextServerAddress = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, -100, 110, 10);
    }
    
    public void k() throws IOException {
        super.k();
        if (this.canScroll) {
            this.scrollbar.mouseInput();
            final int i = Mouse.getEventDWheel();
            if (i != 0) {
                this.j.q.d().c();
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        this.scrollbar.calc();
        a(this.l - 150, this.m - 200, this.l - 2, this.m - 16, Integer.MIN_VALUE);
        this.canScroll = (mouseX > this.l - 150 && mouseX < this.l - 2 && mouseY > this.m - 150 && mouseY < this.m - 16);
        int row = 0;
        for (final AutoTextKeyBinds.AutoText component : LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds()) {
            final double posY = this.m - 195 + row * 10 + this.scrollbar.getScrollY();
            ++row;
            if (posY >= this.m - 200) {
                if (posY > this.m - 25) {
                    continue;
                }
                final boolean hover = this.selectedAutoText == null && mouseX > this.l - 150 + 1 && mouseX < this.l - 2 - 1 && mouseY > posY - 1.0 && mouseY < posY + 9.0;
                if (hover || this.selectedAutoText == component) {
                    a(this.l - 150 + 1, (int)posY - 1, this.l - 2 - 1, (int)posY + 9, hover ? ModColor.toRGB(100, 200, 200, 100) : Integer.MAX_VALUE);
                }
                this.c(LabyModCore.getMinecraft().getFontRenderer(), LabyMod.getInstance().getDrawUtils().trimStringToWidth("[" + ModColor.cl("a") + (component.isKeyShift() ? "SHIFT+" : "") + (component.isKeyAlt() ? "ALT+" : "") + (component.isKeyCtrl() ? "CTRL+" : "") + Keyboard.getKeyName(component.getKeyCode()) + ModColor.cl("r") + "] " + component.getMessage(), 130), this.l - 145, (int)posY, Integer.MAX_VALUE);
                if (this.selectedAutoText != null) {
                    continue;
                }
                final boolean hoverX = mouseX > this.l - 12 - 1 && mouseX < this.l - 12 + 7 && mouseY > posY && mouseY < posY + 8.0;
                this.c(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl(hoverX ? "c" : "4") + "\u2718", this.l - 12, (int)posY, Integer.MAX_VALUE);
            }
        }
        if (!this.scrollbar.isHidden()) {
            a(this.l - 6, this.m - 145, this.l - 5, this.m - 20, Integer.MIN_VALUE);
            a(this.l - 7, (int)this.scrollbar.getTop(), this.l - 4, (int)(this.scrollbar.getTop() + this.scrollbar.getBarLength()), Integer.MAX_VALUE);
        }
        if (this.selectedAutoText == null) {
            final boolean hover2 = mouseX > this.l - 165 && mouseX < this.l - 152 && mouseY > this.m - 200 && mouseY < this.m - 200 + 13;
            a(this.l - 165, this.m - 200, this.l - 152, this.m - 200 + 13, hover2 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), "+", this.l - 158, this.m - 197, hover2 ? ModColor.toRGB(50, 220, 120, 210) : Integer.MAX_VALUE);
        }
        else {
            a(this.l - 270, this.m - 200, this.l - 152, this.m - 16, Integer.MIN_VALUE);
            final int relX = this.l - 270;
            final int relY = this.m - 200;
            this.drawElementTextField("message", this.textFieldMessage, relX, relY, mouseX, mouseY);
            this.drawElementTextField("key", this.textFieldAutoTextKeyCode, relX, relY + 23, mouseX, mouseY);
            this.drawElementCheckBox("with_shift", this.selectedAutoText.isKeyShift(), relX, relY + 46, mouseX, mouseY);
            this.drawElementCheckBox("with_alt", this.selectedAutoText.isKeyAlt(), relX, relY + 46 + 11, mouseX, mouseY);
            this.drawElementCheckBox("with_ctrl", this.selectedAutoText.isKeyCtrl(), relX, relY + 46 + 22, mouseX, mouseY);
            this.drawElementCheckBox("send_instantly", !this.selectedAutoText.isSendNotInstantly(), relX, relY + 46 + 33, mouseX, mouseY);
            this.drawElementCheckBox("server_bound", this.selectedAutoText.isServerBound(), relX, relY + 46 + 44, mouseX, mouseY);
            if (this.selectedAutoText.isServerBound()) {
                this.drawElementTextField("server_address", this.textFieldAutoTextServerAddress, relX, relY + 46 + 55, mouseX, mouseY);
            }
            final boolean hoverCancel = mouseX > this.l - 268 && mouseX < this.l - 213 && mouseY > this.m - 30 && mouseY < this.m - 18;
            final boolean hoverSave = mouseX > this.l - 210 && mouseX < this.l - 154 && mouseY > this.m - 30 && mouseY < this.m - 18;
            a(this.l - 268, this.m - 30, this.l - 213, this.m - 18, hoverCancel ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE);
            a(this.l - 210, this.m - 30, this.l - 154, this.m - 18, hoverSave ? ModColor.toRGB(100, 200, 100, 200) : Integer.MAX_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("button_cancel"), this.l - 248 + 22 - 14, this.m - 30 + 2, Integer.MAX_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("button_save"), this.l - 200 + 23 - 4, this.m - 30 + 2, Integer.MAX_VALUE);
            this.textFieldMessage.g();
            this.textFieldAutoTextKeyCode.g();
            if (this.selectedAutoText != null && this.selectedAutoText.isServerBound()) {
                this.textFieldAutoTextServerAddress.g();
            }
        }
        this.c(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("ingame_chat_tab_autotext"), this.l - 150, this.m - 210, -1);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        if (this.selectedAutoText == null && mouseX > this.l - 165 && mouseX < this.l - 152 && mouseY > this.m - 200 && mouseY < this.m - 200 + 13) {
            this.loadAutoText(new AutoTextKeyBinds.AutoText("", false, false, false, -1, false, false, ""));
        }
        if (this.selectedAutoText == null) {
            int row = 0;
            AutoTextKeyBinds.AutoText todoDelete = null;
            for (final AutoTextKeyBinds.AutoText component : LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds()) {
                final double posY = this.m - 195 + row * 10 + this.scrollbar.getScrollY();
                ++row;
                if (posY >= this.m - 200) {
                    if (posY > this.m - 25) {
                        continue;
                    }
                    if (mouseX > this.l - 12 - 1 && mouseX < this.l - 12 + 7 && mouseY > posY && mouseY < posY + 8.0) {
                        todoDelete = component;
                    }
                    else {
                        if (mouseX <= this.l - 150 + 1 || mouseX >= this.l - 2 - 1 || mouseY <= posY - 1.0 || mouseY >= posY + 9.0) {
                            continue;
                        }
                        this.loadAutoText(component);
                    }
                }
            }
            if (todoDelete != null) {
                LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds().remove(todoDelete);
                LabyMod.getInstance().getChatToolManager().saveTools();
            }
        }
        else {
            final int relX = this.l - 270;
            final int relY = this.m - 200;
            if (this.isHoverElementCheckbox("with_alt", this.selectedAutoText.isKeyAlt(), relX, relY + 46 + 11, mouseX, mouseY)) {
                this.selectedAutoText.setKeyAlt(!this.selectedAutoText.isKeyAlt());
            }
            if (this.isHoverElementCheckbox("with_shift", this.selectedAutoText.isKeyShift(), relX, relY + 46, mouseX, mouseY)) {
                this.selectedAutoText.setKeyShift(!this.selectedAutoText.isKeyShift());
            }
            if (this.isHoverElementCheckbox("with_ctrl", this.selectedAutoText.isKeyCtrl(), relX, relY + 46 + 22, mouseX, mouseY)) {
                this.selectedAutoText.setKeyCtrl(!this.selectedAutoText.isKeyCtrl());
            }
            if (this.isHoverElementCheckbox("send_instantly", this.selectedAutoText.isSendNotInstantly(), relX, relY + 46 + 33, mouseX, mouseY)) {
                this.selectedAutoText.setSendNotInstantly(!this.selectedAutoText.isSendNotInstantly());
            }
            if (this.isHoverElementCheckbox("server_bound", this.selectedAutoText.isServerBound(), relX, relY + 46 + 44, mouseX, mouseY)) {
                this.selectedAutoText.setServerBound(!this.selectedAutoText.isServerBound());
                if (this.selectedAutoText.isServerBound() && this.textFieldAutoTextServerAddress.b().isEmpty()) {
                    String value = "";
                    final bse serverData = bib.z().C();
                    if (serverData == null && bib.z().E()) {
                        value = "singleplayer";
                    }
                    else {
                        value = serverData.b;
                    }
                    this.textFieldAutoTextServerAddress.a(ModUtils.getProfileNameByIp(value));
                }
            }
            final boolean hoverCancel = mouseX > this.l - 268 && mouseX < this.l - 213 && mouseY > this.m - 30 && mouseY < this.m - 18;
            final boolean hoverSave = mouseX > this.l - 210 && mouseX < this.l - 154 && mouseY > this.m - 30 && mouseY < this.m - 18;
            if (hoverCancel) {
                this.selectedAutoText = null;
            }
            if (hoverSave) {
                this.selectedAutoText.setMessage(this.textFieldMessage.b());
                this.selectedAutoText.setServerAddress(this.textFieldAutoTextServerAddress.b());
                if (this.selectedAutoText.getMessage().replaceAll(" ", "").isEmpty() || this.selectedAutoText.getKeyCode() == -1) {
                    this.markMessageRed = this.textFieldMessage.b().replaceAll(" ", "").isEmpty();
                    this.markKeybindRed = (this.selectedAutoText.getKeyCode() == -1);
                }
                if (!this.markMessageRed && !this.markKeybindRed) {
                    if (!LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds().contains(this.selectedAutoText)) {
                        LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds().add(this.selectedAutoText);
                    }
                    LabyMod.getInstance().getChatToolManager().saveTools();
                    this.selectedAutoText = null;
                    this.b();
                }
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.selectedAutoText != null && this.textFieldMessage.m()) {
            this.textFieldMessage.a(typedChar, keyCode);
            if (keyCode == 15) {
                this.textFieldMessage.b(false);
                this.textFieldAutoTextKeyCode.b(true);
            }
            return;
        }
        if (this.selectedAutoText != null && this.textFieldAutoTextServerAddress.m()) {
            this.textFieldAutoTextServerAddress.a(typedChar, keyCode);
            if (keyCode == 15) {
                this.textFieldAutoTextServerAddress.b(false);
                this.textFieldMessage.b(true);
            }
            return;
        }
        if (this.selectedAutoText != null && keyCode != 56 && keyCode != 29 && keyCode != 42 && this.textFieldAutoTextKeyCode.m()) {
            this.textFieldAutoTextKeyCode.b(false);
            try {
                this.textFieldAutoTextKeyCode.a(Keyboard.getKeyName(keyCode));
            }
            catch (Exception e) {
                this.textFieldAutoTextKeyCode.a(String.valueOf(keyCode));
            }
            this.selectedAutoText.setKeyCode(keyCode);
            this.selectedAutoText.setKeyAlt(Keyboard.isKeyDown(56));
            this.selectedAutoText.setKeyCtrl(Keyboard.isKeyDown(29));
            this.selectedAutoText.setKeyShift(Keyboard.isKeyDown(42));
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    protected void b(final int mouseX, final int mouseY, final int mouseButton) {
        super.b(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        this.textFieldMessage.a(mouseX, mouseY, mouseButton);
        this.textFieldAutoTextKeyCode.a(mouseX, mouseY, mouseButton);
        if (this.selectedAutoText != null && this.selectedAutoText.isServerBound()) {
            this.textFieldAutoTextServerAddress.a(mouseX, mouseY, mouseButton);
        }
    }
    
    private void drawElementTextField(String prefix, final bje textField, final int x, final int y, final int mouseX, final int mouseY) {
        prefix = LanguageManager.translate("autotext_" + prefix) + ":";
        this.c(LabyModCore.getMinecraft().getFontRenderer(), prefix, x + 2, y + 2, Integer.MAX_VALUE);
        a(x + 2, y + 12, x + 2 + 114, y + 12 + 10, ((this.markMessageRed && textField != null && textField.equals(this.textFieldMessage)) || (this.markKeybindRed && textField != null && textField.equals(this.textFieldAutoTextKeyCode))) ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE);
        if (textField == null) {
            return;
        }
        LabyModCore.getMinecraft().setTextFieldXPosition(textField, x + 2);
        LabyModCore.getMinecraft().setTextFieldYPosition(textField, y + 13);
        textField.a(false);
        textField.f(120);
    }
    
    private void drawElementCheckBox(String text, final boolean check, int x, final int y, final int mouseX, final int mouseY) {
        final boolean hover = this.isHoverElementCheckbox(text, check, x, y, mouseX, mouseY);
        text = LanguageManager.translate("autotext_" + text);
        this.c(LabyModCore.getMinecraft().getFontRenderer(), text, x + 2, y + 2, Integer.MAX_VALUE);
        x += LabyModCore.getMinecraft().getFontRenderer().a(text) + 2;
        a(x + 3, y + 1, x + 12, y + 10, hover ? 2147483547 : Integer.MAX_VALUE);
        if (!check) {
            return;
        }
        this.a(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl("a") + "\u2714", x + 8, y + 1, Integer.MAX_VALUE);
    }
    
    private boolean isHoverElementCheckbox(String text, final boolean check, int x, final int y, final int mouseX, final int mouseY) {
        text = LanguageManager.translate("autotext_" + text);
        x += LabyModCore.getMinecraft().getFontRenderer().a(text) + 2;
        return mouseX > x + 3 && mouseX < x + 12 && mouseY > y + 1 && mouseY < y + 10;
    }
    
    private void loadAutoText(final AutoTextKeyBinds.AutoText autoText) {
        if (autoText == null) {
            return;
        }
        this.selectedAutoText = autoText;
        this.markMessageRed = false;
        this.markKeybindRed = false;
        this.textFieldMessage.a(autoText.getMessage());
        this.textFieldAutoTextServerAddress.a((autoText.getServerAddress() == null) ? "" : autoText.getServerAddress());
        this.textFieldAutoTextKeyCode.a((autoText.getKeyCode() == -1) ? "" : Keyboard.getKeyName(autoText.getKeyCode()));
    }
}
