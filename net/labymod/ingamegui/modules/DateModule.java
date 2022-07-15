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

public class DateModule extends SimpleModule
{
    private String selectedDateFormat;
    private SimpleDateFormat dateFormat;
    private Date date;
    private long lastUpdate;
    private String lastRenderedString;
    
    public DateModule() {
        this.date = new Date();
        this.lastUpdate = -1L;
        this.lastRenderedString = "";
    }
    
    @Override
    public String getDisplayName() {
        return "Date";
    }
    
    @Override
    public String getDisplayValue() {
        if (this.lastUpdate + 10000L < System.currentTimeMillis()) {
            this.lastUpdate = System.currentTimeMillis();
            this.date.setTime(System.currentTimeMillis());
            this.lastRenderedString = ((this.dateFormat == null) ? ("Invalid date format: " + this.selectedDateFormat) : this.dateFormat.format(this.date));
        }
        return this.lastRenderedString;
    }
    
    @Override
    public String getDefaultValue() {
        return "Unknown";
    }
    
    private void updateSimpleDateFormat() {
        try {
            this.dateFormat = new SimpleDateFormat(this.selectedDateFormat);
        }
        catch (IllegalArgumentException error) {
            this.dateFormat = null;
        }
    }
    
    public void loadSettings() {
        this.selectedDateFormat = this.getAttribute("dateFormat", "MM/dd/yyyy");
        if (this.selectedDateFormat.equals("Use Custom Format")) {
            this.selectedDateFormat = this.getAttribute("customFormat", "MM/dd/yyyy");
        }
        this.updateSimpleDateFormat();
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        final DropDownMenu<String> dropDownMenu = (DropDownMenu<String>)new DropDownMenu("Date format", 0, 0, 0, 0);
        dropDownMenu.addOption((Object)"MM/dd/yyyy");
        dropDownMenu.addOption((Object)"MM/dd/yy");
        dropDownMenu.addOption((Object)"MM/dd");
        dropDownMenu.addOption((Object)"dd.MM.yyyy");
        dropDownMenu.addOption((Object)"dd.MM.yy");
        dropDownMenu.addOption((Object)"dd.MM");
        dropDownMenu.addOption((Object)"Use Custom Format");
        dropDownMenu.setSelected((Object)this.selectedDateFormat);
        final DropDownElement dropDownElement = new DropDownElement(this, "Date format", "dateFormat", dropDownMenu, null);
        settingsElements.add(dropDownElement);
        settingsElements.add(new StringElement(this, new ControlElement.IconData(Material.ANVIL), "Custom format", "customFormat"));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "date";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 4;
    }
}
