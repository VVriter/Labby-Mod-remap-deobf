//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import java.text.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class RangeModule extends SimpleModule
{
    private DecimalFormat df;
    private double lastRange;
    private long lastAttack;
    
    public RangeModule() {
        this.df = new DecimalFormat("#.##");
        this.lastRange = 0.0;
        this.lastAttack = 0L;
        LabyMod.getInstance().getLabyModAPI().getEventManager().registerOnAttack((Consumer)new Consumer<vg>() {
            @Override
            public void accept(final vg entity) {
                final bib mc = bib.z();
                if (mc.s != null && mc.s.a.ordinal() == 2 && mc.s.d.S() == entity.S()) {
                    RangeModule.this.lastRange = mc.s.c.f(mc.aa().f(1.0f));
                    RangeModule.this.lastAttack = System.currentTimeMillis();
                }
            }
        });
    }
    
    @Override
    public String getDisplayName() {
        return "Range";
    }
    
    @Override
    public String getDisplayValue() {
        return this.df.format(this.lastRange) + " blocks";
    }
    
    public boolean isShown() {
        return this.lastAttack + 3000L > System.currentTimeMillis() && super.isShown();
    }
    
    public void loadSettings() {
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "range";
    }
    
    public String getDescription() {
        return "Shows you the range of your last hit on a player and proves that you didn't use any rangehacks.";
    }
    
    public int getSortingId() {
        return 12;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
