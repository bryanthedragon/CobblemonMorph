/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;

/**
 * A collection of the Cobblemon [TagKey]s related to the [Registries.ITEM].
 *
 * @author Licious
 * @since January 8th, 2023
 */
public final class CobblemonItemTags {
    public final ABILITY_CHANGERS = create("ability_changers");
    public final ANCIENT_POKE_BALLS = create("ancient_poke_balls");
    public final APPLES = create("apples");
    public final APRICORN_LOGS = create("apricorn_logs");
    public final APRICORN_POKE_BALLS = create("apricorn_poke_balls");
    public final APRICORN_SPROUTS = create("apricorn_sprouts");
    public final APRICORNS = create("apricorns");
    public final APRIJUICES = create("aprijuices");

    /** This tag is only used for a Torterra aspect based easter egg evolution at the moment. It simply includes the 'minecraft:azalea' and 'minecraft:flowering_azalea' items by default. */
    public final AZALEA_TREE = create("azalea_tree");
    public final BATTLE_ITEMS = create("battle_items");
    public final BERRIES = create("berries");
    public final BLACK_TUMBLESTONE_BRICKS = create("black_tumblestone_bricks");
    public final BLACK_TUMBLESTONES = create("black_tumblestones");
    public final BOATS = create("boats");

    /** This tag is used for Fossil Machine natural materials */
    public final COOKED_MEAT = create("cooked_meat");
    public final DAWN_STONE_ORES = create("dawn_stone_ores");
    public final POTTERY_SHERDS = create("decorated_pot_sherds");
    public final DEEP_SEAS = create("deep_seas");
    public final DUSK_STONE_ORES = create("dusk_stone_ores");
    public final ETHERS = create("ethers");
    public final EVOLUTION_ITEMS = create("evolution_items");
    public final EVOLUTION_STONE_BLOCKS = create("evolution_stone_blocks");
    public final EVOLUTION_STONES = create("evolution_stones");
    public final EXPERIENCE_CANDIES = create("experience_candies");
    public final FEATHERS = create("feathers");
    public final FIRE_STONE_ORES = create("fire_stone_ores");
    public final FOSSIL_MACHINE_PARTS = create("fossil_machine_parts");
    public final FOSSILS = create("fossils");
    public final FULL_HEAL_INGREDIENTS = create("full_heal_ingredients");
    public final GILDED_CHESTS = create("gilded_chests");
    public final HANGING_SIGNS = create("hanging_signs");
    public final HERBS = create("herbs");
    public final ICE_STONE_ORES = create("ice_stone_ores");
    public final LEAF_STONE_ORES = create("leaf_stone_ores");
    public final MACHINES = create("machines");
    public final MINT_LEAF = create("mint_leaf");
    public final MINT_SEEDS = create("mint_seeds");
    public final MINTS = create("mints");
    public final MOCHIS = create("mochis");
    public final MOON_STONE_ORES = create("moon_stone_ores");
    public final MUTATED_BERRIES = create("mutated_berries");
    public final PLANTS = create("plants");
    public final POKE_BALLS = create("poke_balls");
    public final POKE_RODS = create("poke_rods");
    public final POKEDEX = create("pokedex");
    public final POKEDEX_SCREEN = create("pokedex_screen");
    public final POKE_FOOD = create("poke_food");
    public final POTIONS = create("potions");
    public final PROTEIN_INGREDIENTS = create("protein_ingredients");

