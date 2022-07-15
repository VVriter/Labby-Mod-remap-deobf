//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_112;

import net.labymod.core.asm.global.*;
import org.objectweb.asm.*;

public class GuiBossOverlayVisitor extends ClassEditor
{
    public GuiBossOverlayVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean nextIsRender = false;
                private int lastALoadVar = -1;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    if (opcode == 178) {
                        this.nextIsRender = true;
                    }
                }
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (opcode == 25 && this.nextIsRender) {
                        this.lastALoadVar = var;
                    }
                    super.visitVarInsn(opcode, var);
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (this.nextIsRender && this.lastALoadVar != -1 && opcode == 183) {
                        super.visitMethodInsn(184, "net/labymod/core_implementation/mc112/util/BossbarUtil", "shouldRenderBar", "(ILjava/lang/Object;)I", this.nextIsRender = false);
                        super.visitVarInsn(25, this.lastALoadVar);
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        return mv;
    }
}
