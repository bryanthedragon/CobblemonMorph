package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.StructurePoolAccessor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonStructureProcessorLists
import com.mojang.datafixers.util.Pair
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.Holder.Reference
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.ProcessorLists
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList

@SourceDebugExtension(["SMAP\nCobblemonStructures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonStructures.kt\ncom/cobblemon/mod/common/world/CobblemonStructures\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,311:1\n1#2:312\n*E\n"])
public object CobblemonStructures {
   private final val EMPTY_PROCESSOR_LIST_KEY: ResourceKey<StructureProcessorList> =
      ResourceKey.m_135785_(Registries.f_257011_, new ResourceLocation("minecraft", "empty"))
      private const val berryFarmWeight: Int = 1
   public final val desertHousesPoolLocation: ResourceLocation = new ResourceLocation("minecraft", "village/desert/houses")
   private const val longPathWeight: Int = 10
   public final val plainsHousesPoolLocation: ResourceLocation = new ResourceLocation("minecraft", "village/plains/houses")
   private const val pokecenterWeight: Int = 35
   public final val savannaHousesPoolLocation: ResourceLocation = new ResourceLocation("minecraft", "village/savanna/houses")
   public final val snowyHousesPoolLocation: ResourceLocation = new ResourceLocation("minecraft", "village/snowy/houses")
   public final val taigaHousesPoolLocation: ResourceLocation = new ResourceLocation("minecraft", "village/taiga/houses")

   public fun registerJigsaws(server: MinecraftServer) {
      val templatePoolRegistry: Registry = server.m_206579_().m_175515_(Registries.f_256948_);
      val processorListRegistry: Registry = server.m_206579_().m_175515_(Registries.f_257011_);
      this.addBerryFarms(templatePoolRegistry, processorListRegistry);
   }

