//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util;

import net.labymod.labyconnect.packets.*;
import java.util.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.nio.charset.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import io.netty.util.*;

public class PacketBufNew extends PacketBuf
{
    public PacketBufNew(final ByteBuf buf) {
        super(buf);
    }
    
    @Override
    public void writeByteArray(final byte[] data) {
        this.writeInt(data.length);
        this.writeBytes(data);
    }
    
    @Override
    public byte[] readByteArray() {
        final byte[] b = new byte[this.readInt()];
        for (int i = 0; i < b.length; ++i) {
            b[i] = this.readByte();
        }
        return b;
    }
    
    @Override
    public void writeEnum(final Enum<?> enume) {
        this.writeInt(enume.ordinal());
    }
    
    @Override
    public void writeUUID(final UUID uuid) {
        this.writeString(uuid.toString());
    }
    
    @Override
    public UUID readUUID() {
        return UUID.fromString(this.readString());
    }
    
    @Override
    public void writeString(final String string) {
        this.writeInt(string.getBytes(Charsets.UTF_8).length);
        this.writeBytes(string.getBytes(Charsets.UTF_8));
    }
    
    @Override
    public String readString() {
        final byte[] a = new byte[this.readInt()];
        for (int i = 0; i < a.length; ++i) {
            a[i] = this.readByte();
        }
        return new String(a, Charsets.UTF_8);
    }
    
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    public boolean release() {
        return this.buf.release();
    }
    
