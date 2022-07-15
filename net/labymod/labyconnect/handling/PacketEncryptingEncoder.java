//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class PacketEncryptingEncoder extends MessageToByteEncoder<ByteBuf>
{
    private final EncryptionTranslator encryptionCodec;
    
    public PacketEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new EncryptionTranslator(cipher);
    }
    
    protected void encode(final ChannelHandlerContext context, final ByteBuf byteBuf, final ByteBuf secondByteBuf) throws ShortBufferException, Exception {
        this.encryptionCodec.cipher(byteBuf, secondByteBuf);
    }
}
