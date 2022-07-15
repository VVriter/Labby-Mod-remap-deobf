//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import net.minecraftforge.fml.common.*;
import net.labymod.main.update.*;
import net.labymod.utils.texture.*;
import net.labymod.user.*;
import net.labymod.api.protocol.cinematic.*;
import net.labymod.api.protocol.chunk.*;
import net.labymod.api.protocol.shadow.*;
import net.labymod.api.protocol.screen.*;
import java.lang.reflect.*;
import net.labymod.accountmanager.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.api.*;
import net.labymod.servermanager.*;
import net.labymod.utils.texture.async.*;
import net.labymod.user.emote.*;
import net.labymod.user.sticker.*;
import net.labymod.discordapp.*;
import net.labymod.ingamechat.tools.*;
import net.labymod.labyplay.*;
import com.mojang.authlib.*;
import net.minecraftforge.fml.common.event.*;
import net.labymod.core.*;
import java.io.*;
import net.labymod.ingamegui.*;
import net.labymod.main.lang.*;
import net.labymod.support.util.*;
import net.labymod.utils.credentials.*;
import net.minecraftforge.common.*;
import net.labymod.addon.*;
import net.labymod.utils.manager.*;
import net.labymod.api.events.*;
import net.labymod.main.listeners.*;
import net.minecraftforge.fml.relauncher.*;
import org.lwjgl.opengl.*;
import net.labymod.accountmanager.storage.credentials.*;
import net.labymod.api.permissions.*;
import net.labymod.mojang.afec.*;
import java.net.*;
import java.awt.*;
import io.netty.channel.*;
import net.labymod.utils.*;
import net.labymod.accountmanager.storage.account.*;
import java.util.*;
import net.labymod.labyconnect.*;

@Mod(modid = "labymod", name = "LabyMod", version = "3.9.41", acceptedMinecraftVersions = "[1.12.2]")
public class LabyMod
{
    private static LabyMod instance;
    private static Random random;
    private Updater updater;
    private static ConfigManager<ModSettings> mainConfig;
    private DynamicTextureManager dynamicTextureManager;
    private DrawUtils drawUtils;
    private UserManager userManager;
    private CinematicProtocol cinematicProtocol;
    private ChunkCachingProtocol chunkCachingProtocol;
    private ShadowProtocol shadowProtocol;
    private ScreenProtocol screenProtocol;
    private Field channelField;
    private final ServerGroupProvider serverGroupProvider;
    private AsyncAccountManager accountManager;
    private SneakingAnimationThread sneakingAnimationThread;
    private boolean hasLeftHand;
    private GuiCustomAchievement guiCustomAchievement;
    private EventManager eventManager;
    private LabyModAPI labyModAPI;
    private ServerManager serverManager;
    private ServerData currentServerData;
    private ClientTickListener clientTickListener;
    private RenderTickListener renderTickListener;
    private GuiOpenListener guiOpenListener;
    private RenderIngamePostOverlayListener priorityOverlayRenderer;
    private MarkerManager markerManager;
    private AsyncTextureLoader asyncTextureLoader;
    private float partialTicks;
    private EmoteRegistry emoteRegistry;
    private StickerRegistry stickerRegistry;
    private DiscordApp discordApp;
    private ChatToolManager chatToolManager;
    private LabyConnect labyConnect;
    private LabyPlay labyPlay;
    protected GameProfile gameProfile;
    protected String playerId;
    private boolean serverHasEmoteSpamProtection;
    private Map<UUID, bsc> playerListDataCache;
    private boolean playerListCacheEnabled;
    private final PinManager pinManager;
    
