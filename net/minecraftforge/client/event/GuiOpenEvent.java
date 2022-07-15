//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.*;

public class GuiOpenEvent extends Event
{
    private static ListenerList listenerList;
    
    @Override
    public ListenerList getListenerList() {
        return GuiOpenEvent.listenerList;
    }
    
    @Override
    public boolean isCancelable() {
        return true;
    }
    
    static {
        GuiOpenEvent.listenerList = new ListenerList();
    }
}
