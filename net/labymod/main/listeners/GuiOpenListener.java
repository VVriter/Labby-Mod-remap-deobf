//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import java.lang.reflect.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.event.*;
import net.labymod.utils.manager.*;
import java.util.*;
import net.labymod.mojang.*;
import net.labymod.gui.skin.*;
import net.labymod.ingamechat.*;
import net.labymod.mojang.inventory.scale.*;
import net.labymod.mojang.inventory.*;
import net.labymod.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.main.*;

public class GuiOpenListener
{
    private static final boolean IS_MC_18;
    private blk lastScreen;
    private blr mainMenu;
    private bnf guiMultiplayer;
    private Field defaultInputTextField;
    private Boolean replayModInstalled;
    private boolean replacedThings;
    private static final String[] serverListServerData;
    private static final String[] entityRendererItemRendererNames;
    private static final String[] entityRendererNames;
    private static final String[] itemRendererNames;
    private static final String[] upperChestInventory;
    private static final String[] lowerChestInventory;
    
    public GuiOpenListener() {
        this.replayModInstalled = null;
        try {
            this.defaultInputTextField = ReflectionHelper.findField(bkn.class, LabyModCore.getMappingAdapter().getDefaultInputFieldTextMappings());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGuiOpen(final GuiOpenEvent event) {
        blk gui = LabyModCore.getForge().getGuiOpenEventGui(event);
        SignManager.reset();
        if (gui instanceof blr) {
            this.mainMenu = (blr)gui;
            if (!this.replacedThings) {
                Map<Class<? extends avj>, bwy<? extends avj>> mapSpecialRenderers = null;
                try {
                    mapSpecialRenderers = (Map<Class<? extends avj>, bwy<? extends avj>>)ReflectionHelper.findField(bwx.class, LabyModCore.getMappingAdapter().getMapSpecialRenderersMappings()).get(bwx.a);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                final bxf tileEntitySignRendererCustom = LabyModCore.getMinecraft().getCustomSignRenderer();
                mapSpecialRenderers.put((Class<? extends avj>)awc.class, (bwy<? extends avj>)tileEntitySignRendererCustom);
                tileEntitySignRendererCustom.a(bwx.a);
                new RenderPlayerHook();
                this.replacedThings = true;
            }
        }
        if (gui instanceof bnf && gui.getClass() != ModGuiMultiplayer.class) {
            final ModGuiMultiplayer guiMultiplayer = new ModGuiMultiplayer((blk)this.mainMenu);
            this.guiMultiplayer = (bnf)guiMultiplayer;
            gui = (blk)guiMultiplayer;
        }
        else if (gui != null && gui.getClass() == ModGuiMultiplayer.class) {
            this.guiMultiplayer = (bnf)gui;
        }
        if (gui instanceof blg) {
            if (LabyModCore.getMinecraft().getPlayer() != null) {
                gui = (blk)new ModGuiIngameMenu();
            }
            else {
                gui = (blk)this.mainMenu;
            }
        }
        if (gui instanceof blm && LabyMod.getSettings().betterSkinCustomization) {
            gui = (blk)new GuiSkinCustomization(this.lastScreen);
        }
        if (gui != null && gui.getClass() == bkn.class) {
            String defaultInput = "";
            try {
                defaultInput = (String)this.defaultInputTextField.get(gui);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            gui = (blk)new GuiChatCustom(defaultInput);
        }
        if (gui instanceof bmx) {
            gui = (blk)new GuiInventoryCustom(GuiOpenListener.IS_MC_18, (aed)LabyModCore.getMinecraft().getPlayer());
        }
        if (gui instanceof bmp) {
            gui = (blk)new GuiContainerCreativeCustom((aed)LabyModCore.getMinecraft().getPlayer());
        }
        if (LabyMod.getSettings().customInventoryScale != EnumGuiScale.DEFAULT) {
            if (gui instanceof bmm) {
                try {
                    final tv upperChest = (tv)ReflectionHelper.findField(bmm.class, GuiOpenListener.upperChestInventory).get(gui);
                    final tv lowerChest = (tv)ReflectionHelper.findField(bmm.class, GuiOpenListener.lowerChestInventory).get(gui);
                    gui = (blk)new GuiChestCustom(upperChest, lowerChest);
                }
                catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
            if (gui instanceof bmn) {
                gui = (blk)new GuiCraftingCustom(LabyModCore.getMinecraft().getPlayer().bv, (amu)LabyModCore.getMinecraft().getWorld());
            }
        }
        if (gui instanceof ble) {
            gui = (blk)new ModGuiOptions(bib.z().m, bib.z().t);
        }
        if (gui instanceof bkx) {
            try {
                final bse serverData = (bse)ReflectionHelper.findField(bkx.class, GuiOpenListener.serverListServerData).get(gui);
                gui = (blk)new ModGuiScreenServerList((blk)this.guiMultiplayer, serverData);
            }
            catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        LabyModCore.getForge().setGuiOpenEventGui(event, gui);
        this.lastScreen = gui;
    }
    
    public bnf getGuiMultiplayer() {
        return this.guiMultiplayer;
    }
    
    static {
        IS_MC_18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        serverListServerData = LabyModCore.getMappingAdapter().getServerListServerDataMappings();
        entityRendererItemRendererNames = LabyModCore.getMappingAdapter().getEntityRendererItemRendererMappings();
        entityRendererNames = LabyModCore.getMappingAdapter().getEntityRendererMappings();
        itemRendererNames = LabyModCore.getMappingAdapter().getItemRendererMappings();
        upperChestInventory = LabyModCore.getMappingAdapter().getUpperChestInventoryMappings();
        lowerChestInventory = LabyModCore.getMappingAdapter().getLowerChestInventoryMappings();
    }
}
