//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.geometry.blockbench.*;
import java.io.*;
import com.google.gson.*;
import net.labymod.user.cosmetic.geometry.effect.*;
import java.util.*;

public class BlockBenchLoader
{
    private static final Gson GSON;
    private BlockBench blockBench;
    private bqf baseModel;
    private GeometryModelRenderer model;
    private final Map<String, BlockBenchCube> cubes;
    private final List<GeometryEffect> effects;
    private final Map<String, Item> nameToItemMappings;
    private final Map<String, GeometryModelRenderer> nameToModelMappings;
    private final Map<String, Integer> nameToIdMappings;
    private final Map<Integer, String> idToNameMappings;
    
    public BlockBenchLoader(final bqf baseModel, final BlockBench blockBench) throws IOException {
        this.cubes = new HashMap<String, BlockBenchCube>();
        this.effects = new ArrayList<GeometryEffect>();
        this.nameToItemMappings = new HashMap<String, Item>();
        this.nameToModelMappings = new HashMap<String, GeometryModelRenderer>();
        this.nameToIdMappings = new HashMap<String, Integer>();
        this.idToNameMappings = new HashMap<Integer, String>();
        this.baseModel = baseModel;
        this.model = new GeometryModelRenderer();
        this.blockBench = blockBench;
        for (final BlockBenchCube cube : this.blockBench.elements) {
            this.cubes.put(cube.uuid, cube);
        }
        this.addGroup(this.blockBench.outliner, this.model, new Group());
    }
    
    public BlockBenchLoader(final bpx baseModel, final InputStream inputStream) throws IOException {
        this((bqf)baseModel, (BlockBench)BlockBenchLoader.GSON.fromJson((Reader)new InputStreamReader(inputStream), (Class)BlockBench.class));
        inputStream.close();
    }
    
    public BlockBenchLoader(final bpx baseModel, final String json) throws IOException {
        this((bqf)baseModel, (BlockBench)BlockBenchLoader.GSON.fromJson(json, (Class)BlockBench.class));
    }
    
    public BlockBenchLoader(final bpx baseModel, final File file) throws IOException {
        this(baseModel, new FileInputStream(file));
    }
    
    private void addGroup(final JsonArray outliner, final GeometryModelRenderer parent, final Group group) {
        for (int i = 0; i < outliner.size(); ++i) {
            final JsonElement element = outliner.get(i);
            if (element.isJsonObject()) {
                final Group child = (Group)BlockBenchLoader.GSON.fromJson(element, (Class)Group.class);
                final GeometryModelRenderer model = this.addModel(parent, (Item)child, (Item)group);
                this.addGroup(child.children, model, child);
            }
            else {
                final BlockBenchCube cube = this.cubes.get(element.getAsString());
                final float originX = group.origin.get(0).floatValue();
                final float originY = group.origin.get(1).floatValue();
                final float originZ = group.origin.get(2).floatValue();
                final float fromX = cube.from.get(0).floatValue();
                final float fromY = cube.from.get(1).floatValue();
                final float fromZ = cube.from.get(2).floatValue();
                final float toX = cube.to.get(0).floatValue();
                final float toY = cube.to.get(1).floatValue();
                final float toZ = cube.to.get(2).floatValue();
                final int sizeX = Math.round(Math.abs(fromX - toX));
                final int sizeY = Math.round(Math.abs(fromY - toY));
                final int sizeZ = Math.round(Math.abs(fromZ - toZ));
                float x = originX - toX;
                float y = -fromY - sizeY + originY;
                float z = fromZ - originZ;
                final int textureOffsetX = (cube.uvOffset == null) ? 0 : cube.uvOffset.get(0);
                final int textureOffsetZ = (cube.uvOffset == null) ? 0 : cube.uvOffset.get(1);
                final GeometryModelRenderer target = (cube.rotation == null) ? parent : this.addModel(parent, (Item)cube, (Item)group);
                if (cube.rotation != null) {
                    x -= target.rotationPointX;
                    y -= target.rotationPointY;
                    z -= target.rotationPointZ;
                }
                target.setTextureOffset(textureOffsetX, textureOffsetZ);
                target.setTextureSize(this.blockBench.resolution.width, this.blockBench.resolution.height);
                boolean shouldAdd = true;
                final GeometryEffect effect = this.getEffectByModel(target);
                if (effect != null) {
                    shouldAdd = effect.onCubeAdd(target, x, y, z, sizeX, sizeY, sizeZ, cube.inflate, cube.mirror);
                }
                if (shouldAdd) {
                    target.addBox(x, y, z, (float)sizeX, (float)sizeY, (float)sizeZ, cube.inflate, cube.mirror);
                }
            }
        }
    }
    
