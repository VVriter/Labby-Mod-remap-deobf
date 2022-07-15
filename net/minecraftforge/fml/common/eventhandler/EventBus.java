//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.eventhandler;

import java.util.concurrent.*;
import com.google.common.reflect.*;
import java.lang.annotation.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.common.base.*;

public class EventBus
{
    private static int maxID;
    private ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners;
    private final int busID;
    
    public EventBus() {
        this.listeners = new ConcurrentHashMap<Object, ArrayList<IEventListener>>();
        this.busID = EventBus.maxID++;
        ListenerList.resize(this.busID + 1);
    }
    
    public void register(final Object target) {
        if (this.listeners.containsKey(target)) {
            return;
        }
        final Set<? extends Class<?>> supers = (Set<? extends Class<?>>)TypeToken.of((Class)target.getClass()).getTypes().rawTypes();
        for (final Method method : target.getClass().getMethods()) {
            for (final Class<?> cls : supers) {
                try {
                    final Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (!real.isAnnotationPresent(SubscribeEvent.class)) {
                        continue;
                    }
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods must require a single argument.");
                    }
                    final Class<?> eventType = parameterTypes[0];
                    if (!Event.class.isAssignableFrom(eventType)) {
                        throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes a argument that is not an Event " + eventType);
                    }
                    this.register(eventType, target, real);
                    break;
                }
                catch (NoSuchMethodException ex) {}
            }
        }
    }
    
    private void register(final Class<?> eventType, final Object target, final Method method) {
        try {
            final Constructor<?> ctr = eventType.getConstructor((Class<?>[])new Class[0]);
            ctr.setAccessible(true);
            final Event event = (Event)ctr.newInstance(new Object[0]);
            final ASMEventHandler listener = new ASMEventHandler(target, method);
            event.getListenerList().register(this.busID, listener.getPriority(), (IEventListener)listener);
            ArrayList<IEventListener> others = this.listeners.get(target);
            if (others == null) {
                others = new ArrayList<IEventListener>();
                this.listeners.put(target, others);
            }
            others.add((IEventListener)listener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void unregister(final Object object) {
        final ArrayList<IEventListener> list = this.listeners.remove(object);
        if (list == null) {
            return;
        }
        for (final IEventListener listener : list) {
            ListenerList.unregisterAll(this.busID, listener);
        }
    }
    
    public boolean post(final Event event) {
        final IEventListener[] listeners = event.getListenerList().getListeners(this.busID);
        int index = 0;
        try {
            while (index < listeners.length) {
                listeners[index].invoke(event);
                ++index;
            }
        }
        catch (Throwable throwable) {
            Throwables.propagate(throwable);
        }
        return event.isCancelable() && event.isCanceled();
    }
    
    static {
        EventBus.maxID = 0;
    }
}
