//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.item;

import net.labymod.main.*;
import net.labymod.api.permissions.*;
import net.labymod.api.protocol.liquid.*;
import javax.annotation.*;

public class ItemBucketCustom extends ahi
{
    private final aow containedBlock;
    
    public ItemBucketCustom(final aow containedBlockIn) {
        super(containedBlockIn);
        this.containedBlock = containedBlockIn;
    }
    
    public ue<aip> a(final amu worldIn, final aed playerIn, final ub handIn) {
        final boolean flag = this.containedBlock == aox.a;
        final aip itemstack = playerIn.b(handIn);
        final bhc raytraceresult = this.a(worldIn, playerIn, flag);
        if (raytraceresult == null) {
            return (ue<aip>)new ue(ud.b, (Object)itemstack);
        }
        if (raytraceresult.a != bhc.a.b) {
            return (ue<aip>)new ue(ud.b, (Object)itemstack);
        }
        final boolean allowed = worldIn.G && LabyMod.getSettings().improvedLavaFixedGhostBlocks && Permissions.isAllowed(Permissions.Permission.IMPROVED_LAVA);
        final et blockpos = raytraceresult.a();
        if (!worldIn.a(playerIn, blockpos)) {
            return (ue<aip>)new ue(ud.c, (Object)itemstack);
        }
        if (flag) {
            if (!playerIn.a(blockpos.a(raytraceresult.b), raytraceresult.b, itemstack)) {
                return (ue<aip>)new ue(ud.c, (Object)itemstack);
            }
            final awt iblockstate = worldIn.o(blockpos);
            final bcz material = iblockstate.a();
            if (material == bcz.h && (int)iblockstate.c((axj)aru.b) == 0) {
                playerIn.a(qf.S, 1.0f, 1.0f);
                if (!allowed) {
                    worldIn.a(blockpos, aox.a.t(), 11);
                    playerIn.b(qs.b((ain)this));
                    return (ue<aip>)new ue(ud.a, (Object)this.fillBucket(itemstack, playerIn, air.aA));
                }
                FixedLiquidBucketProtocol.handleBucketAction(FixedLiquidBucketProtocol.Action.FILL_BUCKET, blockpos.p(), blockpos.q(), blockpos.r());
            }
            else {
                if (material != bcz.i || (int)iblockstate.c((axj)aru.b) != 0) {
                    return (ue<aip>)new ue(ud.c, (Object)itemstack);
                }
                playerIn.a(qf.T, 1.0f, 1.0f);
                if (!allowed) {
                    worldIn.a(blockpos, aox.a.t(), 11);
                    playerIn.b(qs.b((ain)this));
                    return (ue<aip>)new ue(ud.a, (Object)this.fillBucket(itemstack, playerIn, air.aB));
                }
                FixedLiquidBucketProtocol.handleBucketAction(FixedLiquidBucketProtocol.Action.FILL_BUCKET, blockpos.p(), blockpos.q(), blockpos.r());
            }
        }
        else {
            final boolean flag2 = worldIn.o(blockpos).u().a((amy)worldIn, blockpos);
            final et blockpos2 = (flag2 && raytraceresult.b == fa.b) ? blockpos : blockpos.a(raytraceresult.b);
            if (!playerIn.a(blockpos2, raytraceresult.b, itemstack)) {
                return (ue<aip>)new ue(ud.c, (Object)itemstack);
            }
            if (this.a(playerIn, worldIn, blockpos2)) {
                if (playerIn instanceof oq) {
                    m.x.a((oq)playerIn, blockpos2, itemstack);
                }
                if (!allowed) {
                    playerIn.b(qs.b((ain)this));
                    return (ue<aip>)(playerIn.bO.d ? new ue(ud.a, (Object)itemstack) : new ue(ud.a, (Object)new aip(air.az)));
                }
            }
        }
        return (ue<aip>)new ue(ud.c, (Object)itemstack);
    }
    
    private aip fillBucket(final aip emptyBuckets, final aed player, final ain fullBucket) {
        if (player.bO.d) {
            return emptyBuckets;
        }
        emptyBuckets.g(1);
        if (emptyBuckets.b()) {
            return new aip(fullBucket);
        }
        if (!player.bv.e(new aip(fullBucket))) {
            player.a(new aip(fullBucket), false);
        }
        return emptyBuckets;
    }
    
    public boolean a(@Nullable final aed player, final amu worldIn, final et posIn) {
        if (this.containedBlock == aox.a) {
            return false;
        }
        final awt iblockstate = worldIn.o(posIn);
        final bcz material = iblockstate.a();
        final boolean flag = !material.a();
        final boolean flag2 = iblockstate.u().a((amy)worldIn, posIn);
        if (!worldIn.d(posIn) && !flag && !flag2) {
            return false;
        }
        if (worldIn.s.l() && this.containedBlock == aox.i) {
            final int l = posIn.p();
            final int i = posIn.q();
            final int j = posIn.r();
            worldIn.a(player, posIn, qf.bN, qg.e, 0.5f, 2.6f + (worldIn.r.nextFloat() - worldIn.r.nextFloat()) * 0.8f);
            for (int k = 0; k < 8; ++k) {
                worldIn.a(fj.m, l + Math.random(), i + Math.random(), j + Math.random(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        else {
            if (!worldIn.G && (flag || flag2) && !material.d()) {
                worldIn.b(posIn, true);
            }
            final qe soundevent = (this.containedBlock == aox.k) ? qf.R : qf.Q;
            worldIn.a(player, posIn, soundevent, qg.e, 1.0f, 1.0f);
            final boolean allowed = worldIn.G && LabyMod.getSettings().improvedLavaFixedGhostBlocks && Permissions.isAllowed(Permissions.Permission.IMPROVED_LAVA);
            if (allowed) {
                FixedLiquidBucketProtocol.handleBucketAction(FixedLiquidBucketProtocol.Action.EMPTY_BUCKET, posIn.p(), posIn.q(), posIn.r());
            }
            else {
                worldIn.a(posIn, this.containedBlock.t(), 11);
            }
        }
        return true;
    }
}
