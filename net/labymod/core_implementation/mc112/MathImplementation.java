//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core.*;

public class MathImplementation implements MathAdapter
{
    public int clamp_int(final int num, final int min, final int max) {
        return rk.a(num, min, max);
    }
    
    public int floor_float(final float value) {
        return rk.d(value);
    }
    
    public int ceiling_float_int(final float value) {
        return rk.f(value);
    }
    
    public int ceiling_double_int(final double value) {
        return rk.f(value);
    }
    
    public float sin(final float value) {
        return rk.a(value);
    }
    
    public float cos(final float value) {
        return rk.b(value);
    }
    
    public float clamp_float(final float num, final float min, final float max) {
        return rk.a(num, min, max);
    }
    
    public float sqrt_float(final float value) {
        return rk.c(value);
    }
    
    public float abs(final float value) {
        return rk.e(value);
    }
    
    public int hsvToRGB(final float r, final float g, final float b) {
        return rk.c(r, g, b);
    }
    
    public float wrapAngleTo180_float(final float value) {
        return rk.g(value);
    }
    
    public int floor_double(final double value) {
        return rk.c(value);
    }
    
    public double clamp_double(final double num, final double min, final double max) {
        return rk.a(num, min, max);
    }
}
