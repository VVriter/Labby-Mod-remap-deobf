//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import net.labymod.main.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import java.lang.reflect.*;

public class LavaLightUpdater
{
    public static void update() {
        final boolean value = LabyMod.getSettings().improvedLavaNoLight;
        final aow blockFlowingLava = aow.c(10);
        final aow blockLava = aow.c(11);
        try {
            updateLightField(blockLava, value);
            updateLightField(blockFlowingLava, value);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (LabyMod.getInstance().isInGame()) {
            bib.z().g.a();
        }
    }
    
    private static void updateLightField(final aow block, final boolean value) throws Exception {
        final Field lightValueField = ReflectionHelper.findField(aow.class, LabyModCore.getMappingAdapter().getLightValueMappings());
        lightValueField.setAccessible(true);
        lightValueField.set(block, value ? 0 : 15);
    }
}
