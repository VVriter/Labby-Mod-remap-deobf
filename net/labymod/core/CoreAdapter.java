//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

public interface CoreAdapter
{
    ForgeAdapter getForgeImplementation();
    
    MathAdapter getMathImplementation();
    
    MinecraftAdapter getMinecraftImplementation();
    
    RenderAdapter getRenderImplementation();
    
    RenderPlayerAdapter getRenderPlayerImplementation();
    
    SoundAdapter getSoundImplementation();
    
    WorldRendererAdapter getWorldRendererImplementation();
    
    MappingAdapter getMappingAdapter();
    
    ServerPingerAdapter getServerPingerImplementation();
    
    ProtocolAdapter getProtocolAdapter();
}
