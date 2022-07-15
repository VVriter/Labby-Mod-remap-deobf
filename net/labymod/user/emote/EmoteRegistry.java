//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote;

import net.labymod.api.events.*;
import net.labymod.user.gui.*;
import java.util.concurrent.*;
import net.labymod.main.*;
import net.labymod.api.*;
import net.labymod.core.*;
import com.google.gson.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.user.*;
import net.labymod.support.util.*;
import net.labymod.utils.*;
import com.google.common.util.concurrent.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.user.emote.keys.provider.*;
import java.nio.*;

public class EmoteRegistry implements Consumer<Object>, ServerMessageEvent
{
    protected Map<Short, KeyFrameStorage> emoteSources;
    protected Map<UUID, EmoteRenderer> playingEmotes;
    private EmoteSelectorGui emoteSelectorGui;
    private boolean cleanPlayingMap;
    private EmoteLoader emoteLoader;
    
    public EmoteRegistry() {
        this.emoteSources = new HashMap<Short, KeyFrameStorage>();
        this.playingEmotes = new ConcurrentHashMap<UUID, EmoteRenderer>();
        this.emoteSelectorGui = new EmoteSelectorGui();
        this.cleanPlayingMap = false;
        this.emoteLoader = new EmoteLoader(this);
    }
    
    public void init() {
        final EventManager eventManager = LabyMod.getInstance().getEventManager();
        eventManager.registerOnIncomingPacket((Consumer)this);
        eventManager.register((ServerMessageEvent)this);
        this.loadEmoteSources();
    }
    
    public void loadEmoteSources() {
        this.emoteLoader.read((Consumer)new Consumer<Map<Short, KeyFrameStorage>>() {
            @Override
            public void accept(final Map<Short, KeyFrameStorage> accepted) {
                EmoteRegistry.this.emoteSources = accepted;
            }
        });
    }
    
