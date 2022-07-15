//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

import java.nio.*;
import net.jpountz.util.*;

final class XXHash64JNI extends XXHash64
{
    public static final XXHash64 INSTANCE;
    private static XXHash64 SAFE_INSTANCE;
    
    public long hash(final byte[] buf, final int off, final int len, final long seed) {
        SafeUtils.checkRange(buf, off, len);
        return XXHashJNI.XXH64(buf, off, len, seed);
    }
    
    public long hash(final ByteBuffer buf, final int off, final int len, final long seed) {
        if (buf.isDirect()) {
            ByteBufferUtils.checkRange(buf, off, len);
            return XXHashJNI.XXH64BB(buf, off, len, seed);
        }
        if (buf.hasArray()) {
            return this.hash(buf.array(), off + buf.arrayOffset(), len, seed);
        }
        XXHash64 safeInstance = XXHash64JNI.SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = (XXHash64JNI.SAFE_INSTANCE = XXHashFactory.safeInstance().hash64());
        }
        return safeInstance.hash(buf, off, len, seed);
    }
    
    static {
        INSTANCE = new XXHash64JNI();
    }
}
