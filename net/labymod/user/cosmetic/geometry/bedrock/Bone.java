//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.bedrock;

import com.google.gson.annotations.*;
import java.util.*;

public class Bone
{
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("pivot")
    @Expose
    public List<Double> pivot;
    @SerializedName("parent")
    @Expose
    public String parent;
    @SerializedName("cubes")
    @Expose
    public List<BedrockCube> cubes;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    public String uuid;
    
    public Bone() {
        this.pivot = null;
        this.cubes = null;
        this.rotation = null;
        this.uuid = UUID.randomUUID().toString();
    }
}
