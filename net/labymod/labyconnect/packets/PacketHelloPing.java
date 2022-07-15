//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketHelloPing extends Packet
{
    private long a;
    
    public PacketHelloPing() {
    }
    
    public PacketHelloPing(final long a) {
    }
    
    public void read(final PacketBuf buf) {
        this.a = buf.readLong();
        buf.readInt();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeLong(this.a);
        buf.writeInt(26);
    }
    
    public int getId() {
        return 0;
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
