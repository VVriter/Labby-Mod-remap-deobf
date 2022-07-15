//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import net.labymod.ingamegui.enums.*;
import net.labymod.ingamegui.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.settings.*;
import net.labymod.main.lang.*;

public abstract class ItemModule extends Module
{
    public DurabilityVisibility visibility;
    private EnumItemSlot itemSlot;
    private nf placeHolderTexture;
    
    public ItemModule() {
        this.visibility = DurabilityVisibility.DEFAULT;
        this.placeHolderTexture = null;
    }
    
    public void init() {
        super.init();
        this.visibility = DurabilityVisibility.valueOf(this.getAttribute("visibility", this.visibility.name()));
        try {
            this.itemSlot = EnumItemSlot.values()[Integer.parseInt(this.getAttribute("itemSlot", "0"))];
        }
        catch (Exception ex) {
            this.itemSlot = EnumItemSlot.NONE;
        }
        if (this.getModuleConfigElement().isUsingExtendedSettings()) {
            this.padding = (this.getAttributes().containsKey("padding") ? Integer.parseInt(this.getAttributes().get("padding")) : 0);
            this.backgroundVisible = (this.getAttributes().containsKey("backgroundVisible") && Boolean.parseBoolean(this.getAttributes().get("backgroundVisible")));
            this.backgroundTransparency = (this.getAttributes().containsKey("backgroundTransparency") ? Integer.parseInt(this.getAttributes().get("backgroundTransparency")) : 50);
            this.backgroundColor = (this.getAttributes().containsKey("backgroundColor") ? Integer.parseInt(this.getAttributes().get("backgroundColor")) : Integer.MIN_VALUE);
        }
    }
    
    public void draw(final double x, final double y, final double rightX) {
        this.draw(x, y, rightX, null);
    }
    
    public void draw(double x, double y, final double rightX, final EnumItemSlot itemSlot) {
        final double padding = (this.itemSlot == EnumItemSlot.NONE) ? this.padding : 0.0;
        final double width = this.getRawWidth() + this.scaleModuleSize(padding * 2.0, true);
        final double height = this.getRawHeight() + this.scaleModuleSize(padding * 2.0, true);
        if (this.itemSlot != EnumItemSlot.NONE) {
            this.applyScale(false);
            x = this.scaleModuleSize(x, false);
            y = this.scaleModuleSize(y, false);
            bus.b(width / 2.0, height / 2.0, 0.0);
            this.applyScale(true);
            x = this.scaleModuleSize(x, true);
            y = this.scaleModuleSize(y, true);
            x -= width / 2.0;
            y -= height / 2.0;
        }
        final boolean extended = this.getModuleConfigElement().isUsingExtendedSettings();
        if (!extended) {
            this.padding = ModuleConfig.getConfig().getPadding();
            this.backgroundVisible = ModuleConfig.getConfig().isBackgroundVisible();
            this.backgroundTransparency = ModuleConfig.getConfig().getBackgroundTransparency();
        }
        if (this.backgroundVisible) {
            final int color = extended ? this.backgroundColor : ModuleConfig.getConfig().getBackgroundColor();
            final int red = 0xFF & color >> 16;
            final int blue = 0xFF & color >> 0;
            final int green = 0xFF & color >> 8;
            LabyMod.getInstance().getDrawUtils().drawRect(x, y, x + width, y + height, this.backgroundTransparency << 24 | red << 16 | green << 8 | blue);
        }
        final double paddingSize = this.scaleModuleSize(padding, true);
        final aip item = this.getItem();
        if (item == null) {
            final nf textureLocation = (this.placeHolderTexture == null) ? (this.placeHolderTexture = this.getPlaceholderTexture()) : this.placeHolderTexture;
            bib.z().N().a(textureLocation);
            LabyMod.getInstance().getDrawUtils().drawTexture(x + paddingSize, y + paddingSize, 256.0, 256.0, 16.0, 16.0, 0.7f);
        }
        else {
            bus.k();
            LabyMod.getInstance().getDrawUtils().drawItem(item, x + paddingSize, y + paddingSize, null);
            bus.j();
        }
        DurabilityVisibility visibility = this.visibility;
        if (visibility == DurabilityVisibility.DEFAULT || !this.getModuleConfigElement().isUsingExtendedSettings()) {
            visibility = ModuleConfig.getConfig().getDefaultDurabilityVisibility();
        }
        if (!this.drawDurability()) {
            visibility = DurabilityVisibility.OFF;
        }
        if ((item == null || item.k() != 0) && visibility != DurabilityVisibility.OFF && visibility != DurabilityVisibility.DEFAULT) {
            final DurabilityVisibility.DurabilityBuilder durabilityBuilder = visibility.getDurabilityBuilder();
            if (durabilityBuilder != null) {
                if (((itemSlot == null || itemSlot == EnumItemSlot.NONE) && rightX != -1.0) || (itemSlot != null && itemSlot != EnumItemSlot.NONE && itemSlot.isRightbound())) {
                    LabyMod.getInstance().getDrawUtils().drawRightString(durabilityBuilder.build(item), x - 5.0, y + height / 2.0 - 2.0);
                }
                else {
                    final String text = durabilityBuilder.build(item);
                    LabyMod.getInstance().getDrawUtils().drawString(text, x + width + 5.0, y + height / 2.0 - padding);
                }
            }
        }
    }
    
