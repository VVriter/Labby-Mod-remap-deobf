//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.eventhandler;

import com.google.common.base.*;

public abstract class Event
{
    private boolean isCanceled;
    private EventPriority phase;
    
    public Event() {
        this.isCanceled = false;
        this.phase = null;
    }
    
    public boolean isCancelable() {
        return false;
    }
    
    public boolean isCanceled() {
        return this.isCanceled;
    }
    
    public void setCanceled(final boolean cancel) {
        if (!this.isCancelable()) {
            throw new IllegalArgumentException("Attempted to cancel a uncancelable event");
        }
        this.isCanceled = cancel;
    }
    
    public abstract ListenerList getListenerList();
    
    public EventPriority getPhase() {
        return this.phase;
    }
    
    public void setPhase(final EventPriority value) {
        Preconditions.checkArgument(value != null, (Object)"setPhase argument must not be null");
        final int prev = (this.phase == null) ? -1 : this.phase.ordinal();
        Preconditions.checkArgument(prev < value.ordinal(), "Attempted to set event phase to %s when already %s", new Object[] { value, this.phase });
        this.phase = value;
    }
}
