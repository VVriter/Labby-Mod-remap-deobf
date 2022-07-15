//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

import java.nio.*;

public abstract class XXHash32
{
    public abstract int hash(final byte[] p0, final int p1, final int p2, final int p3);
    
    public abstract int hash(final ByteBuffer p0, final int p1, final int p2, final int p3);
    
    public final int hash(final ByteBuffer buf, final int seed) {
        final int hash = this.hash(buf, buf.position(), buf.remaining(), seed);
        buf.position(buf.limit());
        return hash;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
