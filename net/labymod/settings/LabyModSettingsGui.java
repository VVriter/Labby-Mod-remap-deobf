//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.core.*;
import net.labymod.main.lang.*;
import org.lwjgl.input.*;
import net.labymod.gui.elements.*;
import java.util.*;
import net.labymod.main.*;
import net.labymod.utils.manager.*;
import java.io.*;
import net.labymod.utils.*;
import net.labymod.main.update.*;
import net.labymod.settings.elements.*;

public class LabyModSettingsGui extends blk
{
    private Scrollbar scrollbar;
    private ModTextField searchField;
    private blk lastScreen;
    private SettingsElement mouseOverElement;
    private List<SettingsElement> listedElementsStored;
    private List<SettingsElement> tempElementsStored;
    private ArrayList<SettingsElement> path;
    private bja buttonGoBack;
    private double preScrollPos;
    private boolean hoverUpdateButton;
    private boolean closed;
    private boolean skipDrawDescription;
    private boolean hoverIconButtonAddons;
    private boolean hoverIconButtonGUI;
    
    public LabyModSettingsGui() {
        this(null);
    }
    
    public LabyModSettingsGui(final blk lastScreen) {
        this.scrollbar = new Scrollbar(1);
        this.listedElementsStored = new ArrayList<SettingsElement>();
        this.tempElementsStored = new ArrayList<SettingsElement>();
        this.path = new ArrayList<SettingsElement>();
        this.preScrollPos = 0.0;
        this.closed = false;
        this.skipDrawDescription = false;
        this.lastScreen = lastScreen;
    }
    
