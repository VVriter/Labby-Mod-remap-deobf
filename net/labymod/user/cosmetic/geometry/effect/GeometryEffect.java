//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect;

import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public abstract class GeometryEffect
{
    protected final GeometryModelRenderer model;
    protected final String[] args;
    
    public GeometryEffect(final String name, final GeometryModelRenderer model) {
        this.args = name.split("_");
        this.model = model;
    }
    
    public GeometryEffect load() {
        return (this.args.length >= this.getParametersAmount()) ? (this.parse() ? this : null) : null;
    }
    
    protected abstract boolean parse();
    
    protected abstract int getParametersAmount();
    
    protected String getParameter(final int index) {
        return this.args[index + 1];
    }
    
    protected boolean hasParameter(final int index) {
        return this.args.length > index + 1;
    }
    
    protected String getParameter(final int index, final int requiredLength) {
        final String value = this.args[index + 1];
        return (value.length() == requiredLength) ? value : null;
    }
    
    public boolean onCubeAdd(final GeometryModelRenderer target, final float x, final float y, final float z, final int sizeX, final int sizeY, final int sizeZ, final float inflate, final boolean mirror) {
        return true;
    }
    
    public abstract void apply(final RemoteData p0, final MetaEffectFrameParameter p1);
    
    public GeometryModelRenderer getModel() {
        return this.model;
    }
    
    public String[] getArgs() {
        return this.args;
    }
}
