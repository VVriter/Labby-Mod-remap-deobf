//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public class GeometryGlow extends GeometryEffect
{
    public GeometryGlow(final String name, final GeometryModelRenderer model) {
        super(name, model);
    }
    
    @Override
    protected boolean parse() {
        return true;
    }
    
    @Override
    protected int getParametersAmount() {
        return 0;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        this.model.glow = true;
    }
}
