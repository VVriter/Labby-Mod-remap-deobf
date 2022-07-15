//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components;

import net.labymod.serverapi.common.widgets.util.*;

public abstract class ValueContainerWidget extends ContainerWidget
{
    protected String value;
    
    public ValueContainerWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final String value, final int width, final int height) {
        super(id, anchor, offsetX, offsetY, width, height);
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
}
