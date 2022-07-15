//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GuiMainMenuVisitor extends ClassEditor
{
    private final String drawScreenName;
    private final String mouseClickedName;
    private final String guiMainMenuName;
    private final String splashTextName;
    private final String realmsButtonName;
    private final String guiButtonName;
    private final String switchToRealmsName;
    
    public GuiMainMenuVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.drawScreenName = LabyModTransformer.getMappingImplementation().getGuiScreenDrawScreenName();
        this.mouseClickedName = LabyModTransformer.getMappingImplementation().getGuiScreenMouseClickedName();
        this.guiMainMenuName = LabyModTransformer.getMappingImplementation().getGuiMainMenuName();
        this.splashTextName = LabyModTransformer.getMappingImplementation().getSplashTextName();
        this.realmsButtonName = LabyModTransformer.getMappingImplementation().getRealmsButtonName();
        this.guiButtonName = LabyModTransformer.getMappingImplementation().getGuiButtonName();
        this.switchToRealmsName = LabyModTransformer.getMappingImplementation().getSwitchToRealmsName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.drawScreenName) && desc.equals("(IIF)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitLdcInsn(final Object cst) {
                    if (cst instanceof String && ((String)cst).startsWith("Copyright")) {
                        this.visitVarInsn(25, 0);
                        this.visitVarInsn(21, 1);
                        this.visitVarInsn(21, 2);
                        this.visitVarInsn(25, 0);
                        this.visitFieldInsn(180, GuiMainMenuVisitor.this.guiMainMenuName, GuiMainMenuVisitor.this.splashTextName, "Ljava/lang/String;");
                        this.visitVarInsn(25, 0);
                        this.visitFieldInsn(180, GuiMainMenuVisitor.this.guiMainMenuName, GuiMainMenuVisitor.this.realmsButtonName, "L" + GuiMainMenuVisitor.this.guiButtonName + ";");
                        this.visitMethodInsn(184, "BytecodeMethods", "onRenderMainMenu", "(IILjava/lang/String;L" + GuiMainMenuVisitor.this.guiButtonName + ";)Ljava/lang/String;", false);
                        this.visitFieldInsn(181, GuiMainMenuVisitor.this.guiMainMenuName, GuiMainMenuVisitor.this.splashTextName, "Ljava/lang/String;");
                    }
                    super.visitLdcInsn(cst);
                }
            };
        }
        if (name.equals(this.mouseClickedName) && desc.equals("(III)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        this.visitVarInsn(25, 0);
                        this.visitVarInsn(21, 1);
                        this.visitVarInsn(21, 2);
                        this.visitVarInsn(21, 3);
                        this.visitMethodInsn(184, "BytecodeMethods", "onMouseClickedMainMenu", "(III)V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        if (name.equals(this.switchToRealmsName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitTypeInsn(final int opcode, final String type) {
                    if (opcode == 187) {
                        final Label label = new Label();
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "onConnectRealms", "()Z", false);
                        this.mv.visitJumpInsn(153, label);
                        this.mv.visitInsn(177);
                        this.mv.visitLabel(label);
                    }
                    super.visitTypeInsn(opcode, type);
                }
            };
        }
        return mv;
    }
}
