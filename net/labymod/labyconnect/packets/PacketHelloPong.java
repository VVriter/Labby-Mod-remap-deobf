//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketHelloPong extends Packet
{
    private long a;
    
    public PacketHelloPong() {
    }
    
    public PacketHelloPong(final long a) {
        this.a = a;
    }
    
    public void read(final PacketBuf buf) {
        this.a = buf.readLong();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeLong(this.a);
    }
    
    public int getId() {
        return 1;
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
