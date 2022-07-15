//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.lz4;

import java.nio.*;
import net.jpountz.util.*;

final class LZ4JNICompressor extends LZ4Compressor
{
    public static final LZ4Compressor INSTANCE;
    private static LZ4Compressor SAFE_INSTANCE;
    
    public int compress(final byte[] src, final int srcOff, final int srcLen, final byte[] dest, final int destOff, final int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        final int result = LZ4JNI.LZ4_compress_limitedOutput(src, (ByteBuffer)null, srcOff, srcLen, dest, (ByteBuffer)null, destOff, maxDestLen);
        if (result <= 0) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        return result;
    }
    
    public int compress(final ByteBuffer src, int srcOff, final int srcLen, final ByteBuffer dest, int destOff, final int maxDestLen) {
        ByteBufferUtils.checkNotReadOnly(dest);
        ByteBufferUtils.checkRange(src, srcOff, srcLen);
        ByteBufferUtils.checkRange(dest, destOff, maxDestLen);
        if ((!src.hasArray() && !src.isDirect()) || (!dest.hasArray() && !dest.isDirect())) {
            LZ4Compressor safeInstance = LZ4JNICompressor.SAFE_INSTANCE;
            if (safeInstance == null) {
                safeInstance = (LZ4JNICompressor.SAFE_INSTANCE = LZ4Factory.safeInstance().fastCompressor());
            }
            return safeInstance.compress(src, srcOff, srcLen, dest, destOff, maxDestLen);
        }
        byte[] srcArr = null;
        byte[] destArr = null;
        ByteBuffer srcBuf = null;
        ByteBuffer destBuf = null;
        if (src.hasArray()) {
            srcArr = src.array();
            srcOff += src.arrayOffset();
        }
        else {
            assert src.isDirect();
            srcBuf = src;
        }
        if (dest.hasArray()) {
            destArr = dest.array();
            destOff += dest.arrayOffset();
        }
        else {
            assert dest.isDirect();
            destBuf = dest;
        }
        final int result = LZ4JNI.LZ4_compress_limitedOutput(srcArr, srcBuf, srcOff, srcLen, destArr, destBuf, destOff, maxDestLen);
        if (result <= 0) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        return result;
    }
    
    static {
        INSTANCE = new LZ4JNICompressor();
    }
}
