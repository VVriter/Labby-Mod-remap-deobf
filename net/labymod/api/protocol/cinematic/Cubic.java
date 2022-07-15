//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.cinematic;

import java.beans.*;

public class Cubic
{
    private double a;
    private double b;
    private double c;
    private double d;
    
    public double eval(final double u) {
        return ((this.d * u + this.c) * u + this.b) * u + this.a;
    }
    
    @ConstructorProperties({ "a", "b", "c", "d" })
    public Cubic(final double a, final double b, final double c, final double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
