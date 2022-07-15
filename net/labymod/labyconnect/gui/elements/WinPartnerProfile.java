//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.gui.elements.*;
import net.labymod.gui.layout.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.labyconnect.gui.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.labyconnect.user.*;

public class WinPartnerProfile extends WindowElement<GuiFriendsLayout>
{
    private SmallDropDownMenu smallDropDownMenu;
    
    public WinPartnerProfile(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        this.initDropDown();
    }
    
    private void initDropDown() {
        (this.smallDropDownMenu = new SmallDropDownMenu(0, this.bottom - 14, 30, 10)).setRenderCustomSelected(LanguageManager.translate("chat_user_options"));
        if (GuiFriendsLayout.selectedUser != null && GuiFriendsLayout.selectedUser.getCurrentServerInfo() != null && GuiFriendsLayout.selectedUser.getCurrentServerInfo().isServerAvailable()) {
            this.smallDropDownMenu.addDropDownEntry(LanguageManager.translate("chat_user_join_server"));
        }
        this.smallDropDownMenu.addDropDownEntry(LanguageManager.translate("labynet_profile"));
        this.smallDropDownMenu.addDropDownEntry(LanguageManager.translate("chat_user_about"));
        this.smallDropDownMenu.addDropDownEntry(LanguageManager.translate("chat_user_remove_friend"));
        this.smallDropDownMenu.setChangeable(false);
        this.smallDropDownMenu.setMinecraftStyle(false);
        this.smallDropDownMenu.setColor(Integer.MIN_VALUE);
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        if (GuiFriendsLayout.selectedUser == null) {
            return;
        }
        final DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
        drawUtils.drawOverlayBackground(this.left, this.top, this.right, this.top);
        if (!GuiFriendsLayout.selectedUser.isOnline()) {
            bus.c(0.2f, 0.2f, 0.2f, 1.0f);
        }
        else {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        }
        final int headPadding = 2;
        final int headSize = this.bottom - this.top - headPadding * 2;
        if (GuiFriendsLayout.selectedUser.isParty()) {
            bib.z().N().a(ModTextures.MISC_PARTY);
            drawUtils.drawTexture(this.left + 1 + headPadding, this.top + headPadding, 255.0, 255.0, headSize, headSize);
        }
        else {
            drawUtils.drawPlayerHead(GuiFriendsLayout.selectedUser.getGameProfile(), this.left + 1 + headPadding, this.top + headPadding, headSize);
        }
        final ServerInfo serverInfo = GuiFriendsLayout.selectedUser.getCurrentServerInfo();
        final String displayOnlineStatus = ModColor.cl(GuiFriendsLayout.selectedUser.getStatus().getChatColor()) + GuiFriendsLayout.selectedUser.getStatus().getName();
        final boolean statusAvailable = serverInfo != null && serverInfo.isServerAvailable() && GuiFriendsLayout.selectedUser.getStatus() != UserStatus.OFFLINE;
        String displayServerStatus = statusAvailable ? (ModColor.cl("f") + " " + LanguageManager.translate("chat_user_online_on") + " " + ModColor.cl("a") + serverInfo.getDisplayAddress()) : "";
        if (statusAvailable && serverInfo.getSpecifiedServerName() != null && !serverInfo.getSpecifiedServerName().isEmpty()) {
            displayServerStatus = displayServerStatus + ModColor.cl("f") + ", " + ModColor.cl("e") + serverInfo.getSpecifiedServerName();
        }
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final UUID uuid = GuiFriendsLayout.selectedUser.getGameProfile().getId();
        String username = GuiFriendsLayout.selectedUser.getGameProfile().getName();
        if (GuiFriendsLayout.selectedUser.isOnline() && userManager.isWhitelisted(uuid)) {
            final User user = userManager.getUser(uuid);
            final char hexColor = user.getGroup().getColorMinecraft();
            username = ModColor.cl(hexColor) + username;
            if (mouseX > this.left + headPadding + headSize + 5 && mouseX < this.left + headPadding + headSize + 5 + this.draw.getStringWidth(username) && mouseY > this.top + 4 && mouseY < this.top + 4 + 10) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, ModColor.cl(hexColor) + user.getGroup().getDisplayName());
            }
        }
        final String partyUsername = GuiFriendsLayout.selectedUser.isParty() ? "Party" : username;
        String finalNameLine = partyUsername + ModColor.cl("7") + " (" + displayOnlineStatus + displayServerStatus + ModColor.cl("7") + ")";
        final GuiFriendsLayout layout = (GuiFriendsLayout)this.layout;
        finalNameLine = LabyMod.getInstance().getDrawUtils().trimStringToWidth(finalNameLine, layout.getChatElementMyProfile().getTotalButtonWidth() - this.left - headPadding - headSize - 10);
        drawUtils.drawString(finalNameLine, this.left + headPadding + headSize + 5, this.top + 4);
        final boolean isTyping = GuiFriendsLayout.selectedUser.getLastTyping() + 1500L > System.currentTimeMillis();
        if (isTyping) {
            drawUtils.drawString(ModColor.cl('7') + LanguageManager.translate("chat_is_typing"), this.left + headPadding + headSize + 5 + this.smallDropDownMenu.getWidthIn() + 5, this.top + 4 + 15, 0.7);
        }
        if (!GuiFriendsLayout.selectedUser.isOnline()) {
            drawUtils.drawString(ModUtils.getTimeDiff(GuiFriendsLayout.selectedUser.getLastOnline()), this.left + headPadding + headSize + 5 + drawUtils.getStringWidth(finalNameLine) + 5, this.top + 4, 0.7);
        }
        if (!GuiFriendsLayout.selectedUser.isParty()) {
            this.smallDropDownMenu.setX(this.left + headPadding + headSize + 5);
            this.smallDropDownMenu.renderButton(mouseX, mouseY);
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        int entryId = this.smallDropDownMenu.onClick(mouseX, mouseY);
        if (entryId >= 0) {
            if (this.smallDropDownMenu.getDropDownEntrys().size() != 4) {
                ++entryId;
            }
            switch (entryId) {
                case 0: {
                    final ServerInfo serverInfo = GuiFriendsLayout.selectedUser.getCurrentServerInfo();
                    if (serverInfo != null && serverInfo.isServerAvailable()) {
                        LabyMod.getInstance().switchServer(serverInfo.getServerIp() + ":" + serverInfo.getServerPort(), true);
                        break;
                    }
                    break;
                }
                case 1: {
                    LabyMod.getInstance().openWebpage(String.format("https://laby.net/@%s", GuiFriendsLayout.selectedUser.getGameProfile().getName()), false);
                    break;
                }
                case 2: {
                    if (GuiFriendsLayout.selectedUser != null) {
                        bib.z().a((blk)new GuiFriendsAbout(bib.z().m, GuiFriendsLayout.selectedUser));
                        break;
                    }
                    break;
                }
                case 3: {
                    final blk lastScreen = bib.z().m;
                    if (GuiFriendsLayout.selectedUser != null) {
                        bib.z().a((blk)new bkq((bkp)new bkp() {
                            public void a(final boolean result, final int id) {
                                if (GuiFriendsLayout.selectedUser != null && result) {
                                    LabyMod.getInstance().getLabyConnect().getClientConnection().sendPacket((Packet)new PacketPlayFriendRemove(GuiFriendsLayout.selectedUser));
                                    bib.z().a(lastScreen);
                                    GuiFriendsLayout.selectedUser = null;
                                }
                                else {
                                    bib.z().a(lastScreen);
                                }
                            }
                        }, LanguageManager.translate("chat_user_remove_friend_popup"), ModColor.cl("c") + GuiFriendsLayout.selectedUser.getGameProfile().getName(), 0));
                        break;
                    }
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void actionPerformed(final bja button) {
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void setPartner(final ChatUser partner) {
        GuiFriendsLayout.selectedUser = partner;
        if (partner != null) {
            partner.setUnreadMessages(0);
        }
        this.initDropDown();
    }
    
    public void updateScreen() {
    }
}
