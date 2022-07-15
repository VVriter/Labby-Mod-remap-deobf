//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.gui;

import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;
import java.io.*;
import net.labymod.core.asm.*;

public class GuiNotAllowed extends blk
{
    private String message;
    
    public GuiNotAllowed(String message) {
        if (message == null || message.isEmpty()) {
            message = LanguageManager.translate("chat_unknown_ban_reason");
        }
        this.message = ModColor.createColors(message.replace("\\n", "\n"));
    }
    
    public void b() {
        super.b();
        this.n.add(new bja(0, this.l / 2 - 50, this.m - 50, 100, 20, "Exit"));
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        int y = this.m / 3;
        for (final String forcedLine : this.message.split("\n")) {
            for (final String line : draw.listFormattedStringToWidth(forcedLine, this.l / 2)) {
                draw.drawCenteredString(line, this.l / 2, y);
                y += 10;
            }
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 0) {
            bib.z().n();
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (!LabyModCoreMod.isObfuscated()) {
            super.a(typedChar, keyCode);
        }
    }
}
