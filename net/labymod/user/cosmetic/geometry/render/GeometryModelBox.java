//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

public class GeometryModelBox
{
    private GeometryPositionTextureVertex[] vertexPositions;
    public GeometryTexturedQuad[] quadList;
    public final float posX1;
    public final float posY1;
    public final float posZ1;
    public final float posX2;
    public final float posY2;
    public final float posZ2;
    public String boxName;
    
    public GeometryModelBox(final int textureX, final int textureY, float x, float y, float z, final float width, final float height, final float depth, final float delta, final boolean mirror, final float texWidth, final float texHeight) {
        this.posX1 = x;
        this.posY1 = y;
        this.posZ1 = z;
        this.posX2 = x + width;
        this.posY2 = y + height;
        this.posZ2 = z + depth;
        this.vertexPositions = new GeometryPositionTextureVertex[8];
        this.quadList = new GeometryTexturedQuad[6];
        float f = x + width;
        float f2 = y + height;
        float f3 = z + depth;
        x -= delta;
        y -= delta;
        z -= delta;
        f += delta;
        f2 += delta;
        f3 += delta;
        if (mirror) {
            final float f4 = f;
            f = x;
            x = f4;
        }
        final GeometryPositionTextureVertex positiontexturevertex7 = new GeometryPositionTextureVertex(x, y, z, 0.0f, 0.0f);
        final GeometryPositionTextureVertex positiontexturevertex8 = new GeometryPositionTextureVertex(f, y, z, 0.0f, 8.0f);
        final GeometryPositionTextureVertex positiontexturevertex9 = new GeometryPositionTextureVertex(f, f2, z, 8.0f, 8.0f);
        final GeometryPositionTextureVertex positiontexturevertex10 = new GeometryPositionTextureVertex(x, f2, z, 8.0f, 0.0f);
        final GeometryPositionTextureVertex positiontexturevertex11 = new GeometryPositionTextureVertex(x, y, f3, 0.0f, 0.0f);
        final GeometryPositionTextureVertex positiontexturevertex12 = new GeometryPositionTextureVertex(f, y, f3, 0.0f, 8.0f);
        final GeometryPositionTextureVertex positiontexturevertex13 = new GeometryPositionTextureVertex(f, f2, f3, 8.0f, 8.0f);
        final GeometryPositionTextureVertex positiontexturevertex14 = new GeometryPositionTextureVertex(x, f2, f3, 8.0f, 0.0f);
        this.vertexPositions[0] = positiontexturevertex7;
        this.vertexPositions[1] = positiontexturevertex8;
        this.vertexPositions[2] = positiontexturevertex9;
        this.vertexPositions[3] = positiontexturevertex10;
        this.vertexPositions[4] = positiontexturevertex11;
        this.vertexPositions[5] = positiontexturevertex12;
        this.vertexPositions[6] = positiontexturevertex13;
        this.vertexPositions[7] = positiontexturevertex14;
        this.quadList[0] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex12, positiontexturevertex8, positiontexturevertex9, positiontexturevertex13 }, textureX + depth + width, textureY + depth, textureX + depth + width + depth, textureY + depth + height, texWidth, texHeight);
        this.quadList[1] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex7, positiontexturevertex11, positiontexturevertex14, positiontexturevertex10 }, (float)textureX, textureY + depth, textureX + depth, textureY + depth + height, texWidth, texHeight);
        this.quadList[2] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex12, positiontexturevertex11, positiontexturevertex7, positiontexturevertex8 }, textureX + depth, (float)textureY, textureX + depth + width, textureY + depth, texWidth, texHeight);
        this.quadList[3] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex9, positiontexturevertex10, positiontexturevertex14, positiontexturevertex13 }, textureX + depth + width, textureY + depth, textureX + depth + width + width, (float)textureY, texWidth, texHeight);
        this.quadList[4] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex8, positiontexturevertex7, positiontexturevertex10, positiontexturevertex9 }, textureX + depth, textureY + depth, textureX + depth + width, textureY + depth + height, texWidth, texHeight);
        this.quadList[5] = new GeometryTexturedQuad(new GeometryPositionTextureVertex[] { positiontexturevertex11, positiontexturevertex12, positiontexturevertex13, positiontexturevertex14 }, textureX + depth + width + depth, textureY + depth, textureX + depth + width + depth + width, textureY + depth + height, texWidth, texHeight);
        if (mirror) {
            for (int i = 0; i < this.quadList.length; ++i) {
                this.quadList[i].flipFace();
            }
        }
    }
    
    public GeometryModelBox setBoxName(final String name) {
        this.boxName = name;
        return this;
    }
}
