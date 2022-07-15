//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;
import io.netty.bootstrap.*;
import com.google.common.util.concurrent.*;
import net.labymod.support.util.*;
import net.labymod.support.listener.*;
import net.labymod.api.events.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import io.netty.handler.timeout.*;
import net.labymod.main.lang.*;
import io.netty.util.concurrent.*;
import javax.crypto.*;
import net.labymod.labyconnect.handling.*;
import net.labymod.main.*;
import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.log.*;
import net.labymod.labyconnect.gui.*;
import java.math.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.exceptions.*;
import java.security.*;
import com.mojang.authlib.minecraft.*;
import net.labymod.user.*;
import net.labymod.user.group.*;
import java.nio.*;
import net.labymod.labyconnect.packets.*;
import java.util.*;
import net.labymod.gui.*;
import io.netty.channel.*;
import java.io.*;
import com.google.gson.*;
import net.labymod.api.*;
import net.labymod.utils.manager.*;
import net.labymod.core.asm.*;
import net.labymod.addon.*;
import oshi.hardware.*;
import oshi.*;
import com.sun.management.*;
import java.lang.management.*;
import net.labymod.utils.*;
import org.lwjgl.opengl.*;
import java.awt.*;

@ChannelHandler.Sharable
public class ClientConnection extends PacketHandler
{
    private NioEventLoopGroup nioEventLoopGroup;
    private ExecutorService executorService;
    private NioSocketChannel nioSocketChannel;
    private Bootstrap bootstrap;
    private EnumConnectionState currentConnectionState;
    private LabyConnect labyConnect;
    private String lastKickMessage;
    private String mojang;
    public String customIp;
    public int customPort;
    private long lastPing;
    private Consumer<String> dashboardPinConsumer;
    
