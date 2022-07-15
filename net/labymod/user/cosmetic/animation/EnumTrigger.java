//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation;

public enum EnumTrigger
{
    MOVING, 
    IDLE, 
    START_MOVING, 
    STOP_MOVING, 
    START_SNEAKING, 
    STOP_SNEAKING, 
    SNEAK_MOVING, 
    SNEAK_IDLE;
    
    public static EnumTrigger getById(final String id) {
        for (final EnumTrigger trigger : values()) {
            if (trigger.name().equals(id)) {
                return trigger;
            }
        }
        return null;
    }
}
