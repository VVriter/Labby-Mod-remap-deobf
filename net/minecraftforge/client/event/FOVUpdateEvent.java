//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.*;

public class FOVUpdateEvent extends Event
{
    private static ListenerList listenerList;
    public final float fov;
    public float newfov;
    
    public FOVUpdateEvent(final float fov) {
        this.fov = fov;
        this.newfov = fov;
    }
    
    public FOVUpdateEvent() {
        this.fov = 0.0f;
        this.newfov = 0.0f;
    }
    
    @Override
    public ListenerList getListenerList() {
        return FOVUpdateEvent.listenerList;
    }
    
    static {
        FOVUpdateEvent.listenerList = new ListenerList();
    }
}
