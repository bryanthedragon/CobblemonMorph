package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.world.level.levelgen.GenerationStep.Decoration
import net.minecraft.world.level.levelgen.placement.PlacedFeature

public object CobblemonPlacedFeatures {
   public final val APRICORN_TREES: ResourceKey<PlacedFeature> = INSTANCE.of("apricorn_trees")
   public final val BERRY_GROVE: ResourceKey<PlacedFeature> = INSTANCE.of("berry_groves")
   public final val BIG_ROOT: ResourceKey<PlacedFeature> = INSTANCE.of("big_root")
   public final val BLACK_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("black_apricorn_tree")
   public final val BLUE_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("blue_apricorn_tree")
   public final val GREEN_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("green_apricorn_tree")
   public final val MEDICINAL_LEEK: ResourceKey<PlacedFeature> = INSTANCE.of("medicinal_leek")
   public final val MINTS: ResourceKey<PlacedFeature> = INSTANCE.of("mints")
   public final val PINK_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("pink_apricorn_tree")
   public final val RED_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("red_apricorn_tree")
   public final val REVIVAL_HERB: ResourceKey<PlacedFeature> = INSTANCE.of("revival_herb")
   public final val WHITE_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("white_apricorn_tree")
   public final val YELLOW_APRICORN_TREE_PLACED_FEATURE: ResourceKey<PlacedFeature> = INSTANCE.of("yellow_apricorn_tree")

   public fun register() {
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(APRICORN_TREES, Decoration.VEGETAL_DECORATION, null);
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(MINTS, Decoration.VEGETAL_DECORATION, null);
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(MEDICINAL_LEEK, Decoration.VEGETAL_DECORATION, null);
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(BIG_ROOT, Decoration.VEGETAL_DECORATION, BiomeTags.f_215817_);
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(REVIVAL_HERB, Decoration.VEGETAL_DECORATION, CobblemonBiomeTags.HAS_REVIVAL_HERBS);
      Cobblemon.INSTANCE.getImplementation().addFeatureToWorldGen(BERRY_GROVE, Decoration.VEGETAL_DECORATION, BiomeTags.f_215817_);
   }

   private fun of(id: String): ResourceKey<PlacedFeature> {
      val var10000: ResourceKey = PlacementUtils.m_255070_("cobblemon:$id");
      return var10000;
   }
}
