package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

public object CobblemonItemTags {
   public final val ABILITY_CHANGERS: TagKey<Item> = INSTANCE.create("ability_changers")
   public final val ANCIENT_POKE_BALLS: TagKey<Item> = INSTANCE.create("ancient_poke_balls")
   public final val ANY_HELD_ITEM: TagKey<Item> = INSTANCE.create("held/is_held_item")
   public final val APRICORNS: TagKey<Item> = INSTANCE.create("apricorns")
   public final val APRICORN_LOGS: TagKey<Item> = INSTANCE.create("apricorn_logs")
   public final val APRICORN_SPROUTS: TagKey<Item> = INSTANCE.create("apricorn_sprouts")
   public final val AZALEA_TREE: TagKey<Item> = INSTANCE.create("azalea_tree")
   public final val BERRIES: TagKey<Item> = INSTANCE.create("berries")
   public final val BOATS: TagKey<Item> = INSTANCE.create("boats")
   public final val COBBLEMON_SEEDS: TagKey<Item> = INSTANCE.create("cobblemon_seeds")
   public final val CONSUMED_IN_NPC_BATTLE: TagKey<Item> = INSTANCE.create("held/consumed_in_npc_battle")
   public final val CONSUMED_IN_PVP_BATTLE: TagKey<Item> = INSTANCE.create("held/consumed_in_pvp_battle")
   public final val CONSUMED_IN_WILD_BATTLE: TagKey<Item> = INSTANCE.create("held/consumed_in_wild_battle")
   public final val COOKED_MEAT: TagKey<Item> = INSTANCE.create("cooked_meat")
   public final val DAWN_STONE_ORES: TagKey<Item> = INSTANCE.create("dawn_stone_ores")
   public final val DESTINY_KNOT: TagKey<Item> = INSTANCE.create("held/destiny_knot")
   public final val DUSK_STONE_ORES: TagKey<Item> = INSTANCE.create("dusk_stone_ores")
   public final val EVERSTONE: TagKey<Item> = INSTANCE.create("held/everstone")
   public final val EVOLUTION_ITEMS: TagKey<Item> = INSTANCE.create("evolution_items")
   public final val EVOLUTION_STONES: TagKey<Item> = INSTANCE.create("evolution_stones")
   public final val EXPERIENCE_CANDIES: TagKey<Item> = INSTANCE.create("experience_candies")
   public final val EXPERIENCE_SHARE: TagKey<Item> = INSTANCE.create("held/experience_share")
   public final val FIRE_STONE_ORES: TagKey<Item> = INSTANCE.create("fire_stone_ores")
   public final val FOSSILS: TagKey<Item> = INSTANCE.create("fossils")
   public final val HANGING_SIGNS: TagKey<Item> = INSTANCE.create("hanging_signs")
   public final val HERBS: TagKey<Item> = INSTANCE.create("herbs")
   public final val ICE_STONE_ORES: TagKey<Item> = INSTANCE.create("ice_stone_ores")
   public final val IS_FRIENDSHIP_BOOSTER: TagKey<Item> = INSTANCE.create("is_friendship_booster")
   public final val LEAF_STONE_ORES: TagKey<Item> = INSTANCE.create("leaf_stone_ores")
   public final val LEAVES_LEFTOVERS: TagKey<Item> = INSTANCE.create("held/leaves_leftovers")
   public final val LUCKY_EGG: TagKey<Item> = INSTANCE.create("held/lucky_egg")
   public final val MINTS: TagKey<Item> = INSTANCE.create("mints")
   public final val MINT_LEAF: TagKey<Item> = INSTANCE.create("mint_leaf")
   public final val MINT_SEEDS: TagKey<Item> = INSTANCE.create("mint_seeds")
   public final val MOON_STONE_ORES: TagKey<Item> = INSTANCE.create("moon_stone_ores")
   public final val MUTATED_BERRIES: TagKey<Item> = INSTANCE.create("mutated_berries")
   public final val PLANTS: TagKey<Item> = INSTANCE.create("plants")
   public final val POKE_BALLS: TagKey<Item> = INSTANCE.create("poke_balls")
   public final val POTTERY_SHERDS: TagKey<Item> = INSTANCE.create("decorated_pot_sherds")
   public final val POWER_ANKLET: TagKey<Item> = INSTANCE.create("held/power_anklet")
   public final val POWER_BAND: TagKey<Item> = INSTANCE.create("held/power_band")
   public final val POWER_BELT: TagKey<Item> = INSTANCE.create("held/power_belt")
   public final val POWER_BRACER: TagKey<Item> = INSTANCE.create("held/power_bracer")
   public final val POWER_LENS: TagKey<Item> = INSTANCE.create("held/power_lens")
   public final val POWER_WEIGHT: TagKey<Item> = INSTANCE.create("held/power_weight")
   public final val PROTEIN_INGREDIENTS: TagKey<Item> = INSTANCE.create("protein_ingredients")
   public final val RAW_MEAT: TagKey<Item> = INSTANCE.create("raw_meat")
   public final val SEEDS: TagKey<Item> = INSTANCE.create("seeds")
   public final val SHINY_STONE_ORES: TagKey<Item> = INSTANCE.create("shiny_stone_ores")
   public final val SIGNS: TagKey<Item> = INSTANCE.create("signs")
   public final val SUN_STONE_ORES: TagKey<Item> = INSTANCE.create("sun_stone_ores")
   public final val THUNDER_STONE_ORES: TagKey<Item> = INSTANCE.create("thunder_stone_ores")
   public final val TUMBLESTONES: TagKey<Item> = INSTANCE.create("tumblestones")
   public final val WATER_STONE_ORES: TagKey<Item> = INSTANCE.create("water_stone_ores")
   public final val ZINC_INGREDIENTS: TagKey<Item> = INSTANCE.create("zinc_ingredients")

   private fun create(path: String): TagKey<Item> {
      return TagKey.m_203882_(Registries.f_256913_, MiscUtilsKt.cobblemonResource(path));
   }
}
