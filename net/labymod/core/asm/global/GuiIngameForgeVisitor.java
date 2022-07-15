//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;

public class GuiIngameForgeVisitor extends ClassEditor
{
    public GuiIngameForgeVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!name.equals("renderCrosshairs") && desc.equals("(F)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean nextIsScoreboard = false;
                
                public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
                    super.visitFrame(type, nLocal, local, nStack, stack);
                    if (type == 4) {
                        this.nextIsScoreboard = true;
                    }
                }
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    super.visitJumpInsn(opcode, label);
                    if (opcode == 198 && this.nextIsScoreboard) {
                        super.visitMethodInsn(184, "BytecodeMethods", "canRenderScoreboard", "()Z", this.nextIsScoreboard = false);
                        super.visitJumpInsn(153, label);
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitMethodInsn(184, "BytecodeMethods", "onRenderIngameOverlay", "()V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        if (name.equals("renderHealth") && desc.equals("(II)V")) {
            return new MethodVisitor(262144, mv) {
                private int varCounter = 0;
                private int highlightVar = -1;
                
                public void visitVarInsn(final int opcode, final int var) {
                    super.visitVarInsn(opcode, var);
                    if (opcode == 21 && this.highlightVar != -1 && var == this.highlightVar) {
                        ++this.varCounter;
                        if (this.varCounter == 2) {
                            super.visitMethodInsn(184, "BytecodeMethods", "shouldRender18HeartAnimation", "(Z)Z", false);
                        }
                    }
                    if (opcode == 54 && this.highlightVar == -1) {
                        ++this.varCounter;
                        if (this.varCounter == 2) {
                            this.highlightVar = var;
                            this.varCounter = 0;
                        }
                    }
                }
            };
        }
        if (name.equals("renderAir") && desc.equals("(II)V")) {
            return new MethodVisitor(262144, mv) {
                private int varCounter = 0;
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (opcode == 54) {
                        ++this.varCounter;
                        if (this.varCounter == 2) {
                            super.visitMethodInsn(184, "BytecodeMethods", "renderSaturationBarAndModifyAirOffset", "(I)I", false);
                        }
                    }
                    super.visitVarInsn(opcode, var);
                }
            };
        }
        if (name.equals("renderCrosshairs")) {
            return new MethodVisitor(262144, mv) {
                private boolean crosshair = true;
                private int count = 0;
                
                public void visitIntInsn(final int opcode, final int operand) {
                    super.visitIntInsn(opcode, operand);
                    if (this.crosshair && opcode == 16 && operand == 16) {
                        ++this.count;
                        if (this.count == 2) {
                            super.visitMethodInsn(184, "BytecodeMethods", "onRenderCrosshair112", "(I)I", this.crosshair = false);
                        }
                    }
                }
            };
        }
        return mv;
    }
}
