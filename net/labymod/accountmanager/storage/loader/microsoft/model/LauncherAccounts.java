//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model;

import java.util.*;

public class LauncherAccounts
{
    public Map<String, LauncherAccount> accounts;
    public String activeAccountLocalId;
    public String mojangClientToken;
    
    public LauncherAccounts() {
        this.accounts = new HashMap<String, LauncherAccount>();
    }
}
