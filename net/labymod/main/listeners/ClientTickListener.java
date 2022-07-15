//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.gui.elements.*;
import net.labymod.settings.*;
import org.lwjgl.input.*;

public class ClientTickListener
{
    private boolean cancelSwingAnimation;
    private boolean quitted;
    private int lastPressedKey;
    
    public ClientTickListener() {
        this.cancelSwingAnimation = false;
        this.quitted = true;
        this.lastPressedKey = -1;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        LabyMod.getInstance().getAsyncTextureLoader().processBulkTasks();
        LabyModCore.getServerPinger().tick();
        SneakingAnimationThread sneakingAnimationThread = LabyMod.getInstance().getSneakingAnimationThread();
        if (sneakingAnimationThread != null && !sneakingAnimationThread.isAlive()) {
            LabyMod.getInstance().setSneakingAnimationThread((SneakingAnimationThread)null);
        }
        if (sneakingAnimationThread == null && LabyMod.getSettings().oldSneaking) {
            LabyMod.getInstance().setSneakingAnimationThread(sneakingAnimationThread = new SneakingAnimationThread());
            sneakingAnimationThread.start();
        }
        if (!LabyMod.getInstance().isInGame()) {
            if (!this.quitted) {
                this.quitted = true;
                LabyMod.getInstance().onQuit();
            }
        }
        else if (this.quitted) {
            this.quitted = false;
        }
        if (bib.z().m == null) {
            this.checkPressedKeys();
        }
        LabyMod.getInstance().getUserManager().getUserActionGui().tick();
        LabyMod.getInstance().getLabyConnect().getTracker().onGameTick();
    }
    
    private void checkPressedKeys() {
        if (this.isPressed(LabyMod.getSettings().keyModuleEditor)) {
            if (this.canFireKey(LabyMod.getSettings().keyModuleEditor)) {
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        Tabs.lastOpenScreen = null;
                        bib.z().a((blk)new LabyModModuleEditorGui(bib.z().m));
                    }
                });
            }
        }
        else if (this.isPressed(LabyMod.getSettings().keyAddons)) {
            if (this.canFireKey(LabyMod.getSettings().keyAddons)) {
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        Tabs.lastOpenScreen = null;
                        bib.z().a((blk)new LabyModAddonsGui(bib.z().m));
                    }
                });
            }
        }
        else if (this.isPressed(LabyMod.getSettings().keyToggleHitbox)) {
            if (this.canFireKey(LabyMod.getSettings().keyToggleHitbox)) {
                bib.z().ac().b(!bib.z().ac().b());
            }
        }
        else if (this.isPressed(LabyMod.getSettings().keyEmote)) {
            if (this.canFireKey(LabyMod.getSettings().keyEmote) && LabyMod.getSettings().emotes) {
                LabyMod.getInstance().getEmoteRegistry().getEmoteSelectorGui().open();
            }
        }
        else if (this.isPressed(LabyMod.getSettings().keyMarker)) {
            if (this.canFireKey(LabyMod.getSettings().keyMarker) && LabyMod.getSettings().marker) {
                LabyMod.getInstance().getMarkerManager().setClientMarker();
            }
        }
        else if (this.isPressed(LabyMod.getSettings().keyStickerMenu)) {
            if (this.canFireKey(LabyMod.getSettings().keyStickerMenu) && LabyMod.getSettings().stickers) {
                LabyMod.getInstance().getStickerRegistry().getStickerSelectorGui().open();
            }
        }
        else {
            if (this.canReleaseKey(LabyMod.getSettings().keyEmote)) {
                LabyMod.getInstance().getEmoteRegistry().getEmoteSelectorGui().close();
            }
            if (this.canReleaseKey(LabyMod.getSettings().keyStickerMenu)) {
                LabyMod.getInstance().getStickerRegistry().getStickerSelectorGui().close();
            }
            this.lastPressedKey = -1;
        }
    }
    
    private boolean canFireKey(final int key) {
        if (this.lastPressedKey != -1 || key == -1) {
            return false;
        }
        this.lastPressedKey = key;
        return true;
    }
    
    private boolean canReleaseKey(final int key) {
        return this.lastPressedKey != -1 && key != -1 && this.lastPressedKey == key;
    }
    
    private boolean isPressed(final int key) {
        return key != -1 && ((key >= 0) ? Keyboard.isKeyDown(key) : Mouse.isButtonDown(key + 100));
    }
    
    public boolean isCancelSwingAnimation() {
        return this.cancelSwingAnimation;
    }
    
    public void setCancelSwingAnimation(final boolean cancelSwingAnimation) {
        this.cancelSwingAnimation = cancelSwingAnimation;
    }
}