    /** See [COOKED_MEAT] */
    public final RAW_MEAT = create("raw_meat");
    public final RED_TUMBLESTONE_BRICKS = create("red_tumblestone_bricks");
    public final RED_TUMBLESTONES = create("red_tumblestones");
    public final REMEDIES = create("remedies");
    public final REMEDY_BERRIES = create("remedy_berries");
    public final RESTORES = create("restores");
    public final REVIVES = create("revives");
    public final SANDWICH_VEGGIE = create("sandwich_veggies");
    public final SEEDS = create("seeds");
    public final SHINY_STONE_ORES = create("shiny_stone_ores");
    public final SIGNS = create("signs");
    public final SKY_TUMBLESTONE_BRICKS = create("sky_tumblestone_bricks");
    public final SKY_TUMBLESTONES = create("sky_tumblestones");
    public final SUN_STONE_ORES = create("sun_stone_ores");
    public final SUPER_POTION_INGREDIENTS = create("super_potion_ingredients");
    public final FULL_HEAL_BOTTLES = create("full_heal_bottles");
    public final SWEETS = create("sweets");
    public final TEACUPS = create("teacups");
    public final TEAPOTS = create("teapots");
    public final THUNDER_STONE_ORES = create("thunder_stone_ores");
    public final TIER_1_POKE_BALL_MATERIALS = create("tier_1_poke_ball_materials");
    public final TIER_1_POKE_BALLS = create("tier_1_poke_balls");
    public final TIER_2_POKE_BALL_MATERIALS = create("tier_2_poke_ball_materials");
    public final TIER_2_POKE_BALLS = create("tier_2_poke_balls");
    public final TIER_3_POKE_BALL_MATERIALS = create("tier_3_poke_ball_materials");
    public final TIER_3_POKE_BALLS = create("tier_3_poke_balls");
    public final TIER_4_POKE_BALL_MATERIALS = create("tier_4_poke_ball_materials");
    public final TIER_4_POKE_BALLS = create("tier_4_poke_balls");
    public final TIER_5_POKE_BALLS = create("tier_5_poke_balls");
    public final TUMBLESTONE_BRICKS = create("tumblestone_bricks");
    public final TUMBLESTONES = create("tumblestones");
    public final TYPE_GEMS = create("type_gems");
    public final VITAMINS = create("vitamins");
    public final WATER_STONE_ORES = create("water_stone_ores");
    public final ZINC_INGREDIENTS = create("zinc_ingredients");

    // Held Item Tags
    public final CONSUMED_IN_NPC_BATTLE = create("held/consumed_in_npc_battle");
    public final CONSUMED_IN_PVP_BATTLE = create("held/consumed_in_pvp_battle");
    public final CONSUMED_IN_WILD_BATTLE = create("held/consumed_in_wild_battle");
    public final DESTINY_KNOT = create("held/destiny_knot");
    public final EVERSTONE = create("held/everstone");
    public final EXPERIENCE_SHARE = create("held/experience_share");
    public final IS_FRIENDSHIP_BOOSTER = create("is_friendship_booster");
    public final ANY_HELD_ITEM = create("held/is_held_item");
    public final BLACKLISTED_ITEMS_TO_HOLD = create("held/blacklisted_items_to_hold");
    public final WHITELISTED_ITEMS_TO_HOLD = create("held/whitelisted_items_to_hold");

    /** Tag that flags items as being able to "create" [CobblemonItems.LEFTOVERS]. */
    public final LEAVES_LEFTOVERS = create("held/leaves_leftovers");
    public final LUCKY_EGG = create("held/lucky_egg");
    public final POWER_ANKLET = create("held/power_anklet");
    public final POWER_BAND = create("held/power_band");
    public final POWER_BELT = create("held/power_belt");
    public final POWER_BRACER = create("held/power_bracer");
    public final POWER_LENS = create("held/power_lens");
    public final POWER_WEIGHT = create("held/power_weight");
    public final TERRAIN_SEEDS = create("held/terrain_seeds");

    //Held Item Visibility Tags
    public final WEARABLE_FACE_ITEMS = create("held/visibility/face");
    public final WEARABLE_HAT_ITEMS = create("held/visibility/hat");
    public final HIDDEN_ITEMS = create("held/visibility/hidden");

    //Empty Tag
    public final EMPTY = create("empty");
    private fun create(String path) = TagKey.create(Registries.ITEM, cobblemonResource(path))

}