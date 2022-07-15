//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GuiScreenVisitor extends ClassEditor
{
    private String drawWorldBackgroundName;
    private String drawScreenName;
    
    public GuiScreenVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.drawWorldBackgroundName = LabyModTransformer.getMappingImplementation().getDrawWorldBackgroundName();
        this.drawScreenName = LabyModTransformer.getMappingImplementation().getGuiScreenDrawScreenName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.drawWorldBackgroundName)) {
            return new MethodVisitor(262144, mv) {
                private Label guiBackgroundLabel;
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    this.mv.visitJumpInsn(opcode, label);
                    if (opcode == 198) {
                        this.guiBackgroundLabel = new Label();
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "isGuiBackground", "()Z", false);
                        this.mv.visitJumpInsn(153, this.guiBackgroundLabel);
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 182 && this.guiBackgroundLabel != null) {
                        this.mv.visitLabel(this.guiBackgroundLabel);
                        this.guiBackgroundLabel = null;
                    }
                }
            };
        }
        if (name.equals(this.drawScreenName) && desc.equals("(IIF)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        this.mv.visitIntInsn(21, 1);
                        this.mv.visitIntInsn(21, 2);
                        this.mv.visitIntInsn(23, 3);
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "drawMenuOverlay", "(IIF)V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
