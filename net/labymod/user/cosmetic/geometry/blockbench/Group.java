//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.blockbench;

import com.google.gson.annotations.*;
import com.google.gson.*;

public class Group extends Item
{
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("export")
    @Expose
    public Boolean export;
    @SerializedName("isOpen")
    @Expose
    public Boolean isOpen;
    @SerializedName("locked")
    @Expose
    public Boolean locked;
    @SerializedName("visibility")
    @Expose
    public Boolean visibility;
    @SerializedName("autouv")
    @Expose
    public Integer autouv;
    @SerializedName("children")
    @Expose
    public JsonArray children;
    
    public Group() {
        this.children = null;
    }
}
