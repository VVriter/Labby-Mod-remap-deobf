//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet.ai;

import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.pet.*;

public interface PetAI<T>
{
    void preRender(final ModelCosmetics p0, final vg p1, final PetStorage<T> p2, final float p3, final float p4, final float p5);
    
    void onTick(final ModelCosmetics p0, final aed p1, final PetStorage<T> p2, final float p3, final float p4, final float p5, final float p6);
    
    boolean canAttach();
    
    void renderFirstPerson(final aed p0, final float p1);
    
    void firstPersonTransform(final vg p0, final PetStorage<T> p1, final boolean p2, final float p3);
}
