//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.account;

import net.labymod.accountmanager.*;
import net.labymod.gui.elements.*;
import java.util.function.*;
import net.labymod.main.lang.*;
import net.labymod.accountmanager.storage.*;
import net.labymod.accountmanager.storage.loader.external.model.*;
import java.io.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.accountmanager.storage.account.*;

public class GuiAccountManager extends blk
{
    private static boolean launcherSessionsRefreshed;
    private final AsyncAccountManager accountManager;
    private final blk lastScreen;
    private bja buttonSwitch;
    private bja buttonRemove;
    private final Scrollbar scrollbar;
    private Account selectedAccount;
    private Account hoveredAccount;
    private long lastLoginClick;
    
    public GuiAccountManager(final blk lastScreen) {
        this.lastLoginClick = 0L;
        this.lastScreen = lastScreen;
        this.accountManager = LabyMod.getInstance().getAccountManager();
        if (!GuiAccountManager.launcherSessionsRefreshed) {
            GuiAccountManager.launcherSessionsRefreshed = true;
            this.accountManager.refreshLauncherSessionsAsync((Consumer)new Consumer<Account>() {
                @Override
                public void accept(final Account account) {
                    if (!account.isMicrosoft()) {
                        try {
                            Thread.sleep(10000L);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Runnable[0]);
        }
        (this.scrollbar = new Scrollbar(21)).setSpeed(15);
    }
    
    public void b() {
        super.b();
        this.n.clear();
        this.n.add(new bja(0, this.l / 2 - 180, this.m - 25, 60, 20, LanguageManager.translate("button_add")));
        this.n.add(this.buttonRemove = new bja(1, this.l / 2 - 110, this.m - 25, 60, 20, LanguageManager.translate("button_remove")));
        this.n.add(this.buttonSwitch = new bja(2, this.l / 2 - 40, this.m - 25, 60, 20, LanguageManager.translate("button_switch")));
        this.n.add(new bja(3, this.l / 2 + 30, this.m - 25, 80, 20, LanguageManager.translate("button_direct_login")));
        this.n.add(new bja(4, this.l / 2 + 120, this.m - 25, 60, 20, LanguageManager.translate("button_cancel")));
        this.scrollbar.setPosition(this.l / 2 + 80, 35, this.l / 2 + 84, this.m - 35);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 0: {
                bib.z().a((blk)new GuiAccountChoose((blk)this, true));
                break;
            }
            case 1: {
                if (this.selectedAccount != null && this.selectedAccount.getStorageType() == StorageType.EXTERNAL) {
                    this.accountManager.getStorage().removeAccount((ExternalAccount)this.selectedAccount);
                    this.accountManager.saveAsync(new Runnable[0]);
                    break;
                }
                break;
            }
            case 2: {
                this.switchAccount();
                break;
            }
            case 3: {
                bib.z().a((blk)new GuiAccountChoose((blk)this, false));
                break;
            }
            case 4: {
                bib.z().a(this.lastScreen);
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        this.c();
        draw.drawOverlayBackground(0, 30, this.l, this.m - 20, 32);
        double y = 35.0 + this.scrollbar.getScrollY();
        final double x = this.l / 2.0f - 75.0f;
        this.hoveredAccount = null;
        final Account[] accounts2;
        final Account[] accounts = accounts2 = this.accountManager.getAccounts();
        for (final Account account : accounts2) {
            if (!account.isDemo()) {
                this.drawProfile(account, x, y, mouseX, mouseY);
                y += 21.0;
            }
        }
        this.scrollbar.update(accounts.length);
        this.scrollbar.draw();
        draw.drawOverlayBackground(0, 30);
        draw.drawOverlayBackground(this.m - 30, this.m);
        draw.drawGradientShadowTop(30.0, 0.0, this.l);
        draw.drawGradientShadowBottom(this.m - 30, 0.0, this.l);
        final boolean accountSelected = this.selectedAccount != null;
        final boolean accountExternal = accountSelected && this.selectedAccount.getStorageType() == StorageType.EXTERNAL;
        this.buttonRemove.l = accountExternal;
        this.buttonSwitch.l = accountSelected;
        if (this.buttonRemove.a() && accountSelected && !accountExternal) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, ModColor.cl('c') + LanguageManager.translate("account_manager_account_can_not_remove"));
        }
        draw.drawCenteredString(LanguageManager.translate("title_account_manager") + " (" + accounts.length + " " + LanguageManager.translate((accounts.length == 1) ? "account" : "accounts") + ")", this.l / 2.0f, 12.0);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    private void drawProfile(final Account account, final double x, final double y, final int mouseX, final int mouseY) {
        final LabyMod laby = LabyMod.getInstance();
        final DrawUtils draw = laby.getDrawUtils();
        final float width = 150.0f;
        final float height = 20.0f;
        final float avatarPadding = 2.0f;
        final float avatarSize = height - avatarPadding * 2.0f;
        final boolean hoverProfile = y < this.m - 30 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
        final float hoverOffset = hoverProfile ? 1.0f : 0.0f;
        if (account == this.selectedAccount) {
            draw.drawRectBorder(x, y, x + width, y + height, Integer.MAX_VALUE, 1.0);
        }
        final boolean loggedIn = LabyMod.getInstance().isSession(account);
        final int iconSizeOffset;
        final boolean hoverChecked = (iconSizeOffset = ((mouseX > x - 15.0 && mouseX < x - 15.0 + 10.0 && mouseY > y + 5.0 && mouseY < y + 5.0 + 10.0) ? 1 : 0)) != 0;
        if (loggedIn) {
            bib.z().N().a(ModTextures.BUTTON_CHECKBOX);
            draw.drawTexture(x - 15.0 - iconSizeOffset, y + 5.0 - iconSizeOffset, 255.0, 255.0, 10.0f + iconSizeOffset * 2.0f, 10.0f + iconSizeOffset * 2.0f);
        }
        final float headBrightness = 0.7f;
        GL11.glColor4f(headBrightness, headBrightness, headBrightness, 1.0f);
        if (account.isMicrosoft()) {
            final nf resourceLocation = laby.getDynamicTextureManager().getTexture("microsoft_avatar/" + account.getStorageType().name() + "/" + account.getUUID().toString(), account.getAvatarImage());
            bib.z().N().a(resourceLocation);
            draw.drawTexture(x + avatarPadding - hoverOffset, y + avatarPadding - hoverOffset, 256.0, 256.0, avatarSize + hoverOffset * 2.0f, avatarSize + hoverOffset * 2.0f);
        }
        else {
            draw.drawPlayerHead(account.getUUID(), (int)(x + avatarPadding - hoverOffset), (int)(y + avatarPadding - hoverOffset), (int)((int)avatarSize + hoverOffset * 2.0f));
        }
        final float textX = (float)(x + avatarSize + avatarPadding * 2.0f);
        final AccountSessionState state = account.getSessionState();
        final char color = (state == AccountSessionState.VALID) ? 'a' : ((state == AccountSessionState.VALIDATING || state == AccountSessionState.REFRESHING) ? 'e' : 'c');
        final String stateName = LanguageManager.translate("account_manager_account_" + state.name().toLowerCase());
        draw.drawString(ModColor.cl(hoverProfile ? 'f' : '7') + account.getUsername(), textX, y + avatarPadding);
        draw.drawString(ModColor.cl(color) + stateName, textX, y + avatarPadding + 10.0, 0.7);
        final boolean hoverAccountType = mouseX > x + 120.0 + 15.0 && mouseX < x + 120.0 + 15.0 + 10.0 && mouseY > y + 5.0 && mouseY < y + 5.0 + 10.0;
        bib.z().N().a(account.isMicrosoft() ? ModTextures.MISC_MICROSOFT : ModTextures.MISC_MOJANGSTUDIOS);
        draw.drawTexture(x + 120.0 + 15.0 - (double)(hoverAccountType ? 1 : 0), y + 5.0 - (double)(hoverAccountType ? 1 : 0), 255.0, 255.0, hoverAccountType ? 12.0 : 10.0, hoverAccountType ? 12.0 : 10.0);
        nf storageTypeIcon = null;
        String storageName = "Unknown";
        switch (account.getStorageType()) {
            case JAVA: {
                storageName = "Java";
                storageTypeIcon = ModTextures.MISC_JAVA;
                break;
            }
            case EXTERNAL: {
                storageName = "LabyMod";
                storageTypeIcon = ModTextures.LOGO_LABYMOD_LOGO;
                break;
            }
            case MICROSOFT: {
                storageName = "Microsoft";
                break;
            }
            case MICROSOFT_STORE: {
                storageName = "Microsoft Store";
                storageTypeIcon = ModTextures.MISC_MICROSOFT_STORE;
                break;
            }
        }
        if (storageTypeIcon != null) {
            bib.z().N().a(storageTypeIcon);
            draw.drawTexture(x + 120.0 + 15.0 - (hoverAccountType ? 1 : 0) + 5.0, y + 5.0 - (hoverAccountType ? 1 : 0) + 5.0, 255.0, 255.0, hoverAccountType ? 10.0 : 8.0, hoverAccountType ? 10.0 : 8.0);
        }
        if (hoverAccountType) {
            final String accountType = account.isMicrosoft() ? "Microsoft" : "Mojang";
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, ModColor.createColors(LanguageManager.translate("account_manager_account_type", accountType, storageName)));
        }
        if (hoverChecked && loggedIn) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, ModColor.cl('a') + LanguageManager.translate("account_manager_logged_in"));
        }
        if (hoverProfile) {
            this.hoveredAccount = account;
        }
    }
    
    private void switchAccount() {
        if (this.selectedAccount != null) {
            try {
                LabyMod.getInstance().setSession(this.selectedAccount);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final int button) throws IOException {
        if (this.hoveredAccount != null) {
            this.selectedAccount = this.hoveredAccount;
        }
        if (this.selectedAccount != null && this.hoveredAccount != null) {
            if (this.lastLoginClick + 300L > System.currentTimeMillis()) {
                this.a(this.buttonSwitch);
            }
            this.lastLoginClick = System.currentTimeMillis();
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        super.a(mouseX, mouseY, button);
    }
    
    public void b(final int mouseX, final int mouseY, final int button) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, button);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            bib.z().a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    static {
        GuiAccountManager.launcherSessionsRefreshed = false;
    }
}
