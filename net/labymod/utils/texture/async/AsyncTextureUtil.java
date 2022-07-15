//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture.async;

import java.nio.*;
import java.awt.image.*;
import org.lwjgl.opengl.*;
import java.util.function.*;

public class AsyncTextureUtil
{
    private static final ThreadLocal<IntBuffer> DATA_BUFFER;
    
    public static int uploadTextureImage(final int id, final BufferedImage image) {
        allocateTexture(id, image.getWidth(), image.getHeight());
        return uploadTextureImageSub(id, image);
    }
    
    public static int uploadTextureImageSub(final int textureId, final BufferedImage image) {
        bus.i(textureId);
        uploadTextureImageSubImpl(image);
        return textureId;
    }
    
    public static void allocateTexture(final int id, final int width, final int height) {
        bus.i(id);
        GL11.glTexParameteri(3553, 33085, 0);
        GL11.glTexParameterf(3553, 33082, 0.0f);
        GL11.glTexParameterf(3553, 33083, 0.0f);
        GL11.glTexParameterf(3553, 34049, 0.0f);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, (IntBuffer)null);
    }
    
    private static void uploadTextureImageSubImpl(final BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final int k = 4194304 / width;
        final int[] pixels = new int[k * width];
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        final IntBuffer dataBuffer = AsyncTextureUtil.DATA_BUFFER.get();
        for (int l = 0; l < width * height; l += width * k) {
            final int i1 = l / width;
            final int j1 = Math.min(k, height - i1);
            final int k2 = width * j1;
            image.getRGB(0, i1, width, j1, pixels, 0, width);
            copyToBufferPos(dataBuffer, pixels, 0, k2);
            GL11.glTexSubImage2D(3553, 0, 0, i1, width, j1, 32993, 33639, dataBuffer);
        }
    }
    
    private static void copyToBufferPos(final IntBuffer dataBuffer, final int[] pixels, final int start, final int length) {
        int[] aint = pixels;
        if (bib.z().t.g) {
            aint = updateAnaglyph(pixels);
        }
        dataBuffer.clear();
        dataBuffer.put(aint, start, length);
        dataBuffer.position(0).limit(length);
    }
    
    public static int[] updateAnaglyph(final int[] p_110985_0_) {
        final int[] aint = new int[p_110985_0_.length];
        for (int i = 0; i < p_110985_0_.length; ++i) {
            aint[i] = anaglyphColor(p_110985_0_[i]);
        }
        return aint;
    }
    
    public static int anaglyphColor(final int pixels) {
        final int a = pixels >> 24 & 0xFF;
        final int b = pixels >> 16 & 0xFF;
        final int g = pixels >> 8 & 0xFF;
        final int r = pixels & 0xFF;
        final int i1 = (b * 30 + g * 59 + r * 11) / 100;
        final int j1 = (b * 30 + g * 70) / 100;
        final int k1 = (b * 30 + r * 70) / 100;
        return a << 24 | i1 << 16 | j1 << 8 | k1;
    }
    
    static {
        DATA_BUFFER = ThreadLocal.withInitial((Supplier<? extends IntBuffer>)new Supplier<IntBuffer>() {
            @Override
            public IntBuffer get() {
                return bia.f(4194304);
            }
        });
    }
}
