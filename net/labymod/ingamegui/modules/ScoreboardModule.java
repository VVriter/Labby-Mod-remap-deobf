//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.core.*;
import net.labymod.main.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import net.labymod.ingamegui.enums.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ScoreboardModule extends Module
{
    private static bhg dummyScoreObjective;
    private static boolean enabled;
    private EnumScoreVisibility scoreVisibility;
    private double zoomValue;
    private boolean background;
    private boolean entries;
    private boolean showDisplayName;
    private boolean centerDisplayName;
    private double scoreboardWidth;
    private double scoreboardHeight;
    private int slot;
    
    public ScoreboardModule() {
        this.zoomValue = 100.0;
        this.scoreboardWidth = 50.0;
        this.scoreboardHeight = 50.0;
        this.slot = -1;
    }
    
    public void init() {
        super.init();
        try {
            this.slot = Integer.parseInt(this.getAttribute("slot", "-1"));
        }
        catch (Exception ex) {
            this.slot = -1;
        }
    }
    
    public void draw(final double x, final double y, final double rightX) {
        if (this.isShown()) {
            final bhk scoreboard = LabyModCore.getMinecraft().getWorld().af();
            bhg scoreobjective = null;
            final bhh scoreplayerteam = scoreboard.g(LabyModCore.getMinecraft().getPlayer().h_());
            if (scoreplayerteam != null) {
                final int i1 = LabyModCore.getMinecraft().getTeamColorIndex(scoreplayerteam);
                if (i1 >= 0) {
                    scoreobjective = scoreboard.a(3 + i1);
                }
            }
            final bhg scoreobjective2 = (scoreobjective != null) ? scoreobjective : scoreboard.a(1);
            if (scoreobjective2 != null) {
                this.renderScoreboard(scoreobjective2, x, y, !this.isLastRightBound());
            }
        }
        else {
            if (ScoreboardModule.dummyScoreObjective == null) {
                ScoreboardModule.dummyScoreObjective = LabyModCore.getMinecraft().getDummyScoreObjective();
            }
            this.renderScoreboard(ScoreboardModule.dummyScoreObjective, x, y, !this.isLastRightBound());
        }
    }
    
    private void renderScoreboard(final bhg scoreboardObjective, double x, double y, final boolean rightBound) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.G();
        bus.a(this.zoomValue / 100.0, this.zoomValue / 100.0, 1.0);
        x /= this.zoomValue / 100.0;
        y /= this.zoomValue / 100.0;
        final bhk scoreboard = scoreboardObjective.a();
        Collection<bhi> collection = (Collection<bhi>)scoreboard.i(scoreboardObjective);
        final List<bhi> list = (List<bhi>)Lists.newArrayList(Iterables.filter((Iterable)collection, (Predicate)new Predicate<bhi>() {
            public boolean apply(final bhi score) {
                return score.e() != null && !score.e().startsWith("#");
            }
        }));
        if (list.size() > 15) {
            collection = (Collection<bhi>)Lists.newArrayList(Iterables.skip((Iterable)list, collection.size() - 15));
        }
        else {
            collection = list;
        }
        if (!this.entries) {
            collection = (Collection<bhi>)Lists.newArrayList();
        }
        int scoreboardWidth = LabyModCore.getMinecraft().getFontRenderer().a(scoreboardObjective.d()) + 2;
        Integer scoreIndex = null;
        boolean failed = collection.size() < 2;
        for (final bhi score : collection) {
            final bhh scoreplayerteam = scoreboard.g(score.e());
            final String scoreName = bhh.a((bhm)scoreplayerteam, score.e()) + ((this.scoreVisibility == EnumScoreVisibility.HIDDEN) ? " " : (": " + ModColor.RED + score.c()));
            scoreboardWidth = Math.max(scoreboardWidth, LabyModCore.getMinecraft().getFontRenderer().a(scoreName));
            if (scoreIndex == null) {
                scoreIndex = score.c();
            }
            else {
                if (failed) {
                    continue;
                }
                final int scorePoints = score.c();
                if (scorePoints == scoreIndex + 1) {
                    scoreIndex = scorePoints;
                }
                else {
                    failed = true;
                }
            }
        }
        final boolean scoreVisible = (this.scoreVisibility == EnumScoreVisibility.AUTO) ? failed : (this.scoreVisibility == EnumScoreVisibility.VISIBLE);
        final double totalHeight = collection.size() * LabyModCore.getMinecraft().getFontRenderer().a + (this.showDisplayName ? LabyModCore.getMinecraft().getFontRenderer().a : 0);
        final double scoreboardPositionBottom = y + totalHeight + 1.0;
        final double scoreboardPositionX = x;
        int index = 0;
        double positionY = scoreboardPositionBottom;
        double scoreboardRight = x + scoreboardWidth;
        for (final bhi score2 : collection) {
            ++index;
            final bhh scoreplayerteam2 = scoreboard.g(score2.e());
            final String scoreName2 = bhh.a((bhm)scoreplayerteam2, score2.e());
            final String scoreNumber = ModColor.RED + "" + score2.c();
            positionY = scoreboardPositionBottom - index * LabyModCore.getMinecraft().getFontRenderer().a;
            scoreboardRight = x + scoreboardWidth;
            if (this.entries) {
                if (this.background) {
                    draw.drawRect(scoreboardPositionX, positionY, scoreboardRight, positionY + LabyModCore.getMinecraft().getFontRenderer().a, 1342177280);
                }
                if (rightBound) {
                    draw.drawRightString(scoreName2, scoreboardPositionX + scoreboardWidth - 2.0, positionY);
                }
                else {
                    draw.drawString(scoreName2, scoreboardPositionX + 2.0, positionY);
                }
                if (!scoreVisible) {
                    continue;
                }
                if (rightBound) {
                    draw.drawString(scoreNumber, x, positionY);
                }
                else {
                    draw.drawRightString(scoreNumber, x + scoreboardWidth, positionY);
                }
            }
        }
        if (this.showDisplayName) {
            final String displayName = scoreboardObjective.d();
            if (this.background) {
                draw.drawRect(scoreboardPositionX, positionY - LabyModCore.getMinecraft().getFontRenderer().a - 1.0, scoreboardRight, positionY - 1.0, 1610612736);
            }
            if (this.background) {
                draw.drawRect(scoreboardPositionX, positionY - 1.0, scoreboardRight, positionY, 1342177280);
            }
            if (this.centerDisplayName) {
                draw.drawCenteredString(displayName, scoreboardPositionX + scoreboardWidth / 2, positionY - LabyModCore.getMinecraft().getFontRenderer().a);
            }
            else if (rightBound) {
                draw.drawRightString(displayName, scoreboardPositionX + scoreboardWidth - 1.0, positionY - LabyModCore.getMinecraft().getFontRenderer().a);
            }
            else {
                draw.drawString(displayName, scoreboardPositionX + 1.0, positionY - LabyModCore.getMinecraft().getFontRenderer().a);
            }
        }
        this.scoreboardWidth = scoreboardWidth;
        this.scoreboardHeight = totalHeight + 1.0;
        bus.H();
    }
    
    public boolean isShown() {
        if (LabyModCore.getMinecraft().getWorld() == null) {
            return false;
        }
        final bhk scoreboard = LabyModCore.getMinecraft().getWorld().af();
        bhg scoreobjective = null;
        final bhh scoreplayerteam = scoreboard.g(LabyModCore.getMinecraft().getPlayer().h_());
        if (scoreplayerteam != null) {
            final int i1 = LabyModCore.getMinecraft().getTeamColorIndex(scoreplayerteam);
            if (i1 >= 0) {
                scoreobjective = scoreboard.a(3 + i1);
            }
        }
        final bhg scoreobjective2 = (scoreobjective != null) ? scoreobjective : scoreboard.a(1);
        return scoreobjective2 != null;
    }
    
    public void settingUpdated(final boolean enabled) {
        super.settingUpdated(enabled);
        ScoreboardModule.enabled = super.isEnabled(EnumDisplayType.INGAME);
        this.slot = -1;
    }
    
    public void loadSettings() {
        this.zoomValue = Integer.valueOf(this.getAttribute("size", "100"));
        this.scoreVisibility = EnumScoreVisibility.valueOf(this.getAttribute("scoreVisibility", "AUTO"));
        this.background = Boolean.valueOf(this.getAttribute("background", "true"));
        this.entries = Boolean.valueOf(this.getAttribute("entries", "true"));
        this.entries = Boolean.valueOf(this.getAttribute("entries", "true"));
        this.showDisplayName = Boolean.valueOf(this.getAttribute("showDisplayName", "true"));
        this.centerDisplayName = Boolean.valueOf(this.getAttribute("centerDisplayName", "true"));
        ScoreboardModule.enabled = super.isEnabled(EnumDisplayType.INGAME);
    }
    
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings((List)settingsElements);
        final DropDownMenu<String> dropDownMenu = (DropDownMenu<String>)new DropDownMenu("Scores", 0, 0, 0, 0);
        dropDownMenu.addOption((Object)"VISIBLE");
        dropDownMenu.addOption((Object)"HIDDEN");
        dropDownMenu.addOption((Object)"AUTO");
        dropDownMenu.setSelected((Object)"VISIBLE");
        final DropDownElement dropDownElement = new DropDownElement(this, "Scores", "scoreVisibility", dropDownMenu, null);
        settingsElements.add(dropDownElement);
        settingsElements.add(new SliderElement(this, this.getModuleIcon(this.getSettingName(), "size"), "Size", "size").setRange(50, 150));
        settingsElements.add(new BooleanElement(this, this.getModuleIcon(this.getSettingName(), "background"), "Background", "background"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.HOPPER), "Score entries", "entries"));
        settingsElements.add(new BooleanElement(this, this.getModuleIcon(this.getSettingName(), "showdisplayname"), "Show Displayname", "showDisplayName"));
        settingsElements.add(new BooleanElement(this, new ControlElement.IconData(Material.SIGN), "Center Displayname", "centerDisplayName"));
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.SIGN);
    }
    
    public double getHeight() {
        return this.scoreboardHeight * (this.zoomValue / 100.0);
    }
    
    public double getWidth() {
        return this.scoreboardWidth * (this.zoomValue / 100.0);
    }
    
    public String getSettingName() {
        return "scoreboard";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 11;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
    
    public static boolean isEnabled() {
        return ScoreboardModule.enabled;
    }
    
    public void setSlot(final int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public enum EnumScoreVisibility
    {
        VISIBLE, 
        HIDDEN, 
        AUTO;
    }
}
