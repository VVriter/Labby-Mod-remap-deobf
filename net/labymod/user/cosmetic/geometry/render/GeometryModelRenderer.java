//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

import java.awt.*;
import com.google.common.collect.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;

public class GeometryModelRenderer
{
    public float textureWidth;
    public float textureHeight;
    public int textureOffsetX;
    public int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public boolean compiled;
    private int displayList;
    public boolean mirror;
    public boolean showModel;
    public boolean isHidden;
    public List<GeometryModelBox> cubeList;
    public List<GeometryModelRenderer> childModels;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    public Color color;
    public Extruded extruded;
    public boolean glow;
    public Map<Integer, Integer> extrudedCompileList;
    
    public GeometryModelRenderer() {
        this.textureWidth = 64.0f;
        this.textureHeight = 32.0f;
        this.showModel = true;
        this.cubeList = (List<GeometryModelBox>)Lists.newArrayList();
        this.childModels = (List<GeometryModelRenderer>)Lists.newArrayList();
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.scaleZ = 1.0f;
        this.color = null;
        this.extruded = null;
        this.extrudedCompileList = new HashMap<Integer, Integer>();
    }
    
    public void addChild(final GeometryModelRenderer renderer) {
        if (this.childModels == null) {
            this.childModels = (List<GeometryModelRenderer>)Lists.newArrayList();
        }
        this.childModels.add(renderer);
    }
    
    public GeometryModelRenderer setTextureOffset(final int x, final int y) {
        this.textureOffsetX = x;
        this.textureOffsetY = y;
        return this;
    }
    
    public void addBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        final GeometryModelBox box = new GeometryModelBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, mirror, this.textureWidth, this.textureHeight);
        this.cubeList.add(box);
    }
    
    public void setRotationPoint(final float rotationPointXIn, final float rotationPointYIn, final float rotationPointZIn) {
        this.rotationPointX = rotationPointXIn;
        this.rotationPointY = rotationPointYIn;
        this.rotationPointZ = rotationPointZIn;
    }
    
    public void render(final float scale) {
        if (this.extruded != null) {
            final int code = this.extruded.hashCode();
            if (this.extrudedCompileList.containsKey(code)) {
                this.displayList = this.extrudedCompileList.get(code);
                this.compiled = true;
            }
            else {
                this.compiled = false;
            }
        }
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(scale);
            }
            bus.c(this.offsetX, this.offsetY, this.offsetZ);
            bus.G();
            if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                bus.c(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
            }
            if (this.scaleX != 1.0f || this.scaleY != 1.0f || this.scaleZ != 1.0f) {
                bus.b(this.scaleX, this.scaleY, this.scaleZ);
            }
            if (this.rotateAngleZ != 0.0f) {
                bus.b(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            if (this.rotateAngleY != 0.0f) {
                bus.b(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                bus.b(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.color != null) {
                GL11.glColor4f(this.color.getRed() / 255.0f, this.color.getGreen() / 255.0f, this.color.getBlue() / 255.0f, this.color.getAlpha() / 255.0f);
            }
            if (this.glow) {
                bus.g();
                bib.z().o.h();
            }
            bus.s(this.displayList);
            if (this.childModels != null) {
                for (int i = 0; i < this.childModels.size(); ++i) {
                    this.childModels.get(i).render(scale);
                }
            }
            if (this.color != null) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (this.glow) {
                bus.f();
                bib.z().o.i();
            }
            bus.H();
            bus.c(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }
    
    private void compileDisplayList(final float scale) {
        GL11.glNewList(this.displayList = bia.a(1), 4864);
        if (this.extruded != null) {
            final int code = this.extruded.hashCode();
            this.extrudedCompileList.put(code, this.displayList);
        }
        final WorldRendererAdapter worldRenderer = LabyModCore.getWorldRenderer();
        for (int cubeIndex = 0; cubeIndex < this.cubeList.size(); ++cubeIndex) {
            final GeometryModelBox box = this.cubeList.get(cubeIndex);
            for (int quadIndex = 0; quadIndex < box.quadList.length; ++quadIndex) {
                if (this.extruded == null || this.extruded.isVisible(cubeIndex, quadIndex)) {
                    box.quadList[quadIndex].draw(worldRenderer, scale);
                }
            }
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    public GeometryModelRenderer setTextureSize(final int textureWidthIn, final int textureHeightIn) {
        this.textureWidth = (float)textureWidthIn;
        this.textureHeight = (float)textureHeightIn;
        return this;
    }
    
    public void setScale(final float scaleX, final float scaleY, final float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }
}
