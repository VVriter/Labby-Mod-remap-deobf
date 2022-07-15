//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.common.eventhandler;

import java.util.*;
import java.lang.reflect.*;
import org.objectweb.asm.*;
import com.google.common.collect.*;

public class ASMEventHandler implements IEventListener
{
    private static int IDs;
    private static final String HANDLER_DESC;
    private static final String HANDLER_FUNC_DESC;
    private static final ASMClassLoader LOADER;
    private static final HashMap<Method, Class<?>> cache;
    private final IEventListener handler;
    private final SubscribeEvent subInfo;
    private String readable;
    
    public ASMEventHandler(final Object target, final Method method) throws Exception {
        this.handler = (IEventListener)this.createWrapper(method).getConstructor(Object.class).newInstance(target);
        this.subInfo = method.getAnnotation(SubscribeEvent.class);
        this.readable = "ASM: " + target + " " + method.getName() + Type.getMethodDescriptor(method);
    }
    
    @Override
    public void invoke(final Event event) {
        if (this.handler != null && (!event.isCancelable() || !event.isCanceled() || this.subInfo.receiveCanceled())) {
            this.handler.invoke(event);
        }
    }
    
    public EventPriority getPriority() {
        return this.subInfo.priority();
    }
    
    public Class<?> createWrapper(final Method callback) {
        if (ASMEventHandler.cache.containsKey(callback)) {
            return ASMEventHandler.cache.get(callback);
        }
        final ClassWriter cw = new ClassWriter(0);
        final String name = this.getUniqueName(callback);
        final String desc = name.replace('.', '/');
        final String instType = Type.getInternalName((Class)callback.getDeclaringClass());
        final String eventType = Type.getInternalName((Class)callback.getParameterTypes()[0]);
        cw.visit(50, 33, desc, (String)null, "java/lang/Object", new String[] { ASMEventHandler.HANDLER_DESC });
        cw.visitSource(".dynamic", (String)null);
        cw.visitField(1, "instance", "Ljava/lang/Object;", (String)null, (Object)null).visitEnd();
        MethodVisitor mv = cw.visitMethod(1, "<init>", "(Ljava/lang/Object;)V", (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(25, 1);
        mv.visitFieldInsn(181, desc, "instance", "Ljava/lang/Object;");
        mv.visitInsn(177);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        mv = cw.visitMethod(1, "invoke", ASMEventHandler.HANDLER_FUNC_DESC, (String)null, (String[])null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, desc, "instance", "Ljava/lang/Object;");
        mv.visitTypeInsn(192, instType);
        mv.visitVarInsn(25, 1);
        mv.visitTypeInsn(192, eventType);
        mv.visitMethodInsn(182, instType, callback.getName(), Type.getMethodDescriptor(callback), false);
        mv.visitInsn(177);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        cw.visitEnd();
        final Class<?> ret = ASMEventHandler.LOADER.define(name, cw.toByteArray());
        ASMEventHandler.cache.put(callback, ret);
        return ret;
    }
    
    private String getUniqueName(final Method callback) {
        return String.format("%s_%d_%s_%s_%s", this.getClass().getName(), ASMEventHandler.IDs++, callback.getDeclaringClass().getSimpleName(), callback.getName(), callback.getParameterTypes()[0].getSimpleName());
    }
    
    @Override
    public String toString() {
        return this.readable;
    }
    
    static {
        ASMEventHandler.IDs = 0;
        HANDLER_DESC = Type.getInternalName((Class)IEventListener.class);
        HANDLER_FUNC_DESC = Type.getMethodDescriptor(IEventListener.class.getDeclaredMethods()[0]);
        LOADER = new ASMClassLoader();
        cache = Maps.newHashMap();
    }
    
    private static class ASMClassLoader extends ClassLoader
    {
        private ASMClassLoader() {
            super(ASMClassLoader.class.getClassLoader());
        }
        
        public Class<?> define(final String name, final byte[] data) {
            return this.defineClass(name, data, 0, data.length);
        }
    }
}
