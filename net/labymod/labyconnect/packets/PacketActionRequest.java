//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketActionRequest extends Packet
{
    private UUID uuid;
    
    public PacketActionRequest() {
    }
    
    public PacketActionRequest(final UUID uuid) {
        this.uuid = uuid;
    }
    
    public void read(final PacketBuf buf) {
        this.uuid = UUID.fromString(buf.readString());
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.uuid.toString());
    }
    
    public void handle(final PacketHandler packetHandler) {
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
