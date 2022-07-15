//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui;

import net.labymod.gui.elements.*;
import java.text.*;
import org.lwjgl.input.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.labyconnect.user.*;
import java.io.*;
import java.util.*;

public class GuiFriendsAbout extends blk
{
    private blk lastScreen;
    private Scrollbar scrollbar;
    private DateFormat formatTime;
    private DateFormat formatDate;
    private Map<String, String> infoList;
    private ChatUser player;
    
    public GuiFriendsAbout(final blk lastScreen, final ChatUser player) {
        this.scrollbar = new Scrollbar(10);
        this.formatTime = new SimpleDateFormat("HH:mm");
        this.formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.infoList = new HashMap<String, String>();
        this.lastScreen = lastScreen;
        this.player = player;
    }
    
    public void b() {
        super.b();
        Keyboard.enableRepeatEvents(true);
        this.n.add(new bja(1, this.l / 2 - 100, this.m - 37, 200, 20, LanguageManager.translate("button_done")));
        this.scrollbar.init();
        this.scrollbar.setPosition(this.l / 2 + 130, 40, this.l / 2 + 134, this.m - 40);
        this.scrollbar.setSpeed(10);
        this.infoList.clear();
        this.formatTime.setCalendar(Calendar.getInstance());
        this.formatTime.setTimeZone(TimeZone.getTimeZone(this.player.getTimeZone()));
        this.infoList.put(LanguageManager.translate("profile_timezone"), LabyMod.getInstance().getDrawUtils().trimStringToWidth(this.formatTime.getTimeZone().getID(), 120));
        this.infoList.put(LanguageManager.translate("profile_time"), this.formatTime.format(this.formatTime.getCalendar().getTime()));
        if (this.player.getFirstJoined() != 0L) {
            this.infoList.put(LanguageManager.translate("profile_first_joined"), this.formatDate.format(this.player.getFirstJoined()));
        }
        if (this.player.getLastOnline() != 0L) {
            this.infoList.put(LanguageManager.translate("profile_last_online"), this.formatDate.format(this.player.isOnline() ? System.currentTimeMillis() : this.player.getLastOnline()));
        }
        if (this.player.getContactAmount() != 0) {
            this.infoList.put(LanguageManager.translate("profile_friends"), "" + this.player.getContactAmount());
        }
        if (!this.player.getStatusMessage().isEmpty()) {
            this.infoList.put(LanguageManager.translate("profile_status_message"), LabyMod.getInstance().getDrawUtils().trimStringToWidth(this.player.getStatusMessage(), 100));
        }
        this.infoList.put(LanguageManager.translate("profile_status"), ModColor.cl(this.player.getStatus().getChatColor()) + this.player.getStatus().getName());
        if (this.player.getStatus() != UserStatus.OFFLINE) {
            this.infoList.put("Playing", (this.player.getCurrentServerInfo() != null && this.player.getCurrentServerInfo().isServerAvailable()) ? this.player.getCurrentServerInfo().getDisplayAddress() : "No");
        }
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.j.a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 1) {
            this.j.a(this.lastScreen);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        LabyMod.getInstance().getDrawUtils().drawDimmedOverlayBackground(this.l / 2 - 130, 40, this.l / 2 + 130, this.m - 40);
        double listY = 42.0 + this.scrollbar.getScrollY();
        final int entryHeight = 10;
        for (final Map.Entry<String, String> entry : this.infoList.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            final boolean hoverEntry = mouseX > this.l / 2 - 130 && mouseX < this.l / 2 + 130 && mouseY > listY && mouseY < listY + entryHeight + 1.0;
            final int hoverColor = hoverEntry ? 200 : 100;
            LabyMod.getInstance().getDrawUtils().drawRect(this.l / 2 - 130, listY, this.l / 2 + 130, listY + entryHeight, ModColor.toRGB(hoverColor, hoverColor, hoverColor, 30));
            LabyMod.getInstance().getDrawUtils().drawString(ModColor.cl("e") + key + ":", this.l / 2 - 120, listY + 1.0);
            LabyMod.getInstance().getDrawUtils().drawRightString(value, this.l / 2 + 120, listY + 1.0);
            listY += entryHeight + 1;
        }
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 40);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(this.m - 40, this.m);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(40.0, this.l / 2 - 130, this.l / 2 + 130);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(this.m - 40, this.l / 2 - 130, this.l / 2 + 130);
        LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate("profile_title", ModColor.cl(this.player.getStatus().getChatColor()) + this.player.getGameProfile().getName()), this.l / 2 - 109, 30.0, 1.0);
        bus.d(1.0f, 1.0f, 1.0f);
        LabyMod.getInstance().getDrawUtils().drawPlayerHead(this.player.getGameProfile(), this.l / 2 - 130, 22, 17);
        this.scrollbar.update(this.infoList.size());
        this.scrollbar.draw();
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, state);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
}
