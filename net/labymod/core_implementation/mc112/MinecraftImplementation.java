//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import java.lang.reflect.*;
import net.labymod.main.*;
import net.labymod.core_implementation.mc112.listener.*;
import net.minecraftforge.fml.relauncher.*;
import net.labymod.user.cosmetic.*;
import net.labymod.support.util.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.core_implementation.mc112.util.prediction.*;
import net.labymod.core_implementation.mc112.util.*;
import com.mojang.authlib.properties.*;
import com.mojang.authlib.*;
import net.labymod.labyconnect.*;
import com.mojang.authlib.minecraft.*;
import net.labymod.core.*;
import java.util.*;
import com.google.gson.*;
import net.labymod.utils.manager.*;

public class MinecraftImplementation implements MinecraftAdapter
{
    private Field fieldPlayerInfoMap;
    private Field fieldAddPlayerData;
    
    public void init(final LabyMod labymod) {
        new ServerIncomingPacketListener(labymod).register();
        try {
            (this.fieldPlayerInfoMap = ReflectionHelper.findField(brz.class, LabyModCore.getMappingAdapter().getFieldPlayerInfoMap())).setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public bud getPlayer() {
        return bib.z().h;
    }
    
    public bsb getWorld() {
        return bib.z().f;
    }
    
    public ChatComponent getChatComponent(final Object chatComponent) {
        if (chatComponent == null) {
            return null;
        }
        return new ChatComponent(((hh)chatComponent).c(), ((hh)chatComponent).d(), hh.a.a((hh)chatComponent));
    }
    
    public void openGuiDisconnected(final blk prevScreen, final String translation, final String message) {
        bib.z().a((blk)new bky((blk)((prevScreen == null) ? new blr() : prevScreen), "disconnect.lost", (hh)new ho(message)));
    }
    
    public String getBiome() {
        return (this.getPlayer() == null || this.getWorld() == null || this.getPlayer().c() == null) ? "?" : this.getWorld().C().a(this.getPlayer().c()).l();
    }
    
    public void displayMessageInChat(final String text) {
        for (final hh line : new StringMessage(text, false).getOutput()) {
            bib.z().q.d().a(line);
        }
    }
    
    public void displayMessageInChatURL(final String text, final String url) {
        this.displayMessageInChatCustomAction(text, hg.a.a.ordinal(), url);
    }
    
    public void displayMessageInChatCustomAction(final String text, final int actionId, final String command) {
        final hg clickEvent = new hg(hg.a.values()[actionId], command);
        for (final hh line : new StringMessage(text, false).getOutput()) {
            line.b().a(clickEvent);
            bib.z().q.d().a(line);
        }
    }
    
    public boolean isBlocking(final aed player) {
        final aip itemInUse = player.cJ();
        return player.cG() && itemInUse.c().f(itemInUse) == akc.d;
    }
    
    public void playSound(final nf resourceLocation, final float pitch) {
        final qe soundevent = (qe)qe.a.c((Object)resourceLocation);
        if (soundevent != null) {
            final cgp masterRecord = cgp.a(soundevent, pitch);
            if (masterRecord != null) {
                bib.z().U().a((cgt)masterRecord);
            }
        }
    }
    
    public int getItemInUseMaxCount() {
        return this.getPlayer().cL();
    }
    
    public boolean isHandActive() {
        return this.getPlayer().cG();
    }
    
    public aip getItemInUse() {
        return this.getPlayer().cJ();
    }
    
    public void updateOnlineServers(final bnj serverSelectionList, final bsf serverList) {
        serverSelectionList.a(serverList);
    }
    
    public brz getConnection() {
        return bib.z().v();
    }
    
    public uz getInvisiblityPotion() {
        return vb.n;
    }
    
    public vg getRidingEntity(final bua player) {
        return player.bJ();
    }
    
    public aip getItem(final aec inventoryPlayer, final int index) {
        return (aip)inventoryPlayer.a.get(index);
    }
    
    public int getAnimationsToGo(final aip itemStack) {
        return itemStack.D();
    }
    
    public BlockPosition getPosition(final Object blockPosition) {
        final et pos = (et)blockPosition;
        return new BlockPosition(pos.p(), pos.q(), pos.r());
    }
    
    public String getClickEventValue(final int x, final int y) {
        final hh iChatClickHover = bib.z().q.d().a(x, y);
        if (iChatClickHover == null || iChatClickHover.b() == null || iChatClickHover.b().h() == null) {
            return null;
        }
        final String value = iChatClickHover.b().h().b();
        return value;
    }
    
    public ain getItemBow() {
        return (ain)air.g;
    }
    
    public List<?> splitText(final Object textComponent, final int maxTextLenght, final bip fontRendererIn, final boolean p_178908_3_, final boolean forceTextColor) {
        return (List<?>)bjc.a((hh)textComponent, maxTextLenght, fontRendererIn, p_178908_3_, forceTextColor);
    }
    
    public aip getMainHandItem() {
        return (this.getPlayer() == null) ? null : this.getPlayer().b(ub.a);
    }
    
    public aip getOffHandItem() {
        return (this.getPlayer() == null) ? null : this.getPlayer().b(ub.b);
    }
    
    public int getStackSize(final aip itemStack) {
        return itemStack.E();
    }
    
    public String getPotionDurationString(final va potion) {
        return uz.a(potion, 1.0f);
    }
    
    public uz getPotion(final va potionEffect) {
        return potionEffect.a();
    }
    
    public ain getTargetBlockItem() {
        final bsb world = LabyModCore.getMinecraft().getWorld();
        if (world != null && bib.z().s != null) {
            final et current = bib.z().s.a();
            if (current != null) {
                final awt blockstate = world.o(current);
                if (blockstate != null) {
                    return blockstate.u().a((amu)world, current, blockstate).c();
                }
            }
        }
        return null;
    }
    
    public bcz getLavaMaterial() {
        return bcz.i;
    }
    
    public boolean isRightArmPoseBow(final ModelCosmetics modelCosmetics) {
        return modelCosmetics.m == bpx.a.d;
    }
    
    public boolean isAimedBow(final ModelCosmetics modelCosmetics) {
        return false;
    }
    
    public va getPotionEffect(final uz potion, final int duration, final int amplifier) {
        return new va(potion, duration, amplifier);
    }
    
    public boolean isSelected(final bnj serverSelectionList, final int index) {
        return serverSelectionList.e() == index;
    }
    
    public void sendPluginMessage(final String channelName, final gy packetBuffer) {
        if (this.getConnection() != null) {
            if (!channelName.equals("labymod3:ccp") && !channelName.equals("labymod3:main") && !channelName.equals("labymod3:shadow")) {
                Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "[OUT] " + channelName);
            }
            this.getConnection().a((ht)new lh(channelName, packetBuffer));
        }
    }
    
    public void updateServerList(final bnj serverSelectionList, final bsf serverList) {
        serverSelectionList.a(serverList);
    }
    
    public void writeUniqueIdToBuffer(final gy buffer, final UUID uuid) {
        buffer.a(uuid);
    }
    
    public String readStringFromBuffer(final gy buffer) {
        return buffer.e(Integer.MAX_VALUE);
    }
    
    public bhg getDummyScoreObjective() {
        final bhg dummyScoreObjective = new bhg(new bhk(), "Displayname", (bhq)new bhq() {
            public boolean b() {
                return true;
            }
            
            public bhq.a c() {
                return bhq.a.a;
            }
            
            public String a() {
                return "NAME";
            }
        });
        dummyScoreObjective.a().a(1, dummyScoreObjective);
        for (int i = 1; i < 4; ++i) {
            dummyScoreObjective.a().c("Player " + i, dummyScoreObjective);
        }
        return dummyScoreObjective;
    }
    
    public Object getTaggedChatComponent(final Object textComponent) {
        hh chatComponent = (hh)textComponent;
        try {
            final String json = hh.a.a(chatComponent);
            final String editedJson = TagManager.getTaggedMessage(json);
            if (editedJson != null) {
                chatComponent = hh.a.a(editedJson);
                chatComponent.a(new ho(" " + TagManager.SYMBOL).a(new hn().a(a.o)));
            }
        }
        catch (Exception ex) {}
        return chatComponent;
    }
    
    public bip getFontRenderer() {
        return bib.z().k;
    }
    
    public void setButtonXPosition(final bja button, final int x) {
        button.h = x;
    }
    
    public void setButtonYPosition(final bja button, final int y) {
        button.i = y;
    }
    
    public void setTextFieldXPosition(final bje field, final int x) {
        field.a = x;
    }
    
    public void setTextFieldYPosition(final bje field, final int y) {
        field.f = y;
    }
    
    public int getXPosition(final bja button) {
        return button.h;
    }
    
    public int getYPosition(final bja button) {
        return button.i;
    }
    
    public int getXPosition(final bje field) {
        return field.a;
    }
    
    public int getYPosition(final bje field) {
        return field.f;
    }
    
    public int getTeamColorIndex(final bhh team) {
        return team.m().b();
    }
    
    public void drawButton(final bja button, final int mouseX, final int mouseY) {
        button.a(bib.z(), mouseX, mouseY, LabyMod.getInstance().getPartialTicks());
    }
    
    public bxf getCustomSignRenderer() {
        return new bxf() {
            public void a(final awc te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
                SignManager.render(te);
                super.a(te, x, y, z, partialTicks, destroyStage, alpha);
            }
        };
    }
    
    public String vectoString(final Vec3i vec3i) {
        return MoreObjects.toStringHelper((Object)vec3i).add("x", vec3i.getX()).add("y", vec3i.getY()).add("z", vec3i.getZ()).toString();
    }
    
    public PacketBuf createPacketBuf(final ByteBuf byteBuf) {
        return new PacketBufNew(byteBuf);
    }
    
    public int getSelectedServerInSelectionList(final bnj serverSelectionList) {
        return serverSelectionList.e();
    }
    
    public boolean hasInGameFocus() {
        return bib.z().x;
    }
    
    public boolean isCurrentScreenNull() {
        return bib.z().m == null;
    }
    
    public boolean isMinecraftChatOpen() {
        return bib.z().m instanceof bkn;
    }
    
    public long getLastAttackTime() {
        return bib.z().h.bV();
    }
    
    public vp getLastAttackedEntity() {
        return bib.z().h.bU();
    }
    
    public void handleBlockBuild() {
    }
    
    public double calculateEyeMovement(final vg entityIn, final vg targetEntity) {
        final bhe eyePositionClient = targetEntity.f(0.0f);
        final bhe eyePositionEntity = entityIn.f(0.0f);
        bhe eyeLookVector = entityIn.e(0.0f);
        eyeLookVector = new bhe(eyeLookVector.b, 0.0, eyeLookVector.d);
        final bhe eyeVector = new bhe(eyePositionEntity.b - eyePositionClient.b, 0.0, eyePositionEntity.d - eyePositionClient.d).a().b(1.5707964f);
        return eyeLookVector.b(eyeVector);
    }
    
    public void setSecondLayerBit(final bua player, final int index, final byte value) {
    }
    
    public void sendClientSettings(final String langIn, final int viewIn, final aed.b chatVisibilityIn, final boolean enableColorsIn, final int modelPartFlagsIn) {
        if (!LabyMod.getInstance().isInGame()) {
            return;
        }
        final brz client = LabyModCore.getMinecraft().getPlayer().d;
        if (client != null) {
            client.a((ht)new lc(langIn, viewIn, chatVisibilityIn, enableColorsIn, modelPartFlagsIn, bib.z().t.C));
        }
    }
    
    public UUID isEmotePacket(final Object packet) {
        if (!(packet instanceof kd)) {
            return null;
        }
        final amu world = (amu)bib.z().f;
        final bud player = bib.z().h;
        if (world == null || player == null) {
            return null;
        }
        try {
            final kd metaPacket = (kd)packet;
            if (metaPacket.a() == null) {
                return null;
            }
            Byte value = null;
            for (final nb.a<?> watchableObject : metaPacket.a()) {
                if (watchableObject != null && watchableObject.b() instanceof Byte && watchableObject.a() != null && watchableObject.a().a() == 13) {
                    value = (Byte)watchableObject.b();
                }
                if (watchableObject != null && watchableObject.b() instanceof Byte && watchableObject.a() != null && watchableObject.a().a() == 14 && metaPacket.b() == player.S() && bib.z().t != null) {
                    watchableObject.a((Object)(byte)bib.z().t.C.ordinal());
                }
            }
            if (value == null || value >= 0) {
                return null;
            }
            final vg entity = world.a(metaPacket.b());
            return (entity == null || entity.bm().equals(LabyMod.getInstance().getPlayerUUID()) || !(entity instanceof aed)) ? null : entity.bm();
        }
        catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    public boolean isJumpPredicted() {
        final bud player = this.getPlayer();
        return player != null && JumpPrediction.isJumpPredicted(player.p, player.q, player.r, player.m, player.n, player.o, player.v, player.w, player.bw().b, player.bw().e, player.e.b, player.e.a, player.G, player.H, player.cy(), player.aU(), player.z, player.aS());
    }
    
    public boolean isJumpPredicted(final double posX, final double posY, final double posZ, final double prevPosX, final double prevPosY, final double prevPosZ, final float rotationYaw, final float rotationPitch, final double minY, final double maxY, final float moveForward, final float moveStrafe, final double width, final double height, final float aiMoveSpeed, final boolean sneaking, final boolean onGround, final boolean riding) {
        return JumpPrediction.isJumpPredicted(posX, posY, posZ, prevPosX, prevPosY, prevPosZ, rotationYaw, rotationPitch, minY, maxY, moveForward, moveStrafe, width, height, aiMoveSpeed, sneaking, onGround, riding);
    }
    
    public int getGameMode(final UUID uuid) {
        final brz netHandler = LabyModCore.getMinecraft().getPlayer().d;
        if (netHandler == null) {
            return -1;
        }
        final bsc info = netHandler.a(uuid);
        if (info == null) {
            return -1;
        }
        return info.b().a();
    }
    
    public vg getEntityMouseOver() {
        final bhc objectMouseOver = bib.z().s;
        if (objectMouseOver != null && objectMouseOver.d != null) {
            return objectMouseOver.d;
        }
        return null;
    }
    
    public boolean isElytraFlying(final vg entityIn) {
        return entityIn instanceof vp && ((vp)entityIn).cP();
    }
    
    public boolean isWearingElytra(final vg entity) {
        if (entity instanceof vp) {
            final aip itemstack = ((vp)entity).b(vl.e);
            return itemstack.c() == air.cS;
        }
        return false;
    }
    
    public vg getRenderViewEntity() {
        return bib.z().aa();
    }
    
    public boolean isAchievementGui(final blk screen) {
        return screen instanceof bmb;
    }
    
    public String getBossBarMessage() {
        return BossbarUtil.lastRenderedMessage;
    }
    
    public void setUseLeftHand(final boolean value) {
        bib.z().t.C = (value ? vo.a : vo.b);
    }
    
    public boolean isUsingLeftHand() {
        return bib.z().t.C == vo.a;
    }
    
    public PropertyMap getPropertyMap() {
        return bib.z().L();
    }
    
    public void handlePlayerListItemCache(final Object object) {
        final brz netHandler = LabyModCore.getMinecraft().getPlayer().d;
        if (netHandler == null) {
            return;
        }
        final LabyConnectUserTracker tracker = LabyMod.getInstance().getLabyConnect().getTracker();
        final jp packet = (jp)object;
        if (packet.b().ordinal() == 0) {
            final List<?> list = (List<?>)packet.a();
            for (final Object data : list) {
                try {
                    if (this.fieldAddPlayerData == null) {
                        (this.fieldAddPlayerData = ReflectionHelper.findField(data.getClass(), LabyModCore.getMappingAdapter().getAddPlayerDataProfileMappings())).setAccessible(true);
                    }
                    final GameProfile gameProfile = (GameProfile)this.fieldAddPlayerData.get(data);
                    final UUID uuid = gameProfile.getId();
                    final bsc networkPlayerInfo = netHandler.a(uuid);
                    if (networkPlayerInfo == null) {
                        continue;
                    }
                    if (LabyMod.getInstance().isPlayerListCacheEnabled()) {
                        LabyMod.getInstance().getPlayerListDataCache().put(uuid, networkPlayerInfo);
                    }
                    if (networkPlayerInfo.c() == -42) {
                        try {
                            final Map<UUID, bsc> playerInfoMap = (Map<UUID, bsc>)this.fieldPlayerInfoMap.get(netHandler);
                            playerInfoMap.remove(uuid);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    tracker.onPlayerInfoAdd(networkPlayerInfo);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (packet.b().ordinal() == 4) {
            final List<?> list = (List<?>)packet.a();
            for (final Object data : list) {
                try {
                    if (this.fieldAddPlayerData == null) {
                        (this.fieldAddPlayerData = ReflectionHelper.findField(data.getClass(), LabyModCore.getMappingAdapter().getAddPlayerDataProfileMappings())).setAccessible(true);
                    }
                    final GameProfile gameProfile = (GameProfile)this.fieldAddPlayerData.get(data);
                    final UUID uuid = gameProfile.getId();
                    tracker.onPlayerInfoRemove(uuid);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
    public void clearChatMessages() {
        bib.z().q.d().a(false);
    }
    
    public ph getPlayerProfileCache() {
        try {
            final Field field = awd.class.getDeclaredField("profileCache");
            field.setAccessible(true);
            return (ph)field.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public MinecraftSessionService getMinecraftSessionService() {
        try {
            final Field field = awd.class.getDeclaredField("sessionService");
            field.setAccessible(true);
            return (MinecraftSessionService)field.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<BoundingBox> getCollisionBoxes(final amu world, final BoundingBox box) {
        final List<bhb> aABBs = (List<bhb>)world.a((vg)null, new bhb(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxY));
        final List<BoundingBox> boxes = new ArrayList<BoundingBox>();
        for (final bhb axis : aABBs) {
            boxes.add(new BoundingBox(axis.a, axis.b, axis.c, axis.d, axis.e, axis.f));
        }
        return boxes;
    }
    
    public boolean hasChestplate(final aed player) {
        return !player.b(vl.e).b();
    }
    
    public double getDistanceToEntitySqr(final vg entityIn, final bud player) {
        final double d0 = entityIn.p - player.p;
        final double d2 = entityIn.q - player.q;
        final double d3 = entityIn.r - player.r;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public String rawTextToString(final JsonElement value) {
        return hh.a.b(value.toString()).e();
    }
    
    public void windowClick(final int windowId, final int lastHoveredSlotIndex, final int button, final int mode, final bud player) {
        bib.z().c.a(windowId, lastHoveredSlotIndex, button, afw.values()[mode], (aed)player);
    }
    
    public boolean isItemStackEmpty(final aip stack) {
        return stack == null || stack.b();
    }
    
    public List<String> getTooltip(final aip stack, final bud player) {
        return (List<String>)stack.a((aed)player, (akb)(bib.z().t.z ? akb.a.b : akb.a.a));
    }
    
    public MarkerManager.Vector3d getLookVector(final bud player) {
        final bhe vec = player.aJ();
        return new MarkerManager.Vector3d(vec.b, vec.c, vec.d);
    }
    
    public MarkerManager.Vector3d getPositionEyes(final bud player, final float partialTicks) {
        final bhe vec = player.f(partialTicks);
        return new MarkerManager.Vector3d(vec.b, vec.c, vec.d);
    }
    
    public awt getBlockState(final amu world, final double x, final double y, final double z) {
        return world.o(new et(x, y, z));
    }
    
    public boolean isSolid(final awt state) {
        return state.a().a();
    }
    
    public boolean isMapInMainHand(final aed player) {
        return player.co() != null && player.co().c() == air.bl;
    }
}
