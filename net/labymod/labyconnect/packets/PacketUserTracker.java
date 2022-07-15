//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketUserTracker extends Packet
{
    private EnumTrackingChannel channel;
    private EnumTrackingAction action;
    private UUID[] users;
    
    public PacketUserTracker() {
    }
    
    public PacketUserTracker(final EnumTrackingChannel channel, final EnumTrackingAction action) {
        this(channel, action, new UUID[0]);
    }
    
    public PacketUserTracker(final EnumTrackingChannel channel, final EnumTrackingAction action, final UUID[] users) {
        this.channel = channel;
        this.action = action;
        this.users = users;
    }
    
    public void read(final PacketBuf buf) {
        this.channel = EnumTrackingChannel.values()[buf.readByte()];
        this.action = EnumTrackingAction.values()[buf.readByte()];
        if (this.action != EnumTrackingAction.CLEAR) {
            this.users = new UUID[buf.readInt()];
            for (int i = 0; i < this.users.length; ++i) {
                this.users[i] = new UUID(buf.readLong(), buf.readLong());
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.readByte();
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeByte(this.channel.ordinal());
        buf.writeByte(this.action.ordinal());
        buf.writeInt(this.users.length);
        if (this.action != EnumTrackingAction.CLEAR) {
            for (final UUID user : this.users) {
                buf.writeLong(user.getMostSignificantBits());
                buf.writeLong(user.getLeastSignificantBits());
            }
        }
        if (this.action == EnumTrackingAction.UPDATE) {
            buf.writeByte(0);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
    }
    
    public EnumTrackingChannel getChannel() {
        return this.channel;
    }
    
    public EnumTrackingAction getAction() {
        return this.action;
    }
    
    public UUID[] getUsers() {
        return this.users;
    }
    
    public enum EnumTrackingChannel
    {
        ENTITIES, 
        LIST;
    }
    
    public enum EnumTrackingAction
    {
        ADD, 
        REMOVE, 
        UPDATE, 
        CLEAR, 
        SYNC;
    }
}
