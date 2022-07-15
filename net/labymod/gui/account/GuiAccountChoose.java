//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.account;

import net.labymod.main.lang.*;
import net.labymod.gui.elements.*;
import java.io.*;
import net.labymod.main.*;

public class GuiAccountChoose extends blk
{
    private blk lastScreen;
    private boolean saveAccount;
    
    public GuiAccountChoose(final blk lastScreen, final boolean saveAccount) {
        this.lastScreen = lastScreen;
        this.saveAccount = saveAccount;
    }
    
    public void b() {
        super.b();
        this.n.add(new bja(0, this.l / 2 - 50, this.m / 2 + 30, 100, 20, LanguageManager.translate("button_cancel")));
        final String stringMicrosoft = LanguageManager.translate("microsoft_account");
        this.n.add(new TabbedGuiButton(ModTextures.MISC_MICROSOFT, 1, this.l / 2 - 75, this.m / 2 - 45, 150, 20, stringMicrosoft));
        final String stringMojang = LanguageManager.translate("mojang_account");
        this.n.add(new TabbedGuiButton(ModTextures.MISC_MOJANGSTUDIOS, 2, this.l / 2 - 75, this.m / 2 - 20, 150, 20, stringMojang));
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 0: {
                bib.z().a(this.lastScreen);
                break;
            }
            case 1: {
                bib.z().a((blk)new GuiMicrosoftLogin(this.lastScreen, this.saveAccount));
                break;
            }
            case 2: {
                bib.z().a((blk)new GuiMojangLogin(this.lastScreen, this.saveAccount));
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        LabyMod.getInstance().getDrawUtils().drawCenteredString(LanguageManager.translate("account_manager_choose"), this.l / 2.0f, this.m / 2.0f - 80.0f);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            bib.z().a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
}
