//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util.prediction;

public class JumpVec2f
{
    public static final JumpVec2f ZERO;
    public static final JumpVec2f ONE;
    public static final JumpVec2f UNIT_X;
    public static final JumpVec2f NEGATIVE_UNIT_X;
    public static final JumpVec2f UNIT_Y;
    public static final JumpVec2f NEGATIVE_UNIT_Y;
    public static final JumpVec2f MAX;
    public static final JumpVec2f MIN;
    public final float x;
    public final float y;
    
    public JumpVec2f(final float xIn, final float yIn) {
        this.x = xIn;
        this.y = yIn;
    }
    
    static {
        ZERO = new JumpVec2f(0.0f, 0.0f);
        ONE = new JumpVec2f(1.0f, 1.0f);
        UNIT_X = new JumpVec2f(1.0f, 0.0f);
        NEGATIVE_UNIT_X = new JumpVec2f(-1.0f, 0.0f);
        UNIT_Y = new JumpVec2f(0.0f, 1.0f);
        NEGATIVE_UNIT_Y = new JumpVec2f(0.0f, -1.0f);
        MAX = new JumpVec2f(Float.MAX_VALUE, Float.MAX_VALUE);
        MIN = new JumpVec2f(Float.MIN_VALUE, Float.MIN_VALUE);
    }
}
