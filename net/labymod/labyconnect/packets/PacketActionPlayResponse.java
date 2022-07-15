//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import net.labymod.main.*;

public class PacketActionPlayResponse extends Packet
{
    private short requestId;
    private boolean allowed;
    private String reason;
    
    public PacketActionPlayResponse() {
    }
    
    public PacketActionPlayResponse(final boolean allowed) {
        this.allowed = allowed;
    }
    
    public void read(final PacketBuf buf) {
        this.requestId = buf.readShort();
        if (!(this.allowed = buf.readBoolean())) {
            this.reason = buf.readString();
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeShort((int)this.requestId);
        buf.writeBoolean(this.allowed);
        if (!this.allowed) {
            buf.writeString(this.reason);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        LabyMod.getInstance().getUserManager().resolveAction(this.requestId, this);
    }
    
    public boolean isAllowed() {
        return this.allowed;
    }
    
    public short getRequestId() {
        return this.requestId;
    }
    
    public String getReason() {
        return this.reason;
    }
}
