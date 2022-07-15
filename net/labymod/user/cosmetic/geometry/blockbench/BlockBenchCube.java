//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.blockbench;

import java.util.*;
import com.google.gson.annotations.*;

public class BlockBenchCube extends Item
{
    @SerializedName("from")
    @Expose
    public List<Double> from;
    @SerializedName("to")
    @Expose
    public List<Double> to;
    @SerializedName("autouv")
    @Expose
    public Integer autouv;
    @SerializedName("color")
    @Expose
    public Integer color;
    @SerializedName("locked")
    @Expose
    public Boolean locked;
    @SerializedName("faces")
    @Expose
    public Faces faces;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("uv_offset")
    @Expose
    public List<Integer> uvOffset;
    @Expose
    public Float inflate;
    
    public BlockBenchCube() {
        this.from = null;
        this.to = null;
        this.uvOffset = null;
        this.inflate = 0.0f;
    }
}
