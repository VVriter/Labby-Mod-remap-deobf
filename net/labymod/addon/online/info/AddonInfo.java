//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon.online.info;

import net.labymod.utils.*;
import java.io.*;
import net.labymod.addon.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.api.*;
import net.labymod.addon.online.*;
import net.labymod.support.util.*;

public class AddonInfo
{
    protected UUID uuid;
    protected String name;
    protected int version;
    protected String author;
    protected String description;
    protected int category;
    protected AddonElement addonElement;
    protected String offlineIcon;
    
    public AddonInfo(final UUID uuid, final String name, final int version, final String author, final String description, final int category) {
        this.offlineIcon = null;
        this.uuid = uuid;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description.replaceAll("\r", "");
        this.category = category;
        this.addonElement = new AddonElement(this, new Consumer<AddonActionState>() {
            @Override
            public void accept(final AddonActionState state) {
                AddonInfo.this.addonElement.setLastActionState(state);
                if (state == AddonActionState.INSTALL && AddonInfo.this instanceof OnlineAddonInfo) {
                    final OnlineAddonInfo onlineAddonInfo = (OnlineAddonInfo)AddonInfo.this;
                    new AddonDownloader(onlineAddonInfo, (CallbackAddonDownloadProcess)new CallbackAddonDownloadProcess() {
                        public void success(final File file) {
                            if (onlineAddonInfo.isIncludeInJar()) {
                                LabyModOFAddon.downloadOFHandler(LabyModOFAddon.INSTALL = true);
                                return;
                            }
                            if (!onlineAddonInfo.restart) {
                                AddonInfo.this.addonElement.setLastActionState(AddonActionState.UNINSTALL_REVOKE);
                                AddonLoader.resolveJarFile(file, this.getClass().getClassLoader());
                                final LabyModAddon loadedAddon = AddonLoader.enableAddon(AddonInfo.this.uuid, LabyMod.getInstance().getLabyModAPI());
                                if (loadedAddon == null) {
                                    AddonInfo.this.addonElement.setLastActionState(AddonActionState.ERROR);
                                }
                                else {
                                    AddonInfo.this.addonElement.setInstalledAddon(loadedAddon);
                                }
                                if (loadedAddon != null) {
                                    AddonInfo.this.addonElement.getSubSettings().clear();
                                    AddonInfo.this.addonElement.getSubSettings().addAll(loadedAddon.getSubSettings());
                                }
                            }
                            else {
                                AddonLoader.getFiles().put(AddonInfo.this.uuid, file);
                            }
                        }
                        
                        public void progress(final double percent) {
                            AddonInfo.this.addonElement.setInstallProgress(percent);
                            AddonInfo.this.addonElement.setLastActionState(AddonActionState.INSTALL);
                        }
                        
                        public void failed(final String message) {
                        }
                    }).downloadAsync();
                }
                if (state == AddonActionState.UNINSTALL && AddonInfo.this.addonElement.isAddonInstalled()) {
                    AddonInfo.this.addonElement.getInstalledAddon().about.deleted = true;
                    if (AddonInfo.this instanceof OnlineAddonInfo && ((OnlineAddonInfo)AddonInfo.this).isIncludeInJar()) {
                        LabyModOFAddon.downloadOFHandler(true);
                    }
                }
                if (state == AddonActionState.UNINSTALL_REVOKE && AddonInfo.this.addonElement.isAddonInstalled()) {
                    AddonInfo.this.addonElement.getInstalledAddon().about.deleted = false;
                }
                if (state == AddonActionState.INSTALL_REVOKE) {
                    if (AddonInfo.this instanceof OnlineAddonInfo && ((OnlineAddonInfo)AddonInfo.this).isIncludeInJar()) {
                        LabyModOFAddon.INSTALL = false;
                    }
                    else {
                        final File file = AddonLoader.getFiles().get(AddonInfo.this.uuid);
                        if (file != null && file.exists() && file.delete()) {
                            Debug.log(Debug.EnumDebugMode.ADDON, "Successfully deleted addon " + AddonInfo.this.name);
                        }
                        else {
                            Debug.log(Debug.EnumDebugMode.ADDON, "Error while deleting addon " + AddonInfo.this.name);
                        }
                    }
                }
            }
        });
    }
    
    public void init() {
        if (this.addonElement.getInstalledAddon() != null) {
            return;
        }
        final LabyModAddon installedAddon = AddonLoader.getInstalledAddonByInfo(this);
        this.addonElement.setInstalledAddon(installedAddon);
        this.addonElement.setLastActionState((installedAddon == null) ? AddonActionState.INSTALL_REVOKE : AddonActionState.UNINSTALL_REVOKE);
        if (installedAddon != null) {
            this.addonElement.getSubSettings().addAll(installedAddon.getSubSettings());
        }
    }
    
    public String getDownloadURL() {
        return String.format("https://dl.labymod.net/latest/?file=%s&a=1", this.uuid);
    }
    
    public String getImageURL() {
        return (this instanceof OnlineAddonInfo) ? String.format("https://dl.labymod.net/latest/addons/%s/icon.png", this.uuid) : this.offlineIcon;
    }
    
    public File getFileDestination() {
        return new File(AddonLoader.getAddonsDirectory(), this.name + ".jar");
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getCategory() {
        return this.category;
    }
    
    public AddonElement getAddonElement() {
        return this.addonElement;
    }
    
    public String getOfflineIcon() {
        return this.offlineIcon;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setOfflineIcon(final String offlineIcon) {
        this.offlineIcon = offlineIcon;
    }
    
    public enum AddonActionState
    {
        INSTALL, 
        INSTALL_REVOKE, 
        UNINSTALL, 
        UNINSTALL_REVOKE, 
        ERROR;
    }
}
