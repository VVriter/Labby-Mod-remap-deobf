//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.handling;

import io.netty.channel.*;
import net.labymod.labyconnect.packets.*;

public abstract class PacketHandler extends SimpleChannelInboundHandler<Object>
{
    protected void channelRead0(final ChannelHandlerContext ctx, final Object packet) throws Exception {
        this.handlePacket((Packet)packet);
    }
    
    private void handlePacket(final Packet packet) {
        packet.handle(this);
    }
    
    public abstract void handle(final PacketLoginData p0);
    
    public abstract void handle(final PacketHelloPing p0);
    
    public abstract void handle(final PacketHelloPong p0);
    
    public abstract void handle(final PacketPlayPlayerOnline p0);
    
    public abstract void handle(final PacketLoginComplete p0);
    
    public abstract void handle(final PacketChatVisibilityChange p0);
    
    public abstract void handle(final PacketKick p0);
    
    public abstract void handle(final PacketDisconnect p0);
    
    public abstract void handle(final PacketPlayRequestAddFriend p0);
    
    public abstract void handle(final PacketLoginFriend p0);
    
    public abstract void handle(final PacketLoginRequest p0);
    
    public abstract void handle(final PacketNotAllowed p0);
    
    public abstract void handle(final PacketPing p0);
    
    public abstract void handle(final PacketPong p0);
    
    public abstract void handle(final PacketServerMessage p0);
    
    public abstract void handle(final PacketMessage p0);
    
    public abstract void handle(final PacketPlayTyping p0);
    
    public abstract void handle(final PacketPlayRequestAddFriendResponse p0);
    
    public abstract void handle(final PacketPlayRequestRemove p0);
    
    public abstract void handle(final PacketPlayDenyFriendRequest p0);
    
    public abstract void handle(final PacketPlayFriendRemove p0);
    
    public abstract void handle(final PacketLoginOptions p0);
    
    public abstract void handle(final PacketPlayServerStatus p0);
    
    public abstract void handle(final PacketPlayServerStatusUpdate p0);
    
    public abstract void handle(final PacketPlayFriendStatus p0);
    
    public abstract void handle(final PacketPlayFriendPlayingOn p0);
    
    public abstract void handle(final PacketPlayChangeOptions p0);
    
    public abstract void handle(final PacketLoginTime p0);
    
    public abstract void handle(final PacketLoginVersion p0);
    
    public abstract void handle(final PacketEncryptionRequest p0);
    
    public abstract void handle(final PacketEncryptionResponse p0);
    
    public abstract void handle(final PacketMojangStatus p0);
    
    public abstract void handle(final PacketUpdateCosmetics p0);
    
    public abstract void handle(final PacketAddonMessage p0);
    
    public abstract void handle(final PacketUserBadge p0);
    
    public abstract void handle(final PacketActionBroadcast p0);
}