    public ClientConnection(final LabyConnect labyConnect) {
        this.nioEventLoopGroup = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Chat#%d").build());
        this.executorService = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setNameFormat("Helper#%d").build());
        this.currentConnectionState = EnumConnectionState.OFFLINE;
        this.lastKickMessage = "Unknown";
        this.customIp = null;
        this.customPort = -1;
        this.lastPing = System.currentTimeMillis();
        this.labyConnect = labyConnect;
        if (Debug.isActive()) {
            LabyMod.getInstance().getEventManager().register((MessageSendEvent)new LabyConnectCommands(this));
        }
        final long duration;
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            duration = System.currentTimeMillis() - this.lastPing;
            if (this.currentConnectionState != EnumConnectionState.OFFLINE && duration > 60000L) {
                this.disconnect(false);
            }
        }, 0L, 60L, TimeUnit.SECONDS);
    }
    
    public void connect() {
        String defaultIp = "chat.labymod.net";
        int defaultPort = 30336;
        final String customPort = System.getProperty("customChatPort");
        if (customPort != null) {
            defaultPort = Integer.parseInt(customPort);
        }
        if (this.customIp != null) {
            defaultIp = this.customIp;
        }
        if (this.customPort != -1) {
            defaultPort = this.customPort;
        }
        this.connect(defaultIp, defaultPort);
    }
    
    public void connect(final String ip, final int port) {
        if (this.nioSocketChannel != null && this.nioSocketChannel.isOpen()) {
            this.nioSocketChannel.close();
            this.nioSocketChannel = null;
        }
        this.labyConnect.setForcedLogout(false);
        this.labyConnect.getChatlogManager().loadChatlogs(LabyMod.getInstance().getPlayerUUID());
        this.lastPing = System.currentTimeMillis();
        this.updateConnectionState(EnumConnectionState.HELLO);
        this.mojang = bib.z().K().d();
        (this.bootstrap = new Bootstrap()).group((EventLoopGroup)this.nioEventLoopGroup);
        this.bootstrap.option(ChannelOption.TCP_NODELAY, (Object)true);
        this.bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)10000);
        this.bootstrap.channel((Class)NioSocketChannel.class);
        this.bootstrap.handler((ChannelHandler)new ClientChannelInitializer(this.labyConnect, this));
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (LabyMod.getInstance().isPremium()) {
                        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Connecting to " + ip + ":" + port);
                        ClientConnection.this.bootstrap.connect(ip, port).syncUninterruptibly();
                        ClientConnection.this.sendPacket(new PacketHelloPing(System.currentTimeMillis()));
                    }
                    else {
                        ClientConnection.this.updateConnectionState(EnumConnectionState.OFFLINE);
                        ClientConnection.this.lastKickMessage = "Not a premium account.";
                    }
                }
                catch (UnresolvedAddressException error) {
                    ClientConnection.this.updateConnectionState(EnumConnectionState.OFFLINE);
                    ClientConnection.this.lastKickMessage = ((error.getMessage() == null) ? "Unknown error" : error.getMessage());
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "UnresolvedAddressException: " + error.getMessage());
                    error.printStackTrace();
                }
                catch (Throwable throwable) {
                    if (throwable instanceof ConnectTimeoutException || throwable instanceof TimeoutException) {
                        if (ip.equals("chat.labymod.net")) {
                            ClientConnection.this.connect("chat2.labymod.net", port);
                        }
                        else {
                            ClientConnection.this.updateConnectionState(EnumConnectionState.OFFLINE);
                            ClientConnection.this.lastKickMessage = "Could not reach our servers.";
                            throwable.printStackTrace();
                            IssueCollector.handle(ip, port);
                        }
                        return;
                    }
                    ClientConnection.this.updateConnectionState(EnumConnectionState.OFFLINE);
                    ClientConnection.this.lastKickMessage = ((throwable.getMessage() == null) ? "Unknown error" : throwable.getMessage());
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Throwable: " + throwable.getMessage());
                    throwable.printStackTrace();
                    if (ClientConnection.this.lastKickMessage.contains("no further information") || throwable.getMessage() == null) {
                        ClientConnection.this.lastKickMessage = LanguageManager.translate("chat_not_reachable");
                    }
                }
            }
        });
    }
    
    public void disconnect(final boolean kicked) {
        if (this.currentConnectionState == EnumConnectionState.OFFLINE) {
            return;
        }
        this.updateConnectionState(EnumConnectionState.OFFLINE);
        LabyMod.getInstance().getUserManager().resetFamiliars();
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                ClientConnection.this.labyConnect.getChatlogManager().saveChatlogs(LabyMod.getInstance().getPlayerUUID());
                if (ClientConnection.this.nioSocketChannel != null && !kicked) {
                    ClientConnection.this.nioSocketChannel.writeAndFlush((Object)new PacketDisconnect("Logout")).addListener((GenericFutureListener)new ChannelFutureListener() {
                        public void operationComplete(final ChannelFuture arg0) throws Exception {
                            if (ClientConnection.this.nioSocketChannel != null) {
                                ClientConnection.this.nioSocketChannel.close();
                            }
                        }
                    });
                }
            }
        });
    }
    
    public void updateConnectionState(final EnumConnectionState connectionState) {
        this.currentConnectionState = connectionState;
    }
    
    public void enableEncryption(final SecretKey key) {
        this.nioSocketChannel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new PacketEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
        this.nioSocketChannel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new PacketEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Enabled LabyConnect encryption!");
    }
    
    @Override
    public void handle(final PacketLoginData packet) {
    }
    
    @Override
    public void handle(final PacketHelloPing packet) {
    }
    
    @Override
    public void handle(final PacketHelloPong packet) {
        this.updateConnectionState(EnumConnectionState.LOGIN);
        this.sendPacket(new PacketLoginData(LabyMod.getInstance().getPlayerUUID(), LabyMod.getInstance().getPlayerName(), LabyMod.getSettings().motd));
        this.sendPacket(new PacketLoginOptions(LabyMod.getSettings().showConnectedIp, this.labyConnect.getClientProfile().getUserStatus(), this.labyConnect.getClientProfile().getTimeZone()));
        this.sendPacket(new PacketLoginVersion(26, Source.ABOUT_MC_VERSION + "_" + "3.9.41"));
        this.labyConnect.getFriends().clear();
        this.labyConnect.getRequests().clear();
        this.lastPing = System.currentTimeMillis();
    }
    
    @Override
    public void handle(final PacketPlayPlayerOnline packet) {
        final ChatUser chatUser = this.labyConnect.getChatUserByUUID(packet.getPlayer().getGameProfile().getId());
        chatUser.setStatus(packet.getPlayer().getStatus());
        chatUser.setStatusMessage(packet.getPlayer().getStatusMessage());
        if (LabyMod.getSettings().alertsOnlineStatus) {
            if (packet.getPlayer().isOnline()) {
                LabyMod.getInstance().notifyMessageProfile(packet.getPlayer().getGameProfile(), ModColor.cl("a") + LanguageManager.translate("chat_user_now_online"));
            }
            else {
                LabyMod.getInstance().notifyMessageProfile(packet.getPlayer().getGameProfile(), ModColor.cl("c") + LanguageManager.translate("chat_user_now_offline"));
            }
        }
        this.labyConnect.sortFriendList(LabyMod.getSettings().friendSortType);
    }
    
    @Override
    public void handle(final PacketLoginComplete packet) {
        this.updateConnectionState(EnumConnectionState.PLAY);
        if (packet.getDashboardPin() != null && !packet.getDashboardPin().isEmpty()) {
            try {
                final JsonObject jsonObject = (JsonObject)new JsonParser().parse(packet.getDashboardPin());
                if (jsonObject.has("pin")) {
                    final String pin = jsonObject.get("pin").getAsString();
                    final long expiresAt = jsonObject.get("expires_at").getAsLong();
                    LabyMod.getInstance().getPinManager().update(LabyMod.getInstance().getPlayerUUID(), pin, expiresAt);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        LabyMod.getInstance().getUserManager().resetFamiliars();
        this.sendStatistics();
        this.labyConnect.getTracker().onLabyConnectConnect();
    }
    
    @Override
    public void handle(final PacketChatVisibilityChange packet) {
    }
    
    @Override
    public void handle(final PacketKick packet) {
        this.disconnect(true);
        this.lastKickMessage = ((packet.getReason() == null) ? LanguageManager.translate("chat_unknown_kick_reason") : packet.getReason());
        LabyMod.getInstance().notifyMessageRaw(LanguageManager.translate("chat_disconnected_title"), this.lastKickMessage);
    }
    
    @Override
    public void handle(final PacketDisconnect packet) {
        this.disconnect(true);
        this.lastKickMessage = ((packet.getReason() == null) ? LanguageManager.translate("chat_unknown_disconnect_reason") : packet.getReason());
        LabyMod.getInstance().notifyMessageRaw(LanguageManager.translate("chat_disconnected_title"), this.lastKickMessage);
    }
    
    @Override
    public void handle(final PacketPlayRequestAddFriend packet) {
    }
    
    @Override
    public void handle(final PacketLoginFriend packet) {
        this.labyConnect.getFriends().addAll(packet.getFriends());
        this.labyConnect.sortFriendList(LabyMod.getSettings().friendSortType);
    }
    
    @Override
    public void handle(final PacketLoginRequest packet) {
        if (LabyMod.getSettings().ignoreRequests) {
            for (final ChatRequest chatRequest : packet.getRequests()) {
                this.sendPacket(new PacketPlayDenyFriendRequest(chatRequest));
            }
        }
        else {
            this.labyConnect.getRequests().addAll(packet.getRequests());
            for (final ChatRequest chatRequest : this.labyConnect.getRequests()) {
                LabyMod.getInstance().notifyMessageProfile(chatRequest.getGameProfile(), ModColor.cl("f") + LanguageManager.translate("chat_user_friend_request"));
            }
        }
        if (bib.z().m instanceof GuiFriendsLayout) {
            ((GuiFriendsLayout)bib.z().m).getChatElementMyProfile().updateButtons();
        }
    }
    
    @Override
    public void handle(final PacketNotAllowed packet) {
    }
    
    @Override
    public void handle(final PacketPing packet) {
        this.sendPacket(new PacketPong());
        this.lastPing = System.currentTimeMillis();
    }
    
    @Override
    public void handle(final PacketPong packet) {
    }
    
    @Override
    public void handle(final PacketServerMessage packet) {
        LabyMod.getInstance().notifyMessageRaw(LanguageManager.translate("chat_server_message_title"), packet.getMessage());
    }
    
    @Override
    public void handle(final PacketMessage packet) {
        final ChatUser chatUser = this.labyConnect.getChatUserByUUID(packet.getSender().getGameProfile().getId());
        if (chatUser != null) {
            chatUser.setLastTyping(0L);
            final SingleChat singleChat = this.labyConnect.getChatlogManager().getChat(chatUser);
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    singleChat.addMessage(new MessageChatComponent(packet.getSender().getGameProfile().getName(), System.currentTimeMillis(), packet.getMessage()));
                }
            });
            LabyMod.getInstance().notifyMessageProfile(packet.getSender().getGameProfile(), packet.getMessage());
        }
    }
    
    @Override
    public void handle(final PacketPlayTyping packet) {
        final ChatUser chatUser = this.labyConnect.getChatUserByUUID(packet.getPlayer().getGameProfile().getId());
        if (chatUser != null) {
            chatUser.setLastTyping(System.currentTimeMillis());
        }
    }
    
    @Override
    public void handle(final PacketPlayRequestAddFriendResponse packet) {
        GuiFriendsAddFriend.response = (packet.isRequestSent() ? "true" : packet.getReason());
    }
    
    @Override
    public void handle(final PacketPlayRequestRemove packet) {
        final Iterator<ChatRequest> iterator = this.labyConnect.getRequests().iterator();
        while (iterator.hasNext()) {
            final ChatRequest next = iterator.next();
            if (next.getGameProfile().getName().equalsIgnoreCase(packet.getPlayerName())) {
                iterator.remove();
            }
        }
    }
    
    @Override
    public void handle(final PacketPlayDenyFriendRequest packet) {
    }
    
    @Override
    public void handle(final PacketPlayFriendRemove packet) {
        final Iterator<ChatUser> iterator = this.labyConnect.getFriends().iterator();
        while (iterator.hasNext()) {
            final ChatUser next = iterator.next();
            if (next.equals(packet.getToRemove())) {
                iterator.remove();
                if (GuiFriendsLayout.selectedUser == null || !GuiFriendsLayout.selectedUser.equals(next)) {
                    continue;
                }
                GuiFriendsLayout.selectedUser = null;
            }
        }
        this.labyConnect.sortFriendList(LabyMod.getSettings().friendSortType);
        GameIconHelper.updateIcon(true, false);
    }
    
    @Override
    public void handle(final PacketLoginOptions packet) {
    }
    
    @Override
    public void handle(final PacketPlayServerStatus packet) {
    }
    
    @Override
    public void handle(final PacketPlayServerStatusUpdate packet) {
    }
    
    @Override
    public void handle(final PacketPlayFriendStatus packet) {
        final ChatUser chatUser = this.labyConnect.getChatUser(packet.getPlayer());
        chatUser.setCurrentServerInfo(packet.getPlayerInfo());
    }
    
    @Override
    public void handle(final PacketPlayFriendPlayingOn packet) {
        if (!LabyMod.getSettings().alertsPlayingOn) {
            return;
        }
        if (packet.getGameModeName() == null || packet.getGameModeName().isEmpty()) {
            return;
        }
        String message = null;
        if (packet.getGameModeName().contains(".")) {
            message = LanguageManager.translate("chat_user_now_playing_on", packet.getGameModeName());
        }
        else {
            message = LanguageManager.translate("chat_user_now_playing", packet.getGameModeName());
        }
        LabyMod.getInstance().notifyMessageProfile(packet.getPlayer().getGameProfile(), message);
    }
    
    @Override
    public void handle(final PacketPlayChangeOptions packet) {
    }
    
    @Override
    public void handle(final PacketLoginTime packet) {
        this.labyConnect.getClientProfile().setFirstJoined(packet.getDateJoined());
    }
    
    @Override
    public void handle(final PacketLoginVersion packet) {
    }
    
    @Override
    public void handle(final PacketEncryptionRequest encryptionRequest) {
        final UUID uuid = bib.z().K().e().getId();
        final PublicKey publicKey = CryptManager.decodePublicKey(encryptionRequest.getPublicKey());
        final SecretKey secretKey = CryptManager.createNewSharedKey();
        if (uuid == null) {
            this.lastKickMessage = LanguageManager.translate("chat_invalid_session");
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, this.lastKickMessage);
            this.disconnect(false);
            return;
        }
        final String pin = LabyMod.getInstance().getPinManager().getValidPinOf(uuid);
        if (pin != null) {
            this.sendPacket(new PacketEncryptionResponse(secretKey, publicKey, encryptionRequest.getVerifyToken(), pin), channel -> this.enableEncryption(secretKey));
            return;
        }
        try {
            final String serverId = encryptionRequest.getServerId();
            final String hash = new BigInteger(CryptManager.getServerIdHash(serverId, publicKey, secretKey)).toString(16);
            final MinecraftSessionService minecraftSessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createMinecraftSessionService();
            minecraftSessionService.joinServer(bib.z().K().e(), this.mojang, hash);
            this.sendPacket(new PacketEncryptionResponse(secretKey, publicKey, encryptionRequest.getVerifyToken()), new Consumer<NioSocketChannel>() {
                @Override
                public void accept(final NioSocketChannel channel) {
                    ClientConnection.this.enableEncryption(secretKey);
                }
            });
            return;
        }
        catch (AuthenticationUnavailableException e1) {
            this.lastKickMessage = LanguageManager.translate("chat_authentication_unavaileable");
        }
        catch (InvalidCredentialsException e2) {
            this.lastKickMessage = LanguageManager.translate("chat_invalid_session");
        }
        catch (AuthenticationException e3) {
            this.lastKickMessage = LanguageManager.translate("chat_login_failed");
        }
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, this.lastKickMessage);
        this.disconnect(false);
    }
    
    @Override
    public void handle(final PacketEncryptionResponse packet) {
    }
    
    @Override
    public void handle(final PacketMojangStatus packet) {
    }
    
    @Override
    public void handle(final PacketUpdateCosmetics packet) {
        final UUID uuid = LabyMod.getInstance().getPlayerUUID();
        if (uuid == null) {
            return;
        }
        final String json = packet.getJson();
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        if (json == null) {
            userManager.removeCheckedUser(uuid);
            userManager.getUser(uuid).unloadCosmeticTextures();
            return;
        }
        userManager.updateUsersJson(uuid, json, new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void handle(final PacketUserBadge packetUserStatus) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final UUID[] uuids = packetUserStatus.getUuids();
        final byte[] ranks = packetUserStatus.getRanks();
        final boolean validRanks = uuids.length == ranks.length;
        for (int i = 0; i < packetUserStatus.getUuids().length; ++i) {
            final UUID uuid = uuids[i];
            final User user = userManager.getUser(uuid);
            user.setFamiliar(true);
            if (validRanks) {
                final int rank = ranks[i];
                if (rank > 0) {
                    try {
                        final LabyGroup group = userManager.getGroupManager().getGroupById(ranks[i]);
                        user.setGroup(group);
                    }
                    catch (Exception error) {
                        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Error on updating user rank of " + uuid.toString() + ": " + ranks[i]);
                    }
                }
            }
        }
    }
    
    @Override
    public void handle(final PacketActionBroadcast packet) {
        switch (packet.getType()) {
            case EMOTE: {
                final short emoteId = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN).getShort();
                LabyMod.getInstance().getEmoteRegistry().handleEmote(packet.getUniqueId(), emoteId);
                break;
            }
            case COSMETIC_CHANGE: {
                final String json = new String(packet.getData());
                LabyMod.getInstance().getUserManager().updateUsersJson(packet.getUniqueId(), json, null);
                break;
            }
            case STICKER: {
                final short stickerId = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN).getShort();
                final UserManager userManager = LabyMod.getInstance().getUserManager();
                LabyMod.getInstance().getStickerRegistry().handleSticker(userManager.getUser(packet.getUniqueId()), stickerId);
                break;
            }
        }
    }
    
    @Override
    public void handle(final PacketAddonMessage packet) {
        LabyMod.getInstance().getEventManager().callAddonMessage(packet);
        final String key = packet.getKey();
        if (key.equals("UPDATE")) {
            LabyMod.getInstance().getUpdater().setForceUpdate(true);
        }
        if (key.equals("refresh_labymod")) {
            LabyMod.getInstance().getUserManager().refresh();
        }
        if (key.equals("UPDATE-BACKUP")) {
            LabyMod.getInstance().getUpdater().setBackupMethod(true);
            LabyMod.getInstance().getUpdater().setForceUpdate(true);
        }
        if (key.equals("invalidate_pin")) {
            LabyMod.getInstance().getPinManager().invalidatePinOf(LabyMod.getInstance().getPlayerUUID());
        }
        if (key.equals("dashboard_pin")) {
            final JsonObject jsonObject = (JsonObject)new JsonParser().parse(packet.getJson());
            if (jsonObject.has("pin")) {
                final String pin = jsonObject.get("pin").getAsString();
                if (this.dashboardPinConsumer != null) {
                    this.dashboardPinConsumer.accept(pin);
                }
            }
        }
        if (key.equals("server_message")) {
            final JsonObject jsonObject = (JsonObject)new JsonParser().parse(packet.getJson());
            LabyMod.getInstance().displayMessageInChat(ModColor.createColors(jsonObject.get("message").getAsString()));
        }
        if (key.equals("language_flags")) {
            final JsonObject map = (JsonObject)new JsonParser().parse(packet.getJson());
            try {
                final Map<UUID, nf> flags = LabyMod.getInstance().getPriorityOverlayRenderer().getLanguageFlags();
                for (final Map.Entry<String, JsonElement> entry : map.entrySet()) {
                    final UUID uuid = UUID.fromString(entry.getKey());
                    final String code = entry.getValue().getAsString();
                    if (!code.contains(".") && !code.contains("/") && !code.contains("\\") && !code.equals("??")) {
                        flags.put(uuid, new nf(String.format("labymod/textures/flags/%s.png", code)));
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (key.equals("labynet")) {
            try {
                final JsonObject jsonObject = (JsonObject)new JsonParser().parse(packet.getJson());
                final JsonObject settings = jsonObject.get("settings").getAsJsonObject();
                if (!settings.entrySet().isEmpty() && !LabyMod.getInstance().isInGame()) {
                    final JsonObject response;
                    bib.z().a(() -> bib.z().a((blk)new GuiLabyNet(settings, settingsOut -> {
                        response = new JsonObject();
                        response.add("settings", settingsOut);
                        this.sendPacket(new PacketAddonMessage("labynet", response.toString()));
                    })));
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        this.disconnect(false);
        if (!(cause instanceof IOException)) {
            cause.printStackTrace();
            ctx.close();
        }
    }
    
    public void sendPacket(final Packet packet, final Consumer<NioSocketChannel> consumer) {
        if (this.nioSocketChannel == null) {
            return;
        }
        if (!this.nioSocketChannel.isOpen() || !this.nioSocketChannel.isWritable() || this.currentConnectionState == EnumConnectionState.OFFLINE) {
            return;
        }
        if (this.nioSocketChannel.eventLoop().inEventLoop()) {
            this.nioSocketChannel.writeAndFlush((Object)packet).addListeners(new GenericFutureListener[] { (GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE });
            if (consumer != null) {
                consumer.accept(this.nioSocketChannel);
            }
        }
        else {
            this.nioSocketChannel.eventLoop().execute((Runnable)new Runnable() {
                @Override
                public void run() {
                    ClientConnection.this.nioSocketChannel.writeAndFlush((Object)packet).addListeners(new GenericFutureListener[] { (GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE });
                    if (consumer != null) {
                        consumer.accept(ClientConnection.this.nioSocketChannel);
                    }
                }
            });
        }
    }
    
    public void sendPacket(final Packet packet) {
        this.sendPacket(packet, null);
    }
    
    private void sendStatistics() {
        if (LabyMod.getSettings().sendAnonymousStatistics) {
            try {
                final JsonObject obj = new JsonObject();
                final JsonObject minecraft = new JsonObject();
                final JsonObject labymod = new JsonObject();
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
                labymod.add("addons", (JsonElement)addons);
                labymod.add("settings", ConfigManager.GSON.toJsonTree((Object)LabyMod.getSettings()));
                labymod.addProperty("version", "3.9.41");
                minecraft.add("labymod", (JsonElement)labymod);
                final JsonObject forge = new JsonObject();
                forge.addProperty("installed", Boolean.valueOf(LabyModCoreMod.isForge()));
                forge.add("mods", (JsonElement)ForgeModsScanner.getInstance().getJsonArray());
                minecraft.add("forge", (JsonElement)forge);
                minecraft.addProperty("version", Source.ABOUT_MC_VERSION);
                minecraft.addProperty("protocol_version", (Number)Source.ABOUT_MC_PROTOCOL_VERSION);
                obj.add("minecraft", (JsonElement)minecraft);
                final JsonObject operatingSystem = new JsonObject();
                operatingSystem.addProperty("name", System.getProperty("os.name"));
                operatingSystem.addProperty("version", System.getProperty("os.version"));
                obj.add("os", (JsonElement)operatingSystem);
                Processor[] processors = new Processor[0];
                try {
                    processors = new SystemInfo().getHardware().getProcessors();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
                long memorySize = 0L;
                try {
                    memorySize = ((OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
                }
                catch (Throwable e2) {
                    e2.printStackTrace();
                }
                final JsonObject hardware = new JsonObject();
                hardware.addProperty("cpu", (processors.length == 0 && processors[0] != null) ? "None" : processors[0].toString());
                hardware.addProperty("memory", (Number)memorySize);
                hardware.addProperty("hashed_mac", ModUtils.sha1(ModUtils.getMAC()));
                final JsonArray monitors = new JsonArray();
                try {
                    final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    if (graphicsEnvironment != null) {
                        final GraphicsDevice[] screenDevices;
                        final GraphicsDevice[] graphicsDevices = screenDevices = graphicsEnvironment.getScreenDevices();
                        for (final GraphicsDevice graphicsDevice : screenDevices) {
                            final DisplayMode displayMode = graphicsDevice.getDisplayMode();
                            if (displayMode != null) {
                                final JsonObject monitor = new JsonObject();
                                monitor.addProperty("width", (Number)displayMode.getWidth());
                                monitor.addProperty("height", (Number)displayMode.getHeight());
                                monitor.addProperty("refresh_rate", (Number)displayMode.getRefreshRate());
                                monitors.add((JsonElement)monitor);
                            }
                        }
                    }
                }
                catch (HeadlessException ex) {}
                hardware.add("monitors", (JsonElement)monitors);
                obj.add("hardware", (JsonElement)hardware);
                final JsonObject java = new JsonObject();
                java.addProperty("version", System.getProperty("java.version"));
                obj.add("java", (JsonElement)java);
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final JsonObject opengl = new JsonObject();
                            opengl.addProperty("version", GL11.glGetString(7938));
                            opengl.addProperty("vendor", GL11.glGetString(7936));
                            opengl.addProperty("renderer", GL11.glGetString(7937));
                            obj.add("opengl", (JsonElement)opengl);
                            ClientConnection.this.sendPacket(new PacketAddonMessage("anonymous_statistics", obj.toString()));
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            catch (Throwable e3) {
                e3.printStackTrace();
            }
        }
    }
    
    @Deprecated
    public boolean isPinAvailable() {
        return LabyMod.getInstance().getPinManager().hasValidPin(LabyMod.getInstance().getPlayerUUID());
    }
    
    @Deprecated
    public void requestPin(final Consumer<String> consumer) {
        final String pin = LabyMod.getInstance().getPinManager().getValidPinOf(LabyMod.getInstance().getPlayerUUID());
        if (pin != null) {
            consumer.accept(pin);
        }
    }
    
    public void requestDashboardPin(final Consumer<String> consumer) {
        this.dashboardPinConsumer = consumer;
        this.sendPacket(new PacketAddonMessage("dashboard_pin", new JsonObject().toString()));
    }
    
    public void setNioSocketChannel(final NioSocketChannel nioSocketChannel) {
        this.nioSocketChannel = nioSocketChannel;
    }
    
    public EnumConnectionState getCurrentConnectionState() {
        return this.currentConnectionState;
    }
    
    public String getLastKickMessage() {
        return this.lastKickMessage;
    }
    
    public String getCustomIp() {
        return this.customIp;
    }
}
