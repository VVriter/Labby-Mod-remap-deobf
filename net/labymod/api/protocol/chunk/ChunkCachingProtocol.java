//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.chunk;

import net.labymod.api.events.*;
import java.util.concurrent.atomic.*;
import net.jpountz.xxhash.*;
import net.labymod.api.*;
import net.labymod.support.util.*;
import io.netty.channel.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import io.netty.buffer.*;
import java.util.*;
import java.io.*;
import net.labymod.main.*;

public class ChunkCachingProtocol extends ChannelInboundHandlerAdapter implements PluginMessageEvent, Consumer<ServerData>
{
    private static final boolean MC18;
    public static final String PM_CHANNEL = "labymod3:ccp";
    public static final int CCP_VERSION = 2;
    private boolean cachingSupported;
    private AtomicLong currentlyCachedBytes;
    private long maxChunkCacheSize;
    private XXHashFactory factory;
    private ChunkHashMap<Integer, Extracted> chunkCache;
    private long loadedBytesInSession;
    private long downloadedBytesInSession;
    
    public ChunkCachingProtocol() {
        this.cachingSupported = false;
        this.currentlyCachedBytes = new AtomicLong(0L);
        this.factory = XXHashFactory.fastestInstance();
        this.chunkCache = new ChunkHashMap<Integer, Extracted>();
        this.loadedBytesInSession = 0L;
        this.downloadedBytesInSession = 0L;
        final EventManager eventManager = LabyMod.getInstance().getLabyModAPI().getEventManager();
        eventManager.register((PluginMessageEvent)this);
        eventManager.registerOnQuit((Consumer)this);
    }
    
