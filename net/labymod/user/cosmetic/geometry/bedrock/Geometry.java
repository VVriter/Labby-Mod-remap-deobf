//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.bedrock;

import com.google.gson.annotations.*;
import java.util.*;

public class Geometry
{
    @SerializedName("format_version")
    @Expose
    public String formatVersion;
    @SerializedName("minecraft:geometry")
    @Expose
    public List<MinecraftGeometry> minecraftGeometry;
    
    public Geometry() {
        this.minecraftGeometry = null;
    }
}
