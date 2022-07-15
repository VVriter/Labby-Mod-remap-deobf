//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.ingamegui.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import org.lwjgl.input.*;

public class ClickTestModule extends SimpleModule implements MouseInputEvent
{
    private String selectedMouseTracking;
    private TrackingKey trackingKey0;
    private TrackingKey trackingKey1;
    private int currentlyTrackingKey;
    private int selectedMouseTrackingMode;
    private boolean displayAtZeroClicks;
    private boolean fpsPolling;
    
    public ClickTestModule() {
        this.selectedMouseTracking = null;
        this.trackingKey0 = new TrackingKey(0);
        this.trackingKey1 = new TrackingKey(1);
        this.currentlyTrackingKey = -1;
        LabyMod.getInstance().getEventManager().register((MouseInputEvent)this);
    }
    
    @Override
    public String getDisplayName() {
        return "Clicks";
    }
    
    @Override
    public String getDisplayValue() {
        final int count = this.getTrackingCount();
        return String.valueOf(count);
    }
    
    @Override
    public String getDefaultValue() {
        return "?";
    }
    
    private int getTrackingCount() {
        int count = 0;
        switch (this.currentlyTrackingKey) {
            case -1: {
                count = this.trackingKey0.getClicks() + this.trackingKey1.getClicks();
                if (this.selectedMouseTrackingMode == 3) {
                    this.currentlyTrackingKey = (this.trackingKey0.isPressed() ? 0 : 1);
                    break;
                }
                break;
            }
            case 0: {
                count = this.trackingKey0.getClicks();
                break;
            }
            case 1: {
                count = this.trackingKey1.getClicks();
                break;
            }
        }
        return count;
    }
    
    public boolean isShown() {
        if (this.isEnabled(Module.getLastDrawnDisplayType())) {
            if (this.fpsPolling) {
                this.trackingKey0.checkKeyState();
                this.trackingKey1.checkKeyState();
            }
            else {
                this.trackingKey0.updateResult();
                this.trackingKey1.updateResult();
            }
        }
        final int count = this.getTrackingCount();
        return count != 0 || this.displayAtZeroClicks;
    }
    
    public void receiveMouseInput(final int mouseButton) {
        if (!this.fpsPolling) {
            if (mouseButton == 0) {
                this.trackingKey0.mouseEvent();
            }
            else if (mouseButton == 1) {
                this.trackingKey1.mouseEvent();
            }
        }
    }
    
    public void draw(final double x, final double y, final double rightX) {
        super.draw(x, y, rightX);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        switch (this.selectedMouseTrackingMode) {
            case 0: {
                this.currentlyTrackingKey = -1;
                break;
            }
            case 1: {
                this.currentlyTrackingKey = 0;
                break;
            }
            case 2: {
                this.currentlyTrackingKey = 1;
            }
            case 3: {
                if (this.getTrackingCount() == 0) {
                    this.currentlyTrackingKey = -1;
                    break;
                }
                break;
            }
        }
    }
    
    public void loadSettings() {
        this.selectedMouseTracking = this.getAttribute("mouseTrackingMode", "Left and rightclick");
        if (this.selectedMouseTracking == null || this.selectedMouseTracking.equals("Left and rightclick")) {
            this.selectedMouseTrackingMode = 0;
        }
        else if (this.selectedMouseTracking.equals("Leftclick")) {
            this.selectedMouseTrackingMode = 1;
        }
        else if (this.selectedMouseTracking.equals("Rightclick")) {
            this.selectedMouseTrackingMode = 2;
        }
        else {
            this.selectedMouseTrackingMode = 3;
        }
        this.displayAtZeroClicks = Boolean.valueOf(this.getAttribute("displayAtZeroClicks", "false"));
        this.fpsPolling = this.getAttribute("mouseTrackingMethod", "FPS Polling").equals("Actual Mouse Input");
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        final DropDownMenu<String> dropDownMenu = (DropDownMenu<String>)new DropDownMenu("Tracking mouse keys", 0, 0, 0, 0);
        dropDownMenu.addOption((Object)"First clicked key");
        dropDownMenu.addOption((Object)"Left and rightclick");
        dropDownMenu.addOption((Object)"Leftclick");
        dropDownMenu.addOption((Object)"Rightclick");
        dropDownMenu.setSelected((Object)this.selectedMouseTracking);
        final DropDownElement<String> dropDownElement = new DropDownElement<String>(this, "Tracking mouse keys", "mouseTrackingMode", dropDownMenu, new DropDownElement.DrowpDownLoadValue<String>() {
            @Override
            public String load(final String value) {
                return value;
            }
        });
        settingsElements.add(dropDownElement);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.EYE_OF_ENDER), "Visible at 0 clicks", "displayAtZeroClicks"));
        final DropDownMenu<String> dropDownMenuMethod = (DropDownMenu<String>)new DropDownMenu("Tracking method", 0, 0, 0, 0);
        dropDownMenuMethod.addOption((Object)"Actual Mouse Input");
        dropDownMenuMethod.addOption((Object)"FPS Polling");
        dropDownMenuMethod.setSelected((Object)this.selectedMouseTracking);
        final DropDownElement<String> dropDownElementMethod = new DropDownElement<String>(this, "Tracking method", "mouseTrackingMethod", dropDownMenuMethod, new DropDownElement.DrowpDownLoadValue<String>() {
            @Override
            public String load(final String value) {
                return value;
            }
        });
        settingsElements.add(dropDownElementMethod);
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "clicktest";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public static class TrackingKey
    {
        private int keyId;
        private long lastCPSCheck;
        private int cpsCount;
        private int cpsCountSecond;
        private boolean cpsPressed;
        
        public TrackingKey(final int keyId) {
            this.lastCPSCheck = 0L;
            this.keyId = keyId;
        }
        
        public boolean isPressed() {
            return Mouse.isButtonDown(this.keyId);
        }
        
        public void checkKeyState() {
            if (this.isPressed()) {
                if (!this.cpsPressed) {
                    this.cpsPressed = true;
                    ++this.cpsCount;
                }
            }
            else {
                this.cpsPressed = false;
            }
            this.updateResult();
        }
        
        public void mouseEvent() {
            ++this.cpsCount;
        }
        
        public void updateResult() {
            if (this.lastCPSCheck + 1000L < System.currentTimeMillis()) {
                this.lastCPSCheck = System.currentTimeMillis();
                this.cpsCountSecond = this.cpsCount;
                this.cpsCount = 0;
            }
        }
        
        public int getClicks() {
            return this.cpsCountSecond;
        }
    }
}