    public void accept(final ServerData accepted) {
        if (accepted != null && accepted.getIp() != null) {
            this.disable(accepted.getIp(), false);
        }
    }
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (!channelName.equals("labymod3:ccp") || !LabyMod.getSettings().chunkCaching) {
            return;
        }
        if (!this.cachingSupported) {
            this.cachingSupported = true;
            long size = LabyMod.getSettings().chunkCachingSize * 1000000;
            final long maxSize = Runtime.getRuntime().maxMemory();
            if (size > maxSize) {
                size = maxSize;
            }
            this.maxChunkCacheSize = size;
            Debug.log(Debug.EnumDebugMode.CCP, "Chunk caching is supported by the server! Available cache size: " + ModUtils.humanReadableByteCount(this.maxChunkCacheSize, true, true));
            if (!ChunkCachingProtocol.MC18) {
                try {
                    final Channel channel = LabyMod.getInstance().getNettyChannel();
                    final ChannelPipeline pipeline = channel.pipeline();
                    if (pipeline.context("ccp") == null) {
                        pipeline.addBefore("decoder", "ccp", (ChannelHandler)new CCPChannelHandler(this));
                    }
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
            if (LabyMod.getSettings().chunkCachingStoreInFile) {
                final ServerData serverData = LabyMod.getInstance().getCurrentServerData();
                if (serverData != null && serverData.getIp() != null && serverData.getIp().length() > 2) {
                    this.loadInCurrentCache(serverData.getIp());
                }
            }
        }
        try {
            this.handleCCPMessageProtocol(packetBuffer);
        }
        catch (Exception error2) {
            error2.printStackTrace();
        }
    }
    
    private void handleCCPMessageProtocol(final gy packetBuffer) {
        final boolean isBulk = packetBuffer.readBoolean();
        final boolean flag = packetBuffer.readBoolean();
        final short arraySize = packetBuffer.readShort();
        final int[][] responseList = new int[arraySize][4];
        final Extracted[] allChunks = new Extracted[arraySize];
        int cachedChunkCount = 0;
        for (int i = 0; i < arraySize; ++i) {
            final int hash = packetBuffer.readInt();
            final int chunkX = packetBuffer.readInt();
            final int chunkZ = packetBuffer.readInt();
            final Extracted extracted = this.chunkCache.get(hash);
            final boolean isCached = extracted != null;
            this.chunkCache.renewEntry(hash);
            responseList[i][0] = (isCached ? 1 : 0);
            responseList[i][1] = hash;
            responseList[i][2] = chunkX;
            responseList[i][3] = chunkZ;
            if (isBulk) {
                allChunks[i] = extracted;
                if (isCached) {
                    ++cachedChunkCount;
                }
            }
            else if (isCached) {
                LabyModCore.getCoreAdapter().getProtocolAdapter().loadChunk(this, extracted, chunkX, chunkZ, flag);
                this.loadedBytesInSession += extracted.decompressedLength;
            }
        }
        if (isBulk && cachedChunkCount > 0) {
            this.loadBulkChunks(responseList, allChunks, cachedChunkCount, flag);
        }
        this.sendResponseToServer(responseList);
    }
    
    protected void onReceive112ChunkData(final byte[] array) {
        final int hash = this.factory.hash32().hash(array, 9, array.length - 9, -42421337);
        final byte[] compressed = GZIPCompression.compress(array);
        long count = this.currentlyCachedBytes.addAndGet(compressed.length);
        final Extracted extracted = new Extracted((short)0, compressed, array.length);
        this.chunkCache.put(hash, extracted);
        for (count += compressed.length; count > this.maxChunkCacheSize; count = this.currentlyCachedBytes.addAndGet(-this.chunkCache.removeEldestEntry().data.length)) {}
        this.downloadedBytesInSession += array.length;
    }
    
    public void onReceive18ChunkData(final int x, final int z, final int dataSize, final byte[] array) {
        if (dataSize == 0 || !this.cachingSupported) {
            return;
        }
        final int hash = this.factory.hash32().hash(array, 0, array.length, -42421337);
        final byte[] compressed = GZIPCompression.compress(array);
        long count = this.currentlyCachedBytes.addAndGet(compressed.length);
        final Extracted extracted = new Extracted((short)dataSize, compressed, array.length);
        this.chunkCache.put(hash, extracted);
        Extracted eldest;
        for (count += compressed.length; count > this.maxChunkCacheSize; count = this.currentlyCachedBytes.addAndGet(-eldest.data.length)) {
            eldest = this.chunkCache.removeEldestEntry();
            if (eldest != null) {}
        }
        this.downloadedBytesInSession += array.length;
    }
    
    private void sendResponseToServer(final int[][] responseList) {
        final gy packetBuffer = new gy(Unpooled.buffer());
        packetBuffer.writeByte(33);
        packetBuffer.writeShort(responseList.length);
        for (final int[] entry : responseList) {
            final int isCached = entry[0];
            final int x = entry[2];
            final int z = entry[3];
            packetBuffer.writeByte(isCached);
            packetBuffer.writeInt(x);
            packetBuffer.writeInt(z);
        }
        LabyModCore.getMinecraft().sendPluginMessage("labymod3:ccp", packetBuffer);
    }
    
    private void loadBulkChunks(final int[][] responseList, final Extracted[] allChunks, final int cachedChunkCount, final boolean flag) {
        final Extracted[] cachedChunks = new Extracted[cachedChunkCount];
        final int[] chunkXArray = new int[cachedChunkCount];
        final int[] chunkZArray = new int[cachedChunkCount];
        int t = 0;
        for (int i = 0; i < responseList.length; ++i) {
            if (responseList[i][0] == 1 && allChunks[i] != null) {
                chunkXArray[t] = responseList[i][2];
                chunkZArray[t] = responseList[i][3];
                final Extracted extracted = allChunks[i];
                cachedChunks[t] = extracted;
                this.loadedBytesInSession += extracted.decompressedLength;
                ++t;
            }
        }
        LabyModCore.getCoreAdapter().getProtocolAdapter().loadChunkBulk(this, cachedChunks, chunkXArray, chunkZArray);
    }
    
    public void disable(final String serverKey, final boolean worldSwitch) {
        if (worldSwitch) {
            this.cachingSupported = false;
            return;
        }
        if (!this.cachingSupported) {
            return;
        }
        Debug.log(Debug.EnumDebugMode.CCP, "Removed " + this.chunkCache.size() + " (" + ModUtils.humanReadableByteCount(this.currentlyCachedBytes.get(), true, true) + ") chunks in cache!");
        if (LabyMod.getSettings().chunkCachingStoreInFile) {
            try {
                this.saveCurrentCache(serverKey);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.chunkCache.clear();
        this.currentlyCachedBytes.set(0L);
        this.loadedBytesInSession = 0L;
        this.downloadedBytesInSession = 0L;
    }
    
    private void saveCurrentCache(final String key) throws Exception {
        final File cacheFile = new File("LabyMod/ccp/", key);
        if (!cacheFile.getParentFile().exists()) {
            cacheFile.getParentFile().mkdir();
        }
        final DataOutputStream dos = new DataOutputStream(new FileOutputStream(cacheFile));
        dos.writeInt(this.chunkCache.size());
        for (final Map.Entry<Integer, Extracted> entry : this.chunkCache.entrySet()) {
            dos.writeInt(entry.getKey());
            final Extracted extracted = entry.getValue();
            dos.writeShort(extracted.dataSize);
            dos.writeInt(extracted.decompressedLength);
            dos.writeInt(extracted.data.length);
            dos.write(extracted.data);
        }
        dos.writeLong(this.currentlyCachedBytes.get());
        dos.writeLong(this.loadedBytesInSession);
        dos.writeLong(this.downloadedBytesInSession);
        dos.flush();
        dos.close();
    }
    
    private void loadInCurrentCache(final String key) {
        final File cacheFile = new File("LabyMod/ccp/", key);
        if (!cacheFile.exists()) {
            return;
        }
        try {
            final DataInputStream dis = new DataInputStream(new FileInputStream(cacheFile));
            for (int len = dis.readInt(), i = 0; i < len; ++i) {
                final int hash = dis.readInt();
                final short dataSize = dis.readShort();
                final int decompressedLength = dis.readInt();
                final byte[] data = new byte[dis.readInt()];
                dis.read(data);
                final Extracted extracted = new Extracted(dataSize, data, decompressedLength);
                this.chunkCache.put(hash, extracted);
            }
            this.currentlyCachedBytes.set(dis.readLong());
            this.loadedBytesInSession = dis.readLong();
            this.downloadedBytesInSession = dis.readLong();
            dis.close();
        }
        catch (Exception error) {
            error.printStackTrace();
            cacheFile.delete();
        }
    }
    
    public boolean isCachingSupported() {
        return this.cachingSupported;
    }
    
    public AtomicLong getCurrentlyCachedBytes() {
        return this.currentlyCachedBytes;
    }
    
    public ChunkHashMap<Integer, Extracted> getChunkCache() {
        return this.chunkCache;
    }
    
    public long getLoadedBytesInSession() {
        return this.loadedBytesInSession;
    }
    
    public long getDownloadedBytesInSession() {
        return this.downloadedBytesInSession;
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
