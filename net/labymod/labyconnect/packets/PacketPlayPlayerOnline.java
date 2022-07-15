//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayPlayerOnline extends Packet
{
    private ChatUser newOnlinePlayer;
    
    public PacketPlayPlayerOnline(final ChatUser newOnlinePlayer) {
        this.newOnlinePlayer = newOnlinePlayer;
    }
    
    public PacketPlayPlayerOnline() {
    }
    
    public void read(final PacketBuf buf) {
        this.newOnlinePlayer = buf.readChatUser();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.newOnlinePlayer);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public ChatUser getPlayer() {
        return this.newOnlinePlayer;
    }
}
