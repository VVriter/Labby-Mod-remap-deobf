//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core.*;

public class CoreImplementation implements CoreAdapter
{
    private ForgeAdapter forgeImpl;
    private MathAdapter mathImpl;
    private MinecraftAdapter minecraftImpl;
    private RenderAdapter renderImpl;
    private RenderPlayerAdapter renderPlayerImpl;
    private SoundAdapter soundImpl;
    private WorldRendererAdapter worldRendererImpl;
    private MappingAdapter mappingImpl;
    private ServerPingerAdapter serverPingerImpl;
    private ProtocolImplementation chunkImpl;
    
    public CoreImplementation() {
        this.forgeImpl = (ForgeAdapter)new ForgeImplementation();
        this.mathImpl = (MathAdapter)new MathImplementation();
        this.minecraftImpl = (MinecraftAdapter)new MinecraftImplementation();
        this.renderImpl = (RenderAdapter)new RenderImplementation();
        this.renderPlayerImpl = (RenderPlayerAdapter)new RenderPlayerImplementation();
        this.soundImpl = (SoundAdapter)new SoundImplementation();
        this.worldRendererImpl = (WorldRendererAdapter)new WorldRendererImplementation();
        this.mappingImpl = (MappingAdapter)new MappingImplementation();
        this.serverPingerImpl = (ServerPingerAdapter)new ServerPingerImplementation();
        this.chunkImpl = new ProtocolImplementation();
    }
    
    public ForgeAdapter getForgeImplementation() {
        return this.forgeImpl;
    }
    
    public MathAdapter getMathImplementation() {
        return this.mathImpl;
    }
    
    public MinecraftAdapter getMinecraftImplementation() {
        return this.minecraftImpl;
    }
    
    public RenderAdapter getRenderImplementation() {
        return this.renderImpl;
    }
    
    public RenderPlayerAdapter getRenderPlayerImplementation() {
        return this.renderPlayerImpl;
    }
    
    public SoundAdapter getSoundImplementation() {
        return this.soundImpl;
    }
    
    public WorldRendererAdapter getWorldRendererImplementation() {
        return this.worldRendererImpl;
    }
    
    public MappingAdapter getMappingAdapter() {
        return this.mappingImpl;
    }
    
    public ServerPingerAdapter getServerPingerImplementation() {
        return this.serverPingerImpl;
    }
    
    public ProtocolAdapter getProtocolAdapter() {
        return (ProtocolAdapter)this.chunkImpl;
    }
}
