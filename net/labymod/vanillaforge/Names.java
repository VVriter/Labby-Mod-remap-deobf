//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge;

import java.util.*;

public enum Names
{
    MINECRAFT_CLASS("net.minecraft.client.Minecraft", "bib"), 
    ENTITY_PLAYER("net.minecraft.entity.player.EntityPlayer", "aed"), 
    RENDER_MANAGER("net.minecraft.client.renderer.entity.RenderManager", "bzf"), 
    GUI_INGAME("net.minecraft.client.gui.GuiIngame", "biq"), 
    ABSTRACT_CLIENT_PLAYER("net.minecraft.client.entity.AbstractClientPlayer", "bua"), 
    GET_FOV_MODIFIER("getFovModifier", "u"), 
    START_MINECRAFT("init", "aq"), 
    TIMER_FIELD("timer", "Y"), 
    TIMER_CLASS("net.minecraft.util.Timer", "bih"), 
    TIMER_RENDER_PARTIAL_TICKS("renderPartialTicks", "b"), 
    RUN_TICK("runTick", "t"), 
    RUN_TICK_KEYBOARD("runTickKeyboard", "aD"), 
    RUN_GAME_LOOP("runGameLoop", "az"), 
    DISPLAY_GUI_SCREEN("displayGuiScreen", "a"), 
    MINECRAFT_MC_PROFILER("mcProfiler", "B"), 
    PROFILER("net.minecraft.profiler.Profiler", "rl"), 
    PROFILER_END_SECTION("endSection", "b"), 
    GUI_SCREEN("net.minecraft.client.gui.GuiScreen", "blk"), 
    GUI_MAIN_MENU("net.minecraft.client.gui.GuiMainMenu", "blr"), 
    CURRENT_SCREEN("currentScreen", "m"), 
    SCALED_RESOLUTION("net.minecraft.client.gui.ScaledResolution", "bit"), 
    UPDATE_CAMERA_AND_RENDER("updateCameraAndRender", "a"), 
    DRAW_SCREEN("drawScreen", "a"), 
    ENTITY_RENDERER("net.minecraft.client.renderer.EntityRenderer", "buq"), 
    ENTITY_RENDERER_MINECRAFT_FIELD("mc", "h"), 
    RENDER_GLOBAL("net.minecraft.client.renderer.RenderGlobal", "buy"), 
    END_START_SECTION("endStartSection", "c"), 
    RENDER_WORLD_PASS("renderWorldPass", "a");
    
    private static final Map<Names, String> nameCaches;
    private String name_112;
    private String name_112_obf;
    
    private Names(final String name_112, final String name_112_obf) {
        this.name_112 = name_112;
        this.name_112_obf = name_112_obf;
    }
    
    public String getName() {
        return this.getName(true);
    }
    
    public String getName(final boolean asm) {
        if (Names.nameCaches.containsKey(this)) {
            String name = Names.nameCaches.get(this);
            if (asm) {
                name = name.replaceAll("\\.", "/");
            }
            return name;
        }
        String returnedName = LabyModTweaker.getInstance().isObfuscated() ? this.name_112_obf : this.name_112;
        Names.nameCaches.put(this, returnedName);
        if (asm) {
            returnedName = returnedName.replaceAll("\\.", "/");
        }
        return returnedName;
    }
    
    static {
        nameCaches = new HashMap<Names, String>();
    }
}
