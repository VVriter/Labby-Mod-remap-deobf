//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class EntityPlayerSPVisitor extends ClassEditor
{
    private String swingItemName;
    private String onUpdateName;
    
    public EntityPlayerSPVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.swingItemName = (LabyModCoreMod.isObfuscated() ? "bw" : "swingItem");
        this.onUpdateName = (LabyModCoreMod.isObfuscated() ? "t_" : "onUpdate");
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.swingItemName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean foundFirstVarInsn;
                private Label ifLabel = new Label();
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (opcode == 25) {
                        if (this.foundFirstVarInsn) {
                            this.mv.visitMethodInsn(184, "BytecodeMethods", "shouldCancelAnimation", "()Z", false);
                            this.mv.visitJumpInsn(153, this.ifLabel);
                            this.mv.visitInsn(177);
                            this.mv.visitLabel(this.ifLabel);
                        }
                        else {
                            this.foundFirstVarInsn = true;
                        }
                    }
                    this.mv.visitVarInsn(opcode, var);
                }
            };
        }
        if (name.equals(this.onUpdateName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                boolean added = false;
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (!this.added && opcode == 25) {
                        this.added = true;
                        super.visitIntInsn(opcode, var);
                        super.visitMethodInsn(184, "BytecodeMethods", "onUpdateBlockBuild", "()V", false);
                        super.visitIntInsn(opcode, var);
                    }
                    else {
                        super.visitIntInsn(opcode, var);
                    }
                }
            };
        }
        return mv;
    }
}
