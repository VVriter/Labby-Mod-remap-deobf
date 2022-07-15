//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

import net.labymod.api.permissions.*;
import org.lwjgl.input.*;
import net.labymod.support.util.*;
import net.labymod.core.*;
import net.labymod.mojang.settings.*;
import net.labymod.ingamegui.*;
import net.labymod.core.asm.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.labymod.mojang.afec.*;
import java.util.*;
import net.labymod.splash.*;
import net.labymod.utils.*;
import net.labymod.gui.*;
import net.labymod.api.protocol.cinematic.*;
import net.labymod.user.cosmetic.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.settings.*;
import net.labymod.settings.elements.*;
import net.labymod.gui.elements.*;
import net.labymod.user.emote.*;
import net.labymod.user.sticker.*;
import net.labymod.user.*;
import net.labymod.ingamegui.modules.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.io.*;
import net.labymod.support.report.*;
import net.labymod.main.*;

public class BytecodeMethods
{
    private static final boolean MC18;
    private static int lastItemSlot;
    
    public static boolean spawnEntityInWorld(final boolean spawned, final vg entity) {
        if (entity instanceof aed) {
            LabyMod.getInstance().getLabyConnect().getTracker().onVisiblePlayer(entity.bm());
        }
        return spawned;
    }
    
    public static void removeEntity(final vg entity) {
        if (entity instanceof aed) {
            LabyMod.getInstance().getLabyConnect().getTracker().onEntityDestruct(entity.bm());
        }
    }
    
    public static boolean shouldCancelMouseClick(final boolean isHittingBlock) {
        if (LabyMod.getInstance() != null) {
            LabyMod.getInstance().getEventManager().callMouseInput(1);
        }
        return LabyMod.getInstance().getUserManager().getUserActionGui().isOpen() || LabyMod.getInstance().getEmoteRegistry().getEmoteSelectorGui().isOpen() || LabyMod.getInstance().getStickerRegistry().getStickerSelectorGui().isOpen() || ((!LabyMod.getSettings().oldBlockbuild || !Permissions.isAllowed(Permissions.Permission.BLOCKBUILD)) && isHittingBlock);
    }
    
    public static boolean modifyEventButtonState(final boolean vanillaEventButtonState) {
        final boolean isUsingControlKeys = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(29) || Keyboard.isKeyDown(56);
        final boolean fixedEventButtonState = Mouse.getEventButton() == 3 && !vanillaEventButtonState && isUsingControlKeys && Mouse.isButtonDown(4);
        return vanillaEventButtonState || fixedEventButtonState;
    }
    
    public static void shutdown() {
        Debug.log(Debug.EnumDebugMode.GENERAL, "Calling shutdown hooks...");
        LabyMod.getInstance().getLabyModAPI().getEventManager().callShutdownHook();
    }
    
    public static void handlePlayerListItem(final Object object) {
        LabyModCore.getMinecraft().handlePlayerListItemCache(object);
    }
    
    public static void writeOptions(final PrintWriter writer) {
        OptionsRemapper.INSTANCE.write(writer);
    }
    
    public static PrintWriter createWriter(final PrintWriter writer) {
        return (PrintWriter)new OptionsPrintWriter((Writer)writer);
    }
    
    public static void loadOptions() {
        OptionsRemapper.INSTANCE.init();
    }
    
    public static bsc getCachedPlayerInfo(final bsc minecraftPlayerInfo, final UUID uuid) {
        if (minecraftPlayerInfo == null) {
            return LabyMod.getInstance().getPlayerListDataCache().get(uuid);
        }
        return minecraftPlayerInfo;
    }
    
    public static void onPreRenderLivingBase() {
        if (LabyMod.getSettings().entityCulling) {
            bus.q();
        }
    }
    
    public static void onMouseClick() {
        if (LabyMod.getInstance() != null) {
            LabyMod.getInstance().getEventManager().callMouseInput(0);
        }
    }
    
