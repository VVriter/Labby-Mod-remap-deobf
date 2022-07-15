//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketKick extends Packet
{
    private String cause;
    
    public PacketKick(final String cause) {
        this.cause = cause;
    }
    
    public PacketKick() {
    }
    
    public void read(final PacketBuf buf) {
        this.cause = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.getReason());
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getReason() {
        return this.cause;
    }
}
