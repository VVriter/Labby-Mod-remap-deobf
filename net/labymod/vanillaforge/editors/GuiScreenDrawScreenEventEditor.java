//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import net.minecraftforge.client.event.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class GuiScreenDrawScreenEventEditor extends ClassEditor
{
    public GuiScreenDrawScreenEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            if (method.name.equals("<init>") && !method.desc.equals("()V")) {
                method.desc = "(L" + Names.GUI_SCREEN.getName(true) + ";IIF)V";
                for (final AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode instanceof MethodInsnNode) {
                        final MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                        if (methodInsnNode.getOpcode() == 183) {
                            methodInsnNode.desc = "(L" + Names.GUI_SCREEN.getName(true) + ";)V";
                            final InsnList insertList = new InsnList();
                            insertList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                            method.instructions.insertBefore(insnNode, insertList);
                            final InsnList insertAfter = new InsnList();
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(25, 0));
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(21, 2));
                            insertAfter.add((AbstractInsnNode)new FieldInsnNode(181, Type.getInternalName((Class)GuiScreenEvent.DrawScreenEvent.class), "mouseX", "I"));
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(25, 0));
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(21, 3));
                            insertAfter.add((AbstractInsnNode)new FieldInsnNode(181, Type.getInternalName((Class)GuiScreenEvent.DrawScreenEvent.class), "mouseY", "I"));
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(25, 0));
                            insertAfter.add((AbstractInsnNode)new VarInsnNode(23, 4));
                            insertAfter.add((AbstractInsnNode)new FieldInsnNode(181, Type.getInternalName((Class)GuiScreenEvent.DrawScreenEvent.class), "renderPartialTicks", "F"));
                            method.instructions.insert(insnNode, insertAfter);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }
}
