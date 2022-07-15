//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.objects;

import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.geometry.*;

public class RemoteCosmetic extends GeometryCosmetic<RemoteData>
{
    private final double scale;
    private final RemoteObject data;
    
    public RemoteCosmetic(final RemoteObject data, final IGeometryProviderCallback<RemoteData> callback) {
        super(data, (IGeometryProviderCallback)callback);
        this.data = data;
        this.scale = data.scale;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final RemoteData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks, final boolean firstPersonContext) {
        if (this.data.hideCape) {
            final UserManager userManager = LabyMod.getInstance().getUserManager();
            final User user = userManager.getUser(entityIn.bm());
            final boolean mojangCapeVisible = user.canRenderMojangCape((bua)entityIn);
            final boolean wearingCape = ((bua)entityIn).a(aee.a);
            if (mojangCapeVisible || !wearingCape || LabyModCore.getMinecraft().isWearingElytra(entityIn)) {
                return;
            }
        }
        bus.G();
        bus.a(this.scale, this.scale, this.scale);
        try {
            super.render(modelCosmetics, entityIn, (CosmeticData)cosmeticData, scale, movementFactor, walkingSpeed, tickValue, firstRotationX, secondRotationX, partialTicks, firstPersonContext);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        bus.H();
    }
    
    protected AnimationStorage getAnimationStorage(final RemoteData cosmeticData) {
        return (cosmeticData instanceof CosmeticRemoteCosmeticData) ? ((CosmeticRemoteCosmeticData)cosmeticData).storage : null;
    }
    
    public static class CosmeticRemoteCosmeticData extends RemoteData
    {
        public AnimationStorage storage;
        
        public CosmeticRemoteCosmeticData() {
            this.storage = new AnimationStorage();
        }
    }
}
