//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

public interface RenderAdapter
{
    nf getOptionsBackground();
    
    nf getInventoryBackground();
    
    nf getButtonsTexture();
    
    nf getIcons();
    
    void drawActivePotionEffects(final double p0, final double p1, final nf p2);
    
    void cullFaceBack();
    
    void cullFaceFront();
    
    void renderItemIntoGUI(final aip p0, final double p1, final double p2);
    
    void renderItemOverlayIntoGUI(final aip p0, final double p1, final double p2, final String p3);
    
    void renderEntity(final bzf p0, final vg p1, final double p2, final double p3, final double p4, final float p5, final float p6, final boolean p7);
    
    void renderLivingLabel(final vg p0, final String p1, final double p2, final double p3, final double p4);
    
    void renderLivingLabel(final vg p0, final String p1, final double p2, final double p3, final double p4, final int p5);
    
    boolean canRenderName(final vg p0);
}
