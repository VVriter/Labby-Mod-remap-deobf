//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.*;

public abstract class GuiScreenEvent extends Event
{
    public static class DrawScreenEvent extends GuiScreenEvent
    {
        private static ListenerList listenerList;
        public int mouseX;
        public int mouseY;
        public float renderPartialTicks;
        
        @Override
        public ListenerList getListenerList() {
            return DrawScreenEvent.listenerList;
        }
        
        public DrawScreenEvent(final int mouseX, final int mouseY, final float renderPartialTicks) {
        }
        
        public DrawScreenEvent() {
        }
        
        static {
            DrawScreenEvent.listenerList = new ListenerList();
        }
        
        public static class Post extends DrawScreenEvent
        {
            private static ListenerList listenerList;
            
            @Override
            public ListenerList getListenerList() {
                return Post.listenerList;
            }
            
            public Post(final int mouseX, final int mouseY, final float renderPartialTicks) {
                super(mouseX, mouseY, renderPartialTicks);
            }
            
            public Post() {
            }
            
            static {
                Post.listenerList = new ListenerList();
            }
        }
    }
}
