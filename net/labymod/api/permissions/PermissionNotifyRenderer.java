//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.permissions;

import net.labymod.main.*;
import java.util.*;
import net.labymod.utils.*;

public class PermissionNotifyRenderer
{
    private Map<Permissions.Permission, Boolean> updateValues;
    private Map<Permissions.Permission, Boolean> lastRenderedPermissions;
    private long lastCheckCalled;
    
    public PermissionNotifyRenderer() {
        this.updateValues = new HashMap<Permissions.Permission, Boolean>();
        this.lastRenderedPermissions = new HashMap<Permissions.Permission, Boolean>();
    }
    
    public void checkChangedPermissions() {
        if (!LabyMod.getSettings().notifyPermissionChanges) {
            return;
        }
        final Map<Permissions.Permission, Boolean> updateValues = new HashMap<Permissions.Permission, Boolean>();
        for (final Permissions.Permission permission : Permissions.Permission.values()) {
            final boolean defaultValue = permission.isDefaultEnabled();
            final boolean serverValue = Permissions.isAllowed(permission);
            if (defaultValue != serverValue) {
                updateValues.put(permission, serverValue);
            }
        }
        this.updateValues = updateValues;
        boolean equals = this.updateValues.size() == this.lastRenderedPermissions.size();
        if (!this.lastRenderedPermissions.isEmpty()) {
            for (final Map.Entry<Permissions.Permission, Boolean> set : this.updateValues.entrySet()) {
                final Boolean value = this.lastRenderedPermissions.get(set.getKey());
                if (value == null || value != set.getValue()) {
                    equals = false;
                    break;
                }
            }
        }
        this.lastRenderedPermissions.putAll(this.updateValues);
        if (!this.updateValues.isEmpty() && !equals) {
            this.lastCheckCalled = System.currentTimeMillis();
        }
    }
    
    public void quit() {
        if (!this.lastRenderedPermissions.isEmpty() && LabyMod.getSettings().notifyPermissionChanges) {
            this.lastRenderedPermissions.clear();
        }
    }
    
    public void render(final int screenWidth) {
        final int maxDisplayTime = 5000;
        if (this.lastCheckCalled + maxDisplayTime > System.currentTimeMillis() && !bib.z().E()) {
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            bus.G();
            bus.c(0.0f, 0.0f, 10.0f);
            int y = 5;
            int fadeInAnimation = (int)(System.currentTimeMillis() - (this.lastCheckCalled + 1000L));
            int fadeOutAnimation = (int)(System.currentTimeMillis() - (this.lastCheckCalled + maxDisplayTime - 1000L));
            fadeInAnimation /= 5;
            if (fadeInAnimation > 0) {
                fadeInAnimation = 0;
            }
            for (final Map.Entry<Permissions.Permission, Boolean> permissionEntry : this.updateValues.entrySet()) {
                final boolean value = permissionEntry.getValue();
                final String displayName = (value ? ModColor.cl("a") : ModColor.cl("4")) + permissionEntry.getKey().getDisplayName() + " " + (value ? "\u2714" : "\u2716");
                final int width = draw.getStringWidth(displayName);
                fadeOutAnimation -= y * 2;
                fadeOutAnimation /= (int)1.2;
                if (fadeOutAnimation < 0) {
                    fadeOutAnimation = 0;
                }
                draw.drawRectangle(screenWidth - 5 - width - 2 + fadeOutAnimation, y + fadeInAnimation, screenWidth - 5 + fadeOutAnimation, y + 12 + fadeInAnimation, Integer.MIN_VALUE);
                draw.drawRectBorder(screenWidth - 5 - width - 2 + fadeOutAnimation, y + fadeInAnimation, screenWidth - 5 + fadeOutAnimation, y + 12 + fadeInAnimation, Integer.MIN_VALUE, 1.0);
                draw.drawRightString(displayName, screenWidth - 5 - 1 + fadeOutAnimation, y + 2 + fadeInAnimation);
                y += 13;
            }
            bus.c(0.0f, 0.0f, -10.0f);
            bus.H();
        }
    }
}
