//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;

public class ScaledResolutionVisitor extends ClassEditor
{
    public ScaledResolutionVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("<init>")) {
            return new MethodVisitor(262144, mv) {
                boolean flag = true;
                
                public void visitInsn(final int opcode) {
                    if (opcode == 135) {
                        return;
                    }
                    if (opcode == 111) {
                        super.visitInsn(this.flag ? 4 : 3);
                        super.visitMethodInsn(184, "BytecodeMethods", "getCustomScale", "(IIZ)D", false);
                        this.flag = false;
                        return;
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
