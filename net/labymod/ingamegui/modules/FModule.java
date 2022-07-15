//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.core.*;
import java.util.*;
import net.labymod.ingamegui.enums.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class FModule extends TextModule
{
    private boolean directionNumber;
    private boolean cardinalPoints;
    private boolean xzDirection;
    private boolean shortenNames;
    
    @Override
    public String[] getKeys() {
        return new String[] { "F" };
    }
    
    @Override
    public String[] getDefaultKeys() {
        return this.getKeys();
    }
    
    @Override
    public List<List<Text>> getTextValues() {
        final List<Text> texts = new ArrayList<Text>();
        final double f = this.getFValue();
        if (this.directionNumber) {
            texts.add(Text.getText(String.valueOf(f), this.valueColor));
        }
        if (this.cardinalPoints || this.xzDirection) {
            final XZDRange range = XZDRange.getRangeByF(f);
            if (range != null) {
                if (this.cardinalPoints) {
                    if (!texts.isEmpty()) {
                        texts.add(Text.getText(" "));
                    }
                    texts.addAll(this.getCardinalPoint(range));
                }
                if (this.xzDirection) {
                    if (!texts.isEmpty()) {
                        texts.add(Text.getText(" "));
                    }
                    texts.addAll(this.getXZD(range));
                }
            }
        }
        if (texts.isEmpty()) {
            texts.add(Text.getText("Useless"));
        }
        return Collections.singletonList(texts);
    }
    
    @Override
    public List<List<Text>> getDefaultTextValues() {
        return Collections.singletonList(Collections.singletonList(Text.getText("?", -1)));
    }
    
    private double getFValue() {
        vg entity = LabyModCore.getMinecraft().getRenderViewEntity();
        if (entity == null) {
            entity = (vg)LabyModCore.getMinecraft().getPlayer();
        }
        double f = LabyModCore.getMath().wrapAngleTo180_float((entity == null) ? 1.0f : entity.v);
        if (f <= 0.0) {
            f += 360.0;
        }
        f /= 8.0;
        f /= 11.0;
        f = Math.round(f * 10.0) / 10.0;
        if (f >= 4.0) {
            f = 0.0;
        }
        return f;
    }
    
    private List<Text> getCardinalPoint(final XZDRange range) {
        final List<XZDRange.DisplayedDirection> displayedDirections = Arrays.asList(range.getDisplayed());
        final List<String> cardinalPoints = new ArrayList<String>();
        final List<Text> texts = new ArrayList<Text>();
        if (displayedDirections.contains(XZDRange.DisplayedDirection.ZMINUS)) {
            cardinalPoints.add(this.shortenNames ? "N" : "North");
        }
        if (displayedDirections.contains(XZDRange.DisplayedDirection.ZPLUS)) {
            cardinalPoints.add(this.shortenNames ? "S" : "South");
        }
        if (displayedDirections.contains(XZDRange.DisplayedDirection.XPLUS)) {
            cardinalPoints.add(this.shortenNames ? "E" : "East");
        }
        if (displayedDirections.contains(XZDRange.DisplayedDirection.XMINUS)) {
            cardinalPoints.add(this.shortenNames ? "W" : "West");
        }
        for (final String point : cardinalPoints) {
            if (!texts.isEmpty()) {
                texts.add(Text.getText("/", this.bracketsColor));
            }
            texts.add(Text.getText(point, this.valueColor));
        }
        return texts;
    }
    
    private List<Text> getXZD(final XZDRange range) {
        final List<Text> texts = new ArrayList<Text>();
        if (range == null) {
            return Collections.emptyList();
        }
        final EnumModuleFormatting displayFormatting = this.getDisplayFormatting();
        Text prefix = null;
        Text suffix = null;
        if (this.cardinalPoints || this.directionNumber) {
            switch (displayFormatting) {
                case SQUARE_BRACKETS: {
                    prefix = Text.getText("[", this.bracketsColor);
                    suffix = Text.getText("]", this.bracketsColor);
                    break;
                }
                case BRACKETS: {
                    prefix = Text.getText("<", this.bracketsColor);
                    suffix = Text.getText(">", this.bracketsColor);
                    break;
                }
                case HYPHEN: {
                    prefix = Text.getText("(", this.bracketsColor);
                    suffix = Text.getText(")", this.bracketsColor);
                }
                case COLON: {}
            }
        }
        if (prefix != null) {
            texts.add(prefix);
        }
        texts.addAll(range.getTexts(this.valueColor, this.bracketsColor));
        if (suffix != null) {
            texts.add(suffix);
        }
        return texts;
    }
    
    public void loadSettings() {
        this.directionNumber = Boolean.valueOf(this.getAttribute("directionNumber", "true"));
        this.cardinalPoints = Boolean.valueOf(this.getAttribute("cardinalPoints", "true"));
        this.xzDirection = Boolean.valueOf(this.getAttribute("xzDirection", "true"));
        this.shortenNames = Boolean.valueOf(this.getAttribute("shortenNames", "false"));
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.COMPASS), "Direction Number", "directionNumber"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.COMPASS), "Cardinal points", "cardinalPoints"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.COMPASS), "XZ Direction", "xzDirection"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SIGN), "Shorten cardinal names", "shortenNames"));
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getSettingName() {
        return "f";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 2;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    private enum XZDRange
    {
        ZPLUS(0.0, 0.3, new DisplayedDirection[] { DisplayedDirection.ZPLUS }), 
        ZPLUS_XMINUS(0.3, 0.8, new DisplayedDirection[] { DisplayedDirection.XMINUS, DisplayedDirection.ZPLUS }), 
        XMINUS(0.8, 1.4, new DisplayedDirection[] { DisplayedDirection.XMINUS }), 
        XMINUS_ZMINUS(1.4, 1.8, new DisplayedDirection[] { DisplayedDirection.XMINUS, DisplayedDirection.ZMINUS }), 
        ZMINUS(1.8, 2.4, new DisplayedDirection[] { DisplayedDirection.ZMINUS }), 
        ZMINUS_XPLUS(2.4, 2.8, new DisplayedDirection[] { DisplayedDirection.XPLUS, DisplayedDirection.ZMINUS }), 
        XPLUS(2.8, 3.4, new DisplayedDirection[] { DisplayedDirection.XPLUS }), 
        XPLUS_ZPLUS(3.4, 3.8, new DisplayedDirection[] { DisplayedDirection.XPLUS, DisplayedDirection.ZPLUS }), 
        ZPLUS_2(3.8, 4.0, new DisplayedDirection[] { DisplayedDirection.ZPLUS });
        
        private double min;
        private double max;
        private DisplayedDirection[] displayed;
        
        private XZDRange(final double min, final double max, final DisplayedDirection[] displayed) {
            this.min = min;
            this.max = max;
            this.displayed = displayed;
        }
        
        public static XZDRange getRangeByF(final double f) {
            if (f >= 4.0) {
                return XZDRange.ZPLUS_2;
            }
            for (final XZDRange range : values()) {
                if (f >= range.min && f < range.max) {
                    return range;
                }
            }
            return null;
        }
        
        public List<Text> getTexts(final int valueColor, final int bracketsColor) {
            final List<Text> texts = new ArrayList<Text>();
            for (final DisplayedDirection direction : this.displayed) {
                if (texts.size() > 0) {
                    texts.add(Text.getText(", ", bracketsColor));
                }
                texts.add(direction.getText(valueColor));
            }
            return texts;
        }
        
        public DisplayedDirection[] getDisplayed() {
            return this.displayed;
        }
        
        private enum DisplayedDirection
        {
            XPLUS("X+"), 
            XMINUS("X-"), 
            ZPLUS("Z+"), 
            ZMINUS("Z-");
            
            private String text;
            
            private DisplayedDirection(final String text) {
                this.text = text;
            }
            
            public Text getText(final int valueColor) {
                return Text.getText(this.text, valueColor);
            }
        }
    }
}
