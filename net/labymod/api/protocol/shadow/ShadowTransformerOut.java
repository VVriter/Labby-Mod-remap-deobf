//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.shadow;

import io.netty.channel.*;
import net.labymod.core.*;
import java.beans.*;

public class ShadowTransformerOut extends ChannelOutboundHandlerAdapter
{
    private ShadowProtocol shadow;
    
    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
        if (!this.shadow.isShadowSupported()) {
            super.write(ctx, msg, promise);
            return;
        }
        if (LabyModCore.getCoreAdapter().getProtocolAdapter().handleOutgoingPacket(msg, this.shadow)) {
            return;
        }
        super.write(ctx, msg, promise);
    }
    
    @ConstructorProperties({ "shadow" })
    public ShadowTransformerOut(final ShadowProtocol shadow) {
        this.shadow = shadow;
    }
}
