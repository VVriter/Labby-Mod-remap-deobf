//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.main.*;

public class GuiImageButton extends bir
{
    private nf imageResource;
    private nf hoverImageResource;
    private int offsetX;
    private int offsetY;
    private int hoverOffsetX;
    private int hoverOffsetY;
    private int textureSizeX;
    private int textureSizeY;
    private double x;
    private double y;
    private double width;
    private double height;
    private double imageHoverZoom;
    private float imageAlpha;
    private float imageAlphaHover;
    private boolean enabled;
    
    public GuiImageButton(final nf imageResource, final nf hoverImageResource) {
        this.imageHoverZoom = 0.5;
        this.imageAlpha = 1.0f;
        this.imageAlphaHover = 1.0f;
        this.enabled = true;
        this.imageResource = imageResource;
        this.hoverImageResource = hoverImageResource;
    }
    
    public GuiImageButton(final nf imageResource) {
        this(imageResource, imageResource);
    }
    
    public GuiImageButton(final nf imageResource, final int offsetX, final int offsetY, final int hoverOffsetX, final int hoverOffsetY, final int textureSizeX, final int textureSizeY) {
        this.imageHoverZoom = 0.5;
        this.imageAlpha = 1.0f;
        this.imageAlphaHover = 1.0f;
        this.enabled = true;
        this.imageResource = imageResource;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.hoverOffsetX = hoverOffsetX;
        this.hoverOffsetY = hoverOffsetY;
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
    }
    
    public GuiImageButton(final nf imageResource, final int offsetX, final int offsetY, final int textureSizeX, final int textureSizeY) {
        this(imageResource, offsetX, offsetY, offsetX, offsetY, textureSizeX, textureSizeY);
    }
    
    public void init(final double x, final double y, final double width, final double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void init(final double x, final double y, final double size) {
        this.init(x, y, size, size);
    }
    
    public void draw(final double x, final double y, final double width, final double height, final int mouseX, final int mouseY) {
        this.init(x, y, width, height);
        this.draw(mouseX, mouseY);
    }
    
    public void draw(final double x, final double y, final double size, final int mouseX, final int mouseY) {
        this.init(x, y, size);
        this.draw(mouseX, mouseY);
    }
    
    public void draw(final int mouseX, final int mouseY) {
        final boolean mouseOver = this.isMouseOver(mouseX, mouseY);
        final double zoom = mouseOver ? this.imageHoverZoom : 0.0;
        final boolean usingTextureMap = this.hoverImageResource == null;
        bib.z().N().a((mouseOver && !usingTextureMap) ? this.hoverImageResource : this.imageResource);
        if (usingTextureMap) {
            LabyMod.getInstance().getDrawUtils().drawTexture(this.x - zoom, this.y - zoom, mouseOver ? ((double)this.hoverOffsetX) : ((double)this.offsetX), mouseOver ? ((double)this.hoverOffsetY) : ((double)this.offsetY), this.textureSizeX, this.textureSizeY, this.width + zoom * 2.0, this.height + zoom * 2.0, mouseOver ? this.imageAlphaHover : this.imageAlpha);
        }
        else {
            LabyMod.getInstance().getDrawUtils().drawTexture(this.x - zoom, this.y - zoom, 255.0, 255.0, this.width + zoom * 2.0, this.height + zoom * 2.0, mouseOver ? this.imageAlphaHover : this.imageAlpha);
        }
    }
    
    public boolean isMouseOver(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height && this.enabled;
    }
    
    public void setImageHoverZoom(final double imageHoverZoom) {
        this.imageHoverZoom = imageHoverZoom;
    }
    
    public void setImageAlpha(final float imageAlpha) {
        this.imageAlpha = imageAlpha;
    }
    
    public void setImageAlphaHover(final float imageAlphaHover) {
        this.imageAlphaHover = imageAlphaHover;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
