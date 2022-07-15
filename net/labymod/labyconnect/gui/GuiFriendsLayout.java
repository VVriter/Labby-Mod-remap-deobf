//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.gui.elements.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.gui.layout.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import java.io.*;

public class GuiFriendsLayout extends WindowLayout
{
    private static final int WINDOW_SPLIT_DEPTH = 2;
    private static final int WINDOW_SPLIT_MIN = 80;
    private static final int WINDOW_SPLIT_MAX = 200;
    public static ChatUser selectedUser;
    private boolean hoverTableSplit;
    private boolean draggingTable;
    private boolean profileOpen;
    private WinChatlog chatElementChatlog;
    private WinFriendlist chatElementFriendlist;
    private WinMyProfile chatElementMyProfile;
    private WinPartnerProfile chatElementPartnerProfile;
    private WinMessageField chatElementMessageField;
    private WinSearchField chatElementSearchField;
    private WinLogoutButton chatElementLogoutButton;
    private WinProfileSettings chatElementProfileSettings;
    
    public GuiFriendsLayout() {
        this.hoverTableSplit = false;
        this.draggingTable = false;
        this.profileOpen = false;
    }
    
    public void b() {
        if (!LabyMod.getInstance().getLabyConnect().isOnline()) {
            bib.z().a((blk)new GuiFriendsNotConnected(this));
            GuiFriendsLayout.selectedUser = null;
        }
        super.b();
        Tabs.initGui((blk)this);
        this.chatElementPartnerProfile.setPartner(GuiFriendsLayout.selectedUser);
        if (this.chatElementMessageField.getFieldMessage() != null) {
            this.chatElementMessageField.getFieldMessage().setFocused(true);
        }
    }
    
    public void m() {
        LabyMod.getMainConfig().save();
        super.m();
    }
    