    public void b() {
        super.b();
        this.doQuery(null);
        this.n.clear();
        (this.searchField = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 150, 35, 300, 16)).setPlaceHolder(LanguageManager.translate("search_textbox_placeholder"));
        this.searchField.setBlackBox(false);
        this.scrollbar.setPosition(this.l / 2 + 152, 60, this.l / 2 + 152 + 4, this.m - 30);
        this.scrollbar.setSpeed(20);
        this.scrollbar.update(this.listedElementsStored.size());
        this.scrollbar.init();
        if (this.lastScreen != null) {
            this.n.add(new bja(5, this.l / 2 - 100, this.m - 22, LanguageManager.translate("button_done")));
        }
        this.n.add(this.buttonGoBack = new bja(6, this.l / 2 - 152, 32, 20, 20, "<"));
        this.buttonGoBack.m = !this.path.isEmpty();
        Keyboard.enableRepeatEvents(true);
        Tabs.initGui((blk)this);
    }
    
    public void m() {
        super.m();
        Keyboard.enableRepeatEvents(false);
        LabyMod.getMainConfig().save();
    }
    
    private void doQuery(final String query) {
        this.tempElementsStored.clear();
        if (this.path.isEmpty()) {
            for (final SettingsCategory settingsCategory : DefinedSettings.getCategories()) {
                this.queryCategory(settingsCategory, query);
            }
        }
        else {
            final SettingsElement currentOpenElement = this.path.get(this.path.size() - 1);
            this.tempElementsStored.addAll(currentOpenElement.getSubSettings().getElements());
        }
        this.listedElementsStored = this.tempElementsStored;
    }
    
    private void queryCategory(final SettingsCategory settingsCategory, final String query) {
        final List<SettingsElement> elementsToAdd = new ArrayList<SettingsElement>();
        for (final SettingsElement element : settingsCategory.getSettings().getElements()) {
            if (query == null || settingsCategory.getTitle().toLowerCase().contains(query) || this.isSettingElement(query, element)) {
                elementsToAdd.add(element);
            }
            if (query != null && !query.isEmpty() && element.getSubSettings() != null && !element.getSubSettings().getElements().isEmpty()) {
                for (final SettingsElement subElement : element.getSubSettings().getElements()) {
                    if (this.isSettingElement(query, subElement)) {
                        elementsToAdd.add(subElement);
                    }
                }
            }
        }
        if (!elementsToAdd.isEmpty()) {
            this.tempElementsStored.add((SettingsElement)new HeaderElement(settingsCategory.getTitle()));
        }
        for (final SettingsElement element : elementsToAdd) {
            this.tempElementsStored.add(element);
        }
        for (final SettingsCategory subCategory : settingsCategory.getSubList()) {
            this.queryCategory(subCategory, query);
        }
    }
    
    private boolean isSettingElement(final String query, final SettingsElement settingsElement) {
        return settingsElement.getDisplayName().toLowerCase().contains(query) || (settingsElement.getDescriptionText() != null && settingsElement.getDescriptionText().toLowerCase().contains(query));
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.drawSettingsList(mouseX, mouseY, this.m - 30);
        draw.drawOverlayBackground(0, 55);
        draw.drawGradientShadowTop(55.0, 0.0, this.l);
        final int bottomBarheight = (this.lastScreen == null && !LabyMod.getInstance().getUpdater().isUpdateAvailable()) ? 10 : 25;
        draw.drawOverlayBackground(this.m - bottomBarheight, this.m);
        draw.drawGradientShadowBottom(this.m - bottomBarheight, 0.0, this.l);
        this.scrollbar.draw(mouseX, mouseY);
        if (!this.path.isEmpty()) {
            final SettingsElement currentOpenElement = this.path.get(this.path.size() - 1);
            draw.drawString(currentOpenElement.getDisplayName(), this.l / 2 - 125, 39.0);
            if (currentOpenElement instanceof ControlElement) {
                final ControlElement control = (ControlElement)currentOpenElement;
                final ControlElement.IconData iconData = control.getIconData();
                if (iconData.hasTextureIcon()) {
                    bib.z().N().a(iconData.getTextureIcon());
                    LabyMod.getInstance().getDrawUtils().drawTexture(this.l / 2 + 130, 35.0, 256.0, 256.0, 16.0, 16.0);
                }
                else if (iconData.hasMaterialIcon()) {
                    LabyMod.getInstance().getDrawUtils().drawItem(iconData.getMaterialIcon().createItemStack(), this.l / 2 + 130, 35.0, null);
                }
            }
        }
        else {
            this.searchField.drawTextBox();
        }
        if (!this.skipDrawDescription) {
            this.drawDescriptions(mouseX, mouseY, 55, this.m - 30);
        }
        this.drawUpdateNotification(mouseX, mouseY);
        if (!LabyMod.getSettings().labymodSettingsInTabs) {
            this.hoverIconButtonGUI = this.drawIconButton("GUI", ModTextures.CHAT_TAB_GUI_EDITOR, this.l / 2 + 170, 42, mouseX, mouseY);
            this.hoverIconButtonAddons = this.drawIconButton("Addons", ModTextures.TAB_ADDONS, this.l / 2 + 170 + 20, 42, mouseX, mouseY);
        }
        super.a(mouseX, mouseY, partialTicks);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    private boolean drawIconButton(final String title, final nf resourceLocation, final int x, final int y, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        double size = 15.0;
        final boolean mouseOver = mouseX > x - size / 2.0 && mouseX < x + size / 2.0 && mouseY > y - size / 2.0 && mouseY < y + size / 2.0;
        if (mouseOver) {
            size += 2.0;
        }
        bib.z().N().a(resourceLocation);
        draw.drawTexture(x - size / 2.0, y - size / 2.0, 255.0, 255.0, size, size);
        if (mouseOver) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, title);
        }
        return mouseOver;
    }
    
    private void drawUpdateNotification(final int mouseX, final int mouseY) {
        if (this.l < 350) {
            return;
        }
        final int offsetY = (this.lastScreen == null && !LabyMod.getInstance().getUpdater().isUpdateAvailable()) ? -8 : 0;
        bib.z().N().a(ModTextures.BUTTON_UPDATE);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        this.hoverUpdateButton = (mouseX > 5 && mouseX < 20 && mouseY > this.m - 20 + offsetY && mouseY < this.m - 20 + 15 + offsetY);
        final int add = this.hoverUpdateButton ? 1 : 0;
        draw.drawTexture(5 - add, this.m - 20 - add + offsetY, 255.0, 255.0, 15 + add * 2, 15 + add * 2);
        final boolean updateAvailable = LabyMod.getInstance().getUpdater().isUpdateAvailable();
        if (updateAvailable) {
            final boolean coreUpdate = LabyMod.getInstance().getUpdater().getCoreUpdate().isUpdateAvailable();
            final String infoText = coreUpdate ? LanguageManager.translate("update_available") : LanguageManager.translate("addon_update_available", new Object[] { LabyMod.getInstance().getUpdater().getAddonUpdateCount() });
            draw.drawString(infoText, 25.0, this.m - 20 + 4);
            if (this.hoverUpdateButton) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, LanguageManager.translate("reinstall_version", new Object[] { LabyMod.getInstance().getUpdater().getCoreUpdate().getLatestVersion() }));
            }
        }
        else if (this.hoverUpdateButton) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, LanguageManager.translate("reinstall"));
        }
    }
    
    private void drawSettingsList(final int mouseX, final int mouseY, final int maxY) {
        this.mouseOverElement = null;
        this.skipDrawDescription = false;
        double totalEntryHeight = 0.0;
        for (int zLevel = 0; zLevel < 2; ++zLevel) {
            double posY = 60.0 + this.scrollbar.getScrollY();
            final int midX = this.l / 2;
            final int elementLength = 150;
            totalEntryHeight = 0.0;
            for (final SettingsElement element : this.listedElementsStored) {
                if (((!(element instanceof DropDownElement) || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 0) || ((element instanceof DropDownElement || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 1)) {
                    if (element instanceof DropDownElement) {
                        ((DropDownElement)element).getDropDownMenu().setMaxY(maxY);
                        if (((DropDownElement)element).getDropDownMenu().isOpen()) {
                            this.skipDrawDescription = true;
                        }
                    }
                    element.draw(midX - elementLength, (int)posY, midX + elementLength, (int)posY + element.getEntryHeight(), mouseX, mouseY);
                    if (element.isMouseOver()) {
                        this.mouseOverElement = element;
                    }
                }
                posY += element.getEntryHeight() + 1;
                totalEntryHeight += element.getEntryHeight() + 1;
            }
        }
        this.scrollbar.setEntryHeight(totalEntryHeight / this.listedElementsStored.size());
        this.scrollbar.update(this.listedElementsStored.size());
    }
    
    private void drawDescriptions(final int mouseX, final int mouseY, final int minY, final int maxY) {
        for (final SettingsElement element : this.listedElementsStored) {
            if (element.isMouseOver() && mouseY > minY && mouseY < maxY) {
                element.drawDescription(mouseX, mouseY, this.l);
            }
        }
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 5) {
            this.closed = true;
            bib.z().a(this.lastScreen);
        }
        if (button.k == 6 && !this.path.isEmpty()) {
            this.path.remove(this.path.size() - 1);
            this.b();
            this.scrollbar.setScrollY(this.preScrollPos);
            this.preScrollPos = 0.0;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
        if (this.closed) {
            return;
        }
        if (this.hoverUpdateButton) {
            try {
                final Updater updater = LabyMod.getInstance().getUpdater();
                updater.setForceUpdate(true);
                updater.ensureUpdaterAvailable((Consumer)new Consumer<Boolean>() {
                    @Override
                    public void accept(final Boolean accepted) {
                        bib.z().n();
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        for (final SettingsElement element : this.listedElementsStored) {
            if (element instanceof DropDownElement && ((DropDownElement)element).onClickDropDown(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
        for (final SettingsElement element : this.listedElementsStored) {
            element.unfocus(mouseX, mouseY, mouseButton);
            if (element.isMouseOver()) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (this.mouseOverElement != null && this.mouseOverElement instanceof ControlElement) {
            final ControlElement element2 = (ControlElement)this.mouseOverElement;
            if (element2.hasSubList() && element2.getButtonAdvanced().a() && element2.getButtonAdvanced().l) {
                element2.getButtonAdvanced().a(this.j.U());
                if (element2.getAdvancedButtonCallback() == null) {
                    this.path.add((SettingsElement)element2);
                    this.preScrollPos = this.scrollbar.getScrollY();
                    this.scrollbar.setScrollY(0.0);
                    this.b();
                }
                else {
                    element2.getAdvancedButtonCallback().accept(true);
                }
            }
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hoverIconButtonGUI) {
            bib.z().a((blk)new LabyModModuleEditorGui((blk)this));
        }
        if (this.hoverIconButtonAddons) {
            bib.z().a((blk)new LabyModAddonsGui((blk)this));
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        for (final SettingsElement element : this.listedElementsStored) {
            element.mouseRelease(mouseX, mouseY, state);
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        for (final SettingsElement element : this.listedElementsStored) {
            element.mouseClickMove(mouseX, mouseY, clickedMouseButton);
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        for (final SettingsElement element : this.listedElementsStored) {
            final boolean cancel = element instanceof KeyElement && ((KeyElement)element).getTextField().isFocused();
            element.keyTyped(typedChar, keyCode);
            if (cancel) {
                return;
            }
        }
        if (keyCode == 1) {
            this.j.a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
        if (this.path.isEmpty() && this.searchField.textboxKeyTyped(typedChar, keyCode)) {
            this.doQuery(this.searchField.getText().toLowerCase());
        }
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    public void e() {
        super.e();
        this.searchField.updateCursorCounter();
    }
}
