//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.core.*;

public class PacketSplitter extends MessageToByteEncoder<ByteBuf>
{
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf buffer, final ByteBuf byteBuf) {
        final int var4 = buffer.readableBytes();
        final int var5 = PacketBuf.getVarIntSize(var4);
        if (var5 > 3) {
            throw new IllegalArgumentException("unable to fit " + var4 + " into " + 3);
        }
        final PacketBuf packetBuffer = LabyModCore.getMinecraft().createPacketBuf(byteBuf);
        packetBuffer.ensureWritable(var5 + var4);
        packetBuffer.writeVarIntToBuffer(var4);
        packetBuffer.writeBytes(buffer, buffer.readerIndex(), var4);
    }
}
