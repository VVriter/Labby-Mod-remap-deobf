//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayTyping extends Packet
{
    private ChatUser player;
    private ChatUser inChatWith;
    private boolean typing;
    
    public PacketPlayTyping(final ChatUser player, final ChatUser inChatWith, final boolean typing) {
        this.player = player;
        this.inChatWith = inChatWith;
        this.typing = typing;
    }
    
    public PacketPlayTyping() {
    }
    
    public void read(final PacketBuf buf) {
        this.player = buf.readChatUser();
        this.inChatWith = buf.readChatUser();
        this.typing = buf.readBoolean();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.player);
        buf.writeChatUser(this.inChatWith);
        buf.writeBoolean(this.typing);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public ChatUser getInChatWith() {
        return this.inChatWith;
    }
    
    public ChatUser getPlayer() {
        return this.player;
    }
    
    public boolean isTyping() {
        return this.typing;
    }
}
