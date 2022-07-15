//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;

public class RenderPigVisitor extends ClassEditor
{
    public RenderPigVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("<init>")) {
            return new MethodVisitor(262144, mv) {
                private String owner = null;
                private String name = null;
                private String desc = null;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 182) {
                        this.owner = owner;
                        this.name = name;
                        this.desc = desc;
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 177 && this.owner != null) {
                        super.visitVarInsn(25, 0);
                        super.visitTypeInsn(187, "net/labymod/mojang/layer/CrownLayer");
                        super.visitInsn(89);
                        super.visitVarInsn(25, 0);
                        super.visitMethodInsn(183, "net/labymod/mojang/layer/CrownLayer", "<init>", "(L" + this.owner + ";)V", false);
                        super.visitMethodInsn(182, this.owner, this.name, this.desc, false);
                        super.visitInsn(87);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
