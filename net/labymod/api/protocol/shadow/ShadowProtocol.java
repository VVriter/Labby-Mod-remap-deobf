//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.shadow;

import net.labymod.utils.*;
import net.labymod.api.events.*;
import java.util.concurrent.atomic.*;
import net.labymod.main.*;
import net.labymod.api.*;
import io.netty.channel.*;

public class ShadowProtocol implements Consumer<ServerData>, PluginMessageEvent
{
    public static final String PM_CHANNEL = "labymod3:shadow";
    public static final int SHADOW_VERSION = 1;
    private boolean shadowSupported;
    private AtomicInteger packetCounter;
    
    public ShadowProtocol() {
        this.shadowSupported = false;
        this.packetCounter = new AtomicInteger(0);
        final EventManager eventManager = LabyMod.getInstance().getEventManager();
        eventManager.registerOnQuit((Consumer)this);
        eventManager.register((PluginMessageEvent)this);
    }
    
    @Override
    public void accept(final ServerData accepted) {
        this.shadowSupported = false;
    }
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (channelName.equals("labymod3:shadow")) {
            final int packetId = packetBuffer.readInt();
            if (packetId == 0) {
                this.packetCounter.set(0);
            }
            if (packetId == 1) {
                try {
                    final Channel channel = LabyMod.getInstance().getNettyChannel();
                    final ChannelPipeline pipeline = channel.pipeline();
                    if (pipeline.context("labytransformerin") == null) {
                        pipeline.addAfter("decoder", "labytransformerin", (ChannelHandler)new ShadowTransformerIn(this));
                        pipeline.addAfter("encoder", "labytransformerout", (ChannelHandler)new ShadowTransformerOut(this));
                    }
                    this.shadowSupported = true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (packetId == 2) {
                this.shadowSupported = false;
            }
        }
    }
    
    public void increaseCounter() {
        this.packetCounter.incrementAndGet();
    }
    
    public boolean isShadowSupported() {
        return this.shadowSupported;
    }
    
    public AtomicInteger getPacketCounter() {
        return this.packetCounter;
    }
}
