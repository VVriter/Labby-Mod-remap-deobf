//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.mappings;

import net.labymod.core.asm.*;
import net.labymod.main.*;

public class UnobfuscatedImplementation implements MappingAdapter
{
    private static final boolean MC18;
    
    public String getDrawWorldBackgroundName() {
        return "drawWorldBackground";
    }
    
    public String getInitGuiName() {
        return "initGui";
    }
    
    public String getGuiDisconnectedMessageName() {
        return "message";
    }
    
    public String getChatComponentClassName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/util/IChatComponent" : "net/minecraft/util/text/ITextComponent";
    }
    
    public String getGuiDisconnectedName() {
        return "net/minecraft/client/gui/GuiDisconnected";
    }
    
    public String getGetUnformattedTextForChatName() {
        return UnobfuscatedImplementation.MC18 ? "getUnformattedTextForChat" : "getUnformattedComponentText";
    }
    
    public String getParentScreenName() {
        return "parentScreen";
    }
    
    public String getGuiScreenName() {
        return "net/minecraft/client/gui/GuiScreen";
    }
    
    public String getConnectName() {
        return "connect";
    }
    
    public String getOptionsBackgroundName() {
        return UnobfuscatedImplementation.MC18 ? "optionsBackground" : "OPTIONS_BACKGROUND";
    }
    
    public String getGuiName() {
        return "net/minecraft/client/gui/Gui";
    }
    
    public String getBindTextureName() {
        return "bindTexture";
    }
    
    public String getResourceLocationName() {
        return "net/minecraft/util/ResourceLocation";
    }
    
    public String getTextureManagerName() {
        return "net/minecraft/client/renderer/texture/TextureManager";
    }
    
    public String getTessellatorName() {
        return "net/minecraft/client/renderer/Tessellator";
    }
    
    public String getDrawName() {
        return "draw";
    }
    
    public String getGuiSlotOverlayBackgroundName() {
        return "overlayBackground";
    }
    
    public String getGuiConnectingName() {
        return "net/minecraft/client/multiplayer/GuiConnecting";
    }
    
    public String getGuiSlotName() {
        return "net/minecraft/client/gui/GuiSlot";
    }
    
    public String getEntityPlayerSpName() {
        return "net/minecraft/client/entity/EntityPlayerSP";
    }
    
    public String getEntityLivingBaseName() {
        return "net/minecraft/entity/EntityLivingBase";
    }
    
    public String getEntityClassName() {
        return "net/minecraft/entity/Entity";
    }
    
    public String getOrientCameraName() {
        return "orientCamera";
    }
    
    public String getEyeHeightName() {
        return "getEyeHeight";
    }
    
    public String getEntityRendererName() {
        return "net/minecraft/client/renderer/EntityRenderer";
    }
    
    public String getServerListEntryNormalName() {
        return "net/minecraft/client/gui/ServerListEntryNormal";
    }
    
    public String getServerName() {
        return UnobfuscatedImplementation.MC18 ? "server" : "server";
    }
    
    public String getServerMotdName() {
        return "serverMOTD";
    }
    
    public String getPingToServerName() {
        return "pingToServer";
    }
    
    public String getPopulationInfoName() {
        return "populationInfo";
    }
    
    public String getDrawEntryName() {
        return "drawEntry";
    }
    
    public String getServerDataName() {
        return "net/minecraft/client/multiplayer/ServerData";
    }
    
    public String getGuiMultiplayerName() {
        return "net/minecraft/client/gui/GuiMultiplayer";
    }
    
    public String getGuiScreenDrawScreenName() {
        return "drawScreen";
    }
    
    public String getNetHandlerPlayClientName() {
        return "net/minecraft/client/network/NetHandlerPlayClient";
    }
    
    public String getHandleCustomPayLoadName() {
        return "handleCustomPayload";
    }
    
    public String getCustomPayLoadPacketName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/network/play/server/S3FPacketCustomPayload" : "net/minecraft/network/play/server/SPacketCustomPayload";
    }
    
    public String getChannelNameName() {
        return "getChannelName";
    }
    
    public String getBufferDataName() {
        return "getBufferData";
    }
    
    public String getPacketBufferName() {
        return "net/minecraft/network/PacketBuffer";
    }
    
    public String getScaledResolutionName() {
        return "net/minecraft/client/gui/ScaledResolution";
    }
    
    public String getStartGameName() {
        return UnobfuscatedImplementation.MC18 ? "startGame" : "init";
    }
    
    public String getDrawSplashScreenName() {
        return "drawSplashScreen";
    }
    
    public String getCreateDisplayName() {
        return "createDisplay";
    }
    
    public String getRenderEntitiesName() {
        return "renderEntities";
    }
    
    public String getRenderGlobalName() {
        return "net/minecraft/client/renderer/RenderGlobal";
    }
    
    public String getCountEntitiesRenderedName() {
        return "countEntitiesRendered";
    }
    
    public String getCountEntitiesTotalName() {
        return "countEntitiesTotal";
    }
    
    public String getICameraClassName() {
        return "net/minecraft/client/renderer/culling/ICamera";
    }
    
    public String getMinecraftName() {
        return "net/minecraft/client/Minecraft";
    }
    
    public String getFullscreenName() {
        return "fullscreen";
    }
    
    public String getToggleFullscreenName() {
        return "toggleFullscreen";
    }
    
    public String getSetInitialDisplayModeName() {
        return "setInitialDisplayMode";
    }
    
    public String getItemRendererName() {
        return "net/minecraft/client/renderer/ItemRenderer";
    }
    
    public String getTransformFirstPersonItemName() {
        return UnobfuscatedImplementation.MC18 ? "transformFirstPersonItem" : "transformFirstPerson";
    }
    
    public String getItemStackName() {
        return "net/minecraft/item/ItemStack";
    }
    
    public String getRenderItemInFirstPersonName() {
        return "renderItemInFirstPerson";
    }
    
    public String getItemToRenderName() {
        return UnobfuscatedImplementation.MC18 ? "itemToRender" : "itemStackMainHand";
    }
    
    public String getPushMatrixName() {
        return "pushMatrix";
    }
    
    public String getModelPlayerName() {
        return "net/minecraft/client/model/ModelPlayer";
    }
    
    public String getServerPingerName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/client/network/OldServerPinger" : "net/minecraft/client/network/ServerPinger";
    }
    
    public String getGuiContainerName() {
        return "net/minecraft/client/gui/inventory/GuiContainer";
    }
    
    public String getGuiContainerMouseReleasedName() {
        return "mouseReleased";
    }
    
    public String getSlotName() {
        return "net/minecraft/inventory/Slot";
    }
    
    public String getSlotGetStackName() {
        return "getStack";
    }
    
    public String getItemName() {
        return "net/minecraft/item/Item";
    }
    
    public String getItemBucketName() {
        return "net/minecraft/item/ItemBucket";
    }
    
    public String getLastAttackerTimeName() {
        return UnobfuscatedImplementation.MC18 ? "lastAttackerTime" : "lastAttackedEntityTime";
    }
    
    public String getRightClickMouseName() {
        return "rightClickMouse";
    }
    
    public String getEntityPlayerName() {
        return "net/minecraft/entity/player/EntityPlayer";
    }
    
    public String getS21PacketChunkDataName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/network/play/server/S21PacketChunkData" : "net/minecraft/network/play/server/SPacketChunkData";
    }
    
    public String getS26PacketMapChunkBulkName() {
        return "net/minecraft/network/play/server/S26PacketMapChunkBulk";
    }
    
    public String getReadPacketDataName() {
        return "readPacketData";
    }
    
    public String getBootstrapName() {
        return "net/minecraft/init/Bootstrap";
    }
    
    public String getPrintToSYSOUTName() {
        return "printToSYSOUT";
    }
    
    public String getMessageDeserializerName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/util/MessageDeserializer" : "net/minecraft/network/NettyPacketDecoder";
    }
    
    public String getModelBipedName() {
        return "net/minecraft/client/model/ModelBiped";
    }
    
    public String getHandleResourcePackName() {
        return "handleResourcePack";
    }
    
    public String getPacketResourcePackSendName() {
        return "net/minecraft/network/play/server/S48PacketResourcePackSend";
    }
    
    public String getRunTickName() {
        return "runTick";
    }
    
    public String getThirdPersonViewName() {
        return "thirdPersonView";
    }
    
    public String getMCDefaultResourcePack() {
        return "mcDefaultResourcePack";
    }
    
    public String getClickMouseName() {
        return "clickMouse";
    }
    
    public String getGuiNewChatName() {
        return "net/minecraft/client/gui/GuiNewChat";
    }
    
    public String getGuiPlayerTabOverlayName() {
        return "net/minecraft/client/gui/GuiPlayerTabOverlay";
    }
    
    public String getGuiBossOverlayName() {
        return "net/minecraft/client/gui/GuiBossOverlay";
    }
    
    public String getGuiIngameName() {
        return "net/minecraft/client/gui/GuiIngame";
    }
    
    public String getGlStateManagerName() {
        return "net/minecraft/client/renderer/GlStateManager";
    }
    
    public String getEnableAlphaName() {
        return "enableAlpha";
    }
    
    public String getRenderPlayerStatsName() {
        return "renderPlayerStats";
    }
    
    public String getGuiMainMenuName() {
        return "net/minecraft/client/gui/GuiMainMenu";
    }
    
    public String getSplashTextName() {
        return "splashText";
    }
    
    public String getGuiScreenMouseClickedName() {
        return "mouseClicked";
    }
    
    public String getRealmsButtonName() {
        return "realmsButton";
    }
    
    public String getGuiButtonName() {
        return "net/minecraft/client/gui/GuiButton";
    }
    
    public String getSwitchToRealmsName() {
        return "switchToRealms";
    }
    
    public String getShouldRenderName() {
        return "shouldRender";
    }
    
    public String getDoRenderEntityName() {
        return "doRenderEntity";
    }
    
    public String getRenderManagerName() {
        return "net/minecraft/client/renderer/entity/RenderManager";
    }
    
    public String getSetDeadName() {
        return "setDead";
    }
    
    public String getLoadWorldName() {
        return "loadWorld";
    }
    
    public String getWorldClientName() {
        return "net/minecraft/client/multiplayer/WorldClient";
    }
    
    public String getRenderDebugBoundingBoxName() {
        return "renderDebugBoundingBox";
    }
    
    public String getPacketPlayerListItemName() {
        return UnobfuscatedImplementation.MC18 ? "net/minecraft/network/play/server/S38PacketPlayerListItem" : "net/minecraft/network/play/server/SPacketPlayerListItem";
    }
    
    public String getNetworkPlayerInfoName() {
        return "net/minecraft/client/network/NetworkPlayerInfo";
    }
    
    public String getRenderLivingBaseName() {
        return "net/minecraft/client/renderer/entity/RenderLivingBase";
    }
    
    public String getGameSettingsName() {
        return "net/minecraft/client/settings/GameSettings";
    }
    
    public String getSaveOptionsName() {
        return "saveOptions";
    }
    
    public String getDispatchKeypressesName() {
        return "dispatchKeypresses";
    }
    
    public String getShutdownMinecraftAppletName() {
        return "shutdownMinecraftApplet";
    }
    
    public String getCheckHotbarKeysName() {
        return "checkHotbarKeys";
    }
    
    public String getIsCallingFromMinecraftThreadNameName() {
        return "isCallingFromMinecraftThreadName";
    }
    
    public String getRenderPigName() {
        return "net/minecraft/client/renderer/entity/RenderPig";
    }
    
    public String getSpawnEntityInWorldName() {
        return UnobfuscatedImplementation.MC18 ? "spawnEntityInWorld" : "spawnEntity";
    }
    
    public String getRemoveEntityNameName() {
        return "removeEntity";
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
