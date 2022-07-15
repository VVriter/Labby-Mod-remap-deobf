//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketLoginOptions extends Packet
{
    private boolean showServer;
    private UserStatus status;
    private TimeZone timeZone;
    
    public PacketLoginOptions(final boolean showServer, final UserStatus status, final TimeZone timeZone) {
        this.showServer = showServer;
        this.status = status;
        this.timeZone = timeZone;
    }
    
    public PacketLoginOptions() {
    }
    
    public void read(final PacketBuf buf) {
        this.showServer = buf.readBoolean();
        this.status = buf.readUserStatus();
        this.timeZone = TimeZone.getTimeZone(buf.readString());
    }
    
    public void write(final PacketBuf buf) {
        buf.writeBoolean(this.showServer);
        buf.writeUserStatus(this.status);
        buf.writeString(this.timeZone.getID());
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public Options getOptions() {
        return new Options(this.showServer, this.status, this.timeZone);
    }
    
    public static class Options
    {
        private final boolean showServer;
        private final UserStatus onlineStatus;
        private final TimeZone timeZone;
        
        public Options(final boolean showServer, final UserStatus onlineStatus, final TimeZone timeZone) {
            this.showServer = showServer;
            this.timeZone = timeZone;
            this.onlineStatus = onlineStatus;
        }
        
        public boolean isShowServer() {
            return this.showServer;
        }
        
        public UserStatus getOnlineStatus() {
            return this.onlineStatus;
        }
        
        public TimeZone getTimeZone() {
            return this.timeZone;
        }
    }
}
