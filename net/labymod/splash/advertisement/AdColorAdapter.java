//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.splash.advertisement;

import com.google.gson.*;
import java.awt.*;
import java.io.*;
import com.google.gson.stream.*;

public class AdColorAdapter extends TypeAdapter<Color>
{
    public Color read(final JsonReader jsonReader) throws IOException {
        return Color.decode(jsonReader.nextString());
    }
    
    public void write(final JsonWriter jsonWriter, final Color color) throws IOException {
        jsonWriter.name("color").value(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
