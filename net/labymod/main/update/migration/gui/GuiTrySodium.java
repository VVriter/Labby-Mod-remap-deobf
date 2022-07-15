//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration.gui;

import java.util.function.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;

public class GuiTrySodium extends blk
{
    private final blk previousScreen;
    private final Consumer<Boolean> callback;
    private final boolean hasOptiFine;
    
    public GuiTrySodium(final blk previousScreen, final Consumer<Boolean> callback, final boolean hasOptiFine) {
        this.previousScreen = previousScreen;
        this.callback = callback;
        this.hasOptiFine = hasOptiFine;
    }
    
    public void b() {
        super.b();
        this.n.clear();
        this.n.add(new bja(1, this.l / 2 + 5, this.m / 2 + 80, 125, 20, LanguageManager.translate("update_minecraft_sodium_install")));
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode != 1) {
            super.a(typedChar, keyCode);
        }
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 1: {
                this.callback.accept(true);
                bib.z().a(this.previousScreen);
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final String latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion().minecraftVersion;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawString(ModColor.cl('e') + ModColor.cl('n') + LanguageManager.translate("update_minecraft_sodium_title"), this.l / 2 - 150, this.m / 2 - 80);
        final String message = "update_minecraft_sodium_description";
        final List<String> list = draw.listFormattedStringToWidth(ModColor.createColors(LanguageManager.translate(message, new Object[] { latestVersion })), 300);
        int y = 0;
        for (final String line : list) {
            draw.drawString(line, this.l / 2 - 150, this.m / 2 - 50 + y);
            y += 10;
        }
        bib.z().N().a(ModTextures.MISC_SODIUM);
        draw.drawTexture(this.l / 2 + 150 - 30, this.m / 2 - 95, 256.0, 256.0, 30.0, 30.0);
        final boolean mouseOver = mouseX > this.l / 2 - 150 && mouseX < this.l / 2 && mouseY > this.m / 2 + 80 && mouseY < this.m / 2 + 100;
        final String skipButtonText = LanguageManager.translate(this.hasOptiFine ? "update_minecraft_sodium_skip_optifine" : "update_minecraft_sodium_skip");
        draw.drawString(ModColor.cl(mouseOver ? 'c' : '7') + skipButtonText, this.l / 2 - 150, this.m / 2 + 86);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        final boolean mouseOver = mouseX > this.l / 2 - 150 && mouseX < this.l / 2 && mouseY > this.m / 2 + 80 && mouseY < this.m / 2 + 100;
        if (mouseOver) {
            this.callback.accept(false);
            bib.z().a(this.previousScreen);
        }
    }
}