    private GeometryModelRenderer addModel(final GeometryModelRenderer parentModel, final Item item, final Item group) {
        final GeometryModelRenderer model = new GeometryModelRenderer();
        this.rotateGroup(model, item, group);
        parentModel.addChild(model);
        this.addMapping(model, item);
        return model;
    }
    
    private void rotateGroup(final GeometryModelRenderer model, final Item child, final Item group) {
        float originX = (child.origin == null) ? 0.0f : child.origin.get(0).floatValue();
        float originY = (child.origin == null) ? 0.0f : child.origin.get(1).floatValue();
        float originZ = (child.origin == null) ? 0.0f : child.origin.get(2).floatValue();
        if (group.origin != null) {
            originX -= group.origin.get(0).floatValue();
            originY -= group.origin.get(1).floatValue();
            originZ -= group.origin.get(2).floatValue();
        }
        model.setRotationPoint(-originX, -originY, originZ);
        if (child.rotation != null) {
            final float rotationX = child.rotation.get(0).floatValue();
            final float rotationY = child.rotation.get(1).floatValue();
            final float rotationZ = child.rotation.get(2).floatValue();
            model.rotateAngleX = (float)Math.toRadians(-rotationX);
            model.rotateAngleY = (float)Math.toRadians(-rotationY);
            model.rotateAngleZ = (float)Math.toRadians(rotationZ);
        }
    }
    
    private void addMapping(final GeometryModelRenderer model, final Item item) {
        final int id = this.nameToIdMappings.size();
        final String name = item.name;
        this.nameToItemMappings.put(name, item);
        this.nameToModelMappings.put(name, model);
        this.nameToIdMappings.put(name, id);
        this.idToNameMappings.put(id, name);
        try {
            final GeometryEffect effect = EnumEffectType.createEffect(name, model);
            if (effect != null) {
                this.effects.add(effect);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public GeometryModelRenderer getModel(final String name) {
        return this.nameToModelMappings.get(name);
    }
    
    public Item getItem(final String name) {
        return this.nameToItemMappings.get(name);
    }
    
    public Map<String, Item> getItems() {
        return this.nameToItemMappings;
    }
    
    public GeometryModelRenderer getModel(final int id) {
        return this.getModel(this.idToNameMappings.get(id));
    }
    
    public int getModelId(final String name) {
        return this.nameToIdMappings.get(name);
    }
    
    public Collection<GeometryModelRenderer> getModels() {
        return this.nameToModelMappings.values();
    }
    
    public Map<String, BlockBenchCube> getCubes() {
        return this.cubes;
    }
    
    public BlockBench getBlockBench() {
        return this.blockBench;
    }
    
    public GeometryModelRenderer getModel() {
        return this.model;
    }
    
    public List<GeometryEffect> getEffects() {
        return this.effects;
    }
    
    public int getTotalPoseCount() {
        return this.nameToModelMappings.size();
    }
    
    public GeometryEffect getEffectByModel(final GeometryModelRenderer model) {
        for (final GeometryEffect effect : this.effects) {
            if (effect.getModel() == model) {
                return effect;
            }
        }
        return null;
    }
    
    static {
        GSON = new Gson();
    }
}
