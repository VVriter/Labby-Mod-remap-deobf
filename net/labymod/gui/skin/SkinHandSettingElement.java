//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.skin;

import net.labymod.utils.*;
import net.labymod.gui.elements.*;
import net.labymod.core.*;

public class SkinHandSettingElement extends SkinCustomizationSettingElement
{
    public SkinHandSettingElement(final String displayString, final String iconName) {
        super(displayString, iconName);
        this.initCheckBox();
        this.checkBox.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                LabyModCore.getMinecraft().setUseLeftHand(accepted == CheckBox.EnumCheckBoxValue.ENABLED);
            }
        });
    }
    
    protected CheckBox.EnumCheckBoxValue loadValue() {
        return LabyModCore.getMinecraft().isUsingLeftHand() ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED;
    }
}
