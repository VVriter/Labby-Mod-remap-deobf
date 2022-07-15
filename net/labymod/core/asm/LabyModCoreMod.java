//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.launchwrapper.*;
import net.labymod.addon.*;
import java.net.*;
import java.util.*;
import java.io.*;
import net.labymod.support.util.*;

public class LabyModCoreMod implements IFMLLoadingPlugin
{
    private static boolean obfuscated;
    private static boolean forge;
    
    @Override
    public String[] getASMTransformerClass() {
        for (final StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().contains("FMLPluginWrapper")) {
                LabyModCoreMod.forge = true;
                break;
            }
        }
        final List<String> transformers = new ArrayList<String>();
        transformers.add("net.labymod.core.asm.LabyModTransformer");
        if (LabyModCoreMod.forge) {
            transformers.addAll(AddonLoader.getTransformerClasses((URLClassLoader)Launch.classLoader));
        }
        else {
            transformers.addAll(AddonLoader.getTransformerClasses((URLClassLoader)this.getClass().getClassLoader()));
        }
        return transformers.toArray(new String[transformers.size()]);
    }
    
    @Override
    public String getModContainerClass() {
        return null;
    }
    
    @Override
    public String getSetupClass() {
        return null;
    }
    
    @Override
    public void injectData(final Map<String, Object> data) {
        LabyModCoreMod.obfuscated = data.get("runtimeDeobfuscationEnabled");
        LabyModCoreMod.forge = true;
    }
    
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
    
    public static boolean isObfuscated() {
        return LabyModCoreMod.obfuscated;
    }
    
    public static void setObfuscated(final boolean obfuscated) {
        LabyModCoreMod.obfuscated = obfuscated;
    }
    
    public static boolean isForge() {
        return LabyModCoreMod.forge;
    }
    
    public static void setForge(final boolean forge) {
        LabyModCoreMod.forge = forge;
    }
    
    static {
        System.setErr(new CapturePrintStream(System.err));
        System.setOut(new CapturePrintStream(System.out));
        System.setProperty("java.net.preferIPv4Stack", "true");
        Debug.init();
    }
}
