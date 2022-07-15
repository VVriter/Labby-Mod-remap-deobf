//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import java.util.*;
import net.labymod.labyconnect.handling.*;
import net.labymod.core.*;
import net.labymod.main.*;
import java.nio.charset.*;
import net.labymod.utils.*;
import net.labymod.user.*;

public class PacketActionRequestResponse extends Packet
{
    private UUID uuid;
    private short actionId;
    private byte[] data;
    
    public PacketActionRequestResponse() {
    }
    
    public PacketActionRequestResponse(final UUID uuid, final short actionId, final byte[] data) {
        this.uuid = uuid;
        this.actionId = actionId;
        this.data = data;
    }
    
    public void read(final PacketBuf buf) {
        this.uuid = UUID.fromString(buf.readString());
        this.actionId = buf.readShort();
        final int length = buf.readVarIntFromBuffer();
        buf.readBytes(this.data = new byte[length]);
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.uuid.toString());
        buf.writeShort((int)this.actionId);
        if (this.data == null) {
            buf.writeVarIntToBuffer(0);
        }
        else {
            buf.writeVarIntToBuffer(this.data.length);
            buf.writeBytes(this.data);
        }
    }
    
    public void handle(final PacketHandler packetHandler) {
        switch (this.actionId) {
            case 1: {
                if (LabyModCore.getMinecraft().getPlayer() != null && LabyModCore.getMinecraft().getPlayer().bm().equals(this.uuid)) {
                    break;
                }
                LabyMod.getInstance().getEmoteRegistry().handleEmote(this.uuid, this.data);
                break;
            }
            case 2: {
                LabyMod.getInstance().getUserManager().updateUsersJson(this.uuid, new String(this.data, StandardCharsets.UTF_8), null);
                break;
            }
            case 3: {
                if (LabyModCore.getMinecraft().getPlayer() != null && LabyModCore.getMinecraft().getPlayer().bm().equals(this.uuid)) {
                    break;
                }
                final User user = LabyMod.getInstance().getUserManager().getUser(this.uuid);
                LabyMod.getInstance().getStickerRegistry().handleSticker(user, LabyMod.getInstance().getStickerRegistry().bytesToShort(this.data));
                break;
            }
        }
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public short getActionId() {
        return this.actionId;
    }
    
    public byte[] getData() {
        return this.data;
    }
}
