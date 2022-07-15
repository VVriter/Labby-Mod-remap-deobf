//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketLoginFriend extends Packet
{
    private List<ChatUser> friends;
    
    public PacketLoginFriend(final List<ChatUser> friends) {
        this.friends = friends;
    }
    
    public PacketLoginFriend() {
    }
    
    public void read(final PacketBuf buf) {
        final List<ChatUser> players = new ArrayList<ChatUser>();
        for (int a = buf.readInt(), i = 0; i < a; ++i) {
            players.add(buf.readChatUser());
        }
        (this.friends = new ArrayList<ChatUser>()).addAll(players);
    }
    
    public void write(final PacketBuf buf) {
        buf.writeInt(this.getFriends().size());
        for (int i = 0; i < this.getFriends().size(); ++i) {
            final ChatUser p = this.getFriends().get(i);
            buf.writeChatUser(p);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public List<ChatUser> getFriends() {
        return this.friends;
    }
}
