//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketLoginVersion extends Packet
{
    private int versionId;
    private String versionName;
    private String updateLink;
    
    public PacketLoginVersion(final int internalVersion, final String externalVersion) {
        this.versionId = internalVersion;
        this.versionName = externalVersion;
    }
    
    public PacketLoginVersion() {
    }
    
    public void read(final PacketBuf buf) {
        this.versionId = buf.readInt();
        this.versionName = buf.readString();
        this.updateLink = buf.readString();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeInt(this.versionId);
        buf.writeString(this.versionName);
        buf.writeString("");
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getVersionName() {
        return this.versionName;
    }
    
    public int getVersionID() {
        return this.versionId;
    }
    
    public String getUpdateLink() {
        return this.updateLink;
    }
}