    protected void initLayout(final List<WindowElement<?>> windowElements) {
        this.n.clear();
        final ChatUser clientUser = LabyMod.getInstance().getLabyConnect().getClientProfile().buildClientUser();
        final int marginWindowX = 10;
        final int marginWindowY = 10;
        final int windowTop = 20 + marginWindowY;
        final int windowLeft = marginWindowX;
        final int windowRight = this.l - marginWindowX;
        final int windowBottom = this.m - marginWindowY;
        final int tableSplitSearchListHeight = 30;
        final int tableSplitProfileLogHeight = 30;
        final int tableSplitLogChatFieldHeight = 25;
        final int tableSplitListLogWidth = LabyMod.getSettings().labymodChatSplitX;
        final int tableSplitLogProfileWidth = this.profileOpen ? 170 : 0;
        (this.chatElementPartnerProfile = new WinPartnerProfile(this)).construct((double)(windowLeft + tableSplitListLogWidth), (double)windowTop, (double)windowRight, (double)(windowTop + tableSplitProfileLogHeight));
        (this.chatElementMyProfile = new WinMyProfile(this, clientUser)).construct((double)(windowLeft + tableSplitListLogWidth), (double)windowTop, (double)windowRight, (double)(windowTop + tableSplitProfileLogHeight));
        (this.chatElementChatlog = new WinChatlog(this, clientUser)).construct((double)(windowLeft + tableSplitListLogWidth), (double)(windowTop + tableSplitProfileLogHeight), (double)(windowRight - tableSplitLogProfileWidth), (double)(windowBottom - tableSplitLogChatFieldHeight));
        (this.chatElementFriendlist = new WinFriendlist(this)).construct((double)windowLeft, (double)(windowTop + tableSplitSearchListHeight), (double)(windowLeft + tableSplitListLogWidth), (double)(windowBottom - tableSplitLogChatFieldHeight));
        (this.chatElementMessageField = new WinMessageField(this)).construct((double)(windowLeft + tableSplitListLogWidth), (double)(windowBottom - tableSplitLogChatFieldHeight), (double)(windowRight - tableSplitLogProfileWidth), (double)windowBottom);
        (this.chatElementSearchField = new WinSearchField(this)).construct((double)windowLeft, (double)windowTop, (double)(windowLeft + tableSplitListLogWidth), (double)(windowTop + tableSplitSearchListHeight));
        (this.chatElementLogoutButton = new WinLogoutButton(this)).construct((double)windowLeft, (double)(windowBottom - tableSplitLogChatFieldHeight), (double)(windowLeft + tableSplitListLogWidth), (double)windowBottom);
        (this.chatElementProfileSettings = new WinProfileSettings(this)).construct((double)(windowRight - tableSplitLogProfileWidth + 3), (double)(windowTop + tableSplitProfileLogHeight), (double)windowRight, (double)(windowBottom - tableSplitLogChatFieldHeight));
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (!LabyMod.getInstance().getLabyConnect().isOnline()) {
            bib.z().a((blk)new GuiFriendsNotConnected(this));
            GuiFriendsLayout.selectedUser = null;
        }
        draw.drawAutoDimmedBackground(0.0);
        this.chatElementChatlog.draw(mouseX, mouseY);
        this.chatElementFriendlist.draw(mouseX, mouseY);
        if (this.profileOpen) {
            this.chatElementProfileSettings.draw(mouseX, mouseY);
        }
        this.drawHeaderAndFooter(this.chatElementChatlog.getTop(), this.chatElementChatlog.getBottom());
        this.chatElementPartnerProfile.draw(mouseX, mouseY);
        this.chatElementMyProfile.draw(mouseX, mouseY);
        this.chatElementMessageField.draw(mouseX, mouseY);
        this.chatElementSearchField.draw(mouseX, mouseY);
        this.chatElementLogoutButton.draw(mouseX, mouseY);
        this.hoverTableSplit = (mouseX > this.chatElementFriendlist.getRight() - 2 && mouseX < this.chatElementChatlog.getLeft() + 2 && mouseY > this.chatElementFriendlist.getTop() && mouseY < this.chatElementFriendlist.getBottom());
        if (this.hoverTableSplit || this.draggingTable) {
            draw.drawCenteredString("|||", mouseX + 1, mouseY - 3);
            draw.drawRectangle(this.chatElementFriendlist.getRight() - 1, this.chatElementFriendlist.getTop(), this.chatElementChatlog.getLeft(), this.chatElementFriendlist.getBottom(), Integer.MAX_VALUE);
        }
        super.a(mouseX, mouseY, partialTicks);
        MultiplayerTabs.drawParty(mouseX, mouseY, this.l);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    private void drawHeaderAndFooter(final int maxHeader, final int minFooter) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawOverlayBackground(0, maxHeader);
        draw.drawOverlayBackground(minFooter, this.m);
        draw.drawGradientShadowTop(maxHeader, 0.0, this.l);
        draw.drawGradientShadowBottom(minFooter, 0.0, this.l);
        draw.drawOverlayBackground(0, this.chatElementFriendlist.getTop(), this.chatElementFriendlist.getLeft(), this.chatElementFriendlist.getBottom());
        draw.drawOverlayBackground(this.chatElementFriendlist.getRight() - 3, this.chatElementFriendlist.getTop(), 5, this.chatElementFriendlist.getBottom());
        draw.drawOverlayBackground(this.chatElementProfileSettings.getRight(), this.chatElementChatlog.getTop(), this.l - this.chatElementProfileSettings.getRight(), this.chatElementChatlog.getBottom());
        draw.drawOverlayBackground(this.chatElementChatlog.getRight(), this.chatElementProfileSettings.getTop(), 2, this.chatElementProfileSettings.getBottom());
    }
    
    public void e() {
        super.e();
        this.chatElementMessageField.updateScreen();
        this.chatElementSearchField.updateScreen();
    }
    
