//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.support.gui.*;

public class PacketNotAllowed extends Packet
{
    private String reason;
    private long until;
    
    public PacketNotAllowed(final String reason, final long until) {
        this.reason = reason;
        this.until = until;
    }
    
    public PacketNotAllowed() {
    }
    
    public void read(final PacketBuf buf) {
        this.reason = buf.readString();
        this.until = buf.readLong();
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.reason);
        buf.writeLong(this.until);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
        this.handle();
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public long getUntil() {
        return this.until;
    }
    
    public void handle() {
        if (this.until <= 0L) {
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    LabyMod.getInstance().connectToServer(null);
                    final String message = (PacketNotAllowed.this.reason == null || PacketNotAllowed.this.reason.isEmpty()) ? LanguageManager.translate("chat_unknown_kick_reason") : PacketNotAllowed.this.reason;
                    final blk prevScreen = (blk)LabyMod.getInstance().getGuiOpenListener().getGuiMultiplayer();
                    LabyModCore.getMinecraft().openGuiDisconnected((blk)((prevScreen == null) ? new blr() : prevScreen), "disconnect.lost", ModColor.createColors(message));
                }
            });
        }
        else {
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    LabyMod.getInstance().connectToServer(null);
                }
            });
            bib.z().a((blk)new GuiNotAllowed(this.reason));
        }
    }
}
