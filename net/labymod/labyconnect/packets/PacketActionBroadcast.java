//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketActionBroadcast extends Packet
{
    private UUID uniqueId;
    private ActionType type;
    private byte[] data;
    
    public void read(final PacketBuf buf) {
        this.uniqueId = new UUID(buf.readLong(), buf.readLong());
        this.type = ActionType.values()[buf.readShort() - 1];
        buf.readBytes(this.data = new byte[buf.readVarIntFromBuffer()]);
    }
    
    public void write(final PacketBuf buf) {
        buf.writeLong(this.uniqueId.getMostSignificantBits());
        buf.writeLong(this.uniqueId.getLeastSignificantBits());
        buf.writeShort((int)(short)this.type.getId());
        buf.writeVarIntToBuffer(this.data.length);
        buf.writeBytes(this.data);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public ActionType getType() {
        return this.type;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public enum ActionType
    {
        EMOTE(1), 
        COSMETIC_CHANGE(2), 
        STICKER(3);
        
        private final int id;
        
        private ActionType(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
    }
}
