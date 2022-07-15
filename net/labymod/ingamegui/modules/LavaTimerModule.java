//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.core.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class LavaTimerModule extends SimpleModule
{
    private long lavaTimerStarted;
    private long lavaTimerFadeOut;
    private int lastTimerSeconds;
    private int fadeOutSeconds;
    
    public LavaTimerModule() {
        this.lavaTimerStarted = 0L;
        this.lavaTimerFadeOut = 0L;
        this.lastTimerSeconds = 0;
    }
    
    @Override
    public String getDisplayName() {
        return "Burning Timer";
    }
    
    @Override
    public String getDisplayValue() {
        int seconds = (int)(System.currentTimeMillis() - this.lavaTimerStarted) / 1000;
        if (this.lastTimerSeconds != 0) {
            seconds = this.lastTimerSeconds;
        }
        return ((this.lastTimerSeconds == 0) ? "" : ModColor.cl("c")) + ModUtils.parseTimer(seconds);
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
        if (LabyModCore.getMinecraft().getWorld() == null || LabyModCore.getMinecraft().getPlayer() == null) {
            return;
        }
        final boolean inLava = LabyModCore.getMinecraft().getPlayer().aR();
        if (inLava && this.lavaTimerStarted == 0L) {
            this.lastTimerSeconds = 0;
            this.lavaTimerStarted = System.currentTimeMillis();
        }
        else if (this.lavaTimerStarted != 0L && !inLava) {
            this.lastTimerSeconds = (int)(System.currentTimeMillis() - this.lavaTimerStarted) / 1000;
            this.lavaTimerStarted = 0L;
            this.lavaTimerFadeOut = System.currentTimeMillis() + 1000 * this.fadeOutSeconds;
        }
    }
    
    public void loadSettings() {
        this.fadeOutSeconds = Integer.valueOf(this.getAttribute("fadeout", "3"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new NumberElement(this, new ControlElement.IconData(Material.LAVA_BUCKET), "Fadeout time in seconds", "fadeout").setMaxValue(10));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "lava_challenge_timer";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getWorld() != null && LabyModCore.getMinecraft().getPlayer() != null && (this.lavaTimerStarted != 0L || this.lavaTimerFadeOut > System.currentTimeMillis());
    }
}
