//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import org.objectweb.asm.*;
import net.labymod.vanillaforge.*;

public class FOVUpdateEventEditor extends ClassEditor
{
    public FOVUpdateEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, String desc, final String signature, final String[] exceptions) {
        if (name.equals("<init>") && desc.equals("(F)V")) {
            desc = "(L" + Names.ENTITY_PLAYER.getName() + ";F)V";
            return new MethodVisitor(262144, super.visitMethod(access, name, desc, signature, exceptions)) {
                public void visitVarInsn(final int i, int i1) {
                    if (i == 23 && i1 == 1) {
                        i1 = 2;
                    }
                    super.visitVarInsn(i, i1);
                }
                
                public void visitInsn(final int i) {
                    if (i == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(25, 1);
                        this.visitFieldInsn(181, "net/minecraftforge/client/event/FOVUpdateEvent", "entity", "L" + Names.ENTITY_PLAYER.getName() + ";");
                    }
                    super.visitInsn(i);
                }
            };
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
    
    public void visitSource(final String s, final String s1) {
        super.visitSource(s, s1);
        this.visitField(17, "entity", "L" + Names.ENTITY_PLAYER.getName() + ";", (String)null, (Object)null).visitEnd();
        final MethodVisitor mvGetter = this.visitMethod(1, "getFov", "()F", null, null);
        mvGetter.visitVarInsn(25, 0);
        mvGetter.visitFieldInsn(180, "net/minecraftforge/client/event/FOVUpdateEvent", "fov", "F");
        mvGetter.visitInsn(174);
        mvGetter.visitMaxs(0, 0);
        final MethodVisitor mvSetter = this.visitMethod(1, "setNewfov", "(F)V", null, null);
        mvSetter.visitVarInsn(25, 0);
        mvSetter.visitVarInsn(23, 1);
        mvSetter.visitFieldInsn(181, "net/minecraftforge/client/event/FOVUpdateEvent", "newfov", "F");
        mvSetter.visitInsn(177);
        mvSetter.visitMaxs(0, 0);
    }
}
