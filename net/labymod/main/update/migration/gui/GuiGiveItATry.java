//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration.gui;

import java.util.function.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;

public class GuiGiveItATry extends blk
{
    private final blk previousScreen;
    private final Consumer<Integer> callback;
    
    public GuiGiveItATry(final blk previousScreen, final Consumer<Integer> callback) {
        this.previousScreen = previousScreen;
        this.callback = callback;
    }
    
    public void b() {
        super.b();
        this.n.clear();
        this.n.add(new bja(0, this.l / 2 - 60, this.m / 2 + 80, 100, 20, LanguageManager.translate("update_minecraft_try_later")));
        this.n.add(new bja(1, this.l / 2 + 50, this.m / 2 + 80, 100, 20, ModColor.cl('a') + LanguageManager.translate("update_minecraft_try_update_now")));
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 0: {
                this.callback.accept(1);
                bib.z().a(this.previousScreen);
                break;
            }
            case 1: {
                this.callback.accept(2);
                bib.z().a(this.previousScreen);
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final String latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion().minecraftVersion;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bib.z().N().a(ModTextures.MISC_GIVE_IT_A_TRY);
        draw.drawTexture(this.l / 2 + 150 - 40, this.m / 2 - 115, 256.0, 256.0, 45.0, 60.0);
        draw.drawString(ModColor.cl('e') + ModColor.cl('o') + LanguageManager.translate("update_minecraft_try_title"), this.l / 2 - 150, this.m / 2 - 100);
        final List<String> list = draw.listFormattedStringToWidth(ModColor.createColors(LanguageManager.translate("update_minecraft_try_description", new Object[] { latestVersion })), 600);
        int y = 0;
        for (final String line : list) {
            draw.drawString(line, this.l / 2 - 150, this.m / 2 - 70 + y);
            y += 10;
        }
        final boolean mouseOver = mouseX > this.l / 2 - 150 && mouseX < this.l / 2 && mouseY > this.m / 2 + 80 && mouseY < this.m / 2 + 100;
        draw.drawString(ModColor.cl(mouseOver ? 'c' : '7') + LanguageManager.translate("update_minecraft_sodium_skip"), this.l / 2 - 150, this.m / 2 + 86);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        final boolean mouseOver = mouseX > this.l / 2 - 150 && mouseX < this.l / 2 && mouseY > this.m / 2 + 80 && mouseY < this.m / 2 + 100;
        if (mouseOver) {
            this.callback.accept(0);
            bib.z().a(this.previousScreen);
        }
    }
}
