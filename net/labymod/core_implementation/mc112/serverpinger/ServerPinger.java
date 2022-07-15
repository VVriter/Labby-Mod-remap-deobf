//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.serverpinger;

import net.labymod.utils.*;
import net.labymod.core.*;
import java.net.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.*;
import io.netty.bootstrap.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import io.netty.channel.socket.nio.*;
import java.util.*;

public class ServerPinger
{
    private static final Splitter PING_RESPONSE_SPLITTER;
    private final List<gw> pingDestinations;
    
    public ServerPinger() {
        this.pingDestinations = Collections.synchronizedList((List<gw>)Lists.newArrayList());
    }
    
    public void ping(final Consumer<ServerPingerData> doneCallback, final ServerPingerData server) throws Throwable {
        final bsd serveraddress = bsd.a(server.getIpAddress());
        gw networkmanager = null;
        try {
            networkmanager = gw.a(InetAddress.getByName(serveraddress.a()), serveraddress.b(), false);
        }
        catch (UnknownHostException e) {
            doneCallback.accept(null);
            e.printStackTrace();
        }
        this.pingDestinations.add(networkmanager);
        final gw finalNetworkManager = networkmanager;
        networkmanager.a((hb)new mq() {
            private boolean successful;
            private boolean receivedStatus;
            private long pinged;
            
            public void a(final ms packetIn) {
                if (this.receivedStatus) {
                    finalNetworkManager.a((hh)new ho("Received unrequested status"));
                }
                else {
                    this.receivedStatus = true;
                    final mt serverstatusresponse = packetIn.a();
                    server.setPingToServer(System.currentTimeMillis() - server.getTimePinged());
                    if (serverstatusresponse.a() != null) {
                        server.setMotd(serverstatusresponse.a().d());
                    }
                    else {
                        server.setMotd("");
                    }
                    if (serverstatusresponse.b() != null) {
                        server.setCurrentPlayers(serverstatusresponse.b().b());
                        server.setMaxPlayers(serverstatusresponse.b().a());
                        if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.b().c())) {
                            final StringBuilder stringbuilder = new StringBuilder();
                            for (final GameProfile gameprofile : serverstatusresponse.b().c()) {
                                if (stringbuilder.length() > 0) {
                                    stringbuilder.append("\n");
                                }
                                stringbuilder.append(gameprofile.getName());
                            }
                            if (serverstatusresponse.b().c().length < serverstatusresponse.b().b()) {
                                if (stringbuilder.length() > 0) {
                                    stringbuilder.append("\n");
                                }
                                stringbuilder.append("... and ").append(serverstatusresponse.b().b() - serverstatusresponse.b().c().length).append(" more ...");
                            }
                            server.setPlayerList(stringbuilder.toString());
                        }
                    }
                    if (serverstatusresponse.d() != null) {
                        final String s = serverstatusresponse.d();
                        if (s.startsWith("data:image/png;base64,")) {
                            server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length()));
                        }
                    }
                    if (serverstatusresponse.c() != null) {
                        server.setGameVersion(serverstatusresponse.c().a());
                        server.setVersion(serverstatusresponse.c().b());
                    }
                    else {
                        server.setGameVersion("?");
                    }
                    this.successful = true;
                    this.pinged = bib.I();
                    finalNetworkManager.a((ht)new mv(this.pinged));
                }
            }
            
            public void a(final mr packetIn) {
                final long i = this.pinged;
                final long j = bib.I();
                server.setPingToServer(j - i);
                doneCallback.accept(server);
                finalNetworkManager.a((hh)new ho("Finished"));
            }
            
            public void a(final hh reason) {
                if (!this.successful) {
                    try {
                        ServerPinger.this.tryCompatibilityPing(doneCallback, server);
                    }
                    catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            }
        });
        try {
            networkmanager.a((ht)new md(serveraddress.a(), serveraddress.b(), gx.c));
            networkmanager.a((ht)new mw());
        }
        catch (Throwable throwable) {
            doneCallback.accept(null);
            throwable.printStackTrace();
        }
    }
    
    private void tryCompatibilityPing(final Consumer<ServerPingerData> doneCallback, final ServerPingerData server) {
        final bsd serveraddress = bsd.a(server.getIpAddress());
        if (serveraddress == null) {
            return;
        }
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)gw.d.c())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
            protected void initChannel(final Channel p_initChannel_1_) throws Exception {
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                }
                catch (ChannelException ex) {}
                p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>() {
                        public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
                            super.channelActive(p_channelActive_1_);
                            final ByteBuf bytebuf = Unpooled.buffer();
                            try {
                                bytebuf.writeByte(254);
                                bytebuf.writeByte(1);
                                bytebuf.writeByte(250);
                                char[] achar = "MC|PingHost".toCharArray();
                                bytebuf.writeShort(achar.length);
                                for (final char c0 : achar) {
                                    bytebuf.writeChar((int)c0);
                                }
                                bytebuf.writeShort(7 + 2 * serveraddress.a().length());
                                bytebuf.writeByte(127);
                                achar = serveraddress.a().toCharArray();
                                bytebuf.writeShort(achar.length);
                                for (final char c2 : achar) {
                                    bytebuf.writeChar((int)c2);
                                }
                                bytebuf.writeInt(serveraddress.b());
                                p_channelActive_1_.channel().writeAndFlush((Object)bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                            }
                            finally {
                                bytebuf.release();
                            }
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final ByteBuf p_channelRead0_2_) throws Exception {
                            final short short1 = p_channelRead0_2_.readUnsignedByte();
                            boolean success = false;
                            if (short1 == 255) {
                                final String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
                                final String[] astring = (String[])Iterables.toArray(ServerPinger.PING_RESPONSE_SPLITTER.split((CharSequence)s), (Class)String.class);
                                if ("§1".equals(astring[0])) {
                                    final int i = rk.a(astring[1], 0);
                                    final String s2 = astring[2];
                                    final String s3 = astring[3];
                                    final int j = rk.a(astring[4], -1);
                                    final int k = rk.a(astring[5], -1);
                                    server.setMotd(s3);
                                    server.setCurrentPlayers(j);
                                    server.setMaxPlayers(k);
                                    success = true;
                                    doneCallback.accept(server);
                                }
                            }
                            if (!success) {
                                doneCallback.accept(null);
                            }
                            p_channelRead0_1_.close();
                        }
                        
                        public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) throws Exception {
                            p_exceptionCaught_1_.close();
                            doneCallback.accept(null);
                        }
                    } });
            }
        })).channel((Class)NioSocketChannel.class)).connect(serveraddress.a(), serveraddress.b());
    }
    
    public void pingPendingNetworks() {
        synchronized (this.pingDestinations) {
            final Iterator<gw> iterator = this.pingDestinations.iterator();
            while (iterator.hasNext()) {
                final gw networkmanager = iterator.next();
                try {
                    if (networkmanager == null) {
                        continue;
                    }
                    if (networkmanager.g()) {
                        networkmanager.a();
                    }
                    else {
                        iterator.remove();
                        networkmanager.l();
                    }
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }
    
    public void clearPendingNetworks() {
        synchronized (this.pingDestinations) {
            final Iterator<gw> iterator = this.pingDestinations.iterator();
            while (iterator.hasNext()) {
                final gw networkmanager = iterator.next();
                if (networkmanager != null && networkmanager.g()) {
                    iterator.remove();
                    networkmanager.a((hh)new ho("Cancelled"));
                }
            }
        }
    }
    
    static {
        PING_RESPONSE_SPLITTER = Splitter.on('\0').limit(6);
    }
}
