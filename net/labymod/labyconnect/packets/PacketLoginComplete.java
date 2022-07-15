//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketLoginComplete extends Packet
{
    private String showDashboardButton;
    
    public PacketLoginComplete(final String string) {
        this.showDashboardButton = string;
    }
    
    public PacketLoginComplete() {
    }
    
    public void read(final PacketBuf buf) {
        this.showDashboardButton = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.showDashboardButton);
    }
    
    public int getId() {
        return 2;
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getDashboardPin() {
        return this.showDashboardButton;
    }
}
