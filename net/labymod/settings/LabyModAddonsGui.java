//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.addon.online.*;
import net.labymod.addon.*;
import net.labymod.main.lang.*;
import net.labymod.core.*;
import org.lwjgl.input.*;
import net.labymod.api.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.gui.elements.*;
import com.google.common.collect.*;
import net.labymod.addon.online.info.*;
import net.labymod.ingamegui.*;
import java.io.*;
import java.util.*;
import net.labymod.settings.elements.*;

public class LabyModAddonsGui extends blk
{
    private blk lastScreen;
    private Scrollbar scrollbar;
    private bja buttonDone;
    private bja buttonBack;
    private bja buttonWarningExitGame;
    private bja buttonWarningRestartLater;
    private ModTextField fieldSearch;
    private boolean displayRestartWarning;
    private AddonElement openedAddonSettings;
    private AddonElement mouseOverAddonEntry;
    private SettingsElement mouseOverElement;
    private List<SettingsElement> listedElementsStored;
    private List<SettingsElement> tempElementsStored;
    private ArrayList<SettingsElement> path;
    private double preScrollPos;
    private EnumSortingState selectedSortingState;
    private EnumSortingState hoveredSortingState;
    private List<AddonInfo> sortedAddonInfoList;
    private int[] enabledFilter;
    
    public LabyModAddonsGui() {
        this(null);
    }
    
    public LabyModAddonsGui(final blk lastScreen) {
        this.scrollbar = new Scrollbar(1);
        this.displayRestartWarning = false;
        this.openedAddonSettings = null;
        this.listedElementsStored = new ArrayList<SettingsElement>();
        this.tempElementsStored = new ArrayList<SettingsElement>();
        this.path = new ArrayList<SettingsElement>();
        this.preScrollPos = 0.0;
        this.selectedSortingState = EnumSortingState.TRENDING;
        this.hoveredSortingState = null;
        this.sortedAddonInfoList = (List<AddonInfo>)AddonInfoManager.getInstance().getAddonInfoList();
        this.enabledFilter = null;
        this.lastScreen = lastScreen;
        AddonInfoManager.getInstance().init();
        AddonInfoManager.getInstance().createElementsForAddons();
        if (!AddonLoader.getAddons().isEmpty()) {
            this.selectedSortingState = EnumSortingState.INSTALLED;
        }
        if (AddonInfoManager.getInstance().isLoaded()) {
            this.handleCheckBox(false, 0, 0, 0);
        }
    }
    
