//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys;

import java.beans.*;

public class EmoteKeyFrame
{
    private long offset;
    private EmotePose[] emotePoses;
    
    public EmoteKeyFrame clone() {
        final EmotePose[] emotePosesCopied = new EmotePose[this.emotePoses.length];
        int t = 0;
        for (final EmotePose emotePose : this.emotePoses) {
            emotePosesCopied[t] = new EmotePose(emotePose.getBodyPart(), emotePose.getX(), emotePose.getY(), emotePose.getZ(), emotePose.isInterpolate());
            ++t;
        }
        return new EmoteKeyFrame(this.offset, emotePosesCopied);
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public EmotePose[] getEmotePoses() {
        return this.emotePoses;
    }
    
    public void setOffset(final long offset) {
        this.offset = offset;
    }
    
    public void setEmotePoses(final EmotePose[] emotePoses) {
        this.emotePoses = emotePoses;
    }
    
    @ConstructorProperties({ "offset", "emotePoses" })
    public EmoteKeyFrame(final long offset, final EmotePose[] emotePoses) {
        this.offset = offset;
        this.emotePoses = emotePoses;
    }
}
