//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import org.objectweb.asm.*;

public class RenderManagerEditor extends ClassEditor
{
    public RenderManagerEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public FieldVisitor visitField(int i, final String s, final String s1, final String s2, final Object o) {
        if (i == 2) {
            i = 1;
        }
        return super.visitField(i, s, s1, s2, o);
    }
}
