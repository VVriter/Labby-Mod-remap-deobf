//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.utils.*;
import net.labymod.labyconnect.handling.*;
import net.labymod.main.*;

public class PacketAddonDevelopment extends Packet
{
    private UUID sender;
    private UUID[] receivers;
    private String key;
    private byte[] data;
    
    public PacketAddonDevelopment(final UUID sender, final String key, final byte[] data) {
        this.sender = sender;
        this.key = key;
        this.data = GZIPCompression.compress(data);
        this.receivers = new UUID[0];
    }
    
    public PacketAddonDevelopment(final UUID sender, final UUID[] receivers, final String key, final byte[] data) {
        this.sender = sender;
        this.receivers = receivers;
        this.key = key;
        this.data = GZIPCompression.compress(data);
    }
    
    public void read(final PacketBuf buf) {
        this.sender = new UUID(buf.readLong(), buf.readLong());
        final int receiverCnt = buf.readShort();
        this.receivers = new UUID[receiverCnt];
        for (int i = 0; i < this.receivers.length; ++i) {
            this.receivers[i] = new UUID(buf.readLong(), buf.readLong());
        }
        this.key = buf.readString();
        final byte[] data = new byte[buf.readInt()];
        buf.readBytes(data);
        this.data = data;
    }
    
    public void write(final PacketBuf buf) {
        buf.writeLong(this.sender.getMostSignificantBits());
        buf.writeLong(this.sender.getLeastSignificantBits());
        buf.writeShort(this.receivers.length);
        for (final UUID receiver : this.receivers) {
            buf.writeLong(receiver.getMostSignificantBits());
            buf.writeLong(receiver.getLeastSignificantBits());
        }
        buf.writeString(this.key);
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }
    
    public void handle(final PacketHandler packetHandler) {
        LabyMod.getInstance().getEventManager().callAddonDevelopmentPacket(this);
    }
    
    public byte[] getData() {
        return GZIPCompression.decompress(this.data);
    }
    
    public UUID getSender() {
        return this.sender;
    }
    
    public UUID[] getReceivers() {
        return this.receivers;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public PacketAddonDevelopment() {
    }
}
