//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.elements.*;
import net.labymod.gui.layout.*;
import java.util.*;
import net.labymod.core.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.labyconnect.log.*;

public class WinMessageField extends WindowElement<GuiFriendsLayout>
{
    private static String storedMessage;
    private static long cooldown;
    private ModTextField fieldMessage;
    private bja buttonSendMessage;
    private long typingCooldown;
    
    public WinMessageField(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
        this.typingCooldown = 0L;
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final int paddingHeight = 3;
        final int paddingWidth = 0;
        final int dragBarWidth = 2;
        final int spaceBetween = 5;
        final int buttonWidth = 70;
        (this.fieldMessage = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), left + paddingWidth + dragBarWidth, top + paddingHeight, right - left - buttonWidth - paddingWidth * 2 - spaceBetween, bottom - top - paddingHeight * 2)).setBlackBox(false);
        this.fieldMessage.setMaxStringLength(120);
        this.fieldMessage.setText(WinMessageField.storedMessage);
        buttonlist.add(this.buttonSendMessage = new bja(1, right - buttonWidth, top + (bottom - top - 20) / 2, buttonWidth - paddingWidth, 20, LanguageManager.translate("button_send")));
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        this.fieldMessage.drawTextBox();
        this.buttonSendMessage.l = (GuiFriendsLayout.selectedUser != null && !this.fieldMessage.getText().replaceAll(" ", "").isEmpty() && WinMessageField.cooldown < System.currentTimeMillis());
        this.fieldMessage.setEnabled(GuiFriendsLayout.selectedUser != null);
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.fieldMessage.mouseClicked(mouseX, mouseY, mouseButton);
        return false;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.fieldMessage.textboxKeyTyped(typedChar, keyCode)) {
            WinMessageField.storedMessage = this.fieldMessage.getText();
            if (GuiFriendsLayout.selectedUser != null && this.typingCooldown + 1000L < System.currentTimeMillis()) {
                this.typingCooldown = System.currentTimeMillis();
                LabyMod.getInstance().getLabyConnect().getClientConnection().sendPacket((Packet)new PacketPlayTyping(LabyMod.getInstance().getLabyConnect().getClientProfile().buildClientUser(), GuiFriendsLayout.selectedUser, true));
            }
        }
        if (keyCode == 28 && this.fieldMessage.isFocused()) {
            this.actionPerformed(this.buttonSendMessage);
        }
    }
    
    public void actionPerformed(final bja button) {
        if (button.l && button.k == 1 && GuiFriendsLayout.selectedUser != null && !this.fieldMessage.getText().replaceAll(" ", "").isEmpty()) {
            final SingleChat singleChat = LabyMod.getInstance().getLabyConnect().getChatlogManager().getChat(GuiFriendsLayout.selectedUser);
            final MessageChatComponent messageChatComponent = new MessageChatComponent(LabyMod.getInstance().getPlayerName(), System.currentTimeMillis(), this.fieldMessage.getText());
            singleChat.addMessage(messageChatComponent);
            this.fieldMessage.setText("");
            WinMessageField.storedMessage = "";
            WinMessageField.cooldown = System.currentTimeMillis() + 1000L;
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void updateScreen() {
        this.fieldMessage.updateCursorCounter();
    }
    
    public ModTextField getFieldMessage() {
        return this.fieldMessage;
    }
    
    static {
        WinMessageField.storedMessage = "";
        WinMessageField.cooldown = 0L;
    }
}
