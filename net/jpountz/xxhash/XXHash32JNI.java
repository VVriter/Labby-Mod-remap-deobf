//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

import java.nio.*;
import net.jpountz.util.*;

final class XXHash32JNI extends XXHash32
{
    public static final XXHash32 INSTANCE;
    private static XXHash32 SAFE_INSTANCE;
    
    public int hash(final byte[] buf, final int off, final int len, final int seed) {
        SafeUtils.checkRange(buf, off, len);
        return XXHashJNI.XXH32(buf, off, len, seed);
    }
    
    public int hash(final ByteBuffer buf, final int off, final int len, final int seed) {
        if (buf.isDirect()) {
            ByteBufferUtils.checkRange(buf, off, len);
            return XXHashJNI.XXH32BB(buf, off, len, seed);
        }
        if (buf.hasArray()) {
            return this.hash(buf.array(), off + buf.arrayOffset(), len, seed);
        }
        XXHash32 safeInstance = XXHash32JNI.SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = (XXHash32JNI.SAFE_INSTANCE = XXHashFactory.safeInstance().hash32());
        }
        return safeInstance.hash(buf, off, len, seed);
    }
    
    static {
        INSTANCE = new XXHash32JNI();
    }
}
