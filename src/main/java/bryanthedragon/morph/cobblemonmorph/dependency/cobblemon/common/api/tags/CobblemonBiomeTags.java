package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome

public object CobblemonBiomeTags {
   public final val HAS_APRICORNS_DENSE: TagKey<Biome> = INSTANCE.create("has_feature/apricorns_dense")
   public final val HAS_APRICORNS_NORMAL: TagKey<Biome> = INSTANCE.create("has_feature/apricorns_normal")
   public final val HAS_APRICORNS_SPARSE: TagKey<Biome> = INSTANCE.create("has_feature/apricorns_sparse")
   public final val HAS_DAWN_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_dawn_stone_normal")
   public final val HAS_DAWN_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_dawn_stone_rare")
   public final val HAS_DUSK_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_dusk_stone_normal")
   public final val HAS_DUSK_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_dusk_stone_rare")
   public final val HAS_FIRE_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_fire_stone_normal")
   public final val HAS_FIRE_STONE_ORE_NETHER: TagKey<Biome> = INSTANCE.create("has_ore/ore_fire_stone_nether")
   public final val HAS_FIRE_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_fire_stone_rare")
   public final val HAS_ICE_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_ice_stone_normal")
   public final val HAS_ICE_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_ice_stone_rare")
   public final val HAS_LEAF_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_leaf_stone_normal")
   public final val HAS_LEAF_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_leaf_stone_rare")
   public final val HAS_MOON_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_moon_stone_normal")
   public final val HAS_MOON_STONE_ORE_DRIPSTONE: TagKey<Biome> = INSTANCE.create("has_ore/ore_moon_stone_dripstone")
   public final val HAS_MOON_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_moon_stone_rare")
   public final val HAS_REVIVAL_HERBS: TagKey<Biome> = INSTANCE.create("has_feature/revival_herbs")
   public final val HAS_SHINY_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_shiny_stone_normal")
   public final val HAS_SHINY_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_shiny_stone_rare")
   public final val HAS_SUN_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_sun_stone_normal")
   public final val HAS_SUN_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_sun_stone_rare")
   public final val HAS_THUNDER_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_thunder_stone_normal")
   public final val HAS_THUNDER_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_thunder_stone_rare")
   public final val HAS_WATER_STONE_ORE: TagKey<Biome> = INSTANCE.create("has_ore/ore_water_stone_normal")
   public final val HAS_WATER_STONE_ORE_RARE: TagKey<Biome> = INSTANCE.create("has_ore/ore_water_stone_rare")
   public final val IS_AUTUMN: TagKey<Biome> = INSTANCE.create("has_season/autumn")
   public final val IS_SPRING: TagKey<Biome> = INSTANCE.create("has_season/spring")
   public final val IS_SUMMER: TagKey<Biome> = INSTANCE.create("has_season/summer")
   public final val IS_TEMPERATE: TagKey<Biome> = INSTANCE.create("is_temperate")
   public final val IS_WINTER: TagKey<Biome> = INSTANCE.create("has_season/winter")

   private fun create(path: String): TagKey<Biome> {
      return TagKey.m_203882_(Registries.f_256952_, MiscUtilsKt.cobblemonResource(path));
   }
}
