//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp;

import net.labymod.api.events.*;
import java.io.*;
import java.util.*;
import net.labymod.discordapp.listeners.*;
import net.labymod.main.*;
import net.labymod.support.util.*;
import net.labymod.utils.*;
import net.labymod.discordapp.api.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.labymod.core.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.google.gson.*;

public class DiscordApp implements PluginMessageEvent, ServerMessageEvent
{
    private static final String APPLICATION_ID = "576456544942686228";
    public static File libraryFile;
    private DiscordEventHandlers handlers;
    private ModRichPresence richPresence;
    private boolean initialized;
    private boolean connected;
    private UUID queuedSpectateKey;
    private UUID queuedJoinKey;
    
    public DiscordApp() {
        this.handlers = new DiscordEventHandlers();
        this.richPresence = new ModRichPresence(this);
        this.initialized = false;
        this.connected = false;
        this.queuedSpectateKey = null;
        this.queuedJoinKey = null;
        this.handlers.ready = (DiscordEventHandlers.ready_callback)new ReadyListener(this);
        this.handlers.disconnected = (DiscordEventHandlers.disconnected_callback)new DisconnectListener(this);
        this.handlers.errored = (DiscordEventHandlers.errored_callback)new ErroredListener(this);
        this.handlers.joinGame = (DiscordEventHandlers.joinGame_callback)new JoinGameListener(this);
        this.handlers.joinRequest = (DiscordEventHandlers.joinRequest_callback)new JoinRequestListener(this);
        this.handlers.spectateGame = (DiscordEventHandlers.spectateGame_callback)new SpectateGameListener(this);
        LabyMod.getInstance().getEventManager().registerShutdownHook((Runnable)new Runnable() {
            @Override
            public void run() {
                DiscordApp.this.shutdown();
            }
        });
    }
    
    public void initialize() {
        if (this.initialized) {
            return;
        }
        if (!LabyMod.getSettings().discordRichPresence) {
            return;
        }
        Debug.log(Debug.EnumDebugMode.DISCORD, "Initialize discord rpc..");
        final DiscordLibraryProvider discordLibraryProvider = new DiscordLibraryProvider();
        if (discordLibraryProvider.isValidOS()) {
            discordLibraryProvider.execute((Consumer)new Consumer<File>() {
                @Override
                public void accept(final File libraryFile) {
                    if (libraryFile != null) {
                        DiscordApp.libraryFile = libraryFile;
                        try {
                            DiscordRPCLibrary.initialize("576456544942686228", DiscordApp.this.handlers, 1, (String)null);
                            DiscordApp.this.initialized = true;
                            DiscordApp.this.onLibraryLoaded();
                        }
                        catch (Throwable error) {
                            DiscordApp.this.initialized = false;
                            error.printStackTrace();
                        }
                    }
                    else {
                        Debug.log(Debug.EnumDebugMode.DISCORD, "No Discord library found!");
                    }
                }
            });
        }
    }
    
    public void shutdown() {
        if (this.initialized) {
            Debug.log(Debug.EnumDebugMode.DISCORD, "Shutdown discord rpc..");
            DiscordRPCLibrary.shutdown();
            this.initialized = false;
            this.connected = false;
        }
    }
    
