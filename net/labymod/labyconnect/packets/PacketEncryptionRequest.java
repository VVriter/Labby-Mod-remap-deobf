//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public class PacketEncryptionRequest extends Packet
{
    private String serverId;
    private byte[] publicKey;
    private byte[] verifyToken;
    
    public PacketEncryptionRequest(final String serverId, final byte[] publicKey, final byte[] verifyToken) {
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }
    
    public PacketEncryptionRequest() {
    }
    
    public byte[] getPublicKey() {
        return this.publicKey;
    }
    
    public String getServerId() {
        return this.serverId;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    public void read(final PacketBuf buf) {
        this.serverId = buf.readString();
        this.publicKey = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.serverId);
        buf.writeByteArray(this.publicKey);
        buf.writeByteArray(this.verifyToken);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
