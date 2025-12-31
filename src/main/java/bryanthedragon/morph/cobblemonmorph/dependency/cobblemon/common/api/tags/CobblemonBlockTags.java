/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;

/**
 * A collection of the Cobblemon [TagKey]s related to the [Registries.BLOCK].
 *
 * @author Licious
 * @since October 29th, 2022
 */
public final class CobblemonBlockTags {

    public final ALL_HANGING_SIGNS = createTag("all_hanging_signs");
    public final ALL_SAPLINGS = createTag("all_saplings");
    public final ALL_SIGNS = createTag("all_signs");
    public final ANCIENT_CITY_BLOCKS = createTag("ancient_city_blocks");
    public final APRICORN_LEAVES = createTag("apricorn_leaves");
    public final APRICORN_LOGS = createTag("apricorn_logs");
    public final APRICORN_SAPLINGS = createTag("apricorn_saplings");
    public final APRICORNS = createTag("apricorns");
    public final BERRIES = createTag("berries");
    public final BERRY_REPLACEABLE = createTag("berry_replaceable");
    public final BERRY_SOIL = createTag("berry_soil");
    public final BERRY_WILD_SOIL = createTag("berry_wild_soil");
    public final BLACK_TUMBLESTONE_BRICKS = createTag("black_tumblestone_bricks");
    public final BLACK_TUMBLESTONES = createTag("black_tumblestones");
    public final BLUE_FLOWERS = createTag("blue_flowers");
    public final CEILING_HANGING_SIGNS = createTag("ceiling_hanging_signs");
    public final CROPS = createTag("crops");
    public final DAWN_STONE_ORES = createTag("dawn_stone_ores");
    public final DESERT_PYRAMID_BLOCKS = createTag("desert_pyramid_blocks");
    public final DRIPSTONE_GROWABLE = createTag("dripstone_growable");
    public final DRIPSTONE_REPLACEABLES = createTag("dripstone_replaceables");
    public final DUSK_STONE_ORES = createTag("dusk_stone_ores");
    public final END_CITY_BLOCKS = createTag("end_city_blocks");
    public final EVOLUTION_STONE_BLOCKS = createTag("evolution_stone_blocks");
    public final FIRE_STONE_ORES = createTag("fire_stone_ores");
    public final FLOWERS = createTag("flowers");
    public final FOSSIL_MACHINE_PARTS = createTag("fossil_machine_parts");
    public final GALARICA_NUT_MAY_PLACE_ON = createTag("galarica_nut_may_place_on");
    public final GEMSTONES = createTag("gemstones");
    public final GILDED_CHESTS = createTag("gilded_chests");
    public final GLAZED_TERRACOTTA_BLOCKS = createTag("glazed_terracotta_blocks");
    public final ICE_STONE_ORES = createTag("ice_stone_ores");
    public final JUNGLE_PYRAMID_BLOCKS = createTag("jungle_pyramid_blocks");
    public final LEAF_STONE_ORES = createTag("leaf_stone_ores");
    public final MACHINES = createTag("machines");
    public final MANSION_BLOCKS = createTag("mansion_blocks");
    public final MEDICINAL_LEEK_PLANTABLE = createTag("medicinal_leek_plantable");
    public final HEARTY_GRAINS_LAND_PLANTABLE = createTag("hearty_grains_land_plantable");
    public final HEARTY_GRAINS_WATER_PLANTABLE = createTag("hearty_grains_water_plantable");
    public final MINTS = createTag("mints");
    public final MOON_STONE_ORES = createTag("moon_stone_ores");
    public final NATURAL = createTag("natural");
    public final NETHER_STRUCTURE_BLOCKS = createTag("nether_structure_blocks");
    public final PINK_FLOWERS = createTag("pink_flowers");
    public final RED_FLOWERS = createTag("red_flowers");
    public final RED_TUMBLESTONE_BRICKS = createTag("red_tumblestone_bricks");
    public final RED_TUMBLESTONES = createTag("red_tumblestones");
    public final REDSTONE_BLOCKS = createTag("redstone_blocks");
    public final ROOTS = createTag("roots");
    public final ROOTS_SPREADABLE = createTag("roots_spreadable");
    public final SACCHARINE_LEAVES = createTag("saccharine_leaves");
    public final SACCHARINE_LOGS = createTag("saccharine_logs");
    public final SACCHARINE_SAPLING = createTag("saccharine_sapling");
    public final RUINED_PORTAL_BLOCKS = createTag("ruined_portal_blocks");
    public final SEES_SKY = createTag("sees_sky");
    public final SHINY_STONE_ORES = createTag("shiny_stone_ores");
    public final SIGNS = createTag("signs");
    public final SKY_TUMBLESTONE_BRICKS = createTag("sky_tumblestone_bricks");
    public final SKY_TUMBLESTONES = createTag("sky_tumblestones");
    public final SMALL_FLOWERS = createTag("small_flowers");
    public final SNOW_BLOCK = createTag("snow_block");
    public final STANDING_SIGNS = createTag("standing_signs");
    public final SUN_STONE_ORES = createTag("sun_stone_ores");
    public final THUNDER_STONE_ORES = createTag("thunder_stone_ores");
    public final TRAIL_RUINS_BLOCKS = createTag("trail_ruins_blocks");
    public final TRASH = createTag("trash");
    public final TREES = createTag("trees");
    public final TUMBLESTONE_BRICKS = createTag("tumblestone_bricks");
    public final TUMBLESTONES = createTag("tumblestones");
    public final TUMBLESTONE_HEAT_SOURCE = createTag("tumblestone_heat_source");
    public final WALL_HANGING_SIGNS = createTag("wall_hanging_signs");
    public final WALL_SIGNS = createTag("wall_signs");
    public final WATER_STONE_ORES = createTag("water_stone_ores");
    public final WHITE_FLOWERS = createTag("white_flowers");
    public final YELLOW_FLOWERS = createTag("yellow_flowers");

    private fun createTag(String name) = TagKey.create(Registries.BLOCK, cobblemonResource(name))

}
