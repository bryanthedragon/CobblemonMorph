package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature

import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature

public object CobblemonConfiguredFeatures {
   public final val BERRY_GROVE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("berry_groves")
   public final val BIG_ROOTS_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("big_root")
   public final val BLACK_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("black_apricorn_tree")
   public final val BLUE_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("blue_apricorn_tree")
   public final val GREEN_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("green_apricorn_tree")
   public final val MEDICINAL_LEEKS_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("medicinal_leek")
   public final val MINTS_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("mints")
   public final val PINK_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("pink_apricorn_tree")
   public final val RED_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("red_apricorn_tree")
   public final val REVIVAL_HERBS_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("revival_herb")
   public final val WHITE_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("white_apricorn_tree")
   public final val YELLOW_APRICORN_TREE_KEY: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("yellow_apricorn_tree")

   private fun of(id: String): ResourceKey<ConfiguredFeature<*, *>> {
      val var10000: ResourceKey = FeatureUtils.m_255087_("cobblemon:$id");
      return var10000;
   }
}
