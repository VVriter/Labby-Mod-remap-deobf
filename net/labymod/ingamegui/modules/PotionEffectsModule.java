//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.core.*;
import net.labymod.api.permissions.*;
import net.labymod.main.*;
import java.util.*;
import java.awt.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class PotionEffectsModule extends Module
{
    private List<va> dummyPotionEffect;
    
    public PotionEffectsModule() {
        this.dummyPotionEffect = new ArrayList<va>(Arrays.asList(LabyModCore.getMinecraft().getPotionEffect(uz.b("speed"), 1, 2), LabyModCore.getMinecraft().getPotionEffect(uz.b("slowness"), 1, 1), LabyModCore.getMinecraft().getPotionEffect(uz.b("haste"), 1, 1)));
    }
    
    public void init() {
        super.init();
    }
    
    public void loadSettings() {
    }
    
    public void draw(final int x, int y, final int rightX) {
        final Collection<va> potionEffects = this.getActivePotionEffects();
        if (potionEffects.size() == 0) {
            return;
        }
        if (!Permissions.isAllowed(Permissions.Permission.GUI_POTION_EFFECTS)) {
            return;
        }
        bus.G();
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.g();
        final int slotHeight = this.getSlotHeight(potionEffects);
        for (final va potionEffect : potionEffects) {
            final uz potion = LabyModCore.getMinecraft().getPotion(potionEffect);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            final String name = cey.a(potion.a(), new Object[0]);
            String amplifier = "";
            if (potionEffect.c() == 1) {
                amplifier = cey.a("enchantment.level.2", new Object[0]);
            }
            else if (potionEffect.c() == 2) {
                amplifier = cey.a("enchantment.level.3", new Object[0]);
            }
            else if (potionEffect.c() == 3) {
                amplifier = cey.a("enchantment.level.4", new Object[0]);
            }
            if (rightX == -1) {
                final int stringWidth = LabyModCore.getMinecraft().getFontRenderer().a(name + " ");
                LabyModCore.getMinecraft().getFontRenderer().a(name, (float)(x + 28), (float)(y - 1), this.getColor("nameColor", DefaultValues.POTION_NAME_COLOR));
                LabyModCore.getMinecraft().getFontRenderer().a(amplifier, (float)(x + (28 + stringWidth)), (float)(y - 1), this.getColor("ampColor", DefaultValues.POTION_AMPLIFIER_COLOR));
                LabyModCore.getMinecraft().getFontRenderer().a(LabyModCore.getMinecraft().getPotionDurationString(potionEffect), (float)(x + 28), (float)(y + 9), this.getColor("durationColor", DefaultValues.POTION_DURATION_COLOR));
            }
            else {
                final int stringWidth = LabyMod.getInstance().getDrawUtils().getStringWidth(amplifier.isEmpty() ? "" : (" " + amplifier));
                LabyMod.getInstance().getDrawUtils().drawRightStringWithShadow(amplifier, rightX - 28, y - 1, this.getColor("ampColor", DefaultValues.POTION_AMPLIFIER_COLOR));
                LabyMod.getInstance().getDrawUtils().drawRightStringWithShadow(name, rightX - (28 + stringWidth), y - 1, this.getColor("nameColor", DefaultValues.POTION_NAME_COLOR));
                LabyMod.getInstance().getDrawUtils().drawRightStringWithShadow(LabyModCore.getMinecraft().getPotionDurationString(potionEffect), rightX - 28, y + 9, this.getColor("durationColor", DefaultValues.POTION_DURATION_COLOR));
            }
            bib.z().N().a(LabyModCore.getRenderImplementation().getInventoryBackground());
            bus.d(1.0f, 1.0f, 1.0f);
            if (potion.c()) {
                final int potionStatusIconIndex = potion.d();
                if (rightX == -1) {
                    LabyMod.getInstance().getDrawUtils().b(x, y, potionStatusIconIndex % 8 * 18, 198 + potionStatusIconIndex / 8 * 18, 18, 18);
                }
                else {
                    LabyMod.getInstance().getDrawUtils().b(rightX - 18, y, potionStatusIconIndex % 8 * 18, 198 + potionStatusIconIndex / 8 * 18, 18, 18);
                }
            }
            y += slotHeight;
        }
        bus.H();
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getPlayer() != null && !LabyModCore.getMinecraft().getPlayer().ca().isEmpty();
    }
    
    protected boolean supportsRescale() {
        return true;
    }
    
    private Collection<va> getActivePotionEffects() {
        if (!this.isShown()) {
            return this.dummyPotionEffect;
        }
        return (Collection<va>)LabyModCore.getMinecraft().getPlayer().ca();
    }
    
    private int getColor(final String key, final int defaultColor) {
        final Color color = ModColor.getColorByString(this.getAttributes().get(key));
        if (color == null) {
            return defaultColor;
        }
        return color.getRGB();
    }
    
    private int getSlotHeight(final Collection<va> potionEffects) {
        int slotHeight = 23;
        if (potionEffects.size() > 5) {
            slotHeight = 132 / (potionEffects.size() - 1);
        }
        return slotHeight;
    }
    
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings((List)settingsElements);
        final ColorPickerCheckBoxBulkElement colorPickerBulkElement = new ColorPickerCheckBoxBulkElement("Colors");
        final ColorPicker nameColorPicker = new ColorPicker("Name", ModColor.getColorByString(this.getAttributes().get("nameColor")), (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(DefaultValues.POTION_NAME_COLOR);
            }
        }, 0, 0, 0, 0);
        nameColorPicker.setHasAdvanced(true);
        nameColorPicker.setHasDefault(false);
        nameColorPicker.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(final Color color) {
                PotionEffectsModule.this.getAttributes().put("nameColor", String.valueOf((color == null) ? -1 : color.getRGB()));
            }
        });
        colorPickerBulkElement.addColorPicker(nameColorPicker);
        final ColorPicker amplifierColorPicker = new ColorPicker("Amplifier", ModColor.getColorByString(this.getAttributes().get("ampColor")), (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(DefaultValues.POTION_AMPLIFIER_COLOR);
            }
        }, 0, 0, 0, 0);
        amplifierColorPicker.setHasAdvanced(true);
        amplifierColorPicker.setHasDefault(false);
        amplifierColorPicker.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(final Color color) {
                PotionEffectsModule.this.getAttributes().put("ampColor", String.valueOf((color == null) ? -1 : color.getRGB()));
            }
        });
        colorPickerBulkElement.addColorPicker(amplifierColorPicker);
        final ColorPicker durationColorPicker = new ColorPicker("Name", ModColor.getColorByString(this.getAttributes().get("durationColor")), (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(DefaultValues.POTION_DURATION_COLOR);
            }
        }, 0, 0, 0, 0);
        durationColorPicker.setHasAdvanced(true);
        durationColorPicker.setHasDefault(false);
        durationColorPicker.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(final Color color) {
                PotionEffectsModule.this.getAttributes().put("durationColor", String.valueOf((color == null) ? -1 : color.getRGB()));
            }
        });
        colorPickerBulkElement.addColorPicker(durationColorPicker);
        settingsElements.add(colorPickerBulkElement);
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public double getHeight() {
        final Collection<va> potionEffects = this.getActivePotionEffects();
        return (short)this.scaleModuleSize((double)(this.getSlotHeight(potionEffects) * potionEffects.size()), false);
    }
    
    public double getWidth() {
        final Collection<va> potionEffects = this.getActivePotionEffects();
        int maxWidth = 0;
        for (final va potionEffect : potionEffects) {
            final uz potion = LabyModCore.getMinecraft().getPotion(potionEffect);
            String amplifierString = cey.a(potion.a(), new Object[0]);
            if (potionEffect.c() == 1) {
                amplifierString = amplifierString + " " + cey.a("enchantment.level.2", new Object[0]);
            }
            else if (potionEffect.c() == 2) {
                amplifierString = amplifierString + " " + cey.a("enchantment.level.3", new Object[0]);
            }
            else if (potionEffect.c() == 3) {
                amplifierString = amplifierString + " " + cey.a("enchantment.level.4", new Object[0]);
            }
            final int width = LabyModCore.getMinecraft().getFontRenderer().a(LabyModCore.getMinecraft().getPotionDurationString(potionEffect));
            final int width2 = LabyModCore.getMinecraft().getFontRenderer().a(amplifierString);
            final int completeWidth = Math.max(width, width2) + (potion.c() ? 28 : 0);
            if (completeWidth > maxWidth) {
                maxWidth = completeWidth;
            }
        }
        return (short)this.scaleModuleSize((double)maxWidth, false);
    }
    
    public String getSettingName() {
        return "potions";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 11;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
