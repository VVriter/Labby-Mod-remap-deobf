//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketLoginTime extends Packet
{
    private ChatUser player;
    private long dateJoined;
    private long lastOnline;
    
    public PacketLoginTime(final ChatUser player, final long dateJoined, final long lastOnline) {
        this.player = player;
        this.dateJoined = dateJoined;
        this.lastOnline = lastOnline;
    }
    
    public PacketLoginTime() {
    }
    
    public void read(final PacketBuf buf) {
        this.player = buf.readChatUser();
        this.dateJoined = buf.readLong();
        this.lastOnline = buf.readLong();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.player);
        buf.writeLong(this.dateJoined);
        buf.writeLong(this.lastOnline);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public long getDateJoined() {
        return this.dateJoined;
    }
    
    public long getLastOnline() {
        return this.lastOnline;
    }
    
    public ChatUser getPlayer() {
        return this.player;
    }
}
