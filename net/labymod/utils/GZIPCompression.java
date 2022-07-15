//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.util.zip.*;
import java.io.*;

public class GZIPCompression
{
    public static byte[] compress(final byte[] input) {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
            final GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(input);
            gzip.close();
            final byte[] compressed = bos.toByteArray();
            bos.close();
            return compressed;
        }
        catch (IOException e) {
            e.printStackTrace();
            return input;
        }
    }
    
    public static byte[] decompress(final byte[] input) {
        try {
            final byte[] buffer = new byte[1024];
            final ByteArrayInputStream bis = new ByteArrayInputStream(input);
            final GZIPInputStream gis = new GZIPInputStream(bis);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len;
            while ((len = gis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            gis.close();
            out.close();
            return out.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            return input;
        }
    }
}
