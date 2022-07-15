//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user;

import net.labymod.api.events.*;
import net.labymod.user.gui.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.remote.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.support.util.*;
import com.google.gson.*;
import net.labymod.utils.*;
import net.labymod.user.util.*;
import net.labymod.user.group.*;
import net.labymod.utils.request.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.custom.handler.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.zip.*;
import java.io.*;
import com.google.common.util.concurrent.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.splash.*;

public class UserManager implements ServerMessageEvent, PluginMessageEvent
{
    private ExecutorService executorService;
    private Map<UUID, User> users;
    private boolean whitelistLoaded;
    private boolean subTitlesModified;
    private List<Long> whitelistedUsers;
    private Map<UUID, Boolean> checkedUsers;
    private Map<Integer, Class<?>> cosmeticIdToClassData;
    private JsonParser jsonParser;
    private CosmeticImageManager cosmeticImageManager;
    private UserActionGui userActionGui;
    private CosmeticClassLoader cosmeticClassLoader;
    private GroupManager groupManager;
    private RemoteCosmeticLoader remoteCosmeticLoader;
    protected AtomicInteger currentRequestId;
    protected FutureMap<Short, PacketActionPlayResponse> responseFutureMap;
    protected boolean lastSpamProtectedLegState;
    private CloakImageHandler.EnumCapePriority capePriority;
    
