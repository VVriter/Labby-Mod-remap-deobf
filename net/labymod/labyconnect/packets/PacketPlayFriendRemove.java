//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayFriendRemove extends Packet
{
    private ChatUser toRemove;
    
    public PacketPlayFriendRemove(final ChatUser toRemove) {
        this.toRemove = toRemove;
    }
    
    public PacketPlayFriendRemove() {
    }
    
    public void read(final PacketBuf buf) {
        this.toRemove = buf.readChatUser();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.toRemove);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public ChatUser getToRemove() {
        return this.toRemove;
    }
}
