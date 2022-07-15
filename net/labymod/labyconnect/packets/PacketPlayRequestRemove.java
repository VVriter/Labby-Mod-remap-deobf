//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketPlayRequestRemove extends Packet
{
    private String playerName;
    
    public PacketPlayRequestRemove(final String playerName) {
        this.playerName = playerName;
    }
    
    public PacketPlayRequestRemove() {
    }
    
    public void read(final PacketBuf buf) {
        this.playerName = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.playerName);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
}