    public UserManager() {
        this.executorService = Executors.newFixedThreadPool(5);
        this.users = new HashMap<UUID, User>();
        this.whitelistLoaded = false;
        this.subTitlesModified = false;
        this.whitelistedUsers = new ArrayList<Long>();
        this.checkedUsers = new HashMap<UUID, Boolean>();
        this.cosmeticIdToClassData = new HashMap<Integer, Class<?>>();
        this.jsonParser = new JsonParser();
        this.cosmeticImageManager = new CosmeticImageManager(Source.getUserAgent());
        this.userActionGui = new UserActionGui(this);
        this.cosmeticClassLoader = new CosmeticClassLoader();
        this.groupManager = new GroupManager();
        this.remoteCosmeticLoader = new RemoteCosmeticLoader();
        this.currentRequestId = new AtomicInteger(-32768);
        this.responseFutureMap = new FutureMap<Short, PacketActionPlayResponse>(requestId -> {}, 1000L, null);
        this.lastSpamProtectedLegState = false;
        try {
            try {
                for (final Class<?> loadedClassInfo : this.cosmeticClassLoader.getCosmeticClasses()) {
                    final CosmeticRenderer<CosmeticData> cosmeticRenderer = (CosmeticRenderer<CosmeticData>)loadedClassInfo.newInstance();
                    for (final Class<?> subClasses : loadedClassInfo.getClasses()) {
                        if (CosmeticData.class.isAssignableFrom(subClasses)) {
                            this.cosmeticIdToClassData.put(cosmeticRenderer.getCosmeticId(), subClasses);
                        }
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            this.remoteCosmeticLoader.getAsync((IRemoteCallback)new IRemoteCallback() {
                public void load(final CosmeticRenderer<?> renderer) {
                    for (final Class<?> subClasses : renderer.getClass().getClasses()) {
                        if (CosmeticData.class.isAssignableFrom(subClasses)) {
                            final int id = renderer.getCosmeticId();
                            UserManager.this.cosmeticIdToClassData.put(renderer.getCosmeticId(), subClasses);
                            for (final User user : LabyMod.getInstance().getUserManager().getUsers().values()) {
                                if (user.getPossibleRemoteIds().containsKey(id)) {
                                    try {
                                        final CosmeticData data = UserManager.this.loadData(user, renderer.getCosmeticId(), user.getPossibleRemoteIds().get(id), subClasses);
                                        user.getCosmetics().put(id, data);
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                
                public void unload(final CosmeticRenderer<?> renderer) {
                    final int id = renderer.getCosmeticId();
                    UserManager.this.cosmeticIdToClassData.remove(id);
                    for (final User user : LabyMod.getInstance().getUserManager().getUsers().values()) {
                        user.getCosmetics().remove(id);
                    }
                }
            });
            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Registered " + this.cosmeticIdToClassData.size() + " cosmetics!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        LabyMod.getInstance().getEventManager().register((ServerMessageEvent)this);
        LabyMod.getInstance().getEventManager().register((PluginMessageEvent)this);
        try {
            this.capePriority = CloakImageHandler.EnumCapePriority.valueOf(LabyMod.getSettings().capePriority);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("account_subtitle")) {
            this.subTitlesModified = true;
            try {
                final JsonArray jsonArray = serverMessage.getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); ++i) {
                    final JsonObject accountEntry = jsonArray.get(i).getAsJsonObject();
                    if (accountEntry.has("uuid")) {
                        final UUID uuid = UUID.fromString(accountEntry.get("uuid").getAsString());
                        final User user = this.getUser(uuid);
                        final boolean prevHasSubTitle = user.getSubTitle() != null;
                        String subTitle = accountEntry.has("value") ? accountEntry.get("value").getAsString() : null;
                        if (subTitle != null) {
                            subTitle = ModColor.createColors(subTitle);
                        }
                        double subTitleSize = accountEntry.has("size") ? accountEntry.get("size").getAsDouble() : 0.5;
                        if (subTitleSize < 0.8) {
                            subTitleSize = 0.8;
                        }
                        if (subTitleSize > 1.6) {
                            subTitleSize = 1.6;
                        }
                        user.setSubTitle(subTitle);
                        user.setSubTitleSize(subTitleSize);
                        if (!prevHasSubTitle && subTitle != null) {
                            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Added subtitle for " + uuid.toString() + ": " + subTitle);
                        }
                        else if (prevHasSubTitle && subTitle == null) {
                            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Removed subtitle of " + uuid.toString() + "!");
                        }
                        else {
                            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Updated subtitle of " + uuid.toString() + " to " + subTitle);
                        }
                    }
                }
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (this.subTitlesModified && channelName.equals("MC|Brand")) {
            for (final User user : this.users.values()) {
                user.setSubTitle((String)null);
            }
            this.subTitlesModified = false;
        }
    }
    
    public void init(final UUID clientUUID, final Consumer<Boolean> consumer) {
        this.loadWhitelist(new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Loaded " + accepted + " whitelisted users.");
                UserManager.this.setChecked(clientUUID, false);
                if (clientUUID != null && UserManager.this.containsInCSV(clientUUID)) {
                    UserManager.this.getUserDataOf(clientUUID, new Consumer<User>() {
                        @Override
                        public void accept(final User user) {
                            UserManager.this.setChecked(clientUUID, true);
                            consumer.accept(true);
                            UserManager.this.remoteCosmeticLoader.load();
                        }
                    });
                }
                else {
                    consumer.accept(accepted != 0);
                    UserManager.this.remoteCosmeticLoader.load();
                }
            }
        });
    }
    
    public User getUser(final UUID uuid) {
        User user = this.users.get(uuid);
        if (user == null) {
            this.users.put(uuid, user = new User(uuid));
        }
        return user;
    }
    
    public boolean isWhitelisted(final UUID uuid) {
        if (!this.whitelistLoaded) {
            return false;
        }
        final Boolean result = this.checkedUsers.get(uuid);
        if (result == null) {
            this.checkedUsers.put(uuid, false);
            if (this.containsInCSV(uuid)) {
                this.getUserDataOf(uuid, new Consumer<User>() {
                    @Override
                    public void accept(final User user) {
                        UserManager.this.setChecked(uuid, true);
                    }
                });
            }
            return false;
        }
        return result;
    }
    
    private boolean containsInCSV(final UUID uuid) {
        final long uuidPart = uuid.getMostSignificantBits() >> 32 & 0xFFFFFFFFL;
        return this.whitelistedUsers.contains(uuidPart);
    }
    
    private void getUserDataOf(final UUID uuid, final Consumer<User> callback) {
        try {
            try {
                final User user = this.getUser(uuid);
                this.loadUserData(user, new UserResolvedCallback() {
                    @Override
                    public void resolvedGroup(final LabyGroup group) {
                        user.setGroup(group);
                    }
                    
                    @Override
                    public void resolvedCosmetics(final Map<Integer, CosmeticData> cosmetics) {
                        user.setCosmetics((Map)cosmetics);
                    }
                    
                    @Override
                    public void resolvedDailyEmoteFlat(final boolean value) {
                        user.setDailyEmoteFlat(value);
                    }
                    
                    @Override
                    public void complete() {
                        if (callback != null) {
                            callback.accept(user);
                        }
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception error) {
            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Error while resolving user data of " + uuid.toString() + " (" + error.getMessage() + ")");
            error.printStackTrace();
        }
    }
    
    public void updateUsersJson(final UUID uuid, final String json, final Consumer<Boolean> callback) {
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final User user = UserManager.this.getUser(uuid);
                    UserManager.this.handleJsonString(user, json, new UserResolvedCallback() {
                        @Override
                        public void resolvedGroup(final LabyGroup group) {
                            user.setGroup(group);
                        }
                        
                        @Override
                        public void resolvedCosmetics(final Map<Integer, CosmeticData> cosmetics) {
                            user.setCosmetics((Map)cosmetics);
                        }
                        
                        @Override
                        public void resolvedDailyEmoteFlat(final boolean value) {
                            user.setDailyEmoteFlat(value);
                        }
                        
                        @Override
                        public void complete() {
                            if (callback != null) {
                                callback.accept(true);
                            }
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.accept(false);
                    }
                }
            }
        });
    }
    
    private void loadUserData(final User user, final UserResolvedCallback callback) throws Exception {
        final String url = String.format("https://dl.labymod.net/userdata/%s.json", user.getUuid().toString());
        Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Load user data of " + user.getUuid().toString());
        DownloadServerRequest.getStringAsync(url, new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                try {
                    UserManager.this.handleJsonString(user, json, callback);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Response code for " + user.getUuid().toString() + " is " + exception.getCode());
            }
        });
    }
    
    private void handleJsonString(final User user, final String jsonString, final UserResolvedCallback callback) throws Exception {
        final boolean isClient = user.getUuid().equals(LabyMod.getInstance().getPlayerUUID());
        if (isClient) {
            Debug.log(Debug.EnumDebugMode.USER_MANAGER, jsonString);
        }
        final JsonElement jsonElement = this.jsonParser.parse(jsonString);
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        user.setHideCape(false);
        try {
            if (jsonObject.has("c")) {
                final JsonArray jsonArray = jsonObject.get("c").getAsJsonArray();
                final Iterator<JsonElement> cosmeticIterator = (Iterator<JsonElement>)jsonArray.iterator();
                final Map<Integer, CosmeticData> storedCosmeticData = new HashMap<Integer, CosmeticData>();
                final Map<EnumLegacyCosmeticType, Integer> priorityLevels = new HashMap<EnumLegacyCosmeticType, Integer>();
                while (cosmeticIterator.hasNext()) {
                    final JsonObject cosmeticJsonObject = cosmeticIterator.next().getAsJsonObject();
                    if (!cosmeticJsonObject.has("i")) {
                        Debug.log(Debug.EnumDebugMode.USER_MANAGER, cosmeticJsonObject.toString() + " has no id");
                    }
                    else {
                        final int id = cosmeticJsonObject.get("i").getAsInt();
                        this.remoteCosmeticLoader.discover(id);
                        if (!cosmeticJsonObject.has("d")) {
                            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Cosmetic id " + id + " has no data (" + cosmeticJsonObject.toString() + ")");
                        }
                        else {
                            final JsonArray dataArray = cosmeticJsonObject.get("d").getAsJsonArray();
                            final Iterator<JsonElement> dataIterator = (Iterator<JsonElement>)dataArray.iterator();
                            final List<String> dataList = new ArrayList<String>();
                            while (dataIterator.hasNext()) {
                                final JsonElement dataElement = dataIterator.next();
                                if (!dataElement.isJsonNull()) {
                                    final String dataString = dataElement.getAsString();
                                    dataList.add(dataString);
                                }
                            }
                            final Class<?> dataClass = this.cosmeticIdToClassData.get(id);
                            if (dataClass == null) {
                                user.getPossibleRemoteIds().put(id, dataList);
                                Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Cosmetic id " + id + " not found in cosmeticIdToClassData (size=" + this.cosmeticIdToClassData.size() + ") Maybe a remote cosmetic?");
                            }
                            else {
                                final CosmeticData data = this.loadData(user, id, dataList, dataClass);
                                storedCosmeticData.put(id, data);
                                if (id == 0) {
                                    user.getCloakContainer().resolved();
                                    user.setHideCape(true);
                                }
                                if (id == 22) {
                                    user.getBandanaContainer().resolved();
                                }
                                if (id == 27) {
                                    user.getShoesContainer().resolved();
                                }
                                if (id == 34) {
                                    user.getKawaiiMaskContainer().resolved();
                                }
                                if (id == 31) {
                                    user.getCoverMaskContainer().resolved();
                                }
                                if (id == 33) {
                                    user.getWatchContainer().resolved();
                                }
                                if (id == 24) {
                                    user.getAngelWingsContainer().resolved();
                                }
                                if (id == 19) {
                                    user.getCapContainer().resolved();
                                }
                                if (id == 44) {
                                    user.getPetDragonContainer().resolved();
                                }
                                if (id == 38) {
                                    user.getBunnyShoesContainer().resolved();
                                }
                                if (id == 41) {
                                    user.getScarfContainer().resolved();
                                }
                                final EnumLegacyCosmeticType type = data.getType();
                                if (priorityLevels.containsKey(type)) {
                                    final int level = priorityLevels.get(type) + 1;
                                    final Map<EnumLegacyCosmeticType, Integer> map = priorityLevels;
                                    final EnumLegacyCosmeticType enumLegacyCosmeticType = type;
                                    final CosmeticData cosmeticData = data;
                                    final int priorityLevel = level;
                                    cosmeticData.priorityLevel = priorityLevel;
                                    map.put(enumLegacyCosmeticType, priorityLevel);
                                }
                                else {
                                    final Map<EnumLegacyCosmeticType, Integer> map2 = priorityLevels;
                                    final EnumLegacyCosmeticType enumLegacyCosmeticType2 = type;
                                    final CosmeticData cosmeticData2 = data;
                                    final int priorityLevel2 = 0;
                                    cosmeticData2.priorityLevel = priorityLevel2;
                                    map2.put(enumLegacyCosmeticType2, priorityLevel2);
                                }
                            }
                        }
                    }
                }
                callback.resolvedCosmetics(storedCosmeticData);
                for (final CosmeticData data2 : storedCosmeticData.values()) {
                    data2.completed(user);
                }
            }
            if (jsonObject.has("e")) {
                final JsonArray emoteArray = jsonObject.get("e").getAsJsonArray();
                final Iterator<JsonElement> emoteIterator = (Iterator<JsonElement>)emoteArray.iterator();
                final List<Short> emotes = new ArrayList<Short>();
                while (emoteIterator.hasNext()) {
                    final JsonElement element = emoteIterator.next();
                    final short emoteId = element.getAsShort();
                    emotes.add(emoteId);
                }
                user.setEmotes((List)emotes);
            }
            if (jsonObject.has("st") || jsonObject.has("s")) {
                final JsonObject stickerObject = (jsonObject.has("s") ? jsonObject.get("s") : jsonObject.get("st")).getAsJsonObject();
                final Iterator<JsonElement> packsArray = (Iterator<JsonElement>)stickerObject.get("p").getAsJsonArray().iterator();
                final List<Short> packs = new ArrayList<Short>();
                while (packsArray.hasNext()) {
                    final short packId = packsArray.next().getAsShort();
                    packs.add(packId);
                }
                user.setStickerPacks((List)packs);
            }
            if (jsonObject.has("g")) {
                final JsonArray groupArray = jsonObject.get("g").getAsJsonArray();
                if (groupArray.size() > 0) {
                    final JsonObject group = groupArray.get(0).getAsJsonObject();
                    final short id2 = group.get("i").getAsShort();
                    final LabyGroup labyGroup = this.groupManager.getGroupById(id2);
                    if (labyGroup != null) {
                        callback.resolvedGroup(labyGroup);
                    }
                }
            }
            if (jsonObject.has("f")) {
                final JsonObject flatObject = jsonObject.get("f").getAsJsonObject();
                if (flatObject.has("e")) {
                    callback.resolvedDailyEmoteFlat(flatObject.get("e").getAsBoolean());
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        callback.complete();
    }
    
    private CosmeticData loadData(final User user, final int id, final List<String> dataList, final Class<?> dataClass) throws InstantiationException, IllegalAccessException {
        final CosmeticData cosmeticData = (CosmeticData)dataClass.newInstance();
        final boolean isClient = user.getUuid().equals(LabyMod.getInstance().getPlayerUUID());
        try {
            cosmeticData.init(id, user);
            final String[] array = dataList.toArray(new String[dataList.size()]);
            if (array.length != 0) {
                cosmeticData.loadData(array);
            }
            if (isClient) {
                Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Loaded cosmetic " + id + " for client");
            }
        }
        catch (Exception error) {
            Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Parse error while loading " + dataClass.getSimpleName() + ": " + error.getMessage());
        }
        if (cosmeticData instanceof RemoteData) {
            for (final CosmeticImageHandler handler : this.getCosmeticImageManager().getCosmeticImageHandlers()) {
                if (handler instanceof RemoteImageHandler && ((RemoteImageHandler)handler).id == id) {
                    final UserTextureContainer container = handler.getContainer(user);
                    final UUID textureUUID = ((RemoteData)cosmeticData).textureUUID;
                    if (container != null && textureUUID != null) {
                        container.setFileName(textureUUID);
                    }
                    if (!((RemoteImageHandler)handler).isHideCape()) {
                        continue;
                    }
                    user.setHideCape(true);
                }
            }
        }
        for (final Map.Entry<Integer, UserTextureContainer> remote : user.getRemoteContainer().entrySet()) {
            if (remote.getKey() == id) {
                remote.getValue().resolved();
            }
        }
        return cosmeticData;
    }
    
    public void loadWhitelist(final Consumer<Integer> callback) {
        Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Load whitelist..");
        DownloadServerRequest.getBytesAsync("https://dl.labymod.net/whitelist.bin", new ServerResponse<byte[]>() {
            @Override
            public void success(final byte[] compressedBytes) {
                try {
                    final Inflater inflater = new Inflater();
                    inflater.setInput(compressedBytes);
                    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compressedBytes.length);
                    final byte[] buffer2 = new byte[1024];
                    while (!inflater.finished()) {
                        final int count = inflater.inflate(buffer2);
                        if (count == 0) {
                            break;
                        }
                        outputStream.write(buffer2, 0, count);
                    }
                    outputStream.close();
                    final byte[] decompressedBytes = outputStream.toByteArray();
                    for (int b = 0; b < decompressedBytes.length; b += 8) {
                        long uuidPart = 0L;
                        for (int i = 0; i < 8; ++i) {
                            uuidPart += ((long)decompressedBytes[b + i] & 0xFFL) << 8 * i;
                        }
                        UserManager.this.whitelistedUsers.add(uuidPart);
                    }
                    UserManager.this.whitelistLoaded = true;
                    callback.accept(UserManager.this.whitelistedUsers.size());
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
            }
        });
    }
    
    public void setChecked(final UUID uuid, final boolean value) {
        this.checkedUsers.put(uuid, value);
    }
    
    public void removeCheckedUser(final UUID uuid) {
        this.checkedUsers.remove(uuid);
    }
    
    public void clearCache() {
        this.whitelistLoaded = false;
        this.users.clear();
        this.whitelistedUsers.clear();
        this.checkedUsers.clear();
        this.cosmeticImageManager.unloadUnusedTextures(true, true);
    }
    
    public void requestAction(final short id, final byte[] bytes, final FutureCallback<PacketActionPlayResponse> callback) {
        int requestId = 0;
        if ((requestId = this.currentRequestId.incrementAndGet()) > 32767) {
            this.currentRequestId.set(-32768);
            requestId = -32768;
        }
        Futures.addCallback((ListenableFuture)this.responseFutureMap.get((short)requestId), (FutureCallback)callback);
        LabyMod.getInstance().getLabyConnect().getClientConnection().sendPacket((Packet)new PacketActionPlay((short)requestId, id, bytes));
    }
    
    public void resolveAction(final short requestId, final PacketActionPlayResponse packetActionPlayResponse) {
        this.responseFutureMap.resolve(requestId, packetActionPlayResponse);
    }
    
    public void refresh() {
        this.remoteCosmeticLoader.reset();
        this.clearCache();
        this.init(LabyMod.getInstance().getPlayerUUID(), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean success) {
                LabyMod.getInstance().getUserManager().getCosmeticImageManager().loadPlayersInView();
            }
        });
        this.getGroupManager().load();
        LabyMod.getInstance().getEmoteRegistry().loadEmoteSources();
        LabyMod.getInstance().getStickerRegistry().loadPacks();
        SplashLoader.getLoader().load();
    }
    
    public boolean canSeeDraftCosmetics(final User user) {
        return this.groupManager.hasPermissionOf(user, (short)11);
    }
    
    public User getClientUser() {
        final UUID uuid = LabyMod.getInstance().getPlayerUUID();
        return (uuid == null) ? null : this.getUser(LabyMod.getInstance().getPlayerUUID());
    }
    
    public void resetFamiliars() {
        for (final User user : this.users.values()) {
            user.setFamiliar(false);
        }
    }
    
    public Map<UUID, User> getUsers() {
        return this.users;
    }
    
    public Map<Integer, Class<?>> getCosmeticIdToClassData() {
        return this.cosmeticIdToClassData;
    }
    
    public CosmeticImageManager getCosmeticImageManager() {
        return this.cosmeticImageManager;
    }
    
    public UserActionGui getUserActionGui() {
        return this.userActionGui;
    }
    
    public CosmeticClassLoader getCosmeticClassLoader() {
        return this.cosmeticClassLoader;
    }
    
    public GroupManager getGroupManager() {
        return this.groupManager;
    }
    
    public RemoteCosmeticLoader getRemoteCosmeticLoader() {
        return this.remoteCosmeticLoader;
    }
    
    public CloakImageHandler.EnumCapePriority getCapePriority() {
        return this.capePriority;
    }
    
    public void setCapePriority(final CloakImageHandler.EnumCapePriority capePriority) {
        this.capePriority = capePriority;
    }
}
