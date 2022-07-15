//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import io.netty.channel.socket.nio.*;
import java.util.concurrent.*;
import io.netty.handler.timeout.*;
import net.labymod.labyconnect.handling.*;
import io.netty.channel.*;

public class ClientChannelInitializer extends ChannelInitializer<NioSocketChannel>
{
    private LabyConnect labyConnect;
    private ClientConnection clientConnection;
    
    public ClientChannelInitializer(final LabyConnect labyConnect, final ClientConnection clientConnection) {
        this.labyConnect = labyConnect;
        this.clientConnection = clientConnection;
    }
    
    protected void initChannel(final NioSocketChannel channel) throws Exception {
        this.clientConnection.setNioSocketChannel(channel);
        channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(120L, TimeUnit.SECONDS)).addLast("splitter", (ChannelHandler)new PacketPrepender()).addLast("decoder", (ChannelHandler)new PacketDecoder(this.labyConnect)).addLast("prepender", (ChannelHandler)new PacketSplitter()).addLast("encoder", (ChannelHandler)new PacketEncoder(this.labyConnect)).addLast(new ChannelHandler[] { (ChannelHandler)this.getClientConnection() });
    }
    
    public ClientConnection getClientConnection() {
        return this.clientConnection;
    }
}
