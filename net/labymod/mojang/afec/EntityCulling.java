//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.afec;

import java.lang.reflect.*;
import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import net.labymod.support.util.*;

public class EntityCulling
{
    public static final boolean OPEN_GL_33_SUPPORTED;
    public static final String CREDIT_LINE;
    public static boolean shaderPackLoaded;
    private static Field shaderField;
    private static boolean shaderModFound;
    
    public static boolean alternativeShouldRender(final vg entity, final double x, final double y, final double z) {
        final ModSettings settings = LabyMod.getSettings();
        final boolean isPlayer = entity instanceof aed;
        if (!EntityCulling.OPEN_GL_33_SUPPORTED || entity == bib.z().aa() || (isPlayer && !settings.afecPlayers) || EntityCulling.shaderPackLoaded) {
            return true;
        }
        EnumQueryState state = EntityAFECFields.getState(entity);
        boolean visible = EntityAFECFields.getVisible(entity);
        boolean nameTag = !visible && EntityAFECFields.getNameTag(entity);
        if (state == null) {
            state = EnumQueryState.WAIT_FOR_ID;
        }
        if (state == EnumQueryState.DESTROYED) {
            return true;
        }
        int id;
        long timeoutTime;
        if (state == EnumQueryState.WAIT_FOR_QUERY || state == EnumQueryState.WAIT_FOR_ID) {
            id = ((state == EnumQueryState.WAIT_FOR_ID) ? GL15.glGenQueries() : EntityAFECFields.getId(entity));
            double targetX;
            double targetY;
            double targetZ;
            if (settings.afecMovementPrediction) {
                final float partialTicks = LabyMod.getInstance().getPartialTicks();
                final double renderPosX = (x - (entity.M + (entity.p - entity.M) * partialTicks)) * -1.0;
                final double renderPosY = (y - (entity.N + (entity.q - entity.N) * partialTicks)) * -1.0;
                final double renderPosZ = (z - (entity.O + (entity.r - entity.O) * partialTicks)) * -1.0;
                targetX = entity.p - (entity.p - entity.M) - renderPosX;
                targetY = entity.q - (entity.q - entity.N) - renderPosY;
                targetZ = entity.r - (entity.r - entity.O) - renderPosZ;
            }
            else {
                targetX = x;
                targetY = y;
                targetZ = z;
            }
            GL15.glBeginQuery(35887, id);
            drawCameraTarget(entity, targetX, targetY, targetZ);
            GL15.glEndQuery(35887);
            EntityAFECFields.setState(entity, state = EnumQueryState.WAIT_FOR_RESULT);
            EntityAFECFields.setId(entity, id);
            EntityAFECFields.setTimeoutTime(entity, timeoutTime = System.currentTimeMillis());
        }
        else {
            id = EntityAFECFields.getId(entity);
            timeoutTime = EntityAFECFields.getTimeoutTime(entity);
        }
        if (state == EnumQueryState.WAIT_FOR_RESULT) {
            final boolean available = GL15.glGetQueryObjecti(id, 34919) == 1;
            if (available) {
                visible = (GL15.glGetQueryObjecti(id, 34918) == 1);
                long duration;
                if (visible) {
                    duration = 1000L;
                }
                else {
                    final double distance = Math.pow(x - entity.p, 2.0) + Math.pow(y - entity.q, 2.0) + Math.pow(z - entity.r, 2.0);
                    final int targetInterval = isPlayer ? settings.afecPlayerInterval : settings.afecEntityInterval;
                    duration = (settings.afecDistanceDetection ? ((distance > Math.pow(35.0, 2.0)) ? 500L : targetInterval) : targetInterval);
                    EntityAFECFields.setNameTag(entity, nameTag = (distance < Math.pow(64.0, 2.0)));
                }
                EntityAFECFields.setState(entity, state = EnumQueryState.QUERY_RESULT_AVAILABLE);
                EntityAFECFields.setVisible(entity, visible);
                EntityAFECFields.setTimeoutTime(entity, timeoutTime = System.currentTimeMillis() + duration);
            }
            else if (System.currentTimeMillis() > timeoutTime) {
                EntityAFECFields.setState(entity, EnumQueryState.WAIT_FOR_ID);
                GL15.glDeleteQueries(id);
            }
        }
        if (state == EnumQueryState.QUERY_RESULT_AVAILABLE && System.currentTimeMillis() > timeoutTime) {
            EntityAFECFields.setState(entity, EnumQueryState.WAIT_FOR_QUERY);
        }
        if (!entity.aU() && !visible && nameTag) {
            final RenderAdapter implementation = LabyModCore.getRenderImplementation();
            if (isPlayer) {
                if (settings.afecHidePlayerNames || !implementation.canRenderName(entity)) {
                    return visible;
                }
            }
            else if (settings.afecHideEntityNames || !entity.br()) {
                return visible;
            }
            final String displayName = entity.i_().d();
            final float partialTicks2 = LabyMod.getInstance().getPartialTicks();
            final double posX = entity.m + (entity.p - entity.m) * partialTicks2;
            final double posY = entity.n + (entity.q - entity.n) * partialTicks2;
            final double posZ = entity.o + (entity.r - entity.o) * partialTicks2;
            implementation.renderLivingLabel(entity, displayName, -x + posX, -y + posY, -z + posZ);
        }
        return visible;
    }
    
