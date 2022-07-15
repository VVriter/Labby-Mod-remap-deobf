//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import java.io.*;
import net.labymod.api.permissions.*;
import java.util.regex.*;
import net.labymod.utils.*;
import com.google.gson.*;
import net.labymod.main.*;
import net.labymod.api.events.*;
import net.labymod.core.*;
import java.util.*;

public class TagManager
{
    public static char SYMBOL;
    private static ConfigManager<TagConfig> configManager;
    private static JsonParser jsonParser;
    
    public static void init() {
        TagManager.configManager = (ConfigManager<TagConfig>)new ConfigManager(new File("LabyMod/", "tags.json"), (Class)TagConfig.class);
    }
    
    public static void save() {
        TagManager.configManager.save();
    }
    
    public static ConfigManager<TagConfig> getConfigManager() {
        return TagManager.configManager;
    }
    
    public static String getTaggedMessage(final String message) {
        if (!Permissions.isAllowed(Permissions.Permission.TAGS)) {
            return null;
        }
        try {
            for (final Map.Entry<String, String> tagEntry : ((TagConfig)TagManager.configManager.getSettings()).getTags().entrySet()) {
                if (message.toLowerCase().contains(tagEntry.getKey().toLowerCase())) {
                    final String regex = "(?i)" + tagEntry.getKey();
                    final Pattern pattern = Pattern.compile(regex);
                    final String replacement = ModUtils.translateAlternateColorCodes('&', tagEntry.getValue());
                    if ((message.startsWith("[") || message.startsWith("{")) && (message.endsWith("]") || message.endsWith("}"))) {
                        final JsonElement element = TagManager.jsonParser.parse(message);
                        replaceObject(element, pattern, replacement);
                        return element.toString();
                    }
                    return pattern.matcher(message).replaceAll(replacement);
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }
    
    private static void replaceObject(final JsonElement element, final Pattern pattern, final String replacement) {
        if (element.isJsonArray()) {
            final JsonArray jsonArray = element.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); ++i) {
                replaceObject(jsonArray.get(i), pattern, replacement);
            }
        }
        if (element.isJsonObject()) {
            final JsonObject object = element.getAsJsonObject();
            if (object.has("extra")) {
                replaceObject(object.get("extra"), pattern, replacement);
            }
            if (object.has("with")) {
                replaceObject(object.get("with"), pattern, replacement);
            }
            if (object.has("text")) {
                String text = object.get("text").getAsString();
                text = pattern.matcher(text).replaceAll(replacement);
                object.addProperty("text", text);
            }
        }
    }
    
    public static Object tagComponent(Object chatComponent) {
        for (final MessageModifyChatEvent a : LabyMod.getInstance().getEventManager().getMessageModifyChat()) {
            chatComponent = a.onModifyChatMessage(chatComponent);
        }
        if (getConfigManager() == null || ((TagConfig)getConfigManager().getSettings()).getTags().isEmpty() || !Permissions.isAllowed(Permissions.Permission.TAGS)) {
            return chatComponent;
        }
        return LabyModCore.getMinecraft().getTaggedChatComponent(chatComponent);
    }
    
    static {
        TagManager.SYMBOL = '\u270e';
        TagManager.jsonParser = new JsonParser();
    }
    
    public static class TagConfig
    {
        private Map<String, String> tags;
        
        public TagConfig() {
            this.tags = new HashMap<String, String>();
        }
        
        public Map<String, String> getTags() {
            return this.tags;
        }
    }
}
