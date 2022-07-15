//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.core.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;

public class GuiInputPrompt extends blk
{
    private String message;
    private String value;
    private String placeholder;
    private int maxLength;
    private Consumer<String> consumer;
    private ModTextField textField;
    
    public GuiInputPrompt(final String message, final String value, final String placeholder, final int maxLength, final Consumer<String> consumer) {
        this.message = message;
        this.value = value;
        this.placeholder = placeholder;
        this.maxLength = maxLength;
        this.consumer = consumer;
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        this.n.add(new bja(0, this.l / 2 - 50, this.m / 2 + 25, 100, 20, "Submit"));
        (this.textField = new ModTextField(1, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 100, this.m / 2, 200, 20)).setText(this.value);
        this.textField.setMaxStringLength(this.maxLength);
        this.textField.setFocused(true);
        this.textField.setCursorPositionEnd();
        this.textField.setPlaceHolder(this.placeholder);
        super.b();
    }
    
    public void m() {
        super.m();
        Keyboard.enableRepeatEvents(false);
        this.consumer.accept(this.textField.getText());
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1 || keyCode == 28) {
            bib.z().a((blk)null);
        }
        else {
            this.textField.textboxKeyTyped(typedChar, keyCode);
            super.a(typedChar, keyCode);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.a(mouseX, mouseY, mouseButton);
    }
    
    public void e() {
        this.textField.updateCursorCounter();
        super.e();
    }
    
    protected void a(final bja button) throws IOException {
        switch (button.k) {
            case 0: {
                bib.z().a((blk)null);
                break;
            }
        }
        super.a(button);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final List<String> list = draw.listFormattedStringToWidth(this.message, this.l / 2);
        int y = this.m / 2 - 10 - list.size() * 10;
        for (final String line : list) {
            draw.drawCenteredString(line, this.l / 2, y);
            y += 10;
        }
        this.textField.drawTextBox();
        super.a(mouseX, mouseY, partialTicks);
    }
}
