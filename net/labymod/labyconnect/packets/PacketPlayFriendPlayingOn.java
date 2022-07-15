//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayFriendPlayingOn extends Packet
{
    private ChatUser player;
    private String gameModeName;
    
    public PacketPlayFriendPlayingOn(final ChatUser player, final String gameModeName) {
        this.player = player;
        this.gameModeName = gameModeName;
    }
    
    public PacketPlayFriendPlayingOn() {
    }
    
    public void read(final PacketBuf buf) {
        this.player = buf.readChatUser();
        this.gameModeName = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.player);
        buf.writeString(this.gameModeName);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getGameModeName() {
        return this.gameModeName;
    }
    
    public ChatUser getPlayer() {
        return this.player;
    }
}
