//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge;

import net.minecraft.launchwrapper.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class LabyModTweaker implements ITweaker
{
    private static LabyModTweaker instance;
    private LaunchClassLoader launchClassLoader;
    private List<String> arguments;
    private File gameDirectory;
    private File assetsDirectory;
    private String version;
    private boolean obfuscated;
    private static final String coreModClass = "net.labymod.core.asm.LabyModCoreMod";
    
    public void acceptOptions(final List<String> arguments, final File gameDirectory, final File assetsDirectory, final String version) {
        LabyModTweaker.instance = this;
        this.arguments = arguments;
        this.gameDirectory = gameDirectory;
        this.assetsDirectory = assetsDirectory;
        this.version = version;
        System.out.println("[LabyMod-Vanilla] Detected version of Minecraft for VanillaForge: " + this.version);
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader launchClassLoader) {
        this.launchClassLoader = launchClassLoader;
        try {
            Class.forName("net.minecraft.client.Minecraft");
            this.obfuscated = false;
        }
        catch (ClassNotFoundException e) {
            this.obfuscated = true;
        }
        try {
            launchClassLoader.registerTransformer(LabyModTransformer.class.getName());
            final Class labyModCoreModClass = launchClassLoader.loadClass("net.labymod.core.asm.LabyModCoreMod");
            final Object labyModCoreMod = labyModCoreModClass.newInstance();
            labyModCoreModClass.getDeclaredMethod("setObfuscated", Boolean.TYPE).invoke(null, this.obfuscated);
            for (final String transformer : (String[])labyModCoreModClass.getDeclaredMethod("getASMTransformerClass", (Class[])new Class[0]).invoke(labyModCoreMod, new Object[0])) {
                launchClassLoader.registerTransformer(transformer);
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (InstantiationException e5) {
            e5.printStackTrace();
        }
    }
    
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
    
    public String[] getLaunchArguments() {
        final List<String> returnedArguments = new ArrayList<String>();
        returnedArguments.addAll(this.arguments);
        returnedArguments.addAll(Arrays.asList("--version", this.version));
        if (this.gameDirectory != null) {
            returnedArguments.addAll(Arrays.asList("--gameDir", this.gameDirectory.getPath()));
        }
        if (this.assetsDirectory != null) {
            returnedArguments.addAll(Arrays.asList("--assetsDir", this.assetsDirectory.getPath()));
        }
        return returnedArguments.toArray(new String[0]);
    }
    
    public LaunchClassLoader getLaunchClassLoader() {
        return this.launchClassLoader;
    }
    
    public boolean isObfuscated() {
        return this.obfuscated;
    }
    
    public static LabyModTweaker getInstance() {
        return LabyModTweaker.instance;
    }
    
    public static String getVersionStatic() {
        return getInstance().version;
    }
}
