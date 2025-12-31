/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.campfirepot.CampfireBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.LecternBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock.MintType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.campfirepot.CampfirePotBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedChestBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.general.BaleBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.grower.SaccharineTreeGrower;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonHangingSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallHangingSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.general.HorizontalRotationCarpetBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.general.HorizontalRotationalBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@Suppress("SameParametervalue", "HasPlatformType", "MemberVisibilityCanBePrivate", "unused");
public final class CobblemonBlocks : PlatformRegistry<Registry<Block>, ResourceKey<Registry<Block>>, Block>() {

    @Override public static final registry: Registry<Block> = BuiltInRegistries.BLOCK;
    @Override public static final resourceKey: ResourceKey<Registry<Block>> = Registries.BLOCK;

    public static final APRICORN_BLOCK_SET_TYPE = BlockSetType("apricorn");
    public static final APRICORN_WOOD_TYPE = WoodType.register(WoodType("apricorn", APRICORN_BLOCK_SET_TYPE));
    public static final SACCHARINE_BLOCK_SET_TYPE = BlockSetType("saccharine");
    public static final SACCHARINE_WOOD_TYPE = WoodType.register(WoodType("saccharine", SACCHARINE_BLOCK_SET_TYPE));

    // Evolution Ores
    public static final DAWN_STONE_ORE = evolutionStoneOre("dawn_stone_ore", 7);

    public static final DUSK_STONE_ORE = evolutionStoneOre("dusk_stone_ore");

    public static final FIRE_STONE_ORE = evolutionStoneOre("fire_stone_ore", 10);

