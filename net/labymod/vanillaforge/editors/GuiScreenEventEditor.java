//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class GuiScreenEventEditor extends ClassEditor
{
    public GuiScreenEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
    }
    
    public void accept(final String name, final ClassNode node) {
        node.fields.add(new FieldNode(1, "gui", "L" + Names.GUI_SCREEN.getName(true) + ";", (String)null, (Object)null));
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            if (method.name.equals("<init>")) {
                method.desc = "(L" + Names.GUI_SCREEN.getName(true) + ";)V";
                for (final AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode instanceof MethodInsnNode) {
                        final MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                        if (methodInsnNode.getOpcode() == 183) {
                            final InsnList insertList = new InsnList();
                            insertList.add((AbstractInsnNode)new VarInsnNode(25, 0));
                            insertList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                            insertList.add((AbstractInsnNode)new FieldInsnNode(181, "net/minecraftforge/client/event/GuiScreenEvent", "gui", "L" + Names.GUI_SCREEN.getName(true) + ";"));
                            method.instructions.insert(insnNode, insertList);
                            break;
                        }
                    }
                }
                break;
            }
        }
        final MethodNode methodNode = new MethodNode(262144, 1, "<init>", "()V", (String)null, (String[])null);
        methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
        methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(183, "net/minecraftforge/fml/common/eventhandler/Event", "<init>", "()V", false));
        methodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
        node.methods.add(methodNode);
    }
}
