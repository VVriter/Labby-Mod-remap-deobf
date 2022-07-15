//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import net.labymod.labyconnect.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import net.labymod.core.*;
import net.labymod.support.util.*;
import java.io.*;
import net.labymod.labyconnect.packets.*;

public class PacketDecoder extends ByteToMessageDecoder
{
    private LabyConnect labyConnect;
    
    public PacketDecoder(final LabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> objects) throws Exception {
        final PacketBuf packetBuffer = LabyModCore.getMinecraft().createPacketBuf(byteBuf);
        if (packetBuffer.readableBytes() < 1) {
            return;
        }
        final int id = packetBuffer.readVarIntFromBuffer();
        final Packet packet = Protocol.getProtocol().getPacket(id);
        if ((id != 62 && id != 63) || this.labyConnect.getClientConnection().getCustomIp() != null) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "[IN] " + id + " " + packet.getClass().getSimpleName());
        }
        packet.read(packetBuffer);
        if (packetBuffer.readableBytes() > 0) {
            throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetBuffer.readableBytes() + " bytes extra whilst reading packet " + packet);
        }
        objects.add(packet);
    }
}
