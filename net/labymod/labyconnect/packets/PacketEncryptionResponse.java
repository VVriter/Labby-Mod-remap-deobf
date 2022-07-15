//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import javax.crypto.*;
import java.security.*;
import java.nio.charset.*;
import net.labymod.labyconnect.handling.*;

public class PacketEncryptionResponse extends Packet
{
    private byte[] sharedSecret;
    private byte[] verifyToken;
    private byte[] pin;
    
    public PacketEncryptionResponse(final SecretKey key, final PublicKey publicKey, final byte[] hash) {
        this.pin = new byte[0];
        this.sharedSecret = CryptManager.encryptData((Key)publicKey, key.getEncoded());
        this.verifyToken = CryptManager.encryptData((Key)publicKey, hash);
    }
    
    public PacketEncryptionResponse(final SecretKey key, final PublicKey publicKey, final byte[] hash, final String pin) {
        this(key, publicKey, hash);
        this.pin = CryptManager.encryptData((Key)publicKey, pin.getBytes(StandardCharsets.UTF_8));
    }
    
    public PacketEncryptionResponse() {
        this.pin = new byte[0];
    }
    
    public byte[] getSharedSecret() {
        return this.sharedSecret;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    public void read(final PacketBuf buf) {
        this.sharedSecret = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeByteArray(new byte[] { 42 });
        buf.writeByteArray(this.sharedSecret);
        buf.writeByteArray(this.verifyToken);
        buf.writeByteArray(this.pin);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
