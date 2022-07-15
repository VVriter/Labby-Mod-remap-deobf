//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.*;

public class RenderWorldLastEvent extends Event
{
    private static final ListenerList listenerList;
    public final float partialTicks;
    
    public RenderWorldLastEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    @Override
    public ListenerList getListenerList() {
        return RenderWorldLastEvent.listenerList;
    }
    
    static {
        listenerList = new ListenerList();
    }
}
