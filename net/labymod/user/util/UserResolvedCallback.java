//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.util;

import java.util.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.user.group.*;

public interface UserResolvedCallback
{
    void resolvedCosmetics(final Map<Integer, CosmeticData> p0);
    
    void resolvedGroup(final LabyGroup p0);
    
    void complete();
    
    void resolvedDailyEmoteFlat(final boolean p0);
}
