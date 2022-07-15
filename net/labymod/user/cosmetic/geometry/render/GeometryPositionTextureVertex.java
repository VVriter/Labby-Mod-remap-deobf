//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

public class GeometryPositionTextureVertex
{
    public GeometryVector3 vector3D;
    public float texturePositionX;
    public float texturePositionY;
    
    public GeometryPositionTextureVertex(final float p_i1158_1_, final float p_i1158_2_, final float p_i1158_3_, final float p_i1158_4_, final float p_i1158_5_) {
        this(new GeometryVector3(p_i1158_1_, p_i1158_2_, p_i1158_3_), p_i1158_4_, p_i1158_5_);
    }
    
    public GeometryPositionTextureVertex setTexturePosition(final float p_78240_1_, final float p_78240_2_) {
        return new GeometryPositionTextureVertex(this, p_78240_1_, p_78240_2_);
    }
    
    public GeometryPositionTextureVertex(final GeometryPositionTextureVertex textureVertex, final float texturePositionXIn, final float texturePositionYIn) {
        this.vector3D = textureVertex.vector3D;
        this.texturePositionX = texturePositionXIn;
        this.texturePositionY = texturePositionYIn;
    }
    
    public GeometryPositionTextureVertex(final GeometryVector3 vector3DIn, final float texturePositionXIn, final float texturePositionYIn) {
        this.vector3D = vector3DIn;
        this.texturePositionX = texturePositionXIn;
        this.texturePositionY = texturePositionYIn;
    }
}
