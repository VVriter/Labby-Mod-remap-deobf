//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import net.minecraftforge.client.event.*;

public interface ForgeAdapter
{
    blk getGuiOpenEventGui(final GuiOpenEvent p0);
    
    void setGuiOpenEventGui(final GuiOpenEvent p0, final blk p1);
    
    void setNewFov(final FOVUpdateEvent p0, final float p1);
}
