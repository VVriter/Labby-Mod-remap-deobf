//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.java.model;

import java.util.*;

public class LauncherProfiles
{
    public String selectedProfile;
    public Map<String, AuthenticationDatabase> authenticationDatabase;
    public String clientToken;
    public Object selectedUser;
    public LauncherVersion launcherVersion;
    public Object profiles;
    public Object settings;
    
    public LauncherProfiles() {
        this.authenticationDatabase = new HashMap<String, AuthenticationDatabase>();
    }
}
