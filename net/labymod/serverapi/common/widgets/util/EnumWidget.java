//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.util;

import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;

public enum EnumWidget
{
    BUTTON((Class<? extends Widget>)ButtonWidget.class), 
    TEXT_FIELD((Class<? extends Widget>)TextFieldWidget.class), 
    LABEL((Class<? extends Widget>)LabelWidget.class), 
    COLOR_PICKER((Class<? extends Widget>)ColorPickerWidget.class), 
    IMAGE((Class<? extends Widget>)ImageWidget.class);
    
    private final Class<? extends Widget> clazz;
    
    private EnumWidget(final Class<? extends Widget> clazz) {
        this.clazz = clazz;
    }
    
    public Class<? extends Widget> getClazz() {
        return this.clazz;
    }
    
    public static EnumWidget getTypeOf(final Class<? extends Widget> clazz) {
        for (final EnumWidget type : values()) {
            if (type.clazz == clazz) {
                return type;
            }
        }
        return null;
    }
}