    public LabyMod() {
        this.updater = new Updater();
        this.dynamicTextureManager = new DynamicTextureManager("labymod", ModTextures.MISC_HEAD_QUESTION);
        this.serverGroupProvider = new ServerGroupProvider();
        this.emoteRegistry = new EmoteRegistry();
        this.stickerRegistry = new StickerRegistry();
        this.gameProfile = null;
        this.playerId = null;
        this.serverHasEmoteSpamProtection = false;
        this.playerListDataCache = new HashMap<UUID, bsc>();
        this.playerListCacheEnabled = true;
        this.pinManager = PinManager.load();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Log4JJNDIFix.fix();
        LabyMod.instance = this;
        this.hasLeftHand = !Source.ABOUT_MC_VERSION.startsWith("1.8");
        this.labyModAPI = new LabyModAPI(this);
        this.eventManager = new EventManager();
        this.updater.addShutdownHook();
        final String version = "mc" + Source.getMajorVersion();
        final String coreImplementationPackage = "net.labymod.core_implementation." + version;
        try {
            LabyModCore.setCoreAdapter((CoreAdapter)Class.forName(coreImplementationPackage + ".CoreImplementation").newInstance());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.drawUtils = new DrawUtils();
        this.guiCustomAchievement = new GuiCustomAchievement(bib.z());
        LabyMod.mainConfig = new ConfigManager<ModSettings>(new File("LabyMod/LabyMod-3.json"), ModSettings.class);
        ModuleConfig.loadConfig((String)null, false);
        LanguageManager.updateLang();
        (this.userManager = new UserManager()).init(this.getPlayerUUID(), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean success) {
                if (success) {
                    Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Successfully loaded all userdata");
                }
                else {
                    Debug.log(Debug.EnumDebugMode.USER_MANAGER, "An error occurred while loading all userdata");
                }
            }
        });
        (this.chatToolManager = new ChatToolManager()).initTools();
        this.asyncTextureLoader = new AsyncTextureLoader();
        TagManager.init();
        final File accountsFile = new File("LabyMod/accounts.json");
        final CredentialsAccessor credentialsAccessor = CredentialsStorages.createAccessorForOS();
        (this.accountManager = new AsyncAccountManager(accountsFile, credentialsAccessor, LauncherDirectoryUtils.getWorkingDirectory())).loadAsync(new Runnable[] { () -> {
                this.accountManager.refreshExternalSessions();
                try {
                    this.accountManager.save();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                return;
            } });
        this.eventManager.register((PluginMessageEvent)new PluginMessageListener());
        this.eventManager.register((ServerMessageEvent)new PermissionsListener());
        final RenderIngamePreOverlayListener renderGameOverlay = new RenderIngamePreOverlayListener();
        this.labyModAPI.getEventManager().register((RenderIngameOverlayEvent)renderGameOverlay);
        this.eventManager.register((ServerMessageEvent)(this.priorityOverlayRenderer = new RenderIngamePostOverlayListener()));
        this.labyModAPI.getEventManager().register((RenderIngameOverlayEvent)this.priorityOverlayRenderer);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this.guiOpenListener = new GuiOpenListener());
        MinecraftForge.EVENT_BUS.register(this.renderTickListener = new RenderTickListener());
        MinecraftForge.EVENT_BUS.register(new FOVUpdateListener());
        MinecraftForge.EVENT_BUS.register(this.clientTickListener = new ClientTickListener());
        MinecraftForge.EVENT_BUS.register(new TooltipHelper());
        MinecraftForge.EVENT_BUS.register(this.serverManager = new ServerManager());
        this.serverManager.init();
        MinecraftForge.EVENT_BUS.register(this.emoteRegistry);
        this.emoteRegistry.init();
        this.stickerRegistry.init();
        this.markerManager = new MarkerManager();
        this.labyConnect = new LabyConnect();
        this.labyPlay = new LabyPlay();
        AddonLoader.enableAddons(this.labyModAPI);
        this.updater.checkUpdate();
        LavaLightUpdater.update();
        final File serverResourcePacks = new File("server-resource-packs");
        if (!serverResourcePacks.exists()) {
            serverResourcePacks.mkdir();
        }
        this.labyModAPI.getEventManager().register((MessageSendEvent)new CapeReportCommand());
        final ServerSwitchListener serverSwitch = new ServerSwitchListener(this);
        this.labyModAPI.getEventManager().register((ServerMessageEvent)serverSwitch);
        this.labyModAPI.getEventManager().register((MessageSendEvent)serverSwitch);
        this.labyModAPI.getEventManager().registerOnJoin((Consumer)serverSwitch);
        final AddonRecommendationListener addonRecommendation = new AddonRecommendationListener(this);
        this.labyModAPI.getEventManager().registerOnJoin((Consumer)addonRecommendation);
        this.labyModAPI.getEventManager().register((ServerMessageEvent)addonRecommendation);
        this.labyModAPI.getEventManager().register((ServerMessageEvent)new InputPromptListener(this));
        this.chunkCachingProtocol = new ChunkCachingProtocol();
        this.shadowProtocol = new ShadowProtocol();
        this.cinematicProtocol = new CinematicProtocol();
        this.screenProtocol = new ScreenProtocol();
        this.discordApp = new DiscordApp();
        if (getSettings().discordRichPresence) {
            this.discordApp.initialize();
        }
        LabyModCore.getMinecraft().init(this);
        try {
            (this.channelField = ReflectionHelper.findField(gw.class, LabyModCore.getMappingAdapter().getChannelMappings())).setAccessible(true);
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
        this.dynamicTextureManager.init();
        final String debugOpenGL = System.getProperty("debugOpenGL");
        if (debugOpenGL != null && debugOpenGL.endsWith("true")) {
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    GL11.glEnable(37600);
                    GL11.glEnable(33346);
                    final KHRDebugCallback.Handler handler = (KHRDebugCallback.Handler)new KHRDebugCallback.Handler() {
                        public void handleMessage(final int type, final int id, final int severity, final int length, final String message) {
                            Debug.log(Debug.EnumDebugMode.MINECRAFT, "OpenGL Error: " + type + " " + id + " " + message);
                            new Throwable().printStackTrace();
                        }
                    };
                    KHRDebug.glDebugMessageCallback(new KHRDebugCallback(handler));
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Enabled OpenGL debugging");
                }
            });
        }
    }
    
    public String getPlayerName() {
        return bib.z().K().c();
    }
    
    public UUID getPlayerUUID() {
        final bii session = bib.z().K();
        if (session.b() == null) {
            return session.e().getId();
        }
        if (this.playerId != null && this.gameProfile != null && session.b().equals(this.playerId)) {
            return this.gameProfile.getId();
        }
        this.playerId = session.b();
        final GameProfile e = session.e();
        this.gameProfile = e;
        return e.getId();
    }
    
    public boolean isInGame() {
        return LabyModCore.getMinecraft().getPlayer() != null && LabyModCore.getMinecraft().getWorld() != null;
    }
    
    public static ModSettings getSettings() {
        return (LabyMod.mainConfig == null) ? null : LabyMod.mainConfig.getSettings();
    }
    
    public void displayMessageInChat(final String message) {
        LabyModCore.getMinecraft().displayMessageInChat(message);
    }
    
    public static boolean isBlocking(final aed player) {
        return LabyModCore.getMinecraft().isBlocking(player);
    }
    
    public void notifyMessageProfile(final GameProfile gameProfile, final String message) {
        switch (this.labyConnect.getAlertDisplayType()) {
            case CHAT: {
                final List<String> list = ModUtils.extractUrls(message);
                if (list.isEmpty()) {
                    getInstance().displayMessageInChat(ModColor.cl("7") + gameProfile.getName() + ModColor.cl("f") + ": " + message);
                    break;
                }
                LabyModCore.getMinecraft().displayMessageInChatURL(ModColor.cl("7") + gameProfile.getName() + ModColor.cl("f") + ": " + message, (String)list.get(0));
                break;
            }
            case ACHIEVEMENT: {
                getInstance().getGuiCustomAchievement().displayAchievement(gameProfile, gameProfile.getName(), message);
                break;
            }
        }
    }
    
    public void notifyMessageRaw(final String title, final String message) {
        switch (this.labyConnect.getAlertDisplayType()) {
            case CHAT: {
                final List<String> list = ModUtils.extractUrls(message);
                if (list.isEmpty()) {
                    getInstance().displayMessageInChat(ModColor.cl("7") + title + ModColor.cl("f") + ": " + message);
                    break;
                }
                LabyModCore.getMinecraft().displayMessageInChatURL(ModColor.cl("7") + title + ModColor.cl("f") + ": " + message, (String)list.get(0));
                break;
            }
            case ACHIEVEMENT: {
                getInstance().getGuiCustomAchievement().displayAchievement(title, message);
                break;
            }
        }
    }
    
    public void connectToServer(final String address) {
        this.switchServer(address, true);
    }
    
    public boolean switchServer(final String address, final boolean force) {
        this.serverManager.setPrevServer(bib.z().C());
        if (LabyModCore.getMinecraft().getWorld() != null) {
            final bse currentServerData = bib.z().C();
            if (!force && address != null && currentServerData != null && currentServerData.b != null && ModUtils.getProfileNameByIp(currentServerData.b).equalsIgnoreCase(ModUtils.getProfileNameByIp(address))) {
                return false;
            }
            if (bib.z().aF()) {
                LabyModCore.getMinecraft().getWorld().O();
                bib.z().a((bsb)null);
            }
        }
        this.onQuit();
        if (address != null) {
            final bse serverData = new bse("Server", address, false);
            bib.z().a((blk)new bkr((blk)new blr(), bib.z(), serverData));
        }
        return true;
    }
    
    public static boolean isForge() {
        return LabyModForge.isForge();
    }
    
    public static String getMessage(final String key, final Object... args) {
        return LanguageManager.translate(key, args);
    }
    
    public void onJoinServer(final bse currentServerData) {
        final String[] split = currentServerData.b.split(":");
        int port;
        try {
            port = ((split.length > 1) ? Integer.parseInt(split[1].replaceAll(" ", "")) : 25565);
        }
        catch (Exception error) {
            port = 25565;
        }
        this.labyModAPI.lastServerSwitchTime = System.currentTimeMillis();
        this.currentServerData = new ServerData(split[0], port);
        this.labyConnect.updatePlayingOnServerState("");
        Permissions.getPermissionNotifyRenderer().checkChangedPermissions();
        try {
            this.eventManager.callJoinServer(this.currentServerData);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (currentServerData.b.toLowerCase().contains("hypixel")) {
            this.serverManager.getPermissionMap().put(Permissions.Permission.BLOCKBUILD, false);
            this.serverManager.getPermissionMap().put(Permissions.Permission.CHAT, false);
            this.serverHasEmoteSpamProtection = true;
        }
        else {
            this.serverHasEmoteSpamProtection = false;
        }
        ModuleConfig.switchProfile(currentServerData.b, false);
        EntityCulling.updateShadersModValue();
        Debug.log(Debug.EnumDebugMode.MINECRAFT, "Connected to server " + currentServerData.b);
    }
    
    public void onQuit() {
        this.labyModAPI.lastServerSwitchTime = System.currentTimeMillis();
        this.serverManager.reset();
        Permissions.getPermissionNotifyRenderer().quit();
        bib.z().a(() -> this.userManager.getCosmeticImageManager().unloadUnusedTextures(true, false));
        this.cinematicProtocol.reset(false);
        this.playerListDataCache.clear();
        this.priorityOverlayRenderer.reset();
        try {
            this.eventManager.callQuitServer(this.currentServerData);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        ModuleConfig.switchProfile((String)null, false);
        this.currentServerData = null;
        this.labyConnect.updatePlayingOnServerState("");
        this.labyConnect.getTracker().onDisconnectServer();
    }
    
    public boolean openWebpage(String urlString, final boolean request) {
        try {
            if (!urlString.toLowerCase().startsWith("https://") && !urlString.toLowerCase().startsWith("http://")) {
                urlString = "http://" + urlString;
            }
            final URI uri = new URL(urlString).toURI();
            if (request) {
                final blk lastScreen = bib.z().m;
                bib.z().a((blk)new bkq((bkp)new bkp() {
                    public void a(final boolean result, final int id) {
                        if (result) {
                            final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                                try {
                                    desktop.browse(uri);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        bib.z().a(lastScreen);
                    }
                }, "Do you want to open this link in your default browser?", ModColor.cl("b") + uri.toString(), 31102009));
            }
            else {
                final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(uri);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
    
    public Channel getNettyChannel() throws Exception {
        final brz connecion = LabyModCore.getMinecraft().getConnection();
        if (connecion == null) {
            return null;
        }
        final gw networkManager = connecion.a();
        return (Channel)this.channelField.get(networkManager);
    }
    
    @Deprecated
    public ModTextureUtils getTextureUtils() {
        return ModTextureUtils.INSTANCE;
    }
    
    public boolean isPremium() {
        return bib.z().K() != null && bib.z().K().d() != null && bib.z().K().d().length() > 10;
    }
    
    public boolean isSession(final Account account) {
        final bii session = bib.z().K();
        if (session == null || session.e() == null) {
            return false;
        }
        final GameProfile profile = session.e();
        return profile.getId().equals(account.getUUID()) && Objects.equals(session.d(), account.getAccessToken());
    }
    
    public void setSession(final Account account) throws Exception {
        final ClientConnection clientConnection = getInstance().getLabyConnect().getClientConnection();
        clientConnection.disconnect(false);
        final bib mc = bib.z();
        final Field field = ReflectionHelper.findField(bib.class, LabyModCore.getMappingAdapter().getSessionMappings());
        field.setAccessible(true);
        field.set(mc, new bii(account.getUsername(), account.getUUID().toString(), account.getAccessToken(), "mojang"));
        clientConnection.connect();
        Debug.log(Debug.EnumDebugMode.ACCOUNT_MANAGER, "AccountManager: You are now playing with " + account.getUsername() + ".");
    }
    
    public Updater getUpdater() {
        return this.updater;
    }
    
    public DynamicTextureManager getDynamicTextureManager() {
        return this.dynamicTextureManager;
    }
    
    public DrawUtils getDrawUtils() {
        return this.drawUtils;
    }
    
    public UserManager getUserManager() {
        return this.userManager;
    }
    
    public CinematicProtocol getCinematicProtocol() {
        return this.cinematicProtocol;
    }
    
    public ChunkCachingProtocol getChunkCachingProtocol() {
        return this.chunkCachingProtocol;
    }
    
    public ShadowProtocol getShadowProtocol() {
        return this.shadowProtocol;
    }
    
    public ScreenProtocol getScreenProtocol() {
        return this.screenProtocol;
    }
    
    public Field getChannelField() {
        return this.channelField;
    }
    
    public ServerGroupProvider getServerGroupProvider() {
        return this.serverGroupProvider;
    }
    
    public AsyncAccountManager getAccountManager() {
        return this.accountManager;
    }
    
    public SneakingAnimationThread getSneakingAnimationThread() {
        return this.sneakingAnimationThread;
    }
    
    public boolean isHasLeftHand() {
        return this.hasLeftHand;
    }
    
    public GuiCustomAchievement getGuiCustomAchievement() {
        return this.guiCustomAchievement;
    }
    
    public EventManager getEventManager() {
        return this.eventManager;
    }
    
    public LabyModAPI getLabyModAPI() {
        return this.labyModAPI;
    }
    
    public ServerManager getServerManager() {
        return this.serverManager;
    }
    
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }
    
    public ClientTickListener getClientTickListener() {
        return this.clientTickListener;
    }
    
    public RenderTickListener getRenderTickListener() {
        return this.renderTickListener;
    }
    
    public GuiOpenListener getGuiOpenListener() {
        return this.guiOpenListener;
    }
    
    public RenderIngamePostOverlayListener getPriorityOverlayRenderer() {
        return this.priorityOverlayRenderer;
    }
    
    public MarkerManager getMarkerManager() {
        return this.markerManager;
    }
    
    public AsyncTextureLoader getAsyncTextureLoader() {
        return this.asyncTextureLoader;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public EmoteRegistry getEmoteRegistry() {
        return this.emoteRegistry;
    }
    
    public StickerRegistry getStickerRegistry() {
        return this.stickerRegistry;
    }
    
    public DiscordApp getDiscordApp() {
        return this.discordApp;
    }
    
    public ChatToolManager getChatToolManager() {
        return this.chatToolManager;
    }
    
    public LabyConnect getLabyConnect() {
        return this.labyConnect;
    }
    
    public LabyPlay getLabyPlay() {
        return this.labyPlay;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public String getPlayerId() {
        return this.playerId;
    }
    
    public boolean isServerHasEmoteSpamProtection() {
        return this.serverHasEmoteSpamProtection;
    }
    
    public Map<UUID, bsc> getPlayerListDataCache() {
        return this.playerListDataCache;
    }
    
    public boolean isPlayerListCacheEnabled() {
        return this.playerListCacheEnabled;
    }
    
    public static LabyMod getInstance() {
        return LabyMod.instance;
    }
    
    public static Random getRandom() {
        return LabyMod.random;
    }
    
    public static ConfigManager<ModSettings> getMainConfig() {
        return LabyMod.mainConfig;
    }
    
    public void setSneakingAnimationThread(final SneakingAnimationThread sneakingAnimationThread) {
        this.sneakingAnimationThread = sneakingAnimationThread;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public void setPlayerListCacheEnabled(final boolean playerListCacheEnabled) {
        this.playerListCacheEnabled = playerListCacheEnabled;
    }
    
    public PinManager getPinManager() {
        return this.pinManager;
    }
    
    static {
        LabyMod.random = new Random();
    }
}
