//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.party.model;

public final class PartyActionTypes
{
    public enum Client
    {
        INVITE_PLAYER, 
        INVITE_PLAYER_RESPONSE, 
        CHAT, 
        LEAVE_PARTY, 
        KICK_PLAYER, 
        CHANGE_OWNER;
        
        public String getKey() {
            return this.name().toLowerCase();
        }
        
        public static Client getByKey(final String key) {
            for (final Client type : values()) {
                if (type.getKey().equalsIgnoreCase(key)) {
                    return type;
                }
            }
            return null;
        }
    }
    
    public enum Server
    {
        INVITED_PLAYER, 
        INVITE_SUCCESS, 
        CHAT, 
        SYSTEM_MESSAGE, 
        YOU_LEFT, 
        MEMBER_LIST;
        
        public String getKey() {
            return this.name().toLowerCase();
        }
        
        public static Server getByKey(final String key) {
            for (final Server type : values()) {
                if (type.getKey().equalsIgnoreCase(key)) {
                    return type;
                }
            }
            return null;
        }
    }
    
    public enum Message
    {
        UNKNOWN, 
        PARTY_FULL, 
        PLAYER_NOT_FOUND, 
        PLAYER_IN_OTHER_PARTY, 
        PLAYER_INVITED, 
        PLAYER_LEFT, 
        PLAYER_JOINED, 
        PLAYER_INVITE_REJECTED, 
        PLAYER_KICKED, 
        NEW_OWNER_NOT_IN_PARTY, 
        YOU_ARE_NOT_THE_OWNER, 
        PARTY_DOES_NOT_EXIST, 
        NO_INVITE;
        
        public String getKey() {
            return this.name().toLowerCase();
        }
        
        public static Message getByKey(final String key) {
            for (final Message type : values()) {
                if (type.getKey().equalsIgnoreCase(key)) {
                    return type;
                }
            }
            return Message.UNKNOWN;
        }
    }
}
