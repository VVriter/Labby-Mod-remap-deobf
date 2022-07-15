//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util.prediction;

import net.labymod.core.*;
import java.util.*;

public class JumpPrediction
{
    public static boolean isJumpPredicted(final double posX, final double posY, final double posZ, final double prevPosX, final double prevPosY, final double prevPosZ, final float rotationYaw, final float rotationPitch, final double minY, final double maxY, final float moveForward, final float moveStrafe, final double width, final double height, final float aiMoveSpeed, final boolean sneaking, final boolean onGround, final boolean riding) {
        final float moveX = (float)(posX - prevPosX);
        final float moveZ = (float)(posZ - prevPosZ);
        if (onGround && !sneaking && !riding) {
            final amu world = (amu)LabyModCore.getMinecraft().getWorld();
            final JumpVec2f vec2f = new JumpVec2f(moveStrafe, moveForward);
            if (vec2f.x != 0.0f || vec2f.y != 0.0f) {
                final JumpVec3d vec3d = new JumpVec3d(posX, minY, posZ);
                final double d0 = posX + moveX;
                final double d2 = posZ + moveZ;
                final JumpVec3d vec3d2 = new JumpVec3d(d0, minY, d2);
                JumpVec3d vec3d3 = new JumpVec3d(moveX, 0.0, moveZ);
                final float f = aiMoveSpeed;
                float f2 = (float)vec3d3.lengthSquared();
                if (f2 <= 0.001f) {
                    final float f3 = f * vec2f.x;
                    final float f4 = f * vec2f.y;
                    final float f5 = rk.a(rotationYaw * 0.017453292f);
                    final float f6 = rk.b(rotationYaw * 0.017453292f);
                    vec3d3 = new JumpVec3d(f3 * f6 - f4 * f5, vec3d3.y, f4 * f6 + f3 * f5);
                    f2 = (float)vec3d3.lengthSquared();
                    if (f2 <= 0.001f) {
                        return false;
                    }
                }
                final float f7 = (float)fastInvSqrt(f2);
                final JumpVec3d vec3d4 = scale(vec3d3, f7);
                final JumpVec3d vec3d5 = getForward(rotationPitch, rotationYaw);
                final float f8 = (float)(vec3d5.x * vec3d4.x + vec3d5.z * vec3d4.z);
                if (f8 >= -0.15f) {
                    et blockpos = new et(posX, maxY, posZ);
                    final awt iblockstate = world.o(blockpos);
                    if (iblockstate.d((amy)world, blockpos) == null) {
                        blockpos = blockpos.a();
                        final awt iblockstate2 = world.o(blockpos);
                        if (iblockstate2.d((amy)world, blockpos) == null) {
                            final float f9 = 1.2f;
                            final float f10 = Math.max(f * 7.0f, 1.0f / f7);
                            JumpVec3d vec3d6 = add(vec3d2, scale(vec3d4, f10));
                            final float f11 = (float)width;
                            final float f12 = (float)height;
                            final bhb axisalignedbb = axisAlignedBB(vec3d, addVector(vec3d6, 0.0, f12, 0.0)).b((double)f11, 0.0, (double)f11);
                            final JumpVec3d lvt_19_1_ = addVector(vec3d, 0.0, 0.5099999904632568, 0.0);
                            vec3d6 = addVector(vec3d6, 0.0, 0.5099999904632568, 0.0);
                            final JumpVec3d vec3d7 = crossProduct(vec3d4, new JumpVec3d(0.0, 1.0, 0.0));
                            final JumpVec3d vec3d8 = scale(vec3d7, f11 * 0.5f);
                            final JumpVec3d vec3d9 = subtract(lvt_19_1_, vec3d8);
                            final JumpVec3d vec3d10 = subtract(vec3d6, vec3d8);
                            final JumpVec3d vec3d11 = add(lvt_19_1_, vec3d8);
                            final JumpVec3d vec3d12 = add(vec3d6, vec3d8);
                            final List<bhb> list = (List<bhb>)world.a((vg)null, axisalignedbb);
                            if (!list.isEmpty()) {}
                            float f13 = Float.MIN_VALUE;
                            for (final bhb axisalignedbb2 : list) {
                                if (intersects(axisalignedbb2, vec3d9, vec3d10) || intersects(axisalignedbb2, vec3d11, vec3d12)) {
                                    f13 = (float)axisalignedbb2.e;
                                    final JumpVec3d vec3d13 = getCenter(axisalignedbb2);
                                    final et blockpos2 = new et(vec3d13.x, vec3d13.y, vec3d13.z);
                                    for (int i = 1; i < f9; ++i) {
                                        final et blockpos3 = blockpos2.b(i);
                                        final awt iblockstate3 = world.o(blockpos3);
                                        final bhb axisalignedbb3;
                                        if ((axisalignedbb3 = iblockstate3.d((amy)world, blockpos3)) != null) {
                                            f13 = (float)axisalignedbb3.e + blockpos3.q();
                                            if (f13 - minY > f9) {
                                                return false;
                                            }
                                        }
                                        if (i > 1) {
                                            blockpos = blockpos.a();
                                            final awt iblockstate4 = world.o(blockpos);
                                            if (iblockstate4.d((amy)world, blockpos) != null) {
                                                return false;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                            if (f13 != Float.MIN_VALUE) {
                                final float f14 = (float)(f13 - minY);
                                if (f14 > 0.5f && f14 <= f9) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static JumpVec3d getCenter(final bhb ax) {
        return new JumpVec3d(ax.a + (ax.d - ax.a) * 0.5, ax.b + (ax.e - ax.b) * 0.5, ax.c + (ax.f - ax.c) * 0.5);
    }
    
    public static boolean intersects(final bhb ax, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return ax.a < x2 && ax.d > x1 && ax.b < y2 && ax.e > y1 && ax.c < z2 && ax.f > z1;
    }
    
    public static boolean intersects(final bhb ax, final JumpVec3d min, final JumpVec3d max) {
        return intersects(ax, Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z), Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }
    
    public static JumpVec3d subtract(final JumpVec3d vec, final JumpVec3d vec2) {
        return subtract(vec2, vec.x, vec.y, vec.z);
    }
    
    public static JumpVec3d subtract(final JumpVec3d vec, final double x, final double y, final double z) {
        return addVector(vec, -x, -y, -z);
    }
    
    public static JumpVec3d crossProduct(final JumpVec3d vec, final JumpVec3d vec2) {
        return new JumpVec3d(vec2.y * vec.z - vec2.z * vec.y, vec2.z * vec.x - vec2.x * vec.z, vec2.x * vec.y - vec2.y * vec.x);
    }
    
    private static bhb axisAlignedBB(final JumpVec3d a, final JumpVec3d b) {
        return new bhb(a.x, a.y, a.z, b.x, b.y, b.z);
    }
    
    public static JumpVec3d add(final JumpVec3d vec1, final JumpVec3d vec) {
        return addVector(vec1, vec.x, vec.y, vec.z);
    }
    
    public static JumpVec3d addVector(final JumpVec3d vec, final double x, final double y, final double z) {
        return new JumpVec3d(vec.x + x, vec.y + y, vec.z + z);
    }
    
    public static JumpVec2f getPitchYaw(final float rotationPitch, final float rotationYaw) {
        return new JumpVec2f(rotationPitch, rotationYaw);
    }
    
    public static JumpVec3d getForward(final float rotationPitch, final float rotationYaw) {
        return fromPitchYawVector(getPitchYaw(rotationPitch, rotationYaw));
    }
    
    public static JumpVec3d fromPitchYawVector(final JumpVec2f p_189984_0_) {
        return fromPitchYaw(p_189984_0_.x, p_189984_0_.y);
    }
    
    public static JumpVec3d fromPitchYaw(final float p_189986_0_, final float p_189986_1_) {
        final float f = rk.b(-p_189986_1_ * 0.017453292f - 3.1415927f);
        final float f2 = rk.a(-p_189986_1_ * 0.017453292f - 3.1415927f);
        final float f3 = -rk.b(-p_189986_0_ * 0.017453292f);
        final float f4 = rk.a(-p_189986_0_ * 0.017453292f);
        return new JumpVec3d(f2 * f3, f4, f * f3);
    }
    
    public static JumpVec3d scale(final JumpVec3d vec, final double factor) {
        return new JumpVec3d(vec.x * factor, vec.y * factor, vec.z * factor);
    }
    
    public static double fastInvSqrt(double p_181161_0_) {
        final double d0 = 0.5 * p_181161_0_;
        long i = Double.doubleToRawLongBits(p_181161_0_);
        i = 6910469410427058090L - (i >> 1);
        p_181161_0_ = Double.longBitsToDouble(i);
        p_181161_0_ *= 1.5 - d0 * p_181161_0_ * p_181161_0_;
        return p_181161_0_;
    }
}
