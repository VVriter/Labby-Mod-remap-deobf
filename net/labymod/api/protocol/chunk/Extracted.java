//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.chunk;

import java.beans.*;

public class Extracted
{
    public short dataSize;
    public byte[] data;
    public int decompressedLength;
    
    @ConstructorProperties({ "dataSize", "data", "decompressedLength" })
    public Extracted(final short dataSize, final byte[] data, final int decompressedLength) {
        this.dataSize = dataSize;
        this.data = data;
        this.decompressedLength = decompressedLength;
    }
}