    private static void drawCameraTarget(final vg entity, final double x, final double y, final double z) {
        bus.G();
        bus.a(false, false, false, false);
        bus.a(false);
        bus.d();
        bus.r();
        GL11.glTranslated(-x, -y, -z);
        final double minX = entity.bw().a;
        final double minY = entity.bw().b;
        final double minZ = entity.bw().c;
        final double maxX = entity.bw().d;
        double maxY = entity.bw().e;
        final double maxZ = entity.bw().f;
        if (entity instanceof abz) {
            maxY += 2.0;
        }
        GL11.glBegin(1);
        GL11.glVertex3d(minX, minY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ);
        GL11.glVertex3d(minX, maxY, minZ);
        GL11.glEnd();
        bus.e();
        bus.q();
        bus.a(true);
        bus.a(true, true, true, true);
        bus.H();
    }
    
    public static void onEntityUnload(final vg entity) {
        final EnumQueryState state = EntityAFECFields.getState(entity);
        final int id = EntityAFECFields.getId(entity);
        if (state != null) {
            EntityAFECFields.setState(entity, EnumQueryState.DESTROYED);
            if (bib.z().aF()) {
                GL15.glDeleteQueries(id);
            }
            else {
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        GL15.glDeleteQueries(id);
                    }
                });
            }
        }
    }
    
    public static void updateShadersModValue() {
        if (EntityCulling.shaderModFound) {
            try {
                EntityCulling.shaderPackLoaded = (boolean)EntityCulling.shaderField.get(null);
            }
            catch (Throwable e) {
                EntityCulling.shaderPackLoaded = false;
            }
        }
        else {
            EntityCulling.shaderPackLoaded = false;
        }
    }
    
    static {
        OPEN_GL_33_SUPPORTED = GLContext.getCapabilities().OpenGL33;
        CREDIT_LINE = "\n\n" + ModColor.cl("e") + ModColor.cl("l") + "Inspired by Sk1er\n" + ModColor.cl('7') + "https://sk1er.club/";
        EntityCulling.shaderField = null;
        EntityCulling.shaderModFound = false;
        try {
            final Class<?> clazz = Class.forName("net.optifine.shaders.Shaders");
            (EntityCulling.shaderField = clazz.getDeclaredField("shaderPackLoaded")).setAccessible(true);
            EntityCulling.shaderModFound = true;
            updateShadersModValue();
            Debug.log(Debug.EnumDebugMode.GENERAL, "Found shaders mod value: " + EntityCulling.shaderPackLoaded);
        }
        catch (Throwable e) {
            Debug.log(Debug.EnumDebugMode.GENERAL, "No shaders mod installed");
        }
    }
}
