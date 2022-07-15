//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.settings.elements.*;
import java.util.*;

public class Settings
{
    private List<SettingsElement> elements;
    
    public Settings() {
        this.elements = new ArrayList<SettingsElement>();
    }
    
    public Settings(final SettingsElement... element) {
        (this.elements = new ArrayList<SettingsElement>()).addAll(Arrays.asList(element));
    }
    
    public List<SettingsElement> getElements() {
        return this.elements;
    }
    
    public Settings add(final SettingsElement settingsElement) {
        this.elements.add(settingsElement);
        return this;
    }
    
    public Settings addAll(final ArrayList<SettingsElement> settingsElements) {
        this.elements.addAll(settingsElements);
        this.sort();
        return this;
    }
    
    private void sort() {
        Collections.sort(this.elements, new Comparator<SettingsElement>() {
            @Override
            public int compare(final SettingsElement e1, final SettingsElement e2) {
                return e1.getSortingId() - e2.getSortingId();
            }
        });
    }
}
