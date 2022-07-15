//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.chunk;

import io.netty.channel.*;
import io.netty.buffer.*;

public class CCPChannelHandler extends ChannelInboundHandlerAdapter
{
    private ChunkCachingProtocol ccp;
    
    public CCPChannelHandler(final ChunkCachingProtocol ccp) {
        this.ccp = ccp;
    }
    
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (!this.ccp.isCachingSupported()) {
            ctx.fireChannelRead(msg);
            return;
        }
        if (msg instanceof ByteBuf) {
            final ByteBuf buf = (ByteBuf)msg;
            if (buf.readableBytes() >= 4) {
                final int index = buf.readerIndex();
                final int packetId = this.readVarInt(buf);
                if (packetId != 32) {
                    buf.readerIndex(index);
                    ctx.fireChannelRead(msg);
                    return;
                }
                final byte[] array = buf.nioBuffer().array();
                buf.readerIndex(index);
                this.ccp.onReceive112ChunkData(array);
                buf.readerIndex(index);
            }
        }
        ctx.fireChannelRead(msg);
    }
    
    private int readVarInt(final ByteBuf buf) {
        int var1 = 0;
        int var2 = 0;
        byte var3;
        do {
            var3 = buf.readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 0x80) == 0x80);
        return var1;
    }
}
