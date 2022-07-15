//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.listeners;

import net.labymod.api.events.*;
import net.labymod.discordapp.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.discordapp.api.*;
import net.labymod.support.util.*;
import net.labymod.core.*;
import net.labymod.utils.*;

public class JoinRequestListener implements DiscordEventHandlers.joinRequest_callback, MessageSendEvent
{
    private DiscordApp discordApp;
    
    public JoinRequestListener(final DiscordApp discordApp) {
        this.discordApp = discordApp;
        LabyMod.getInstance().getEventManager().register((MessageSendEvent)this);
    }
    
    public boolean onSend(final String message) {
        if (message.toLowerCase().startsWith("/discordrpc ")) {
            final String[] args = message.split(" ");
            if (args.length >= 3) {
                final boolean accepted = args[1].equalsIgnoreCase("accept");
                this.discordApp.respond(args[2], (int)(accepted ? 1 : 0));
                LabyMod.getInstance().displayMessageInChat(LanguageManager.translate("discordrpc_join_request_accepted"));
            }
            return true;
        }
        return false;
    }
    
    public void apply(final DiscordJoinRequest request) {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Join request: " + request.username + "#" + request.userId);
        LabyModCore.getMinecraft().displayMessageInChatCustomAction(LanguageManager.translate("discordrpc_join_request_chat", request.username), 2, "/discordrpc accept " + request.userId);
        final String imageURL = String.format("https://cdn.discordapp.com/avatars/%s/%s.png", request.userId, request.avatar);
        final String username = ModColor.cl('3') + request.username + ModColor.cl('8') + "#" + ModColor.cl('7') + request.discriminator;
        final String message = ModColor.cl('a') + LanguageManager.translate("discordrpc_join_request_achievement");
        if (LabyMod.getSettings().discordShowAchievements) {
            LabyMod.getInstance().getGuiCustomAchievement().displayAchievement(imageURL, username, message);
        }
    }
}
