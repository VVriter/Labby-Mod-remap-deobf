//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import net.labymod.ingamegui.*;

public class FPSModule extends SimpleModule
{
    private int smoothFPS;
    private boolean displaySmoothFPS;
    
    public FPSModule() {
        this.smoothFPS = 0;
        this.displaySmoothFPS = true;
    }
    
    @Override
    public String getDisplayName() {
        return "FPS";
    }
    
    @Override
    public String getDisplayValue() {
        return String.valueOf((this.displaySmoothFPS && this.smoothFPS != 0) ? this.smoothFPS : bib.af());
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
        if (!this.displaySmoothFPS) {
            return;
        }
        final int fps = bib.af();
        if (this.smoothFPS == fps) {
            return;
        }
        if (this.smoothFPS == 0) {
            this.smoothFPS = fps;
        }
        else {
            final int factor = (Math.abs(fps - this.smoothFPS) > 50) ? 50 : 1;
            this.smoothFPS = ((fps > this.smoothFPS) ? (this.smoothFPS + factor) : (this.smoothFPS - factor));
        }
    }
    
    public void loadSettings() {
    }
    
    @Override
    public void init() {
        super.init();
        this.displaySmoothFPS = Boolean.valueOf(this.getAttribute("smoothFPS", "true"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SLIME_BALL), "Smooth FPS", "smoothFPS").addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                FPSModule.this.setAttribute("smoothFPS", String.valueOf(accepted));
                FPSModule.this.init();
            }
        }));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "fps";
    }
    
    public String getDescription() {
        return "";
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public int getSortingId() {
        return 0;
    }
}
