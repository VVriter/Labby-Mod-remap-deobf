//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketServerMessage extends Packet
{
    private String message;
    
    public PacketServerMessage(final String message) {
        this.message = message;
    }
    
    public PacketServerMessage() {
    }
    
    public void read(final PacketBuf buf) {
        this.message = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.message);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getMessage() {
        return this.message;
    }
}