    public static final NETHER_FIRE_STONE_ORE = this.create("nether_fire_stone_ore", DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.NETHER_ORE).lightLevel{10}));

    public static final ICE_STONE_ORE = evolutionStoneOre("ice_stone_ore");

    public static final LEAF_STONE_ORE = evolutionStoneOre("leaf_stone_ore");

    public static final MOON_STONE_ORE = evolutionStoneOre("moon_stone_ore");

    public static final DRIPSTONE_MOON_STONE_ORE = this.create("dripstone_moon_stone_ore", DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.DRIPSTONE_BLOCK)));

    public static final SHINY_STONE_ORE = evolutionStoneOre("shiny_stone_ore", 12);

    public static final SUN_STONE_ORE = evolutionStoneOre("sun_stone_ore", 2);

    public static final TERRACOTTA_SUN_STONE_ORE = this.create("terracotta_sun_stone_ore", DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F).lightLevel{2}));

    public static final THUNDER_STONE_ORE = evolutionStoneOre("thunder_stone_ore", 6);

    public static final WATER_STONE_ORE = evolutionStoneOre("water_stone_ore");

    public static final DEEPSLATE_DAWN_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_dawn_stone_ore", 7);

    public static final DEEPSLATE_DUSK_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_dusk_stone_ore");

    public static final DEEPSLATE_FIRE_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_fire_stone_ore", 10);

    public static final DEEPSLATE_ICE_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_ice_stone_ore");

    public static final DEEPSLATE_LEAF_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_leaf_stone_ore");

    public static final DEEPSLATE_MOON_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_moon_stone_ore");

    public static final DEEPSLATE_SHINY_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_shiny_stone_ore", 12);

    public static final DEEPSLATE_SUN_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_sun_stone_ore", 2);

    public static final DEEPSLATE_THUNDER_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_thunder_stone_ore", 6);

    public static final DEEPSLATE_WATER_STONE_ORE = this.deepslateEvolutionStoneOre("deepslate_water_stone_ore");

    // Apricorns

    public static final APRICORN_LOG = log("apricorn_log", arg2 = MapColor.COLOR_BROWN);

    public static final STRIPPED_APRICORN_LOG = log("stripped_apricorn_log");

    public static final APRICORN_WOOD = log("apricorn_wood");

    public static final STRIPPED_APRICORN_WOOD = log("stripped_apricorn_wood");

    public static final APRICORN_PLANKS = this.create("apricorn_planks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final APRICORN_LEAVES = leaves("apricorn_leaves");

    public static final APRICORN_FENCE = this.create("apricorn_fence", FenceBlock(BlockBehaviour.Properties.of().mapColor(APRICORN_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final APRICORN_FENCE_GATE = this.create("apricorn_fence_gate", FenceGateBlock(APRICORN_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(APRICORN_PLANKS.defaultMapColor()).strength(2.0f, 3.0f).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));

    public static final APRICORN_BUTTON = this.create("apricorn_button", BlocksInvoker.createWoodenButtonBlock(APRICORN_BLOCK_SET_TYPE));

    public static final APRICORN_PRESSURE_PLATE = this.create("apricorn_pressure_plate", PressurePlateBlockInvoker.cobblemon.create(APRICORN_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(APRICORN_PLANKS.defaultMapColor()).noCollission().strength(0.5f).sound(SoundType.WOOD)));

    public static final APRICORN_SIGN = this.create("apricorn_sign", CobblemonSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), APRICORN_WOOD_TYPE));

 //if you wonder why we don't copy of OAK_WALL_SIGN, then I can tell you its because it uses Properties#dropsLike point the SIGN loot table, but we cant use that in our setup due a deeper rooted issue, so falling back this and writing dedicated (duplicated) loot tables
    public static final APRICORN_WALL_SIGN = this.create("apricorn_wall_sign", CobblemonWallSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), APRICORN_WOOD_TYPE));

    public static final APRICORN_HANGING_SIGN = this.create("apricorn_hanging_sign", CobblemonHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), APRICORN_WOOD_TYPE));

    public static final APRICORN_WALL_HANGING_SIGN = this.create("apricorn_wall_hanging_sign", CobblemonWallHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), APRICORN_WOOD_TYPE));

    public static final APRICORN_SLAB = this.create("apricorn_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final APRICORN_STAIRS = this.create("apricorn_stairs", StairsBlockInvoker.cobblemon.create(APRICORN_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(APRICORN_PLANKS)));

    public static final APRICORN_DOOR = this.create("apricorn_door", DoorBlockInvoker.cobblemon.create(APRICORN_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(APRICORN_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));

    public static final APRICORN_TRAPDOOR = this.create("apricorn_trapdoor", TrapdoorBlockInvoker.cobblemon.create(APRICORN_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion().ispublic static finalidSpawn { _, _, _, _ -> false }));

    private public static final PLANT_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);

    public static final BLACK_APRICORN_SAPLING = this.create("black_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.BLACK));

    public static final BLUE_APRICORN_SAPLING = this.create("blue_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.BLUE));

    public static final GREEN_APRICORN_SAPLING = this.create("green_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.GREEN));

    public static final PINK_APRICORN_SAPLING = this.create("pink_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.PINK));

    public static final RED_APRICORN_SAPLING = this.create("red_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.RED));

    public static final WHITE_APRICORN_SAPLING = this.create("white_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.WHITE));

    public static final YELLOW_APRICORN_SAPLING = this.create("yellow_apricorn_sapling", ApricornSaplingBlock(PLANT_PROPERTIES, Apricorn.YELLOW));

    // Saccharines

    public static final SACCHARINE_LOG = this.create("saccharine_log", SaccharineLogBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));

    public static final SACCHARINE_LOG_SLATHERED = this.create("saccharine_log_slathered", SaccharineLogSlatheredBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));

    public static final STRIPPED_SACCHARINE_LOG = this.create("stripped_saccharine_log", RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())); //log("stripped_saccharine_log");

    public static final SACCHARINE_WOOD = log("saccharine_wood");

    public static final STRIPPED_SACCHARINE_WOOD = this.create("stripped_saccharine_wood", RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())); //log("stripped_saccharine_log");

    public static final SACCHARINE_PLANKS = this.create("saccharine_planks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final SACCHARINE_LEAVES = this.create("saccharine_leaves", SaccharineLeafBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().ispublic static finalidSpawn { _: BlockState?, _: BlockGetter?, _: BlockPos?, Entity entityType<*>? -> entity === EntityType.OCELOT || entity === EntityType.PARROT}.isSuffocating { _: BlockState?, _: BlockGetter?, _: BlockPos? -> false}.isViewBlocking { _: BlockState?, _: BlockGetter?, _: BlockPos? -> false}.ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor { _: BlockState?, _: BlockGetter?, _: BlockPos? -> false}));

    public static final SACCHARINE_FENCE = this.create("saccharine_fence", FenceBlock(BlockBehaviour.Properties.of().mapColor(SACCHARINE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final SACCHARINE_FENCE_GATE = this.create("saccharine_fence_gate", FenceGateBlock(SACCHARINE_WOOD_TYPE, BlockBehaviour.Properties.of().mapColor(SACCHARINE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD).ignitedByLava().forceSolidOn()));

    public static final SACCHARINE_BUTTON = this.create("saccharine_button", BlocksInvoker.createWoodenButtonBlock(BlockSetType.OAK));

    public static final SACCHARINE_PRESSURE_PLATE = this.create("saccharine_pressure_plate", PressurePlateBlockInvoker.cobblemon.create(SACCHARINE_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(SACCHARINE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5f).sound(SoundType.WOOD)));

    public static final SACCHARINE_SIGN = this.create("saccharine_sign", CobblemonSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), SACCHARINE_WOOD_TYPE));

    public static final SACCHARINE_WALL_SIGN = this.create("saccharine_wall_sign", CobblemonWallSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), SACCHARINE_WOOD_TYPE));

    public static final SACCHARINE_HANGING_SIGN = this.create("saccharine_hanging_sign", CobblemonHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), SACCHARINE_WOOD_TYPE));

    public static final SACCHARINE_WALL_HANGING_SIGN = this.create("saccharine_wall_hanging_sign", CobblemonWallHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), SACCHARINE_WOOD_TYPE));

    public static final SACCHARINE_SLAB = this.create("saccharine_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));

    public static final SACCHARINE_STAIRS = this.create("saccharine_stairs", StairsBlockInvoker.cobblemon.create(SACCHARINE_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SACCHARINE_PLANKS)));

    public static final SACCHARINE_DOOR = this.create("saccharine_door", DoorBlockInvoker.cobblemon.create(SACCHARINE_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(SACCHARINE_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));

    public static final SACCHARINE_TRAPDOOR = this.create("saccharine_trapdoor", TrapdoorBlockInvoker.cobblemon.create(SACCHARINE_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));

    public static final SACCHARINE_SAPLING = this.create("saccharine_sapling", SaplingBlock(SaccharineTreeGrower(), PLANT_PROPERTIES));

    public static final MEDICINAL_LEEK = this.create("medicinal_leek", MedicinalLeekBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.CRIMSON_NYLIUM).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MEDICINAL_LEEK_SOUNDS)));

    public static final BUGWORT = this.create("bugwort", BugwortBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.COLOR_PURPLE).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MEDICINAL_LEEK_SOUNDS)));

    public static final ENERGY_ROOT = this.create("energy_root", EnergyRootBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.DIRT).noCollission().randomTicks().instabreak().sound(CobblemonSounds.ENERGY_ROOT_SOUNDS)));

    public static final BIG_ROOT = this.create("big_root", BigRootBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(CobblemonSounds.BIG_ROOT_SOUNDS)));

    public static final REVIVAL_HERB = this.create("revival_herb", RevivalHerbBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).ignitedByLava().noCollission().instabreak().sound(CobblemonSounds.REVIVAL_HERB_SOUNDS)));

    public static final POKE_CAKE = this.create("poke_cake", PokeSnackBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).lightLevel { if (it.getvalue(LIT)) 3 else 0 }, false));

    public static final POKE_SNACK = this.create("poke_snack", PokeSnackBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).lightLevel { if (it.getvalue(LIT)) 3 else 0 }, true));

    public static final TUMBLESTONE_CLUSTER = tumblestoneBlock("tumblestone_cluster", GrowableStoneBlock.STAGE_3, 7, 3, null);

    public static final LARGE_BUDDING_TUMBLESTONE = tumblestoneBlock("large_budding_tumblestone", GrowableStoneBlock.STAGE_2, 5, 3, TUMBLESTONE_CLUSTER);

    public static final MEDIUM_BUDDING_TUMBLESTONE = tumblestoneBlock("medium_budding_tumblestone", GrowableStoneBlock.STAGE_1, 4, 3, LARGE_BUDDING_TUMBLESTONE);

    public static final SMALL_BUDDING_TUMBLESTONE = tumblestoneBlock("small_budding_tumblestone", GrowableStoneBlock.STAGE_0, 3, 4, MEDIUM_BUDDING_TUMBLESTONE);

    public static final SKY_TUMBLESTONE_CLUSTER = tumblestoneBlock("sky_tumblestone_cluster", GrowableStoneBlock.STAGE_3, 7, 3, null);

    public static final LARGE_BUDDING_SKY_TUMBLESTONE = tumblestoneBlock("large_budding_sky_tumblestone", GrowableStoneBlock.STAGE_2, 5, 3, SKY_TUMBLESTONE_CLUSTER);

    public static final MEDIUM_BUDDING_SKY_TUMBLESTONE = tumblestoneBlock("medium_budding_sky_tumblestone", GrowableStoneBlock.STAGE_1, 4, 3, LARGE_BUDDING_SKY_TUMBLESTONE);

    public static final SMALL_BUDDING_SKY_TUMBLESTONE = tumblestoneBlock("small_budding_sky_tumblestone", GrowableStoneBlock.STAGE_0, 3, 4, MEDIUM_BUDDING_SKY_TUMBLESTONE);

    public static final BLACK_TUMBLESTONE_CLUSTER = tumblestoneBlock("black_tumblestone_cluster", GrowableStoneBlock.STAGE_3, 7, 3, null);

    public static final LARGE_BUDDING_BLACK_TUMBLESTONE = tumblestoneBlock("large_budding_black_tumblestone", GrowableStoneBlock.STAGE_2, 5, 3, BLACK_TUMBLESTONE_CLUSTER);

    public static final MEDIUM_BUDDING_BLACK_TUMBLESTONE = tumblestoneBlock("medium_budding_black_tumblestone", GrowableStoneBlock.STAGE_1, 4, 3, LARGE_BUDDING_BLACK_TUMBLESTONE);

    public static final SMALL_BUDDING_BLACK_TUMBLESTONE = tumblestoneBlock("small_budding_black_tumblestone", GrowableStoneBlock.STAGE_0, 3, 4, MEDIUM_BUDDING_BLACK_TUMBLESTONE);

    public static final TUMBLESTONE_BLOCK = this.create("tumblestone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SKY_TUMBLESTONE_BLOCK = this.create("sky_tumblestone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final BLACK_TUMBLESTONE_BLOCK = this.create("black_tumblestone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_TUMBLESTONE = this.create("polished_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_TUMBLESTONE_STAIRS = this.create("polished_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(POLISHED_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_TUMBLESTONE)));

    public static final POLISHED_TUMBLESTONE_SLAB = this.create("polished_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_TUMBLESTONE_WALL = this.create("polished_tumblestone_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_POLISHED_TUMBLESTONE = this.create("chiseled_polished_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_TUMBLESTONE = this.create("smooth_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_TUMBLESTONE_STAIRS = this.create("smooth_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(SMOOTH_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SMOOTH_TUMBLESTONE)));

    public static final SMOOTH_TUMBLESTONE_SLAB = this.create("smooth_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final TUMBLESTONE_BRICKS = this.create("tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final TUMBLESTONE_BRICK_STAIRS = this.create("tumblestone_brick_stairs", StairsBlockInvoker.cobblemon.create(TUMBLESTONE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(TUMBLESTONE_BRICKS)));

    public static final TUMBLESTONE_BRICK_SLAB = this.create("tumblestone_brick_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final TUMBLESTONE_BRICK_WALL = this.create("tumblestone_brick_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_TUMBLESTONE_BRICKS = this.create("chiseled_tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_SKY_TUMBLESTONE = this.create("polished_sky_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_SKY_TUMBLESTONE_STAIRS = this.create("polished_sky_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(POLISHED_SKY_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_SKY_TUMBLESTONE)));

    public static final POLISHED_SKY_TUMBLESTONE_SLAB = this.create("polished_sky_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_SKY_TUMBLESTONE_WALL = this.create("polished_sky_tumblestone_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_POLISHED_SKY_TUMBLESTONE = this.create("chiseled_polished_sky_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_SKY_TUMBLESTONE = this.create("smooth_sky_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_SKY_TUMBLESTONE_STAIRS = this.create("smooth_sky_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(SMOOTH_SKY_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SMOOTH_SKY_TUMBLESTONE)));

    public static final SMOOTH_SKY_TUMBLESTONE_SLAB = this.create("smooth_sky_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SKY_TUMBLESTONE_BRICKS = this.create("sky_tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SKY_TUMBLESTONE_BRICK_STAIRS = this.create("sky_tumblestone_brick_stairs", StairsBlockInvoker.cobblemon.create(SKY_TUMBLESTONE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SKY_TUMBLESTONE_BRICKS)));

    public static final SKY_TUMBLESTONE_BRICK_SLAB = this.create("sky_tumblestone_brick_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SKY_TUMBLESTONE_BRICK_WALL = this.create("sky_tumblestone_brick_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_SKY_TUMBLESTONE_BRICKS = this.create("chiseled_sky_tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_BLACK_TUMBLESTONE = this.create("polished_black_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_BLACK_TUMBLESTONE_STAIRS = this.create("polished_black_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(POLISHED_BLACK_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_BLACK_TUMBLESTONE)));

    public static final POLISHED_BLACK_TUMBLESTONE_SLAB = this.create("polished_black_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final POLISHED_BLACK_TUMBLESTONE_WALL = this.create("polished_black_tumblestone_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_POLISHED_BLACK_TUMBLESTONE = this.create("chiseled_polished_black_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_BLACK_TUMBLESTONE = this.create("smooth_black_tumblestone", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final SMOOTH_BLACK_TUMBLESTONE_STAIRS = this.create("smooth_black_tumblestone_stairs", StairsBlockInvoker.cobblemon.create(SMOOTH_BLACK_TUMBLESTONE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SMOOTH_BLACK_TUMBLESTONE)));

    public static final SMOOTH_BLACK_TUMBLESTONE_SLAB = this.create("smooth_black_tumblestone_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final BLACK_TUMBLESTONE_BRICKS = this.create("black_tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final BLACK_TUMBLESTONE_BRICK_STAIRS = this.create("black_tumblestone_brick_stairs", StairsBlockInvoker.cobblemon.create(BLACK_TUMBLESTONE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLACK_TUMBLESTONE_BRICKS)));

    public static final BLACK_TUMBLESTONE_BRICK_SLAB = this.create("black_tumblestone_brick_slab", SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final BLACK_TUMBLESTONE_BRICK_WALL = this.create("black_tumblestone_brick_wall", WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final CHISELED_BLACK_TUMBLESTONE_BRICKS = this.create("chiseled_black_tumblestone_bricks", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).strength(1.0F).sound(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM)));

    public static final FIRE_STONE_BLOCK = this.create("fire_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT).lightLevel{13}));

    public static final WATER_STONE_BLOCK = this.create("water_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT)));

    public static final THUNDER_STONE_BLOCK = this.create("thunder_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT).lightLevel{8}));

    public static final LEAF_STONE_BLOCK = this.create("leaf_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT)));

    public static final ICE_STONE_BLOCK = this.create("ice_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT)));

    public static final SUN_STONE_BLOCK = this.create("sun_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT).lightLevel{5}));

    public static final MOON_STONE_BLOCK = this.create("moon_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT)));

    public static final SHINY_STONE_BLOCK = this.create("shiny_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT).lightLevel{15}));

    public static final DAWN_STONE_BLOCK = this.create("dawn_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT).lightLevel{15}));

    public static final DUSK_STONE_BLOCK = this.create("dusk_stone_block", Block(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_HYPHAE).strength(1.0F).sound(CobblemonSounds.EVOLUTION_STONE_BLOCK_SOUNDS).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BIT)));

    public static final BLACK_APRICORN = apricornBlock("black_apricorn", Apricorn.BLACK);

    public static final BLUE_APRICORN = apricornBlock("blue_apricorn", Apricorn.BLUE);

    public static final GREEN_APRICORN = apricornBlock("green_apricorn", Apricorn.GREEN);

    public static final PINK_APRICORN = apricornBlock("pink_apricorn", Apricorn.PINK);

    public static final RED_APRICORN = apricornBlock("red_apricorn", Apricorn.RED);

    public static final WHITE_APRICORN = apricornBlock("white_apricorn", Apricorn.WHITE);

    public static final YELLOW_APRICORN = apricornBlock("yellow_apricorn", Apricorn.YELLOW);

    public static final BLACK_CAMPFIRE_POT = create("campfire_pot_black", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final BLUE_CAMPFIRE_POT = create("campfire_pot_blue", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final GREEN_CAMPFIRE_POT = create("campfire_pot_green", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final PINK_CAMPFIRE_POT = create("campfire_pot_pink", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final RED_CAMPFIRE_POT = create("campfire_pot_red", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final WHITE_CAMPFIRE_POT = create("campfire_pot_white", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final YELLOW_CAMPFIRE_POT = create("campfire_pot_yellow", CampfirePotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(CobblemonSounds.CAMPFIRE_POT_SOUNDS).strength(0.5F).pushReaction(PushReaction.BLOCK).noOcclusion()));

    public static final CAMPFIRE = create("campfire", CampfireBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.BLOCK).mapColor(MapColor.PODZOL).strength(2.0F).lightLevel{14}, false));

    public static final SOUL_CAMPFIRE = create("soul_campfire", CampfireBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.BLOCK).mapColor(MapColor.PODZOL).strength(2.0F).lightLevel{9}, true));

    public static final RELIC_COIN_POUCH = create("relic_coin_pouch", CoinPouchBlock(BlockBehaviour.Properties.of().sound(CobblemonSounds.RELIC_COIN_POUCH_SOUNDS).pushReaction(PushReaction.DESTROY).noOcclusion(), true));

    public static final RELIC_COIN_SACK = create("relic_coin_sack", CoinPouchBlock(BlockBehaviour.Properties.of().sound(CobblemonSounds.RELIC_COIN_SACK_SOUNDS).pushReaction(PushReaction.DESTROY).strength(0.4f), false));

    public static final GILDED_CHEST = create("gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.RED));

    public static final BLUE_GILDED_CHEST = create("blue_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.BLUE));

    public static final BLACK_GILDED_CHEST = create("black_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.BLACK));

    public static final YELLOW_GILDED_CHEST = create("yellow_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.YELLOW));

    public static final WHITE_GILDED_CHEST = create("white_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.WHITE));

    public static final GREEN_GILDED_CHEST = create("green_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.GREEN));

    public static final PINK_GILDED_CHEST = create("pink_gilded_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.PINK));

    public static final GIMMIGHOUL_CHEST = create("gimmighoul_chest", GildedChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion().sound(CobblemonSounds.GILDED_CHEST_SOUNDS), GildedChestBlock.Type.FAKE));

    public static final MONITOR = create("monitor", MonitorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().strength(5.0F, 6.0F).lightLevel { if (it.getvalue(MonitorBlock.SCREEN) != MonitorBlock.MonitorScreen.OFF) 13 else 0 }));

    public static final FOSSIL_ANALYZER = create("fossil_analyzer", FossilAnalyzerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final RESTORATION_TANK = create("restoration_tank", RestorationTankBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.GLASS).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

    public static final HEALING_MACHINE = create("healing_machine", HealingMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).strength(2f).noOcclusion().lightLevel { if (it.getvalue(HealingMachineBlock.CHARGE_LEVEL) >= HealingMachineBlock.MAX_CHARGE_LEVEL) 12 else 5 }));

    public static final PC = create("pc", PCBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).strength(2F).noOcclusion().lightLevel { if ((it.getvalue(PCBlock.ON) as Boolean) && (it.getvalue(PCBlock.PART) == PCBlock.PCPart.TOP)) 13 else 0 }));

    public static final LECTERN = create("lectern", LecternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2.5F).ignitedByLava().lightLevel { if (it.getvalue(LecternBlock.EMIT_LIGHT) as Boolean) 13 else 0 }));

    public static final DISPLAY_CASE = create("display_case", DisplayCaseBlock(BlockBehaviour.Properties.of().sound(CobblemonSounds.DISPLAY_CASE_SOUNDS).noOcclusion().pushReaction(PushReaction.BLOCK).mapColor(MapColor.STONE).strength(0.3F).requiresCorrectToolForDrops()));

    public static final INCENSE_SWEET = create("incense_sweet", SweetIncenseBlock(BlockBehaviour.Properties.of().sound(CobblemonSounds.DISPLAY_CASE_SOUNDS).noOcclusion().pushReaction(PushReaction.BLOCK).mapColor(MapColor.STONE).strength(0.3f)));

    public static final PASTURE = create("pasture", PastureBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2F).noOcclusion().pushReaction(PushReaction.BLOCK).lightLevel { if ((it.getvalue(PastureBlock.ON) as Boolean) && (it.getvalue(PastureBlock.PART) == PastureBlock.PasturePart.TOP)) 13 else 0 }));

    public static final RED_MINT = create("red_mint", MintBlock(MintType.RED, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final BLUE_MINT = create("blue_mint", MintBlock(MintType.BLUE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final CYAN_MINT = create("cyan_mint", MintBlock(MintType.CYAN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final PINK_MINT = create("pink_mint", MintBlock(MintType.PINK, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final GREEN_MINT = create("green_mint", MintBlock(MintType.GREEN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final WHITE_MINT = create("white_mint", MintBlock(MintType.WHITE, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).noCollission().randomTicks().instabreak().sound(CobblemonSounds.MINT_SOUNDS).pushReaction(PushReaction.DESTROY)));

    public static final VIVICHOKE_SEEDS = this.create("vivichoke_seeds", VivichokeBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(CobblemonSounds.VIVICHOKE_SOUNDS)));

    public static final PEP_UP_FLOWER = this.create("pep_up_flower", FlowerBlock(MobEffects.LEVITATION, 10F, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final POTTED_PEP_UP_FLOWER = this.create("potted_pep_up_flower", BlocksInvoker.createFlowerPotBlock(PEP_UP_FLOWER));

    public static final HEARTY_GRAINS = this.create("hearty_grains", HeartyGrainsBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).ignitedByLava().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak()));

    public static final GALARICA_NUT_BUSH = this.create("galarica_nut_bush", NutBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY)));

    public static final POTTED_RED_APRICORN_SAPLING = this.create("potted_red_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(RED_APRICORN_SAPLING));

    public static final POTTED_YELLOW_APRICORN_SAPLING = this.create("potted_yellow_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(YELLOW_APRICORN_SAPLING));

    public static final POTTED_GREEN_APRICORN_SAPLING = this.create("potted_green_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(GREEN_APRICORN_SAPLING));

    public static final POTTED_BLUE_APRICORN_SAPLING = this.create("potted_blue_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(BLUE_APRICORN_SAPLING));

    public static final POTTED_PINK_APRICORN_SAPLING = this.create("potted_pink_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(PINK_APRICORN_SAPLING));

    public static final POTTED_BLACK_APRICORN_SAPLING = this.create("potted_black_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(BLACK_APRICORN_SAPLING));

    public static final POTTED_WHITE_APRICORN_SAPLING = this.create("potted_white_apricorn_sapling", BlocksInvoker.createFlowerPotBlock(WHITE_APRICORN_SAPLING));

    public static final POTTED_SACCHARINE_SAPLING = this.create("potted_saccharine_sapling", BlocksInvoker.createFlowerPotBlock(SACCHARINE_SAPLING));

    public static final HEARTY_GRAIN_BALE = this.create("hearty_grain_bale", BaleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(CobblemonSounds.HEARTY_GRAIN_BALE_SOUNDS)));

    public static final TATAMI_BLOCK = this.create("tatami_block", HorizontalRotationalBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.PLANT).strength(0.1F).sound(CobblemonSounds.TATAMI_BLOCK_SOUNDS)));

    public static final TATAMI_MAT = this.create("tatami_mat", HorizontalRotationCarpetBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.PLANT).strength(0.1F).sound(CobblemonSounds.TATAMI_MAT_SOUNDS)));

    /**
     * Returns a map of all the blocks that can be stripped with an axe in the format of input - output.
     *
     * @return A map of all the blocks that can be stripped with an axe in the format of input - output.
     */
    fun strippedBlocks(): Map<Block, Block> = mapOf(APRICORN_WOOD STRIPPED_APRICORN_WOOD, APRICORN_LOG STRIPPED_APRICORN_LOG, SACCHARINE_WOOD STRIPPED_SACCHARINE_WOOD, SACCHARINE_LOG STRIPPED_SACCHARINE_LOG, SACCHARINE_LOG_SLATHERED STRIPPED_SACCHARINE_LOG)

    private fun apricornBlock(String name, apricorn: Apricorn): ApricornBlock = this.create(name, ApricornBlock(BlockBehaviour.Properties.of().mapColor(apricorn.mapColor()).randomTicks().strength(Blocks.OAK_LOG.defaultDestroyTime(), Blocks.OAK_LOG.explosionResistance).sound(SoundType.WOOD).noOcclusion(), apricorn))

    private fun tumblestoneBlock(String name, stage: Int, Int height, xzOffset: Int, nextStage: Block?) : Block {
        return this.create(name, TumblestoneBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noOcclusion().strength(1.5F).sound(CobblemonSounds.TUMBLESTONE_SOUNDS), stage, height, xzOffset, nextStage));
    }

    private public static final berries = mutableMapOf<ResourceLocation, BerryBlock>();


    public static final AGUAV_BERRY = this.berryBlock("aguav");

    public static final APICOT_BERRY = this.berryBlock("apicot");

    public static final ASPEAR_BERRY = this.berryBlock("aspear");

    public static final BABIRI_BERRY = this.berryBlock("babiri");

    public static final BELUE_BERRY = this.berryBlock("belue");

    public static final BLUK_BERRY = this.berryBlock("bluk");

    public static final CHARTI_BERRY = this.berryBlock("charti");

    public static final CHERI_BERRY = this.berryBlock("cheri");

    public static final CHESTO_BERRY = this.berryBlock("chesto");

    public static final CHILAN_BERRY = this.berryBlock("chilan");

    public static final CHOPLE_BERRY = this.berryBlock("chople");

    public static final COBA_BERRY = this.berryBlock("coba");

    public static final COLBUR_BERRY = this.berryBlock("colbur");

    public static final CORNN_BERRY = this.berryBlock("cornn");

    public static final CUSTAP_BERRY = this.berryBlock("custap");

    public static final DURIN_BERRY = this.berryBlock("durin");

    public static final EGGANT_BERRY = this.berryBlock("eggant");

    public static final ENIGMA_BERRY = this.berryBlock("enigma");

    public static final FIGY_BERRY = this.berryBlock("figy");

    public static final GANLON_BERRY = this.berryBlock("ganlon");

    public static final GREPA_BERRY = this.berryBlock("grepa");

    public static final HABAN_BERRY = this.berryBlock("haban");

    public static final HONDEW_BERRY = this.berryBlock("hondew");

    public static final HOPO_BERRY = this.berryBlock("hopo");

    public static final IAPAPA_BERRY = this.berryBlock("iapapa");

    public static final JABOCA_BERRY = this.berryBlock("jaboca");

    public static final KASIB_BERRY = this.berryBlock("kasib");

    public static final KEBIA_BERRY = this.berryBlock("kebia");

    public static final KEE_BERRY = this.berryBlock("kee");

    public static final KELPSY_BERRY = this.berryBlock("kelpsy");

    public static final LANSAT_BERRY = this.berryBlock("lansat");

    public static final LEPPA_BERRY = this.berryBlock("leppa");

    public static final LIECHI_BERRY = this.berryBlock("liechi");

    public static final LUM_BERRY = this.berryBlock("lum");

    public static final MAGO_BERRY = this.berryBlock("mago");

    public static final MAGOST_BERRY = this.berryBlock("magost");

    public static final MARANGA_BERRY = this.berryBlock("maranga");

    public static final MICLE_BERRY = this.berryBlock("micle");

    public static final NANAB_BERRY = this.berryBlock("nanab");

    public static final NOMEL_BERRY = this.berryBlock("nomel");

    public static final OCCA_BERRY = this.berryBlock("occa");

    public static final ORAN_BERRY = this.berryBlock("oran");

    public static final PAMTRE_BERRY = this.berryBlock("pamtre");

    public static final PASSHO_BERRY = this.berryBlock("passho");

    public static final PAYAPA_BERRY = this.berryBlock("payapa");

    public static final PECHA_BERRY = this.berryBlock("pecha");

    public static final PERSIM_BERRY = this.berryBlock("persim");

    public static final PETAYA_BERRY = this.berryBlock("petaya");

    public static final PINAP_BERRY = this.berryBlock("pinap");

    public static final POMEG_BERRY = this.berryBlock("pomeg");

    public static final QUALOT_BERRY = this.berryBlock("qualot");

    public static final RABUTA_BERRY = this.berryBlock("rabuta");

    public static final RAWST_BERRY = this.berryBlock("rawst");

    public static final RAZZ_BERRY = this.berryBlock("razz");

    public static final RINDO_BERRY = this.berryBlock("rindo");

    public static final ROSELI_BERRY = this.berryBlock("roseli");

    public static final ROWAP_BERRY = this.berryBlock("rowap");

    public static final SALAC_BERRY = this.berryBlock("salac");

    public static final SHUCA_BERRY = this.berryBlock("shuca");

    public static final SITRUS_BERRY = this.berryBlock("sitrus");

    public static final SPELON_BERRY = this.berryBlock("spelon");

    public static final STARF_BERRY = this.berryBlock("starf");

    public static final TAMATO_BERRY = this.berryBlock("tamato");

    public static final TANGA_BERRY = this.berryBlock("tanga");

    public static final TOUGA_BERRY = this.berryBlock("touga");

    public static final WACAN_BERRY = this.berryBlock("wacan");

    public static final WATMEL_BERRY = this.berryBlock("watmel");

    public static final WEPEAR_BERRY = this.berryBlock("wepear");

    public static final WIKI_BERRY = this.berryBlock("wiki");

    public static final YACHE_BERRY = this.berryBlock("yache");
    //public static final BINDING_SOIL = this.create("binding_soil", BindingSoilBlock(BlockBehaviour.Properties.of()));

    init {
        /**
         * Makes all blocks in array flammable by adding them FireBlock's flammableRegistry.
         * second public static finalue is burn chance and third public static finalue is spread chance
         */
        arrayOf(
            Triple(APRICORN_LOG, 5, 5),
            Triple(STRIPPED_APRICORN_LOG, 5, 5),
            Triple(APRICORN_WOOD, 5, 5),
            Triple(STRIPPED_APRICORN_WOOD, 5, 5),
            Triple(APRICORN_PLANKS, 5, 20),
            Triple(APRICORN_LEAVES, 30, 60),
            Triple(APRICORN_FENCE, 5, 20),
            Triple(APRICORN_FENCE_GATE, 5, 20),
            Triple(APRICORN_SLAB, 5, 20),
            Triple(APRICORN_STAIRS, 5, 20),
            Triple(SACCHARINE_LOG, 5, 5),
            Triple(SACCHARINE_LOG_SLATHERED, 5, 5),
            Triple(STRIPPED_SACCHARINE_LOG, 5, 5),
            Triple(SACCHARINE_WOOD, 5, 5),
            Triple(STRIPPED_SACCHARINE_WOOD, 5, 5),
            Triple(SACCHARINE_PLANKS, 5, 20),
            Triple(SACCHARINE_LEAVES, 30, 60),
            Triple(SACCHARINE_FENCE, 5, 20),
            Triple(SACCHARINE_FENCE_GATE, 5, 20),
            Triple(SACCHARINE_SLAB, 5, 20),
            Triple(SACCHARINE_STAIRS, 5, 20);

        ).onEach{ data -> setFlammable(data.first, data.second, data.third) }
    }

    fun berries() = this.berries.toMap();

    private fun berryBlock(String name): BerryBlock {
        public static final identifier = cobblemonResource("${name}_berry");
        public static final block = this.create(identifier.path, BerryBlock(identifier, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).dynamicShape().sound(CobblemonSounds.BERRY_BUSH_SOUNDS).strength(0.2F)));
        this.berries[identifier] = block
        return block
    }

    /**
     * Calls helper method from Vanilla
     */
    private fun log(String name, arg: MapColor = MapColor.DIRT, arg2: MapColor = MapColor.DIRT): Block {
        public static final block = BlocksInvoker.createLogBlock(arg, arg2);
        return this.create(name, block);
    }

    /**
     * Method uses generic E in order keep the block as the same return type.
     * If E is not a block then it will not be set as flammable.
     * Calls Vanilla implementation of registering a flammable block.
     * Mixins looks cursed but it is java's fault.
     */
    private fun <E> setFlammable(block: E, burnChance: Int, spreadChance: Int): E {
        if(block !is Block) return block

        var fireBlock: FireBlock =  Blocks.FIRE as FireBlock
        //Cursed Mixin stuff
        (fireBlock as FireBlockInvoker).registerNewFlammableBlock(block as Block, burnChance, spreadChance);
        return block
    }

    private fun evolutionStoneOre(String name) = this.create(name, DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    private fun evolutionStoneOre(String name, lightLevel: Int) = this.create(name, DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).lightLevel { lightLevel }));

    private fun deepslateEvolutionStoneOre(String name) = this.create(name, DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)));
    private fun deepslateEvolutionStoneOre(String name, lightLevel: Int) = this.create(name, DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).lightLevel { lightLevel }));

    /**
     * Helper method for creating leaves
     * copied over from Vanilla
     */
    private fun leaves(String name): Block {
        public static final block = BlocksInvoker.createLeavesBlock(SoundType.GRASS);
        return this.create(name, block);
    }
}