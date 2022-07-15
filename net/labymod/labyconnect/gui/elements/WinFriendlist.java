//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.elements.*;
import net.labymod.labyconnect.user.*;
import net.labymod.gui.layout.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.user.*;

public class WinFriendlist extends WindowElement<GuiFriendsLayout>
{
    private Scrollbar scrollbar;
    private int entryHeight;
    private ChatUser hoveredUser;
    
    public WinFriendlist(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
        if (GuiFriendsLayout.selectedUser != null) {
            GuiFriendsLayout.selectedUser.setUnreadMessages(0);
        }
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        (this.scrollbar = new Scrollbar(0)).init();
        this.scrollbar.setSpeed(15);
        this.scrollbar.setPosition(this.left, this.top, this.left + 4, this.bottom);
        this.updateGuiElements();
    }
    
    private void updateGuiElements() {
        final int elementWidth = this.right - this.left;
        this.entryHeight = (int)(elementWidth * elementWidth * 0.001);
        if (this.entryHeight < 12) {
            this.entryHeight = 12;
        }
        if (this.entryHeight > 40) {
            this.entryHeight = 40;
        }
        this.scrollbar.setEntryHeight((double)this.entryHeight);
        this.scrollbar.setPosition(this.left + 2, this.top + 4, this.left + 6, this.bottom - 4);
    }
    
    public void updateBoundRight(final int right) {
        this.right = right;
        this.updateGuiElements();
    }
    
    public void draw(final int mouseX, final int mouseY) {
        final List<ChatUser> friendList = LabyMod.getInstance().getLabyConnect().getSortFriends();
        final String searchQueryString = ((GuiFriendsLayout)this.layout).getChatElementSearchField().getFieldSearch().getText();
        int totalListedEntrys = 0;
        for (final ChatUser chatUser : friendList) {
            if (!searchQueryString.isEmpty() && !chatUser.getGameProfile().getName().toLowerCase().contains(searchQueryString.toLowerCase())) {
                continue;
            }
            ++totalListedEntrys;
        }
        this.scrollbar.update(totalListedEntrys);
        final double scrollbarWidth = this.scrollbar.isHidden() ? 0.0 : (this.scrollbar.getRight() - this.scrollbar.getLeft());
        final double x = this.left + scrollbarWidth + 5.0;
        double y = this.top + this.scrollbar.getScrollY() + 4.0;
        final int width = this.right - this.left - 12;
        this.hoveredUser = null;
        totalListedEntrys = 0;
        for (final ChatUser chatUser2 : friendList) {
            if (!searchQueryString.isEmpty() && !chatUser2.getGameProfile().getName().toLowerCase().contains(searchQueryString.toLowerCase())) {
                continue;
            }
            if (this.drawEntry(chatUser2, (int)x, (int)y, width, this.entryHeight, mouseX, mouseY)) {
                this.hoveredUser = chatUser2;
            }
            y += this.entryHeight;
            ++totalListedEntrys;
        }
        this.scrollbar.draw();
        super.draw(mouseX, mouseY);
    }
    
    public void actionPerformed(final bja button) {
    }
    
    private boolean drawEntry(final ChatUser chatUser, final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        final DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
        final int size = height - 2;
        final int textPosX = x + size + 2;
        if (GuiFriendsLayout.selectedUser == chatUser) {
            a(x, y, x + width + 2, y + height, ModColor.toRGB(255, 183, 76, 29));
        }
        final String unreadMessage = ModColor.cl("c") + "(" + chatUser.getUnreadMessages() + ")";
        final int unreadWidth = (chatUser.getUnreadMessages() == 0) ? 0 : drawUtils.getStringWidth(unreadMessage);
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final UUID uuid = chatUser.getGameProfile().getId();
        String username = chatUser.getGameProfile().getName();
        if (chatUser.isOnline() && userManager.isWhitelisted(uuid)) {
            final User user = userManager.getUser(uuid);
            final char hexColor = user.getGroup().getColorMinecraft();
            username = ModColor.cl(hexColor) + username;
        }
        final String partyUsername = chatUser.isParty() ? "Party" : username;
        final String displayName = (chatUser.isOnline() ? "" : ModColor.cl("8")) + partyUsername;
        final String playerName = ((GuiFriendsLayout.selectedUser == chatUser) ? ModColor.cl("e") : "") + drawUtils.trimStringToWidth(displayName, x + width - textPosX + 2 - unreadWidth);
        drawUtils.drawString(playerName, textPosX, y + 2);
        if (!chatUser.isOnline()) {
            bus.c(0.2f, 0.2f, 0.2f, 1.0f);
        }
        else {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (chatUser.isParty()) {
            bib.z().N().a(ModTextures.MISC_PARTY);
            drawUtils.drawTexture(x, y, 255.0, 255.0, 12.0, 12.0);
        }
        else {
            drawUtils.drawPlayerHead(chatUser.getGameProfile(), x, y + 1, size);
        }
        if (chatUser.getUnreadMessages() != 0) {
            drawUtils.drawRightString(unreadMessage, x + width - 2, y + 3, 0.7);
        }
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
    
    public void handleMouseInput() {
        if (this.isMouseOver()) {
            this.scrollbar.mouseInput();
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        final GuiFriendsLayout layout = (GuiFriendsLayout)this.layout;
        if (this.hoveredUser != null && this.isMouseOver()) {
            GuiFriendsLayout.selectedUser = this.hoveredUser;
            layout.getChatElementPartnerProfile().setPartner(this.hoveredUser);
            if (layout.getChatElementMessageField().getFieldMessage() != null) {
                layout.getChatElementMessageField().getFieldMessage().setFocused(true);
                return true;
            }
        }
        return false;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public boolean isScrolledToTop() {
        return this.scrollbar.getScrollY() == 0.0;
    }
    
    public void updateScreen() {
    }
}
