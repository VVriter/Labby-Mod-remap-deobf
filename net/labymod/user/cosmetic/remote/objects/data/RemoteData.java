//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.objects.data;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.remote.model.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import net.labymod.main.*;
import java.util.*;

public class RemoteData extends CosmeticData
{
    private RemoteObject object;
    public OffsetVector offset;
    public boolean rightSide;
    public UUID textureUUID;
    public Color[] colors;
    public DepthMap depthMap;
    public boolean depthMapChangedInThisFrame;
    public int depthMapCode;
    
    public RemoteData() {
        this.colors = new Color[0];
        this.depthMapChangedInThisFrame = false;
        this.depthMapCode = -1;
    }
    
    @Override
    public void init(final int id, final User user) {
        this.object = LabyMod.getInstance().getUserManager().getRemoteCosmeticLoader().getObject(id);
    }
    
    @Override
    public void loadData(final String[] data) throws Exception {
        for (int min = Math.min(this.object.options.length, data.length), i = 0; i < min; ++i) {
            final String key = this.object.options[i];
            final String value = data[i];
            final String s = key;
            switch (s) {
                case "offset": {
                    final String[] vectorString = value.split(";");
                    this.offset = new OffsetVector(Double.parseDouble(vectorString[0]), Double.parseDouble(vectorString[1]), Double.parseDouble(vectorString[2]));
                    break;
                }
                case "shoulder_side":
                case "side": {
                    this.rightSide = (Integer.parseInt(value) == 1);
                    break;
                }
                case "texture":
                case "mojang_uuid": {
                    this.textureUUID = UUID.fromString(value);
                    break;
                }
                case "rgb": {
                    final int n = this.colors.length;
                    (this.colors = Arrays.copyOf(this.colors, n + 1))[n] = Color.decode("#" + value);
                    break;
                }
            }
        }
    }
    
    public void updateDepthMap(final DepthMap depthMap) {
        if (this.depthMap != depthMap) {
            this.depthMapChangedInThisFrame = true;
        }
        this.depthMap = depthMap;
    }
    
    @Override
    public boolean isDraft() {
        return this.object.draft;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public static class OffsetVector
    {
        public double x;
        public double y;
        public double z;
        
        public OffsetVector(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
