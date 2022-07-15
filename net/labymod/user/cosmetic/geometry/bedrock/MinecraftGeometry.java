//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.bedrock;

import com.google.gson.annotations.*;
import java.util.*;

public class MinecraftGeometry
{
    @SerializedName("description")
    @Expose
    public Description description;
    @SerializedName("bones")
    @Expose
    public List<Bone> bones;
    
    public MinecraftGeometry() {
        this.bones = null;
    }
}
