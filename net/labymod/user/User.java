//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.concurrent.*;
import net.labymod.user.group.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.custom.handler.*;

public class User
{
    private UUID uuid;
    private Map<Integer, CosmeticData> cosmetics;
    private Map<Integer, List<String>> possibleRemoteIds;
    private List<Short> emotes;
    private List<Short> stickerPacks;
    protected short playingSticker;
    protected long stickerStartedPlaying;
    private LabyGroup group;
    private boolean dailyEmoteFlat;
    private String subTitle;
    private double subTitleSize;
    private boolean mojangCapeModified;
    private boolean mojangCapeVisible;
    private long nextPriorityCheck;
    private final UserTextureContainer cloakContainer;
    private final UserTextureContainer bandanaContainer;
    private final UserTextureContainer shoesContainer;
    private final UserTextureContainer kawaiiMaskContainer;
    private final UserTextureContainer coverMaskContainer;
    private final UserTextureContainer watchContainer;
    private final UserTextureContainer angelWingsContainer;
    private final UserTextureContainer capContainer;
    private final UserTextureContainer petDragonContainer;
    private final UserTextureContainer bunnyShoesContainer;
    private final UserTextureContainer scarfContainer;
    private final Map<Integer, UserTextureContainer> remoteContainer;
    private float maxNameTagHeight;
    private UserManager userManager;
    private boolean familiar;
    public long lastRendered;
    private boolean canSeeDraftCosmetics;
    private boolean hideCape;
    
    public User(final UUID uuid) {
        this.cosmetics = new ConcurrentHashMap<Integer, CosmeticData>();
        this.possibleRemoteIds = new ConcurrentHashMap<Integer, List<String>>();
        this.emotes = new ArrayList<Short>();
        this.stickerPacks = new ArrayList<Short>();
        this.playingSticker = -1;
        this.group = GroupManager.DEFAULT_GROUP;
        this.mojangCapeModified = false;
        this.mojangCapeVisible = true;
        this.nextPriorityCheck = -1L;
        this.remoteContainer = new HashMap<Integer, UserTextureContainer>();
        this.hideCape = false;
        this.uuid = uuid;
        this.cloakContainer = new UserTextureContainer("../capes", this.uuid);
        this.bandanaContainer = new UserTextureContainer("bandanas", this.uuid);
        this.shoesContainer = new UserTextureContainer("shoes");
        this.kawaiiMaskContainer = new UserTextureContainer("kawaiimasks");
        this.coverMaskContainer = new UserTextureContainer("covermasks");
        this.watchContainer = new UserTextureContainer("watches");
        this.angelWingsContainer = new UserTextureContainer("angelwings");
        this.capContainer = new UserTextureContainer("caps");
        this.petDragonContainer = new UserTextureContainer("petdragons");
        this.bunnyShoesContainer = new UserTextureContainer("bunnyshoes");
        this.scarfContainer = new UserTextureContainer("scarf");
        this.userManager = LabyMod.getInstance().getUserManager();
    }
    
    public LabyGroup getGroup() {
        return this.group;
    }
    
    public boolean hasCosmeticById(final int id) {
        return this.cosmetics.containsKey(id);
    }
    
    public void resetMaxNameTagHeight() {
        this.maxNameTagHeight = 0.0f;
    }
    
    public void applyNameTagHeight(final float nameTagHeight) {
        if (nameTagHeight > this.maxNameTagHeight) {
            this.maxNameTagHeight = nameTagHeight;
        }
    }
    
    public void unloadCosmeticTextures() {
        this.cloakContainer.unload();
        this.bandanaContainer.unload();
        this.shoesContainer.unload();
        this.kawaiiMaskContainer.unload();
        this.coverMaskContainer.unload();
        this.watchContainer.unload();
        this.angelWingsContainer.unload();
        this.capContainer.unload();
        this.petDragonContainer.unload();
        this.bunnyShoesContainer.unload();
        this.scarfContainer.unload();
        for (final UserTextureContainer container : this.remoteContainer.values()) {
            container.unload();
        }
        this.hideCape = false;
    }
    
    public boolean canRenderMojangCape(final bua player) {
        if (this.nextPriorityCheck > System.currentTimeMillis()) {
            return this.mojangCapeVisible;
        }
        this.nextPriorityCheck = System.currentTimeMillis() + 500L;
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final brz netHandlerPlayClient = LabyModCore.getMinecraft().getConnection();
        final bsc networkPlayerInfo = (netHandlerPlayClient != null) ? netHandlerPlayClient.a(this.getUuid()) : null;
        final nf locationOptifine = player.q();
        final nf locationMinecon = (networkPlayerInfo == null) ? null : networkPlayerInfo.h();
        this.setMojangCapeModified(locationMinecon == null || !locationMinecon.equals((Object)locationOptifine));
        final boolean enabled = LabyMod.getSettings().cosmetics && LabyMod.getSettings().cosmeticsCustomTextures;
        boolean canRenderMojangCape = !this.hideCape || !enabled;
        if (userManager.getCapePriority() != CloakImageHandler.EnumCapePriority.LABYMOD && (locationMinecon != null || locationOptifine != null)) {
            canRenderMojangCape = true;
        }
        return this.mojangCapeVisible = canRenderMojangCape;
    }
    
