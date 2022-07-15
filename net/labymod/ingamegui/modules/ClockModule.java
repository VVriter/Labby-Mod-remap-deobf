//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.text.*;
import java.util.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ClockModule extends SimpleModule
{
    private String selectedClockFormat;
    private SimpleDateFormat clockFormat;
    private Date date;
    
    public ClockModule() {
        this.date = new Date();
    }
    
    @Override
    public String getDisplayName() {
        return "Clock";
    }
    
    @Override
    public String getDisplayValue() {
        this.date.setTime(System.currentTimeMillis());
        return (this.clockFormat == null) ? ("Invalid time format: " + this.selectedClockFormat) : this.clockFormat.format(this.date);
    }
    
    @Override
    public String getDefaultValue() {
        return "Unknown";
    }
    
    private void updateSimpleDateFormat() {
        try {
            this.clockFormat = new SimpleDateFormat(this.selectedClockFormat);
        }
        catch (IllegalArgumentException error) {
            this.clockFormat = null;
        }
    }
    
    public void loadSettings() {
        this.selectedClockFormat = this.getAttribute("timeFormat", "hh:mm aa");
        if (this.selectedClockFormat.equals("Use Custom Format")) {
            this.selectedClockFormat = this.getAttribute("customFormat", "hh:mm aa");
        }
        this.updateSimpleDateFormat();
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        final DropDownMenu<String> dropDownMenu = (DropDownMenu<String>)new DropDownMenu("Time format", 0, 0, 0, 0);
        dropDownMenu.addOption((Object)"hh:mm aa");
        dropDownMenu.addOption((Object)"HH:mm");
        dropDownMenu.addOption((Object)"HH:mm:ss");
        dropDownMenu.addOption((Object)"Use Custom Format");
        dropDownMenu.setSelected((Object)this.selectedClockFormat);
        final DropDownElement dropDownElement = new DropDownElement(this, "Time format", "timeFormat", dropDownMenu, null);
        settingsElements.add(dropDownElement);
        settingsElements.add(new StringElement(this, new ControlElement.IconData(Material.ANVIL), "Custom format", "customFormat"));
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.WATCH);
    }
    
    public String getSettingName() {
        return "clock";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 3;
    }
}
