//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.group;

import com.google.gson.annotations.*;
import java.awt.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import java.beans.*;

public class LabyGroup
{
    private int id;
    private String name;
    @SerializedName("nice_name")
    private String displayName;
    @SerializedName("color_hex")
    private String colorHex;
    @SerializedName("color_minecraft")
    private char colorMinecraft;
    @SerializedName("tag_name")
    private String tagName;
    @SerializedName("display_type")
    private String displayTypeString;
    private Color color;
    private EnumGroupDisplayType displayType;
    
    protected LabyGroup init() {
        try {
            final EnumGroupDisplayType type = EnumGroupDisplayType.valueOf(this.displayTypeString);
            this.displayType = ((type == null) ? EnumGroupDisplayType.NONE : type);
        }
        catch (Exception error) {
            error.printStackTrace();
            this.displayType = EnumGroupDisplayType.NONE;
        }
        try {
            if (this.colorHex != null && !this.colorHex.isEmpty()) {
                this.color = Color.decode("#" + this.colorHex);
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        return this;
    }
    
    public String getDisplayTag() {
        return ModColor.cl("f") + ModColor.cl("l") + "LABYMOD " + '§' + this.colorMinecraft + this.tagName;
    }
    
    public void renderBadge(final double x, final double y, final double width, final double height, final boolean small) {
        final boolean familiar = this.color == null;
        if (!familiar) {
            bus.d(this.color.getRed() / 255.0f, this.color.getGreen() / 255.0f, this.color.getBlue() / 255.0f);
        }
        final nf texture = familiar ? (small ? ModTextures.BADGE_FAMILIAR_SMALL : ModTextures.BADGE_FAMILIAR) : (small ? ModTextures.BADGE_GROUP_SMALL : ModTextures.BADGE_GROUP);
        bus.m();
        bib.z().N().a(texture);
        LabyMod.getInstance().getDrawUtils().drawTexture(x, y, 255.0, 255.0, 8.0, 8.0, 1.1f);
        bus.d(1.0f, 1.0f, 1.0f);
    }
    
    public LabyGroup() {
    }
    
    @ConstructorProperties({ "id", "name", "displayName", "colorHex", "colorMinecraft", "tagName", "displayTypeString", "color", "displayType" })
    public LabyGroup(final int id, final String name, final String displayName, final String colorHex, final char colorMinecraft, final String tagName, final String displayTypeString, final Color color, final EnumGroupDisplayType displayType) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.colorHex = colorHex;
        this.colorMinecraft = colorMinecraft;
        this.tagName = tagName;
        this.displayTypeString = displayTypeString;
        this.color = color;
        this.displayType = displayType;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public char getColorMinecraft() {
        return this.colorMinecraft;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public EnumGroupDisplayType getDisplayType() {
        return this.displayType;
    }
}
