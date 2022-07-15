//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketChatVisibilityChange extends Packet
{
    private boolean visible;
    
    public void read(final PacketBuf buf) {
        this.visible = buf.readBoolean();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeBoolean(this.visible);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean isVisible() {
        return this.visible;
    }
}
