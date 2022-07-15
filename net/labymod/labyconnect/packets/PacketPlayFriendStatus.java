//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayFriendStatus extends Packet
{
    private ChatUser player;
    private ServerInfo playerInfo;
    
    public PacketPlayFriendStatus(final ChatUser player, final ServerInfo playerInfo) {
        this.player = player;
        this.playerInfo = playerInfo;
    }
    
    public PacketPlayFriendStatus() {
    }
    
    public void read(final PacketBuf buf) {
        this.player = buf.readChatUser();
        this.playerInfo = buf.readServerInfo();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.player);
        buf.writeServerInfo(this.playerInfo);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public ChatUser getPlayer() {
        return this.player;
    }
    
    public ServerInfo getPlayerInfo() {
        return this.playerInfo;
    }
}
