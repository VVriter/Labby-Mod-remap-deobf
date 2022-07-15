//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components;

import net.labymod.serverapi.common.widgets.util.*;

public abstract class ContainerWidget extends Widget
{
    protected int width;
    protected int height;
    
    public ContainerWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final int width, final int height) {
        super(id, anchor, offsetX, offsetY);
        this.width = width;
        this.height = height;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}
