//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.layer;

import net.labymod.main.*;

public class LayerBipedArmorCustom extends ccb
{
    public LayerBipedArmorCustom(final caa<?> rendererIn) {
        super((caa)rendererIn);
    }
    
    public boolean a() {
        return LabyMod.getSettings().oldDamage;
    }
}
