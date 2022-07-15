//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.layer;

import net.labymod.user.cosmetic.geometry.render.*;
import java.util.*;
import java.io.*;
import net.labymod.user.cosmetic.geometry.*;
import net.labymod.main.*;

public class CrownLayer implements ccg<aad>
{
    private final brs headModel;
    private GeometryModelRenderer model;
    
    public CrownLayer(final cak renderer) {
        final bqm quad = (bqm)renderer.b();
        this.headModel = quad.a;
        try {
            final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/minecraft/labymod/models/crown.geo.json");
            if (inputStream != null) {
                final DataInputStream dis = new DataInputStream(inputStream);
                final StringBuilder json = new StringBuilder();
                final Scanner scanner = new Scanner(dis, "UTF-8");
                while (scanner.hasNext()) {
                    json.append(scanner.next());
                }
                scanner.close();
                final GeometryLoader loader = new GeometryLoader(json.toString());
                this.model = loader.toModelRenderer(renderer.b());
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void doRenderLayer(final aad entity, final float arg1, final float arg2, final float arg3, final float arg4, final float arg5, final float arg6, final float scale) {
        if (this.model == null || this.headModel == null || entity.l_() || !entity.n_() || !"Technoblade".equals(entity.bq())) {
            return;
        }
        this.model.rotationPointX = this.headModel.c;
        this.model.rotationPointY = this.headModel.d;
        this.model.rotationPointZ = this.headModel.e;
        this.model.rotateAngleX = this.headModel.f;
        this.model.rotateAngleY = this.headModel.g;
        this.model.rotateAngleZ = this.headModel.h;
        bib.z().N().a(ModTextures.LAYER_CROWN);
        this.model.render(scale);
    }
    
    public boolean a() {
        return true;
    }
}
