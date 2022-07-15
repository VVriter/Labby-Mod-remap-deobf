//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import java.util.*;
import javax.crypto.*;

public class PacketEncryptingDecoder extends MessageToMessageDecoder<ByteBuf>
{
    private final EncryptionTranslator decryptionCodec;
    
    public PacketEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new EncryptionTranslator(cipher);
    }
    
    protected void decode(final ChannelHandlerContext context, final ByteBuf byteBuf, final List<Object> list) throws ShortBufferException, Exception {
        list.add(this.decryptionCodec.decipher(context, byteBuf));
    }
}
