//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketPlayRequestAddFriend extends Packet
{
    private String name;
    
    public PacketPlayRequestAddFriend(final String name) {
        this.name = name;
    }
    
    public PacketPlayRequestAddFriend() {
    }
    
    public void read(final PacketBuf buf) {
        final byte[] a = new byte[buf.readInt()];
        for (int i = 0; i < a.length; ++i) {
            a[i] = buf.readByte();
        }
        this.name = new String(a);
    }
    
    public void write(final PacketBuf buf) {
        buf.writeInt(this.name.getBytes().length);
        buf.writeBytes(this.name.getBytes());
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getName() {
        return this.name;
    }
}
