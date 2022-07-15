//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.core.*;
import net.labymod.settings.elements.*;
import net.labymod.gui.elements.*;
import java.awt.*;
import net.labymod.utils.*;
import net.labymod.ingamegui.*;

public class PingModule extends TextModule
{
    private int averageColor;
    private boolean displayAverage;
    private boolean coloredPing;
    private List<Integer> storedPing;
    private long lastStoredPing;
    private double averagePing;
    private int currentPing;
    
    public PingModule() {
        this.storedPing = new ArrayList<Integer>();
        this.lastStoredPing = 0L;
        this.averagePing = 0.0;
        this.currentPing = 0;
    }
    
    @Override
    public String[] getKeys() {
        return new String[] { "Ping" };
    }
    
    @Override
    public String[] getDefaultKeys() {
        return this.getKeys();
    }
    
    @Override
    public List<List<Text>> getTextValues() {
        final List<Text> texts = new ArrayList<Text>();
        int color = this.valueColor;
        if (this.coloredPing) {
            if (this.currentPing < 80) {
                color = ModColor.GREEN.getColor().getRGB();
            }
            else if (this.currentPing < 120) {
                color = ModColor.YELLOW.getColor().getRGB();
            }
            else if (this.currentPing < 250) {
                color = ModColor.RED.getColor().getRGB();
            }
            else if (this.currentPing < 400) {
                color = ModColor.DARK_RED.getColor().getRGB();
            }
        }
        texts.add(Text.getText(String.valueOf((this.currentPing <= 0) ? "?" : Integer.valueOf(this.currentPing)), color));
        if (this.displayAverage) {
            texts.add(Text.getText(" " + String.valueOf((int)this.averagePing), this.averageColor));
        }
        return Collections.singletonList(texts);
    }
    
    @Override
    public List<List<Text>> getDefaultTextValues() {
        return Collections.singletonList(Collections.singletonList(Text.getText("?", -1)));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (this.isShown() && this.lastStoredPing < System.currentTimeMillis()) {
            this.lastStoredPing = System.currentTimeMillis() + 5000L;
            this.updateCurrentPing();
            if (this.currentPing != 0 && this.displayAverage) {
                this.storedPing.add(this.currentPing);
                if (this.storedPing.size() > 12) {
                    this.storedPing.remove(0);
                }
                int total = 0;
                for (final int ping : this.storedPing) {
                    total += ping;
                }
                this.averagePing = total / this.storedPing.size();
            }
        }
    }
    
    private void updateCurrentPing() {
        final bsc networkPlayerInfo = LabyModCore.getMinecraft().getConnection().a(LabyModCore.getMinecraft().getPlayer().bm());
        if (networkPlayerInfo == null) {
            this.currentPing = 0;
        }
        else {
            this.currentPing = networkPlayerInfo.c();
        }
    }
    
    public void loadSettings() {
        this.coloredPing = Boolean.valueOf(this.getAttribute("coloredPing", "true"));
        this.displayAverage = Boolean.valueOf(this.getAttribute("displayAverage", "false"));
        this.averageColor = Integer.valueOf(this.getAttribute("averageColor", "-1"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, this.getModuleIcon(this.getSettingName(), "coloredping"), "Colored Ping", "coloredPing"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.EMERALD), "Display Average", "displayAverage"));
        final ColorPickerCheckBoxBulkElement colorPickerBulkElement = new ColorPickerCheckBoxBulkElement("Average color");
        settingsElements.add(colorPickerBulkElement);
        final ColorPicker nameColorPicker = new ColorPicker("Average", ModColor.getColorByString(this.getAttributes().get("averageColor")), (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return Color.WHITE;
            }
        }, 0, 0, 0, 0);
        nameColorPicker.setHasAdvanced(true);
        nameColorPicker.setHasDefault(false);
        nameColorPicker.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(final Color color) {
                PingModule.this.getAttributes().put("averageColor", String.valueOf((color == null) ? -1 : color.getRGB()));
                PingModule.this.loadSettings();
            }
        });
        colorPickerBulkElement.addColorPicker(nameColorPicker);
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "ping";
    }
    
    public String getDescription() {
        return "The ping to the current server";
    }
    
    public int getSortingId() {
        return 7;
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getPlayer() != null && !bib.z().E();
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
