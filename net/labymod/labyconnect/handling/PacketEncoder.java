//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.handler.codec.*;
import net.labymod.labyconnect.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import net.labymod.core.*;
import net.labymod.support.util.*;
import net.labymod.labyconnect.packets.*;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    private LabyConnect labyConnect;
    
    public PacketEncoder(final LabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Packet packet, final ByteBuf byteBuf) throws Exception {
        final PacketBuf packetBuffer = LabyModCore.getMinecraft().createPacketBuf(byteBuf);
        final int id = Protocol.getProtocol().getPacketId(packet);
        if ((id != 62 && id != 63) || this.labyConnect.getClientConnection().getCustomIp() != null) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "[OUT] " + id + " " + packet.getClass().getSimpleName());
        }
        packetBuffer.writeVarIntToBuffer(id);
        packet.write(packetBuffer);
    }
}
