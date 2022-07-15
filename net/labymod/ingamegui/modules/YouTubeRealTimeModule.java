//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.text.*;
import java.util.concurrent.*;
import com.google.gson.*;
import net.labymod.utils.request.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.main.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.net.*;
import net.labymod.support.util.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class YouTubeRealTimeModule extends SimpleModule
{
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private ExecutorService executorService;
    private JsonParser jsonParser;
    private String displayChannelUrl;
    private long requestInterval;
    private String channelUniqueId;
    private String requestSubscriberURL;
    private long lastRequest;
    private int subscribers;
    private String statusMessage;
    private String[] apiKeys;
    private int blocked;
    private NumberFormat numberInstance;
    
    public YouTubeRealTimeModule() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.jsonParser = new JsonParser();
        this.requestSubscriberURL = null;
        this.lastRequest = 0L;
        this.blocked = 6;
        this.numberInstance = NumberFormat.getNumberInstance(Locale.US);
        DownloadServerRequest.getJsonObjectAsync("https://dl.labymod.net/subcounter.json", new ServerResponse<JsonElement>() {
            @Override
            public void success(final JsonElement result) {
                final JsonObject jsonObject = result.getAsJsonObject();
                if (jsonObject.has("url")) {
                    YouTubeRealTimeModule.this.requestSubscriberURL = jsonObject.get("url").getAsString();
                }
                if (jsonObject.has("keys")) {
                    final JsonArray jsonArray = jsonObject.get("keys").getAsJsonArray();
                    final Iterator<JsonElement> it = (Iterator<JsonElement>)jsonArray.iterator();
                    YouTubeRealTimeModule.this.apiKeys = new String[jsonArray.size()];
                    int i = 0;
                    while (it.hasNext()) {
                        YouTubeRealTimeModule.this.apiKeys[i] = it.next().getAsString();
                        ++i;
                    }
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
            }
        });
    }
    
    @Override
    public String getDisplayName() {
        return "Subscriber";
    }
    
    @Override
    public String getDisplayValue() {
        return (this.statusMessage == null) ? this.numberInstance.format(this.subscribers) : this.statusMessage;
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.getEnabled().size() == 0 || !LabyMod.getInstance().isInGame()) {
            return;
        }
        if (Module.isDrawn() && this.lastRequest < System.currentTimeMillis()) {
            this.lastRequest = System.currentTimeMillis() + 1000L * this.requestInterval;
            if (this.channelUniqueId == null || this.channelUniqueId.isEmpty()) {
                this.handleAccept((this.displayChannelUrl == null || this.displayChannelUrl.isEmpty()) ? -4 : -3);
            }
            else if (this.blocked <= 0) {
                this.handleAccept(-5);
            }
            else {
                this.requestSubscriber(this.channelUniqueId, new Consumer<Integer>() {
                    @Override
                    public void accept(final Integer subscriberCount) {
                        YouTubeRealTimeModule.this.handleAccept(subscriberCount);
                    }
                });
            }
        }
    }
    
    private void handleAccept(final Integer subscriberCount) {
        switch (subscriberCount) {
            case -6: {
                this.statusMessage = "Cannot parse json!";
                break;
            }
            case -5: {
                this.statusMessage = "YouTube API is blocked! Please restart your game..";
                break;
            }
            case -4: {
                this.statusMessage = "No channel given";
                break;
            }
            case -3: {
                this.statusMessage = "Parsing url..";
                break;
            }
            case -2: {
                this.statusMessage = "Channel not found";
                break;
            }
            case -1: {
                this.statusMessage = "Request failed";
                break;
            }
            default: {
                this.statusMessage = null;
                break;
            }
        }
        if (subscriberCount == -2 || subscriberCount == -3) {
            this.lastRequest = System.currentTimeMillis() + 3000L;
            this.parseInputString(this.displayChannelUrl, new Consumer<String>() {
                @Override
                public void accept(final String accepted) {
                    if (accepted == null) {
                        YouTubeRealTimeModule.this.statusMessage = "Invalid url";
                        YouTubeRealTimeModule.this.lastRequest = System.currentTimeMillis() + 500000L;
                    }
                    else {
                        YouTubeRealTimeModule.this.channelUniqueId = accepted;
                        YouTubeRealTimeModule.this.setAttribute("channelUniqueId", accepted);
                    }
                }
            });
        }
        this.subscribers = subscriberCount;
    }
    
    private void parseInputString(final String inputString, final Consumer<String> callback) {
        if (inputString.contains("/")) {
            final String[] data = inputString.split("/");
            YTChannelType foundType = null;
            for (String value : data) {
                if (foundType == YTChannelType.CHANNEL) {
                    this.statusMessage = "Load channel by channel id..";
                    if (value.contains("?")) {
                        value = value.split("\\?")[0];
                    }
                    callback.accept(value);
                    return;
                }
                if (foundType == YTChannelType.USER) {
                    this.statusMessage = "Fetching channel id by username..";
                    this.requestChannelUniqueId(foundType, value, callback);
                    return;
                }
                if (foundType == YTChannelType.C) {
                    this.statusMessage = "Fetching channel id by cname..";
                    this.requestChannelUniqueId(foundType, value, callback);
                    return;
                }
                foundType = YTChannelType.getByValue(value);
            }
            if (foundType == null) {
                this.statusMessage = "Please use ../c/<name>, ../user/<name> or ../channel/<id>";
            }
        }
        else {
            if (this.statusMessage.isEmpty()) {
                this.statusMessage = "Validate channel id..";
            }
            else {
                this.statusMessage += " - Validate channel id..";
            }
            callback.accept(inputString);
        }
    }
    
    private void requestSubscriber(final String channelUniqueId, final Consumer<Integer> callback) {
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (channelUniqueId.isEmpty()) {
                    return;
                }
                try {
                    if (YouTubeRealTimeModule.this.requestSubscriberURL == null) {
                        callback.accept(-1);
                        return;
                    }
                    final String url = String.format(YouTubeRealTimeModule.this.requestSubscriberURL, channelUniqueId, YouTubeRealTimeModule.this.apiKeys[LabyMod.getRandom().nextInt(YouTubeRealTimeModule.this.apiKeys.length)]);
                    final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    connection.connect();
                    if (connection.getResponseCode() != 200) {
                        Debug.log(Debug.EnumDebugMode.GENERAL, "YouTube API error: " + connection.getResponseCode() + " (" + url + ")");
                        YouTubeRealTimeModule.this.blocked--;
                    }
                    final Scanner scanner = new Scanner(connection.getInputStream());
                    if (scanner.hasNext()) {
                        try {
                            String jsonResponse = "";
                            while (scanner.hasNextLine()) {
                                jsonResponse += scanner.nextLine();
                            }
                            final JsonElement jsonObj = YouTubeRealTimeModule.this.jsonParser.parse(jsonResponse);
                            final int count = jsonObj.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("statistics").getAsJsonObject().get("subscriberCount").getAsInt();
                            if (YouTubeRealTimeModule.this.blocked < 5) {
                                YouTubeRealTimeModule.this.blocked++;
                            }
                            callback.accept(count);
                        }
                        catch (Exception error) {
                            error.printStackTrace();
                            callback.accept(-6);
                        }
                    }
                    else {
                        callback.accept(-2);
                    }
                    scanner.close();
                }
                catch (Exception error2) {
                    error2.printStackTrace();
                    callback.accept(-1);
                }
            }
        });
    }
    
    private void requestChannelUniqueId(final YTChannelType channelType, final String channelName, final Consumer<String> callback) {
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (channelType == null || channelName.isEmpty()) {
                    callback.accept(null);
                    return;
                }
                try {
                    final HttpURLConnection connection = (HttpURLConnection)new URL("https://socialblade.com/youtube/" + channelType.getValue() + "/" + channelName + "/realtime").openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    connection.connect();
                    final Scanner scanner = new Scanner(connection.getInputStream());
                    while (scanner.hasNext()) {
                        final String line = scanner.nextLine();
                        if (!line.startsWith("<p id=\"rawUser\" style=\"display: none;\">")) {
                            continue;
                        }
                        final String uid = line.replaceAll("(?i)<p id=\"rawUser\" style=\"display: none;\">(.+?)</p>", "$1");
                        if (!uid.contains("\"")) {
                            callback.accept(uid);
                            YouTubeRealTimeModule.this.lastRequest = System.currentTimeMillis() + 1000L;
                            scanner.close();
                            return;
                        }
                        break;
                    }
                    scanner.close();
                    callback.accept(null);
                }
                catch (Exception error) {
                    error.printStackTrace();
                    callback.accept(null);
                }
            }
        });
    }
    
    public void loadSettings() {
        this.channelUniqueId = this.getAttribute("channelUniqueId", "");
        this.requestInterval = Integer.parseInt(this.getAttribute("interval", "30"));
        this.displayChannelUrl = this.getAttribute("displayChannelUrl", "");
        if (this.requestInterval < 30L) {
            this.requestInterval = 30L;
        }
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new StringElement(this, new ControlElement.IconData(Material.PAPER), "Channel URL", "displayChannelUrl").maxLength(200).addCallback(new Consumer<String>() {
            @Override
            public void accept(final String value) {
                YouTubeRealTimeModule.this.channelUniqueId = null;
                YouTubeRealTimeModule.this.setAttribute("channelUniqueId", "");
                YouTubeRealTimeModule.this.lastRequest = System.currentTimeMillis() + 1000L;
            }
        }));
        settingsElements.add(new NumberElement(this, new ControlElement.IconData(Material.WATCH), "Update interval", "interval").setRange(30, 500).addCallback(new Consumer<Integer>() {
            @Override
            public void accept(final Integer value) {
                YouTubeRealTimeModule.this.lastRequest = System.currentTimeMillis() + 1000 * value;
            }
        }));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon("youtube");
    }
    
    public String getSettingName() {
        return "youTube_subscriber_counter";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_EXTERNAL_SERVICES;
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public enum YTChannelType
    {
        C("c"), 
        USER("user"), 
        CHANNEL("channel");
        
        private String value;
        
        private YTChannelType(final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public static YTChannelType getByValue(final String value) {
            for (final YTChannelType type : values()) {
                if (type.getValue().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }
    }
}