    private void onLibraryLoaded() {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Discord library's successfully loaded!");
        MinecraftForge.EVENT_BUS.register(this);
        LabyMod.getInstance().getEventManager().register((PluginMessageEvent)this);
        LabyMod.getInstance().getEventManager().register((ServerMessageEvent)this);
        this.richPresence.forceUpdate();
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGuiOpen(final GuiOpenEvent event) {
        final blk gui = LabyModCore.getForge().getGuiOpenEventGui(event);
        this.richPresence.screenUpdated(gui);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (this.initialized) {
            this.richPresence.updateCallbacks();
            if (this.connected) {
                this.richPresence.updateRichPresence();
            }
        }
    }
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (channelName.equals("MC|Brand")) {
            final bse serverData = bib.z().C();
            final boolean singlePlayer = bib.z().E();
            this.richPresence.serverUpdated(serverData, singlePlayer);
            this.redeemQueuedSecretKeys();
        }
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("discord_rpc")) {
            return;
        }
        final JsonObject obj = serverMessage.getAsJsonObject();
        if (obj.has("hasMatchSecret")) {
            final boolean hasMatchSecret = obj.get("hasMatchSecret").getAsBoolean();
            if (hasMatchSecret) {
                final String matchSecret = obj.get("matchSecret").getAsString();
                this.richPresence.serverMatchSecretUpdated(matchSecret);
            }
            else {
                this.richPresence.serverMatchSecretUpdated(null);
            }
        }
        if (obj.has("hasSpectateSecret")) {
            final boolean hasSpectateSecret = obj.get("hasSpectateSecret").getAsBoolean();
            if (hasSpectateSecret) {
                final String spectateSecret = obj.get("spectateSecret").getAsString();
                this.richPresence.serverSpectateSecretUpdated(spectateSecret);
            }
            else {
                this.richPresence.serverSpectateSecretUpdated(null);
            }
        }
        if (obj.has("hasJoinSecret")) {
            final boolean hasJoinSecret = obj.get("hasJoinSecret").getAsBoolean();
            if (hasJoinSecret) {
                final String joinSecret = obj.get("joinSecret").getAsString();
                this.richPresence.serverJoinSecretUpdated(joinSecret);
            }
            else {
                this.richPresence.serverJoinSecretUpdated(null);
            }
        }
        if (obj.has("hasParty")) {
            final boolean hasParty = obj.get("hasParty").getAsBoolean();
            if (hasParty) {
                final String partyId = obj.get("partyId").getAsString();
                final int partySize = obj.get("party_size").getAsInt();
                final int partyMax = obj.get("party_max").getAsInt();
                this.richPresence.serverPartyUpdated(true, partyId, partySize, partyMax);
            }
            else {
                this.richPresence.serverPartyUpdated(false, null, 0, 0);
            }
        }
        if (obj.has("hasGame")) {
            final boolean hasGame = obj.get("hasGame").getAsBoolean();
            if (hasGame) {
                final long startTime = obj.get("game_startTime").getAsLong();
                final long endTime = obj.get("game_endTime").getAsLong();
                final String gamemode = obj.get("game_mode").getAsString();
                this.richPresence.serverGameUpdated(true, gamemode, startTime, endTime);
            }
            else {
                this.richPresence.serverGameUpdated(false, null, 0L, 0L);
            }
        }
    }
    
    public void respond(final String userId, final int reply) {
        DiscordRPCLibrary.respond(userId, reply);
    }
    
    public void redeemSpectateKey(final UUID spectateKey, final String serverDomain) {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Redeem spectate secret key: " + spectateKey.toString() + " on " + serverDomain);
        this.queuedSpectateKey = spectateKey;
        if (!LabyMod.getInstance().switchServer(serverDomain, false)) {
            this.redeemQueuedSecretKeys();
        }
    }
    
    public void redeemJoinKey(final UUID joinKey, final String serverDomain) {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Redeem join secret key: " + joinKey.toString() + " on " + serverDomain);
        this.queuedJoinKey = joinKey;
        if (!LabyMod.getInstance().switchServer(serverDomain, false)) {
            this.redeemQueuedSecretKeys();
        }
    }
    
    private void redeemQueuedSecretKeys() {
        if (this.queuedJoinKey == null && this.queuedSpectateKey == null) {
            return;
        }
        final JsonObject obj = new JsonObject();
        if (this.queuedJoinKey != null) {
            obj.addProperty("joinSecret", this.queuedJoinKey.toString());
        }
        else if (this.queuedSpectateKey != null) {
            obj.addProperty("spectateSecret", this.queuedSpectateKey.toString());
        }
        LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("discord_rpc", (JsonElement)obj);
        this.queuedSpectateKey = null;
        this.queuedJoinKey = null;
    }
    
    public boolean isConnected() {
        return this.connected;
    }
    
    public void setConnected(final boolean connected) {
        this.connected = connected;
    }
    
    static {
        DiscordApp.libraryFile = null;
    }
}
