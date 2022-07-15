//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.party.model;

import java.util.*;
import java.beans.*;

public class PartyInvite
{
    private String username;
    private UUID partyUUID;
    private String partyName;
    
    public String getUsername() {
        return this.username;
    }
    
    public UUID getPartyUUID() {
        return this.partyUUID;
    }
    
    public String getPartyName() {
        return this.partyName;
    }
    
    @ConstructorProperties({ "username", "partyUUID", "partyName" })
    public PartyInvite(final String username, final UUID partyUUID, final String partyName) {
        this.username = username;
        this.partyUUID = partyUUID;
        this.partyName = partyName;
    }
}
