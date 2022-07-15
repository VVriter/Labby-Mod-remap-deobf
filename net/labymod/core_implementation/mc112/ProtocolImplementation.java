//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.api.protocol.chunk.*;
import net.labymod.utils.*;
import io.netty.buffer.*;
import net.labymod.main.*;
import io.netty.channel.*;
import net.labymod.api.protocol.shadow.*;
import net.labymod.core.*;

public class ProtocolImplementation implements ProtocolAdapter
{
    public void loadChunk(final ChunkCachingProtocol protocol, final Extracted extracted, final int chunkX, final int chunkZ, final boolean flag) {
        final byte[] decompressedData = GZIPCompression.decompress(extracted.data);
        final gy readBuffer = new gy(Unpooled.wrappedBuffer(decompressedData));
        final int index = readBuffer.writerIndex();
        readBuffer.writerIndex(0);
        readBuffer.d(32);
        readBuffer.writeInt(chunkX);
        readBuffer.writeInt(chunkZ);
        readBuffer.writerIndex(index);
        try {
            final Channel channel = LabyMod.getInstance().getNettyChannel();
            if (channel == null) {
                return;
            }
            final ChannelPipeline pipeline = channel.pipeline();
            pipeline.context("ccp").fireChannelRead((Object)readBuffer);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public void onReceiveChunkPacket(final Object packetBuffer, final Object packet) {
    }
    
    public void loadChunkBulk(final ChunkCachingProtocol protocol, final Extracted[] extracted, final int[] chunkX, final int[] chunkZ) {
    }
    
    public boolean handleOutgoingPacket(final Object msg, final ShadowProtocol shadowProtocol) {
        if (msg instanceof lk.a) {
            final lk.a packet = (lk.a)msg;
            final gy packetBuffer = new gy(Unpooled.buffer());
            packetBuffer.writeByte(0);
            packetBuffer.writeDouble(packet.a(0.0));
            packetBuffer.writeDouble(packet.b(0.0));
            packetBuffer.writeDouble(packet.c(0.0));
            packetBuffer.writeBoolean(packet.a());
            this.addInputs(packetBuffer, shadowProtocol);
            LabyMod.getInstance().getLabyModAPI().sendPluginMessage("labymod3:shadow", packetBuffer);
            return true;
        }
        if (msg instanceof lk.c) {
            final lk.c packet2 = (lk.c)msg;
            final gy packetBuffer = new gy(Unpooled.buffer());
            packetBuffer.writeByte(1);
            packetBuffer.writeFloat(packet2.a(0.0f));
            packetBuffer.writeFloat(packet2.b(0.0f));
            packetBuffer.writeBoolean(packet2.a());
            this.addInputs(packetBuffer, shadowProtocol);
            LabyMod.getInstance().getLabyModAPI().sendPluginMessage("labymod3:shadow", packetBuffer);
            return true;
        }
        if (msg instanceof lk.b) {
            final lk.b packet3 = (lk.b)msg;
            final gy packetBuffer = new gy(Unpooled.buffer());
            packetBuffer.writeByte(2);
            packetBuffer.writeDouble(packet3.a(0.0));
            packetBuffer.writeDouble(packet3.b(0.0));
            packetBuffer.writeDouble(packet3.c(0.0));
            packetBuffer.writeFloat(packet3.a(0.0f));
            packetBuffer.writeFloat(packet3.b(0.0f));
            packetBuffer.writeBoolean(packet3.a());
            this.addInputs(packetBuffer, shadowProtocol);
            LabyMod.getInstance().getLabyModAPI().sendPluginMessage("labymod3:shadow", packetBuffer);
            return true;
        }
        if (msg instanceof lk) {
            final lk packet4 = (lk)msg;
            final gy packetBuffer = new gy(Unpooled.buffer());
            packetBuffer.writeByte(3);
            packetBuffer.writeBoolean(packet4.a());
            this.addInputs(packetBuffer, shadowProtocol);
            LabyMod.getInstance().getLabyModAPI().sendPluginMessage("labymod3:shadow", packetBuffer);
        }
        return false;
    }
    
    private void addInputs(final gy packetBuffer, final ShadowProtocol shadowProtocol) {
        final bud player = LabyModCore.getMinecraft().getPlayer();
        final bub movementInput = player.e;
        packetBuffer.writeLong(System.currentTimeMillis());
        packetBuffer.writeDouble((double)movementInput.b);
        packetBuffer.writeDouble((double)movementInput.a);
        packetBuffer.writeBoolean(movementInput.g);
        packetBuffer.writeBoolean(movementInput.h);
        packetBuffer.writeDouble(player.p);
        packetBuffer.writeDouble(player.q);
        packetBuffer.writeDouble(player.r);
        packetBuffer.writeFloat(player.v);
        packetBuffer.writeFloat(player.w);
        packetBuffer.writeBoolean(player.aV());
        packetBuffer.writeInt(shadowProtocol.getPacketCounter().get());
    }
}
