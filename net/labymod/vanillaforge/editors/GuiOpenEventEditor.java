//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.minecraftforge.client.event.*;
import net.labymod.vanillaforge.*;
import org.objectweb.asm.*;

public class GuiOpenEventEditor extends ClassEditor
{
    private String name;
    private boolean replacedInit;
    
    public GuiOpenEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public void accept(final String name, final ClassVisitor visitor) {
        super.accept(name, visitor);
        this.name = Type.getInternalName((Class)GuiOpenEvent.class);
    }
    
    public void visitSource(final String s, final String s1) {
        super.visitSource(s, s1);
        this.visitField(2, "gui", "L" + Names.GUI_SCREEN.getName() + ";", (String)null, (Object)null).visitEnd();
        final MethodVisitor mvGetter = this.visitMethod(1, "getGui", "()L" + Names.GUI_SCREEN.getName() + ";", null, null);
        mvGetter.visitVarInsn(25, 0);
        mvGetter.visitFieldInsn(180, "net/minecraftforge/client/event/GuiOpenEvent", "gui", "L" + Names.GUI_SCREEN.getName() + ";");
        mvGetter.visitInsn(176);
        mvGetter.visitMaxs(0, 0);
        final MethodVisitor mvSetter = this.visitMethod(1, "setGui", "(L" + Names.GUI_SCREEN.getName() + ";)V", null, null);
        mvSetter.visitVarInsn(25, 0);
        mvSetter.visitVarInsn(25, 1);
        mvSetter.visitFieldInsn(181, "net/minecraftforge/client/event/GuiOpenEvent", "gui", "L" + Names.GUI_SCREEN.getName() + ";");
        mvSetter.visitInsn(177);
        mvSetter.visitMaxs(0, 0);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, String desc, final String signature, final String[] exceptions) {
        if (name.equals("<init>") && !this.replacedInit) {
            desc = "(L" + Names.GUI_SCREEN.getName() + ";)V";
            final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int i) {
                    if (i == 177) {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitVarInsn(25, 1);
                        this.mv.visitFieldInsn(181, GuiOpenEventEditor.this.name, "gui", "L" + Names.GUI_SCREEN.getName() + ";");
                    }
                    super.visitInsn(i);
                }
                
                public void visitEnd() {
                    super.visitEnd();
                    GuiOpenEventEditor.this.replacedInit = true;
                    final MethodVisitor mv = GuiOpenEventEditor.this.visitMethod(1, "<init>", "()V", null, null);
                    mv.visitCode();
                    final Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitLineNumber(13, l0);
                    mv.visitVarInsn(25, 0);
                    mv.visitMethodInsn(183, "net/minecraftforge/fml/common/eventhandler/Event", "<init>", "()V", false);
                    final Label l2 = new Label();
                    mv.visitLabel(l2);
                    mv.visitLineNumber(15, l2);
                    mv.visitInsn(177);
                    final Label l3 = new Label();
                    mv.visitLabel(l3);
                    mv.visitLocalVariable("this", "Lnet/minecraftforge/client/event/GuiOpenEvent;", (String)null, l0, l3, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
            };
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