    protected aip getItemInArmorSlot(final int slot) {
        final aip itemStack = LabyModCore.getMinecraft().getPlayer().bv.g(slot);
        if (itemStack == null) {
            return null;
        }
        if (ain.a(itemStack.c()) == 0) {
            return null;
        }
        return itemStack;
    }
    
    public String getDescription() {
        return "";
    }
    
    public abstract nf getPlaceholderTexture();
    
    public abstract aip getItem();
    
    public void settingUpdated(final boolean enabled) {
        super.settingUpdated(enabled);
        if (!enabled) {
            this.setItemSlot(EnumItemSlot.NONE);
        }
    }
    
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings((List)settingsElements);
        DefaultElementsCreator.createDurability(this, false, settingsElements);
        DefaultElementsCreator.createBackgroundVisible(this, false, settingsElements);
        DefaultElementsCreator.createPadding(this, false, settingsElements);
    }
    
    public double getWidth() {
        if (this.itemSlot != EnumItemSlot.NONE) {
            return this.getRawWidth();
        }
        return this.scaleModuleSize(this.getRawWidth(), false) + this.padding * 2;
    }
    
    public double getHeight() {
        if (this.itemSlot != EnumItemSlot.NONE) {
            return this.getRawHeight();
        }
        return this.scaleModuleSize(this.getRawHeight(), false) + this.padding * 2;
    }
    
    public double getRawWidth() {
        return 16.0;
    }
    
    public double getRawHeight() {
        return 16.0;
    }
    
    public boolean supportsRescale() {
        return true;
    }
    
    protected int getSpacing() {
        return 0;
    }
    
    public abstract boolean drawDurability();
    
    public EnumItemSlot getItemSlot() {
        return this.itemSlot;
    }
    
    public void setItemSlot(final EnumItemSlot itemSlot) {
        this.itemSlot = itemSlot;
    }
    
    public enum DurabilityVisibility
    {
        DEFAULT(true, "durability_default", (DurabilityBuilder)null), 
        OFF(true, "durability_off", (DurabilityBuilder)null), 
        DURABILITY_ONLY(true, "durability_only", (DurabilityBuilder)new DurabilityBuilder() {
            @Override
            public String build(final aip itemStack) {
                return (itemStack == null) ? "0" : String.valueOf(itemStack.k() - itemStack.i());
            }
        }), 
        DURABILITY_MAX(true, "durability_max", (DurabilityBuilder)new DurabilityBuilder() {
            @Override
            public String build(final aip itemStack) {
                return (itemStack == null) ? "0/0" : (itemStack.k() - itemStack.i() + "/" + itemStack.k());
            }
        }), 
        PERCENT(true, "durability_percent", (DurabilityBuilder)new DurabilityBuilder() {
            @Override
            public String build(final aip itemStack) {
                return (itemStack == null) ? "0%" : (Math.round(100.0 / itemStack.k() * (itemStack.k() - itemStack.i())) + "%");
            }
        });
        
        private boolean translate;
        private String name;
        private DurabilityBuilder durabilityBuilder;
        
        private DurabilityVisibility(final boolean translate, final String name, final DurabilityBuilder durabilityBuilder) {
            this.translate = translate;
            this.name = name;
            this.durabilityBuilder = durabilityBuilder;
        }
        
        public String getDisplayName() {
            if (this.translate) {
                return LanguageManager.translate(this.name);
            }
            return this.name;
        }
        
        public DurabilityBuilder getDurabilityBuilder() {
            return this.durabilityBuilder;
        }
        
        public interface DurabilityBuilder
        {
            String build(final aip p0);
        }
    }
}
