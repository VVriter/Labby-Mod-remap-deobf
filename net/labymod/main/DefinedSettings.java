//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import java.util.*;
import net.labymod.settings.*;
import net.labymod.api.permissions.*;
import net.labymod.api.protocol.liquid.*;
import net.labymod.utils.manager.*;
import net.labymod.mojang.afec.*;
import net.labymod.mojang.inventory.scale.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.*;
import net.labymod.gui.*;
import net.minecraftforge.fml.relauncher.*;
import java.lang.reflect.*;
import net.labymod.user.cosmetic.custom.handler.*;
import net.labymod.settings.elements.*;
import net.labymod.labyconnect.user.*;

public class DefinedSettings
{
    private static final boolean isMC18;
    private static ArrayList<SettingsCategory> mainSettingsCategories;
    private static SettingsCategory chatSetingsCategory;
    
    public static ArrayList<SettingsCategory> getCategories() {
        return DefinedSettings.mainSettingsCategories;
    }
    
    private static SettingsCategory getInformation() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_information");
        final BooleanElement elementPingOnTab = new BooleanElement("tabPing", new ControlElement.IconData());
        elementPingOnTab.getSubSettings().add(new BooleanElement("tabPing_colored", new ControlElement.IconData()));
        mainCategory.addSetting(elementPingOnTab);
        mainCategory.addSetting(new BooleanElement("notifyPermissionChanges", new ControlElement.IconData()));
        final BooleanElement elementFamiliarUsers = new BooleanElement("revealFamiliarUsers", new ControlElement.IconData(ModTextures.LOGO_LABYMOD_LOGO)).addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                if (accepted) {}
            }
        });
        elementFamiliarUsers.getSubSettings().add(new BooleanElement("revealFamiliarUsersPercentage", new ControlElement.IconData()));
        mainCategory.addSetting(elementFamiliarUsers);
        mainCategory.addSetting(new BooleanElement("outOfMemoryWarning", new ControlElement.IconData(Material.BARRIER)));
        return mainCategory;
    }
    
    private static SettingsCategory getAnimations() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_animations");
        mainCategory.addSetting(new BooleanElement("oldDamage", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("oldHearts", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("oldHitbox", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("oldTablist", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("oldSneaking", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("oldInventory", new ControlElement.IconData()));
        if (DefinedSettings.isMC18) {
            mainCategory.addSetting(new BooleanElement("oldSword", new ControlElement.IconData(Material.IRON_SWORD)));
            mainCategory.addSetting(new BooleanElement("oldFood", new ControlElement.IconData(Material.GOLDEN_APPLE)));
            mainCategory.addSetting(new BooleanElement("oldBow", new ControlElement.IconData(Material.BOW)));
            mainCategory.addSetting(new BooleanElement("oldFishing", new ControlElement.IconData(Material.FISHING_ROD)));
            mainCategory.addSetting(new BooleanElement("oldBlockhit", new ControlElement.IconData()));
            mainCategory.addSetting(new BooleanElement("oldItemSwitch", new ControlElement.IconData()));
            mainCategory.addSetting(new BooleanElement("oldItemHold", new ControlElement.IconData()));
        }
        else {
            mainCategory.addSetting(new BooleanElement("oldWalking", new ControlElement.IconData()));
        }
        mainCategory.bindPermissionToAll(Permissions.Permission.ANIMATIONS);
        mainCategory.bindCustomBooleanToAll("1.7", Source.ABOUT_MC_VERSION);
        return mainCategory;
    }
    
    private static SettingsCategory getBugfixes() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_bugfixes");
        final ListContainerElement elementImprovedLava = new ListContainerElement("improved_lava", new ControlElement.IconData(Material.LAVA_BUCKET));
        elementImprovedLava.getSubSettings().add(new BooleanElement("improvedLavaFixedGhostBlocks", new ControlElement.IconData(Material.BUCKET)).addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                FixedLiquidBucketProtocol.handleBucketAction(((boolean)accepted) ? FixedLiquidBucketProtocol.Action.ENABLE : FixedLiquidBucketProtocol.Action.DISABLE, 0, 0, 0);
            }
        }));
        elementImprovedLava.getSubSettings().add(new BooleanElement("improvedLavaNoLight", new ControlElement.IconData(Material.TORCH)).addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                LavaLightUpdater.update();
            }
        }));
        mainCategory.addSetting(elementImprovedLava);
        mainCategory.bindPermissionToAll(Permissions.Permission.IMPROVED_LAVA);
        mainCategory.addSetting(new BooleanElement("refillFix", new ControlElement.IconData(Material.MUSHROOM_SOUP)).bindPermission(Permissions.Permission.REFILL_FIX));
        if (DefinedSettings.isMC18) {
            mainCategory.addSetting(new BooleanElement("crosshairSync", new ControlElement.IconData()).bindPermission(Permissions.Permission.CROSSHAIR_SYNC));
            mainCategory.addSetting(new BooleanElement("oldBlockbuild", new ControlElement.IconData()).bindPermission(Permissions.Permission.BLOCKBUILD));
        }
        mainCategory.addSetting(new BooleanElement("particleFix", new ControlElement.IconData()));
        return mainCategory;
    }
    
    private static SettingsCategory getPerformance() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_performance");
        final BooleanElement chunkCachingElement = new BooleanElement("chunkCaching", new ControlElement.IconData());
        chunkCachingElement.getSubSettings().add(new NumberElement("chunkCachingSize", new ControlElement.IconData(Material.BOOK)).setRange(1, (int)(Runtime.getRuntime().maxMemory() / 1000000L)));
        chunkCachingElement.getSubSettings().add(new BooleanElement("chunkCachingStoreInFile", new ControlElement.IconData(Material.PAPER)));
        mainCategory.addSetting(chunkCachingElement);
        mainCategory.addSetting(new BooleanElement("fastWorldLoading", new ControlElement.IconData()));
        if (EntityCulling.OPEN_GL_33_SUPPORTED) {
            final BooleanElement afec = new BooleanElement("afecEnabled", new ControlElement.IconData());
            afec.setDescriptionText(afec.getDescriptionText() + EntityCulling.CREDIT_LINE);
            afec.getSubSettings().add(new HeaderElement("Entities"));
            afec.getSubSettings().add(new SliderElement("afecEntityInterval", new ControlElement.IconData()).setRange(20, 300));
            afec.getSubSettings().add(new BooleanElement("afecHideEntityNames", new ControlElement.IconData()));
            afec.getSubSettings().add(new HeaderElement("Players"));
            afec.getSubSettings().add(new BooleanElement("afecPlayers", new ControlElement.IconData()));
            afec.getSubSettings().add(new SliderElement("afecPlayerInterval", new ControlElement.IconData()).setRange(20, 300));
            afec.getSubSettings().add(new BooleanElement("afecHidePlayerNames", new ControlElement.IconData()));
            afec.getSubSettings().add(new HeaderElement("Both"));
            afec.getSubSettings().add(new BooleanElement("afecDistanceDetection", new ControlElement.IconData()));
            afec.getSubSettings().add(new HeaderElement(EntityCulling.CREDIT_LINE.replace("\n", " "), 0.5));
            mainCategory.addSetting(afec);
        }
        mainCategory.addSetting(new BooleanElement("entityCulling", new ControlElement.IconData()));
        final BooleanElement marker = new BooleanElement("marker", new ControlElement.IconData());
        marker.getSubSettings().add(new NumberElement("markerDuration", new ControlElement.IconData(Material.WATCH)).setRange(2, 10).setSteps(1));
        mainCategory.addSetting(marker);
        return mainCategory;
    }
    
    private static SettingsCategory getMinecraftChat() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_minecraft_chat");
        mainCategory.addSetting(new BooleanElement("autoText", new ControlElement.IconData()));
        final ListContainerElement nameHistoryElement = new ListContainerElement("name_history", new ControlElement.IconData(Material.BOOK));
        nameHistoryElement.getSubSettings().add(new BooleanElement("hoverNameHistory", new ControlElement.IconData(Material.BOOK_AND_QUILL)));
        nameHistoryElement.getSubSettings().add(new BooleanElement("nameHistory", new ControlElement.IconData(Material.BOOK)));
        mainCategory.addSetting(nameHistoryElement);
        mainCategory.addSetting(new BooleanElement("chatSymbols", new ControlElement.IconData()).bindPermission(Permissions.Permission.CHAT));
        mainCategory.addSetting(new BooleanElement("chatFilter", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("chatShortcuts", new ControlElement.IconData()).bindPermission(Permissions.Permission.CHAT));
        final BooleanElement playerMenu = new BooleanElement("playerMenu", new ControlElement.IconData());
        playerMenu.getSubSettings().add(new BooleanElement("playerMenuEditor", new ControlElement.IconData()));
        playerMenu.getSubSettings().add(new BooleanElement("playerMenuAnimation", new ControlElement.IconData()));
        mainCategory.addSetting(playerMenu);
        mainCategory.addSetting(new BooleanElement("fastChat", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("scalableChat", new ControlElement.IconData()));
        final ListContainerElement advancedElement = new ListContainerElement("advanced_chat_settings", new ControlElement.IconData());
        advancedElement.getSubSettings().add(new SliderElement("chatScrollSpeed", new ControlElement.IconData(Material.DIODE)).setRange(1, 10));
        advancedElement.getSubSettings().add(new SliderElement("chatLineLimit", new ControlElement.IconData(Material.DIODE)).setRange(100, 1000).setSteps(100));
        advancedElement.getSubSettings().add(new BooleanElement("chatAnimation", new ControlElement.IconData()));
        if (!DefinedSettings.isMC18) {
            advancedElement.getSubSettings().add(new BooleanElement("clearChatOnJoin", new ControlElement.IconData(Material.BARRIER)));
        }
        mainCategory.addSetting(advancedElement);
        final ListContainerElement secondChatElement = new ListContainerElement("second_chat", new ControlElement.IconData());
        secondChatElement.getSubSettings().add(new BooleanElement("chatPositionRight", new ControlElement.IconData()));
        secondChatElement.getSubSettings().add(new SliderElement("secondChatWidth", new ControlElement.IconData(Material.DIODE)).setRange(40, 320));
        secondChatElement.getSubSettings().add(new SliderElement("secondChatHeight", new ControlElement.IconData(Material.DIODE)).setRange(20, 180));
        mainCategory.addSetting(secondChatElement);
        mainCategory.addSetting(new BooleanElement("showModuleEditorShortcut", new ControlElement.IconData()));
        return mainCategory;
    }
    
    private static SettingsCategory getPvP() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_pvp");
        mainCategory.addSetting(new BooleanElement("speedFov", new ControlElement.IconData(Material.EYE_OF_ENDER)));
        if (DefinedSettings.isMC18) {
            mainCategory.addSetting(new BooleanElement("swapBow", new ControlElement.IconData()));
        }
        return mainCategory;
    }
    
    private static SettingsCategory getMenuGUI() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_menu_gui");
        mainCategory.addSetting(new BooleanElement("guiBackground", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("directConnectInfo", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("confirmDisconnect", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("quickPlay", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("publicServerList", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("multiplayerIngame", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("borderlessWindow", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("betterSkinCustomization", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("betterShaderSelection", new ControlElement.IconData()));
        final BooleanElement serverlistLiveView = new BooleanElement("serverlistLiveView", new ControlElement.IconData());
        serverlistLiveView.getSubSettings().add(new NumberElement("serverlistLiveViewInterval", new ControlElement.IconData(Material.WATCH)).setRange(3, 60));
        mainCategory.addSetting(serverlistLiveView);
        final DropDownMenu<EnumGuiScale> dropDownMenu = (DropDownMenu<EnumGuiScale>)new DropDownMenu((String)null, 0, 0, 0, 0).fill((Object[])EnumGuiScale.values());
        final DropDownElement<EnumGuiScale> dropDownElement = new DropDownElement<EnumGuiScale>("customInventoryScale", dropDownMenu, new ControlElement.IconData(), new DropDownElement.DrowpDownLoadValue<EnumGuiScale>() {
            @Override
            public EnumGuiScale load(final String value) {
                return EnumGuiScale.valueOf(value);
            }
        });
        dropDownElement.setChangeListener(new Consumer<EnumGuiScale>() {
            @Override
            public void accept(final EnumGuiScale accepted) {
                try {
                    ModSettings.class.getDeclaredField(dropDownElement.getConfigEntryName()).set(LabyMod.getSettings(), accepted);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dropDownMenu.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                LabyMod.getInstance().getDrawUtils().drawString(((EnumGuiScale)object).getDisplayName(), x, y);
            }
        });
        mainCategory.addSetting(dropDownElement);
        final Consumer<Boolean> reinitCallback = new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bib.z().a((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                if (bib.z().m != null) {
                                    Tabs.initGui(bib.z().m);
                                }
                            }
                        });
                    }
                }).start();
            }
        };
        mainCategory.addSetting(new BooleanElement("tabIcons", new ControlElement.IconData()).addCallback(reinitCallback));
        mainCategory.addSetting(new BooleanElement("labymodSettingsInTabs", new ControlElement.IconData()).addCallback(reinitCallback));
        mainCategory.addSetting(new BooleanElement("serverBanner", new ControlElement.IconData()));
        return mainCategory;
    }
    
    private static SettingsCategory getAdditional() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_additional");
        mainCategory.addSetting(new BooleanElement("showMyName", new ControlElement.IconData()));
        if (!DefinedSettings.isMC18) {
            mainCategory.addSetting(new BooleanElement("showBossBar", new ControlElement.IconData()));
        }
        mainCategory.addSetting(new BooleanElement("showSaturation", new ControlElement.IconData()).bindPermission(Permissions.Permission.SATURATION_BAR));
        mainCategory.addSetting(new BooleanElement("signSearch", new ControlElement.IconData(Material.SIGN)));
        if (DefinedSettings.isMC18) {
            mainCategory.addSetting(new BooleanElement("leftHand", new ControlElement.IconData()));
        }
        final BooleanElement discordRichPresence = new BooleanElement("discordRichPresence", new ControlElement.IconData()).addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                if (accepted) {
                    LabyMod.getInstance().getDiscordApp().initialize();
                }
                else {
                    LabyMod.getInstance().getDiscordApp().shutdown();
                }
            }
        });
        discordRichPresence.getSubSettings().add(new BooleanElement("discordAllowJoining", new ControlElement.IconData()));
        discordRichPresence.getSubSettings().add(new BooleanElement("discordAllowSpectating", new ControlElement.IconData()));
        discordRichPresence.getSubSettings().add(new BooleanElement("discordShowIpAddress", new ControlElement.IconData()));
        discordRichPresence.getSubSettings().add(new BooleanElement("discordShowAchievements", new ControlElement.IconData()));
        if (!OSUtil.isUnix()) {
            mainCategory.addSetting(discordRichPresence);
        }
        final BooleanElement tags = new BooleanElement("tags", new ControlElement.IconData());
        tags.setAdvancedButtonCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                bib.z().a((blk)new GuiTags(bib.z().m));
            }
        });
        mainCategory.addSetting(tags);
        return mainCategory;
    }
    
    private static SettingsCategory getKeys() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_keys");
        mainCategory.addSetting(new KeyElement("keyModuleEditor", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyAddons", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyEmote", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyStickerMenu", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyPlayerMenu", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyToggleHitbox", new ControlElement.IconData()));
        mainCategory.addSetting(new KeyElement("keyMarker", new ControlElement.IconData()));
        return mainCategory;
    }
    
    private static SettingsCategory getCosmetics() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_cosmetics");
        mainCategory.addSetting(new BooleanElement("emotes", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("stickers", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("cosmetics", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("cosmeticsHideInDistance", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("cosmeticsCustomTextures", new ControlElement.IconData()));
        try {
            final Field fieldOfShowCapes = ReflectionHelper.findField(bid.class, "ofShowCapes");
            mainCategory.addSetting(new BooleanElement("Optifine Capes", new ControlElement.IconData("labymod/textures/settings/settings/optifine_capes.png"), new Consumer<Boolean>() {
                @Override
                public void accept(final Boolean accepted) {
                    try {
                        fieldOfShowCapes.set(bib.z().t, accepted);
                        bib.z().t.b();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, fieldOfShowCapes.getBoolean(bib.z().t)));
        }
        catch (Exception ex) {}
        final DropDownMenu<CloakImageHandler.EnumCapePriority> dropDownMenu = (DropDownMenu<CloakImageHandler.EnumCapePriority>)new DropDownMenu((String)null, 0, 0, 0, 0).fill((Object[])CloakImageHandler.EnumCapePriority.values());
        final DropDownElement<CloakImageHandler.EnumCapePriority> dropDownElement = (DropDownElement<CloakImageHandler.EnumCapePriority>)new DropDownElement("capePriority", dropDownMenu, new ControlElement.IconData(), new DropDownElement.DrowpDownLoadValue<CloakImageHandler.EnumCapePriority>() {
            @Override
            public CloakImageHandler.EnumCapePriority load(final String value) {
                return CloakImageHandler.EnumCapePriority.valueOf(value);
            }
        }).setCallback((Consumer)new Consumer<CloakImageHandler.EnumCapePriority>() {
            @Override
            public void accept(final CloakImageHandler.EnumCapePriority accepted) {
                LabyMod.getInstance().getUserManager().setCapePriority(accepted);
            }
        });
        mainCategory.addSetting(dropDownElement);
        mainCategory.addSetting(new BooleanElement("capeOriginalParticles", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("loadTexturesAsync", new ControlElement.IconData(Material.PAINTING)));
        final BooleanElement element = new BooleanElement("extrudedTextures", new ControlElement.IconData(Material.COOKIE));
        element.addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                LabyMod.getInstance().getUserManager().refresh();
            }
        });
        mainCategory.addSetting(element);
        return mainCategory;
    }
    
    private static SettingsCategory getLabyModChat() {
        final SettingsCategory mainCategory = new SettingsCategory("settings_category_labymod_chat");
        mainCategory.addSetting(new StringElement("motd", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("ignoreRequests", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("showConnectedIp", new ControlElement.IconData()).addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                LabyMod.getInstance().getLabyConnect().getClientProfile().sendSettingsToServer();
            }
        }));
        final DropDownMenu<EnumAlertDisplayType> dropDownMenu = (DropDownMenu<EnumAlertDisplayType>)new DropDownMenu((String)null, 0, 0, 0, 0).fill((Object[])EnumAlertDisplayType.values());
        final DropDownElement<EnumAlertDisplayType> dropDownElement = (DropDownElement<EnumAlertDisplayType>)new DropDownElement("alertDisplayType", dropDownMenu, null, new DropDownElement.DrowpDownLoadValue<EnumAlertDisplayType>() {
            @Override
            public EnumAlertDisplayType load(final String value) {
                return EnumAlertDisplayType.valueOf(value);
            }
        }).setCallback((Consumer)new Consumer<EnumAlertDisplayType>() {
            @Override
            public void accept(final EnumAlertDisplayType accepted) {
                LabyMod.getInstance().getLabyConnect().updateAlertDisplayType();
            }
        });
        mainCategory.addSetting(dropDownElement);
        mainCategory.addSetting(new BooleanElement("alertsPlayingOn", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("alertsOnlineStatus", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("alertPlaySounds", new ControlElement.IconData()));
        mainCategory.addSetting(new BooleanElement("unreadMessageIcon", new ControlElement.IconData(Material.WORKBENCH)));
        mainCategory.addSetting(new BooleanElement("sendAnonymousStatistics", new ControlElement.IconData()));
        return mainCategory;
    }
    
    public static SettingsCategory getChatSetingsCategory() {
        return DefinedSettings.chatSetingsCategory;
    }
    
    static {
        isMC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        DefinedSettings.mainSettingsCategories = new ArrayList<SettingsCategory>();
        DefinedSettings.chatSetingsCategory = getLabyModChat();
        DefinedSettings.mainSettingsCategories.add(getInformation());
        DefinedSettings.mainSettingsCategories.add(getAnimations());
        DefinedSettings.mainSettingsCategories.add(getBugfixes());
        DefinedSettings.mainSettingsCategories.add(getPerformance());
        DefinedSettings.mainSettingsCategories.add(getMinecraftChat());
        DefinedSettings.mainSettingsCategories.add(getPvP());
        DefinedSettings.mainSettingsCategories.add(getMenuGUI());
        DefinedSettings.mainSettingsCategories.add(getAdditional());
        DefinedSettings.mainSettingsCategories.add(getKeys());
        DefinedSettings.mainSettingsCategories.add(getCosmetics());
    }
}
