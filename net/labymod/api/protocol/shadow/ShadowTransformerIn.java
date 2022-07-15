//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.shadow;

import io.netty.channel.*;
import java.beans.*;

public class ShadowTransformerIn extends ChannelInboundHandlerAdapter
{
    private ShadowProtocol shadow;
    
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (!this.shadow.isShadowSupported()) {
            super.channelRead(ctx, msg);
            return;
        }
        this.shadow.increaseCounter();
        super.channelRead(ctx, msg);
    }
    
    @ConstructorProperties({ "shadow" })
    public ShadowTransformerIn(final ShadowProtocol shadow) {
        this.shadow = shadow;
    }
}
