//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import net.labymod.ingamegui.enums.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.ingamegui.*;
import java.awt.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.ingamegui.modules.*;
import net.labymod.ingamegui.modules.item.*;

public class DefaultValues
{
    public static final boolean GRID_ENABLED = false;
    public static final int PREFIX_COLOR;
    public static final int BRACKETS_COLOR;
    public static final int VALUE_COLOR;
    public static final int BACKGROUND_COLOR;
    public static final int BOLD_FORMATTING = 0;
    public static final int ITALIC_FORMATTING = 0;
    public static final int UNDERLINE_FORMATTING = 0;
    public static final int POTION_NAME_COLOR;
    public static final int POTION_AMPLIFIER_COLOR;
    public static final int POTION_DURATION_COLOR;
    public static final EnumModuleFormatting DEFAULT_FORMATTING;
    public static final ItemModule.DurabilityVisibility DEFAULT_DURABILITY_VISIBILITY;
    public static final Set<Class<? extends Module>> ATTACH_MODULES_RIGHT;
    
    static {
        PREFIX_COLOR = new Color(255, 170, 0).getRGB();
        BRACKETS_COLOR = new Color(170, 170, 170).getRGB();
        VALUE_COLOR = new Color(255, 255, 255).getRGB();
        BACKGROUND_COLOR = new Color(0, 0, 0).getRGB();
        POTION_NAME_COLOR = ModColor.YELLOW.getColor().getRGB();
        POTION_AMPLIFIER_COLOR = ModColor.YELLOW.getColor().getRGB();
        POTION_DURATION_COLOR = new Color(255, 255, 255).getRGB();
        DEFAULT_FORMATTING = EnumModuleFormatting.SQUARE_BRACKETS;
        DEFAULT_DURABILITY_VISIBILITY = ItemModule.DurabilityVisibility.DURABILITY_MAX;
        ATTACH_MODULES_RIGHT = new HashSet<Class<? extends Module>>() {
            {
                this.add((Class<? extends Module>)PotionEffectsModule.class);
                this.add((Class<? extends Module>)HelmetModule.class);
                this.add((Class<? extends Module>)ChestplateModule.class);
                this.add((Class<? extends Module>)LeggingsModule.class);
                this.add((Class<? extends Module>)BootsModule.class);
                this.add((Class<? extends Module>)HeldItemModule.class);
                this.add((Class<? extends Module>)ArrowAmountModule.class);
            }
        };
    }
}
