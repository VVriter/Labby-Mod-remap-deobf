//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote;

import com.google.gson.*;
import net.labymod.user.emote.keys.provider.*;
import java.util.*;
import java.io.*;
import net.labymod.support.util.*;
import net.labymod.utils.*;
import net.labymod.utils.request.*;

public class EmoteLoader
{
    private static final Gson GSON;
    private EmoteRegistry emoteRegistry;
    
    public EmoteLoader(final EmoteRegistry emoteRegistry) {
        this.emoteRegistry = emoteRegistry;
    }
    
    private void decompile(final byte[] decompressed, final Consumer<Map<Short, KeyFrameStorage>> consumer) throws Exception {
        final Map<Short, KeyFrameStorage> emoteSources = new HashMap<Short, KeyFrameStorage>();
        final ByteArrayInputStream bais = new ByteArrayInputStream(decompressed);
        final DataInputStream dis = new DataInputStream(bais);
        for (int count = dis.readInt(), i = 0; i < count; ++i) {
            final short id = dis.readShort();
            final byte[] nameInBytes = new byte[dis.readInt()];
            dis.read(nameInBytes);
            final String name = new String(nameInBytes);
            final byte[] jsonInBytes = new byte[dis.readInt()];
            dis.read(jsonInBytes);
            final String json = new String(jsonInBytes);
            try {
                final KeyFrameStorage storage = (KeyFrameStorage)EmoteLoader.GSON.fromJson(json, (Class)KeyFrameStorage.class);
                storage.setName(name);
                storage.setId(id);
                emoteSources.put(storage.getId(), storage);
            }
            catch (Exception error) {
                Debug.log(Debug.EnumDebugMode.EMOTE, "Invalid emote data: " + json);
            }
        }
        Debug.log(Debug.EnumDebugMode.EMOTE, "Loaded " + this.emoteRegistry.emoteSources.size() + " emotes!");
        consumer.accept(emoteSources);
    }
    
    public Map<Short, KeyFrameStorage> read(final Consumer<Map<Short, KeyFrameStorage>> consumer) {
        DownloadServerRequest.getBytesAsync("https://dl.labymod.net/emotes/emotedata", new ServerResponse<byte[]>() {
            @Override
            public void success(final byte[] bytes) {
                try {
                    final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                    final DataInputStream dis = new DataInputStream(bais);
                    final byte[] compressed = new byte[dis.readInt()];
                    dis.read(compressed);
                    final byte[] decompressed = GZIPCompression.decompress(compressed);
                    EmoteLoader.this.decompile(decompressed, consumer);
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                Debug.log(Debug.EnumDebugMode.EMOTE, "Emotedata response code is " + exception.getCode());
            }
        });
        return null;
    }
    
    static {
        GSON = new Gson();
    }
}
