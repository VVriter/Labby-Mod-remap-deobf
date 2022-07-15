//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys.provider;

import java.util.*;
import net.labymod.user.emote.keys.*;

public class StoredEmote extends EmoteProvider
{
    private Iterator<PoseAtTime>[] iterator;
    
    public StoredEmote(final KeyFrameStorage keyFrameStorage) {
        this.iterator = (Iterator<PoseAtTime>[])new Iterator[7];
        final EmoteKeyFrame[] emoteKeyFrames = keyFrameStorage.getKeyframes();
        for (int id = 0; id < 7; ++id) {
            int count = 0;
            for (final EmoteKeyFrame emoteKeyFrame : emoteKeyFrames) {
                for (final EmotePose subPose : emoteKeyFrame.getEmotePoses()) {
                    if (subPose.getBodyPart() == id) {
                        ++count;
                    }
                }
            }
            final PoseAtTime[] posesAtTime = new PoseAtTime[count];
            int index = 0;
            for (final EmoteKeyFrame keyframe : emoteKeyFrames) {
                for (final EmotePose pose : keyframe.getEmotePoses()) {
                    if (pose.getBodyPart() == id) {
                        posesAtTime[index] = new PoseAtTime(pose, keyframe.getOffset(), true);
                        ++index;
                    }
                }
            }
            final int keyFrameCount = count;
            this.iterator[id] = new Iterator<PoseAtTime>() {
                private int currentIndex = 0;
                
                @Override
                public boolean hasNext() {
                    return this.currentIndex < keyFrameCount && posesAtTime[this.currentIndex] != null;
                }
                
                @Override
                public PoseAtTime next() {
                    return posesAtTime[this.currentIndex++];
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
    
    public boolean hasNext(final int bodyPoseId) {
        return this.iterator[bodyPoseId].hasNext();
    }
    
    public PoseAtTime next(final int bodyPoseId) {
        return this.iterator[bodyPoseId].next();
    }
    
    public boolean isWaiting() {
        return false;
    }
    
    public void clear() {
        for (int i = 0; i < this.iterator.length; ++i) {
            while (this.hasNext(i)) {
                this.next(i);
            }
        }
    }
    
    public Iterator<PoseAtTime>[] getIterator() {
        return this.iterator;
    }
}
