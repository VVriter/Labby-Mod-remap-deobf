//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui;

import net.labymod.gui.layout.*;
import org.lwjgl.input.*;
import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.main.lang.*;
import net.labymod.utils.*;
import java.io.*;

public class GuiFriendsNotConnected extends blk
{
    private WindowLayout prevTabSelected;
    private bja buttonLogin;
    
    public GuiFriendsNotConnected(final WindowLayout prevTabSelected) {
        this.prevTabSelected = prevTabSelected;
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        this.n.add(this.buttonLogin = new bja(6, this.l / 2 - 100, this.m / 2 + 10, 200, 20, ""));
        Tabs.initGui((blk)this);
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(0.0);
        final boolean isOnline = LabyMod.getInstance().getLabyConnect().isOnline();
        final EnumConnectionState currentConnectionState = LabyMod.getInstance().getLabyConnect().getClientConnection().getCurrentConnectionState();
        final boolean isOffline = currentConnectionState == EnumConnectionState.OFFLINE;
        this.buttonLogin.m = !isOnline;
        this.buttonLogin.l = isOffline;
        this.buttonLogin.j = (isOffline ? LanguageManager.translate("button_connect_to_chat") : currentConnectionState.getButtonState());
        if (isOnline) {
            bib.z().a((blk)this.prevTabSelected);
            return;
        }
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(0.0);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 41);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(41.0, 0.0, this.l);
        final String accountName = LabyMod.getInstance().getPlayerName();
        final EnumConnectionState connectionState = LabyMod.getInstance().getLabyConnect().getClientConnection().getCurrentConnectionState();
        LabyMod.getInstance().getDrawUtils().drawCenteredString(ModColor.cl("e") + "LabyMod Chat " + ModColor.cl("7") + "| " + ModColor.cl(connectionState.getDisplayColor()) + connectionState.name(), this.l / 2, this.m / 2 - 5);
        LabyMod.getInstance().getDrawUtils().drawRightString(accountName, this.l - 2, 29.0);
        LabyMod.getInstance().getDrawUtils().drawCenteredString(LabyMod.getInstance().getLabyConnect().getClientConnection().getLastKickMessage(), this.l / 2, this.m / 2 + 40);
        super.a(mouseX, mouseY, partialTicks);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 6) {
            LabyMod.getInstance().getLabyConnect().getClientConnection().connect();
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
    }
    
    public WindowLayout getPrevTabSelected() {
        return this.prevTabSelected;
    }
    
    public bja getButtonLogin() {
        return this.buttonLogin;
    }
}
