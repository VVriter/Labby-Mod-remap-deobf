//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.listener;

import net.labymod.utils.*;
import net.labymod.main.*;
import java.lang.reflect.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import com.mojang.authlib.*;
import java.util.*;

public class ServerIncomingPacketListener implements Consumer<Object>
{
    private LabyMod labymod;
    private Field addPlayerDataProfileField;
    
    public ServerIncomingPacketListener(final LabyMod labymod) {
        this.addPlayerDataProfileField = null;
        this.labymod = labymod;
    }
    
    public void register() {
        this.labymod.getLabyModAPI().getEventManager().registerOnIncomingPacket((Consumer)this);
    }
    
    @Override
    public void accept(final Object packetObject) {
        if (packetObject instanceof jp) {
            final jp packet = (jp)packetObject;
            if (LabyMod.getSettings().revealFamiliarUsers && (packet.b() == jp.a.a || packet.b() == jp.a.e)) {
                final List<?> list = (List<?>)packet.a();
                try {
                    for (final Object obj : list) {
                        if (this.addPlayerDataProfileField == null) {
                            (this.addPlayerDataProfileField = ReflectionHelper.findField(obj.getClass(), LabyModCore.getMappingAdapter().getAddPlayerDataProfileMappings())).setAccessible(true);
                        }
                        final GameProfile gameProfile = (GameProfile)this.addPlayerDataProfileField.get(obj);
                        if (gameProfile != null && packet.b() == jp.a.e) {
                            this.labymod.getUserManager().getUser(gameProfile.getId()).setFamiliar(false);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
