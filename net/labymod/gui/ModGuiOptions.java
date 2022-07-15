//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.main.lang.*;
import net.labymod.core.*;
import net.labymod.gui.elements.*;
import net.labymod.settings.*;
import java.io.*;
import net.labymod.main.*;

public class ModGuiOptions extends ble
{
    private static final boolean MC18;
    
    public ModGuiOptions(final blk p_i1046_1_, final bid p_i1046_2_) {
        super(p_i1046_1_, p_i1046_2_);
    }
    
    public void b() {
        super.b();
        this.n.add(new bja(205, this.l / 2 + 5, this.m / 6 + 24 - 6, 150, 20, LanguageManager.translate("settings_title")));
        if (!ModGuiOptions.MC18 && LabyMod.getSettings().betterShaderSelection) {
            this.n.add(new bja(8675309, this.l / 2 - 155, this.m / 6 + 48 - 6 - 24, 150, 20, "Super Secret Settings..."));
        }
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 200) {
            if (LabyModCore.getMinecraft().getPlayer() != null) {
                this.j.a((blk)new ModGuiIngameMenu());
            }
            else {
                this.j.a((blk)new blr());
            }
            return;
        }
        if (button.k == 8675309 && LabyMod.getSettings().betterShaderSelection) {
            this.j.a((blk)new GuiShaderSelection((blk)this));
            return;
        }
        if (button.k == 205) {
            Tabs.lastOpenScreen = null;
            this.j.a((blk)new LabyModSettingsGui((blk)this));
            return;
        }
        super.a(button);
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
