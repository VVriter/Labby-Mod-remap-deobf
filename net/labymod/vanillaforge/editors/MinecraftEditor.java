//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import net.minecraftforge.client.event.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class MinecraftEditor extends ClassEditor
{
    private static final String modClassName = "net/labymod/main/LabyMod";
    private static final String modClassInit = "init";
    
    public MinecraftEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            if (method.name.equals(Names.START_MINECRAFT.getName()) && method.desc.equals("()V")) {
                AbstractInsnNode postStartup = null;
                for (final AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == 18) {
                        final Object cst = ((LdcInsnNode)instruction).cst;
                        if (cst.equals("Post startup")) {
                            postStartup = instruction.getPrevious();
                            break;
                        }
                    }
                }
                if (postStartup == null) {
                    continue;
                }
                final InsnList insertList = new InsnList();
                insertList.add((AbstractInsnNode)new TypeInsnNode(187, "LabyMod"));
                insertList.add((AbstractInsnNode)new InsnNode(89));
                insertList.add((AbstractInsnNode)new MethodInsnNode(183, "LabyMod", "<init>", "()V", false));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "LabyMod", "init", "()V", false));
                insertList.add((AbstractInsnNode)new TypeInsnNode(187, "net/labymod/main/LabyMod"));
                insertList.add((AbstractInsnNode)new InsnNode(89));
                insertList.add((AbstractInsnNode)new MethodInsnNode(183, "net/labymod/main/LabyMod", "<init>", "()V", false));
                final String fmlInitializationEventName = "net/minecraftforge/fml/common/event/FMLInitializationEvent";
                insertList.add((AbstractInsnNode)new TypeInsnNode(187, fmlInitializationEventName));
                insertList.add((AbstractInsnNode)new InsnNode(89));
                insertList.add((AbstractInsnNode)new MethodInsnNode(183, fmlInitializationEventName, "<init>", "()V", false));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "net/labymod/main/LabyMod", "init", "(L" + fmlInitializationEventName + ";)V", false));
                insertList.add((AbstractInsnNode)new LdcInsnNode((Object)LabyModTweaker.getVersionStatic()));
                insertList.add((AbstractInsnNode)new MethodInsnNode(184, "net/labymod/addon/LabyModOFAddon", "addOptifineByVanillaForge", "(Ljava/lang/String;)V", false));
                method.instructions.insertBefore(postStartup, insertList);
            }
            else if (method.name.equals(Names.RUN_TICK.getName()) && method.desc.equals("()V")) {
                AbstractInsnNode endSectionNode = null;
                for (final AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == 182) {
                        final MethodInsnNode methodNode = (MethodInsnNode)instruction;
                        if (methodNode.name.equals(Names.PROFILER_END_SECTION.getName())) {
                            if (methodNode.owner.equals(Names.PROFILER.getName())) {
                                final AbstractInsnNode prevNode = instruction.getPrevious();
                                if (prevNode != null) {
                                    if (((FieldInsnNode)prevNode).name.equals(Names.MINECRAFT_MC_PROFILER.getName())) {
                                        endSectionNode = instruction;
                                    }
                                }
                            }
                        }
                    }
                }
                if (endSectionNode == null) {
                    continue;
                }
                final InsnList insertList = new InsnList();
                insertList.add((AbstractInsnNode)new MethodInsnNode(184, "EventCaller", "instance", "()LEventCaller;", false));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "EventCaller", "callTick", "()V", false));
                method.instructions.insertBefore(endSectionNode.getPrevious().getPrevious(), insertList);
            }
            else if (method.name.equals(Names.RUN_TICK_KEYBOARD.getName()) && method.desc.equals("()V")) {
                int eventKeyStateCount = 0;
                boolean nextKeyboardJumpInstruction = false;
                LabelNode insertLabelNode = null;
                for (final AbstractInsnNode instruction2 : method.instructions.toArray()) {
                    if (instruction2.getOpcode() == 184) {
                        final MethodInsnNode methodInsnNode = (MethodInsnNode)instruction2;
                        if (methodInsnNode.name.equals("getEventKeyState") && methodInsnNode.owner.equals("org/lwjgl/input/Keyboard") && ++eventKeyStateCount == 3) {
                            nextKeyboardJumpInstruction = true;
                        }
                    }
                    else if (instruction2.getOpcode() == 153 && nextKeyboardJumpInstruction) {
                        nextKeyboardJumpInstruction = false;
                        insertLabelNode = ((JumpInsnNode)instruction2).label;
                    }
                }
                if (insertLabelNode == null) {
                    continue;
                }
                final InsnList keyInsertList = new InsnList();
                keyInsertList.add((AbstractInsnNode)new MethodInsnNode(184, "EventCaller", "instance", "()LEventCaller;", false));
                keyInsertList.add((AbstractInsnNode)new MethodInsnNode(182, "EventCaller", "callKeyInput", "()V", false));
                method.instructions.insert((AbstractInsnNode)insertLabelNode, keyInsertList);
            }
            else if (method.name.equals(Names.DISPLAY_GUI_SCREEN.getName()) && method.desc.equals("(L" + Names.GUI_SCREEN.getName() + ";)V")) {
                AbstractInsnNode instanceOfNode = null;
                for (final AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == 193) {
                        if (((TypeInsnNode)instruction).desc.equals(Names.GUI_MAIN_MENU.getName())) {
                            instanceOfNode = instruction;
                            break;
                        }
                    }
                }
                if (instanceOfNode == null) {
                    continue;
                }
                final InsnList insertList = new InsnList();
                insertList.add((AbstractInsnNode)new VarInsnNode(25, 0));
                insertList.add((AbstractInsnNode)new FieldInsnNode(180, Names.MINECRAFT_CLASS.getName(), Names.CURRENT_SCREEN.getName(), "L" + Names.GUI_SCREEN.getName() + ";"));
                insertList.add((AbstractInsnNode)new VarInsnNode(58, 4));
                final String guiOpenEvent = Type.getInternalName((Class)GuiOpenEvent.class);
                insertList.add((AbstractInsnNode)new TypeInsnNode(187, guiOpenEvent));
                insertList.add((AbstractInsnNode)new InsnNode(89));
                insertList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                insertList.add((AbstractInsnNode)new MethodInsnNode(183, guiOpenEvent, "<init>", "(L" + Names.GUI_SCREEN.getName() + ";)V", false));
                insertList.add((AbstractInsnNode)new VarInsnNode(58, 5));
                insertList.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
                insertList.add((AbstractInsnNode)new VarInsnNode(25, 5));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                final LabelNode label = new LabelNode();
                insertList.add((AbstractInsnNode)new JumpInsnNode(153, label));
                insertList.add((AbstractInsnNode)new InsnNode(177));
                insertList.add((AbstractInsnNode)label);
                insertList.add((AbstractInsnNode)new VarInsnNode(25, 5));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, guiOpenEvent, "getGui", "()L" + Names.GUI_SCREEN.getName() + ";", false));
                insertList.add((AbstractInsnNode)new VarInsnNode(58, 1));
                method.instructions.insertBefore(instanceOfNode.getPrevious(), insertList);
            }
            else {
                if (!method.name.equals(Names.RUN_GAME_LOOP.getName()) || !method.desc.equals("()V")) {
                    continue;
                }
                AbstractInsnNode gameRendererInstruction = null;
                for (final AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction instanceof LdcInsnNode && ((LdcInsnNode)instruction).cst.equals("gameRenderer")) {
                        gameRendererInstruction = instruction;
                        break;
                    }
                }
                if (gameRendererInstruction == null) {
                    continue;
                }
                final InsnList insertList = new InsnList();
                insertList.add((AbstractInsnNode)new MethodInsnNode(184, "EventCaller", "instance", "()LEventCaller;", false));
                insertList.add((AbstractInsnNode)new VarInsnNode(25, 0));
                insertList.add((AbstractInsnNode)new FieldInsnNode(180, Names.MINECRAFT_CLASS.getName(), Names.TIMER_FIELD.getName(), "L" + Names.TIMER_CLASS.getName() + ";"));
                insertList.add((AbstractInsnNode)new FieldInsnNode(180, Names.TIMER_CLASS.getName(), Names.TIMER_RENDER_PARTIAL_TICKS.getName(), "F"));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "EventCaller", "callRenderTick", "(F)V", false));
                method.instructions.insert(gameRendererInstruction.getNext(), insertList);
            }
        }
    }
}
