//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.party.model;

import java.util.*;

public interface PartyListener
{
    void onInvitedPlayer(final String p0, final UUID p1, final String p2);
    
    void onInviteSuccess(final String p0, final UUID p1);
    
    void onChatMessage(final String p0, final String p1);
    
    void onSystemMessage(final PartyActionTypes.Message p0, final String[] p1);
    
    void onPartyLeft(final UUID p0);
    
    void onMemberList(final UUID p0, final PartyMember[] p1);
}
