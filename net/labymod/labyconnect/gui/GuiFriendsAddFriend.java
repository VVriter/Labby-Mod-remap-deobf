//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui;

import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.labyconnect.user.*;
import java.util.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.utils.*;
import java.awt.*;

public class GuiFriendsAddFriend extends blk
{
    public static String response;
    private blk lastScreen;
    private ModTextField username;
    private bja done;
    private bja cancel;
    private String error;
    private long time;
    private boolean flash;
    private boolean check;
    private boolean canAdd;
    private String defaultQuery;
    
    public GuiFriendsAddFriend(final blk lastScreen, final String friendAddQuery) {
        this.error = "";
        this.time = 0L;
        this.flash = false;
        this.check = false;
        this.canAdd = true;
        this.defaultQuery = "";
        this.lastScreen = lastScreen;
        this.defaultQuery = friendAddQuery;
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        (this.username = new ModTextField(-1, LabyMod.getInstance().getDrawUtils().fontRenderer, this.l / 2 - 100, this.m / 2 - 20, 200, 20)).setBlacklistWord(" ");
        this.username.setMaxStringLength(16);
        this.username.setText(this.defaultQuery);
        if (this.defaultQuery != null && !this.defaultQuery.isEmpty()) {
            this.username.setCursorPositionEnd();
            this.username.setSelectionPos(0);
        }
        this.done = new bja(0, this.l / 2 + 3, this.m / 2 + 5, 98, 20, LanguageManager.translate("button_request_user"));
        this.n.add(this.done);
        this.cancel = new bja(1, this.l / 2 - 101, this.m / 2 + 5, 98, 20, LanguageManager.translate("button_cancel"));
        this.n.add(this.cancel);
        super.b();
        this.check();
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (!this.check && this.username.textboxKeyTyped(typedChar, keyCode)) {
            this.check();
        }
        if (this.done.l && (keyCode == 28 || keyCode == 156)) {
            this.a(this.done);
        }
        if (keyCode == 1) {
            this.j.a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    private void check() {
        final ArrayList<ChatUser> list = new ArrayList<ChatUser>(LabyMod.getInstance().getLabyConnect().getFriends());
        this.canAdd = true;
        for (final ChatUser p : list) {
            if (p.getGameProfile().getName().equalsIgnoreCase(this.username.getText())) {
                this.canAdd = false;
                break;
            }
        }
        if (this.username.getText().equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
            this.canAdd = false;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 0) {
            this.check = true;
            LabyMod.getInstance().getLabyConnect().getClientConnection().sendPacket((Packet)new PacketPlayRequestAddFriend(this.username.getText()));
            this.username.setText("");
        }
        if (button.k == 1) {
            this.j.a(this.lastScreen);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        if (GuiFriendsAddFriend.response != null) {
            this.error = GuiFriendsAddFriend.response;
            this.check = false;
            GuiFriendsAddFriend.response = null;
            this.flash = true;
            this.time = System.currentTimeMillis();
        }
        this.done.l = (!this.check && !this.username.getText().isEmpty() && this.canAdd);
        LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate("minecraft_name") + ":", this.l / 2 - 100, this.m / 2 - 33);
        if (!this.error.isEmpty()) {
            if (this.error.contains("true")) {
                a(0, 10, this.l, 30, ModColor.toRGB(30, 220, 100, 200));
                LabyMod.getInstance().getDrawUtils().drawCenteredString("The request has been sent!", this.l / 2, 16.0);
            }
            else {
                final String c = ModColor.cl("f");
                a(0, 10, this.l, 30, Color.RED.getRGB());
                if (this.time + 1000L > System.currentTimeMillis() && this.flash) {
                    LabyMod.getInstance().getDrawUtils().drawCenteredString(c + "Error: " + this.error, this.l / 2 - 1, 16.0);
                }
                else {
                    LabyMod.getInstance().getDrawUtils().drawCenteredString(c + "Error: " + this.error, this.l / 2, 16.0);
                }
            }
            this.flash = !this.flash;
        }
        if (this.check) {
            a(0, 10, this.l, 30, Color.BLUE.getRGB());
            LabyMod.getInstance().getDrawUtils().drawCenteredString("Loading..", this.l / 2 - 1, 16.0);
        }
        this.username.drawTextBox();
        super.a(mouseX, mouseY, partialTicks);
    }
    
    static {
        GuiFriendsAddFriend.response = null;
    }
}
