//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.core.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ComboModule extends SimpleModule
{
    private int lastEntityId;
    private long lastHit;
    private int comboCount;
    private float lastHealth;
    
    public ComboModule() {
        this.lastEntityId = -1;
        this.lastHit = 0L;
        this.comboCount = 0;
        LabyMod.getInstance().getLabyModAPI().getEventManager().registerOnAttack((Consumer)new Consumer<vg>() {
            @Override
            public void accept(final vg entity) {
                if (!(entity instanceof aed)) {
                    return;
                }
                final aed entityPlayer = (aed)entity;
                if (entityPlayer.ay > 1) {
                    return;
                }
                if (entity.S() == ComboModule.this.lastEntityId) {
                    ComboModule.this.comboCount++;
                }
                else {
                    ComboModule.this.comboCount = 1;
                }
                ComboModule.this.lastEntityId = entity.S();
                ComboModule.this.lastHit = System.currentTimeMillis();
            }
        });
    }
    
    @Override
    public String getDisplayName() {
        return "Combo";
    }
    
    @Override
    public String getDisplayValue() {
        return String.valueOf(this.comboCount);
    }
    
    public boolean isShown() {
        return this.comboCount != 0 && super.isShown();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (LabyModCore.getMinecraft().getPlayer() == null) {
            return;
        }
        if (LabyModCore.getMinecraft().getPlayer().cd() < this.lastHealth) {
            this.comboCount = 0;
        }
        this.lastHealth = LabyModCore.getMinecraft().getPlayer().cd();
        if (this.lastHit + 2000L < System.currentTimeMillis()) {
            this.comboCount = 0;
        }
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
        return "combo";
    }
    
    public String getDescription() {
        return "Counts the number of hits you distribute while comboing your opponent.";
    }
    
    public int getSortingId() {
        return 12;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
