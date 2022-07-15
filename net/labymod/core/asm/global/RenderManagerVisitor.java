//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class RenderManagerVisitor extends ClassEditor
{
    private String entityName;
    private String shouldRenderName;
    private String doRenderEntityName;
    private String tesselatorName;
    private String glStateManagerName;
    private String renderDebugBoundingBoxName;
    
    public RenderManagerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.entityName = LabyModTransformer.getMappingImplementation().getEntityClassName();
        this.shouldRenderName = LabyModTransformer.getMappingImplementation().getShouldRenderName();
        this.doRenderEntityName = LabyModTransformer.getMappingImplementation().getDoRenderEntityName();
        this.tesselatorName = LabyModTransformer.getMappingImplementation().getTessellatorName();
        this.glStateManagerName = LabyModTransformer.getMappingImplementation().getGlStateManagerName();
        this.renderDebugBoundingBoxName = LabyModTransformer.getMappingImplementation().getRenderDebugBoundingBoxName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.shouldRenderName) && desc.startsWith("(L" + this.entityName + ";") && desc.endsWith(";DDD)Z")) {
            return new MethodVisitor(262144, mv) {
                private Label label;
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (this.label == null) {
                        super.visitMethodInsn(184, "BytecodeMethods", "isAlternativeFrustrumCullingEnabled", "()Z", false);
                        super.visitJumpInsn(153, this.label = new Label());
                        super.visitVarInsn(25, 1);
                        super.visitVarInsn(24, 3);
                        super.visitVarInsn(24, 5);
                        super.visitVarInsn(24, 7);
                        super.visitMethodInsn(184, "BytecodeMethods", "shouldRender", "(L" + RenderManagerVisitor.this.entityName + ";DDD)Z", false);
                        super.visitInsn(172);
                        super.visitLabel(this.label);
                    }
                    super.visitVarInsn(opcode, var);
                }
            };
        }
        if (name.equals(this.doRenderEntityName) && desc.startsWith("(L" + this.entityName + ";DDDFFZ)")) {
            return new MethodVisitor(262144, mv) {
                private boolean loaded = false;
                
                public void visitVarInsn(final int opcode, final int var) {
                    if (!this.loaded && opcode == 25 && var == 0) {
                        this.loaded = true;
                        super.visitCode();
                        super.visitVarInsn(25, 1);
                        super.visitVarInsn(24, 2);
                        super.visitVarInsn(24, 4);
                        super.visitVarInsn(24, 6);
                        super.visitVarInsn(23, 9);
                        super.visitMethodInsn(184, "BytecodeMethods", "doRenderEntity", "(L" + RenderManagerVisitor.this.entityName + ";DDDF)V", false);
                    }
                    super.visitVarInsn(opcode, var);
                }
            };
        }
        if (name.equals(this.renderDebugBoundingBoxName) && desc.startsWith("(L" + this.entityName + ";DDDFF)V")) {
            return new MethodVisitor(262144, mv) {
                private Label label;
                private boolean injected = false;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (this.label == null && owner.equals(RenderManagerVisitor.this.tesselatorName)) {
                        super.visitMethodInsn(184, "BytecodeMethods", "shouldRenderBoundingBoxVector", "()Z", false);
                        super.visitJumpInsn(153, this.label = new Label());
                    }
                    if (this.label != null && !this.injected && owner.equals(RenderManagerVisitor.this.glStateManagerName)) {
                        this.injected = true;
                        super.visitLabel(this.label);
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        return mv;
    }
}
