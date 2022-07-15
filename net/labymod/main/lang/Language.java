//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.lang;

import java.util.*;
import java.util.concurrent.*;

public class Language
{
    public Map<String, String> translations;
    private String name;
    
    public Language(final String name) {
        this.translations = new ConcurrentHashMap<String, String>();
        this.name = name;
    }
    
    public void add(final String key, final String translation) {
        this.translations.put(key.toLowerCase(), translation);
    }
    
    public String get(final String key) {
        return this.translations.get(key.toLowerCase());
    }
    
    public Map<String, String> getTranslations() {
        return this.translations;
    }
    
    public String getName() {
        return this.name;
    }
}
