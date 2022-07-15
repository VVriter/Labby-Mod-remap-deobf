//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.util;

import java.util.*;
import net.labymod.ingamegui.*;
import com.google.common.reflect.*;
import net.labymod.support.util.*;
import net.labymod.user.cosmetic.cosmetics.staff.*;
import net.labymod.user.cosmetic.cosmetics.event.*;
import net.labymod.user.cosmetic.cosmetics.partner.*;
import net.labymod.user.cosmetic.cosmetics.shop.wings.*;
import net.labymod.user.cosmetic.cosmetics.shop.body.*;
import net.labymod.user.cosmetic.cosmetics.shop.shoes.*;
import net.labymod.user.cosmetic.cosmetics.shop.head.*;
import net.labymod.user.cosmetic.cosmetics.shop.pet.*;
import net.labymod.user.cosmetic.cosmetics.shop.head.masks.*;
import com.google.common.collect.*;

public class CosmeticClassLoader
{
    private static final String PACKAGE = "net.labymod.user.cosmetic.cosmetics";
    private List<Class<?>> cosmeticClasses;
    
    public List<Class<?>> getCosmeticClasses() {
        return this.cosmeticClasses;
    }
    
    public CosmeticClassLoader() {
        this.cosmeticClasses = new ArrayList<Class<?>>();
        boolean getTopLevelClassesRecursiveFailed = true;
        try {
            for (final ClassPath.ClassInfo classInfo : ClassPath.from(Module.class.getClassLoader()).getTopLevelClassesRecursive("net.labymod.user.cosmetic.cosmetics")) {
                final Class<?> loadedClassInfo = (Class<?>)classInfo.load();
                this.cosmeticClasses.add(loadedClassInfo);
                getTopLevelClassesRecursiveFailed = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            getTopLevelClassesRecursiveFailed = true;
        }
        try {
            if (getTopLevelClassesRecursiveFailed) {
                Debug.log(Debug.EnumDebugMode.GENERAL, "getTopLevelClassesRecursive failed! Adding backup classes..");
                for (final Class<?> cosmeticClass : new Class[] { CosmeticWingsAngel.class, CosmeticBandana.class, CosmeticCap.class, CosmeticCatTail.class, CosmeticCloak.class, CosmeticMerchCrown.class, CosmeticFlower.class, CosmeticHalloween.class, CosmeticHalo.class, CosmeticHeadset.class, CosmeticDevilHorn.class, CosmeticMoehritz.class, CosmeticRabbit.class, CosmeticShoes.class, CosmeticSnoxh.class, CosmeticTool.class, CosmeticWingsDragon.class, CosmeticWitchHat.class, CosmeticWolfTail.class, CosmeticXmasHat.class, CosmeticRednose.class, CosmeticAntlers.class, CosmeticBeard.class, CosmeticWingsCrystal.class, CosmeticWingsSteampunk.class, CosmeticBackPack.class, CosmeticRoyalCrown.class, CosmeticAbgegrieft.class, CosmeticReved.class, CosmeticMaskKawaii.class, CosmeticWingsButterfly.class, CosmeticWatch.class, CosmeticEyelids.class, CosmeticMooseHat.class, CosmeticUnicornHat.class, CosmeticScarf.class, CosmeticCowHat.class, CosmeticBunnyShoes.class, CosmeticMustache.class, CosmeticPetDragon.class, CosmeticMaskCover.class }) {
                    this.cosmeticClasses.add(cosmeticClass);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
