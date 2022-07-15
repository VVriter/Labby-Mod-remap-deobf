//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.relauncher;

import java.util.*;

public interface IFMLLoadingPlugin
{
    String[] getASMTransformerClass();
    
    String getModContainerClass();
    
    String getSetupClass();
    
    void injectData(final Map<String, Object> p0);
    
    String getAccessTransformerClass();
}
