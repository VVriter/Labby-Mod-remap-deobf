//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.util.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class MemoryModule extends TextModule
{
    private EnumMemoryType enumMemoryType;
    private boolean coloredNumber;
    private boolean moreSpace;
    private long lastUpdate;
    private List<List<Text>> lastRenderedMemory;
    
    public MemoryModule() {
        this.lastUpdate = -1L;
        this.lastRenderedMemory = Collections.singletonList((List<Text>)new ArrayList<Text>());
    }
    
    @Override
    public String[] getKeys() {
        return new String[] { "Memory" };
    }
    
    @Override
    public List<List<Text>> getTextValues() {
        if (this.lastUpdate + 50L < System.currentTimeMillis()) {
            this.lastUpdate = System.currentTimeMillis();
            final List<Text> texts = new ArrayList<Text>();
            final long maxMemory = Runtime.getRuntime().maxMemory();
            final long totalMemory = Runtime.getRuntime().totalMemory();
            final long freeMemory = Runtime.getRuntime().freeMemory();
            final long usedMemory = totalMemory - freeMemory;
            final long percent = usedMemory * 100L / maxMemory;
            int statusColor = this.valueColor;
            if (this.coloredNumber) {
                statusColor = ((percent >= 70L) ? ((percent >= 90L) ? ModColor.DARK_RED.getColor().getRGB() : ModColor.RED.getColor().getRGB()) : statusColor);
            }
            if (this.enumMemoryType == EnumMemoryType.PERCENT || this.enumMemoryType == EnumMemoryType.USED_PERCENT) {
                texts.add(new Text(percent + "%", statusColor));
            }
            if (this.enumMemoryType == EnumMemoryType.USED_SLASH_MAX || this.enumMemoryType == EnumMemoryType.USED_OF_MAX || this.enumMemoryType == EnumMemoryType.USED || this.enumMemoryType == EnumMemoryType.USED_PERCENT) {
                final Text readableByteCountUsedMemory = new Text(((this.enumMemoryType == EnumMemoryType.USED_PERCENT) ? " " : "") + ModUtils.humanReadableByteCount(usedMemory, true, this.moreSpace), statusColor);
                texts.add(readableByteCountUsedMemory);
                if (this.enumMemoryType != EnumMemoryType.USED && this.enumMemoryType != EnumMemoryType.USED_PERCENT) {
                    final Text readableByteCountMaxMemory = new Text(ModUtils.humanReadableByteCount(maxMemory, true, this.moreSpace), -1);
                    texts.add(new Text((this.enumMemoryType == EnumMemoryType.USED_SLASH_MAX) ? "/" : " of ", this.bracketsColor));
                    texts.add(readableByteCountMaxMemory);
                }
            }
            return this.lastRenderedMemory = Collections.singletonList(texts);
        }
        return this.lastRenderedMemory;
    }
    
    @Override
    public String[] getDefaultKeys() {
        return this.getKeys();
    }
    
    @Override
    public List<List<Text>> getDefaultTextValues() {
        return Collections.singletonList(Collections.singletonList(Text.getText("?", -1)));
    }
    
    public void loadSettings() {
        this.enumMemoryType = EnumMemoryType.valueOf(this.getAttribute("memoryType", "PERCENT"));
        this.coloredNumber = Boolean.valueOf(this.getAttribute("coloredNumber", "true"));
        this.moreSpace = Boolean.valueOf(this.getAttribute("moreSpace", "true"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        final DropDownMenu<EnumMemoryType> dropDownMenu = (DropDownMenu<EnumMemoryType>)new DropDownMenu("Memory Display Type", 0, 0, 0, 0).fill((Object[])EnumMemoryType.values());
        dropDownMenu.setSelected((Object)this.enumMemoryType);
        final DropDownElement<EnumMemoryType> dropDownElement = new DropDownElement<EnumMemoryType>(this, "Memory Display Type", "memoryType", dropDownMenu, new DropDownElement.DrowpDownLoadValue<EnumMemoryType>() {
            @Override
            public EnumMemoryType load(final String value) {
                return EnumMemoryType.valueOf(value);
            }
        });
        settingsElements.add(dropDownElement);
        settingsElements.add(new BooleanElement(this, this.getModuleIcon(this.getSettingName(), "colorednumber"), "Colored number", "coloredNumber"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.DIODE), "More space", "moreSpace"));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "memory";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 6;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    private enum EnumMemoryType
    {
        PERCENT, 
        USED, 
        USED_PERCENT, 
        USED_SLASH_MAX, 
        USED_OF_MAX;
    }
}
