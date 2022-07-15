//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.log;

import java.util.*;
import net.labymod.core.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.labyconnect.user.*;
import org.lwjgl.opengl.*;
import net.labymod.labyconnect.gui.*;
import net.labymod.labyconnect.*;
import net.labymod.main.*;

public class SingleChat
{
    private static final nf POP_SOUND;
    private final int id;
    private ChatUser chatPartner;
    private List<MessageChatComponent> messages;
    
    public SingleChat(final int id, final ChatUser friend, final List<MessageChatComponent> messages) {
        this.messages = Collections.synchronizedList(new ArrayList<MessageChatComponent>());
        this.id = id;
        this.chatPartner = friend;
        this.messages = messages;
    }
    
    public void addMessage(final MessageChatComponent message) {
        this.messages.add(message);
        this.chatPartner.setLastInteraction(System.currentTimeMillis());
        final LabyConnect chatClient = LabyMod.getInstance().getLabyConnect();
        final UserStatus userStatus = chatClient.getClientProfile().getUserStatus();
        final boolean isClientSender = message.getSender().equalsIgnoreCase(LabyMod.getInstance().getPlayerName());
        final boolean playSounds = LabyMod.getSettings().alertPlaySounds;
        if (isClientSender) {
            if (playSounds) {
                LabyModCore.getMinecraft().playSound(SingleChat.POP_SOUND, 1.5f);
            }
            if (this.chatPartner.isParty()) {
                LabyMod.getInstance().getLabyPlay().getPartySystem().sendChatMessage(message.getMessage());
            }
            else {
                final ChatUser clientUser = chatClient.getClientProfile().buildClientUser();
                final PacketMessage packet = new PacketMessage(clientUser, this.chatPartner, message.getMessage(), 0L, 0.0, System.currentTimeMillis());
                chatClient.getClientConnection().sendPacket((Packet)packet);
            }
        }
        else if (playSounds && userStatus != UserStatus.BUSY) {
            LabyModCore.getMinecraft().playSound(SingleChat.POP_SOUND, 2.5f);
            if (!Display.isActive() || bib.z().m == null || !(bib.z().m instanceof GuiFriendsLayout) || GuiFriendsLayout.selectedUser == null || !GuiFriendsLayout.selectedUser.equals(this.chatPartner)) {
                this.chatPartner.increaseUnreadMessages();
            }
        }
    }
    
    public SingleChat apply(final ChatUser chatUser) {
        this.chatPartner = chatUser;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public ChatUser getChatPartner() {
        return this.chatPartner;
    }
    
    public List<MessageChatComponent> getMessages() {
        return this.messages;
    }
    
    public void setChatPartner(final ChatUser chatPartner) {
        this.chatPartner = chatPartner;
    }
    
    public void setMessages(final List<MessageChatComponent> messages) {
        this.messages = messages;
    }
    
    static {
        POP_SOUND = new nf(Source.ABOUT_MC_VERSION.startsWith("1.8") ? "random.pop" : "entity.chicken.egg");
    }
}
