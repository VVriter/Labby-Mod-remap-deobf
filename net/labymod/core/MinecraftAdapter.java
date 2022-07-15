//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import net.labymod.main.*;
import net.labymod.user.cosmetic.*;
import java.util.*;
import io.netty.buffer.*;
import net.labymod.labyconnect.packets.*;
import com.mojang.authlib.properties.*;
import com.mojang.authlib.minecraft.*;
import com.google.gson.*;
import net.labymod.utils.manager.*;

public interface MinecraftAdapter
{
    void init(final LabyMod p0);
    
    bud getPlayer();
    
    bsb getWorld();
    
    ChatComponent getChatComponent(final Object p0);
    
    String getBiome();
    
    void displayMessageInChat(final String p0);
    
    void displayMessageInChatURL(final String p0, final String p1);
    
    void displayMessageInChatCustomAction(final String p0, final int p1, final String p2);
    
    boolean isBlocking(final aed p0);
    
    void playSound(final nf p0, final float p1);
    
    int getItemInUseMaxCount();
    
    boolean isHandActive();
    
    aip getItemInUse();
    
    void updateOnlineServers(final bnj p0, final bsf p1);
    
    brz getConnection();
    
    uz getInvisiblityPotion();
    
    vg getRidingEntity(final bua p0);
    
    aip getItem(final aec p0, final int p1);
    
    int getAnimationsToGo(final aip p0);
    
    BlockPosition getPosition(final Object p0);
    
    String getClickEventValue(final int p0, final int p1);
    
    ain getItemBow();
    
    List<?> splitText(final Object p0, final int p1, final bip p2, final boolean p3, final boolean p4);
    
    aip getMainHandItem();
    
    aip getOffHandItem();
    
    int getStackSize(final aip p0);
    
    String getPotionDurationString(final va p0);
    
    uz getPotion(final va p0);
    
    ain getTargetBlockItem();
    
    bcz getLavaMaterial();
    
    boolean isRightArmPoseBow(final ModelCosmetics p0);
    
    boolean isAimedBow(final ModelCosmetics p0);
    
    va getPotionEffect(final uz p0, final int p1, final int p2);
    
    boolean isSelected(final bnj p0, final int p1);
    
    int getSelectedServerInSelectionList(final bnj p0);
    
    void sendPluginMessage(final String p0, final gy p1);
    
    void updateServerList(final bnj p0, final bsf p1);
    
    void writeUniqueIdToBuffer(final gy p0, final UUID p1);
    
    String readStringFromBuffer(final gy p0);
    
    bhg getDummyScoreObjective();
    
    Object getTaggedChatComponent(final Object p0);
    
    bip getFontRenderer();
    
    void setButtonXPosition(final bja p0, final int p1);
    
    void setButtonYPosition(final bja p0, final int p1);
    
    void setTextFieldXPosition(final bje p0, final int p1);
    
    void setTextFieldYPosition(final bje p0, final int p1);
    
    int getXPosition(final bja p0);
    
    int getYPosition(final bja p0);
    
    int getXPosition(final bje p0);
    
    int getYPosition(final bje p0);
    
    int getTeamColorIndex(final bhh p0);
    
    void drawButton(final bja p0, final int p1, final int p2);
    
    bxf getCustomSignRenderer();
    
    String vectoString(final Vec3i p0);
    
    PacketBuf createPacketBuf(final ByteBuf p0);
    
    boolean hasInGameFocus();
    
    boolean isCurrentScreenNull();
    
    boolean isMinecraftChatOpen();
    
    long getLastAttackTime();
    
    vp getLastAttackedEntity();
    
    void handleBlockBuild();
    
    double calculateEyeMovement(final vg p0, final vg p1);
    
    void setSecondLayerBit(final bua p0, final int p1, final byte p2);
    
    void sendClientSettings(final String p0, final int p1, final aed.b p2, final boolean p3, final int p4);
    
    UUID isEmotePacket(final Object p0);
    
    boolean isJumpPredicted();
    
    boolean isJumpPredicted(final double p0, final double p1, final double p2, final double p3, final double p4, final double p5, final float p6, final float p7, final double p8, final double p9, final float p10, final float p11, final double p12, final double p13, final float p14, final boolean p15, final boolean p16, final boolean p17);
    
    int getGameMode(final UUID p0);
    
    vg getEntityMouseOver();
    
    boolean isElytraFlying(final vg p0);
    
    boolean isWearingElytra(final vg p0);
    
    vg getRenderViewEntity();
    
    boolean isAchievementGui(final blk p0);
    
    String getBossBarMessage();
    
    void setUseLeftHand(final boolean p0);
    
    boolean isUsingLeftHand();
    
    PropertyMap getPropertyMap();
    
    void handlePlayerListItemCache(final Object p0);
    
    void openGuiDisconnected(final blk p0, final String p1, final String p2);
    
    void clearChatMessages();
    
    ph getPlayerProfileCache();
    
    MinecraftSessionService getMinecraftSessionService();
    
    List<BoundingBox> getCollisionBoxes(final amu p0, final BoundingBox p1);
    
    boolean hasChestplate(final aed p0);
    
    double getDistanceToEntitySqr(final vg p0, final bud p1);
    
    String rawTextToString(final JsonElement p0);
    
    void windowClick(final int p0, final int p1, final int p2, final int p3, final bud p4);
    
    boolean isItemStackEmpty(final aip p0);
    
    List<String> getTooltip(final aip p0, final bud p1);
    
    MarkerManager.Vector3d getLookVector(final bud p0);
    
    MarkerManager.Vector3d getPositionEyes(final bud p0, final float p1);
    
    awt getBlockState(final amu p0, final double p1, final double p2, final double p3);
    
    boolean isSolid(final awt p0);
    
    boolean isMapInMainHand(final aed p0);
}
