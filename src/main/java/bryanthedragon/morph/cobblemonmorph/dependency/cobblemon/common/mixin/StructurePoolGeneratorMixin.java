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
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement.PieceState;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement.Placer;
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

@Mixin(Placer.class)
public abstract class StructurePoolGeneratorMixin {
   @Final
   @Mutable
   @Shadow
   Deque<PieceState> f_210321_;
   Map<String, Integer> generatedStructureGroupCounts;
   private static final Map<String, Integer> structureMaxes;
   private static final Map<ResourceLocation, Set<String>> structureGroups = new HashMap<>();

   public Set<String> getGroups(ResourceLocation structureIdentifier) {
      return structureGroups.getOrDefault(structureIdentifier, Set.of());
   }

   public boolean hasReachedMaximum(ResourceLocation structureIdentifier) {
      for (String group : this.getGroups(structureIdentifier)) {
         int count = this.generatedStructureGroupCounts.getOrDefault(group, 0);
         if (count >= structureMaxes.getOrDefault(group, Integer.MAX_VALUE)) {
            return true;
         }
      }

      return false;
   }

   public void incrementStructureCount(ResourceLocation structureIdentifier) {
      for (String group : this.getGroups(structureIdentifier)) {
         this.generatedStructureGroupCounts.put(group, this.generatedStructureGroupCounts.getOrDefault(group, 0) + 1);
      }
   }

   @Inject(method = "<init>", at = @At("RETURN"))
   private void onStructurePoolGeneratorCreation(
      Registry<StructureTemplatePool> registry,
      int maxSize,
      ChunkGenerator chunkGenerator,
      StructureTemplateManager structureTemplateManager,
      List<? super PoolElementStructurePiece> children,
      RandomSource random,
      CallbackInfo ci
   ) {
      this.generatedStructureGroupCounts = new HashMap<>();
   }

   @ModifyVariable(method = "generatePiece", at = @At("STORE"), ordinal = 1)
   private Iterator<StructurePoolElement> reduceStructurePoolElementIterator(Iterator<StructurePoolElement> iterator) {
      List<StructurePoolElement> reducedList = new ArrayList<>();

      while (iterator.hasNext()) {
         StructurePoolElement structure = iterator.next();
         ResourceLocation structurePieceLocationKey = getCobblemonOnlyLocation(structure);
         if (structurePieceLocationKey == null) {
            reducedList.add(structure);
         } else if (!this.hasReachedMaximum(structurePieceLocationKey)) {
            reducedList.add(structure);
         }
      }

      return reducedList.iterator();
   }

   @ModifyVariable(method = "generatePiece", at = @At("STORE"), ordinal = 1)
   private PoolElementStructurePiece injected(PoolElementStructurePiece poolStructurePiece) {
      ResourceLocation structureLocationKey = getCobblemonOnlyLocation(poolStructurePiece.m_209918_());
      if (structureLocationKey != null) {
         this.incrementStructureCount(structureLocationKey);
      }

      return poolStructurePiece;
   }

   @Inject(method = "generatePiece", at = @At("HEAD"))
   private void beforeGeneratePiece(
      PoolElementStructurePiece piece,
      MutableObject<VoxelShape> pieceShape,
      int minY,
      boolean modifyBoundingBox,
      LevelHeightAccessor world,
      RandomState noiseConfig,
      CallbackInfo ci
   ) {
   }

   private static ResourceLocation getCobblemonOnlyLocation(StructurePoolElement structurePoolElement) {
      ResourceLocation location = getLocationIfAvailable(structurePoolElement);
      if (location == null) {
         return null;
      } else {
         return !location.m_135827_().equals("cobblemon") ? null : location;
      }
   }

   private static ResourceLocation getLocationIfAvailable(StructurePoolElement structurePoolElement) {
      if (structurePoolElement instanceof LegacySinglePoolElement legacySinglePoolElement) {
         return legacySinglePoolElement.f_210411_.left().isEmpty() ? null : (ResourceLocation)legacySinglePoolElement.f_210411_.left().get();
      } else if (structurePoolElement instanceof SinglePoolElement singlePoolElement) {
         return singlePoolElement.f_210411_.left().isEmpty() ? null : (ResourceLocation)singlePoolElement.f_210411_.left().get();
      } else {
         return null;
      }
   }

   static {
      String pokecenter = "pokecenter";
      String berry = "berry_farm";
      structureGroups.put(CobblemonStructureIDs.PLAINS_POKECENTER, Set.of(pokecenter));
      structureGroups.put(CobblemonStructureIDs.DESERT_POKECENTER, Set.of(pokecenter));
      structureGroups.put(CobblemonStructureIDs.SAVANNA_POKECENTER, Set.of(pokecenter));
      structureGroups.put(CobblemonStructureIDs.SNOWY_POKECENTER, Set.of(pokecenter));
      structureGroups.put(CobblemonStructureIDs.TAIGA_POKECENTER, Set.of(pokecenter));
      structureGroups.put(CobblemonStructureIDs.SAVANNA_BERRY_SMALL, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.SAVANNA_BERRY_LARGE, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.PLAINS_BERRY_SMALL, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.PLAINS_BERRY_LARGE, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.SNOWY_BERRY_SMALL, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.SNOWY_BERRY_LARGE, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.DESERT_BERRY_SMALL, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.DESERT_BERRY_LARGE, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.TAIGA_BERRY_SMALL, Set.of(berry));
      structureGroups.put(CobblemonStructureIDs.TAIGA_BERRY_LARGE, Set.of(berry));
      Map<String, Integer> aMap = new HashMap<>();
      aMap.put(pokecenter, 1);
      aMap.put(berry, 2);
      structureMaxes = Collections.unmodifiableMap(aMap);
   }
}
