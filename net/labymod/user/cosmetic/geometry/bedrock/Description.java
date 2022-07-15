//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.bedrock;

import com.google.gson.annotations.*;
import java.util.*;

public class Description
{
    @SerializedName("identifier")
    @Expose
    public String identifier;
    @SerializedName("texture_width")
    @Expose
    public Integer textureWidth;
    @SerializedName("texture_height")
    @Expose
    public Integer textureHeight;
    @SerializedName("visible_bounds_width")
    @Expose
    public Integer visibleBoundsWidth;
    @SerializedName("visible_bounds_height")
    @Expose
    public Double visibleBoundsHeight;
    @SerializedName("visible_bounds_offset")
    @Expose
    public List<Double> visibleBoundsOffset;
    
    public Description() {
        this.visibleBoundsOffset = null;
    }
}
