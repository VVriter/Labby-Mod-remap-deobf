//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import java.util.*;
import java.lang.reflect.*;
import net.labymod.serverapi.common.widgets.util.*;
import com.google.gson.*;

public class WidgetScreen
{
    private static final Gson GSON;
    private final int id;
    private final List<Widget> widgets;
    private WidgetLayout layout;
    
    public WidgetScreen(final int id) {
        this.widgets = new ArrayList<Widget>();
        this.layout = null;
        this.id = id;
    }
    
    public void setLayout(final WidgetLayout widgetLayout) {
        this.layout = widgetLayout;
    }
    
    public void addWidget(final Widget widget) {
        this.widgets.add(widget);
    }
    
    public WidgetLayout getLayout() {
        return this.layout;
    }
    
    public JsonObject toJsonObject(final EnumScreenAction action) {
        final JsonObject object = WidgetScreen.GSON.toJsonTree((Object)this).getAsJsonObject();
        object.addProperty("action", (Number)action.ordinal());
        return object;
    }
    
    public List<Widget> getWidgets() {
        return this.widgets;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static WidgetScreen from(final JsonObject object) {
        return (WidgetScreen)WidgetScreen.GSON.fromJson((JsonElement)object, (Class)WidgetScreen.class);
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)Widget.class, (Object)((src, typeOfSrc, context) -> {
            final JsonObject object = new JsonObject();
            object.addProperty("type", (Number)EnumWidget.getTypeOf((Class)src.getClass()).ordinal());
            object.add("attributes", context.serialize((Object)src));
            return object;
        })).registerTypeAdapter((Type)Widget.class, (Object)((json, typeOfSrc, context) -> {
            final JsonObject obj = json.getAsJsonObject();
            final int id = obj.get("type").getAsInt();
            final JsonObject attributes = obj.get("attributes").getAsJsonObject();
            return (Widget)context.deserialize((JsonElement)attributes, (Type)EnumWidget.values()[id].getClazz());
        })).create();
    }
}
