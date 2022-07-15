//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import net.labymod.labyconnect.user.*;
import com.google.common.base.*;

public class PacketPlayServerStatus extends Packet
{
    private String serverIp;
    private int port;
    private String gamemode;
    
    public PacketPlayServerStatus(final String serverIp, final int port) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatus() {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
    }
    
    public PacketPlayServerStatus(final String serverIp, final int port, final String gamemode) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = gamemode;
    }
    
    public void read(final PacketBuf buf) {
        this.serverIp = buf.readString();
        this.port = buf.readInt();
        if (buf.readBoolean()) {
            this.gamemode = buf.readString();
        }
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);
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
    
    public String getServerIp() {
        return this.serverIp;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getGamemode() {
        return this.gamemode;
    }
    
    public ServerInfo build() {
        if (this.gamemode == null) {
            return new ServerInfo(this.serverIp, this.port);
        }
        return new ServerInfo(this.serverIp, this.port, this.gamemode);
    }
    
    public boolean equals(final PacketPlayServerStatus packet) {
        return this.serverIp.equals(packet.getServerIp()) && this.port == packet.getPort() && Objects.equal((Object)this.gamemode, (Object)packet.getGamemode());
    }
}