    @Override
    public void accept(final Object packet) {
        if (!LabyMod.getSettings().emotes) {
            return;
        }
        final UUID uuid = LabyModCore.getMinecraft().isEmotePacket(packet);
        if (uuid != null) {
            LabyMod.getInstance().getLabyConnect().getClientConnection().sendPacket((Packet)new PacketActionRequest(uuid));
        }
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("emote_api")) {
            return;
        }
        final JsonArray array = serverMessage.getAsJsonArray();
        for (int i = 0; i < array.size(); ++i) {
            final JsonObject object = array.get(i).getAsJsonObject();
            if (object.has("uuid")) {
                if (object.has("emote_id")) {
                    final UUID uuid = UUID.fromString(object.get("uuid").getAsString());
                    final short emoteId = object.get("emote_id").getAsShort();
                    if (uuid.getLeastSignificantBits() == 0L || LabyMod.getInstance().getPriorityOverlayRenderer().isWatermarkValid()) {
                        this.handleEmote(uuid, emoteId);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (!this.cleanPlayingMap) {
            return;
        }
        this.cleanPlayingMap = false;
        for (final EmoteRenderer renderer : this.playingEmotes.values()) {
            if (renderer.isAborted()) {
                final UUID uuid = renderer.getUuid();
                this.abortEmote(uuid);
            }
        }
    }
    
    public EmoteRenderer getEmoteRendererFor(final bua abstractClientPlayer) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final UUID uuid = abstractClientPlayer.bm();
        if ((userManager.isWhitelisted(uuid) || uuid.getLeastSignificantBits() == 0L || LabyMod.getInstance().getPriorityOverlayRenderer().isWatermarkValid()) && !abstractClientPlayer.aX()) {
            return this.playingEmotes.get(uuid);
        }
        return null;
    }
    
    public void playEmote(final short emoteId) {
        if (LabyModCore.getMinecraft().getPlayer() == null || !LabyMod.getSettings().emotes) {
            Debug.log(Debug.EnumDebugMode.EMOTE, "Playing emote canceled because emotes are disabled or player is null.");
            return;
        }
        Debug.log(Debug.EnumDebugMode.EMOTE, "Request labymod chat to play emote id " + emoteId);
        if (!LabyMod.getInstance().getLabyConnect().isOnline()) {
            LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + LabyMod.getMessage("emote_error_not_connected", new Object[0]));
            Debug.log(Debug.EnumDebugMode.EMOTE, "Playing emote canceled because player is not connected to labymod chat!");
            return;
        }
        final byte[] emoteIdBytes = this.shortToBytes(emoteId);
        LabyMod.getInstance().getUserManager().requestAction((short)1, emoteIdBytes, (FutureCallback<PacketActionPlayResponse>)new FutureCallback<PacketActionPlayResponse>() {
            public void onSuccess(final PacketActionPlayResponse responsePacket) {
                if (responsePacket == null || !responsePacket.isAllowed()) {
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, (responsePacket == null) ? "Response timed out" : ("Response packet: " + responsePacket.getReason()));
                    if (emoteId != -1) {
                        String message = null;
                        if (responsePacket == null) {
                            message = LabyMod.getMessage("emote_error_no_response", new Object[0]);
                        }
                        else if (responsePacket.getReason().startsWith("illegal emote use")) {
                            message = LabyMod.getMessage("emote_error_illegal_emote", new Object[0]);
                        }
                        else {
                            message = responsePacket.getReason();
                        }
                        LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + message);
                    }
                }
            }
            
            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
                LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + "Error: " + throwable.getMessage());
            }
        });
    }
    
    public void abortEmote(final UUID uuid) {
        this.playingEmotes.remove(uuid);
    }
    
    public EmoteRenderer handleEmote(final UUID player, final byte[] data) {
        if (data.length < 2) {
            return null;
        }
        final short emoteId = this.bytesToShort(data);
        return this.handleEmote(player, emoteId);
    }
    
    public EmoteRenderer handleEmote(final UUID player, final short emoteId) {
        if (!LabyMod.getSettings().emotes) {
            return null;
        }
        if (emoteId == -1) {
            final EmoteRenderer emoteRenderer = this.playingEmotes.get(player);
            if (emoteRenderer != null) {
                emoteRenderer.abort();
            }
            return null;
        }
        if (!player.equals(LabyMod.getInstance().getPlayerUUID()) && this.playingEmotes.containsKey(player)) {
            return null;
        }
        Debug.log(Debug.EnumDebugMode.EMOTE, player.toString() + " started playing emote " + emoteId);
        if (!this.emoteSources.containsKey(emoteId)) {
            if (player.equals(LabyMod.getInstance().getPlayerUUID())) {
                LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + "Invalid emote id: " + emoteId + ". Maybe restart your game?");
            }
            Debug.log(Debug.EnumDebugMode.EMOTE, "Invalid emote id: " + emoteId);
            return null;
        }
        final KeyFrameStorage keyFrameStorage = this.emoteSources.get(emoteId);
        EmoteRenderer prevRenderer = this.playingEmotes.get(player);
        if (keyFrameStorage != null) {
            if (prevRenderer != null && !prevRenderer.isVisible()) {
                prevRenderer = null;
            }
            try {
                final EmoteRenderer emoteRenderer2 = new EmoteRenderer(player, emoteId, keyFrameStorage.getTimeout(), false, new StoredEmote(keyFrameStorage), prevRenderer);
                this.playingEmotes.put(player, emoteRenderer2);
                return emoteRenderer2;
            }
            catch (Exception error) {
                error.printStackTrace();
                LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + "Error while playing emotes: " + error.getMessage());
                return null;
            }
        }
        if (prevRenderer != null) {
            prevRenderer.abort();
        }
        return null;
    }
    
    public void stream(final UUID player, final EmoteProvider emoteProvider) {
        final EmoteRenderer prevRenderer = this.playingEmotes.get(player);
        if (prevRenderer != null) {
            prevRenderer.abort();
        }
        if (emoteProvider != null) {
            final EmoteRenderer emoteRenderer = new EmoteRenderer(player, (short)(-3), 0L, true, emoteProvider, prevRenderer);
            this.playingEmotes.put(player, emoteRenderer);
        }
        else {
            this.playingEmotes.remove(player);
        }
    }
    
    public short bytesToShort(final byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }
    
    public byte[] shortToBytes(final short value) {
        final byte[] bytes = new byte[2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).putShort(value);
        return bytes;
    }
    
    public Map<Short, KeyFrameStorage> getEmoteSources() {
        return this.emoteSources;
    }
    
    public Map<UUID, EmoteRenderer> getPlayingEmotes() {
        return this.playingEmotes;
    }
    
    public EmoteSelectorGui getEmoteSelectorGui() {
        return this.emoteSelectorGui;
    }
    
    public boolean isCleanPlayingMap() {
        return this.cleanPlayingMap;
    }
    
    public EmoteLoader getEmoteLoader() {
        return this.emoteLoader;
    }
    
    public void setCleanPlayingMap(final boolean cleanPlayingMap) {
        this.cleanPlayingMap = cleanPlayingMap;
    }
}
