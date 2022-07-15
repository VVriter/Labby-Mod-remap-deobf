//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import net.labymod.core.*;
import io.netty.buffer.*;
import net.labymod.labyconnect.packets.*;

public class PacketPrepender extends ByteToMessageDecoder
{
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf buffer, final List<Object> objects) {
        buffer.markReaderIndex();
        final byte[] a = new byte[3];
        for (int i = 0; i < a.length; ++i) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }
            a[i] = buffer.readByte();
            if (a[i] >= 0) {
                final PacketBuf buf = LabyModCore.getMinecraft().createPacketBuf(Unpooled.wrappedBuffer(a));
                try {
                    final int varInt = buf.readVarIntFromBuffer();
                    if (buffer.readableBytes() < varInt) {
                        buffer.resetReaderIndex();
                        return;
                    }
                    objects.add(buffer.readBytes(varInt));
                }
                finally {
                    buf.release();
                }
                return;
            }
        }
        throw new RuntimeException("length wider than 21-bit");
    }
}
