//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.shoes;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticShoes extends CosmeticRenderer<CosmeticShoesData>
{
    public static final int ID = 27;
    private brs shoeRight;
    private brs shoeLeft;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 27;
        final int height = 18;
        (this.shoeRight = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-2.6f, 9.0f, -2.5f, 5, 4, 5, modelSize);
        this.shoeRight.a(-1.9f, 12.0f, 0.0f);
        brs shoeFront = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(15, 0);
        shoeFront.a(-2.6f, 9.0f, -2.5f, 5, 3, 1, modelSize);
        shoeFront.a(0.0f, 1.0f, -1.0f);
        this.shoeRight.a(shoeFront);
        (this.shoeLeft = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 9)).a(-2.4f, 9.0f, -2.5f, 5, 4, 5, modelSize);
        this.shoeLeft.a(1.9f, 12.001f, 0.001f);
        shoeFront = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(15, 9);
        shoeFront.a(-2.4f, 9.0f, -2.5f, 5, 3, 1, modelSize);
        shoeFront.a(0.0f, 1.0f, -1.0f);
        this.shoeLeft.a(shoeFront);
    }
    
    public void setInvisible(final boolean invisible) {
        this.shoeRight.j = invisible;
        this.shoeLeft.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticShoesData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf location = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getShoesImageHandler().getResourceLocation((bua)entityIn);
        if (location == null) {
            return;
        }
        bus.G();
        bib.z().N().a(location);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bpx.a(modelCosmetics.j, this.shoeRight);
        bpx.a(modelCosmetics.k, this.shoeLeft);
        this.shoeRight.a(scale);
        this.shoeLeft.a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 27;
    }
    
    public String getCosmeticName() {
        return "Shoes";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticShoesData extends CosmeticData
    {
        private UserTextureContainer userTextureContainer;
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getShoesContainer();
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.userTextureContainer.setFileName(UUID.fromString(data[0]));
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.LEG;
        }
    }
}
