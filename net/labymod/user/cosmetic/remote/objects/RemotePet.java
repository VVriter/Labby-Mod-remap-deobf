//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.objects;

import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.geometry.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.user.cosmetic.util.*;

public class RemotePet extends GeometryPet<RemoteData>
{
    private final double scale;
    private final RemoteObject data;
    
    public RemotePet(final RemoteObject data, final IGeometryProviderCallback<RemoteData> callback) {
        super(data, (IGeometryProviderCallback)callback);
        this.data = data;
        this.scale = data.scale;
    }
    
    public void renderModel(final float renderScale) {
        bus.G();
        bus.a(this.scale, this.scale, this.scale);
        super.renderModel(renderScale);
        bus.H();
    }
    
    protected PetStorage<RemoteData> getStorage(final RemoteData cosmeticData) {
        return (cosmeticData instanceof CosmeticRemotePetData) ? ((CosmeticRemotePetData)cosmeticData).storage : null;
    }
    
    public static class CosmeticRemotePetData extends RemoteData
    {
        protected PetStorage<RemoteData> storage;
        
        public CosmeticRemotePetData() {
            this.storage = (PetStorage<RemoteData>)new PetStorage();
        }
        
        public void loadData(final String[] data) throws Exception {
            super.loadData(data);
            this.storage.rightShoulder = this.rightSide;
        }
    }
}