    public static boolean isGuiBackground() {
        final blk screen = bib.z().m;
        return (screen != null && LabyModCore.getMinecraft().isAchievementGui(screen)) || LabyMod.getSettings().guiBackground;
    }
    
    public static boolean canSwitchShader() {
        return false;
    }
    
    public static boolean canRenderScoreboard() {
        return !ScoreboardModule.isEnabled() || !Module.isDrawn();
    }
    
    public static int onRenderCrosshair112(final int height) {
        return LabyMod.getInstance().getLabyModAPI().isCrosshairHidden() ? 0 : height;
    }
    
    public static boolean onRenderCrosshair18(final boolean renderCorsshair) {
        return !LabyMod.getInstance().getLabyModAPI().isCrosshairHidden() && renderCorsshair;
    }
    
    public static boolean shouldRenderBoundingBoxVector() {
        return !LabyMod.getSettings().oldHitbox;
    }
    
    public static void onRenderIngameOverlay() {
        final LabyMod labymod = LabyMod.getInstance();
        if (!LabyModCoreMod.isForge()) {
            final RenderGameOverlayEvent event = new RenderGameOverlayEvent(labymod.getPartialTicks(), labymod.getDrawUtils().getScaledResolution());
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
        labymod.getEventManager().callRenderIngameOverlay(labymod.getPartialTicks());
    }
    
    public static Thread isCallingFromMinecraftThread(final Thread minecraftThread) {
        final Thread currentThread = Thread.currentThread();
        final boolean isMinecraftThread = minecraftThread == currentThread;
        if (isMinecraftThread) {
            return minecraftThread;
        }
        final Thread asyncThread = (Thread)((LabyMod.getInstance() == null) ? null : LabyMod.getInstance().getAsyncTextureLoader());
        return (currentThread == asyncThread) ? asyncThread : currentThread;
    }
    
    public static boolean isAlternativeFrustrumCullingEnabled() {
        return LabyMod.getSettings().afecEnabled;
    }
    
    public static boolean shouldRender(final vg entity, final double x, final double y, final double z) {
        return EntityCulling.alternativeShouldRender(entity, x, y, z);
    }
    
    public static void doRenderEntity(final vg entity, final double x, final double y, final double z, final float partialTicks) {
        bus.G();
        LabyMod.getInstance().getEventManager().callRenderEntity(entity, x, y, z, partialTicks);
        bus.l();
        bus.H();
    }
    
    public static void onEntitySetDead(final vg entity) {
        if (bib.z().aF()) {
            EntityCulling.onEntityUnload(entity);
        }
    }
    
    public static void onLoadWorld(final bsb worldClient) {
        final amu prevWorld = (amu)LabyModCore.getMinecraft().getWorld();
        if (worldClient != null) {
            LabyMod.getInstance().getLabyConnect().getTracker().onLoadWorld();
        }
        if (worldClient == null && prevWorld != null) {
            for (final vg entity : prevWorld.e) {
                EntityCulling.onEntityUnload(entity);
            }
        }
    }
    
    public static boolean shouldRender18HeartAnimation(final boolean flag) {
        return (!LabyMod.getSettings().oldHearts || !Permissions.isAllowed(Permissions.Permission.ANIMATIONS)) && flag;
    }
    
    public static String onRenderMainMenu(final int mouseX, final int mouseY, final String splashText, final bja realmsButton) {
        SplashLoader.getLoader().render(mouseX, mouseY);
        if (!LabyModCoreMod.isForge()) {
            LabyMod.getInstance().getDrawUtils().drawString("LabyMod 3.9.41 ", 2.0, (double)(LabyMod.getInstance().getDrawUtils().getHeight() - 20));
        }
        if (realmsButton != null) {
            final String address = bib.z().t.aA;
            if (LabyMod.getSettings().quickPlay && address != null && !address.isEmpty()) {
                realmsButton.j = ((address.length() > 16) ? address.substring(0, 16) : address);
            }
        }
        final String customSplashText = SplashLoader.getLoader().getCustomSplashText();
        return (customSplashText == null) ? splashText : customSplashText;
    }
    
    public static boolean onConnectRealms() {
        final String address = bib.z().t.aA;
        if (LabyMod.getSettings().quickPlay && address != null && !address.isEmpty()) {
            LabyMod.getInstance().connectToServer(bib.z().t.aA);
            return true;
        }
        return false;
    }
    
    public static void onMouseClickedMainMenu(final int mouseX, final int mouseY, final int mouseButton) {
        SplashLoader.getLoader().onClick(mouseX, mouseY);
    }
    
    public static int renderSaturationBarAndModifyAirOffset(final int bubbleY) {
        if (!LabyMod.getSettings().showSaturation) {
            return bubbleY;
        }
        final aed entityplayer = (aed)bib.z().aa();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final bit scaledRes = draw.getScaledResolution();
        final float foodSaturation = entityplayer.di().e();
        final int x = scaledRes.a() / 2 + 91;
        final int y = scaledRes.b() - 39;
        final int foodY = y - 10;
        for (int tile = 0; tile < foodSaturation / 2.0f; ++tile) {
            final int foodX = x - tile * 8 - 9;
            draw.b(foodX, foodY, 16, 27, 9, 9);
            if (tile * 2 + 1 < foodSaturation) {
                draw.b(foodX, foodY, 52, 27, 9, 9);
            }
            if (tile * 2 + 1 == foodSaturation) {
                draw.b(foodX, foodY, 61, 27, 9, 9);
            }
        }
        return (foodSaturation > 0.0f) ? (bubbleY - 10) : bubbleY;
    }
    
    public static void onLoadEntityShader(final vg entity) {
        if (LabyMod.getInstance() != null && LabyMod.getInstance().isInGame() && cii.O && entity == LabyModCore.getMinecraft().getPlayer() && LabyMod.getSettings().loadedShader != null) {
            try {
                GuiShaderSelection.loadShader(new nf(LabyMod.getSettings().loadedShader));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean shouldCancelAnimation() {
        final boolean cancel = LabyMod.getInstance().getClientTickListener().isCancelSwingAnimation();
        if (cancel) {
            LabyMod.getInstance().getClientTickListener().setCancelSwingAnimation(false);
        }
        return cancel;
    }
    
    public static void onUpdateBlockBuild() {
        LabyModCore.getCoreAdapter().getMinecraftImplementation().handleBlockBuild();
    }
    
    public static boolean shouldRenderMultiplayerBackground() {
        return LabyMod.getInstance() == null || !LabyMod.getInstance().isInGame() || !(bib.z().m instanceof bnf);
    }
    
    public static void orientCamera() {
        final CinematicProtocol cinematicProtocol = LabyMod.getInstance().getCinematicProtocol();
        if (cinematicProtocol.isRunning()) {
            GL11.glRotatef(cinematicProtocol.getCurrentTilt(), 0.0f, 0.0f, 1.0f);
        }
    }
    
    public static void translateEyeHeight(final vg entity, final boolean invert) {
        if (LabyModCore.getMinecraft().getPlayer() != entity) {
            return;
        }
        if (LabyMod.getSettings().oldSneaking) {
            final SneakingAnimationThread animationThread = LabyMod.getInstance().getSneakingAnimationThread();
            final float ySize = (animationThread == null) ? 0.0f : animationThread.getYSize();
            float value = ySize - (entity.aU() ? 0.08f : 0.0f);
            if (invert) {
                value *= -1.0f;
            }
            bus.c(0.0f, value, 0.0f);
        }
        if (bib.z().t.aw == 0) {
            final EmoteRenderer emoteRenderer = LabyMod.getInstance().getEmoteRegistry().getEmoteRendererFor((bua)entity);
            if (emoteRenderer != null) {
                bus.b(-entity.v, 0.0f, 1.0f, 0.0f);
                emoteRenderer.transformEntity(entity, true, 0.0f, 0.0f);
                bus.b(entity.v, 0.0f, 1.0f, 0.0f);
            }
        }
    }
    
    public static double getCustomScale(final int scale, final int factor, final boolean isWidth) {
        final blk currentScreen = bib.z().m;
        if (currentScreen == null) {
            final LabyMod instance = LabyMod.getInstance();
            if (instance != null) {
                final EmoteRegistry emoteRegistry = instance.getEmoteRegistry();
                if (emoteRegistry != null) {
                    emoteRegistry.getEmoteSelectorGui().lockMouseMovementInCircle();
                }
                final StickerRegistry stickerRegistry = instance.getStickerRegistry();
                if (stickerRegistry != null) {
                    stickerRegistry.getStickerSelectorGui().lockMouseMovementInCircle();
                }
                final UserManager userManager = instance.getUserManager();
                if (userManager != null) {
                    userManager.getUserActionGui().lockMouseMovementInCircle();
                }
                final CinematicProtocol cinematicProtocol = instance.getCinematicProtocol();
                if (cinematicProtocol != null) {
                    cinematicProtocol.onRenderTick((TickEvent.RenderTickEvent)null);
                }
            }
        }
        else {
            boolean useCustomScaling = currentScreen instanceof LabyModModuleEditorGui;
            if (currentScreen instanceof StringElement.ExpandedStringElementGui && ((StringElement.ExpandedStringElementGui)currentScreen).getBackgroundScreen() instanceof LabyModModuleEditorGui) {
                useCustomScaling = true;
            }
            if (currentScreen instanceof ColorPicker.AdvancedColorSelectorGui && ((ColorPicker.AdvancedColorSelectorGui)currentScreen).getBackgroundScreen() instanceof LabyModModuleEditorGui) {
                useCustomScaling = true;
            }
            if (useCustomScaling) {
                return scale / LabyMod.getInstance().getDrawUtils().getCustomScaling();
            }
        }
        return scale / (double)factor;
    }
    
    public static boolean shouldKeepServerData() {
        return false;
    }
    
    public static void onReceivePluginMessage(final String channelName, final gy packetBuffer) {
        if (channelName != null && channelName.equals("MC|Brand")) {
            LabyMod.getInstance().getLabyModAPI().lastServerSwitchTime = System.currentTimeMillis();
        }
        LabyMod.getInstance().getEventManager().callAllPluginMessage(channelName, packetBuffer);
    }
    
    public static void onUpdateEntityCountInfo(final int rendered, final int total) {
        EntityCountModule.renderedEntityCount = rendered;
        EntityCountModule.totalEntityCount = total;
    }
    
    public static void borderlessWindowAtInitialDisplayMode(final boolean fullscreen) throws LWJGLException {
        if (fullscreen) {
            if (LabyMod.getSettings() != null && LabyMod.getSettings().borderlessWindow) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            }
            else {
                Display.setFullscreen(true);
            }
        }
        else {
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
        }
    }
    
    public static void borderlessWindowAtToggleFullscreen(final boolean fullscreen, final boolean pre) throws LWJGLException {
        if (LabyMod.getSettings() != null) {
            if (pre) {
                if (LabyMod.getSettings().borderlessWindow) {
                    System.setProperty("org.lwjgl.opengl.Window.undecorated", "" + !fullscreen);
                }
            }
            else {
                Display.setFullscreen(!LabyMod.getSettings().borderlessWindow && fullscreen);
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            }
        }
    }
    
    public static void transformFirstPersonItem(final aip itemToRender) {
        if (!BytecodeMethods.MC18) {
            return;
        }
        final int itemId = (itemToRender != null && itemToRender.c() != null) ? ain.a(itemToRender.c()) : 0;
        if (!Permissions.isAllowed(Permissions.Permission.ANIMATIONS)) {
            return;
        }
        boolean transformAllItems = LabyMod.getSettings().oldItemHold;
        if (LabyMod.getSettings().oldBow && itemId == 261) {
            if (LabyModCore.getMinecraft().getPlayer().au) {
                bus.b(-0.10333333, 0.0, 0.0);
                transformAllItems = false;
            }
            else if (LabyModCore.getMinecraft().getPlayer().cK() != 0) {
                bus.c(-0.053333335f, 0.03f, -0.013333334f);
                bus.b(1.7f, 1.0f, 0.0f, 0.0f);
                bus.b(0.9f, 0.0f, 1.0f, 0.0f);
                bus.b(0.0f, 0.0f, 0.0f, 1.0f);
                transformAllItems = false;
            }
        }
        if (LabyMod.getSettings().oldSword && itemToRender != null && itemToRender.c() instanceof ajy) {
            if (LabyModCore.getMinecraft().getPlayer().au && LabyModCore.getMinecraft().getPlayer().cK() != 0) {
                bus.c(-0.07333333f, 0.05f, -0.11333334f);
                transformAllItems = false;
            }
            else if (LabyModCore.getMinecraft().getPlayer().au) {
                bus.b(-0.10333333, 0.0, 0.0);
                transformAllItems = false;
            }
            else if (LabyModCore.getMinecraft().getPlayer().cK() != 0) {
                bus.b(0.0, 0.04, -0.036666665);
                transformAllItems = false;
            }
        }
        if (LabyMod.getSettings().oldFishing && itemId == 346) {
            bus.c(0.0f, 0.0f, -0.3f);
            transformAllItems = false;
        }
        if (LabyMod.getSettings().oldFood && itemToRender != null && (itemToRender.c() instanceof aij || itemToRender.c() instanceof ajd)) {
            if (LabyModCore.getMinecraft().getPlayer().au) {
                bus.b(-0.10333333, 0.0, 0.0);
                transformAllItems = false;
            }
            else if (LabyModCore.getMinecraft().getPlayer().cK() != 0) {
                bus.b(0.0, -0.036666665, 0.07000000029802322);
                transformAllItems = false;
            }
        }
        if (transformAllItems) {
            bus.b(0.08, -0.01, 0.029999999329447746);
            bus.b(6.1f, 0.0f, 1.0f, 0.0f);
        }
    }
    
    public static float renderItemInFirstPerson(final aip itemToRender, final float f) {
        if (BytecodeMethods.MC18) {
            boolean swap = LabyMod.getSettings().leftHand;
            final int itemId = (itemToRender != null && itemToRender.c() != null) ? ain.a(itemToRender.c()) : 0;
            if (LabyMod.getSettings().swapBow && itemId == 261) {
                swap = !swap;
            }
            if (!LabyMod.getInstance().isHasLeftHand() && swap) {
                bus.b(-1.0f, 1.0f, 1.0f);
                bus.r();
            }
        }
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player != null) {
            final EmoteRenderer emoteRenderer = LabyMod.getInstance().getEmoteRegistry().getEmoteRendererFor((bua)player);
            if (emoteRenderer != null) {
                emoteRenderer.transformModel((bpx)null);
            }
        }
        return (BytecodeMethods.MC18 && LabyMod.getSettings().oldBlockhit && Permissions.isAllowed(Permissions.Permission.ANIMATIONS)) ? f : 0.0f;
    }
    
    public static boolean allowedToShiftAllItems(final aip itemStack) {
        final boolean enabled = LabyMod.getSettings().refillFix && Permissions.isAllowed(Permissions.Permission.REFILL_FIX);
        final boolean isItem = ain.a(itemStack.c()) == 282 || ain.a(itemStack.c()) == 373;
        return !enabled || !isItem;
    }
    
    public static boolean isCrosshairsyncEnabled() {
        return LabyMod.getSettings().crosshairSync && Permissions.isAllowed(Permissions.Permission.CROSSHAIR_SYNC);
    }
    
    public static void onAttack(final vg entity) {
        if (entity != null) {
            LabyMod.getInstance().getEventManager().callAttackEntity(entity);
        }
    }
    
    public static boolean modifyCriticalHit(final boolean flag) {
        return !LabyMod.getSettings().particleFix && flag;
    }
    
    public static float modifyEnchantmentCritical(final float f) {
        return LabyMod.getSettings().particleFix ? 0.0f : f;
    }
    
    public static void onReceiveChunkData(final Object packetBuffer, final Object packetObject) {
        LabyModCore.getCoreAdapter().getProtocolAdapter().onReceiveChunkPacket(packetBuffer, packetObject);
    }
    
    public static boolean useGCOnLoadWorld() {
        return LabyMod.getSettings() == null || !LabyMod.getSettings().fastWorldLoading;
    }
    
    public static void reportCrash(final File file, final Object mcCrashReport) {
        CrashReportHandler.getInstance().report(file, (b)mcCrashReport);
    }
    
    public static void onIncomingPacket(final Object packet) {
        LabyMod.getInstance().getEventManager().callincomingPacket(packet);
    }
    
    public static void transformModelPre(final Object model, final Object entity) {
        if (model instanceof bpx) {
            EmoteRenderer.resetModel((bpx)model);
        }
    }
    
    public static void transformModelPost(final Object model, final Object entity) {
        if (model instanceof bpx && entity instanceof bua) {
            final EmoteRenderer emoteRenderer = LabyMod.getInstance().getEmoteRegistry().getEmoteRendererFor((bua)entity);
            if (emoteRenderer != null) {
                emoteRenderer.transformModel((bpx)model);
            }
        }
    }
    
    public static boolean isItemStackEqual(final boolean vanillaResult, final aip itemStackA, final aip itemStackB) {
        if (LabyMod.getSettings() == null || !LabyMod.getSettings().oldItemSwitch) {
            return vanillaResult;
        }
        final boolean equalsWithoutDamage = LabyModCore.getMinecraft().getStackSize(itemStackA) == LabyModCore.getMinecraft().getStackSize(itemStackB) && itemStackA.c() == itemStackB.c() && (itemStackA.p() != null || itemStackB.p() == null) && (itemStackA.p() == null || itemStackA.p().equals((Object)itemStackB.p()));
        final boolean isBow = itemStackB.c() instanceof ahg || itemStackA.c() instanceof ahg;
        final boolean isFlintAndSteel = itemStackB.c() instanceof aii || itemStackA.c() instanceof aii;
        boolean result = (isBow || isFlintAndSteel) ? (equalsWithoutDamage || vanillaResult) : vanillaResult;
        if (LabyModCore.getMinecraft().getPlayer() != null) {
            final int itemSlot = LabyModCore.getMinecraft().getPlayer().bv.d;
            if (itemSlot != BytecodeMethods.lastItemSlot) {
                result = false;
            }
            if (itemStackA.equals(itemStackB)) {
                BytecodeMethods.lastItemSlot = itemSlot;
            }
        }
        return result;
    }
    
    public static boolean shouldCancelReequipAnimation(final aip itemStackA, final aip itemStackB) {
        return false;
    }
    
    public static void drawMenuOverlay(final int mouseX, final int mouseY, final float partialTicks) {
        LabyMod.getInstance().getRenderTickListener().drawMenuOverlay(mouseX, mouseY, partialTicks);
    }
    
    public static String modifyResourcePackURL(final String url) {
        return url.replaceAll("\\.{2}", "");
    }
    
    public static float subtractBackwardsWalkingAnimation(final float defaultValue) {
        return (LabyMod.getSettings() != null && LabyMod.getSettings().oldWalking) ? 0.0f : defaultValue;
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        BytecodeMethods.lastItemSlot = 0;
    }
}