    public void k() throws IOException {
        super.k();
        this.chatElementChatlog.handleMouseInput();
        this.chatElementFriendlist.handleMouseInput();
        if (this.profileOpen) {
            this.chatElementProfileSettings.handleMouseInput();
        }
        if (GuiFriendsLayout.selectedUser != null) {
            GuiFriendsLayout.selectedUser.setUnreadMessages(0);
        }
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        this.chatElementChatlog.actionPerformed(button);
        this.chatElementFriendlist.actionPerformed(button);
        this.chatElementPartnerProfile.actionPerformed(button);
        this.chatElementMyProfile.actionPerformed(button);
        this.chatElementMessageField.actionPerformed(button);
        this.chatElementSearchField.actionPerformed(button);
        this.chatElementLogoutButton.actionPerformed(button);
        if (this.profileOpen) {
            this.chatElementProfileSettings.actionPerformed(button);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
        if (this.chatElementSearchField.mouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
        if (this.chatElementMyProfile.mouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
        this.chatElementChatlog.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.chatElementFriendlist.mouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
        this.chatElementPartnerProfile.mouseClicked(mouseX, mouseY, mouseButton);
        this.chatElementMessageField.mouseClicked(mouseX, mouseY, mouseButton);
        this.chatElementLogoutButton.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.profileOpen) {
            this.chatElementProfileSettings.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.hoverTableSplit) {
            this.draggingTable = true;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.chatElementChatlog.mouseClickMove(mouseX, mouseY);
        this.chatElementFriendlist.mouseClickMove(mouseX, mouseY);
        if (this.profileOpen) {
            this.chatElementProfileSettings.mouseClickMove(mouseX, mouseY);
        }
        if (this.draggingTable) {
            LabyMod.getSettings().labymodChatSplitX = mouseX - this.chatElementFriendlist.getLeft();
            if (LabyMod.getSettings().labymodChatSplitX < 80) {
                LabyMod.getSettings().labymodChatSplitX = 80;
            }
            if (LabyMod.getSettings().labymodChatSplitX > 200) {
                LabyMod.getSettings().labymodChatSplitX = 200;
            }
            this.initLayout();
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        if (this.draggingTable) {
            this.draggingTable = false;
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        this.chatElementChatlog.keyTyped(typedChar, keyCode);
        this.chatElementFriendlist.keyTyped(typedChar, keyCode);
        this.chatElementPartnerProfile.keyTyped(typedChar, keyCode);
        this.chatElementMyProfile.keyTyped(typedChar, keyCode);
        this.chatElementMessageField.keyTyped(typedChar, keyCode);
        this.chatElementSearchField.keyTyped(typedChar, keyCode);
        this.chatElementLogoutButton.keyTyped(typedChar, keyCode);
        if (this.profileOpen) {
            this.chatElementProfileSettings.keyTyped(typedChar, keyCode);
        }
    }
    
    public boolean isHoverTableSplit() {
        return this.hoverTableSplit;
    }
    
    public boolean isDraggingTable() {
        return this.draggingTable;
    }
    
    public boolean isProfileOpen() {
        return this.profileOpen;
    }
    
    public WinChatlog getChatElementChatlog() {
        return this.chatElementChatlog;
    }
    
    public WinFriendlist getChatElementFriendlist() {
        return this.chatElementFriendlist;
    }
    
    public WinMyProfile getChatElementMyProfile() {
        return this.chatElementMyProfile;
    }
    
    public WinPartnerProfile getChatElementPartnerProfile() {
        return this.chatElementPartnerProfile;
    }
    
    public WinMessageField getChatElementMessageField() {
        return this.chatElementMessageField;
    }
    
    public WinSearchField getChatElementSearchField() {
        return this.chatElementSearchField;
    }
    
    public WinLogoutButton getChatElementLogoutButton() {
        return this.chatElementLogoutButton;
    }
    
    public WinProfileSettings getChatElementProfileSettings() {
        return this.chatElementProfileSettings;
    }
    
    public void setProfileOpen(final boolean profileOpen) {
        this.profileOpen = profileOpen;
    }
}
