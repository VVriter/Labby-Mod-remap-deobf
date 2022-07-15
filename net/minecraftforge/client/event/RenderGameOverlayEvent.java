//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.*;

public class RenderGameOverlayEvent extends Event
{
    private static ListenerList listenerList;
    public final float partialTicks;
    
    public RenderGameOverlayEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    @Override
    public ListenerList getListenerList() {
        return RenderGameOverlayEvent.listenerList;
    }
    
    static {
        RenderGameOverlayEvent.listenerList = new ListenerList();
    }
}
