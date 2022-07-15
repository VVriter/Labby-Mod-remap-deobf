//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

import net.labymod.core.*;

public class GeometryTexturedQuad
{
    public GeometryPositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    
    public GeometryTexturedQuad(final GeometryPositionTextureVertex[] vertices) {
        this.vertexPositions = vertices;
        this.nVertices = vertices.length;
    }
    
    public GeometryTexturedQuad(final GeometryPositionTextureVertex[] vertices, final float texcoordU1, final float texcoordV1, final float texcoordU2, final float texcoordV2, final float textureWidth, final float textureHeight) {
        this(vertices);
        final float f = 0.0f / textureWidth;
        final float f2 = 0.0f / textureHeight;
        vertices[0] = vertices[0].setTexturePosition(texcoordU2 / textureWidth - f, texcoordV1 / textureHeight + f2);
        vertices[1] = vertices[1].setTexturePosition(texcoordU1 / textureWidth + f, texcoordV1 / textureHeight + f2);
        vertices[2] = vertices[2].setTexturePosition(texcoordU1 / textureWidth + f, texcoordV2 / textureHeight - f2);
        vertices[3] = vertices[3].setTexturePosition(texcoordU2 / textureWidth - f, texcoordV2 / textureHeight - f2);
    }
    
    public void flipFace() {
        final GeometryPositionTextureVertex[] apositiontexturevertex = new GeometryPositionTextureVertex[this.vertexPositions.length];
        for (int i = 0; i < this.vertexPositions.length; ++i) {
            apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }
        this.vertexPositions = apositiontexturevertex;
    }
    
    public void draw(final WorldRendererAdapter renderer, final float scale) {
        final GeometryVector3 vec3 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D);
        final GeometryVector3 vec4 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D);
        final GeometryVector3 vec5 = vec4.crossProduct(vec3).normalize();
        float f = (float)vec5.xCoord;
        float f2 = (float)vec5.yCoord;
        float f3 = (float)vec5.zCoord;
        if (this.invertNormal) {
            f = -f;
            f2 = -f2;
            f3 = -f3;
        }
        renderer.begin(7, cdy.c);
        for (int i = 0; i < 4; ++i) {
            final GeometryPositionTextureVertex positiontexturevertex = this.vertexPositions[i];
            renderer.pos(positiontexturevertex.vector3D.xCoord * scale, positiontexturevertex.vector3D.yCoord * scale, positiontexturevertex.vector3D.zCoord * scale).tex((double)positiontexturevertex.texturePositionX, (double)positiontexturevertex.texturePositionY).normal(f, f2, f3).endVertex();
        }
        bve.a().b();
    }
}
