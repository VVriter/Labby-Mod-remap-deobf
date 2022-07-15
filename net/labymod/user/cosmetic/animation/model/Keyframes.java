//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation.model;

import java.util.*;

public class Keyframes
{
    private List<KeyframeVector> keyframes;
    private final float defaultX;
    private final float defaultY;
    private final float defaultZ;
    
    public Keyframes(final float defaultX, final float defaultY, final float defaultZ) {
        this.keyframes = new ArrayList<KeyframeVector>();
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.defaultZ = defaultZ;
    }
    
    public KeyframeVector get(final long offset) {
        KeyframeVector from = new KeyframeVector(0L, this.defaultX, this.defaultY, this.defaultZ, false);
        for (int i = 0; i < this.keyframes.size(); ++i) {
            final KeyframeVector current = this.keyframes.get(i);
            if (current.offset >= offset) {
                return this.process(from, current, offset, current.smooth);
            }
            from = current;
        }
        return from;
    }
    
    public long getLength() {
        return this.keyframes.isEmpty() ? 0L : this.keyframes.get(this.keyframes.size() - 1).offset;
    }
    
    public void add(final long offset, final KeyframeVector vector) {
        this.keyframes.add(vector);
    }
    
    public void add(final long offset, final double x, final double y, final double z, final boolean smooth) {
        this.keyframes.add(new KeyframeVector(offset, x, y, z, smooth));
    }
    
    private KeyframeVector process(final KeyframeVector from, final KeyframeVector to, final long offset, final boolean interpolate) {
        final long progress = offset - from.offset;
        final long duration = to.offset - from.offset;
        final KeyframeVector vector = new KeyframeVector(offset);
        vector.x = process(from.x, to.x, progress, duration, interpolate);
        vector.y = process(from.y, to.y, progress, duration, interpolate);
        vector.z = process(from.z, to.z, progress, duration, interpolate);
        return vector;
    }
    
    public static double process(final double x, final double x2, final long progress, final long duration, final boolean interpolate) {
        if (x == x2 || duration == 0L || progress > duration) {
            return x2;
        }
        if (interpolate) {
            return interpolate(x, x2, (double)progress, (double)duration);
        }
        return linear(x, x2, progress, duration);
    }
    
    public static double interpolate(final double startY, final double endY, final double currentTime, final double endTime) {
        if (startY == endY || endTime == 0.0 || currentTime > endTime) {
            return endY;
        }
        return startY + sigmoid(currentTime / endTime * 4.0) * (endY - startY);
    }
    
    public static double linear(final double root, final double target, final long progress, final long animationDuration) {
        if (root == target || animationDuration == 0L || progress > animationDuration) {
            return target;
        }
        final double difference = root - target;
        return root - difference / animationDuration * progress;
    }
    
    private static double sigmoid(final double input) {
        return 1.0 / (1.0 + Math.exp(-input * 2.0 + 4.0));
    }
}
