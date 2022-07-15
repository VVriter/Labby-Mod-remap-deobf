//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.blockbench;

import com.google.gson.annotations.*;
import java.util.*;

public class Item
{
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("origin")
    @Expose
    public List<Double> origin;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    @SerializedName("mirror")
    @Expose
    public boolean mirror;
    
    public Item() {
        this.origin = null;
        this.rotation = null;
        this.mirror = false;
    }
}
