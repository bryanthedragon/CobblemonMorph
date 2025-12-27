package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;

import java.util.ArrayList;


import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CobblemonOrePlacedFeatures {
   public final val DAWN_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val DAWN_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val DAWN_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val DAWN_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val DUSK_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val DUSK_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val DUSK_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val DUSK_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val FIRE_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val FIRE_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val FIRE_STONE_NETHER: ResourceKey<PlacedFeature>
   public final val FIRE_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val FIRE_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val ICE_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val ICE_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val ICE_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val ICE_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val LEAF_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val LEAF_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val LEAF_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val LEAF_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val MOON_STONE_DRIPSTONE: ResourceKey<PlacedFeature>
   public final val MOON_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val MOON_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val MOON_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val MOON_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val SHINY_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val SHINY_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val SHINY_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val SHINY_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val SUN_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val SUN_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val SUN_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val SUN_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val THUNDER_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val THUNDER_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val THUNDER_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val THUNDER_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   public final val WATER_STONE_LOWER: ResourceKey<PlacedFeature>
   public final val WATER_STONE_LOWER_RARE: ResourceKey<PlacedFeature>
   public final val WATER_STONE_UPPER: ResourceKey<PlacedFeature>
   public final val WATER_STONE_UPPER_RARE: ResourceKey<PlacedFeature>
   private final val features: ArrayList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore.CobblemonOrePlacedFeatures.FeatureHolder> = new ArrayList()

   public fun register() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         Cobblemon.INSTANCE
            .getImplementation()
            .addFeatureToWorldGen(
               (`element$iv` as CobblemonOrePlacedFeatures.FeatureHolder).getFeature(),
               Decoration.UNDERGROUND_ORES,
               (`element$iv` as CobblemonOrePlacedFeatures.FeatureHolder).getValidBiomes()
            );
      }
   }

   private fun of(id: String, validBiomes: TagKey<Biome>): ResourceKey<PlacedFeature> {
      val feature: ResourceKey = PlacementUtils.m_255070_("cobblemon:ore/$id");
      val var10000: java.util.Collection = features;
      var10000.add(new CobblemonOrePlacedFeatures.FeatureHolder(feature, validBiomes));
      return feature;
   }

   @JvmStatic
   fun {
      var var10000: CobblemonOrePlacedFeatures = INSTANCE;
      var var10002: TagKey = CobblemonBiomeTags.HAS_DAWN_STONE_ORE;
      DAWN_STONE_UPPER = var10000.of("dawn_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE;
      DAWN_STONE_LOWER = var10000.of("dawn_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE_RARE;
      DAWN_STONE_UPPER_RARE = var10000.of("dawn_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DAWN_STONE_ORE_RARE;
      DAWN_STONE_LOWER_RARE = var10000.of("dawn_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE;
      DUSK_STONE_UPPER = var10000.of("dusk_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE;
      DUSK_STONE_LOWER = var10000.of("dusk_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE_RARE;
      DUSK_STONE_UPPER_RARE = var10000.of("dusk_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_DUSK_STONE_ORE_RARE;
      DUSK_STONE_LOWER_RARE = var10000.of("dusk_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE;
      FIRE_STONE_UPPER = var10000.of("fire_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE;
      FIRE_STONE_LOWER = var10000.of("fire_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_RARE;
      FIRE_STONE_UPPER_RARE = var10000.of("fire_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_RARE;
      FIRE_STONE_LOWER_RARE = var10000.of("fire_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_FIRE_STONE_ORE_NETHER;
      FIRE_STONE_NETHER = var10000.of("fire_stone_nether", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_ICE_STONE_ORE;
      ICE_STONE_UPPER = var10000.of("ice_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_ICE_STONE_ORE;
      ICE_STONE_LOWER = var10000.of("ice_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_ICE_STONE_ORE_RARE;
      ICE_STONE_UPPER_RARE = var10000.of("ice_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_ICE_STONE_ORE_RARE;
      ICE_STONE_LOWER_RARE = var10000.of("ice_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE;
      LEAF_STONE_UPPER = var10000.of("leaf_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE;
      LEAF_STONE_LOWER = var10000.of("leaf_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE_RARE;
      LEAF_STONE_UPPER_RARE = var10000.of("leaf_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_LEAF_STONE_ORE_RARE;
      LEAF_STONE_LOWER_RARE = var10000.of("leaf_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_MOON_STONE_ORE;
      MOON_STONE_UPPER = var10000.of("moon_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_MOON_STONE_ORE;
      MOON_STONE_LOWER = var10000.of("moon_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_RARE;
      MOON_STONE_UPPER_RARE = var10000.of("moon_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_RARE;
      MOON_STONE_LOWER_RARE = var10000.of("moon_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_MOON_STONE_ORE_DRIPSTONE;
      MOON_STONE_DRIPSTONE = var10000.of("moon_stone_dripstone", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE;
      SHINY_STONE_UPPER = var10000.of("shiny_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE;
      SHINY_STONE_LOWER = var10000.of("shiny_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE_RARE;
      SHINY_STONE_UPPER_RARE = var10000.of("shiny_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SHINY_STONE_ORE_RARE;
      SHINY_STONE_LOWER_RARE = var10000.of("shiny_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SUN_STONE_ORE;
      SUN_STONE_UPPER = var10000.of("sun_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SUN_STONE_ORE;
      SUN_STONE_LOWER = var10000.of("sun_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SUN_STONE_ORE_RARE;
      SUN_STONE_UPPER_RARE = var10000.of("sun_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_SUN_STONE_ORE_RARE;
      SUN_STONE_LOWER_RARE = var10000.of("sun_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE;
      THUNDER_STONE_UPPER = var10000.of("thunder_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE;
      THUNDER_STONE_LOWER = var10000.of("thunder_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE_RARE;
      THUNDER_STONE_UPPER_RARE = var10000.of("thunder_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_THUNDER_STONE_ORE_RARE;
      THUNDER_STONE_LOWER_RARE = var10000.of("thunder_stone_lower_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_WATER_STONE_ORE;
      WATER_STONE_UPPER = var10000.of("water_stone_upper", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_WATER_STONE_ORE;
      WATER_STONE_LOWER = var10000.of("water_stone_lower", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_WATER_STONE_ORE_RARE;
      WATER_STONE_UPPER_RARE = var10000.of("water_stone_upper_rare", var10002);
      var10000 = INSTANCE;
      var10002 = CobblemonBiomeTags.HAS_WATER_STONE_ORE_RARE;
      WATER_STONE_LOWER_RARE = var10000.of("water_stone_lower_rare", var10002);
   }

   private data class FeatureHolder(feature: ResourceKey<PlacedFeature>, validBiomes: TagKey<Biome>) {
      public final val feature: ResourceKey<PlacedFeature>
      public final val validBiomes: TagKey<Biome>

      init {
         this.feature = feature;
         this.validBiomes = validBiomes;
      }

      public operator fun component1(): ResourceKey<PlacedFeature> {
         return this.feature;
      }

      public operator fun component2(): TagKey<Biome> {
         return this.validBiomes;
      }

      public fun copy(feature: ResourceKey<PlacedFeature> = this.feature, validBiomes: TagKey<Biome> = this.validBiomes): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore.CobblemonOrePlacedFeatures.FeatureHolder {
         return new CobblemonOrePlacedFeatures.FeatureHolder(feature, validBiomes);
      }

      public override fun toString(): String {
         return "FeatureHolder(feature=${this.feature}, validBiomes=${this.validBiomes})";
      }

      public override fun hashCode(): Int {
         return this.feature.hashCode() * 31 + this.validBiomes.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is CobblemonOrePlacedFeatures.FeatureHolder) {
            return false;
         } else {
            val var2: CobblemonOrePlacedFeatures.FeatureHolder = other as CobblemonOrePlacedFeatures.FeatureHolder;
            if (!(this.feature == (other as CobblemonOrePlacedFeatures.FeatureHolder).feature)) {
               return false;
            } else {
               return this.validBiomes == var2.validBiomes;
            }
         }
      }
   }
}
