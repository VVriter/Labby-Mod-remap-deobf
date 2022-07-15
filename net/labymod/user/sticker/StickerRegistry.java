//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.sticker;

import net.labymod.api.events.*;
import net.labymod.user.gui.*;
import net.labymod.main.*;
import net.labymod.api.*;
import net.labymod.support.util.*;
import net.labymod.utils.request.*;
import java.util.*;
import com.google.gson.*;
import net.labymod.user.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import com.google.common.util.concurrent.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.user.sticker.data.*;
import java.nio.*;

public class StickerRegistry implements ServerMessageEvent
{
    public static final long STICKER_DURATION = 4000L;
    private StickerSelectorGui stickerSelectorGui;
    protected StickerData stickerData;
    
    public StickerRegistry() {
        this.stickerSelectorGui = new StickerSelectorGui();
    }
    
    public void init() {
        final EventManager eventManager = LabyMod.getInstance().getEventManager();
        eventManager.register((ServerMessageEvent)this);
        this.loadPacks();
    }
    
    public void loadPacks() {
        DownloadServerRequest.getStringAsync("https://dl.labymod.net/stickers.json", new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                StickerRegistry.this.stickerData = (StickerData)new Gson().fromJson(json, (Class)StickerData.class);
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                Debug.log(Debug.EnumDebugMode.EMOTE, "Stickerdata response code is " + exception.getCode());
            }
        });
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("sticker_api")) {
            return;
        }
        final JsonArray array = serverMessage.getAsJsonArray();
        for (int i = 0; i < array.size(); ++i) {
            final JsonObject object = array.get(i).getAsJsonObject();
            if (object.has("uuid")) {
                if (object.has("sticker_id")) {
                    final UUID uuid = UUID.fromString(object.get("uuid").getAsString());
                    final short stickerId = object.get("sticker_id").getAsShort();
                    final UserManager userManager = LabyMod.getInstance().getUserManager();
                    this.handleSticker(userManager.getUser(uuid), stickerId);
                }
            }
        }
    }
    
    public void playSticker(final User user, final short stickerId) {
        if (LabyModCore.getMinecraft().getPlayer() == null || !LabyMod.getSettings().stickers) {
            Debug.log(Debug.EnumDebugMode.STICKER, "Playing sticker canceled because sticker are disabled or player is null.");
            return;
        }
        if (!LabyMod.getInstance().getLabyConnect().isOnline()) {
            LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + LabyMod.getMessage("sticker_error_not_connected", new Object[0]));
            Debug.log(Debug.EnumDebugMode.STICKER, "Playing sticker canceled because player is not connected to labymod chat!");
            return;
        }
        final byte[] stickerIdBytes = this.shortToBytes(stickerId);
        Debug.log(Debug.EnumDebugMode.STICKER, "Request labymod chat to play sticker id " + stickerId);
        LabyMod.getInstance().getUserManager().requestAction((short)3, stickerIdBytes, (FutureCallback<PacketActionPlayResponse>)new FutureCallback<PacketActionPlayResponse>() {
            public void onSuccess(final PacketActionPlayResponse responsePacket) {
                if (responsePacket == null || !responsePacket.isAllowed()) {
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, (responsePacket == null) ? "Response timed out" : ("Response packet: " + responsePacket.getReason()));
                    if (stickerId != -1) {
                        String message = null;
                        if (responsePacket == null) {
                            message = LabyMod.getMessage("sticker_no_response", new Object[0]);
                        }
                        else if (responsePacket.getReason().startsWith("illegal sticker use")) {
                            message = LabyMod.getMessage("sticker_error_illegal_sticker", new Object[0]);
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
    
    public Sticker getSticker(final short id) {
        for (final StickerPack stickerPack : this.stickerData.getPacks()) {
            for (final Sticker sticker : stickerPack.getStickers()) {
                if (sticker.getId() == id) {
                    return sticker;
                }
            }
        }
        return null;
    }
    
    public void handleSticker(final User user, final short id) {
        user.setPlayingSticker(id);
        if (id != -1) {
            user.setStickerStartedPlaying(System.currentTimeMillis());
        }
    }
    
    public void stopSticker(final User user) {
        this.handleSticker(user, (short)(-1));
    }
    
    public short bytesToShort(final byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }
    
    public byte[] shortToBytes(final short value) {
        final byte[] bytes = new byte[2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).putShort(value);
        return bytes;
    }
    
    public StickerSelectorGui getStickerSelectorGui() {
        return this.stickerSelectorGui;
    }
    
    public StickerData getStickerData() {
        return this.stickerData;
    }
}
