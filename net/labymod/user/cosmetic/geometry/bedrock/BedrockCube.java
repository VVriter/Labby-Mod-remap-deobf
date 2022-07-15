//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.bedrock;

import com.google.gson.annotations.*;
import java.util.*;

public class BedrockCube
{
    @SerializedName("origin")
    @Expose
    public List<Double> origin;
    @SerializedName("size")
    @Expose
    public List<Integer> size;
    @SerializedName("uv")
    @Expose
    public List<Integer> uv;
    @SerializedName("pivot")
    @Expose
    public List<Double> pivot;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    @SerializedName("mirror")
    @Expose
    public Boolean mirror;
    @Expose
    public Float inflate;
    public String uuid;
    
    public BedrockCube() {
        this.origin = null;
        this.size = null;
        this.uv = null;
        this.pivot = null;
        this.rotation = null;
        this.mirror = false;
        this.inflate = 0.0f;
        this.uuid = UUID.randomUUID().toString();
    }
}
