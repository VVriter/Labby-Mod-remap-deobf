//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class OldServerPingerVisitor extends ClassEditor
{
    private String serverDataName;
    
    public OldServerPingerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.serverDataName = LabyModTransformer.getMappingImplementation().getServerDataName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (access == 1 && desc.equals("(L" + this.serverDataName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean insertedIfEq;
                private boolean visitedAConstNull;
                private Label label = new Label();
                
                public void visitInsn(final int opcode) {
                    super.visitInsn(opcode);
                    if (opcode == 87 && !this.insertedIfEq) {
                        final Label startLabel = new Label();
                        this.visitVarInsn(25, 1);
                        this.visitFieldInsn(180, OldServerPingerVisitor.this.serverDataName, LabyModTransformer.getMappingImplementation().getPingToServerName(), "J");
                        this.visitLdcInsn((Object)new Long(-2L));
                        this.visitInsn(148);
                        this.visitJumpInsn(153, startLabel);
                        this.visitMethodInsn(184, "BytecodeMethods", "shouldKeepServerData", "()Z", false);
                        this.visitJumpInsn(153, this.label);
                        this.visitLabel(startLabel);
                        this.insertedIfEq = true;
                    }
                    if (this.insertedIfEq && opcode == 1) {
                        this.visitedAConstNull = true;
                    }
                }
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    if (opcode == 181 && this.visitedAConstNull) {
                        this.visitedAConstNull = false;
                        this.visitLabel(this.label);
                    }
                }
            };
        }
        return mv;
    }
}
