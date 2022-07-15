//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;
import java.util.*;
import java.nio.charset.*;
import java.util.zip.*;
import java.io.*;

public class PacketAddonMessage extends Packet
{
    private String key;
    private byte[] data;
    
    public PacketAddonMessage(final String key, final byte[] data) {
        this.key = key;
        this.data = data;
    }
    
    public PacketAddonMessage(final String key, final String json) {
        this.key = key;
        this.data = this.toBytes(json);
    }
    
    public PacketAddonMessage() {
    }
    
    public void read(final PacketBuf buf) {
        this.key = buf.readString();
        final byte[] data = new byte[buf.readInt()];
        buf.readBytes(data);
        this.data = data;
    }
    
    public void write(final PacketBuf buf) {
        buf.writeString(this.key);
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }
    
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getJson() {
        try {
            final StringBuilder outStr = new StringBuilder();
            if (this.data == null || this.data.length == 0) {
                return "";
            }
            if (this.isCompressed(this.data)) {
                final GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(this.data));
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outStr.append(line);
                }
            }
            else {
                outStr.append(Arrays.toString(this.data));
            }
            return outStr.toString();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private byte[] toBytes(final String in) {
        final byte[] str = in.getBytes(StandardCharsets.UTF_8);
        try {
            if (str == null || str.length == 0) {
                return new byte[0];
            }
            final ByteArrayOutputStream obj = new ByteArrayOutputStream();
            final GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(str);
            gzip.flush();
            gzip.close();
            return obj.toByteArray();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private boolean isCompressed(final byte[] compressed) {
        return compressed[0] == 31 && compressed[1] == -117;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public byte[] getData() {
        return this.data;
    }
}
