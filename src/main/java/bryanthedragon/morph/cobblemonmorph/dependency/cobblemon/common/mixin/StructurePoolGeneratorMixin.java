/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.LevelHeightAccessor
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.RandomState
 *  net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece
 *  net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$PieceState
 *  net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer
 *  net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.CobblemonStructureIDs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={JigsawPlacement.Placer.class})
public abstract class StructurePoolGeneratorMixin {
    @Final
    @Mutable
    @Shadow
    Deque<JigsawPlacement.PieceState> f_210321_;
    Map<String, Integer> generatedStructureGroupCounts;
    private static final Map<String, Integer> structureMaxes;
    private static final Map<ResourceLocation, Set<String>> structureGroups;

    public Set<String> getGroups(ResourceLocation structureIdentifier) {
        return structureGroups.getOrDefault(structureIdentifier, Set.of());
    }

    public boolean hasReachedMaximum(ResourceLocation structureIdentifier) {
        Set<String> groups = this.getGroups(structureIdentifier);
        for (String group : groups) {
            int count = this.generatedStructureGroupCounts.getOrDefault(group, 0);
            if (count < structureMaxes.getOrDefault(group, Integer.MAX_VALUE)) continue;
            return true;
        }
        return false;
    }

    public void incrementStructureCount(ResourceLocation structureIdentifier) {
        Set<String> groups = this.getGroups(structureIdentifier);
        for (String group : groups) {
            this.generatedStructureGroupCounts.put(group, this.generatedStructureGroupCounts.getOrDefault(group, 0) + 1);
        }
    }

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void onStructurePoolGeneratorCreation(Registry<StructureTemplatePool> registry, int maxSize, ChunkGenerator chunkGenerator, StructureTemplateManager structureTemplateManager, List<? super PoolElementStructurePiece> children, RandomSource random, CallbackInfo ci) {
        this.generatedStructureGroupCounts = new HashMap<String, Integer>();
    }

    @ModifyVariable(method={"generatePiece"}, at=@At(value="STORE"), ordinal=1)
    private Iterator<StructurePoolElement> reduceStructurePoolElementIterator(Iterator<StructurePoolElement> iterator) {
        ArrayList<StructurePoolElement> reducedList = new ArrayList<StructurePoolElement>();
        while (iterator.hasNext()) {
            StructurePoolElement structure = iterator.next();
            ResourceLocation structurePieceLocationKey = StructurePoolGeneratorMixin.getCobblemonOnlyLocation(structure);
            if (structurePieceLocationKey == null) {
                reducedList.add(structure);
                continue;
            }
            if (this.hasReachedMaximum(structurePieceLocationKey)) continue;
            reducedList.add(structure);
        }
        return reducedList.iterator();
    }

    @ModifyVariable(method={"generatePiece"}, at=@At(value="STORE"), ordinal=1)
    private PoolElementStructurePiece injected(PoolElementStructurePiece poolStructurePiece) {
        ResourceLocation structureLocationKey = StructurePoolGeneratorMixin.getCobblemonOnlyLocation(poolStructurePiece.m_209918_());
        if (structureLocationKey != null) {
            this.incrementStructureCount(structureLocationKey);
        }
        return poolStructurePiece;
    }

    @Inject(method={"generatePiece"}, at={@At(value="HEAD")})
    private void beforeGeneratePiece(PoolElementStructurePiece piece, MutableObject<VoxelShape> pieceShape, int minY, boolean modifyBoundingBox, LevelHeightAccessor world, RandomState noiseConfig, CallbackInfo ci) {
    }

    private static ResourceLocation getCobblemonOnlyLocation(StructurePoolElement structurePoolElement) {
        ResourceLocation location = StructurePoolGeneratorMixin.getLocationIfAvailable(structurePoolElement);
        if (location == null) {
            return null;
        }
        if (!location.m_135827_().equals("cobblemon")) {
            return null;
        }
        return location;
    }

    private static ResourceLocation getLocationIfAvailable(StructurePoolElement structurePoolElement) {
        if (structurePoolElement instanceof LegacySinglePoolElement) {
            LegacySinglePoolElement legacySinglePoolElement = (LegacySinglePoolElement)structurePoolElement;
            if (legacySinglePoolElement.f_210411_.left().isEmpty()) {
                return null;
            }
            return (ResourceLocation)legacySinglePoolElement.f_210411_.left().get();
        }
        if (structurePoolElement instanceof SinglePoolElement) {
            SinglePoolElement singlePoolElement = (SinglePoolElement)structurePoolElement;
            if (singlePoolElement.f_210411_.left().isEmpty()) {
                return null;
            }
            return (ResourceLocation)singlePoolElement.f_210411_.left().get();
        }
        return null;
    }

    static {
        structureGroups = new HashMap<ResourceLocation, Set<String>>();
        String pokecenter = "pokecenter";
        String berry = "berry_farm";
        structureGroups.put(CobblemonStructureIDs.PLAINS_POKECENTER, Set.of((Object)pokecenter));
        structureGroups.put(CobblemonStructureIDs.DESERT_POKECENTER, Set.of((Object)pokecenter));
        structureGroups.put(CobblemonStructureIDs.SAVANNA_POKECENTER, Set.of((Object)pokecenter));
        structureGroups.put(CobblemonStructureIDs.SNOWY_POKECENTER, Set.of((Object)pokecenter));
        structureGroups.put(CobblemonStructureIDs.TAIGA_POKECENTER, Set.of((Object)pokecenter));
        structureGroups.put(CobblemonStructureIDs.SAVANNA_BERRY_SMALL, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.SAVANNA_BERRY_LARGE, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.PLAINS_BERRY_SMALL, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.PLAINS_BERRY_LARGE, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.SNOWY_BERRY_SMALL, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.SNOWY_BERRY_LARGE, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.DESERT_BERRY_SMALL, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.DESERT_BERRY_LARGE, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.TAIGA_BERRY_SMALL, Set.of((Object)berry));
        structureGroups.put(CobblemonStructureIDs.TAIGA_BERRY_LARGE, Set.of((Object)berry));
        HashMap<String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put(pokecenter, 1);
        aMap.put(berry, 2);
        structureMaxes = Collections.unmodifiableMap(aMap);
    }
}

