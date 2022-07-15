//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.text.*;
import net.labymod.core.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class CoordinatesModule extends SimpleTextModule
{
    private DecimalFormat df;
    private boolean displayYAxis;
    private boolean displayXZAxis;
    private boolean displaySingleLine;
    
    @Override
    public String[] getKeys() {
        if (this.displaySingleLine) {
            return this.displayYAxis ? (this.displayXZAxis ? new String[] { "XYZ" } : new String[] { "Y" }) : (this.displayXZAxis ? new String[] { "XZ" } : new String[] { "Coodinates" });
        }
        return this.displayYAxis ? (this.displayXZAxis ? new String[] { "X", "Y", "Z" } : new String[] { "Y" }) : (this.displayXZAxis ? new String[] { "X", "Z" } : new String[] { "Coordinates" });
    }
    
    @Override
    public String[] getDefaultKeys() {
        return this.getKeys();
    }
    
    @Override
    public String[] getValues() {
        vg player = LabyModCore.getMinecraft().getRenderViewEntity();
        if (player == null) {
            player = (vg)LabyModCore.getMinecraft().getPlayer();
        }
        if (player == null) {
            return this.getDefaultValues();
        }
        final boolean t = this.df != null;
        final String x = t ? String.valueOf(this.df.format(player.p)) : String.valueOf((int)player.p);
        final String y = t ? String.valueOf(this.df.format(player.q)) : String.valueOf((int)player.q);
        final String z = t ? String.valueOf(this.df.format(player.r)) : String.valueOf((int)player.r);
        if (this.displaySingleLine) {
            return this.displayYAxis ? (this.displayXZAxis ? new String[] { x + ", " + y + ", " + z } : new String[] { y }) : (this.displayXZAxis ? new String[] { x + ", " + z } : new String[] { "hidden" });
        }
        return this.displayYAxis ? (this.displayXZAxis ? new String[] { x, y, z } : new String[] { y }) : (this.displayXZAxis ? new String[] { x, z } : new String[] { "hidden" });
    }
    
    public boolean isShown() {
        return this.displayYAxis || this.displayXZAxis;
    }
    
    @Override
    public String[] getDefaultValues() {
        final String x = "0";
        final String y = "0";
        final String z = "0";
        if (this.displaySingleLine) {
            return this.displayYAxis ? (this.displayXZAxis ? new String[] { x + ", " + y + ", " + z } : new String[] { y }) : (this.displayXZAxis ? new String[] { x + ", " + z } : new String[] { "hidden" });
        }
        return this.displayYAxis ? (this.displayXZAxis ? new String[] { x, y, z } : new String[] { y }) : (this.displayXZAxis ? new String[] { x, z } : new String[] { "hidden" });
    }
    
    public void loadSettings() {
        this.displayYAxis = Boolean.valueOf(this.getAttribute("displayYAxis", "true"));
        this.displayXZAxis = Boolean.valueOf(this.getAttribute("displayXZAxis", "true"));
        this.displaySingleLine = Boolean.valueOf(this.getAttribute("displaySingleLine", "false"));
        final int digitCount = Integer.valueOf(this.getAttribute("digitCount", "0"));
        if (digitCount == 0) {
            this.df = null;
        }
        else {
            String zeros = "";
            for (int i = 0; i < digitCount; ++i) {
                zeros += "0";
            }
            this.df = new DecimalFormat("0." + zeros);
        }
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SIGN), "Display Y Axis", "displayYAxis"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SIGN), "Display XZ Axis", "displayXZAxis"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SIGN), "Display in single line", "displaySingleLine"));
        settingsElements.add(new NumberElement(this, new ControlElement.IconData(Material.COMMAND), "Number of digits", "digitCount").setRange(0, 5));
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.COMPASS);
    }
    
    public String getSettingName() {
        return "coordinates";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 1;
    }
}
