//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.blockbench;

import com.google.gson.annotations.*;
import com.google.gson.*;
import java.util.*;

public class BlockBench
{
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("geometry_name")
    @Expose
    public String geometryName;
    @SerializedName("modded_entity_version")
    @Expose
    public String moddedEntityVersion;
    @SerializedName("visible_box")
    @Expose
    public List<Double> visibleBox;
    @SerializedName("layered_textures")
    @Expose
    public Boolean layeredTextures;
    @SerializedName("resolution")
    @Expose
    public Resolution resolution;
    @SerializedName("elements")
    @Expose
    public List<BlockBenchCube> elements;
    @SerializedName("outliner")
    @Expose
    public JsonArray outliner;
    @SerializedName("textures")
    @Expose
    public List<Texture> textures;
    
    public BlockBench() {
        this.visibleBox = null;
        this.resolution = new Resolution();
        this.elements = new ArrayList<BlockBenchCube>();
        this.outliner = new JsonArray();
        this.textures = null;
    }
}