    public boolean isFamiliar() {
        return this.familiar;
    }
    
    public boolean isStickerVisible() {
        if (this.playingSticker == -1) {
            return false;
        }
        final long timePassed = System.currentTimeMillis() - this.stickerStartedPlaying;
        return timePassed < 4000L;
    }
    
    public void setGroup(final LabyGroup group) {
        this.group = ((group == null) ? GroupManager.DEFAULT_GROUP : group);
        this.canSeeDraftCosmetics = this.userManager.canSeeDraftCosmetics(this);
    }
    
    public void setFamiliar(final boolean familiar) {
        this.familiar = familiar;
    }
    
    public boolean isRendered() {
        return System.currentTimeMillis() - this.lastRendered < 1000L;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public Map<Integer, CosmeticData> getCosmetics() {
        return this.cosmetics;
    }
    
    public Map<Integer, List<String>> getPossibleRemoteIds() {
        return this.possibleRemoteIds;
    }
    
    public List<Short> getEmotes() {
        return this.emotes;
    }
    
    public List<Short> getStickerPacks() {
        return this.stickerPacks;
    }
    
    public short getPlayingSticker() {
        return this.playingSticker;
    }
    
    public long getStickerStartedPlaying() {
        return this.stickerStartedPlaying;
    }
    
    public boolean isDailyEmoteFlat() {
        return this.dailyEmoteFlat;
    }
    
    public String getSubTitle() {
        return this.subTitle;
    }
    
    public double getSubTitleSize() {
        return this.subTitleSize;
    }
    
    public boolean isMojangCapeModified() {
        return this.mojangCapeModified;
    }
    
    public boolean isMojangCapeVisible() {
        return this.mojangCapeVisible;
    }
    
    public long getNextPriorityCheck() {
        return this.nextPriorityCheck;
    }
    
    public UserTextureContainer getCloakContainer() {
        return this.cloakContainer;
    }
    
    public UserTextureContainer getBandanaContainer() {
        return this.bandanaContainer;
    }
    
    public UserTextureContainer getShoesContainer() {
        return this.shoesContainer;
    }
    
    public UserTextureContainer getKawaiiMaskContainer() {
        return this.kawaiiMaskContainer;
    }
    
    public UserTextureContainer getCoverMaskContainer() {
        return this.coverMaskContainer;
    }
    
    public UserTextureContainer getWatchContainer() {
        return this.watchContainer;
    }
    
    public UserTextureContainer getAngelWingsContainer() {
        return this.angelWingsContainer;
    }
    
    public UserTextureContainer getCapContainer() {
        return this.capContainer;
    }
    
    public UserTextureContainer getPetDragonContainer() {
        return this.petDragonContainer;
    }
    
    public UserTextureContainer getBunnyShoesContainer() {
        return this.bunnyShoesContainer;
    }
    
    public UserTextureContainer getScarfContainer() {
        return this.scarfContainer;
    }
    
    public Map<Integer, UserTextureContainer> getRemoteContainer() {
        return this.remoteContainer;
    }
    
    public float getMaxNameTagHeight() {
        return this.maxNameTagHeight;
    }
    
    public UserManager getUserManager() {
        return this.userManager;
    }
    
    public long getLastRendered() {
        return this.lastRendered;
    }
    
    public boolean isCanSeeDraftCosmetics() {
        return this.canSeeDraftCosmetics;
    }
    
    public void setCosmetics(final Map<Integer, CosmeticData> cosmetics) {
        this.cosmetics = cosmetics;
    }
    
    public void setPossibleRemoteIds(final Map<Integer, List<String>> possibleRemoteIds) {
        this.possibleRemoteIds = possibleRemoteIds;
    }
    
    public void setEmotes(final List<Short> emotes) {
        this.emotes = emotes;
    }
    
    public void setStickerPacks(final List<Short> stickerPacks) {
        this.stickerPacks = stickerPacks;
    }
    
    public void setPlayingSticker(final short playingSticker) {
        this.playingSticker = playingSticker;
    }
    
    public void setStickerStartedPlaying(final long stickerStartedPlaying) {
        this.stickerStartedPlaying = stickerStartedPlaying;
    }
    
    public void setDailyEmoteFlat(final boolean dailyEmoteFlat) {
        this.dailyEmoteFlat = dailyEmoteFlat;
    }
    
    public void setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
    }
    
    public void setSubTitleSize(final double subTitleSize) {
        this.subTitleSize = subTitleSize;
    }
    
    public void setMojangCapeModified(final boolean mojangCapeModified) {
        this.mojangCapeModified = mojangCapeModified;
    }
    
    public boolean isHideCape() {
        return this.hideCape;
    }
    
    public void setHideCape(final boolean hideCape) {
        this.hideCape = hideCape;
    }
}
