//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import com.google.gson.annotations.*;
import net.labymod.serverapi.common.widgets.util.*;

public class ImageWidget extends ContainerWidget
{
    private String url;
    @SerializedName("cut_x")
    private int cutX;
    @SerializedName("cut_y")
    private int cutY;
    @SerializedName("cut_width")
    private int cutWidth;
    @SerializedName("cut_height")
    private int cutHeight;
    
    public ImageWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final int width, final int height, final String url) {
        super(id, anchor, offsetX, offsetY, width, height);
        this.cutWidth = 256;
        this.cutHeight = 256;
        this.url = url;
    }
    
    public ImageWidget(final int id, final String url) {
        super(id, new Anchor(0.0, 0.0), 0.0, 0.0, 0, 0);
        this.cutWidth = 256;
        this.cutHeight = 256;
        this.url = url;
    }
    
    public void setCutXY(final int x, final int y) {
        this.cutX = x;
        this.cutY = y;
    }
    
    public void setCutWithHeight(final int width, final int height) {
        this.cutWidth = width;
        this.cutHeight = height;
    }
    
    public int getCutX() {
        return this.cutX;
    }
    
    public int getCutY() {
        return this.cutY;
    }
    
    public int getCutWidth() {
        return this.cutWidth;
    }
    
    public int getCutHeight() {
        return this.cutHeight;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