    public void b() {
        super.b();
        this.n.clear();
        this.doQuery();
        this.sortAddonInfoList();
        this.scrollbar.setPosition(this.l / 2 + 152, this.isInSubSettings() ? 50 : 80, this.l / 2 + 152 + 4, this.m - 15);
        this.scrollbar.setSpeed(20);
        this.scrollbar.init();
        if (this.lastScreen != null) {
            this.n.add(this.buttonDone = new bja(0, this.l / 2 + 50, this.m - 25, 100, 20, LanguageManager.translate("button_done")));
        }
        this.n.add(this.buttonBack = new bja(1, this.l / 2 - 100, 20, 22, 20, "<"));
        this.n.add(this.buttonWarningRestartLater = new bja(2, this.l / 2 - 95, this.m / 2 + 20, 90, 20, LanguageManager.translate("button_restart_later")));
        this.n.add(this.buttonWarningExitGame = new bja(3, this.l / 2 + 5, this.m / 2 + 20, 90, 20, LanguageManager.translate("button_exit_game")));
        (this.fieldSearch = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 150, 35, 200, 16)).setBlackBox(false);
        this.fieldSearch.setPlaceHolder(LanguageManager.translate("search_textbox_placeholder"));
        Keyboard.enableRepeatEvents(true);
        if (this.isInSubSettings()) {
            for (final SettingsElement settingsElement : this.listedElementsStored) {
                settingsElement.init();
            }
        }
        Tabs.initGui((blk)this);
    }
    
    public void m() {
        for (final LabyModAddon addon : AddonLoader.getAddons()) {
            addon.saveConfig();
        }
        super.m();
        Keyboard.enableRepeatEvents(false);
        if (isRestartRequired() && !this.displayRestartWarning) {
            this.displayRestartWarning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bib.z().a((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (!(bib.z().m instanceof LabyModAddonsGui)) {
                                final LabyModAddonsGui gui = new LabyModAddonsGui(bib.z().m);
                                gui.displayRestartWarning = true;
                                bib.z().a((blk)gui);
                            }
                        }
                    });
                }
            }).start();
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.drawElementList(mouseX, mouseY);
        if (!AddonInfoManager.getInstance().isLoaded() || AddonInfoManager.getInstance().getAddonInfoList().isEmpty()) {
            draw.drawCenteredString(LanguageManager.translate("button_no_addons_available", new Object[] { Source.ABOUT_MC_VERSION }), this.l / 2, this.m / 2);
        }
        final int top = this.isInSubSettings() ? 45 : 75;
        draw.drawOverlayBackground(0, top);
        draw.drawGradientShadowTop(top, 0.0, this.l);
        final int bottom = (this.lastScreen == null) ? (this.m - 10) : (this.m - 30);
        draw.drawOverlayBackground(bottom, this.m);
        draw.drawGradientShadowBottom(bottom, 0.0, this.l);
        this.scrollbar.draw(mouseX, mouseY);
        if (this.buttonDone != null) {
            this.buttonDone.l = !this.displayRestartWarning;
        }
        this.buttonBack.m = (this.openedAddonSettings != null);
        this.buttonBack.l = !this.displayRestartWarning;
        this.buttonWarningExitGame.m = this.displayRestartWarning;
        this.buttonWarningRestartLater.m = this.displayRestartWarning;
        if (AddonInfoManager.getInstance().isLoaded()) {
            if (!this.isInSubSettings()) {
                this.drawSortingTabs(mouseX, mouseY);
            }
            else {
                draw.drawString(this.openedAddonSettings.getAddonInfo().getName(), this.l / 2 - 100 + 30, 25.0);
                this.openedAddonSettings.drawIcon(this.l / 2 + 100 - 20, 20, 20, 20);
            }
        }
        if (!this.isInSubSettings() && AddonInfoManager.getInstance().isLoaded()) {
            this.drawCategoryFilterMenu(mouseX, mouseY);
        }
        super.a(mouseX, mouseY, partialTicks);
        if (!this.isInSubSettings()) {
            Tabs.drawScreen((blk)this, mouseX, mouseY);
        }
        if (this.displayRestartWarning) {
            draw.drawIngameBackground();
            draw.drawRectangle(this.l / 2 - 100 - 2, this.m / 2 - 50 - 2, this.l / 2 + 100 + 2, this.m / 2 + 50 + 2, Integer.MIN_VALUE);
            draw.drawRectangle(this.l / 2 - 100, this.m / 2 - 50, this.l / 2 + 100, this.m / 2 + 50, ModColor.toRGB(20, 20, 20, 144));
            LabyModCore.getMinecraft().drawButton(this.buttonWarningRestartLater, mouseX, mouseY);
            LabyModCore.getMinecraft().drawButton(this.buttonWarningExitGame, mouseX, mouseY);
            draw.drawCenteredString(LanguageManager.translate("warning_title"), this.l / 2, this.m / 2 - 44);
            final List<String> list = draw.listFormattedStringToWidth(LanguageManager.translate("warning_content"), 190);
            int lineY = 0;
            for (final String line : list) {
                draw.drawCenteredString(ModColor.cl("4") + line, this.l / 2, this.m / 2 - 30 + lineY);
                lineY += 10;
            }
        }
        if (this.fieldSearch != null && !this.isInSubSettings()) {
            this.fieldSearch.drawTextBox();
        }
        if (this.openedAddonSettings != null) {
            this.openedAddonSettings.onRenderPreview(mouseX, mouseY, partialTicks);
        }
    }
    
    private void drawCategoryFilterMenu(final int mouseX, final int mouseY) {
        int x = this.l / 2 + 60;
        final int y = 36;
        for (final CheckBox checkBox : AddonInfoManager.getInstance().getCategorieCheckboxList()) {
            checkBox.setX(x);
            checkBox.setY(y);
            checkBox.drawCheckbox(mouseX, mouseY);
            x += 25;
        }
    }
    
    private void doQuery() {
        this.tempElementsStored.clear();
        if (this.openedAddonSettings != null) {
            if (this.path.isEmpty()) {
                this.tempElementsStored.addAll(this.openedAddonSettings.getSubSettings());
            }
            else {
                final SettingsElement currentOpenElement = this.path.get(this.path.size() - 1);
                this.tempElementsStored.addAll(currentOpenElement.getSubSettings().getElements());
            }
        }
        this.listedElementsStored = this.tempElementsStored;
    }
    
    private void drawSortingTabs(final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        int tabX = this.l / 2 - 150;
        final int tabY = 63;
        this.hoveredSortingState = null;
        if (AddonInfoManager.getInstance().isLoaded()) {
            for (final EnumSortingState sortingState : EnumSortingState.values()) {
                final boolean selected = this.selectedSortingState == sortingState;
                final int tabLen = draw.getStringWidth(sortingState.getDisplayName()) + 6 + (selected ? 6 : 0);
                String prefix = "";
                if (sortingState == EnumSortingState.INSTALLED) {
                    tabX = this.l / 2 + 150 - tabLen;
                    prefix = ModColor.cl(selected ? "6" : "8");
                }
                this.drawSingleTab(sortingState, prefix, tabX, tabY, tabLen, selected, mouseX, mouseY);
                tabX += tabLen + 1;
            }
        }
    }
    
    private void drawSingleTab(final EnumSortingState sortingState, final String prefix, final int tabX, final int tabY, final int length, final boolean selected, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int tabHeight = 12;
        final boolean hovered = mouseX > tabX && mouseX < tabX + length && mouseY > tabY && mouseY < tabY + tabHeight;
        final int animate = (selected || hovered) ? (selected ? 2 : 1) : 0;
        final int tabMid = tabX + length / 2;
        draw.drawRectangle(tabX, tabY - animate * 2, tabX + length, tabY + tabHeight, ModColor.toRGB(5, 5, 5, 140));
        draw.drawRectBorder(tabX, tabY - animate * 2, tabX + length, tabY + tabHeight, ModColor.toRGB(5, 5, 5, 140), 1.0);
        if (selected) {
            draw.drawRectangle(tabX + 1, tabY - animate * 2 + 1, tabX + length - 1, tabY + tabHeight, ModColor.toRGB(55, 55, 55, 65));
        }
        draw.drawCenteredString(((selected || hovered) ? ModColor.cl("f") : ModColor.cl("8")) + prefix + sortingState.getDisplayName(), tabMid, tabY + 2 - animate);
        if (hovered) {
            this.hoveredSortingState = sortingState;
        }
    }
    
    private void drawElementList(final int mouseX, final int mouseY) {
        double totalEntryHeight = 0.0;
        this.mouseOverAddonEntry = null;
        this.mouseOverElement = null;
        boolean canRenderDescription = true;
        int count = 0;
        if (this.isInSubSettings()) {
            for (int zLevel = 0; zLevel < 2; ++zLevel) {
                totalEntryHeight = 0.0;
                double listY = 50.0 + this.scrollbar.getScrollY();
                this.listedElementsStored = (List<SettingsElement>)Lists.reverse((List)this.listedElementsStored);
                for (final SettingsElement settingsElement : this.listedElementsStored) {
                    listY += settingsElement.getEntryHeight() + 2;
                    totalEntryHeight += settingsElement.getEntryHeight() + 2;
                }
                for (final SettingsElement settingsElement : this.listedElementsStored) {
                    listY -= settingsElement.getEntryHeight() + 2;
                    totalEntryHeight -= settingsElement.getEntryHeight() + 2;
                    if (((!(settingsElement instanceof DropDownElement) || settingsElement instanceof ColorPickerCheckBoxBulkElement) && zLevel == 0) || ((settingsElement instanceof DropDownElement || settingsElement instanceof ColorPickerCheckBoxBulkElement) && zLevel == 1)) {
                        settingsElement.draw(this.l / 2 - 100, (int)listY, this.l / 2 + 100, (int)(listY + settingsElement.getEntryHeight()), mouseX, mouseY);
                        if (canRenderDescription && settingsElement instanceof DropDownElement) {
                            canRenderDescription = !((DropDownElement)settingsElement).getDropDownMenu().isOpen();
                        }
                        if (!settingsElement.isMouseOver()) {
                            continue;
                        }
                        this.mouseOverElement = settingsElement;
                    }
                }
                for (final SettingsElement settingsElement : this.listedElementsStored) {
                    listY += settingsElement.getEntryHeight() + 2;
                    totalEntryHeight += settingsElement.getEntryHeight() + 2;
                }
                this.listedElementsStored = (List<SettingsElement>)Lists.reverse((List)this.listedElementsStored);
            }
            count = this.listedElementsStored.size();
        }
        else {
            final boolean ranked = this.selectedSortingState.isRanked();
            int rank = 1;
            double listY2 = 80.0 + this.scrollbar.getScrollY();
            for (final AddonInfo addonInfo : this.sortedAddonInfoList) {
                boolean visible = this.enabledFilter == null;
                if (!visible) {
                    for (final int i : this.enabledFilter) {
                        if (i == addonInfo.getCategory()) {
                            visible = true;
                        }
                    }
                }
                if (!visible) {
                    continue;
                }
                final boolean hasInstalled = AddonLoader.hasInstalled(addonInfo);
                if (this.selectedSortingState == EnumSortingState.VERIFIED && (this.fieldSearch == null || this.fieldSearch.getText().isEmpty())) {
                    if (!(addonInfo instanceof OnlineAddonInfo)) {
                        continue;
                    }
                    if (!((OnlineAddonInfo)addonInfo).isVerified()) {
                        continue;
                    }
                }
                if (this.selectedSortingState == EnumSortingState.INSTALLED) {
                    if (!hasInstalled) {
                        continue;
                    }
                }
                else if (!(addonInfo instanceof OnlineAddonInfo)) {
                    if (this.fieldSearch == null) {
                        continue;
                    }
                    if (this.fieldSearch.getText().isEmpty()) {
                        continue;
                    }
                }
                final AddonElement addonElement = addonInfo.getAddonElement();
                addonElement.canHover(mouseY >= 75 && mouseY <= this.m - 30).draw(this.l / 2 - 150, (int)listY2, this.l / 2 + 150, (int)(listY2 + addonElement.getEntryHeight()), mouseX, mouseY);
                if (ranked) {
                    String prefix = "8";
                    switch (rank) {
                        case 1: {
                            prefix = "e";
                            break;
                        }
                        case 2: {
                            prefix = "7";
                            break;
                        }
                        case 3: {
                            prefix = "6";
                            break;
                        }
                    }
                    LabyMod.getInstance().getDrawUtils().drawRightString(ModColor.cl(prefix) + rank + ".", this.l / 2 - 152, listY2 + 3.0, 0.7);
                }
                if (addonElement.isMouseOver()) {
                    this.mouseOverAddonEntry = addonInfo.getAddonElement();
                }
                listY2 += addonElement.getEntryHeight() + 2;
                totalEntryHeight += addonElement.getEntryHeight() + 2;
                ++rank;
            }
            count = AddonInfoManager.getInstance().getAddonInfoList().size();
        }
        if (canRenderDescription) {
            this.drawDescriptions(mouseX, mouseY, 80, this.m - 30);
        }
        this.scrollbar.setEntryHeight(totalEntryHeight / count);
        this.scrollbar.update(count);
    }
    
    private void drawDescriptions(final int mouseX, final int mouseY, final int minY, final int maxY) {
        for (final SettingsElement element : this.listedElementsStored) {
            if (element.isMouseOver() && mouseY > minY && mouseY < maxY) {
                element.drawDescription(mouseX, mouseY, this.l);
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (!this.isInSubSettings() && !this.displayRestartWarning && Tabs.mouseClicked((blk)this)) {
            return;
        }
        if (this.displayRestartWarning) {
            return;
        }
        if (this.hoveredSortingState != null) {
            this.selectedSortingState = this.hoveredSortingState;
            this.sortAddonInfoList();
        }
        if (!this.isInSubSettings()) {
            this.handleCheckBox(true, mouseX, mouseY, mouseButton);
        }
        if (this.fieldSearch != null) {
            this.fieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.openedAddonSettings != null) {
            this.openedAddonSettings.onMouseClickedPreview(mouseX, mouseY, mouseButton);
        }
        final int top = this.isInSubSettings() ? 45 : 75;
        final int bottom = (this.lastScreen == null) ? (this.m - 10) : (this.m - 30);
        if (mouseY < top || mouseY > bottom) {
            return;
        }
        if (this.isInSubSettings()) {
            this.unfocusSubListTextfields(mouseX, mouseY, mouseButton);
            for (final SettingsElement element : this.listedElementsStored) {
                if (element instanceof DropDownElement && ((DropDownElement)element).onClickDropDown(mouseX, mouseY, mouseButton)) {
                    return;
                }
                if (element instanceof ColorPickerCheckBoxBulkElement && ((ColorPickerCheckBoxBulkElement)element).onClickBulkEntry(mouseX, mouseY, mouseButton)) {
                    return;
                }
            }
            if (this.mouseOverElement != null) {
                boolean flag = true;
                if (this.mouseOverElement instanceof ControlElement) {
                    final ControlElement element2 = (ControlElement)this.mouseOverElement;
                    final boolean mouseOver = mouseX < LabyMod.getSettings().moduleEditorSplitX - element2.getObjectWidth() - element2.getSubListButtonWidth() - (this.scrollbar.isHidden() ? 5 : 10) - 2;
                    final Module module = element2.getModule();
                    if (module != null && mouseOver) {
                        this.unfocusSubListTextfields(mouseX, mouseY, mouseButton);
                        flag = false;
                    }
                }
                if (flag) {
                    this.mouseOverElement.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
        else {
            for (final AddonInfo addonInfo : AddonInfoManager.getInstance().getAddonInfoList()) {
                final AddonElement element3 = addonInfo.getAddonElement();
                element3.mouseClicked(mouseX, mouseY, mouseButton);
                if (element3.isHoverSubSettingsButton()) {
                    this.openedAddonSettings = this.mouseOverAddonEntry;
                    this.b();
                }
            }
        }
        if (this.mouseOverElement != null && this.mouseOverElement instanceof ControlElement) {
            final ControlElement element4 = (ControlElement)this.mouseOverElement;
            if (element4.hasSubList() && element4.getButtonAdvanced().a() && element4.getButtonAdvanced().l) {
                element4.getButtonAdvanced().a(this.j.U());
                this.path.add((SettingsElement)element4);
                this.preScrollPos = this.scrollbar.getScrollY();
                this.scrollbar.setScrollY(0.0);
                this.b();
            }
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    private void unfocusSubListTextfields(final int mouseX, final int mouseY, final int mouseButton) {
        for (final SettingsElement moduleElement : this.listedElementsStored) {
            moduleElement.unfocus(mouseX, mouseY, mouseButton);
        }
    }
    
    private void sortAddonInfoList() {
        String query = (this.fieldSearch == null) ? null : this.fieldSearch.getText().toLowerCase().replaceAll(" ", "");
        if (query != null && query.isEmpty()) {
            query = null;
        }
        final List<AddonInfo> sortedAddonInfoList = new ArrayList<AddonInfo>();
        for (final AddonInfo addonInfo : AddonInfoManager.getInstance().getAddonInfoList()) {
            if (query == null || this.match(addonInfo.getName(), query) || this.match(addonInfo.getDescription(), query) || this.match(addonInfo.getAuthor(), query)) {
                sortedAddonInfoList.add(addonInfo);
            }
        }
        final int index = this.selectedSortingState.ordinal();
        Collections.sort(sortedAddonInfoList, new Comparator<AddonInfo>() {
            @Override
            public int compare(final AddonInfo a, final AddonInfo b) {
                final int aId = (a instanceof OnlineAddonInfo) ? ((OnlineAddonInfo)a).getSorting()[index] : 0;
                final int bId = (b instanceof OnlineAddonInfo) ? ((OnlineAddonInfo)b).getSorting()[index] : 0;
                return aId - bId;
            }
        });
        this.sortedAddonInfoList = sortedAddonInfoList;
    }
    
    private boolean match(final String s1, final String s2) {
        return s1.toLowerCase().replaceAll(" ", "").contains(s2);
    }
    
    private void handleCheckBox(final boolean handleMouseClick, final int mouseX, final int mouseY, final int mouseButton) {
        if (!AddonInfoManager.getInstance().isLoaded()) {
            return;
        }
        final CheckBox[] checkBoxList = AddonInfoManager.getInstance().getCategorieCheckboxList();
        this.enabledFilter = new int[checkBoxList.length];
        int index = 0;
        for (final CheckBox checkBox : checkBoxList) {
            if (handleMouseClick) {
                checkBox.mouseClicked(mouseX, mouseY, mouseButton);
            }
            this.enabledFilter[index] = ((checkBox.getValue() == CheckBox.EnumCheckBoxValue.ENABLED) ? (index + 1) : 0);
            ++index;
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        if (this.isInSubSettings()) {
            for (final SettingsElement settingsElement : this.listedElementsStored) {
                settingsElement.mouseRelease(mouseX, mouseY, state);
            }
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (this.isInSubSettings()) {
            for (final SettingsElement settingsElement : this.listedElementsStored) {
                settingsElement.mouseClickMove(mouseX, mouseY, clickedMouseButton);
            }
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
        if (this.isInSubSettings()) {
            for (final SettingsElement settingsElement : this.listedElementsStored) {
                if (settingsElement instanceof DropDownElement) {
                    ((DropDownElement)settingsElement).onScrollDropDown();
                }
            }
        }
    }
    
    public void e() {
        super.e();
        if (this.fieldSearch != null) {
            this.fieldSearch.updateCursorCounter();
        }
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button == this.buttonDone && !this.displayRestartWarning) {
            if (isRestartRequired()) {
                this.displayRestartWarning = true;
            }
            else {
                bib.z().a(this.lastScreen);
            }
        }
        if (this.path.isEmpty()) {
            if (button == this.buttonBack && !this.displayRestartWarning) {
                this.openedAddonSettings = null;
            }
        }
        else {
            this.path.remove(this.path.size() - 1);
            this.b();
            this.scrollbar.setScrollY(this.preScrollPos);
            this.preScrollPos = 0.0;
        }
        if (button == this.buttonWarningExitGame && this.displayRestartWarning) {
            this.j.n();
        }
        if (button == this.buttonWarningRestartLater && this.displayRestartWarning) {
            bib.z().a(this.lastScreen);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.isInSubSettings()) {
            for (final SettingsElement settingsElement : this.listedElementsStored) {
                final boolean cancel = settingsElement instanceof KeyElement && ((KeyElement)settingsElement).getTextField().isFocused();
                settingsElement.keyTyped(typedChar, keyCode);
                if (cancel) {
                    return;
                }
            }
            super.a(typedChar, keyCode);
        }
        if (this.fieldSearch != null && !this.isInSubSettings()) {
            this.fieldSearch.textboxKeyTyped(typedChar, keyCode);
            this.sortAddonInfoList();
        }
        if (keyCode == 1) {
            if (!this.displayRestartWarning && isRestartRequired()) {
                this.displayRestartWarning = true;
            }
            else {
                bib.z().a(this.lastScreen);
            }
        }
    }
    
    public static boolean isRestartRequired() {
        for (final LabyModAddon labyModAddon : AddonLoader.getAddons()) {
            if (labyModAddon.about != null && labyModAddon.about.deleted) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isInSubSettings() {
        return this.openedAddonSettings != null;
    }
    
    public enum EnumSortingState
    {
        TRENDING(LanguageManager.translate("sortingtab_trending"), true), 
        TOP(LanguageManager.translate("sortingtab_top"), true), 
        LATEST(LanguageManager.translate("sortingtab_latest"), false), 
        VERIFIED(LanguageManager.translate("sortingtab_featured"), false), 
        INSTALLED(LanguageManager.translate("sortingtab_installed"), false);
        
        private String displayName;
        private boolean ranked;
        
        private EnumSortingState(final String displayName, final boolean ranked) {
            this.displayName = displayName;
            this.ranked = ranked;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public boolean isRanked() {
            return this.ranked;
        }
    }
}
