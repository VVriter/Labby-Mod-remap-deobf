//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.servermanager.group.*;
import net.labymod.gui.*;
import net.labymod.addon.online.*;
import net.labymod.utils.*;
import java.util.*;
import com.google.gson.*;
import net.labymod.addon.*;
import net.labymod.addon.online.info.*;

public class AddonRecommendationListener implements ServerMessageEvent, Consumer<ServerData>
{
    private LabyMod labymod;
    
    public AddonRecommendationListener(final LabyMod labymod) {
        this.labymod = labymod;
    }
    
    public void accept(final ServerData serverData) {
        final ServerGroup group = LabyMod.getInstance().getServerGroupProvider().detectGroup(serverData);
        if (group == null || group.getAddons() == null) {
            return;
        }
        this.loadRecommendedAddons(group.getDisplayName(), group.getAddons(), false);
    }
    
    private void loadRecommendedAddons(final String serverName, final JsonArray array, final boolean notifyServer) {
        final List<GuiAddonRecommendation.RecommendedAddon> addons = new ArrayList<GuiAddonRecommendation.RecommendedAddon>();
        for (int i = 0; i < array.size(); ++i) {
            final JsonObject addonObject = array.get(i).getAsJsonObject();
            final UUID uuid = UUID.fromString(addonObject.get("uuid").getAsString());
            final boolean required = addonObject.get("required").getAsBoolean();
            final GuiAddonRecommendation.RecommendedAddon addon = new GuiAddonRecommendation.RecommendedAddon(uuid, required, (OnlineAddonInfo)null);
            addons.add(addon);
        }
        this.loadRecommendedAddons(serverName, addons, notifyServer);
    }
    
    private void loadRecommendedAddons(final String serverName, final List<GuiAddonRecommendation.RecommendedAddon> addons, final boolean notifyServer) {
        for (final GuiAddonRecommendation.RecommendedAddon addon : addons) {
            addon.bindAddon(AddonInfoManager.getInstance());
        }
        if (!addons.isEmpty() && this.labymod.getCurrentServerData() != null) {
            final List<String> list = Arrays.asList(LabyMod.getSettings().ignoredAddonRecommendationServers);
            if (list.contains(ModUtils.getProfileNameByIp(this.labymod.getCurrentServerData().getIp()))) {
                if (notifyServer) {
                    sendMissingStatus(addons, true);
                }
                return;
            }
        }
        this.openGui(serverName, addons, notifyServer);
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!serverMessage.isJsonObject()) {
            return;
        }
        final JsonObject obj = serverMessage.getAsJsonObject();
        if (messageKey.equals("addon_recommendation") && obj.has("addons")) {
            String serverName = null;
            if (obj.has("server_name")) {
                serverName = obj.get("server_name").getAsString();
            }
            this.loadRecommendedAddons(serverName, obj.getAsJsonArray("addons"), true);
        }
    }
    
    private void openGui(final String serverName, final List<GuiAddonRecommendation.RecommendedAddon> addons, final boolean notifyServer) {
        final AddonInfoManager manager = AddonInfoManager.getInstance();
        manager.init();
        if (sendMissingStatus(addons, false)) {
            bib.z().a(() -> bib.z().a((blk)new GuiAddonRecommendation(serverName, (List)addons, notifyServer)));
        }
    }
    
    public static boolean sendMissingStatus(final List<GuiAddonRecommendation.RecommendedAddon> addons, final boolean isGuiClosed) {
        final AddonInfoManager manager = AddonInfoManager.getInstance();
        final JsonArray missingArray = new JsonArray();
        boolean addonIsMissing = false;
        for (final GuiAddonRecommendation.RecommendedAddon addon : addons) {
            addon.bindAddon(manager);
            if (addon.getOnlineAddonInfo() != null && !AddonLoader.hasInstalled((AddonInfo)addon.getOnlineAddonInfo())) {
                addonIsMissing = true;
                final JsonObject addonObject = new JsonObject();
                addonObject.addProperty("uuid", addon.getUuid().toString());
                addonObject.addProperty("name", addon.getOnlineAddonInfo().getName());
                missingArray.add((JsonElement)addonObject);
            }
        }
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gui_closed", Boolean.valueOf(isGuiClosed));
        jsonObject.addProperty("all_installed", Boolean.valueOf(!addonIsMissing));
        if (addonIsMissing) {
            jsonObject.add("missing", (JsonElement)missingArray);
        }
        LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("addon_recommendation", (JsonElement)jsonObject);
        return addonIsMissing;
    }
}
