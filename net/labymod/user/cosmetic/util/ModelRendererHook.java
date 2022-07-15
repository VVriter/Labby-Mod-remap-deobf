//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.util;

import net.labymod.utils.*;

public class ModelRendererHook extends brs
{
    private Consumer<ModelRendererHook> hook;
    private float scale;
    
    public ModelRendererHook(final bqf model) {
        super(model);
        model.r.remove(this);
    }
    
    public ModelRendererHook(final bqf model, final String boxNameIn) {
        super(model, boxNameIn);
        model.r.remove(this);
    }
    
    public ModelRendererHook(final bqf model, final int texOffX, final int texOffY) {
        super(model, texOffX, texOffY);
        model.r.remove(this);
    }
    
    public void a(final float scale) {
        if (this.hook == null) {
            super.a(scale);
        }
        else {
            this.scale = scale;
            this.hook.accept(this);
        }
    }
    
    public void renderSuper() {
        super.a(this.scale);
    }
    
    public void setHook(final Consumer<ModelRendererHook> hook) {
        this.hook = hook;
    }
    
    public float getScale() {
        return this.scale;
    }
}
