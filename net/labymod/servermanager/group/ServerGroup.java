//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.servermanager.group;

import com.google.gson.annotations.*;
import java.util.*;
import net.labymod.gui.*;
import net.labymod.utils.*;
import org.apache.commons.io.*;

public class ServerGroup
{
    @SerializedName("server_name")
    private final String serverName;
    @SerializedName("nice_name")
    private final String displayName;
    @SerializedName("direct_ip")
    private final String directIp;
    private final String[] wildcards;
    private final Map<String, String> social;
    private final List<GuiAddonRecommendation.RecommendedAddon> addons;
    
    public ServerGroup(final String serverName, final String displayName, final String directIp, final String[] wildcards, final Map<String, String> social, final List<GuiAddonRecommendation.RecommendedAddon> addons) {
        this.serverName = serverName;
        this.displayName = displayName;
        this.directIp = directIp;
        this.wildcards = wildcards;
        this.social = social;
        this.addons = addons;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getDirectIp() {
        return this.directIp;
    }
    
    public String[] getWildcards() {
        return this.wildcards;
    }
    
    public Map<String, String> getSocial() {
        return this.social;
    }
    
    public List<GuiAddonRecommendation.RecommendedAddon> getAddons() {
        return this.addons;
    }
    
    public boolean matches(final ServerData data) {
        if (this.directIp.equalsIgnoreCase(data.getIp())) {
            return true;
        }
        if (this.wildcards != null) {
            for (final String wildcard : this.wildcards) {
                if (FilenameUtils.wildcardMatch(data.getIp(), wildcard.replace('%', '*'), IOCase.INSENSITIVE)) {
                    return true;
                }
            }
        }
        return false;
    }
}
