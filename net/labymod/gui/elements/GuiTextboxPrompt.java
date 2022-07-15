//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.utils.*;
import net.labymod.core.*;
import net.labymod.main.*;
import java.io.*;

public class GuiTextboxPrompt extends blk
{
    private ModTextField field;
    private blk backgroundScreen;
    private String title;
    private String textSubmit;
    private String textCancel;
    private String content;
    private Consumer<String> callback;
    private bja buttonSave;
    private bja buttonCancel;
    private boolean passwordField;
    
    public GuiTextboxPrompt(final blk backgroundScreen, final String title, final String textSubmit, final String textCancel, final String content, final Consumer<String> callback) {
        this.passwordField = false;
        this.backgroundScreen = backgroundScreen;
        this.title = title;
        this.textSubmit = textSubmit;
        this.textCancel = textCancel;
        this.content = content;
        this.callback = callback;
    }
    
    public GuiTextboxPrompt setIsPassword(final boolean passwordField) {
        this.passwordField = passwordField;
        return this;
    }
    
    public void b() {
        super.b();
        this.backgroundScreen.l = this.l;
        this.backgroundScreen.m = this.m;
        this.n.clear();
        (this.field = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 100, this.m / 2, 200, 20)).setPasswordBox(this.passwordField);
        this.field.setText(this.content);
        this.field.setFocused(true);
        this.field.setMaxStringLength(60);
        this.field.setCursorPositionEnd();
        this.n.add(this.buttonSave = new bja(1, this.l / 2 + 10, this.m / 2 + 25, 90, 20, this.textSubmit));
        this.n.add(this.buttonCancel = new bja(2, this.l / 2 - 100, this.m / 2 + 25, 90, 20, this.textCancel));
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.backgroundScreen.a(mouseX, mouseY, partialTicks);
        LabyMod.getInstance().getDrawUtils().drawIngameBackground();
        this.field.setFocused(true);
        this.field.drawTextBox();
        this.buttonSave.l = !this.field.getText().isEmpty();
        LabyMod.getInstance().getDrawUtils().drawCenteredString(this.title, this.l / 2, this.m / 2 - 25);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    public void e() {
        super.e();
        this.field.updateCursorCounter();
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == this.buttonSave.k) {
            this.callback.accept(this.field.getText());
            bib.z().a(this.backgroundScreen);
        }
        if (button.k == this.buttonCancel.k) {
            bib.z().a(this.backgroundScreen);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.a(this.buttonCancel);
            return;
        }
        if (keyCode == 28) {
            this.a(this.buttonSave);
            return;
        }
        super.a(typedChar, keyCode);
        this.field.textboxKeyTyped(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.field.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
