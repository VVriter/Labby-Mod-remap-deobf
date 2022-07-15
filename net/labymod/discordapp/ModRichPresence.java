//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp;

import java.util.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.discordapp.api.*;

public class ModRichPresence
{
    private static final long UPDATE_COOLDOWN = 2000L;
    private static final long HEART_BEAT_TICK = 5000L;
    private DiscordRichPresence drp;
    private boolean updateRequired;
    private long lastCallbackUpdate;
    private long lastRichPresenceUpdate;
    private String displayingScreen;
    private String serverAddress;
    private boolean singlePlayer;
    private float health;
    private int gamemode;
    private String matchSecret;
    private String joinSecret;
    private String spectateSecret;
    private boolean party;
    private String partyId;
    private int partySize;
    private int partyMax;
    private boolean game;
    private long gameStartTimestamp;
    private long gameEndTimestamp;
    private String serverGamemode;
    
    public ModRichPresence(final DiscordApp discordApp) {
        this.drp = new DiscordRichPresence();
        this.updateRequired = true;
        this.lastCallbackUpdate = 0L;
        this.lastRichPresenceUpdate = 0L;
        this.displayingScreen = "Menu";
        this.serverAddress = null;
        this.singlePlayer = false;
        this.health = 20.0f;
        this.gamemode = 0;
        this.matchSecret = null;
        this.joinSecret = null;
        this.spectateSecret = null;
        this.party = false;
        this.partyId = null;
        this.partySize = 0;
        this.partyMax = 0;
        this.game = false;
        this.gameStartTimestamp = 0L;
        this.gameEndTimestamp = 0L;
        this.serverGamemode = null;
    }
    
    public void screenUpdated(final blk currentScreen) {
        final String prevDisplayingScreen = this.displayingScreen;
        if (LabyMod.getInstance().isInGame()) {
            if (currentScreen == null || currentScreen instanceof bkn) {
                this.displayingScreen = "Ingame";
            }
            else {
                this.displayingScreen = "Ingame Menu";
            }
        }
        else {
            this.displayingScreen = "Menu";
            if (this.serverAddress != null) {
                this.serverUpdated(null, false);
            }
        }
        if (!prevDisplayingScreen.equals(this.displayingScreen)) {
            this.updateRequired = true;
        }
    }
    
    public void serverUpdated(final bse serverData, final boolean singlePlayer) {
        final String prevServerAddress = this.serverAddress;
        if (singlePlayer) {
            this.serverAddress = "Singleplayer";
        }
        else if (serverData == null) {
            this.serverAddress = null;
        }
        else {
            this.serverAddress = serverData.b;
        }
        if (!Objects.equals(prevServerAddress, this.serverAddress)) {
            this.updateRequired = true;
            this.singlePlayer = singlePlayer;
            this.gameStartTimestamp = System.currentTimeMillis();
        }
    }
    
    public void serverMatchSecretUpdated(final String matchSecret) {
        if (!Objects.equals(this.matchSecret, matchSecret)) {
            this.updateRequired = true;
        }
        this.matchSecret = matchSecret;
    }
    
    public void serverSpectateSecretUpdated(final String spectateSecret) {
        if (!Objects.equals(this.spectateSecret, spectateSecret)) {
            this.updateRequired = true;
        }
        this.spectateSecret = spectateSecret;
    }
    
    public void serverJoinSecretUpdated(final String joinSecret) {
        if (!Objects.equals(this.matchSecret, joinSecret)) {
            this.updateRequired = true;
        }
        this.joinSecret = joinSecret;
    }
    
    public void serverSecretUpdated(final String matchSecret, final String joinSecret, final String spectateSecret) {
        if (!Objects.equals(this.matchSecret, matchSecret) || !Objects.equals(this.joinSecret, joinSecret) || !Objects.equals(this.spectateSecret, spectateSecret)) {
            this.updateRequired = true;
        }
        this.matchSecret = matchSecret;
        this.joinSecret = joinSecret;
        this.spectateSecret = spectateSecret;
    }
    
    public void serverPartyUpdated(final boolean party, final String partyId, final int size, final int max) {
        if (this.party != party || !Objects.equals(this.partyId, partyId) || size != this.partySize || this.partyMax != max) {
            this.updateRequired = true;
        }
        this.party = party;
        this.partyId = partyId;
        this.partySize = size;
        this.partyMax = max;
    }
    
    public void serverGameUpdated(final boolean game, final String serverGamemode, final long started, final long end) {
        if (this.game != game || !Objects.equals(this.serverGamemode, serverGamemode) || started != this.gameStartTimestamp || this.gameEndTimestamp != end) {
            this.updateRequired = true;
        }
        this.game = game;
        this.serverGamemode = serverGamemode;
        this.gameStartTimestamp = started;
        this.gameEndTimestamp = end;
    }
    
