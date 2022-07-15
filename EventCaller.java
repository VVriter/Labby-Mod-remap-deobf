//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraftforge.client.event.*;

public class EventCaller
{
    private EventBus bus;
    
    public EventCaller() {
        this.bus = MinecraftForge.EVENT_BUS;
    }
    
    public void callTick() {
        this.bus.post((Event)new TickEvent.ClientTickEvent());
    }
    
    public void callRenderTick(final float renderTickTime) {
        this.bus.post((Event)new TickEvent.RenderTickEvent(renderTickTime));
    }
    
    public void callKeyInput() {
        this.bus.post((Event)new InputEvent.KeyInputEvent());
    }
    
    public void callMouseInput() {
        this.bus.post((Event)new InputEvent.MouseInputEvent());
    }
    
    public static boolean mouseNext() {
        final boolean next = Mouse.next();
        if (next) {
            instance().callMouseInput();
        }
        return next;
    }
    
    public static float getFovModifier(final Object entityPlayer, final float fov) {
        final FOVUpdateEvent event = new FOVUpdateEvent(fov);
        instance().bus.post((Event)event);
        return event.newfov;
    }
    
    public static EventCaller instance() {
        return LabyMod.instance().getEventCaller();
    }
}
