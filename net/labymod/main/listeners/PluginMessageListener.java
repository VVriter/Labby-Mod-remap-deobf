//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import net.labymod.support.util.*;
import net.labymod.main.*;
import net.labymod.api.permissions.*;
import net.labymod.api.protocol.liquid.*;
import net.labymod.api.*;
import com.google.gson.*;
import net.labymod.core.asm.*;
import net.labymod.addon.*;
import net.labymod.gui.*;
import net.labymod.utils.manager.*;
import net.labymod.mojang.afec.*;
import net.labymod.settings.*;
import net.labymod.ingamegui.*;
import java.util.*;
import io.netty.handler.codec.*;
import com.google.common.base.*;
import io.netty.buffer.*;

public class PluginMessageListener implements PluginMessageEvent
{
    private static final JsonParser jsonParser;
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (!channelName.equals("labymod3:ccp") && !channelName.equals("labymod3:main") && !channelName.equals("LMC")) {
            Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "[IN] " + channelName);
        }
        try {
            OldPluginMessage.handlePluginMessage(channelName, packetBuffer);
            boolean updatedPermissions = false;
            if (channelName.equals("labymod3:main") || channelName.equals("LMC")) {
                String messageKey = "unknown";
                String messageContent = "couldn't read the content";
                try {
                    if (packetBuffer.readableBytes() <= 0) {
                        throw new Exception("There is no message key in channel " + channelName);
                    }
                    messageKey = this.readStringFromBuffer(32767, packetBuffer);
                    if (packetBuffer.readableBytes() <= 0) {
                        throw new Exception("There is no message content in channel " + channelName);
                    }
                    messageContent = this.readStringFromBuffer(32767, packetBuffer);
                    if (messageKey.equals("PERMISSIONS")) {
                        updatedPermissions = true;
                    }
                    final JsonElement parsedServerMessage = PluginMessageListener.jsonParser.parse(messageContent);
                    LabyMod.getInstance().getEventManager().callServerMessage(messageKey, parsedServerMessage);
                    Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "[IN] [" + channelName + "] " + messageKey + ": " + messageContent + "");
                    if (messageKey.equals("tablist_cache")) {
                        LabyMod.getInstance().setPlayerListCacheEnabled(parsedServerMessage.getAsJsonObject().get("enabled").getAsBoolean());
                        LabyMod.getInstance().getPlayerListDataCache().clear();
                    }
                }
                catch (Exception ex) {
                    Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "Failed parsing JSON message \"" + messageContent + "\" (key: " + messageKey + ")");
                    ex.printStackTrace();
                }
            }
            if (channelName.equals("LABYMOD") || updatedPermissions) {
                Permissions.getPermissionNotifyRenderer().checkChangedPermissions();
            }
            final boolean brandMessage = channelName.equals("MC|Brand");
            if (brandMessage && LabyMod.getSettings().improvedLavaFixedGhostBlocks && LabyMod.getInstance().getCurrentServerData() != null && Permissions.isAllowed(Permissions.Permission.IMPROVED_LAVA)) {
                FixedLiquidBucketProtocol.handleBucketAction(FixedLiquidBucketProtocol.Action.ENABLE, 0, 0, 0);
            }
            if (brandMessage) {
                LabyMod.getInstance().getChunkCachingProtocol().disable((String)null, true);
                final JsonArray addons = new JsonArray();
                for (final LabyModAddon addonInfo : AddonLoader.getAddons()) {
                    if (addonInfo.about != null) {
                        if (addonInfo.about.uuid == null) {
                            continue;
                        }
                        final JsonObject entry = new JsonObject();
                        entry.addProperty("uuid", addonInfo.about.uuid.toString());
                        entry.addProperty("name", addonInfo.about.name);
                        addons.add((JsonElement)entry);
                    }
                }
                final JsonObject ccp = new JsonObject();
                ccp.addProperty("enabled", Boolean.valueOf(LabyMod.getSettings().chunkCaching));
                ccp.addProperty("version", (Number)2);
                final JsonObject shadow = new JsonObject();
                shadow.addProperty("enabled", Boolean.valueOf(true));
                shadow.addProperty("version", (Number)1);
                final JsonObject obj = new JsonObject();
                obj.addProperty("version", "3.9.41");
                obj.add("ccp", (JsonElement)ccp);
                obj.add("shadow", (JsonElement)shadow);
                obj.add("addons", (JsonElement)addons);
                if (LabyModCoreMod.isForge()) {
                    obj.add("mods", (JsonElement)ForgeModsScanner.getInstance().getJsonArray());
                }
                LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("INFO", (JsonElement)obj);
                if (LabyMod.getSettings().loadedShader != null && LabyMod.getSettings().betterShaderSelection) {
                    try {
                        GuiShaderSelection.loadShader(new nf(LabyMod.getSettings().loadedShader));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                SignManager.reset();
                LabyMod.getInstance().getPlayerListDataCache().clear();
                LabyMod.getInstance().getPriorityOverlayRenderer().reset();
                LabyMod.getInstance().getCinematicProtocol().reset(false);
                EntityCulling.updateShadersModValue();
            }
            if (brandMessage && bib.z().E()) {
                ModuleConfig.switchProfile(LabyModModuleEditorGui.SINGLEPLAYER_PROFILE, false);
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public String readStringFromBuffer(final int maxLength, final gy packetBuffer) {
        final int i = this.readVarIntFromBuffer(packetBuffer);
        if (i > maxLength * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")");
        }
        if (i < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final ByteBuf byteBuf = packetBuffer.readBytes(i);
        byte[] bytes = null;
        if (byteBuf.hasArray()) {
            bytes = byteBuf.array();
        }
        else {
            bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        }
        final String s = new String(bytes, Charsets.UTF_8);
        if (s.length() > maxLength) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
        }
        return s;
    }
    
    public int readVarIntFromBuffer(final gy packetBuffer) {
        int i = 0;
        int j = 0;
        while (true) {
            final byte b0 = packetBuffer.readByte();
            i |= (b0 & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((b0 & 0x80) != 0x80) {
                return i;
            }
        }
    }
    
    static {
        jsonParser = new JsonParser();
    }
}
