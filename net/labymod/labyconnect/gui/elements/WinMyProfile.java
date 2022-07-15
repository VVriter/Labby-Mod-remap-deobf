//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.user.*;
import net.labymod.gui.elements.*;
import net.labymod.gui.layout.*;
import java.util.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.labyconnect.gui.*;

public class WinMyProfile extends WindowElement<GuiFriendsLayout>
{
    private ChatUser clientUser;
    private CustomGuiButton buttonFriendRequests;
    private CustomGuiButton buttonAddFriend;
    private CustomGuiButton buttonChatSettings;
    private int totalButtonWidth;
    
    public WinMyProfile(final GuiFriendsLayout chatLayout, final ChatUser clientUser) {
        super((WindowLayout)chatLayout);
        this.clientUser = clientUser;
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final String chatSettingsString = LanguageManager.translate("chat_settings");
        buttonlist.add((bja)(this.buttonFriendRequests = new CustomGuiButton(2, 0, top + (bottom - top - 20) / 2, 0, 20, "")));
        buttonlist.add((bja)(this.buttonAddFriend = new CustomGuiButton(3, 0, top + (bottom - top - 20) / 2, 22, 20, "+")));
        buttonlist.add((bja)(this.buttonChatSettings = new CustomGuiButton(40, 0, top + (bottom - top - 20) / 2, LabyMod.getInstance().getDrawUtils().getStringWidth(chatSettingsString) + 10, 20, chatSettingsString)));
        this.updateButtons();
    }
    
    public void updateButtons() {
        final int requestCount = LabyMod.getInstance().getLabyConnect().getRequests().size();
        final String buttonString = LanguageManager.translate("button_requests") + ((requestCount == 0) ? "" : (" (" + requestCount + ")"));
        final int buttonWidth = LabyMod.getInstance().getDrawUtils().getStringWidth(buttonString) + 8;
        this.buttonFriendRequests.j = buttonString;
        this.buttonFriendRequests.l = (requestCount != 0);
        this.buttonChatSettings.setXPosition(this.right - this.buttonChatSettings.b());
        this.buttonFriendRequests.setXPosition(this.right - buttonWidth - 4 - this.buttonChatSettings.b());
        this.buttonFriendRequests.a(buttonWidth);
        this.buttonAddFriend.setXPosition(this.right - buttonWidth - 4 - 25 - this.buttonChatSettings.b());
        this.totalButtonWidth = LabyModCore.getMinecraft().getXPosition((bja)this.buttonAddFriend);
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        if (this.clientUser == null) {
            return;
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        return false;
    }
    
    public void actionPerformed(final bja button) {
        final GuiFriendsLayout layout = (GuiFriendsLayout)this.layout;
        if (button.k == this.buttonAddFriend.k) {
            bib.z().a((blk)new GuiFriendsAddFriend((blk)this.layout, layout.getChatElementSearchField().getFieldSearch().getText()));
        }
        if (button.k == this.buttonFriendRequests.k) {
            bib.z().a((blk)new GuiFriendsRequests((blk)this.layout));
        }
        if (button.k == this.buttonChatSettings.k) {
            layout.setProfileOpen(!layout.isProfileOpen());
            ((GuiFriendsLayout)this.layout).b();
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void updateScreen() {
    }
    
    public int getTotalButtonWidth() {
        return this.totalButtonWidth;
    }
}
