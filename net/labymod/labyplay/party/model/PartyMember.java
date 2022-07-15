//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.party.model;

import java.util.*;
import java.beans.*;

public class PartyMember
{
    private String name;
    private UUID uuid;
    private boolean owner;
    private boolean member;
    private long timestamp;
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public boolean isOwner() {
        return this.owner;
    }
    
    public boolean isMember() {
        return this.member;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    @ConstructorProperties({ "name", "uuid", "owner", "member", "timestamp" })
    public PartyMember(final String name, final UUID uuid, final boolean owner, final boolean member, final long timestamp) {
        this.name = name;
        this.uuid = uuid;
        this.owner = owner;
        this.member = member;
        this.timestamp = timestamp;
    }
}
