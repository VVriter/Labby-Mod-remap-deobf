//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core.*;
import net.minecraftforge.client.event.*;

public class ForgeImplementation implements ForgeAdapter
{
    public blk getGuiOpenEventGui(final GuiOpenEvent event) {
        return event.getGui();
    }
    
    public void setGuiOpenEventGui(final GuiOpenEvent event, final blk gui) {
        event.setGui(gui);
    }
    
    public void setNewFov(final FOVUpdateEvent event, final float newFov) {
        event.setNewfov(newFov);
    }
}
