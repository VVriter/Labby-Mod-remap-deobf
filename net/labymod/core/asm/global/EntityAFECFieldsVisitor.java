//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class EntityAFECFieldsVisitor extends ClassEditor
{
    private String entityName;
    
    public EntityAFECFieldsVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.entityName = LabyModTransformer.getMappingImplementation().getEntityClassName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("getState")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 1) {
                        super.visitVarInsn(25, 0);
                        super.visitFieldInsn(180, EntityAFECFieldsVisitor.this.entityName, "afecState", "Lnet/labymod/mojang/afec/EnumQueryState;");
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        if (name.equals("getTimeoutTime")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 9) {
                        super.visitVarInsn(25, 0);
                        super.visitFieldInsn(180, EntityAFECFieldsVisitor.this.entityName, "afecTimeoutTime", "J");
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        if (name.equals("getId")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 3) {
                        super.visitVarInsn(25, 0);
                        super.visitFieldInsn(180, EntityAFECFieldsVisitor.this.entityName, "afecId", "I");
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        if (name.equals("getVisible")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 4) {
                        super.visitVarInsn(25, 0);
                        super.visitFieldInsn(180, EntityAFECFieldsVisitor.this.entityName, "afecVisible", "Z");
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        if (name.equals("getNameTag")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 4) {
                        super.visitVarInsn(25, 0);
                        super.visitFieldInsn(180, EntityAFECFieldsVisitor.this.entityName, "afecNameTag", "Z");
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        if (name.equals("setState")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(25, 1);
                        super.visitFieldInsn(181, EntityAFECFieldsVisitor.this.entityName, "afecState", "Lnet/labymod/mojang/afec/EnumQueryState;");
                    }
                    super.visitInsn(177);
                }
            };
        }
        if (name.equals("setTimeoutTime")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(22, 1);
                        super.visitFieldInsn(181, EntityAFECFieldsVisitor.this.entityName, "afecTimeoutTime", "J");
                    }
                    super.visitInsn(177);
                }
            };
        }
        if (name.equals("setId")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(21, 1);
                        super.visitFieldInsn(181, EntityAFECFieldsVisitor.this.entityName, "afecId", "I");
                    }
                    super.visitInsn(177);
                }
            };
        }
        if (name.equals("setVisible")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(21, 1);
                        super.visitFieldInsn(181, EntityAFECFieldsVisitor.this.entityName, "afecVisible", "Z");
                    }
                    super.visitInsn(177);
                }
            };
        }
        if (name.equals("setNameTag")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(21, 1);
                        super.visitFieldInsn(181, EntityAFECFieldsVisitor.this.entityName, "afecNameTag", "Z");
                    }
                    super.visitInsn(177);
                }
            };
        }
        return mv;
    }
}
