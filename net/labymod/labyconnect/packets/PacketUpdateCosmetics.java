//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import java.beans.*;

public class PacketUpdateCosmetics extends Packet
{
    private String json;
    
    public PacketUpdateCosmetics() {
        this.json = null;
    }
    
    public void read(final PacketBuf buf) {
        final boolean hasJsonString = buf.readBoolean();
        if (hasJsonString) {
            this.json = buf.readString();
        }
    }
    
    public void write(final PacketBuf buf) {
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    @ConstructorProperties({ "json" })
    public PacketUpdateCosmetics(final String json) {
        this.json = null;
        this.json = json;
    }
    
    public String getJson() {
        return this.json;
    }
}
