//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.eventhandler;

public enum EventPriority implements IEventListener
{
    HIGHEST, 
    HIGH, 
    NORMAL, 
    LOW, 
    LOWEST;
    
    @Override
    public void invoke(final Event event) {
        event.setPhase(this);
    }
}
