//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

import java.io.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.geometry.bedrock.*;
import com.google.gson.*;
import net.labymod.user.cosmetic.geometry.blockbench.*;
import java.util.*;

public class GeometryLoader
{
    private static final Gson GSON;
    private final Geometry geometry;
    
    public GeometryLoader(final Geometry geometry) throws IOException {
        this.geometry = geometry;
    }
    
    public GeometryLoader(final String json) throws IOException {
        this((Geometry)GeometryLoader.GSON.fromJson(json, (Class)Geometry.class));
    }
    
    public GeometryLoader(final InputStream inputStream) throws IOException {
        this((Geometry)GeometryLoader.GSON.fromJson((Reader)new InputStreamReader(inputStream), (Class)Geometry.class));
        inputStream.close();
    }
    
    public GeometryLoader(final File file) throws IOException {
        this(new FileInputStream(file));
    }
    
    public Geometry getGeometry() {
        return this.geometry;
    }
    
    public GeometryModelRenderer toModelRenderer(final bqf base) throws Exception {
        return this.toBlockBenchLoader(base).getModel();
    }
    
    public BlockBenchLoader toBlockBenchLoader(final bqf base) throws Exception {
        return new BlockBenchLoader(base, this.toBlockBench());
    }
    
    public BlockBench toBlockBench() {
        final BlockBench blockBench = new BlockBench();
        if (this.geometry.minecraftGeometry == null || this.geometry.minecraftGeometry.isEmpty()) {
            return blockBench;
        }
        final MinecraftGeometry geometry = this.geometry.minecraftGeometry.get(0);
        blockBench.resolution.width = geometry.description.textureWidth;
        blockBench.resolution.height = geometry.description.textureHeight;
        for (final Bone bone : geometry.bones) {
            for (int i = 0; i < ((bone.cubes == null) ? 0 : bone.cubes.size()); ++i) {
                final BedrockCube cube = bone.cubes.get(i);
                blockBench.elements.add(this.toBlockBenchCube(cube, bone.name + "_" + i));
            }
        }
        for (final Bone bone : geometry.bones) {
            if (bone.parent == null) {
                blockBench.outliner.add((JsonElement)this.toBlockBenchChild(geometry, bone));
            }
        }
        return blockBench;
    }
    
    private JsonObject toBlockBenchChild(final MinecraftGeometry geometry, final Bone bone) {
        final JsonObject child = new JsonObject();
        child.addProperty("name", bone.name);
        if (bone.pivot != null) {
            final JsonArray arrayOrigin = new JsonArray();
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)(-bone.pivot.get(0))));
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)bone.pivot.get(1)));
            arrayOrigin.add((JsonElement)new JsonPrimitive((Number)bone.pivot.get(2)));
            child.add("origin", (JsonElement)arrayOrigin);
        }
        if (bone.rotation != null) {
            final JsonArray arrayRotation = new JsonArray();
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)(-bone.rotation.get(0))));
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)(-bone.rotation.get(1))));
            arrayRotation.add((JsonElement)new JsonPrimitive((Number)bone.rotation.get(2)));
            child.add("rotation", (JsonElement)arrayRotation);
        }
        child.addProperty("uuid", bone.uuid);
        child.add("children", (JsonElement)this.findChildren(geometry, bone));
        return child;
    }
    
    private JsonArray findChildren(final MinecraftGeometry geometry, final Bone targetBone) {
        final JsonArray array = new JsonArray();
        for (final Bone bone : geometry.bones) {
            if (bone.parent != null && bone.parent.equals(targetBone.name)) {
                array.add((JsonElement)this.toBlockBenchChild(geometry, bone));
            }
        }
        if (targetBone.cubes != null) {
            for (final BedrockCube cube : targetBone.cubes) {
                array.add((JsonElement)new JsonPrimitive(cube.uuid));
            }
        }
        return array;
    }
    
    private BlockBenchCube toBlockBenchCube(final BedrockCube cube, final String name) {
        final BlockBenchCube bbCube = new BlockBenchCube();
        bbCube.name = name;
        bbCube.from = Arrays.asList(-cube.origin.get(0) - cube.size.get(0), cube.origin.get(1), cube.origin.get(2));
        bbCube.to = Arrays.asList(-cube.origin.get(0), cube.origin.get(1) + cube.size.get(1), cube.origin.get(2) + cube.size.get(2));
        if (cube.rotation != null) {
            bbCube.rotation = Arrays.asList(-cube.rotation.get(0), -cube.rotation.get(1), cube.rotation.get(2));
        }
        if (cube.pivot != null) {
            bbCube.origin = Arrays.asList(-cube.pivot.get(0), cube.pivot.get(1), cube.pivot.get(2));
        }
        bbCube.inflate = cube.inflate;
        bbCube.uvOffset = cube.uv;
        bbCube.uuid = cube.uuid;
        bbCube.mirror = cube.mirror;
        return bbCube;
    }
    
    static {
        GSON = new Gson();
    }
}
