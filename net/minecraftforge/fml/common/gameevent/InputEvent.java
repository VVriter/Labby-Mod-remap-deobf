//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.gameevent;

import net.minecraftforge.fml.common.eventhandler.*;

public abstract class InputEvent extends Event
{
    public static class KeyInputEvent extends InputEvent
    {
        private static ListenerList listenerList;
        
        public ListenerList getListenerList() {
            return KeyInputEvent.listenerList;
        }
        
        static {
            KeyInputEvent.listenerList = new ListenerList();
        }
    }
    
    public static class MouseInputEvent extends InputEvent
    {
        private static ListenerList listenerList;
        
        public ListenerList getListenerList() {
            return MouseInputEvent.listenerList;
        }
        
        static {
            MouseInputEvent.listenerList = new ListenerList();
        }
    }
}
