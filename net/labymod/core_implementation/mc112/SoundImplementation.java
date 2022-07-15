//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core.*;

public class SoundImplementation implements SoundAdapter
{
    private nf fireworkTwinkleFar;
    
    public SoundImplementation() {
        this.fireworkTwinkleFar = new nf("entity.firework.twinkle_far");
    }
    
    public void playSignSearchSign(final int x, final int y, final int z) {
        bib.z().U().a((cgt)new cgp((qe)qe.a.c((Object)this.fireworkTwinkleFar), qg.a, 10.0f, 2.0f, (float)x, (float)y, (float)z));
    }
}
