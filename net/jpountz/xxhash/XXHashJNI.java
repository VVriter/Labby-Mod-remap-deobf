//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

import java.nio.*;
import net.jpountz.util.*;

enum XXHashJNI
{
    private static native void init();
    
    static native int XXH32(final byte[] p0, final int p1, final int p2, final int p3);
    
    static native int XXH32BB(final ByteBuffer p0, final int p1, final int p2, final int p3);
    
    static native long XXH32_init(final int p0);
    
    static native void XXH32_update(final long p0, final byte[] p1, final int p2, final int p3);
    
    static native int XXH32_digest(final long p0);
    
    static native void XXH32_free(final long p0);
    
    static native long XXH64(final byte[] p0, final int p1, final int p2, final long p3);
    
    static native long XXH64BB(final ByteBuffer p0, final int p1, final int p2, final long p3);
    
    static native long XXH64_init(final long p0);
    
    static native void XXH64_update(final long p0, final byte[] p1, final int p2, final int p3);
    
    static native long XXH64_digest(final long p0);
    
    static native void XXH64_free(final long p0);
    
    static {
        Native.load();
        init();
    }
}
