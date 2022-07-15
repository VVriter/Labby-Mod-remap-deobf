//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.user.*;
import java.util.*;
import net.labymod.labyconnect.handling.*;

public class PacketPlayChangeOptions extends Packet
{
    private PacketLoginOptions.Options options;
    
    public PacketPlayChangeOptions(final PacketLoginOptions.Options options) {
        this.options = options;
    }
    
    public PacketPlayChangeOptions(final boolean showServer, final UserStatus status, final TimeZone timeZone) {
        this.options = new PacketLoginOptions.Options(showServer, status, timeZone);
    }
    
    public PacketPlayChangeOptions() {
    }
    
    public void read(final PacketBuf buf) {
        this.options = new PacketLoginOptions.Options(buf.readBoolean(), buf.readUserStatus(), TimeZone.getTimeZone(buf.readString()));
    }
    
    public void write(final PacketBuf buf) {
        buf.writeBoolean(this.getOptions().isShowServer());
        buf.writeUserStatus(this.getOptions().getOnlineStatus());
        buf.writeString(this.getOptions().getTimeZone().getID());
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public PacketLoginOptions.Options getOptions() {
        return this.options;
    }
}
