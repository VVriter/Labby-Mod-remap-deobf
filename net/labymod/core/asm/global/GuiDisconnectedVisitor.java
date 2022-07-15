//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GuiDisconnectedVisitor extends ClassEditor
{
    private String initGuiName;
    private String messageName;
    private String iChatComponentName;
    private String guiDisconnectedName;
    private String getUnformattedTextForChatName;
    private String parentScreenName;
    private String guiScreenName;
    
    public GuiDisconnectedVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.initGuiName = LabyModTransformer.getMappingImplementation().getInitGuiName();
        this.messageName = LabyModTransformer.getMappingImplementation().getGuiDisconnectedMessageName();
        this.iChatComponentName = LabyModTransformer.getMappingImplementation().getChatComponentClassName();
        this.guiDisconnectedName = LabyModTransformer.getMappingImplementation().getGuiDisconnectedName();
        this.getUnformattedTextForChatName = LabyModTransformer.getMappingImplementation().getGetUnformattedTextForChatName();
        this.parentScreenName = LabyModTransformer.getMappingImplementation().getParentScreenName();
        this.guiScreenName = LabyModTransformer.getMappingImplementation().getGuiScreenName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.initGuiName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, GuiDisconnectedVisitor.this.guiDisconnectedName, GuiDisconnectedVisitor.this.messageName, "L" + GuiDisconnectedVisitor.this.iChatComponentName + ";");
                        this.mv.visitMethodInsn(185, GuiDisconnectedVisitor.this.iChatComponentName, GuiDisconnectedVisitor.this.getUnformattedTextForChatName, "()Ljava/lang/String;", true);
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, GuiDisconnectedVisitor.this.guiDisconnectedName, GuiDisconnectedVisitor.this.parentScreenName, "L" + GuiDisconnectedVisitor.this.guiScreenName + ";");
                        this.mv.visitMethodInsn(184, "net/labymod/gui/GuiWebPanel", "open", "(Ljava/lang/String;L" + GuiDisconnectedVisitor.this.guiScreenName + ";)V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
