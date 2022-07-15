//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tools.autotext;

import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.main.*;
import net.labymod.api.permissions.*;
import net.labymod.api.events.*;
import net.labymod.core.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoTextListener
{
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (bib.z().m != null) {
            return;
        }
        if (!LabyMod.getInstance().getServerManager().isAllowed(Permissions.Permission.CHAT)) {
            return;
        }
        for (final AutoTextKeyBinds.AutoText keybind : LabyMod.getInstance().getChatToolManager().getAutoTextKeyBinds()) {
            if (keybind.isAvailable() && keybind.isPressed()) {
                keybind.setAvailable(false);
                if (!keybind.isSendNotInstantly()) {
                    boolean cancelled = false;
                    for (final MessageSendEvent messageSend : LabyMod.getInstance().getEventManager().getMessageSend()) {
                        if (messageSend.onSend(keybind.getMessage()) && !cancelled) {
                            cancelled = true;
                        }
                    }
                    if (!cancelled) {
                        LabyModCore.getMinecraft().getPlayer().g(keybind.getMessage());
                    }
                }
                else {
                    bib.z().a((blk)new bkn(keybind.getMessage()));
                }
            }
            if (!keybind.isPressed() && !keybind.isAvailable()) {
                keybind.setAvailable(true);
            }
        }
    }
}
