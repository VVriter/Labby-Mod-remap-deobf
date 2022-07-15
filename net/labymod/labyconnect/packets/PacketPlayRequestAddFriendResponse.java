//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketPlayRequestAddFriendResponse extends Packet
{
    private String searched;
    private boolean requestSent;
    private String reason;
    
    public PacketPlayRequestAddFriendResponse(final String searched, final boolean sent) {
        this.searched = searched;
        this.requestSent = sent;
    }
    
    public PacketPlayRequestAddFriendResponse(final String searched, final boolean sent, final String reason) {
        this.searched = searched;
        this.requestSent = sent;
        this.reason = reason;
    }
    
    public PacketPlayRequestAddFriendResponse() {
    }
    
    public void read(final PacketBuf buf) {
        this.searched = buf.readString();
        if (!(this.requestSent = buf.readBoolean())) {
            this.reason = buf.readString();
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.searched);
        buf.writeBoolean(this.requestSent);
        if (!this.isRequestSent()) {
            buf.writeString(this.reason);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean isRequestSent() {
        return this.requestSent;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public String getSearched() {
        return this.searched;
    }
}
