//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.main.*;
import org.objectweb.asm.*;
import net.labymod.core.asm.*;

public class MinecraftVisitor extends ClassEditor
{
    private String createDisplayName;
    private String drawSplashScreenName;
    private String startGameName;
    private String minecraftName;
    private String fullscreenName;
    private String toggleFullscreenName;
    private String setInitialDisplayMode;
    private String rightClickMouseName;
    private String bootstrapName;
    private String printToSYSOUTName;
    private String runTickName;
    private String thirdPersonViewName;
    private String clickMouseName;
    private String loadWorldName;
    private String worldClientName;
    private String dispatchKeypressesName;
    private String shutdownMinecraftAppletName;
    private String isCallingFromMinecraftThreadName;
    
    public MinecraftVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        LabyModTransformer.addVisitors();
        this.createDisplayName = LabyModTransformer.getMappingImplementation().getCreateDisplayName();
        this.drawSplashScreenName = LabyModTransformer.getMappingImplementation().getDrawSplashScreenName();
        this.startGameName = LabyModTransformer.getMappingImplementation().getStartGameName();
        this.minecraftName = LabyModTransformer.getMappingImplementation().getMinecraftName();
        this.fullscreenName = LabyModTransformer.getMappingImplementation().getFullscreenName();
        this.toggleFullscreenName = LabyModTransformer.getMappingImplementation().getToggleFullscreenName();
        this.setInitialDisplayMode = LabyModTransformer.getMappingImplementation().getSetInitialDisplayModeName();
        this.rightClickMouseName = LabyModTransformer.getMappingImplementation().getRightClickMouseName();
        this.bootstrapName = LabyModTransformer.getMappingImplementation().getBootstrapName();
        this.printToSYSOUTName = LabyModTransformer.getMappingImplementation().getPrintToSYSOUTName();
        this.runTickName = LabyModTransformer.getMappingImplementation().getRunTickName();
        this.thirdPersonViewName = LabyModTransformer.getMappingImplementation().getThirdPersonViewName();
        this.clickMouseName = LabyModTransformer.getMappingImplementation().getClickMouseName();
        this.loadWorldName = LabyModTransformer.getMappingImplementation().getLoadWorldName();
        this.worldClientName = LabyModTransformer.getMappingImplementation().getWorldClientName();
        this.dispatchKeypressesName = LabyModTransformer.getMappingImplementation().getDispatchKeypressesName();
        this.shutdownMinecraftAppletName = LabyModTransformer.getMappingImplementation().getShutdownMinecraftAppletName();
        this.isCallingFromMinecraftThreadName = LabyModTransformer.getMappingImplementation().getIsCallingFromMinecraftThreadNameName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.createDisplayName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitLdcInsn(Object cst) {
                    if (cst instanceof String) {
                        cst = cst + " | LabyMod " + "3.9.41" + " " + "";
                    }
                    super.visitLdcInsn(cst);
                }
            };
        }
        if (name.equals("<init>")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (name.equals("info") && Source.ABOUT_MC_VERSION.startsWith("1.8")) {
                        super.visitMethodInsn(opcode, owner, "debug", desc, itf);
                    }
                    else {
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitMethodInsn(184, "BytecodeMethods", "loadOptions", "()V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        if (name.equals(this.runTickName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean inject = false;
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    super.visitJumpInsn(opcode, label);
                    if ((opcode == 154 || opcode == 160) && this.inject) {
                        super.visitMethodInsn(184, "BytecodeMethods", "canSwitchShader", "()Z", false);
                        super.visitJumpInsn(153, label);
                    }
                    this.inject = false;
                }
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    this.inject = false;
                    if (name.equals(MinecraftVisitor.this.thirdPersonViewName)) {
                        this.inject = true;
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && owner.equals("org/lwjgl/input/Mouse") && name.equals("getEventButtonState")) {
                        super.visitMethodInsn(184, "BytecodeMethods", "modifyEventButtonState", "(Z)Z", false);
                    }
                }
            };
        }
        if (desc.endsWith(";Ljava/lang/String;)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (owner.equals("java/lang/System") && name.equals("gc") && desc.equals("()V")) {
                        final Label label = new Label();
                        super.visitMethodInsn(184, "BytecodeMethods", "useGCOnLoadWorld", "()Z", false);
                        super.visitJumpInsn(153, label);
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                        super.visitLabel(label);
                    }
                    else {
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                    }
                }
            };
        }
        if (name.equals(this.loadWorldName) && desc.equals("(L" + this.worldClientName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitVarInsn(final int opcode, final int var) {
                    if (opcode == 25 && var == 0) {
                        super.visitVarInsn(opcode, var);
                        super.visitVarInsn(opcode, 1);
                        super.visitMethodInsn(184, "BytecodeMethods", "onLoadWorld", "(L" + MinecraftVisitor.this.worldClientName + ";)V", false);
                    }
                    super.visitVarInsn(opcode, var);
                }
            };
        }
        if (desc.endsWith("()Z") && access == 1 && name.equals(this.isCallingFromMinecraftThreadName)) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 180) {
                        super.visitMethodInsn(184, "BytecodeMethods", "isCallingFromMinecraftThread", "(Ljava/lang/Thread;)Ljava/lang/Thread;", false);
                    }
                }
            };
        }
        if (desc.endsWith(";)V") && access == 1) {
            return new MethodVisitor(262144, mv) {
                private int index = 0;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && owner.equals(MinecraftVisitor.this.bootstrapName) && name.equals(MinecraftVisitor.this.printToSYSOUTName) && desc.equals("(Ljava/lang/String;)V")) {
                        if (this.index >= 1 && this.index <= 3) {
                            super.visitIntInsn(25, 3);
                            super.visitIntInsn(25, 1);
                            super.visitMethodInsn(184, "BytecodeMethods", "reportCrash", "(Ljava/io/File;Ljava/lang/Object;)V", false);
                        }
                        ++this.index;
                    }
                }
            };
        }
        if (name.equals(this.startGameName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean renderCustomSplashScreen = false;
                private boolean inserted;
                
                public void visitLabel(final Label label) {
                    super.visitLabel(label);
                    if (!this.inserted) {
                        this.inserted = true;
                        this.mv.visitInsn(LabyModCoreMod.isForge() ? 4 : 3);
                        this.mv.visitMethodInsn(184, "net/labymod/main/LabyModForge", "setForge", "(Z)V", false);
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (!LabyModCoreMod.isForge() && ((opcode == 183 && name.equals(MinecraftVisitor.this.drawSplashScreenName)) || this.renderCustomSplashScreen)) {
                        this.renderCustomSplashScreen = true;
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                        this.mv.visitMethodInsn(184, "net/labymod/utils/manager/CustomLoadingScreen", "renderInstance", "()V", false);
                    }
                    else {
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                    }
                }
            };
        }
        if (name.equals(this.setInitialDisplayMode) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean firstMethodAdded = false;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    if (!this.firstMethodAdded && opcode == 180) {
                        this.firstMethodAdded = true;
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, MinecraftVisitor.this.minecraftName, MinecraftVisitor.this.fullscreenName, "Z");
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "borderlessWindowAtInitialDisplayMode", "(Z)V", false);
                        this.mv.visitVarInsn(25, 0);
                    }
                    if (opcode == 184 && name == "setFullscreen") {
                        return;
                    }
                    super.visitFieldInsn(opcode, owner, name, desc);
                }
            };
        }
        if (name.equals(this.toggleFullscreenName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean firstMethodAdded = false;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    if (!this.firstMethodAdded && opcode == 180) {
                        this.firstMethodAdded = true;
                        super.visitFieldInsn(opcode, owner, name, desc);
                        this.mv.visitInsn(4);
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "borderlessWindowAtToggleFullscreen", "(ZZ)V", false);
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, MinecraftVisitor.this.minecraftName, MinecraftVisitor.this.fullscreenName, "Z");
                        return;
                    }
                    super.visitFieldInsn(opcode, owner, name, desc);
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if (opcode == 184 && name.equals("setFullscreen")) {
                        this.mv.visitInsn(3);
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "borderlessWindowAtToggleFullscreen", "(ZZ)V", false);
                        return;
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        if (name.equals(this.rightClickMouseName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                private boolean addedA = false;
                private boolean addedB = false;
                
                public void visitIntInsn(final int opcode, final int operand) {
                    super.visitIntInsn(opcode, operand);
                    if (opcode == 25 && !this.addedA) {
                        this.addedA = true;
                        super.visitIntInsn(opcode, operand);
                    }
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 182 && !this.addedB) {
                        this.addedB = true;
                        super.visitMethodInsn(184, "BytecodeMethods", "shouldCancelMouseClick", "(Z)Z", false);
                    }
                }
            };
        }
        if (name.equals(this.clickMouseName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                boolean added = false;
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    super.visitJumpInsn(opcode, label);
                    if (!this.added) {
                        super.visitMethodInsn(184, "BytecodeMethods", "onMouseClick", "()V", false);
                        this.added = true;
                    }
                }
            };
        }
        if (name.equals(this.dispatchKeypressesName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && owner.equals("org/lwjgl/input/Keyboard") && name.equals("getEventCharacter")) {
                        this.mv.visitIntInsn(17, 256);
                        this.mv.visitInsn(96);
                    }
                }
            };
        }
        if (name.equals(this.shutdownMinecraftAppletName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && owner.equals("org/lwjgl/opengl/Display") && name.equals("destroy")) {
                        super.visitMethodInsn(184, "BytecodeMethods", "shutdown", "()V", false);
                    }
                }
            };
        }
        return mv;
    }
}
