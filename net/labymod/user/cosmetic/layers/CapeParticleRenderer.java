//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.layers;

import net.labymod.main.*;
import net.labymod.user.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class CapeParticleRenderer
{
    private Random random;
    private List<ParticleStar> starList;
    private long lastStarSpawned;
    
    public CapeParticleRenderer() {
        this.random = LabyMod.getRandom();
        this.starList = new LinkedList<ParticleStar>();
        this.lastStarSpawned = -1L;
    }
    
    public void render(final User user, final bua entitylivingbaseIn, final float partialTicks) {
        if (!LabyMod.getSettings().capeOriginalParticles || user.isMojangCapeModified()) {
            return;
        }
        if (this.lastStarSpawned < System.currentTimeMillis()) {
            this.lastStarSpawned = System.currentTimeMillis() + 200L;
            final double x = (0.5 - this.random.nextDouble()) * 0.5;
            final double y = this.random.nextDouble() - 0.025;
            final ParticleStar particleStar = new ParticleStar(x, y, System.currentTimeMillis());
            this.starList.add(particleStar);
        }
        bus.G();
        bus.b(0.0, 0.0, -0.07);
        bus.m();
        bus.e();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final Iterator<ParticleStar> iterator = this.starList.iterator();
        while (iterator.hasNext()) {
            final ParticleStar next = iterator.next();
            next.render();
            if (next.isFadedOut()) {
                iterator.remove();
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bus.H();
    }
}
