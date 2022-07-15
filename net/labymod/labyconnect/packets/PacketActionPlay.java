//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import java.beans.*;

public class PacketActionPlay extends Packet
{
    private short requestId;
    private short actionType;
    private byte[] data;
    
    public void read(final PacketBuf buf) {
        this.requestId = buf.readShort();
        this.actionType = buf.readShort();
        final int length = buf.readVarIntFromBuffer();
        buf.readBytes(this.data = new byte[length]);
    }
    
    public void write(final PacketBuf buf) {
        buf.writeShort((int)this.requestId);
        buf.writeShort((int)this.actionType);
        if (this.data == null) {
            buf.writeVarIntToBuffer(0);
        }
        else {
            buf.writeVarIntToBuffer(this.data.length);
            buf.writeBytes(this.data);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
    }
    
    public short getRequestId() {
        return this.requestId;
    }
    
    public short getActionType() {
        return this.actionType;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    @ConstructorProperties({ "requestId", "actionType", "data" })
    public PacketActionPlay(final short requestId, final short actionType, final byte[] data) {
        this.requestId = requestId;
        this.actionType = actionType;
        this.data = data;
    }
    
    public PacketActionPlay() {
    }
}
