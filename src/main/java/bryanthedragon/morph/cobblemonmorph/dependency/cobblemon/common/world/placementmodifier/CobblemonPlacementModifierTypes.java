package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.serialization.Codec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import net.minecraft.world.level.levelgen.placement.PlacementModifierType

public object CobblemonPlacementModifierTypes {
   public final val BENEATH_HEIGHTMAP: PlacementModifierType<BeneathHeightmapPlacementModifier> =
      INSTANCE.register("beneath_heightmap", BeneathHeightmapPlacementModifier.Companion.getMODIFIER_CODEC())
      public final val CONDITIONAL_COUNT: PlacementModifierType<ConditionalCountPlacementModifier> =
      INSTANCE.register("conditional_count", ConditionalCountPlacementModifier.Companion.getMODIFIER_CODEC())
      public final val CONDITIONAL_RARITY_FILTER: PlacementModifierType<ConditionalRarityFilterPlacementModifier> =
      INSTANCE.register("conditional_rarity_filter", ConditionalRarityFilterPlacementModifier.Companion.getMODIFIER_CODEC())
      public final val LOCATE_PREDICATE: PlacementModifierType<LocatePredicatePlacementModifier> =
      INSTANCE.register("locate_predicate", LocatePredicatePlacementModifier.Companion.getMODIFIER_CODEC())

   public fun <T : PlacementModifier> register(id: String, codec: Codec<Any>): PlacementModifierType<Any> {
      val var10000: Any = Registry.m_122965_(BuiltInRegistries.f_256986_, MiscUtilsKt.cobblemonResource(id), CobblemonPlacementModifierTypes::register$lambda$0);
      return var10000 as PlacementModifierType<T>;
   }

   public fun touch() {
   }

   @JvmStatic
   fun `register$lambda$0`(`$codec`: Codec): Codec {
      return `$codec`;
   }
}
