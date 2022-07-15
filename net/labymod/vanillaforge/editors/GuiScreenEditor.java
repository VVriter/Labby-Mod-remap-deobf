//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import org.objectweb.asm.*;

public class GuiScreenEditor extends ClassEditor
{
    public GuiScreenEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(int access, final String name, final String desc, final String signature, final String[] exceptions) {
        if (access == 4 || access == 2) {
            access = 1;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
    
    public FieldVisitor visitField(int i, final String s, final String s1, final String s2, final Object o) {
        if (i == 2 || i == 4) {
            i = 1;
        }
        if (i == 20 || i == 18) {
            i = 17;
        }
        return super.visitField(i, s, s1, s2, o);
    }
}