    public DiscordRichPresence build() {
        String state = this.displayingScreen;
        String details = null;
        if (this.singlePlayer) {
            state = this.serverAddress;
            String displayHearts = "";
            if (this.gamemode == 0 || this.gamemode == 2) {
                if (this.health != 0.0f) {
                    if (this.health % 2.0f == 0.0f) {
                        displayHearts = String.valueOf((int)(this.health / 2.0f));
                    }
                    else {
                        displayHearts = String.valueOf(this.health / 2.0f);
                    }
                    displayHearts = displayHearts + " heart" + ((this.health == 2.0f) ? "" : "s");
                }
                else {
                    displayHearts = "Dead";
                }
            }
            switch (this.gamemode) {
                case 0: {
                    details = "Survival - " + displayHearts;
                    break;
                }
                case 1: {
                    details = "Creative mode";
                    break;
                }
                case 2: {
                    details = "Adventure mode" + displayHearts;
                    break;
                }
                case 3: {
                    details = "Spectator";
                    break;
                }
            }
            this.drp.smallImageKey = null;
            this.drp.endTimestamp = 0L;
            this.drp.startTimestamp = this.gameStartTimestamp;
            this.joinSecret = null;
            this.spectateSecret = null;
            this.game = false;
            this.drp.partySize = 0;
            this.drp.partyMax = 0;
        }
        else if (this.serverAddress != null) {
            if (LabyMod.getSettings().discordShowIpAddress) {
                if (this.serverAddress.contains(".")) {
                    try {
                        final String[] domainParts = this.serverAddress.split("\\.");
                        String address = "";
                        for (int max = Math.min(4, domainParts.length), i = 0; i < max; ++i) {
                            if (!address.isEmpty()) {
                                address += ".";
                            }
                            address += domainParts[domainParts.length - max + i];
                        }
                        details = address;
                    }
                    catch (Exception error) {
                        error.printStackTrace();
                    }
                }
                else {
                    details = this.serverAddress;
                }
            }
            if (this.serverAddress.contains(".")) {
                final String[] domainParts = this.serverAddress.split("\\.");
                if (domainParts.length >= 2) {
                    final String preDomainEnding = domainParts[domainParts.length - 2];
                    this.drp.smallImageKey = preDomainEnding.toLowerCase(Locale.ENGLISH);
                }
            }
            if (this.party) {
                this.drp.partySize = this.partySize;
                this.drp.partyMax = this.partyMax;
            }
            if (this.game && this.serverGamemode != null) {
                state = this.serverGamemode;
                this.drp.startTimestamp = this.gameStartTimestamp;
                this.drp.endTimestamp = this.gameEndTimestamp;
            }
            else {
                this.drp.endTimestamp = 0L;
                this.drp.startTimestamp = this.gameStartTimestamp;
            }
        }
        else {
            this.drp.smallImageKey = null;
            this.drp.endTimestamp = 0L;
            this.drp.startTimestamp = 0L;
            this.joinSecret = null;
            this.spectateSecret = null;
            this.game = false;
            this.party = false;
            this.drp.partySize = 0;
            this.drp.partyMax = 0;
        }
        this.drp.partyId = ((this.partyId == null) ? LabyMod.getInstance().getPlayerUUID().toString() : this.partyId);
        this.drp.matchSecret = this.matchSecret;
        this.drp.joinSecret = this.joinSecret;
        this.drp.spectateSecret = this.spectateSecret;
        this.drp.details = details;
        this.drp.state = state;
        this.drp.largeImageText = "MC " + Source.ABOUT_MC_VERSION + " - LabyMod " + "3.9.41" + " " + "";
        this.drp.smallImageText = ((this.drp.smallImageKey == null) ? "" : this.drp.smallImageKey);
        this.drp.largeImageKey = "labymod";
        this.drp.instance = 0;
        return this.drp;
    }
    
    private boolean tickUpdateRequired() {
        if (!LabyMod.getInstance().isInGame()) {
            return false;
        }
        final float health = LabyModCore.getMinecraft().getPlayer().cd();
        final int gamemode = LabyModCore.getMinecraft().getGameMode(LabyMod.getInstance().getPlayerUUID());
        if (health != this.health || gamemode != this.gamemode) {
            this.health = health;
            this.gamemode = gamemode;
            return true;
        }
        return false;
    }
    
    public void updateRichPresence() {
        if (!this.tickUpdateRequired() && !this.updateRequired && this.lastRichPresenceUpdate + 5000L > System.currentTimeMillis()) {
            return;
        }
        this.lastRichPresenceUpdate = System.currentTimeMillis();
        DiscordRPCLibrary.updatePresence(this.build());
        this.updateRequired = false;
    }
    
    public void forceUpdate() {
        this.updateRequired = true;
    }
    
    public void updateCallbacks() {
        if (this.lastCallbackUpdate + 2000L < System.currentTimeMillis()) {
            this.lastCallbackUpdate = System.currentTimeMillis();
            DiscordRPCLibrary.runCallbacks();
        }
    }
    
    public boolean isUpdateRequired() {
        return this.updateRequired;
    }
    
    public long getLastCallbackUpdate() {
        return this.lastCallbackUpdate;
    }
}
