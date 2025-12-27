package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CobblemonOreConfiguredFeatures {
   public final val ORE_DAWN_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("dawn_stone");
   public final val ORE_DUSK_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("dusk_stone");
   public final val ORE_FIRE_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("fire_stone");
   public final val ORE_FIRE_STONE_NETHER: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("nether_fire_stone");
   public final val ORE_ICE_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("ice_stone");
   public final val ORE_LEAF_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("leaf_stone");
   public final val ORE_MOON_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("moon_stone");
   public final val ORE_MOON_STONE_DRIPSTONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("dripstone_moon_stone");
   public final val ORE_SHINY_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("shiny_stone");
   public final val ORE_SUN_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("sun_stone");
   public final val ORE_THUNDER_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("thunder_stone");
   public final val ORE_WATER_STONE: ResourceKey<ConfiguredFeature<*, *>> = INSTANCE.of("water_stone");

   private fun of(id: String): ResourceKey<ConfiguredFeature<*, *>> {
      val var10000: ResourceKey = FeatureUtils.m_255087_("cobblemon:ore/$id");
      return var10000;
   }
}
