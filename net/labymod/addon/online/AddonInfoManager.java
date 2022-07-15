//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon.online;

import net.labymod.addon.online.info.*;
import net.labymod.gui.elements.*;
import net.labymod.utils.request.*;
import net.labymod.addon.*;
import net.labymod.support.util.*;
import net.labymod.main.*;
import net.labymod.api.*;
import java.io.*;
import java.nio.file.*;
import com.google.gson.*;
import java.util.*;

public class AddonInfoManager
{
    private static AddonInfoManager instance;
    private Map<UUID, AddonInfo> addonInfoMap;
    private List<AddonInfo> addonInfoList;
    private List<OnlineAddonInfo> allMinecraftVersionsAddonInfoList;
    private List<String> categories;
    private CheckBox[] categorieCheckboxList;
    private boolean loaded;
    private boolean calledInit;
    
    public AddonInfoManager() {
        this.addonInfoMap = new HashMap<UUID, AddonInfo>();
        this.addonInfoList = new ArrayList<AddonInfo>();
        this.allMinecraftVersionsAddonInfoList = new ArrayList<OnlineAddonInfo>();
        this.categories = new ArrayList<String>(Arrays.asList("All"));
        this.categorieCheckboxList = new CheckBox[this.categories.size()];
        this.loaded = false;
        this.calledInit = false;
    }
    
    public static AddonInfoManager getInstance() {
        if (AddonInfoManager.instance == null) {
            AddonInfoManager.instance = new AddonInfoManager();
        }
        return AddonInfoManager.instance;
    }
    
