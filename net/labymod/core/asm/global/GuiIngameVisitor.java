//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import java.util.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GuiIngameVisitor extends ClassEditor
{
    private final boolean MC18;
    private Map<String, String> constructorRemapper;
    private String guiNewChatName;
    private String guiPlayerTabOverlayName;
    private String guiIngameName;
    private String showCrosshairName;
    private String glStateManagerName;
    private String enableAlphaName;
    private String scaledResolutionName;
    private String entityPlayerName;
    private String renderPlayerStatsName;
    
    public GuiIngameVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.MC18 = LabyModTransformer.getVersion().startsWith("1.8");
        this.constructorRemapper = new HashMap<String, String>();
        final String packageName = this.MC18 ? "mc18" : "mc112";
        this.guiNewChatName = LabyModTransformer.getMappingImplementation().getGuiNewChatName();
        this.guiPlayerTabOverlayName = LabyModTransformer.getMappingImplementation().getGuiPlayerTabOverlayName();
        this.guiIngameName = LabyModTransformer.getMappingImplementation().getGuiIngameName();
        this.entityPlayerName = LabyModTransformer.getMappingImplementation().getEntityPlayerName();
        this.scaledResolutionName = LabyModTransformer.getMappingImplementation().getScaledResolutionName();
        this.renderPlayerStatsName = LabyModTransformer.getMappingImplementation().getRenderPlayerStatsName();
        this.constructorRemapper.put(this.guiNewChatName, String.format("net/labymod/core_implementation/%s/gui/GuiChatAdapter", packageName));
        this.constructorRemapper.put(this.guiPlayerTabOverlayName, String.format("net/labymod/core_implementation/%s/gui/ModPlayerTabOverlay", packageName));
        if (this.MC18) {
            this.showCrosshairName = (LabyModCoreMod.isObfuscated() ? "b" : "showCrosshair");
        }
        else {
            this.glStateManagerName = LabyModTransformer.getMappingImplementation().getGlStateManagerName();
            this.enableAlphaName = LabyModTransformer.getMappingImplementation().getEnableAlphaName();
        }
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("<init>")) {
            return new MethodVisitor(262144, mv) {
                public void visitTypeInsn(final int opcode, final String type) {
                    super.visitTypeInsn(opcode, (opcode == 187) ? ((String)GuiIngameVisitor.this.firstNonNull(GuiIngameVisitor.this.constructorRemapper.get(type), type)) : type);
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, (opcode == 183) ? ((String)GuiIngameVisitor.this.firstNonNull(GuiIngameVisitor.this.constructorRemapper.get(owner), owner)) : owner, name, desc, itf);
                }
            };
        }
        if (desc.equals("(F)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean nextIsScoreboard = false;
                
                public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
                    super.visitFrame(type, nLocal, local, nStack, stack);
                    if (type == 4) {
                        this.nextIsScoreboard = true;
                    }
                }
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    super.visitJumpInsn(opcode, label);
                    if (opcode == 198 && this.nextIsScoreboard) {
                        super.visitMethodInsn(184, "BytecodeMethods", "canRenderScoreboard", "()Z", this.nextIsScoreboard = false);
                        super.visitJumpInsn(153, label);
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (GuiIngameVisitor.this.MC18 && opcode == 182 && owner.equals(GuiIngameVisitor.this.guiIngameName) && name.equals(GuiIngameVisitor.this.showCrosshairName) && desc.equals("()Z")) {
                        super.visitMethodInsn(184, "BytecodeMethods", "onRenderCrosshair18", "(Z)Z", false);
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitMethodInsn(184, "BytecodeMethods", "onRenderIngameOverlay", "()V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        if (name.equals(this.renderPlayerStatsName) && desc.equals("(L" + this.scaledResolutionName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                private int modeHeart = 0;
                private boolean patchedHeart = false;
                private int modeSaturation = 0;
                private int airBubbleVarToModify = -1;
                private boolean nextVirtual = false;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    this.modeHeart = 0;
                    if (opcode == 180 && owner.equals(GuiIngameVisitor.this.entityPlayerName) && desc.endsWith(";")) {
                        this.modeHeart = 1;
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (this.modeHeart == 1 && opcode == 182) {
                        this.modeHeart = 2;
                    }
                    else if (this.modeHeart == 2 && opcode == 182) {
                        this.modeHeart = 3;
                    }
                    else if (this.modeHeart != 4 || opcode != 182) {
                        this.modeHeart = 0;
                    }
                    if (this.nextVirtual && this.airBubbleVarToModify != -1) {
                        this.nextVirtual = false;
                        super.visitVarInsn(21, this.airBubbleVarToModify);
                        super.visitMethodInsn(184, "BytecodeMethods", "renderSaturationBarAndModifyAirOffset", "(I)I", false);
                        super.visitVarInsn(54, this.airBubbleVarToModify);
                    }
                }
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    if (this.modeHeart == 3 && opcode == 153) {
                        this.modeHeart = 4;
                    }
                    else if (this.modeHeart == 4 && opcode == 153 && !this.patchedHeart) {
                        this.patchedHeart = true;
                        super.visitMethodInsn(184, "BytecodeMethods", "shouldRender18HeartAnimation", "(Z)Z", false);
                    }
                    else {
                        this.modeHeart = 0;
                    }
                    super.visitJumpInsn(opcode, label);
                }
                
                public void visitVarInsn(final int opcode, final int var) {
                    super.visitVarInsn(opcode, var);
                    if (this.modeSaturation == 2 && opcode == 54) {
                        this.airBubbleVarToModify = var;
                    }
                    this.modeSaturation = 0;
                }
                
                public void visitIntInsn(final int opcode, final int operand) {
                    super.visitIntInsn(opcode, operand);
                    this.modeSaturation = 0;
                    if (opcode == 16 && operand == 10) {
                        this.modeSaturation = 1;
                    }
                }
                
                public void visitInsn(final int opcode) {
                    super.visitInsn(opcode);
                    if (this.modeSaturation == 1 && opcode == 100) {
                        this.modeSaturation = 2;
                    }
                    else {
                        this.modeSaturation = 0;
                    }
                }
                
                public void visitLdcInsn(final Object cst) {
                    super.visitLdcInsn(cst);
                    if (cst instanceof String && ((String)cst).equals("air")) {
                        this.nextVirtual = true;
                    }
                }
            };
        }
        if (!this.MC18 && desc.equals("(FL" + this.scaledResolutionName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean crosshair = false;
                private int count = 0;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && owner.equals(GuiIngameVisitor.this.glStateManagerName) && name.equals(GuiIngameVisitor.this.enableAlphaName)) {
                        this.crosshair = true;
                    }
                }
                
                public void visitIntInsn(final int opcode, final int operand) {
                    super.visitIntInsn(opcode, operand);
                    if (this.crosshair && opcode == 16 && operand == 16) {
                        ++this.count;
                        if (this.count == 2) {
                            super.visitMethodInsn(184, "BytecodeMethods", "onRenderCrosshair112", "(I)I", this.crosshair = false);
                        }
                    }
                }
            };
        }
        return mv;
    }
    
    private <T> T firstNonNull(final T first, final T second) {
        return (first != null) ? first : second;
    }
}
