//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import net.labymod.api.events.*;
import net.labymod.labyconnect.log.*;
import net.labymod.labyconnect.user.*;
import net.labymod.main.*;
import java.util.concurrent.*;
import com.google.gson.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.support.util.*;
import net.labymod.utils.*;
import java.util.*;

public class LabyConnect implements ServerMessageEvent
{
    private static final long RECONNECT_INTERVAL = 60L;
    private ClientConnection clientConnection;
    private ChatlogManager chatlogManager;
    private List<ChatUser> friends;
    private List<ChatRequest> requests;
    private List<ChatUser> sortFriends;
    private ClientProfile clientProfile;
    private PacketPlayServerStatusUpdate lastPacketPlayServerStatus;
    private EnumAlertDisplayType alertDisplayType;
    private final LabyConnectUserTracker tracker;
    private boolean forcedLogout;
    private boolean viaServerList;
    
    public LabyConnect() {
        this.chatlogManager = new ChatlogManager();
        this.friends = new ArrayList<ChatUser>();
        this.requests = new ArrayList<ChatRequest>();
        this.sortFriends = new ArrayList<ChatUser>();
        this.tracker = new LabyConnectUserTracker(this);
        this.forcedLogout = false;
        this.viaServerList = false;
        try {
            this.clientConnection = new ClientConnection(this);
            this.clientProfile = new ClientProfile(this, this.clientConnection);
        }
        catch (Throwable error) {
            error.printStackTrace();
        }
        this.updateAlertDisplayType();
        LabyMod.getInstance().getEventManager().registerShutdownHook((Runnable)new Runnable() {
            @Override
            public void run() {
                if (LabyConnect.this.clientConnection != null) {
                    LabyConnect.this.clientConnection.disconnect(false);
                }
            }
        });
        LabyMod.getInstance().getEventManager().register((ServerMessageEvent)this);
        final String disableAutoReconnectString = System.getProperty("disableAutoReconnect");
        final boolean disableAutoReconnect = disableAutoReconnectString != null && disableAutoReconnectString.equalsIgnoreCase("true");
        if (disableAutoReconnect) {
            this.clientConnection.connect();
        }
        else {
            Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
                try {
                    if (LabyMod.getInstance().isPremium() && this.clientConnection.getCurrentConnectionState() == EnumConnectionState.OFFLINE && !this.forcedLogout) {
                        this.clientConnection.connect();
                    }
                }
                catch (Throwable error2) {
                    error2.printStackTrace();
                }
            }, 0L, 60L, TimeUnit.SECONDS);
        }
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("server_gamemode")) {
            try {
                final JsonObject jsonObject = serverMessage.getAsJsonObject();
                if (jsonObject.has("show_gamemode")) {
                    final boolean showGamemode = jsonObject.get("show_gamemode").getAsBoolean();
                    if (showGamemode) {
                        if (jsonObject.has("gamemode_name")) {
                            this.updatePlayingOnServerState(jsonObject.get("gamemode_name").getAsString());
                        }
                    }
                    else {
                        this.updatePlayingOnServerState(null);
                    }
                }
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    
    public void updateAlertDisplayType() {
        try {
            this.alertDisplayType = EnumAlertDisplayType.valueOf(LabyMod.getSettings().alertDisplayType);
        }
        catch (Exception error) {
            this.alertDisplayType = EnumAlertDisplayType.ACHIEVEMENT;
            error.printStackTrace();
        }
    }
    
    public boolean isOnline() {
        return this.clientConnection != null && this.clientConnection.getCurrentConnectionState() == EnumConnectionState.PLAY;
    }
    
    public ChatUser getChatUser(final ChatUser chatUser) {
        return this.getChatUserByUUID(chatUser.getGameProfile().getId());
    }
    
    public ChatUser getChatUserByUUID(final UUID uuid) {
        for (final ChatUser chatUser : this.friends) {
            if (chatUser.getGameProfile().getId().equals(uuid)) {
                return chatUser;
            }
        }
        return null;
    }
    
    public void updatePlayingOnServerState(final String gamemode) {
        final ServerData serverData = LabyMod.getInstance().getCurrentServerData();
        final boolean viaServerlist = this.viaServerList && serverData != null;
        PacketPlayServerStatusUpdate packet;
        if (serverData == null || !LabyMod.getInstance().isInGame() || bib.z().E()) {
            packet = new PacketPlayServerStatusUpdate();
        }
        else {
            packet = new PacketPlayServerStatusUpdate(serverData.getIp(), serverData.getPort(), (gamemode == null) ? "" : gamemode, viaServerlist);
        }
        if (this.lastPacketPlayServerStatus != null && this.lastPacketPlayServerStatus.equals(packet)) {
            return;
        }
        this.lastPacketPlayServerStatus = packet;
        this.getClientConnection().sendPacket((Packet)packet);
        if (packet.getServerIp() != null && !packet.getServerIp().isEmpty()) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Now playing on " + packet.getServerIp() + ":" + packet.getPort() + " " + packet.getGamemode());
        }
    }
    
    public void sortFriendList(final int sortMode) {
        final List<ChatUser> sortedList = new ArrayList<ChatUser>();
        for (final ChatUser chatUser : this.friends) {
            switch (sortMode) {
                case 1: {
                    if (!chatUser.isOnline()) {
                        continue;
                    }
                    break;
                }
                case 2: {
                    if (this.getChatlogManager().getChat(chatUser).getMessages().isEmpty()) {
                        continue;
                    }
                    break;
                }
            }
            sortedList.add(chatUser);
        }
        Collections.sort(sortedList, new Comparator<ChatUser>() {
            @Override
            public int compare(final ChatUser a, final ChatUser b) {
                if (a.isParty()) {
                    return Integer.MIN_VALUE;
                }
                switch (sortMode) {
                    case 0: {
                        final long la = a.isOnline() ? (a.getLastOnline() / 1000L + 1L) : (a.getLastOnline() / 2000L);
                        final long lb = b.isOnline() ? (b.getLastOnline() / 1000L + 1L) : (b.getLastOnline() / 2000L);
                        return (int)(lb - la);
                    }
                    case 1: {
                        return 0;
                    }
                    case 2: {
                        final long la = a.isOnline() ? (a.getLastInteraction() / 1000L) : (a.getLastInteraction() / 2000L);
                        final long lb = b.isOnline() ? (b.getLastInteraction() / 1000L) : (b.getLastInteraction() / 2000L);
                        return (int)(lb - la);
                    }
                    default: {
                        return 0;
                    }
                }
            }
        });
        this.sortFriends = sortedList;
    }
    
    public boolean hasFriendOnline(final UUID uuid) {
        for (final ChatUser friend : this.friends) {
            if (friend.isOnline() && friend.getGameProfile().getId().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
    
    public LabyConnectUserTracker getTracker() {
        return this.tracker;
    }
    
    public ClientConnection getClientConnection() {
        return this.clientConnection;
    }
    
    public ChatlogManager getChatlogManager() {
        return this.chatlogManager;
    }
    
    public List<ChatUser> getFriends() {
        return this.friends;
    }
    
    public List<ChatRequest> getRequests() {
        return this.requests;
    }
    
    public List<ChatUser> getSortFriends() {
        return this.sortFriends;
    }
    
    public ClientProfile getClientProfile() {
        return this.clientProfile;
    }
    
    public PacketPlayServerStatusUpdate getLastPacketPlayServerStatus() {
        return this.lastPacketPlayServerStatus;
    }
    
    public EnumAlertDisplayType getAlertDisplayType() {
        return this.alertDisplayType;
    }
    
    public boolean isForcedLogout() {
        return this.forcedLogout;
    }
    
    public boolean isViaServerList() {
        return this.viaServerList;
    }
    
    public void setForcedLogout(final boolean forcedLogout) {
        this.forcedLogout = forcedLogout;
    }
    
    public void setViaServerList(final boolean viaServerList) {
        this.viaServerList = viaServerList;
    }
}
