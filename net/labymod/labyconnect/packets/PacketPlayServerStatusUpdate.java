//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import com.google.common.base.*;

public class PacketPlayServerStatusUpdate extends Packet
{
    private String serverIp;
    private int port;
    private String gamemode;
    private boolean viaServerlist;
    
    public PacketPlayServerStatusUpdate(final String serverIp, final int port) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatusUpdate() {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatusUpdate(final String serverIp, final int port, final String gamemode, final boolean viaServerlist) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = gamemode;
        this.viaServerlist = viaServerlist;
    }
    
    public void read(final PacketBuf buf) {
        this.serverIp = buf.readString();
        this.port = buf.readInt();
        this.viaServerlist = buf.readBoolean();
        if (buf.readBoolean()) {
            this.gamemode = buf.readString();
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);
        buf.writeBoolean(this.viaServerlist);
        if (this.gamemode != null && !this.gamemode.isEmpty()) {
            buf.writeBoolean(true);
            buf.writeString(this.gamemode);
        }
        else {
            buf.writeBoolean(false);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean equals(final PacketPlayServerStatusUpdate packet) {
        return this.serverIp.equals(packet.serverIp) && this.port == packet.port && Objects.equal((Object)this.gamemode, (Object)packet.gamemode);
    }
    
    public String getServerIp() {
        return this.serverIp;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getGamemode() {
        return this.gamemode;
    }
    
    public boolean isViaServerlist() {
        return this.viaServerlist;
    }
}
