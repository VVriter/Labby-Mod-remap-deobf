//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api;

import net.labymod.main.*;
import com.google.gson.*;
import net.labymod.core.*;
import io.netty.buffer.*;
import net.labymod.support.util.*;
import net.labymod.ingamegui.*;
import net.labymod.servermanager.*;
import net.labymod.labyconnect.*;
import net.labymod.user.*;
import net.labymod.utils.texture.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.labyconnect.packets.*;
import net.minecraftforge.common.*;

public class LabyModAPI
{
    public static final String LABYMOD_API_CHANNEL = "labymod3:main";
    public static final String LABYMOD_API_CHANNEL_OLD = "LMC";
    public long lastServerSwitchTime;
    private final Queue<DelayedPayloadMessage> delayedPayloadMessageQueue;
    private LabyMod labyMod;
    private boolean crosshairHidden;
    
    public LabyModAPI(final LabyMod labyMod) {
        this.lastServerSwitchTime = -1L;
        this.delayedPayloadMessageQueue = new ArrayDeque<DelayedPayloadMessage>();
        this.labyMod = labyMod;
    }
    
    public void sendJsonMessageToServer(final String messageKey, final JsonElement message) {
        if (LabyModCore.getMinecraft().getPlayer() == null || bib.z().E()) {
            return;
        }
        final boolean isInfoMessage = messageKey.equals("INFO");
        boolean canSendPayload = this.lastServerSwitchTime + 5000L <= System.currentTimeMillis();
        if (!canSendPayload && !isInfoMessage) {
            this.delayedPayloadMessageQueue.add(new DelayedPayloadMessage(messageKey, message));
            return;
        }
        if (isInfoMessage) {
            canSendPayload = true;
            this.lastServerSwitchTime = -1L;
        }
        final gy packetBuffer = new gy(Unpooled.buffer());
        packetBuffer.a(messageKey);
        packetBuffer.a(message.toString());
        Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "[OUT] [labymod3:main] " + messageKey + ": " + message.toString());
        LabyModCore.getMinecraft().sendPluginMessage("labymod3:main", new gy(packetBuffer.copy()));
        LabyModCore.getMinecraft().sendPluginMessage("LMC", packetBuffer);
        if (isInfoMessage && !this.delayedPayloadMessageQueue.isEmpty()) {
            DelayedPayloadMessage delayedPayloadMessage;
            while ((delayedPayloadMessage = this.delayedPayloadMessageQueue.poll()) != null) {
                this.sendJsonMessageToServer(delayedPayloadMessage.messageKey, delayedPayloadMessage.message);
            }
        }
    }
    
    public void registerModule(final Module module) {
        Module.registerModule(module, true);
    }
    
    public void registerServerSupport(final LabyModAddon labyModAddon, final Server server) {
        this.labyMod.getServerManager().registerServerSupport(labyModAddon, server);
    }
    
    public void sendPluginMessage(final String channelName, final gy packetBuffer) {
        LabyModCore.getMinecraft().sendPluginMessage(channelName, packetBuffer);
    }
    
    public void sendAddonMessage(final String key, final byte[] data) {
        final LabyConnect chatClient = LabyMod.getInstance().getLabyConnect();
        final ClientConnection clientConnection = chatClient.getClientConnection();
        if (clientConnection != null && chatClient.isOnline()) {
            clientConnection.sendPacket(new PacketAddonMessage(key, data));
        }
    }
    
    public void sendAddonMessage(final String key, final String json) {
        final LabyConnect chatClient = LabyMod.getInstance().getLabyConnect();
        final ClientConnection clientConnection = chatClient.getClientConnection();
        if (clientConnection != null && chatClient.isOnline()) {
            clientConnection.sendPacket(new PacketAddonMessage(key, json));
        }
    }
    
    public DrawUtils getDrawUtils() {
        return this.labyMod.getDrawUtils();
    }
    
    public ServerData getCurrentServer() {
        return this.labyMod.getCurrentServerData();
    }
    
    public boolean isCurrentlyPlayingOn(final String address) {
        return this.labyMod.getCurrentServerData() != null && this.labyMod.getCurrentServerData().getIp().toLowerCase().contains(address.toLowerCase());
    }
    
    public boolean isIngame() {
        return this.labyMod.isInGame();
    }
    
    public boolean hasGameFocus() {
        return LabyModCore.getMinecraft().hasInGameFocus();
    }
    
    public boolean isCurrentScreenNull() {
        return LabyModCore.getMinecraft().isCurrentScreenNull();
    }
    
    public boolean isMinecraftChatOpen() {
        return LabyModCore.getMinecraft().isMinecraftChatOpen();
    }
    
    public UserManager getUserManager() {
        return this.labyMod.getUserManager();
    }
    
    public DynamicTextureManager getDynamicTextureManager() {
        return this.labyMod.getDynamicTextureManager();
    }
    
    @Deprecated
    public ModTextureUtils getModTextureUtils() {
        return ModTextureUtils.INSTANCE;
    }
    
    public LabyConnect getLabyModChatClient() {
        return this.labyMod.getLabyConnect();
    }
    
    public UUID getPlayerUUID() {
        return this.labyMod.getPlayerUUID();
    }
    
    public String getPlayerUsername() {
        return this.labyMod.getPlayerName();
    }
    
    public void displayMessageInChat(final String message) {
        this.labyMod.displayMessageInChat(message);
    }
    
    public void connectToServer(final String address) {
        this.labyMod.connectToServer(address);
    }
    
    public void updateCurrentGamemode(final String gamemodeName) {
        this.labyMod.getLabyConnect().updatePlayingOnServerState(gamemodeName);
    }
    
    public void sendAddonDevelopmentPacket(final PacketAddonDevelopment packetAddonDevelopment) {
        this.labyMod.getLabyConnect().getClientConnection().sendPacket(packetAddonDevelopment);
    }
    
    public void registerForgeListener(final Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }
    
    public EventManager getEventManager() {
        return this.labyMod.getEventManager();
    }
    
    public void setCrosshairHidden(final boolean value) {
        this.crosshairHidden = value;
    }
    
    public boolean isCrosshairHidden() {
        return this.crosshairHidden;
    }
    
    static class DelayedPayloadMessage
    {
        private final String messageKey;
        private final JsonElement message;
        
        public DelayedPayloadMessage(final String messageKey, final JsonElement message) {
            this.messageKey = messageKey;
            this.message = message;
        }
    }
}