    public void init() {
        if (this.calledInit) {
            return;
        }
        this.calledInit = true;
        final String majorVersion = Source.getMajorVersion();
        try {
            final JsonObject jsonObject = (JsonObject)DownloadServerRequest.getJsonObject("https://dl.labymod.net/addons.json");
            final JsonArray array = jsonObject.get("categories").getAsJsonArray();
            this.categorieCheckboxList = new CheckBox[array.size()];
            int i = 0;
            for (final JsonElement element : array) {
                final String displayName = element.getAsString();
                this.categories.add(displayName);
                this.categorieCheckboxList[i] = new CheckBox(displayName, CheckBox.EnumCheckBoxValue.ENABLED, null, 0, 0, 15, 15);
                ++i;
            }
            final Set<Map.Entry<String, JsonElement>> addonsSet = (Set<Map.Entry<String, JsonElement>>)jsonObject.get("addons").getAsJsonObject().entrySet();
            for (final Map.Entry<String, JsonElement> entry : addonsSet) {
                final JsonArray jsonArray = entry.getValue().getAsJsonArray();
                final Iterator<JsonElement> addonIterator = (Iterator<JsonElement>)jsonArray.iterator();
                while (addonIterator.hasNext()) {
                    final JsonObject object = addonIterator.next().getAsJsonObject();
                    final JsonElement addonUUID = object.get("uuid");
                    final JsonElement addonName = object.get("name");
                    final JsonElement addonVersion = object.get("version");
                    final JsonElement addonHash = object.get("hash");
                    final JsonElement addonAuthor = object.get("author");
                    final JsonElement addonDescription = object.get("description");
                    final JsonElement addonCategory = object.get("category");
                    final JsonElement addonEnabled = object.get("enabled");
                    final JsonElement addonIncludeInJar = object.get("includeInJar");
                    final JsonElement addonRestart = object.get("restart");
                    final JsonElement addonVerified = object.get("verified");
                    final JsonElement addonMcVersion = object.get("mcversion");
                    if (addonUUID != null && addonName != null && addonVersion != null && addonHash != null && addonAuthor != null && addonDescription != null && addonCategory != null && addonEnabled != null && addonIncludeInJar != null && addonRestart != null && addonVerified != null && addonMcVersion != null) {
                        int[] sorting = new int[this.categories.size()];
                        if (object.has("sorting")) {
                            final JsonArray addonSorting = object.get("sorting").getAsJsonArray();
                            sorting = new int[addonSorting.size()];
                            int index = 0;
                            for (final JsonElement element2 : addonSorting) {
                                sorting[index] = element2.getAsInt();
                                ++index;
                            }
                        }
                        final OnlineAddonInfo onlineAddonInfo = new OnlineAddonInfo(UUID.fromString(addonUUID.getAsString()), addonName.getAsString(), addonVersion.getAsInt(), addonHash.getAsString(), addonAuthor.getAsString(), addonDescription.getAsString(), addonCategory.getAsInt(), addonEnabled.getAsBoolean(), addonIncludeInJar.getAsBoolean(), addonRestart.getAsBoolean(), addonVerified.getAsBoolean(), addonMcVersion.getAsString(), sorting);
                        this.allMinecraftVersionsAddonInfoList.add(onlineAddonInfo);
                        if (!majorVersion.equals(entry.getKey())) {
                            continue;
                        }
                        final UUID uuid = onlineAddonInfo.getUuid();
                        this.addonInfoMap.put(uuid, onlineAddonInfo);
                        this.addonInfoList.add(onlineAddonInfo);
                        if (LabyModOFAddon.INSTALLED_VERSION != null && LabyModOFAddon.OPTIFINE_VERSIONS.containsValue(uuid)) {
                            onlineAddonInfo.setName(LabyModOFAddon.INSTALLED_VERSION);
                        }
                        AddonLoader.getOfflineAddons().remove(uuid);
                        Debug.log(Debug.EnumDebugMode.ADDON, "Resolved addon " + onlineAddonInfo.getName() + " " + onlineAddonInfo.getVersion() + " by " + onlineAddonInfo.getAuthor());
                    }
                }
            }
            this.addonInfoMap = this.addonInfoMap;
            this.addonInfoList = this.addonInfoList;
            for (final AddonInfo offlineAddon : AddonLoader.getOfflineAddons()) {
                this.addonInfoList.add(offlineAddon);
                this.addonInfoMap.put(offlineAddon.getUuid(), offlineAddon);
            }
            this.loaded = true;
        }
        catch (Exception e) {
            Debug.log(Debug.EnumDebugMode.ADDON, "Failed getting available addons!");
            e.printStackTrace();
        }
        LabyMod.getInstance().getEventManager().registerShutdownHook(new Runnable() {
            @Override
            public void run() {
                try {
                    Debug.log(Debug.EnumDebugMode.ADDON, "Addon shutdown hook!");
                    boolean deleteOptifine = false;
                    boolean deleteAction = false;
                    for (final LabyModAddon labyModAddon : AddonLoader.getAddons()) {
                        if (labyModAddon.about.deleted) {
                            deleteAction = true;
                        }
                    }
                    if (deleteAction) {
                        final File file = AddonLoader.getDeleteQueueFile();
                        final FileWriter fileWriter = new FileWriter(file, true);
                        try {
                            final Path path = file.toPath();
                            Files.setAttribute(path, "dos:hidden", true, new LinkOption[0]);
                        }
                        catch (Exception error) {
                            Debug.log(Debug.EnumDebugMode.ADDON, "Can't hide file: " + error.getMessage());
                        }
                        for (final LabyModAddon labyModAddon2 : AddonLoader.getAddons()) {
                            if (!labyModAddon2.about.deleted) {
                                continue;
                            }
                            Debug.log(Debug.EnumDebugMode.ADDON, "Delete " + labyModAddon2.about.name);
                            if (labyModAddon2 instanceof LabyModOFAddon) {
                                deleteOptifine = true;
                            }
                            else {
                                final File foundFile = AddonLoader.getFiles().get(labyModAddon2.about.uuid);
                                if (foundFile != null && foundFile.exists()) {
                                    fileWriter.write(foundFile.getName() + "\n");
                                }
                                else {
                                    Debug.log(Debug.EnumDebugMode.ADDON, "Error while adding " + labyModAddon2.about.name + " to delete queue");
                                }
                            }
                        }
                        fileWriter.close();
                    }
                    if (deleteOptifine) {
                        LabyModOFAddon.executeOfHandler(false);
                    }
                    else if (LabyModOFAddon.INSTALL) {
                        LabyModOFAddon.executeOfHandler(true);
                    }
                }
                catch (Throwable error2) {
                    error2.printStackTrace();
                }
            }
        });
    }
    
    public void createElementsForAddons() {
        for (final AddonInfo info : getInstance().getAddonInfoList()) {
            info.init();
        }
        for (final AddonInfo info : AddonLoader.getOfflineAddons()) {
            info.init();
        }
    }
    
    public Map<UUID, AddonInfo> getAddonInfoMap() {
        return this.addonInfoMap;
    }
    
    public List<AddonInfo> getAddonInfoList() {
        return this.addonInfoList;
    }
    
    public List<OnlineAddonInfo> getAllMinecraftVersionsAddonInfoList() {
        return this.allMinecraftVersionsAddonInfoList;
    }
    
    public List<String> getCategories() {
        return this.categories;
    }
    
    public CheckBox[] getCategorieCheckboxList() {
        return this.categorieCheckboxList;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
}
