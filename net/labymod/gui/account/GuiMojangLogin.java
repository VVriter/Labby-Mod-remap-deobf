//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.account;

import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.core.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import java.awt.*;
import java.util.concurrent.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import net.labymod.accountmanager.storage.account.*;
import net.labymod.accountmanager.authentication.*;
import net.labymod.accountmanager.*;
import java.io.*;

public class GuiMojangLogin extends blk
{
    private blk lastScreen;
    private boolean saveAccount;
    private ModTextField fieldUsername;
    private ModTextField fieldPassword;
    private bja buttonLogin;
    private String lastErrorMessage;
    private long shakingError;
    private String renderHeadName;
    
    public GuiMojangLogin(final blk lastScreen, final boolean saveAccount) {
        this.lastErrorMessage = null;
        this.shakingError = 0L;
        this.lastScreen = lastScreen;
        this.saveAccount = saveAccount;
    }
    
    public void b() {
        super.b();
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        this.fieldUsername = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 110, this.m / 2 - 60, 220, 20);
        (this.fieldPassword = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 110, this.m / 2 - 10, 220, 20)).setPasswordBox(true);
        this.fieldUsername.setMaxStringLength(90000);
        this.fieldPassword.setMaxStringLength(90000);
        this.n.add(new bja(1, this.l / 2 - 110, this.m / 2 + 30, 100, 20, LanguageManager.translate("button_cancel")));
        this.n.add(this.buttonLogin = new bja(2, this.l / 2, this.m / 2 + 30, 110, 20, ""));
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        this.fieldUsername.drawTextBox();
        this.fieldPassword.drawTextBox();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawString(LanguageManager.translate("username_email") + ":", this.l / 2 - 110, this.m / 2 - 75);
        draw.drawString(LanguageManager.translate("password") + ":", this.l / 2 - 110, this.m / 2 - 25);
        final String offlineString = (!this.fieldUsername.getText().isEmpty() && this.fieldPassword.getText().isEmpty()) ? (ModColor.cl("8") + " (" + ModColor.cl("c") + LanguageManager.translate("offline") + ModColor.cl("8") + ")") : "";
        this.buttonLogin.j = LanguageManager.translate(this.saveAccount ? "button_add" : "button_login") + offlineString;
        this.buttonLogin.l = !this.fieldUsername.getText().isEmpty();
        bib.z().N().a(ModTextures.MISC_MOJANGSTUDIOS);
        LabyMod.getInstance().getDrawUtils().drawTexture(this.l / 2 + 95, this.m / 2 - 80, 256.0, 256.0, 15.0, 15.0);
        if (this.renderHeadName != null && !this.renderHeadName.contains("@")) {
            LabyMod.getInstance().getDrawUtils().drawPlayerHead(this.renderHeadName, this.fieldUsername.xPosition - this.fieldUsername.height - 4, this.fieldUsername.yPosition, this.fieldUsername.height);
        }
        if (this.lastErrorMessage != null) {
            draw.drawRectangle(0, 0, this.l, 16, Color.RED.getRGB());
            int shaking = (int)(System.currentTimeMillis() % 10L) - 5;
            if (this.shakingError + 1000L < System.currentTimeMillis()) {
                shaking = 0;
            }
            draw.drawCenteredString(this.lastErrorMessage, (int)(this.l / 2.0f + shaking), 4.0);
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 1: {
                bib.z().a(this.lastScreen);
                break;
            }
            case 2: {
                final AsyncAccountManager accountManager = LabyMod.getInstance().getAccountManager();
                final String username = this.fieldUsername.getText();
                final String password = this.fieldPassword.getText();
                if (this.fieldPassword.getText().isEmpty()) {
                    final String name;
                    UUID uuid;
                    ExternalAccount account2;
                    final AsyncAccountManager asyncAccountManager;
                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {
                            uuid = UUIDFetcher.getUUID(name);
                            if (uuid == null) {
                                uuid = UUID.randomUUID();
                            }
                            account2 = new ExternalAccount(uuid, name, "0");
                            account2.setSessionState(AccountSessionState.OFFLINE);
                            if (this.saveAccount) {
                                asyncAccountManager.getStorage().addAccount(account2);
                                asyncAccountManager.saveAsync(new Runnable[0]);
                            }
                            LabyMod.getInstance().setSession((Account)account2);
                            bib.z().a(this.lastScreen);
                        }
                        catch (Exception e2) {
                            this.lastErrorMessage = ModColor.cl('c') + e2.getMessage();
                        }
                        return;
                    });
                    break;
                }
                final AsyncAccountAuthentication auth = (AsyncAccountAuthentication)accountManager.getStorage().getAuthentication();
                final AsyncAccountManager asyncAccountManager2;
                auth.authenticateMojangAsync(username, password, account -> {
                    try {
                        if (this.saveAccount) {
                            asyncAccountManager2.getStorage().addAccount(account);
                            asyncAccountManager2.saveAsync(new Runnable[0]);
                        }
                        LabyMod.getInstance().setSession((Account)account);
                        bib.z().a(this.lastScreen);
                    }
                    catch (Exception e3) {
                        this.lastErrorMessage = ModColor.cl('c') + e3.getMessage();
                    }
                    return;
                }, e -> {
                    this.lastErrorMessage = ModColor.cl('c') + e.getMessage();
                    this.shakingError = System.currentTimeMillis();
                    return;
                });
                break;
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        final boolean flag = this.fieldUsername.isFocused();
        this.fieldUsername.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.fieldPassword.mouseClicked(mouseX, mouseY, mouseButton) && flag && !this.fieldUsername.isFocused()) {
            this.renderHeadName = this.fieldUsername.getText();
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            bib.z().a(this.lastScreen);
            return;
        }
        if (keyCode == 28 && this.buttonLogin.l) {
            this.a(this.buttonLogin);
            return;
        }
        if (keyCode == 15) {
            final boolean focusUsername = this.fieldUsername.isFocused();
            final boolean focusPassword = this.fieldPassword.isFocused();
            this.fieldUsername.setFocused(focusPassword);
            this.fieldPassword.setFocused(focusUsername);
            if (this.fieldPassword.isFocused()) {
                this.renderHeadName = this.fieldUsername.getText();
            }
            return;
        }
        this.fieldUsername.textboxKeyTyped(typedChar, keyCode);
        this.fieldPassword.textboxKeyTyped(typedChar, keyCode);
        super.a(typedChar, keyCode);
    }
    
    public void e() {
        super.e();
        this.fieldUsername.updateCursorCounter();
        this.fieldPassword.updateCursorCounter();
    }
}
