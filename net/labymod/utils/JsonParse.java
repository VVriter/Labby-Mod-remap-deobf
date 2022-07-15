//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import com.google.gson.*;

public class JsonParse
{
    private static JsonParser jsonParser;
    
    public static String parse(final String input, final String... value) {
        final JsonElement data = JsonParse.jsonParser.parse(input);
        final JsonObject rootobj = data.getAsJsonObject();
        JsonObject currentObject = null;
        String output = "";
        for (int i = 0; i < value.length; ++i) {
            if (currentObject == null) {
                if (value.length == 1) {
                    output = rootobj.get(value[i]).getAsString();
                }
                else {
                    currentObject = rootobj.getAsJsonObject(value[i]);
                }
            }
            else if (i == value.length - 1) {
                output = currentObject.get(value[i]).getAsString();
            }
            else {
                currentObject = currentObject.getAsJsonObject(value[i]);
            }
        }
        return output;
    }
    
    public static JsonElement parse(final String input) {
        return JsonParse.jsonParser.parse(input);
    }
    
    static {
        JsonParse.jsonParser = new JsonParser();
    }
}
