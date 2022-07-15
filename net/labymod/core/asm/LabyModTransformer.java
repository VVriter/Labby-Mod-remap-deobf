//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm;

import net.minecraft.launchwrapper.*;
import net.labymod.main.*;
import net.labymod.core.asm.version_18.*;
import net.labymod.core.asm.mappings.*;
import net.labymod.core.asm.version_112.*;
import java.util.*;
import net.labymod.support.util.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import java.io.*;
import org.apache.commons.io.*;
import net.labymod.core.asm.global.*;

public class LabyModTransformer implements IClassTransformer
{
    private static LabyModTransformer instance;
    private static MappingAdapter mappingImplementation;
    private boolean debugASM;
    private Map<String, Map<String, Class<? extends ClassEditor>>> visitorsByVersion;
    
    public static MappingAdapter getMappingImplementation() {
        return LabyModTransformer.mappingImplementation;
    }
    
    public static String getVersion() {
        return Source.ABOUT_MC_VERSION;
    }
    
    public static void addVisitors() {
        final Map<String, Map<String, Class<? extends ClassEditor>>> visitorsByVersion = LabyModTransformer.instance.getVisitorsByVersion();
        if (!LabyModCoreMod.isObfuscated()) {
            LabyModTransformer.mappingImplementation = new UnobfuscatedImplementation();
        }
        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            if (LabyModCoreMod.isObfuscated()) {
                LabyModTransformer.mappingImplementation = new Minecraft18MappingImplementation();
            }
            final Map<String, Class<? extends ClassEditor>> visitors_18 = new HashMap<String, Class<? extends ClassEditor>>();
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getEntityPlayerSpName(), EntityPlayerSPVisitor.class);
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getEntityLivingBaseName(), EntityLivingBaseVisitor.class);
            addVisitor(visitors_18, "net.labymod.core_implementation.mc18.of.CapeImageBuffer", CapeImageBufferVisitor.class);
            addVisitor(visitors_18, "CapeUtils", CapeUtilsVisitor.class);
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getEntityPlayerName(), EntityPlayerVisitor.class);
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getS26PacketMapChunkBulkName(), ChunkPacketVisitor.class);
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getS21PacketChunkDataName(), ChunkPacketVisitor.class);
            addVisitor(visitors_18, LabyModTransformer.mappingImplementation.getItemStackName(), ItemStackVisitor.class);
            visitorsByVersion.put("1.8", visitors_18);
        }
        if (Source.ABOUT_MC_VERSION.startsWith("1.12")) {
            if (LabyModCoreMod.isObfuscated()) {
                LabyModTransformer.mappingImplementation = new Minecraft112MappingImplementation();
            }
            final Map<String, Class<? extends ClassEditor>> visitors_19 = new HashMap<String, Class<? extends ClassEditor>>();
            addVisitor(visitors_19, LabyModTransformer.mappingImplementation.getEntityLivingBaseName(), net.labymod.core.asm.version_112.EntityLivingBaseVisitor.class);
            addVisitor(visitors_19, LabyModTransformer.mappingImplementation.getGuiBossOverlayName(), GuiBossOverlayVisitor.class);
            addVisitor(visitors_19, LabyModTransformer.mappingImplementation.getRenderLivingBaseName(), RenderLivingBaseVisitor.class);
            visitorsByVersion.put("1.12", visitors_19);
        }
        final Map<String, Class<? extends ClassEditor>> globalVisitors = new HashMap<String, Class<? extends ClassEditor>>();
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiScreenName(), (Class<? extends ClassEditor>)GuiScreenVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiDisconnectedName(), (Class<? extends ClassEditor>)GuiDisconnectedVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiSlotName(), (Class<? extends ClassEditor>)GuiSlotVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getEntityRendererName(), (Class<? extends ClassEditor>)EntityRendererVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getServerListEntryNormalName(), (Class<? extends ClassEditor>)ServerListEntryNormalVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getServerListEntryNormalName() + "$1", ServerListEntryNormal$1Visitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiMultiplayerName(), (Class<? extends ClassEditor>)GuiMultiplayerVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getScaledResolutionName(), (Class<? extends ClassEditor>)ScaledResolutionVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getNetHandlerPlayClientName(), (Class<? extends ClassEditor>)NetHandlerPlayClientVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getRenderGlobalName(), (Class<? extends ClassEditor>)RenderGlobalVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiContainerName(), (Class<? extends ClassEditor>)GuiContainerVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getServerPingerName(), (Class<? extends ClassEditor>)ServerPingerVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getItemName(), (Class<? extends ClassEditor>)ItemVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getMessageDeserializerName(), (Class<? extends ClassEditor>)MessageDeserializerVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getModelBipedName(), (Class<? extends ClassEditor>)ModelBipedVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getItemRendererName(), (Class<? extends ClassEditor>)ItemRendererVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiIngameName(), (Class<? extends ClassEditor>)GuiIngameVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGuiMainMenuName(), (Class<? extends ClassEditor>)GuiMainMenuVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getRenderManagerName(), (Class<? extends ClassEditor>)RenderManagerVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getRenderPigName(), (Class<? extends ClassEditor>)RenderPigVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getEntityClassName(), (Class<? extends ClassEditor>)EntityVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getWorldClientName(), (Class<? extends ClassEditor>)WorldClientVisitor.class);
        addVisitor(globalVisitors, LabyModTransformer.mappingImplementation.getGameSettingsName(), (Class<? extends ClassEditor>)GameSettingsVisitor.class);
        addVisitor(globalVisitors, "net.labymod.mojang.afec.EntityAFECFields", (Class<? extends ClassEditor>)EntityAFECFieldsVisitor.class);
        if (LabyModCoreMod.isForge()) {
            addVisitor(globalVisitors, "net.minecraftforge.client.GuiIngameForge", (Class<? extends ClassEditor>)GuiIngameForgeVisitor.class);
        }
        visitorsByVersion.put("global", globalVisitors);
    }
    
    private static void addVisitor(final Map<String, Class<? extends ClassEditor>> globalVisitors, final String name, final Class<? extends ClassEditor> clazz) {
        globalVisitors.put(name.replace("/", "."), clazz);
    }
    
    public LabyModTransformer() {
        this.debugASM = (System.getProperty("debugASM") != null);
        this.visitorsByVersion = new HashMap<String, Map<String, Class<? extends ClassEditor>>>();
        LabyModTransformer.instance = this;
        final Map<String, Class<? extends ClassEditor>> globalVisitors = new HashMap<String, Class<? extends ClassEditor>>();
        globalVisitors.put("net.minecraft.client.main.Main", (Class<? extends ClassEditor>)MainVisitor.class);
        LabyModTransformer.instance.getVisitorsByVersion().put("global", globalVisitors);
    }
    
    public byte[] transform(final String name, final String transformedName, byte[] bytes) {
        final boolean isObfuscated = !name.equals(transformedName);
        final Set<Class<? extends ClassEditor>> editors = new HashSet<Class<? extends ClassEditor>>();
        for (final Map.Entry<String, Map<String, Class<? extends ClassEditor>>> visitorEntry : this.visitorsByVersion.entrySet()) {
            if (!visitorEntry.getKey().equals("global") && !Source.ABOUT_MC_VERSION.isEmpty() && !Source.ABOUT_MC_VERSION.startsWith(visitorEntry.getKey())) {
                continue;
            }
            if (!visitorEntry.getValue().containsKey(name)) {
                continue;
            }
            editors.add(visitorEntry.getValue().get(name));
        }
        if (editors.size() != 0) {
            bytes = this.transform(name, transformedName, editors, bytes, isObfuscated);
        }
        return bytes;
    }
    
    private byte[] transform(final String name, final String transformedName, final Set<Class<? extends ClassEditor>> editors, final byte[] bytes, final boolean isObfuscated) {
        Debug.log(Debug.EnumDebugMode.ASM, "Transforming " + transformedName + "...");
        try {
            for (final Class<? extends ClassEditor> editorClass : editors) {
                final ClassEditor editor = (ClassEditor)editorClass.newInstance();
                switch (editor.getType()) {
                    case CLASS_NODE: {
                        final ClassNode node = new ClassNode();
                        final ClassReader reader = new ClassReader(bytes);
                        reader.accept((ClassVisitor)node, 0);
                        editor.accept(name, node);
                        final ClassWriter writer = new ClassWriter(3);
                        node.accept((ClassVisitor)writer);
                        return this.debugTransformedClass(transformedName, writer.toByteArray());
                    }
                    case CLASS_VISITOR: {
                        final ClassReader classReader = new ClassReader(bytes);
                        final ClassWriter classWriter = new ClassWriter(classReader, 3);
                        editor.accept(name, (ClassVisitor)classWriter);
                        classReader.accept((ClassVisitor)editor, 0);
                        return this.debugTransformedClass(transformedName, classWriter.toByteArray());
                    }
                    case CLASS_VISITOR_AND_REMAPPER: {
                        ClassReader classReader = new ClassReader(bytes);
                        ClassWriter classWriter = new ClassWriter(classReader, 3);
                        editor.accept(name, (ClassVisitor)classWriter);
                        classReader.accept((ClassVisitor)editor, 0);
                        classReader = new ClassReader(classWriter.toByteArray());
                        classWriter = new ClassWriter(classReader, 3);
                        final RemappingClassAdapter adapter = new RemappingClassAdapter((ClassVisitor)classWriter, (Remapper)new Remapper() {
                            public String map(final String typeName) {
                                return editor.visitMapping(typeName);
                            }
                        });
                        editor.accept(name, (ClassVisitor)classWriter);
                        classReader.accept((ClassVisitor)adapter, 8);
                        return this.debugTransformedClass(transformedName, classWriter.toByteArray());
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
    
    public byte[] debugTransformedClass(final String name, final byte[] bytes) throws Exception {
        if (this.debugASM) {
            final File asmFolder = new File("asm");
            if (!asmFolder.exists()) {
                asmFolder.mkdir();
            }
            final File byteFile = new File(name + ".class");
            final File byteFileInAsm = new File(asmFolder, name + ".class");
            byteFileInAsm.delete();
            FileUtils.writeByteArrayToFile(byteFile, bytes);
            byteFile.renameTo(byteFileInAsm);
        }
        return bytes;
    }
    
    public Map<String, Map<String, Class<? extends ClassEditor>>> getVisitorsByVersion() {
        return this.visitorsByVersion;
    }
    
    public static void resolveMinecraftClass(final String minecraftClassName) {
        final Map<String, Class<? extends ClassEditor>> globalVisitors = new HashMap<String, Class<? extends ClassEditor>>();
        globalVisitors.put(minecraftClassName.replace("/", "."), (Class<? extends ClassEditor>)MinecraftVisitor.class);
        LabyModTransformer.instance.getVisitorsByVersion().put("global", globalVisitors);
    }
    
    public static LabyModTransformer getInstance() {
        return LabyModTransformer.instance;
    }
}