    public boolean release(final int arg0) {
        return this.buf.release(arg0);
    }
    
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }
    
    public byte[] array() {
        return this.buf.array();
    }
    
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }
    
    public int bytesBefore(final byte arg0) {
        return this.buf.bytesBefore(arg0);
    }
    
    public int bytesBefore(final int arg0, final byte arg1) {
        return this.buf.bytesBefore(arg0, arg1);
    }
    
    public int bytesBefore(final int arg0, final int arg1, final byte arg2) {
        return this.buf.bytesBefore(arg0, arg1, arg2);
    }
    
    public int forEachByte(final ByteProcessor processor) {
        return this.buf.forEachByte(processor);
    }
    
    public int forEachByte(final int index, final int length, final ByteProcessor processor) {
        return this.buf.forEachByte(index, length, processor);
    }
    
    public int forEachByteDesc(final ByteProcessor processor) {
        return this.buf.forEachByteDesc(processor);
    }
    
    public int forEachByteDesc(final int index, final int length, final ByteProcessor processor) {
        return this.buf.forEachByteDesc(index, length, processor);
    }
    
    public int capacity() {
        return this.buf.capacity();
    }
    
    public ByteBuf capacity(final int arg0) {
        return this.buf.capacity(arg0);
    }
    
    public ByteBuf clear() {
        return this.buf.clear();
    }
    
    public int compareTo(final ByteBuf arg0) {
        return this.buf.compareTo(arg0);
    }
    
    public ByteBuf copy() {
        return this.buf.copy();
    }
    
    public ByteBuf copy(final int arg0, final int arg1) {
        return this.buf.copy(arg0, arg1);
    }
    
    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }
    
    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }
    
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }
    
    public ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate();
    }
    
    public ByteBuf ensureWritable(final int arg0) {
        return this.buf.ensureWritable(arg0);
    }
    
    public int ensureWritable(final int arg0, final boolean arg1) {
        return this.buf.ensureWritable(arg0, arg1);
    }
    
    public boolean equals(final Object arg0) {
        return this.buf.equals(arg0);
    }
    
    public boolean getBoolean(final int arg0) {
        return this.buf.getBoolean(arg0);
    }
    
    public byte getByte(final int arg0) {
        return this.buf.getByte(arg0);
    }
    
    public ByteBuf getBytes(final int arg0, final ByteBuf arg1) {
        return this.buf.getBytes(arg0, arg1);
    }
    
    public ByteBuf getBytes(final int arg0, final byte[] arg1) {
        return this.buf.getBytes(arg0, arg1);
    }
    
    public ByteBuf getBytes(final int arg0, final ByteBuffer arg1) {
        return this.buf.getBytes(arg0, arg1);
    }
    
    public ByteBuf getBytes(final int arg0, final ByteBuf arg1, final int arg2) {
        return this.buf.getBytes(arg0, arg1, arg2);
    }
    
    public ByteBuf getBytes(final int arg0, final OutputStream arg1, final int arg2) throws IOException {
        return this.buf.getBytes(arg0, arg1, arg2);
    }
    
    public int getBytes(final int arg0, final GatheringByteChannel arg1, final int arg2) throws IOException {
        return this.buf.getBytes(arg0, arg1, arg2);
    }
    
    public int getBytes(final int index, final FileChannel out, final long position, final int length) throws IOException {
        return this.buf.getBytes(index, out, position, length);
    }
    
    public CharSequence getCharSequence(final int index, final int length, final Charset charset) {
        return this.buf.getCharSequence(index, length, charset);
    }
    
    public ByteBuf getBytes(final int arg0, final ByteBuf arg1, final int arg2, final int arg3) {
        return this.buf.getBytes(arg0, arg1, arg2, arg3);
    }
    
    public ByteBuf getBytes(final int arg0, final byte[] arg1, final int arg2, final int arg3) {
        return this.buf.getBytes(arg0, arg1, arg2, arg3);
    }
    
    public char getChar(final int arg0) {
        return this.buf.getChar(arg0);
    }
    
    public double getDouble(final int arg0) {
        return this.buf.getDouble(arg0);
    }
    
    public float getFloat(final int arg0) {
        return this.buf.getFloat(arg0);
    }
    
    public int getInt(final int arg0) {
        return this.buf.getInt(arg0);
    }
    
    public int getIntLE(final int index) {
        return this.buf.getIntLE(index);
    }
    
    public long getLong(final int arg0) {
        return this.buf.getLong(arg0);
    }
    
    public long getLongLE(final int index) {
        return this.buf.getLongLE(index);
    }
    
    public int getMedium(final int arg0) {
        return this.buf.getMedium(arg0);
    }
    
    public int getMediumLE(final int index) {
        return this.buf.getMediumLE(index);
    }
    
    public short getShort(final int arg0) {
        return this.buf.getShort(arg0);
    }
    
    public short getShortLE(final int index) {
        return this.buf.getShortLE(index);
    }
    
    public short getUnsignedByte(final int arg0) {
        return this.buf.getUnsignedByte(arg0);
    }
    
    public long getUnsignedInt(final int arg0) {
        return this.buf.getUnsignedInt(arg0);
    }
    
    public long getUnsignedIntLE(final int index) {
        return this.buf.getUnsignedIntLE(index);
    }
    
    public int getUnsignedMedium(final int arg0) {
        return this.buf.getUnsignedMedium(arg0);
    }
    
    public int getUnsignedMediumLE(final int index) {
        return this.buf.getUnsignedMediumLE(index);
    }
    
    public int getUnsignedShort(final int arg0) {
        return this.buf.getUnsignedShort(arg0);
    }
    
    public int getUnsignedShortLE(final int index) {
        return this.buf.getUnsignedShortLE(index);
    }
    
    public boolean hasArray() {
        return this.buf.hasArray();
    }
    
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }
    
    public int hashCode() {
        return this.buf.hashCode();
    }
    
    public int indexOf(final int arg0, final int arg1, final byte arg2) {
        return this.buf.indexOf(arg0, arg1, arg2);
    }
    
    public ByteBuffer internalNioBuffer(final int arg0, final int arg1) {
        return this.buf.internalNioBuffer(arg0, arg1);
    }
    
    public boolean isDirect() {
        return this.buf.isDirect();
    }
    
    public boolean isReadOnly() {
        return this.buf.isReadOnly();
    }
    
    public ByteBuf asReadOnly() {
        return this.buf.asReadOnly();
    }
    
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    public boolean isReadable(final int arg0) {
        return this.buf.isReadable(arg0);
    }
    
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    public boolean isWritable(final int arg0) {
        return this.buf.isWritable(arg0);
    }
    
    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }
    
    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }
    
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }
    
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }
    
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }
    
    public ByteBuffer nioBuffer(final int arg0, final int arg1) {
        return this.buf.nioBuffer(arg0, arg1);
    }
    
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }
    
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }
    
    public ByteBuffer[] nioBuffers(final int arg0, final int arg1) {
        return this.buf.nioBuffers(arg0, arg1);
    }
    
    public ByteOrder order() {
        return this.buf.order();
    }
    
    public ByteBuf order(final ByteOrder arg0) {
        return this.buf.order(arg0);
    }
    
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }
    
    public byte readByte() {
        return this.buf.readByte();
    }
    
    public ByteBuf readBytes(final int arg0) {
        return this.buf.readBytes(arg0);
    }
    
    public ByteBuf readBytes(final ByteBuf arg0) {
        return this.buf.readBytes(arg0);
    }
    
    public ByteBuf readBytes(final byte[] arg0) {
        return this.buf.readBytes(arg0);
    }
    
    public ByteBuf readBytes(final ByteBuffer arg0) {
        return this.buf.readBytes(arg0);
    }
    
    public ByteBuf readBytes(final ByteBuf arg0, final int arg1) {
        return this.buf.readBytes(arg0, arg1);
    }
    
    public ByteBuf readBytes(final OutputStream arg0, final int arg1) throws IOException {
        return this.buf.readBytes(arg0, arg1);
    }
    
    public int readBytes(final GatheringByteChannel arg0, final int arg1) throws IOException {
        return this.buf.readBytes(arg0, arg1);
    }
    
    public CharSequence readCharSequence(final int length, final Charset charset) {
        return this.readCharSequence(length, charset);
    }
    
    public int readBytes(final FileChannel out, final long position, final int length) throws IOException {
        return this.buf.readBytes(out, position, length);
    }
    
    public ByteBuf readBytes(final ByteBuf arg0, final int arg1, final int arg2) {
        return this.buf.readBytes(arg0, arg1, arg2);
    }
    
    public ByteBuf readBytes(final byte[] arg0, final int arg1, final int arg2) {
        return this.buf.readBytes(arg0, arg1, arg2);
    }
    
    public char readChar() {
        return this.buf.readChar();
    }
    
    public double readDouble() {
        return this.buf.readDouble();
    }
    
    public float readFloat() {
        return this.buf.readFloat();
    }
    
    public int readInt() {
        return this.buf.readInt();
    }
    
    public int readIntLE() {
        return this.buf.readIntLE();
    }
    
    public long readLong() {
        return this.buf.readLong();
    }
    
    public long readLongLE() {
        return this.buf.readLongLE();
    }
    
    public int readMedium() {
        return this.buf.readMedium();
    }
    
    public int readMediumLE() {
        return this.buf.readMediumLE();
    }
    
    public short readShort() {
        return this.buf.readShort();
    }
    
    public short readShortLE() {
        return this.buf.readShortLE();
    }
    
    public ByteBuf readSlice(final int arg0) {
        return this.buf.readSlice(arg0);
    }
    
    public ByteBuf readRetainedSlice(final int length) {
        return this.buf.readRetainedSlice(length);
    }
    
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }
    
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }
    
    public long readUnsignedIntLE() {
        return this.buf.readUnsignedIntLE();
    }
    
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }
    
    public int readUnsignedMediumLE() {
        return this.buf.readUnsignedMediumLE();
    }
    
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }
    
    public int readUnsignedShortLE() {
        return this.buf.readUnsignedShortLE();
    }
    
    public int readableBytes() {
        return this.buf.readableBytes();
    }
    
    public int readerIndex() {
        return this.buf.readerIndex();
    }
    
    public ByteBuf readerIndex(final int arg0) {
        return this.buf.readerIndex(arg0);
    }
    
    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }
    
    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }
    
    public ByteBuf retain() {
        return this.buf.retain();
    }
    
    public ByteBuf touch() {
        return this.buf.touch();
    }
    
    public ByteBuf touch(final Object hint) {
        return this.buf.touch(hint);
    }
    
    public ByteBuf retain(final int arg0) {
        return this.buf.retain(arg0);
    }
    
    public ByteBuf setBoolean(final int arg0, final boolean arg1) {
        return this.buf.setBoolean(arg0, arg1);
    }
    
    public ByteBuf setByte(final int arg0, final int arg1) {
        return this.buf.setByte(arg0, arg1);
    }
    
    public ByteBuf setBytes(final int arg0, final ByteBuf arg1) {
        return this.buf.setBytes(arg0, arg1);
    }
    
    public ByteBuf setBytes(final int arg0, final byte[] arg1) {
        return this.buf.setBytes(arg0, arg1);
    }
    
    public ByteBuf setBytes(final int arg0, final ByteBuffer arg1) {
        return this.buf.setBytes(arg0, arg1);
    }
    
    public ByteBuf setBytes(final int arg0, final ByteBuf arg1, final int arg2) {
        return this.buf.setBytes(arg0, arg1, arg2);
    }
    
    public int setBytes(final int arg0, final InputStream arg1, final int arg2) throws IOException {
        return this.buf.setBytes(arg0, arg1, arg2);
    }
    
    public int setBytes(final int arg0, final ScatteringByteChannel arg1, final int arg2) throws IOException {
        return this.buf.setBytes(arg0, arg1, arg2);
    }
    
    public int setBytes(final int index, final FileChannel in, final long position, final int length) throws IOException {
        return this.buf.setBytes(index, in, position, length);
    }
    
    public ByteBuf setBytes(final int arg0, final ByteBuf arg1, final int arg2, final int arg3) {
        return this.buf.setBytes(arg0, arg1, arg2, arg3);
    }
    
    public ByteBuf setBytes(final int arg0, final byte[] arg1, final int arg2, final int arg3) {
        return this.buf.setBytes(arg0, arg1, arg2, arg3);
    }
    
    public ByteBuf setChar(final int arg0, final int arg1) {
        return this.buf.setChar(arg0, arg1);
    }
    
    public ByteBuf setDouble(final int arg0, final double arg1) {
        return this.buf.setDouble(arg0, arg1);
    }
    
    public ByteBuf setFloat(final int arg0, final float arg1) {
        return this.buf.setFloat(arg0, arg1);
    }
    
    public ByteBuf setIndex(final int arg0, final int arg1) {
        return this.buf.setIndex(arg0, arg1);
    }
    
    public ByteBuf setInt(final int arg0, final int arg1) {
        return this.buf.setInt(arg0, arg1);
    }
    
    public ByteBuf setIntLE(final int index, final int value) {
        return this.buf.setIntLE(index, value);
    }
    
    public ByteBuf setLong(final int arg0, final long arg1) {
        return this.buf.setLong(arg0, arg1);
    }
    
    public ByteBuf setLongLE(final int index, final long value) {
        return this.buf.setLongLE(index, value);
    }
    
    public ByteBuf setMedium(final int arg0, final int arg1) {
        return this.buf.setMedium(arg0, arg1);
    }
    
    public ByteBuf setMediumLE(final int index, final int value) {
        return this.buf.setMediumLE(index, value);
    }
    
    public ByteBuf setShort(final int arg0, final int arg1) {
        return this.buf.setShort(arg0, arg1);
    }
    
    public ByteBuf setShortLE(final int index, final int value) {
        return this.buf.setShortLE(index, value);
    }
    
    public ByteBuf setZero(final int arg0, final int arg1) {
        return this.buf.setZero(arg0, arg1);
    }
    
    public int setCharSequence(final int index, final CharSequence sequence, final Charset charset) {
        return this.buf.setCharSequence(index, sequence, charset);
    }
    
    public ByteBuf skipBytes(final int arg0) {
        return this.buf.skipBytes(arg0);
    }
    
    public ByteBuf slice() {
        return this.buf.slice();
    }
    
    public ByteBuf retainedSlice() {
        return this.buf.retainedSlice();
    }
    
    public ByteBuf slice(final int arg0, final int arg1) {
        return this.buf.slice(arg0, arg1);
    }
    
    public ByteBuf retainedSlice(final int index, final int length) {
        return this.buf.retainedSlice(index, length);
    }
    
    public String toString() {
        return this.buf.toString();
    }
    
    public String toString(final Charset arg0) {
        return this.buf.toString(arg0);
    }
    
    public String toString(final int arg0, final int arg1, final Charset arg2) {
        return this.buf.toString(arg0, arg1, arg2);
    }
    
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }
    
    public int writableBytes() {
        return this.buf.writableBytes();
    }
    
    public ByteBuf writeBoolean(final boolean arg0) {
        return this.buf.writeBoolean(arg0);
    }
    
    public ByteBuf writeByte(final int arg0) {
        return this.buf.writeByte(arg0);
    }
    
    public ByteBuf writeBytes(final ByteBuf arg0) {
        return this.buf.writeBytes(arg0);
    }
    
    public ByteBuf writeBytes(final byte[] arg0) {
        return this.buf.writeBytes(arg0);
    }
    
    public ByteBuf writeBytes(final ByteBuffer arg0) {
        return this.buf.writeBytes(arg0);
    }
    
    public ByteBuf writeBytes(final ByteBuf arg0, final int arg1) {
        return this.buf.writeBytes(arg0, arg1);
    }
    
    public int writeBytes(final InputStream arg0, final int arg1) throws IOException {
        return this.buf.writeBytes(arg0, arg1);
    }
    
    public int writeBytes(final ScatteringByteChannel arg0, final int arg1) throws IOException {
        return this.buf.writeBytes(arg0, arg1);
    }
    
    public int writeBytes(final FileChannel in, final long position, final int length) throws IOException {
        return this.buf.writeBytes(in, position, length);
    }
    
    public ByteBuf writeBytes(final ByteBuf arg0, final int arg1, final int arg2) {
        return this.buf.writeBytes(arg0, arg1, arg2);
    }
    
    public ByteBuf writeBytes(final byte[] arg0, final int arg1, final int arg2) {
        return this.buf.writeBytes(arg0, arg1, arg2);
    }
    
    public ByteBuf writeChar(final int arg0) {
        return this.buf.writeChar(arg0);
    }
    
    public ByteBuf writeDouble(final double arg0) {
        return this.buf.writeDouble(arg0);
    }
    
    public ByteBuf writeFloat(final float arg0) {
        return this.buf.writeFloat(arg0);
    }
    
    public ByteBuf writeInt(final int arg0) {
        return this.buf.writeInt(arg0);
    }
    
    public ByteBuf writeIntLE(final int value) {
        return this.buf.writeIntLE(value);
    }
    
    public ByteBuf writeLong(final long arg0) {
        return this.buf.writeLong(arg0);
    }
    
    public ByteBuf writeLongLE(final long value) {
        return this.buf.writeLongLE(value);
    }
    
    public ByteBuf writeMedium(final int arg0) {
        return this.buf.writeMedium(arg0);
    }
    
    public ByteBuf writeMediumLE(final int value) {
        return this.buf.writeMediumLE(value);
    }
    
    public ByteBuf writeShort(final int arg0) {
        return this.buf.writeShort(arg0);
    }
    
    public ByteBuf writeShortLE(final int value) {
        return this.buf.writeShortLE(value);
    }
    
    public ByteBuf writeZero(final int arg0) {
        return this.buf.writeZero(arg0);
    }
    
    public int writeCharSequence(final CharSequence sequence, final Charset charset) {
        return this.buf.writeCharSequence(sequence, charset);
    }
    
    public int writerIndex() {
        return this.buf.writerIndex();
    }
    
    public ByteBuf writerIndex(final int arg0) {
        return this.buf.writerIndex(arg0);
    }
}
