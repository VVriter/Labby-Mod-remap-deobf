//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.permissions;

import net.labymod.main.*;

public class Permissions
{
    private static PermissionNotifyRenderer permissionNotifyRenderer;
    
    public static boolean isAllowed(final Permission permission) {
        if (LabyMod.getInstance() == null || LabyMod.getInstance().getServerManager() == null) {
            return permission.isDefaultEnabled();
        }
        return LabyMod.getInstance().getServerManager().isAllowed(permission);
    }
    
    public static PermissionNotifyRenderer getPermissionNotifyRenderer() {
        return Permissions.permissionNotifyRenderer;
    }
    
    static {
        Permissions.permissionNotifyRenderer = new PermissionNotifyRenderer();
    }
    
    public enum Permission
    {
        IMPROVED_LAVA("Improved Lava", false), 
        CROSSHAIR_SYNC("Crosshair sync", false), 
        REFILL_FIX("Refill fix", false), 
        ENTITY_MARKER("Entity Marker", false), 
        GUI_ALL("LabyMod GUI", true), 
        GUI_POTION_EFFECTS("Potion Effects", true), 
        GUI_ARMOR_HUD("Armor HUD", true), 
        GUI_ITEM_HUD("Item HUD", true), 
        BLOCKBUILD("Blockbuild", true), 
        TAGS("Tags", true), 
        CHAT("Chat features", true), 
        ANIMATIONS("Animations", true), 
        SATURATION_BAR("Saturation bar", true);
        
        private String displayName;
        private boolean defaultEnabled;
        
        public static Permission getPermissionByName(final String name) {
            for (final Permission permission : values()) {
                if (permission.name().equals(name)) {
                    return permission;
                }
            }
            return null;
        }
        
        private Permission(final String displayName, final boolean defaultEnabled) {
            this.displayName = displayName;
            this.defaultEnabled = defaultEnabled;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public boolean isDefaultEnabled() {
            return this.defaultEnabled;
        }
    }
}
