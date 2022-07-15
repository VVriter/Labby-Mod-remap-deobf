//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketDisconnect extends Packet
{
    private String reason;
    
    public PacketDisconnect() {
    }
    
    public PacketDisconnect(final String reason) {
        this.reason = reason;
    }
    
    public void read(final PacketBuf buf) {
        this.reason = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        if (this.getReason() == null) {
            buf.writeString("Client Error");
            return;
        }
        buf.writeString(this.getReason());
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getReason() {
        return this.reason;
    }
}
