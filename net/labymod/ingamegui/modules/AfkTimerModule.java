//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.core.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class AfkTimerModule extends SimpleModule
{
    private boolean afk;
    private String timer;
    private int currentCount;
    private int currentTick;
    private long lastMove;
    private long lastTimeAfk;
    private int lastMouseX;
    private int lastMouseY;
    private int idleSeconds;
    
    public AfkTimerModule() {
        this.timer = "";
        this.currentCount = 0;
        this.currentTick = 0;
        this.idleSeconds = 20;
    }
    
    @Override
    public String getDisplayName() {
        return "AFK";
    }
    
    @Override
    public String getDisplayValue() {
        return this.timer;
    }
    
    @Override
    public String getDefaultValue() {
        return "00:00";
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (LabyModCore.getMinecraft().getPlayer() == null || this.getEnabled().size() == 0) {
            this.currentCount = 0;
            this.timer = "";
            return;
        }
        final int currentMouseX = Mouse.getX();
        final int currentMouseY = Mouse.getY();
        final long currentMillis = System.currentTimeMillis();
        if (this.lastMouseX != currentMouseX || this.lastMouseY != currentMouseY || LabyModCore.getMinecraft().getPlayer().bg != 0.0f || LabyModCore.getMinecraft().getPlayer().be != 0.0f || LabyModCore.getMinecraft().getPlayer().L != 0.0f) {
            if (this.afk) {
                this.afk = false;
                this.timer = ModColor.cl("c") + this.timer;
                this.lastTimeAfk = currentMillis;
            }
            this.lastMove = currentMillis;
        }
        else if (this.afk) {
            if (++this.currentTick >= 20) {
                this.currentTick = 0;
                ++this.currentCount;
                this.timer = ModUtils.parseTimer(this.currentCount);
            }
        }
        else if (currentMillis - this.lastMove >= 1000 * this.idleSeconds) {
            this.afk = true;
            this.currentCount = 0;
            this.timer = "00:00";
        }
        if (!this.afk && currentMillis - this.lastTimeAfk >= 5000L) {
            this.timer = "";
            this.currentCount = 0;
        }
        this.lastMouseX = currentMouseX;
        this.lastMouseY = currentMouseY;
    }
    
    public void loadSettings() {
        this.idleSeconds = Integer.parseInt(this.getAttribute("idleSeconds", "20"));
        if (this.idleSeconds < 3) {
            this.idleSeconds = 3;
        }
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new NumberElement(this, new ControlElement.IconData(Material.WATCH), "Idle seconds", "idleSeconds").setRange(3, 1800));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "afk_timer";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_OTHER;
    }
    
    public boolean isShown() {
        return !this.timer.isEmpty();
    }
}
