//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketLoginRequest extends Packet
{
    private List<ChatRequest> requesters;
    
    public PacketLoginRequest(final List<ChatRequest> requesters) {
        this.requesters = requesters;
    }
    
    public PacketLoginRequest() {
    }
    
    public List<ChatRequest> getRequests() {
        return this.requesters;
    }
    
    public void read(final PacketBuf buf) {
        this.requesters = new ArrayList<ChatRequest>();
        for (int a = buf.readInt(), i = 0; i < a; ++i) {
            this.requesters.add((ChatRequest)buf.readChatUser());
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeInt(this.getRequests().size());
        for (int i = 0; i < this.getRequests().size(); ++i) {
            buf.writeChatUser((ChatUser)this.getRequests().get(i));
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
