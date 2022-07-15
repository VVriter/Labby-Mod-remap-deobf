//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import java.util.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.support.util.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public class GeometryLayer extends GeometryEffect
{
    private UUID uuid;
    private boolean negate;
    private boolean filterSlim;
    private boolean filterRightSide;
    
    public GeometryLayer(final String name, final GeometryModelRenderer model) {
        super(name, model);
        this.uuid = null;
        this.negate = false;
        this.filterSlim = false;
        this.filterRightSide = false;
    }
    
    @Override
    protected boolean parse() {
        final String id = this.getParameter(0);
        if (id.equals("slim")) {
            this.filterSlim = true;
        }
        else if (id.equals("right")) {
            this.filterRightSide = true;
        }
        else {
            try {
                this.uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
            }
            catch (Exception e) {
                Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Invalid layer UUID: " + id);
            }
        }
        this.negate = (this.hasParameter(1) && this.getParameter(1).equals("negate"));
        return true;
    }
    
    @Override
    protected int getParametersAmount() {
        return 1;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        if (this.uuid != null) {
            this.model.showModel = (remoteData.textureUUID != null && this.negate != remoteData.textureUUID.equals(this.uuid));
        }
        if (this.filterSlim) {
            this.model.showModel = (this.negate != meta.isSlim);
        }
        if (this.filterRightSide) {
            this.model.showModel = (this.negate != meta.rightSide);
        }
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public boolean isNegate() {
        return this.negate;
    }
    
    public boolean isFilterSlim() {
        return this.filterSlim;
    }
    
    public boolean isFilterRightSide() {
        return this.filterRightSide;
    }
}
