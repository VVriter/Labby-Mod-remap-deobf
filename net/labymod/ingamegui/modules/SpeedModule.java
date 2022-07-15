//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.text.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.core.*;
import net.labymod.ingamegui.enums.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class SpeedModule extends SimpleModule
{
    private DecimalFormat df;
    private long lastPositionUpdate;
    private double lastPositionX;
    private double lastPositionY;
    private double lastPositionZ;
    private double speed;
    private boolean displayAtZeroSpeed;
    
    public SpeedModule() {
        this.df = new DecimalFormat("#.##");
    }
    
    @Override
    public String getDisplayName() {
        return "Speed";
    }
    
    @Override
    public String getDisplayValue() {
        return String.valueOf(this.df.format(this.speed) + " blocks/s");
    }
    
    public boolean isShown() {
        return this.displayAtZeroSpeed || this.speed != 0.0;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return;
        }
        if (!this.isEnabled(EnumDisplayType.INGAME)) {
            return;
        }
        if (this.lastPositionUpdate + 1000L < System.currentTimeMillis()) {
            this.lastPositionUpdate = System.currentTimeMillis();
            final vg entity = (vg)LabyModCore.getMinecraft().getPlayer();
            final double d0 = this.lastPositionX - entity.p;
            final double d2 = this.lastPositionY - entity.q;
            final double d3 = this.lastPositionZ - entity.r;
            this.speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
            this.lastPositionX = entity.p;
            this.lastPositionY = entity.q;
            this.lastPositionZ = entity.r;
        }
    }
    
    public void loadSettings() {
        this.displayAtZeroSpeed = Boolean.valueOf(this.getAttribute("displayAtZeroSpeed", "false"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.EYE_OF_ENDER), "Visible at zero speed", "displayAtZeroSpeed"));
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "speed";
    }
    
    public String getDescription() {
        return "Shows your current walking speed in the following unit: blocks per second";
    }
    
    public int getSortingId() {
        return 12;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
