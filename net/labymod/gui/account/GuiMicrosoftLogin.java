//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.account;

import net.labymod.accountmanager.authentication.microsoft.oauth.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.accountmanager.authentication.*;
import net.labymod.accountmanager.storage.account.*;
import net.labymod.accountmanager.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import net.labymod.accountmanager.authentication.microsoft.*;
import java.util.*;

public class GuiMicrosoftLogin extends blk
{
    private final blk lastScreen;
    private final boolean saveAccount;
    private String lastStatusMessage;
    private OAuthServer oAuth;
    
    public GuiMicrosoftLogin(final blk lastScreen, final boolean saveAccount) {
        this.lastScreen = lastScreen;
        this.saveAccount = saveAccount;
        this.lastStatusMessage = ModColor.cl('a') + LanguageManager.translate("microsoft_login_state_new_window");
        try {
            this.oAuth = new OAuthServer();
            LabyMod.getInstance().openWebpage(this.oAuth.getUrl().toString(), false);
            this.oAuth.listenForCodeAsync(code -> {
                if (code == null) {
                    this.lastStatusMessage = ModColor.cl('c') + "Login cancelled";
                }
                else {
                    this.authUsingCode(code);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastStatusMessage = ModColor.cl('c') + "Login process already started? Maybe restart your computer...";
        }
    }
    
    public void m() {
        super.m();
        this.oAuth.close();
    }
    
    public void b() {
        super.b();
        this.n.add(new bja(0, this.l / 2 - 100, this.m / 2 + 10, LanguageManager.translate("button_cancel")));
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 0) {
            bib.z().a(this.lastScreen);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        int listY = 0;
        if (this.lastStatusMessage != null) {
            final List<String> list = draw.listFormattedStringToWidth(this.lastStatusMessage, this.l / 2);
            for (final String line : list) {
                draw.drawCenteredString(line, (int)(this.l / 2.0f), (int)(this.m / 2.0f - 30.0f + listY));
                listY += 10;
            }
        }
        final int size = 20;
        bib.z().N().a(ModTextures.MISC_MICROSOFT);
        draw.drawTexture(this.l / 2.0f - size / 2.0f - 70.0f + 20.0f, 40.0f - size / 2.0f, 256.0, 256.0, size, size);
        draw.drawCenteredString("Microsoft", this.l / 2.0f + 20.0f, 40.0f - size / 2.0f + 3.0f, 2.0);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            bib.z().a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    private void authUsingCode(final String code) {
        final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
        final AsyncAccountAuthentication auth = (AsyncAccountAuthentication)accountManager.getStorage().getAuthentication();
        auth.getMicrosoftService().setCallback(stateId -> this.lastStatusMessage = ModColor.cl('e') + LanguageManager.translate("microsoft_login_state_" + stateId.name().toLowerCase(Locale.ROOT)));
        final AsyncAccountManager asyncAccountManager;
        auth.authenticateMicrosoftAsync(code, account -> {
            try {
                if (this.saveAccount) {
                    asyncAccountManager.getStorage().addAccount(account);
                    asyncAccountManager.saveAsync(new Runnable[0]);
                }
                LabyMod.getInstance().setSession((Account)account);
                bib.z().a(this.lastScreen);
            }
            catch (Exception e2) {
                this.lastStatusMessage = ModColor.cl('c') + e2.getMessage();
            }
        }, e -> this.lastStatusMessage = ModColor.cl('c') + e.getMessage());
    }
}
