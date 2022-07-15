//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class WorldClientVisitor extends ClassEditor
{
    private String entityName;
    private String spawnEntityInWorldName;
    private String removeEntityName;
    
    public WorldClientVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.entityName = LabyModTransformer.getMappingImplementation().getEntityClassName();
        this.spawnEntityInWorldName = LabyModTransformer.getMappingImplementation().getSpawnEntityInWorldName();
        this.removeEntityName = LabyModTransformer.getMappingImplementation().getRemoveEntityNameName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.spawnEntityInWorldName) && desc.equals("(L" + this.entityName + ";)Z")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 172) {
                        super.visitVarInsn(25, 1);
                        super.visitMethodInsn(184, "BytecodeMethods", "spawnEntityInWorld", "(ZL" + WorldClientVisitor.this.entityName + ";)Z", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        if (name.equals(this.removeEntityName) && desc.equals("(L" + this.entityName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 1);
                        super.visitMethodInsn(184, "BytecodeMethods", "removeEntity", "(L" + WorldClientVisitor.this.entityName + ";)V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
