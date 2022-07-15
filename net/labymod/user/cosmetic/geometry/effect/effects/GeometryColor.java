//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public class GeometryColor extends GeometryEffect
{
    private int index;
    
    public GeometryColor(final String name, final GeometryModelRenderer model) {
        super(name, model);
        this.index = 0;
    }
    
    @Override
    protected boolean parse() {
        this.index = Integer.parseInt(this.getParameter(0));
        return true;
    }
    
    @Override
    protected int getParametersAmount() {
        return 1;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        if (remoteData.colors.length > this.index) {
            this.model.color = remoteData.colors[this.index];
        }
    }
    
    public int getIndex() {
        return this.index;
    }
}
