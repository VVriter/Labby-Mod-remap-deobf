//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge;

import net.minecraft.launchwrapper.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.labymod.vanillaforge.editors.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.*;

public class LabyModTransformer implements IClassTransformer
{
    private Map<String, ClassEditor> editors;
    
    public LabyModTransformer() {
        (this.editors = new HashMap<String, ClassEditor>()).put(Names.MINECRAFT_CLASS.getName(false), (ClassEditor)new MinecraftEditor());
        this.editors.put(GuiOpenEvent.class.getName(), (ClassEditor)new GuiOpenEventEditor());
        this.editors.put(RenderGameOverlayEvent.class.getName(), (ClassEditor)new RenderGameOverlayEventEditor());
        this.editors.put(FOVUpdateEvent.class.getName(), (ClassEditor)new FOVUpdateEventEditor());
        this.editors.put(Names.ABSTRACT_CLIENT_PLAYER.getName(false), (ClassEditor)new AbstractClientPlayerEditor());
        this.editors.put(Names.GUI_INGAME.getName(false), (ClassEditor)new GuiIngameEditor());
        this.editors.put(Names.RENDER_MANAGER.getName(false), (ClassEditor)new RenderManagerEditor());
        this.editors.put(Names.GUI_SCREEN.getName(false), (ClassEditor)new GuiScreenEditor());
        this.editors.put(GuiScreenEvent.class.getName(), (ClassEditor)new GuiScreenEventEditor());
        this.editors.put(GuiScreenEvent.DrawScreenEvent.class.getName(), (ClassEditor)new GuiScreenDrawScreenEventEditor());
        this.editors.put(GuiScreenEvent.DrawScreenEvent.Post.class.getName(), (ClassEditor)new GuiScreenDrawScreenPostEventEditor());
        this.editors.put(Names.ENTITY_RENDERER.getName(false), (ClassEditor)new EntityRendererEditor());
        this.editors.put(RenderWorldLastEvent.class.getName(), (ClassEditor)new RenderWorldLastEventEditor());
    }
    
    public byte[] transform(final String name, final String transformed, final byte[] bytes) {
        if (!this.editors.containsKey(name)) {
            return bytes;
        }
        System.out.println("[LabyMod-Vanilla] Transforming class " + name);
        final ClassEditor editor = this.editors.get(name);
        switch (editor.getType()) {
            case CLASS_NODE: {
                final ClassNode node = new ClassNode();
                final ClassReader reader = new ClassReader(bytes);
                reader.accept((ClassVisitor)node, 0);
                editor.accept(name, node);
                final ClassWriter writer = new ClassWriter(3);
                node.accept((ClassVisitor)writer);
                final byte[] returnedBytes = writer.toByteArray();
                return returnedBytes;
            }
            case CLASS_VISITOR: {
                final ClassReader classReader = new ClassReader(bytes);
                final ClassWriter classWriter = new ClassWriter(classReader, 3);
                editor.accept(name, (ClassVisitor)classWriter);
                classReader.accept((ClassVisitor)editor, 0);
                final byte[] retBytes = classWriter.toByteArray();
                return retBytes;
            }
            default: {
                return bytes;
            }
        }
    }
}
