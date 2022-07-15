//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayDenyFriendRequest extends Packet
{
    private ChatRequest denied;
    
    public PacketPlayDenyFriendRequest(final ChatRequest denied) {
        this.denied = denied;
    }
    
    public PacketPlayDenyFriendRequest() {
    }
    
    public void read(final PacketBuf buf) {
        this.denied = (ChatRequest)buf.readChatUser();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser((ChatUser)this.denied);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public ChatRequest getDenied() {
        return this.denied;
    }
}
