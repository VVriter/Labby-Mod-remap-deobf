//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.utils;

import java.nio.charset.*;
import org.apache.commons.io.output.*;
import java.io.*;

public final class IOUtil
{
    public static final int EOF = -1;
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    
    public static String toString(final InputStream inputStream) throws IOException {
        return toString(inputStream, StandardCharsets.UTF_8);
    }
    
    public static String toString(final InputStream inputStream, final Charset encoding) throws IOException {
        final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        copy(inputStream, (Writer)stringBuilderWriter, encoding);
        return stringBuilderWriter.toString();
    }
    
    public static void copy(final InputStream input, final Writer output, final Charset inputEncoding) throws IOException {
        final InputStreamReader inputStreamReader = new InputStreamReader(input, inputEncoding);
        copy(inputStreamReader, output);
    }
    
    public static int copy(final Reader input, final Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int)count;
    }
    
    public static long copyLarge(final Reader input, final Writer output) throws IOException {
        return copyLarge(input, output, new char[1024]);
    }
    
    public static long copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0L;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    public static void write(final byte[] data, final OutputStream outputStream) throws IOException {
        if (data == null) {
            return;
        }
        outputStream.write(data);
    }
    
    public static void write(final byte[] data, final Writer output, final Charset encoding) throws IOException {
        if (data == null) {
            return;
        }
        output.write(new String(data, encoding));
    }
    
    public static void write(final CharSequence data, final OutputStream output, final Charset encoding) throws IOException {
        if (data == null) {
            return;
        }
        write(data.toString(), output, encoding);
    }
    
    public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (data == null) {
            return;
        }
        output.write(data.getBytes(encoding));
    }
    
    public static void writeBytes(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final byte[] data = new byte[1024];
        int readable;
        while ((readable = inputStream.read(data, 0, data.length)) != -1) {
            outputStream.write(data, 0, readable);
        }
        outputStream.flush();
    }
}
