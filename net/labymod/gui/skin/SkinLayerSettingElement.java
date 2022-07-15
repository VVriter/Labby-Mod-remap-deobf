//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.skin;

import net.labymod.utils.*;
import net.labymod.gui.elements.*;
import java.util.*;

public class SkinLayerSettingElement extends SkinCustomizationSettingElement
{
    private aee[] modelParts;
    
    public SkinLayerSettingElement(final GuiSkinCustomization skinCustomization, final String displayString, final String iconName, final aee... modelParts) {
        super((modelParts.length == 1) ? cey.a("options.modelPart." + modelParts[0].c(), new Object[0]) : displayString, iconName);
        this.modelParts = modelParts;
        this.initCheckBox();
        this.checkBox.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                for (final aee part : modelParts) {
                    if (accepted != CheckBox.EnumCheckBoxValue.INDETERMINATE) {
                        skinCustomization.updatePart(part, accepted == CheckBox.EnumCheckBoxValue.ENABLED);
                    }
                }
            }
        });
    }
    
    protected CheckBox.EnumCheckBoxValue loadValue() {
        final Set<aee> enabledList = (Set<aee>)bib.z().t.d();
        if (this.modelParts.length == 1) {
            return enabledList.contains(this.modelParts[0]) ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED;
        }
        boolean allContains = true;
        boolean allContainsNot = true;
        for (final aee part : this.modelParts) {
            if (!enabledList.contains(part)) {
                allContains = false;
            }
            else {
                allContainsNot = false;
            }
        }
        return allContains ? CheckBox.EnumCheckBoxValue.ENABLED : (allContainsNot ? CheckBox.EnumCheckBoxValue.DISABLED : CheckBox.EnumCheckBoxValue.INDETERMINATE);
    }
}
