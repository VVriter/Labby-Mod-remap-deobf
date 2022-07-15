//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.settings.*;

public abstract class SimpleModule extends SimpleTextModule
{
    public abstract String getDisplayName();
    
    public abstract String getDisplayValue();
    
    public abstract String getDefaultValue();
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        DefaultElementsCreator.createKeyCustom(this, settingsElements);
    }
    
    @Override
    public String[] getKeys() {
        String string = this.getDisplayName();
        final String customKey = this.getAttribute("customKey", "");
        if (!customKey.isEmpty()) {
            string = customKey;
        }
        return new String[] { string };
    }
    
    @Override
    public String[] getValues() {
        return new String[] { this.isShown() ? this.getDisplayValue() : this.getDefaultValue() };
    }
    
    @Override
    public String[] getDefaultKeys() {
        String string = this.getDisplayName();
        final String customKey = this.getAttribute("customKey", "");
        if (!customKey.isEmpty()) {
            string = customKey;
        }
        return new String[] { string };
    }
    
    @Override
    public String[] getDefaultValues() {
        return new String[] { this.getDefaultValue() };
    }
}
