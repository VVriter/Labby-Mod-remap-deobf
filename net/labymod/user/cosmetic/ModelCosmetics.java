//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic;

import net.labymod.user.cosmetic.sticker.*;
import net.labymod.user.cosmetic.util.*;
import java.util.concurrent.*;
import net.labymod.main.*;
import net.labymod.user.cosmetic.remote.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import net.labymod.user.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.geometry.*;
import java.util.*;
import net.labymod.user.emote.*;
import net.labymod.api.permissions.*;

public class ModelCosmetics extends bqj
{
    private static final CosmeticClassLoader cosmeticClassLoader;
    private final StickerRenderer stickerRenderer;
    public Map<Integer, CosmeticRenderer<CosmeticData>> cosmeticRenderers;
    private boolean mc18;
    private float partialTicks;
    private boolean isSlim;
    
    public ModelCosmetics(final float modelSize, final boolean value) {
        super(modelSize, value);
        this.cosmeticRenderers = new ConcurrentHashMap<Integer, CosmeticRenderer<CosmeticData>>();
        this.mc18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        this.partialTicks = 1.0f;
        this.isSlim = value;
        this.stickerRenderer = new StickerRenderer(this, modelSize);
        try {
            synchronized (this) {
                final Map<Integer, CosmeticRenderer<CosmeticData>> cosmeticRenderersCopy = new HashMap<Integer, CosmeticRenderer<CosmeticData>>(this.cosmeticRenderers);
                for (final Class<?> loadedClassInfo : ModelCosmetics.cosmeticClassLoader.getCosmeticClasses()) {
                    final CosmeticRenderer<CosmeticData> cosmeticRenderer = (CosmeticRenderer<CosmeticData>)loadedClassInfo.newInstance();
                    cosmeticRenderersCopy.put(cosmeticRenderer.getCosmeticId(), cosmeticRenderer);
                    cosmeticRenderer.addModels(this, modelSize);
                }
                this.cosmeticRenderers = cosmeticRenderersCopy;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        LabyMod.getInstance().getUserManager().getRemoteCosmeticLoader().getAsync(new IRemoteCallback() {
            @Override
            public void load(final CosmeticRenderer<?> renderer) {
                try {
                    synchronized (ModelCosmetics.this) {
                        final Map<Integer, CosmeticRenderer<CosmeticData>> cosmeticRenderersCopy = new HashMap<Integer, CosmeticRenderer<CosmeticData>>(ModelCosmetics.this.cosmeticRenderers);
                        cosmeticRenderersCopy.put(renderer.getCosmeticId(), (CosmeticRenderer<CosmeticData>)renderer);
                        ModelCosmetics.this.cosmeticRenderers = cosmeticRenderersCopy;
                    }
                    renderer.addModels(ModelCosmetics.this, modelSize);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void unload(final CosmeticRenderer<?> renderer) {
                synchronized (ModelCosmetics.this) {
                    final Map<Integer, CosmeticRenderer<CosmeticData>> cosmeticRenderersCopy = new HashMap<Integer, CosmeticRenderer<CosmeticData>>(ModelCosmetics.this.cosmeticRenderers);
                    cosmeticRenderersCopy.remove(renderer.getCosmeticId());
                    ModelCosmetics.this.cosmeticRenderers = cosmeticRenderersCopy;
                }
            }
        });
    }
    
    public ModelCosmetics(final float modelSize, final float f, final int i, final int j) {
        this(modelSize, false);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (LabyMod.getSettings().cosmetics) {
            for (final CosmeticRenderer<?> renderer : this.cosmeticRenderers.values()) {
                renderer.onTick();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (LabyMod.getSettings().cosmetics) {
            for (final CosmeticRenderer<?> renderer : this.cosmeticRenderers.values()) {
                renderer.onRenderWorld();
            }
        }
    }
    
    public void renderFirstPersonArm(final bua clientPlayer, final boolean rightHand) {
        if (LabyMod.getSettings().cosmetics) {
            try {
                final UserManager userManager = LabyMod.getInstance().getUserManager();
                final UUID uuid = clientPlayer.bm();
                if ((userManager.isWhitelisted(uuid) || uuid.getLeastSignificantBits() == 0L) && !clientPlayer.aX()) {
                    final User user = userManager.getUser(clientPlayer.bm());
                    bus.G();
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    final User clientUser = userManager.getClientUser();
                    final boolean canSeeDraftCosmetics = clientUser != null && clientUser.isCanSeeDraftCosmetics();
                    for (final Map.Entry<Integer, CosmeticData> entry : user.getCosmetics().entrySet()) {
                        final CosmeticData data = entry.getValue();
                        final boolean allowedToSee = data != null && (!data.isDraft() || canSeeDraftCosmetics);
                        if (data != null && data.isEnabled()) {
                            if (!allowedToSee) {
                                continue;
                            }
                            final CosmeticRenderer<CosmeticData> cosmeticRenderer = this.cosmeticRenderers.get(entry.getKey());
                            if (cosmeticRenderer == null || !cosmeticRenderer.isVisibleInFirstPerson((Object)data, rightHand)) {
                                continue;
                            }
                            cosmeticRenderer.render(this, (vg)clientPlayer, (Object)data, 0.0625f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, LabyMod.getInstance().getPartialTicks(), true);
                        }
                    }
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.H();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void a(final vp entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTickTime) {
        super.a(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.partialTicks = partialTickTime;
    }
    
    public void a(final vg entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, float yaw, float pitch, final float scale) {
        boolean swap = LabyMod.getSettings().leftHand;
        final aip itemStack = LabyModCore.getMinecraft().getMainHandItem();
        final int itemId = (itemStack != null && itemStack.c() != null) ? ain.a(itemStack.c()) : 0;
        if (LabyMod.getSettings().swapBow && itemId == 261) {
            swap = !swap;
        }
        if ((swap && LabyModCore.getMinecraft().getItemInUseMaxCount() != 0 && itemId == 261) || (swap && LabyMod.getInstance().isHasLeftHand())) {
            swap = false;
        }
        if (entityIn instanceof bua && LabyMod.getSettings().emotes) {
            final bua abstractClientPlayer = (bua)entityIn;
            final EmoteRenderer emoteRenderer = LabyMod.getInstance().getEmoteRegistry().getEmoteRendererFor(abstractClientPlayer);
            if (emoteRenderer != null && emoteRenderer.isVisible()) {
                emoteRenderer.checkForNextFrame();
                emoteRenderer.animate();
                emoteRenderer.transformEntity(entityIn, false, yaw, pitch);
                yaw = emoteRenderer.getFadedYaw();
                pitch = emoteRenderer.getFadedPitch();
            }
            this.e.g = yaw / 57.295776f;
            this.e.f = pitch / 57.295776f;
        }
        if (swap) {
            this.transformForLeftHand(entityIn, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, scale);
        }
        else {
            if (LabyMod.getSettings().entityCulling) {
                bus.q();
            }
            super.a(entityIn, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, scale);
        }
        if (LabyModCore.getMinecraft().isElytraFlying(entityIn)) {
            pitch = -45.0f;
        }
        if (entityIn instanceof bua && (LabyMod.getSettings().cosmetics || LabyMod.getSettings().stickers)) {
            final bua abstractClientPlayer = (bua)entityIn;
            final UserManager userManager = LabyMod.getInstance().getUserManager();
            final UUID uuid = abstractClientPlayer.bm();
            if ((userManager.isWhitelisted(uuid) || uuid.getLeastSignificantBits() == 0L || LabyMod.getInstance().getPriorityOverlayRenderer().isWatermarkValid()) && !entityIn.aX()) {
                final User user = userManager.getUser(uuid);
                user.lastRendered = System.currentTimeMillis();
                if (LabyMod.getSettings().cosmetics) {
                    final nf tex = abstractClientPlayer.m();
                    user.resetMaxNameTagHeight();
                    bus.G();
                    bus.e();
                    if (entityIn.aU()) {
                        bus.c(0.0f, 0.2f, 0.0f);
                    }
                    final User clientUser = userManager.getClientUser();
                    final boolean canSeeDraftCosmetics = clientUser != null && clientUser.isCanSeeDraftCosmetics();
                    final double distanceSqr = LabyMod.getInstance().isInGame() ? LabyModCore.getMinecraft().getDistanceToEntitySqr(entityIn, LabyModCore.getMinecraft().getPlayer()) : 0.0;
                    try {
                        for (final Map.Entry<Integer, CosmeticData> entry : user.getCosmetics().entrySet()) {
                            final CosmeticData data = entry.getValue();
                            final boolean allowedToSee = data != null && (!data.isDraft() || canSeeDraftCosmetics);
                            if (data != null && data.isEnabled()) {
                                if (!allowedToSee) {
                                    continue;
                                }
                                if (LabyMod.getSettings().cosmeticsHideInDistance && data.priorityLevel != 0) {
                                    final int minVisibleDistance = 5;
                                    final int priorityMultiplier = 3;
                                    final int distanceExtension = 5 - Math.min(5, data.priorityLevel);
                                    final double visibleDistanceSqr = Math.pow(minVisibleDistance + distanceExtension * priorityMultiplier, 2.0);
                                    if (distanceSqr > visibleDistanceSqr) {
                                        continue;
                                    }
                                }
                                bus.G();
                                bus.l();
                                bus.f();
                                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                bus.f();
                                bus.a(0);
                                bus.a(1);
                                bus.h();
                                bus.a(1032, 5634);
                                bus.a(770, 771, 1, 0);
                                final CosmeticRenderer<CosmeticData> cosmeticRenderer = this.cosmeticRenderers.get(entry.getKey());
                                if (cosmeticRenderer != null) {
                                    if (swap && !cosmeticRenderer.hasLeftHandSupport()) {
                                        bus.b(-1.0f, 1.0f, 1.0f);
                                        bus.r();
                                    }
                                    try {
                                        final boolean hasRemote = data instanceof RemoteData || cosmeticRenderer instanceof GeometryCosmetic || cosmeticRenderer instanceof GeometryPet;
                                        final boolean isFullRemote = data instanceof RemoteData && (cosmeticRenderer instanceof GeometryCosmetic || cosmeticRenderer instanceof GeometryPet);
                                        if (!hasRemote || isFullRemote) {
                                            cosmeticRenderer.render(this, entityIn, (Object)data, scale, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, this.partialTicks, false);
                                        }
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (swap && !cosmeticRenderer.hasLeftHandSupport()) {
                                        bus.b(-1.0f, 1.0f, 1.0f);
                                    }
                                }
                                bus.q();
                                bus.a(770, 771, 1, 0);
                                bus.y();
                                bus.j(7424);
                                bus.e();
                                bus.l();
                                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                bus.l();
                                bus.I();
                                if (cosmeticRenderer != null) {
                                    user.applyNameTagHeight(cosmeticRenderer.getNameTagHeight());
                                }
                                bib.z().N().a(tex);
                                bus.H();
                            }
                        }
                    }
                    catch (ConcurrentModificationException ex) {}
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.e();
                    bus.H();
                }
                if (LabyMod.getSettings().stickers && user.isStickerVisible()) {
                    final long timePassed = System.currentTimeMillis() - user.getStickerStartedPlaying();
                    this.stickerRenderer.render(entityIn, user, timePassed, scale, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch);
                }
            }
        }
        if (swap) {
            bus.b(-1.0f, 1.0f, 1.0f);
            bus.r();
        }
    }
    
    public void a(final float movementFactor, final float walkingSpeed, final float tickValue, final float var4, final float var5, final float var6, final vg entityIn) {
        super.a(movementFactor, walkingSpeed, tickValue, var4, var5, var6, entityIn);
        if (LabyModCore.getMinecraft().isRightArmPoseBow(this) && this.mc18 && LabyMod.getSettings().oldSword && Permissions.isAllowed(Permissions.Permission.ANIMATIONS)) {
            this.h.g = 0.0f;
            if (this.o > -9990.0f) {
                final brs h = this.h;
                h.g += this.g.g;
                final brs h2 = this.h;
                h2.g += this.g.g * 2.0f;
            }
            if (LabyModCore.getMinecraft().isAimedBow(this)) {
                this.h.g = -0.1f + this.e.g;
            }
            this.b.g = this.h.g;
        }
        for (final CosmeticRenderer<CosmeticData> cosmeticRenderer : this.cosmeticRenderers.values()) {
            cosmeticRenderer.setRotationAngles(this, movementFactor, walkingSpeed, tickValue, var4, var5, var6, entityIn);
        }
    }
    
    public void setInvisible(final boolean invisible) {
        for (final CosmeticRenderer<CosmeticData> cosmeticRenderer : this.cosmeticRenderers.values()) {
            cosmeticRenderer.setInvisible(invisible);
        }
    }
    
    public void a(final boolean visible) {
        for (final CosmeticRenderer<CosmeticData> cosmeticRenderer : this.cosmeticRenderers.values()) {
            cosmeticRenderer.setInvisible(visible);
        }
    }
    
    public void a(final String partName, final int x, final int y) {
        super.a(partName, x, y);
    }
    
    private void transformForLeftHand(final vg entityIn, final float var1, final float var2, final float var3, final float var4, final float var5, final float scale) {
        this.h.k = true;
        this.b.k = true;
        this.i.k = true;
        this.a.k = true;
        super.a(entityIn, var1, var2, var3, var4, var5, scale);
        bus.G();
        bus.b(-1.0f, 1.0f, 1.0f);
        bus.r();
        this.h.k = false;
        this.b.k = false;
        this.i.k = false;
        this.a.k = false;
        if (entityIn.aU()) {
            bus.c(0.0f, 0.2f, 0.0f);
        }
        this.h.a(scale);
        this.b.a(scale);
        this.i.a(scale);
        this.a.a(scale);
        bus.b(-1.0f, 1.0f, 1.0f);
        bus.r();
        bus.H();
    }
    
    public Map<Integer, CosmeticRenderer<CosmeticData>> getCosmeticRenderers() {
        return this.cosmeticRenderers;
    }
    
    public boolean isSlim() {
        return this.isSlim;
    }
    
    static {
        cosmeticClassLoader = new CosmeticClassLoader();
    }
}
