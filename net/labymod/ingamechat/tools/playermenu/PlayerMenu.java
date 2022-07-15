//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tools.playermenu;

import net.labymod.main.lang.*;
import java.util.*;
import java.beans.*;

public class PlayerMenu
{
    private List<PlayerMenuEntry> playerMenuEntries;
    
    public PlayerMenu() {
        this.playerMenuEntries = new ArrayList<PlayerMenuEntry>(Arrays.asList(new PlayerMenuEntry(LanguageManager.translate("playermenu_entry_partyinvite"), "/party invite {name}", true)));
    }
    
    public List<PlayerMenuEntry> getPlayerMenuEntries() {
        return this.playerMenuEntries;
    }
    
    public static class PlayerMenuEntry
    {
        private String displayName;
        private String command;
        private boolean sendInstantly;
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public String getCommand() {
            return this.command;
        }
        
        public boolean isSendInstantly() {
            return this.sendInstantly;
        }
        
        public void setDisplayName(final String displayName) {
            this.displayName = displayName;
        }
        
        public void setCommand(final String command) {
            this.command = command;
        }
        
        public void setSendInstantly(final boolean sendInstantly) {
            this.sendInstantly = sendInstantly;
        }
        
        @ConstructorProperties({ "displayName", "command", "sendInstantly" })
        public PlayerMenuEntry(final String displayName, final String command, final boolean sendInstantly) {
            this.displayName = displayName;
            this.command = command;
            this.sendInstantly = sendInstantly;
        }
    }
}
