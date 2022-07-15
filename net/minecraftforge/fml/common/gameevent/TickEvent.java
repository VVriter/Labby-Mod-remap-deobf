//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.gameevent;

import net.minecraftforge.fml.common.eventhandler.*;

public abstract class TickEvent extends Event
{
    public final Phase phase;
    
    public TickEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public enum Phase
    {
        START, 
        END;
    }
    
    public static class ClientTickEvent extends TickEvent
    {
        private static ListenerList listenerList;
        
        public ClientTickEvent() {
            super(Phase.END);
        }
        
        public ListenerList getListenerList() {
            return ClientTickEvent.listenerList;
        }
        
        static {
            ClientTickEvent.listenerList = new ListenerList();
        }
    }
    
    public static class RenderTickEvent extends TickEvent
    {
        private static ListenerList listenerList;
        public final float renderTickTime;
        
        public RenderTickEvent(final float renderTickTime) {
            super(Phase.START);
            this.renderTickTime = renderTickTime;
        }
        
        public RenderTickEvent() {
            super(Phase.START);
            this.renderTickTime = 0.0f;
        }
        
        public ListenerList getListenerList() {
            return RenderTickEvent.listenerList;
        }
        
        static {
            RenderTickEvent.listenerList = new ListenerList();
        }
    }
}
