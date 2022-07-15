//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GuiMultiplayerVisitor extends ClassEditor
{
    private String drawScreenName;
    
    public GuiMultiplayerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.drawScreenName = LabyModTransformer.getMappingImplementation().getGuiScreenDrawScreenName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.drawScreenName) && desc.equals("(IIF)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitLdcInsn(Object cst) {
                    if (cst instanceof String && String.valueOf(cst).equals("multiplayer.title")) {
                        cst = "";
                    }
                    super.visitLdcInsn(cst);
                }
            };
        }
        return mv;
    }
}