   public fun addBerryFarms(templatePoolRegistry: Registry<StructureTemplatePool>, processorListRegistry: Registry<StructureProcessorList>) {
      val cropToBerryProcessorList: ResourceKey = CobblemonStructureProcessorLists.CROP_TO_BERRY;
      val var10003: ResourceLocation = plainsHousesPoolLocation;
      val var10004: ResourceLocation = CobblemonStructureIDs.PLAINS_BERRY_SMALL;
      val var10006: Projection = Projection.RIGID;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 1, var10006, cropToBerryProcessorList, false, 128, null);
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         plainsHousesPoolLocation,
         CobblemonStructureIDs.PLAINS_BERRY_LARGE,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         desertHousesPoolLocation,
         CobblemonStructureIDs.DESERT_BERRY_SMALL,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         desertHousesPoolLocation,
         CobblemonStructureIDs.DESERT_BERRY_LARGE,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         savannaHousesPoolLocation,
         CobblemonStructureIDs.SAVANNA_BERRY_SMALL,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         savannaHousesPoolLocation,
         CobblemonStructureIDs.SAVANNA_BERRY_LARGE,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         taigaHousesPoolLocation,
         CobblemonStructureIDs.TAIGA_BERRY_SMALL,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         taigaHousesPoolLocation,
         CobblemonStructureIDs.TAIGA_BERRY_LARGE,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         snowyHousesPoolLocation,
         CobblemonStructureIDs.SNOWY_BERRY_SMALL,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
      addBuildingToPool$default(
         this,
         templatePoolRegistry,
         processorListRegistry,
         snowyHousesPoolLocation,
         CobblemonStructureIDs.SNOWY_BERRY_LARGE,
         1,
         Projection.RIGID,
         cropToBerryProcessorList,
         false,
         128,
         null
      );
   }

   private fun addPokecenters(templatePoolRegistry: Registry<StructureTemplatePool>, processorListRegistry: Registry<StructureProcessorList>) {
      var var10003: ResourceLocation = plainsHousesPoolLocation;
      var var10004: ResourceLocation = CobblemonStructureIDs.PLAINS_POKECENTER;
      var var10006: Projection = Projection.RIGID;
      var var10007: ResourceKey = EMPTY_PROCESSOR_LIST_KEY;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 35, var10006, var10007, false, 128, null);
      var10003 = desertHousesPoolLocation;
      var10004 = CobblemonStructureIDs.DESERT_POKECENTER;
      var10006 = Projection.RIGID;
      var10007 = EMPTY_PROCESSOR_LIST_KEY;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 35, var10006, var10007, false, 128, null);
      var10003 = savannaHousesPoolLocation;
      var10004 = CobblemonStructureIDs.SAVANNA_POKECENTER;
      var10006 = Projection.RIGID;
      var10007 = EMPTY_PROCESSOR_LIST_KEY;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 35, var10006, var10007, false, 128, null);
      var10003 = snowyHousesPoolLocation;
      var10004 = CobblemonStructureIDs.SNOWY_POKECENTER;
      var10006 = Projection.RIGID;
      var10007 = EMPTY_PROCESSOR_LIST_KEY;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 35, var10006, var10007, false, 128, null);
      var10003 = taigaHousesPoolLocation;
      var10004 = CobblemonStructureIDs.TAIGA_POKECENTER;
      var10006 = Projection.RIGID;
      var10007 = ProcessorLists.f_127204_;
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, var10003, var10004, 35, var10006, var10007, false, 128, null);
   }

   private fun addLongPaths(templatePoolRegistry: Registry<StructureTemplatePool>, processorListRegistry: Registry<StructureProcessorList>) {
      val plainsStreetsPoolLocation: ResourceLocation = new ResourceLocation("minecraft:village/plains/streets");
      val desertStreetsPoolLocation: ResourceLocation = new ResourceLocation("minecraft:village/desert/streets");
      val savannaStreetsPoolLocation: ResourceLocation = new ResourceLocation("minecraft:village/savanna/streets");
      val snowyStreetsPoolLocation: ResourceLocation = new ResourceLocation("minecraft:village/snowy/streets");
      val taigaStreetsPoolLocation: ResourceLocation = new ResourceLocation("minecraft:village/taiga/streets");
      var var10004: ResourceLocation = CobblemonStructureIDs.PLAINS_LONG_PATH;
      var var10006: Projection = Projection.TERRAIN_MATCHING;
      var var10007: ResourceKey = ProcessorLists.f_127207_;
      this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, plainsStreetsPoolLocation, var10004, 10, var10006, var10007);
      var10004 = CobblemonStructureIDs.DESERT_LONG_PATH;
      var10006 = Projection.TERRAIN_MATCHING;
      var10007 = EMPTY_PROCESSOR_LIST_KEY;
      this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, desertStreetsPoolLocation, var10004, 10, var10006, var10007);
      var10004 = CobblemonStructureIDs.SAVANNA_LONG_PATH;
      var10006 = Projection.TERRAIN_MATCHING;
      var10007 = ProcessorLists.f_127208_;
      this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, savannaStreetsPoolLocation, var10004, 10, var10006, var10007);
      var10004 = CobblemonStructureIDs.SNOWY_LONG_PATH;
      var10006 = Projection.TERRAIN_MATCHING;
      var10007 = ProcessorLists.f_127209_;
      this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, snowyStreetsPoolLocation, var10004, 10, var10006, var10007);
      var10004 = CobblemonStructureIDs.TAIGA_LONG_PATH;
      var10006 = Projection.TERRAIN_MATCHING;
      var10007 = ProcessorLists.f_127209_;
      this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, taigaStreetsPoolLocation, var10004, 10, var10006, var10007);
   }

   public fun addLegacyBuildingToPool(
      templatePoolRegistry: Registry<StructureTemplatePool>,
      processorListRegistry: Registry<StructureProcessorList>,
      poolRL: ResourceLocation,
      nbtPieceRL: ResourceLocation,
      weight: Int,
      projection: net.minecraft.structure.pool.StructurePool.Projection,
      processorListKey: ResourceKey<StructureProcessorList>
   ) {
      this.addBuildingToPool(templatePoolRegistry, processorListRegistry, poolRL, nbtPieceRL, weight, projection, processorListKey, true);
   }

   @JvmOverloads
   public fun addBuildingToPool(
      templatePoolRegistry: Registry<StructureTemplatePool>,
      processorListRegistry: Registry<StructureProcessorList>,
      poolRL: ResourceLocation,
      nbtPieceRL: ResourceLocation,
      weight: Int,
      projection: net.minecraft.structure.pool.StructurePool.Projection,
      processorListKey: ResourceKey<StructureProcessorList>,
      shouldUseLegacySingePoolElement: Boolean = ...
   ) {
      if (!processorListRegistry.m_203636_(processorListKey).isEmpty()) {
         var var10000: Any = processorListRegistry.m_203636_(processorListKey).get();
         val processorList: Reference = var10000 as Reference;
         var listOfPieceEntries: ArrayList = (ArrayList)templatePoolRegistry.m_7745_(poolRL);
         var10000 = listOfPieceEntries as? StructurePoolAccessor;
         if ((listOfPieceEntries as? StructurePoolAccessor) != null) {
            val pool: StructurePoolAccessor = (StructurePoolAccessor)var10000;
            val piece: SinglePoolElement = if (shouldUseLegacySingePoolElement)
               LegacySinglePoolElement.m_210512_(nbtPieceRL.toString(), processorList as Holder).apply(projection) as SinglePoolElement
               else
               SinglePoolElement.m_210531_(nbtPieceRL.toString(), processorList as Holder).apply(projection) as SinglePoolElement;

            for (int var15 = 0; var15 < weight; var15++) {
               pool.getElements().add(piece);
            }

            listOfPieceEntries = new ArrayList<>(pool.getElementCounts());
            listOfPieceEntries.add(new Pair(piece, weight));
            pool.getElements().add(piece);
            pool.setElementCounts(listOfPieceEntries);
         }
      }
   }

   @JvmOverloads
   fun addBuildingToPool(
      templatePoolRegistry: Registry<StructureTemplatePool>,
      processorListRegistry: Registry<StructureProcessorList>,
      poolRL: ResourceLocation,
      nbtPieceRL: ResourceLocation,
      weight: Int,
      projection: Projection,
      processorListKey: ResourceKey<StructureProcessorList>
   ) {
      addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, poolRL, nbtPieceRL, weight, projection, processorListKey, false, 128, null);
   }
}
