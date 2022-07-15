//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.model;

import com.google.gson.annotations.*;
import net.labymod.user.cosmetic.pet.util.*;

public class RemoteObject
{
    public int id;
    public String name;
    public double scale;
    public boolean draft;
    @SerializedName("frame_aspect_ratio")
    public Ratio ratio;
    @SerializedName("frame_animation_delay")
    public long animationDelay;
    @SerializedName("nametag_offset")
    public float nametagOffset;
    @SerializedName("move_type")
    public EnumMoveType moveType;
    public EnumCosmeticType type;
    @SerializedName("texture_type")
    public EnumTextureType textureType;
    @SerializedName("attached_to")
    public EnumAttachedTo attachedTo;
    @SerializedName("mirror")
    public boolean mirror;
    @SerializedName("mirror_type")
    public EnumMirrorType mirrorType;
    @SerializedName("texture_directory")
    public String textureDirectory;
    public String[] options;
    @SerializedName("hide_cape")
    public boolean hideCape;
    
    public RemoteObject() {
        this.scale = 1.0;
        this.draft = true;
        this.ratio = null;
        this.animationDelay = 50L;
        this.nametagOffset = 0.0f;
        this.moveType = EnumMoveType.BOTH;
        this.type = EnumCosmeticType.COSMETIC;
        this.textureType = EnumTextureType.TYPE_BOUND;
        this.attachedTo = EnumAttachedTo.BODY;
        this.mirror = false;
        this.mirrorType = EnumMirrorType.DUPLICATE;
        this.hideCape = false;
    }
}
