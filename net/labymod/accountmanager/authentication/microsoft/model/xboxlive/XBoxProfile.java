//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.xboxlive;

public class XBoxProfile
{
    public User[] profileUsers;
    
    public static class User
    {
        public String id;
        public String hostId;
        public Setting[] settings;
        public boolean isSponsoredUser;
        
        public String getSettingById(final String id) {
            for (final Setting setting : this.settings) {
                if (setting.id.equals(id)) {
                    return setting.value;
                }
            }
            return null;
        }
    }
    
    public static class Setting
    {
        public String id;
        public String value;
    }
}
