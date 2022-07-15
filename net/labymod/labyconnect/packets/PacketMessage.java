//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.handling.*;

public class PacketMessage extends Packet
{
    private ChatUser sender;
    private ChatUser to;
    private String message;
    private long sentTime;
    private long fileSize;
    private double audioTime;
    
    public PacketMessage(final ChatUser sender, final ChatUser to, final String message, final long fileSize, final double time, final long sentTime) {
        this.sender = sender;
        this.to = to;
        this.message = message;
        this.fileSize = fileSize;
        this.audioTime = time;
        this.sentTime = sentTime;
    }
    
    public PacketMessage() {
    }
    
    public void read(final PacketBuf buf) {
        this.sender = buf.readChatUser();
        this.to = buf.readChatUser();
        this.message = buf.readString();
        this.fileSize = buf.readLong();
        this.audioTime = buf.readDouble();
        this.sentTime = buf.readLong();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeChatUser(this.sender);
        buf.writeChatUser(this.to);
        buf.writeString(this.message);
        buf.writeLong(this.fileSize);
        buf.writeDouble(this.audioTime);
        buf.writeLong(this.sentTime);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public double getAudioTime() {
        return this.audioTime;
    }
    
    public long getFileSize() {
        return this.fileSize;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public ChatUser getSender() {
        return this.sender;
    }
    
    public ChatUser getTo() {
        return this.to;
    }
    
    public long getSentTime() {
        return this.sentTime;
    }
}
