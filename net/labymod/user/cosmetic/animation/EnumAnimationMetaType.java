//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation;

public enum EnumAnimationMetaType
{
    TRIGGER("trigger", 't'), 
    PROBABILITY("probability", 'p'), 
    FORCE("force", 'f'), 
    QUEUE("queue", 'q'), 
    SPEED("speed", 's'), 
    CONDITION("condition", 'c');
    
    private String key;
    private char shortcut;
    
    public String getKey() {
        return this.key;
    }
    
    public char getShortcut() {
        return this.shortcut;
    }
    
    public static EnumAnimationMetaType get(final String argument) {
        for (final EnumAnimationMetaType meta : values()) {
            if (meta.key.equals(argument) || meta.shortcut == argument.charAt(0)) {
                return meta;
            }
        }
        return null;
    }
    
    private EnumAnimationMetaType(final String key, final char shortcut) {
        this.key = key;
        this.shortcut = shortcut;
    }
}
