/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.blue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.gray;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock.MintType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.pokedex.PokedexType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.campfirepot.CampfirePotColor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.armor.CobblemonArmorTrims;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.DireHitItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.GuardSpecItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.XStatItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.FriendshipRaisingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.HealingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PPRestoringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.PortionHealingBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.StatusCuringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PonigiriItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.food.SinisterTeaItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityChangeItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class CobblemonItems : PlatformRegistry<Registry<Item>, ResourceKey<Registry<Item>>, Item>() {
    @Override public static final registry: Registry<Item> = BuiltInRegistries.ITEM;
    @Override public static final resourceKey: ResourceKey<Registry<Item>> = Registries.ITEM;

    public static final NPC_EDITOR = create("npc_editor", CobblemonItem(Item.Properties().stacksTo(1)))

    public static final pokeBalls = mutableListOf<PokeBallItem>()
    public static final POKE_BALL = pokeBallItem(PokeBalls.POKE_BALL)
    public static final CITRINE_BALL = pokeBallItem(PokeBalls.CITRINE_BALL)
    public static final VERDANT_BALL = pokeBallItem(PokeBalls.VERDANT_BALL)
    public static final AZURE_BALL = pokeBallItem(PokeBalls.AZURE_BALL)
    public static final ROSEATE_BALL = pokeBallItem(PokeBalls.ROSEATE_BALL)
    public static final SLATE_BALL = pokeBallItem(PokeBalls.SLATE_BALL)
    public static final PREMIER_BALL = pokeBallItem(PokeBalls.PREMIER_BALL)
    public static final GREAT_BALL = pokeBallItem(PokeBalls.GREAT_BALL)
    public static final ULTRA_BALL = pokeBallItem(PokeBalls.ULTRA_BALL)
    public static final SAFARI_BALL = pokeBallItem(PokeBalls.SAFARI_BALL)
    public static final FAST_BALL = pokeBallItem(PokeBalls.FAST_BALL)
    public static final LEVEL_BALL = pokeBallItem(PokeBalls.LEVEL_BALL)
    public static final LURE_BALL = pokeBallItem(PokeBalls.LURE_BALL)
    public static final HEAVY_BALL = pokeBallItem(PokeBalls.HEAVY_BALL)
    public static final LOVE_BALL = pokeBallItem(PokeBalls.LOVE_BALL)
    public static final FRIEND_BALL = pokeBallItem(PokeBalls.FRIEND_BALL)
    public static final MOON_BALL = pokeBallItem(PokeBalls.MOON_BALL)
    public static final SPORT_BALL = pokeBallItem(PokeBalls.SPORT_BALL)
    public static final PARK_BALL = pokeBallItem(PokeBalls.PARK_BALL)
    public static final NET_BALL = pokeBallItem(PokeBalls.NET_BALL)
    public static final DIVE_BALL = pokeBallItem(PokeBalls.DIVE_BALL)
    public static final NEST_BALL = pokeBallItem(PokeBalls.NEST_BALL)
    public static final REPEAT_BALL = pokeBallItem(PokeBalls.REPEAT_BALL)
    public static final TIMER_BALL = pokeBallItem(PokeBalls.TIMER_BALL)
    public static final LUXURY_BALL = pokeBallItem(PokeBalls.LUXURY_BALL)
    public static final DUSK_BALL = pokeBallItem(PokeBalls.DUSK_BALL)
    public static final HEAL_BALL = pokeBallItem(PokeBalls.HEAL_BALL)
    public static final QUICK_BALL = pokeBallItem(PokeBalls.QUICK_BALL)
    public static final DREAM_BALL = pokeBallItem(PokeBalls.DREAM_BALL)
    public static final BEAST_BALL = pokeBallItem(PokeBalls.BEAST_BALL)
    public static final MASTER_BALL = pokeBallItem(PokeBalls.MASTER_BALL)
    public static final CHERISH_BALL = pokeBallItem(PokeBalls.CHERISH_BALL)
    public static final ANCIENT_POKE_BALL = pokeBallItem(PokeBalls.ANCIENT_POKE_BALL)
    public static final ANCIENT_CITRINE_BALL = pokeBallItem(PokeBalls.ANCIENT_CITRINE_BALL)
    public static final ANCIENT_VERDANT_BALL = pokeBallItem(PokeBalls.ANCIENT_VERDANT_BALL)
    public static final ANCIENT_AZURE_BALL = pokeBallItem(PokeBalls.ANCIENT_AZURE_BALL)
    public static final ANCIENT_ROSEATE_BALL = pokeBallItem(PokeBalls.ANCIENT_ROSEATE_BALL)
    public static final ANCIENT_SLATE_BALL = pokeBallItem(PokeBalls.ANCIENT_SLATE_BALL)
    public static final ANCIENT_IVORY_BALL = pokeBallItem(PokeBalls.ANCIENT_IVORY_BALL)
    public static final ANCIENT_GREAT_BALL = pokeBallItem(PokeBalls.ANCIENT_GREAT_BALL)
    public static final ANCIENT_ULTRA_BALL = pokeBallItem(PokeBalls.ANCIENT_ULTRA_BALL)
    public static final ANCIENT_FEATHER_BALL = pokeBallItem(PokeBalls.ANCIENT_FEATHER_BALL)
    public static final ANCIENT_WING_BALL = pokeBallItem(PokeBalls.ANCIENT_WING_BALL)
    public static final ANCIENT_JET_BALL = pokeBallItem(PokeBalls.ANCIENT_JET_BALL)
    public static final ANCIENT_HEAVY_BALL = pokeBallItem(PokeBalls.ANCIENT_HEAVY_BALL)
    public static final ANCIENT_LEADEN_BALL = pokeBallItem(PokeBalls.ANCIENT_LEADEN_BALL)
    public static final ANCIENT_GIGATON_BALL = pokeBallItem(PokeBalls.ANCIENT_GIGATON_BALL)
    public static final ANCIENT_ORIGIN_BALL = pokeBallItem(PokeBalls.ANCIENT_ORIGIN_BALL)

    public static final pokedexes = mutableListOf<PokedexItem>()
    public static final POKEDEX_RED = pokedexItem(PokedexType.RED)
    public static final POKEDEX_YELLOW = pokedexItem(PokedexType.YELLOW)
    public static final POKEDEX_GREEN = pokedexItem(PokedexType.GREEN)
    public static final POKEDEX_BLUE = pokedexItem(PokedexType.BLUE)
    public static final POKEDEX_PINK = pokedexItem(PokedexType.PINK)
    public static final POKEDEX_BLACK = pokedexItem(PokedexType.BLACK)
    public static final POKEDEX_WHITE = pokedexItem(PokedexType.WHITE)

    public static final campfire_pots = mutableListOf<CampfirePotItem>()
    public static final CAMPFIRE_POT_RED = campfirePotItem(CobblemonBlocks.RED_CAMPFIRE_POT, CampfirePotColor.RED)
    public static final CAMPFIRE_POT_YELLOW = campfirePotItem(CobblemonBlocks.YELLOW_CAMPFIRE_POT, CampfirePotColor.YELLOW)
    public static final CAMPFIRE_POT_GREEN = campfirePotItem(CobblemonBlocks.GREEN_CAMPFIRE_POT, CampfirePotColor.GREEN)
    public static final CAMPFIRE_POT_BLUE = campfirePotItem(CobblemonBlocks.BLUE_CAMPFIRE_POT, CampfirePotColor.BLUE)
    public static final CAMPFIRE_POT_PINK = campfirePotItem(CobblemonBlocks.PINK_CAMPFIRE_POT, CampfirePotColor.PINK)
    public static final CAMPFIRE_POT_BLACK = campfirePotItem(CobblemonBlocks.BLACK_CAMPFIRE_POT, CampfirePotColor.BLACK)
    public static final CAMPFIRE_POT_WHITE = campfirePotItem(CobblemonBlocks.WHITE_CAMPFIRE_POT, CampfirePotColor.WHITE)

    public static final HEARTY_GRAINS = compostableItem("hearty_grains", ItemNameBlockItem(CobblemonBlocks.HEARTY_GRAINS, Properties().rarity(Rarity.COMMON)))
    public static final HEARTY_GRAIN_BALE = compostableBlockItem("hearty_grain_bale", CobblemonBlocks.HEARTY_GRAIN_BALE, 0.85f)

    public static final TATAMI_BLOCK = blockItem("tatami_block", CobblemonBlocks.TATAMI_BLOCK)
    public static final TATAMI_MAT = blockItem("tatami_mat", CobblemonBlocks.TATAMI_MAT)

    public static final VIVICHOKE = compostableItem("vivichoke", CobblemonItem(Item.Properties()), 0.80f)

    public static final VIVICHOKE_SEEDS = compostableItem("vivichoke_seeds", VivichokeItem(CobblemonBlocks.VIVICHOKE_SEEDS), 0.30f)

    public static final RED_APRICORN = apricornItem("red", ApricornItem(CobblemonBlocks.RED_APRICORN))
    public static final YELLOW_APRICORN = apricornItem("yellow", ApricornItem(CobblemonBlocks.YELLOW_APRICORN))
    public static final GREEN_APRICORN = apricornItem("green", ApricornItem(CobblemonBlocks.GREEN_APRICORN))
    public static final BLUE_APRICORN = apricornItem("blue", ApricornItem(CobblemonBlocks.BLUE_APRICORN))
    public static final PINK_APRICORN = apricornItem("pink", ApricornItem(CobblemonBlocks.PINK_APRICORN))
    public static final BLACK_APRICORN = apricornItem("black", ApricornItem(CobblemonBlocks.BLACK_APRICORN))
    public static final WHITE_APRICORN = apricornItem("white", ApricornItem(CobblemonBlocks.WHITE_APRICORN))

    public static final RED_APRICORN_SEED = apricornSeedItem("red", ApricornSeedItem(CobblemonBlocks.RED_APRICORN_SAPLING, CobblemonBlocks.RED_APRICORN))
    public static final YELLOW_APRICORN_SEED = apricornSeedItem("yellow", ApricornSeedItem(CobblemonBlocks.YELLOW_APRICORN_SAPLING, CobblemonBlocks.YELLOW_APRICORN))
    public static final GREEN_APRICORN_SEED = apricornSeedItem("green", ApricornSeedItem(CobblemonBlocks.GREEN_APRICORN_SAPLING, CobblemonBlocks.GREEN_APRICORN))
    public static final BLUE_APRICORN_SEED = apricornSeedItem("blue", ApricornSeedItem(CobblemonBlocks.BLUE_APRICORN_SAPLING, CobblemonBlocks.BLUE_APRICORN))
    public static final PINK_APRICORN_SEED = apricornSeedItem("pink", ApricornSeedItem(CobblemonBlocks.PINK_APRICORN_SAPLING, CobblemonBlocks.PINK_APRICORN))
    public static final BLACK_APRICORN_SEED = apricornSeedItem("black", ApricornSeedItem(CobblemonBlocks.BLACK_APRICORN_SAPLING, CobblemonBlocks.BLACK_APRICORN))
    public static final WHITE_APRICORN_SEED = apricornSeedItem("white", ApricornSeedItem(CobblemonBlocks.WHITE_APRICORN_SAPLING, CobblemonBlocks.WHITE_APRICORN))

    public static final APRICORN_LOG = blockItem("apricorn_log", CobblemonBlocks.APRICORN_LOG)
    public static final STRIPPED_APRICORN_LOG = blockItem("stripped_apricorn_log", CobblemonBlocks.STRIPPED_APRICORN_LOG)
    public static final APRICORN_WOOD = blockItem("apricorn_wood", CobblemonBlocks.APRICORN_WOOD)
    public static final STRIPPED_APRICORN_WOOD = blockItem("stripped_apricorn_wood", CobblemonBlocks.STRIPPED_APRICORN_WOOD)
    public static final APRICORN_PLANKS = blockItem("apricorn_planks", CobblemonBlocks.APRICORN_PLANKS)
    public static final APRICORN_LEAVES = compostableBlockItem("apricorn_leaves", CobblemonBlocks.APRICORN_LEAVES, 0.30f)
    public static final APRICORN_BOAT = create("apricorn_boat", CobblemonBoatItem(CobblemonBoatType.APRICORN, false, Item.Properties().stacksTo(1)))
    public static final APRICORN_CHEST_BOAT = create("apricorn_chest_boat", CobblemonBoatItem(CobblemonBoatType.APRICORN, true, Item.Properties().stacksTo(1)))

    public static final APRICORN_DOOR = blockItem("apricorn_door", CobblemonBlocks.APRICORN_DOOR)
    public static final APRICORN_TRAPDOOR = blockItem("apricorn_trapdoor", CobblemonBlocks.APRICORN_TRAPDOOR)
    public static final APRICORN_FENCE = blockItem("apricorn_fence", CobblemonBlocks.APRICORN_FENCE)
    public static final APRICORN_FENCE_GATE = blockItem("apricorn_fence_gate", CobblemonBlocks.APRICORN_FENCE_GATE)
    public static final APRICORN_BUTTON = blockItem("apricorn_button", CobblemonBlocks.APRICORN_BUTTON)
    public static final APRICORN_PRESSURE_PLATE = blockItem("apricorn_pressure_plate", CobblemonBlocks.APRICORN_PRESSURE_PLATE)
    public static final APRICORN_SLAB = blockItem("apricorn_slab", CobblemonBlocks.APRICORN_SLAB)
    public static final APRICORN_STAIRS = blockItem("apricorn_stairs", CobblemonBlocks.APRICORN_STAIRS)
    public static final APRICORN_SIGN = this.create("apricorn_sign", SignItem(Item.Properties().stacksTo(16), CobblemonBlocks.APRICORN_SIGN, CobblemonBlocks.APRICORN_WALL_SIGN))
    public static final APRICORN_HANGING_SIGN = this.create("apricorn_hanging_sign", HangingSignItem(CobblemonBlocks.APRICORN_HANGING_SIGN, CobblemonBlocks.APRICORN_WALL_HANGING_SIGN, Item.Properties().stacksTo(16)))
    public static final GILDED_CHEST = this.create("gilded_chest", BlockItem(CobblemonBlocks.GILDED_CHEST, Item.Properties()))
    public static final BLUE_GILDED_CHEST = this.create("blue_gilded_chest", BlockItem(CobblemonBlocks.BLUE_GILDED_CHEST, Item.Properties()))
    public static final YELLOW_GILDED_CHEST = this.create("yellow_gilded_chest", BlockItem(CobblemonBlocks.YELLOW_GILDED_CHEST, Item.Properties()))
    public static final PINK_GILDED_CHEST = this.create("pink_gilded_chest", BlockItem(CobblemonBlocks.PINK_GILDED_CHEST, Item.Properties()))
    public static final BLACK_GILDED_CHEST = this.create("black_gilded_chest", BlockItem(CobblemonBlocks.BLACK_GILDED_CHEST, Item.Properties()))
    public static final WHITE_GILDED_CHEST = this.create("white_gilded_chest", BlockItem(CobblemonBlocks.WHITE_GILDED_CHEST, Item.Properties()))
    public static final GREEN_GILDED_CHEST = this.create("green_gilded_chest", BlockItem(CobblemonBlocks.GREEN_GILDED_CHEST, Item.Properties()))
    public static final GIMMIGHOUL_CHEST = this.create("gimmighoul_chest", BlockItem(CobblemonBlocks.GIMMIGHOUL_CHEST, Item.Properties()))

        // Saccharines
    public static final SACCHARINE_LOG = blockItem("saccharine_log", CobblemonBlocks.SACCHARINE_LOG)
    public static final SACCHARINE_LOG_SLATHERED = blockItem("saccharine_log_slathered", CobblemonBlocks.SACCHARINE_LOG_SLATHERED)
    public static final STRIPPED_SACCHARINE_LOG = blockItem("stripped_saccharine_log", CobblemonBlocks.STRIPPED_SACCHARINE_LOG)
    public static final SACCHARINE_WOOD = blockItem("saccharine_wood", CobblemonBlocks.SACCHARINE_WOOD)
    public static final STRIPPED_SACCHARINE_WOOD = blockItem("stripped_saccharine_wood", CobblemonBlocks.STRIPPED_SACCHARINE_WOOD)
    public static final SACCHARINE_PLANKS = blockItem("saccharine_planks", CobblemonBlocks.SACCHARINE_PLANKS)
    public static final SACCHARINE_LEAVES = compostableBlockItem("saccharine_leaves", CobblemonBlocks.SACCHARINE_LEAVES, 0.35f)
    public static final SACCHARINE_BOAT = create("saccharine_boat", CobblemonBoatItem(CobblemonBoatType.SACCHARINE, false, Item.Properties().stacksTo(1)))
    public static final SACCHARINE_CHEST_BOAT = create("saccharine_chest_boat", CobblemonBoatItem(CobblemonBoatType.SACCHARINE, true, Item.Properties().stacksTo(1)))

    public static final SACCHARINE_DOOR = blockItem("saccharine_door", CobblemonBlocks.SACCHARINE_DOOR)
    public static final SACCHARINE_TRAPDOOR = blockItem("saccharine_trapdoor", CobblemonBlocks.SACCHARINE_TRAPDOOR)
    public static final SACCHARINE_FENCE = blockItem("saccharine_fence", CobblemonBlocks.SACCHARINE_FENCE)
    public static final SACCHARINE_FENCE_GATE = blockItem("saccharine_fence_gate", CobblemonBlocks.SACCHARINE_FENCE_GATE)
    public static final SACCHARINE_BUTTON = blockItem("saccharine_button", CobblemonBlocks.SACCHARINE_BUTTON)
    public static final SACCHARINE_PRESSURE_PLATE = blockItem("saccharine_pressure_plate", CobblemonBlocks.SACCHARINE_PRESSURE_PLATE)
    public static final SACCHARINE_SLAB = blockItem("saccharine_slab", CobblemonBlocks.SACCHARINE_SLAB)
    public static final SACCHARINE_STAIRS = blockItem("saccharine_stairs", CobblemonBlocks.SACCHARINE_STAIRS)
    public static final SACCHARINE_SIGN = this.create("saccharine_sign", SignItem(Item.Properties().stacksTo(16), CobblemonBlocks.SACCHARINE_SIGN, CobblemonBlocks.SACCHARINE_WALL_SIGN))
    public static final SACCHARINE_HANGING_SIGN = this.create("saccharine_hanging_sign", HangingSignItem(CobblemonBlocks.SACCHARINE_HANGING_SIGN, CobblemonBlocks.SACCHARINE_WALL_HANGING_SIGN, Item.Properties().stacksTo(16)))
    public static final SACCHARINE_SAPLING = compostableBlockItem("saccharine_sapling", CobblemonBlocks.SACCHARINE_SAPLING, 0.30f)

    ////   public static final BUGWORT = compostableBlockItem("bugwort", CobblemonBlocks.BUGWORT) // TODO after 1.7
    public static final POKE_BAIT = noSettingsItem("poke_bait");

    public static final POKE_CAKE = this.create("poke_cake", BlockItem(CobblemonBlocks.POKE_CAKE, Item.Properties().stacksTo(1)))
    public static final POKE_SNACK = this.create("poke_snack", BlockItem(CobblemonBlocks.POKE_SNACK, Item.Properties().stacksTo(16)))

    public static final aprijuices = mutableListOf<AprijuiceItem>()
    public static final APRIJUICE_RED = aprijuiceItem(Apricorn.RED)
    public static final APRIJUICE_YELLOW = aprijuiceItem(Apricorn.YELLOW)
    public static final APRIJUICE_GREEN = aprijuiceItem(Apricorn.GREEN)
    public static final APRIJUICE_BLUE = aprijuiceItem(Apricorn.BLUE)
    public static final APRIJUICE_PINK = aprijuiceItem(Apricorn.PINK)
    public static final APRIJUICE_BLACK = aprijuiceItem(Apricorn.BLACK)
    public static final APRIJUICE_WHITE = aprijuiceItem(Apricorn.WHITE)

    public static final PONIGIRI = create("ponigiri", PonigiriItem())

    public static final SINISTER_TEA = create("sinister_tea", SinisterTeaItem())

    public static final POKE_PUFF = pokepuffItem("poke_puff");

    // FOODS
    public static final SWEET_HEART = noSettingsItem("sweet_heart"); // TODO make a SweetHeartItem class for breeding purposes

    public static final TASTY_TAIL = create("tasty_tail", foodItem(3, 0.3f));

    public static final PEWTER_CRUNCHIES = regionalFoodItem("pewter_crunchies", 64, 7, 0.3f, false);
    public static final RAGE_CANDY_BAR = regionalFoodItem("rage_candy_bar", 64, 8, 0.2f, false);
    public static final LAVA_COOKIE = regionalFoodItem("lava_cookie", 64, 2, 0.3f, false);
    public static final OLD_GATEAU = regionalFoodItem("old_gateau", 64, 9, 0.1f, false);
    public static final CASTELIACONE = regionalFoodItem("casteliacone", 64, 5, 0.3f, false);
    public static final LUMIOSE_GALETTE = regionalFoodItem("lumiose_galette", 64, 4, 0.4f, false);
    public static final BIG_MALASADA = regionalFoodItem("big_malasada", 64, 7, 0.3f, false);
    public static final SMOKED_TAIL_CURRY = regionalFoodItem("smoked_tail_curry", 64, 10, 0.6f, false, ItemStack(Items.BOWL, 1));
    public static final JUBILIFE_MUFFIN = regionalFoodItem("jubilife_muffin", 64, 7, 0.3f, false);
    public static final OPEN_FACED_SANDWICH = regionalFoodItem("open_faced_sandwich", 64, 13, 0.5f, false);
    public static final CHOICE_DUMPLING = noSettingsItem("choice_dumpling");; // todo make a ChoiceDumpingItem class for battle purposes
    public static final SWAP_SNACK = noSettingsItem("swap_snack");; // todo make a SwapSnackItem class for battle purposes
    public static final TWICE_SPICED_BEETROOT = noSettingsItem("twice_spiced_beetroot");; // todo make a TwiceSpiceBeetrootItem class for battle purposes
    public static final POTATO_MOCHI = create("potato_mochi", CobblemonItem(Properties().stacksTo(64).food(FoodProperties.Builder().nutrition(4).saturationModifier(0.4F).build())));
    public static final CANDIED_APPLE = create("candied_apple",  CobblemonItem(Properties().stacksTo(64).food(FoodProperties.Builder().nutrition(6).saturationModifier(0.2F).usingConvertsTo(Items.STICK).build())));
    public static final CANDIED_BERRY = create("candied_berry",  CobblemonItem(Properties().stacksTo(64).food(FoodProperties.Builder().nutrition(5).saturationModifier(0.22F).usingConvertsTo(Items.STICK).build())));

    public static final SCATTER_BANG = this.create("scatter_bang", ScatterBangItem(Item.Settings()));

    public static final STICKY_GLOB = this.create("sticky_glob", StickyGlobItem(Item.Settings()));

    public static final RESTORATION_TANK = blockItem("restoration_tank", CobblemonBlocks.RESTORATION_TANK);
    public static final FOSSIL_ANALYZER = blockItem("fossil_analyzer", CobblemonBlocks.FOSSIL_ANALYZER);
    public static final MONITOR = blockItem("monitor", CobblemonBlocks.MONITOR);
    public static final HEALING_MACHINE = blockItem("healing_machine", CobblemonBlocks.HEALING_MACHINE, Rarity.UNCOMMON);
    public static final PC = blockItem("pc", CobblemonBlocks.PC);
    public static final PASTURE = blockItem("pasture", CobblemonBlocks.PASTURE);
    public static final DISPLAY_CASE = blockItem("display_case", CobblemonBlocks.DISPLAY_CASE);
    public static final INCENSE_SWEET = blockItem("incense_sweet", CobblemonBlocks.INCENSE_SWEET);

    // Evolution items
    public static final LINK_CABLE = create("link_cable", LinkCableItem())
    public static final DRAGON_SCALE = noSettingsItem("dragon_scale");
    public static final METAL_COAT = noSettingsItem("metal_coat");
    public static final UPGRADE = noSettingsItem("upgrade");
    public static final DUBIOUS_DISC = noSettingsItem("dubious_disc");
    public static final DEEP_SEA_SCALE = noSettingsItem("deep_sea_scale");
    public static final DEEP_SEA_TOOTH = noSettingsItem("deep_sea_tooth");
    public static final ELECTIRIZER = noSettingsItem("electirizer");
    public static final MAGMARIZER = noSettingsItem("magmarizer");
    public static final OVAL_STONE = noSettingsItem("oval_stone");
    public static final PROTECTOR = noSettingsItem("protector");
    public static final REAPER_CLOTH = noSettingsItem("reaper_cloth");
    public static final PRISM_SCALE = noSettingsItem("prism_scale");
    public static final SACHET = noSettingsItem("sachet");
    public static final WHIPPED_DREAM = create("whipped_dream", foodItem(8, 0.3f))
    public static final STRAWBERRY_SWEET = create("strawberry_sweet", foodItem(6, 0.125f))
    public static final LOVE_SWEET = create("love_sweet", foodItem(6, 0.125f))
    public static final BERRY_SWEET = create("berry_sweet", foodItem(6, 0.125f))
    public static final CLOVER_SWEET = create("clover_sweet", foodItem(6, 0.125f))
    public static final FLOWER_SWEET = create("flower_sweet", foodItem(6, 0.125f))
    public static final STAR_SWEET = create("star_sweet", foodItem(6, 0.125f))
    public static final RIBBON_SWEET = create("ribbon_sweet", foodItem(6, 0.125f))
    public static final CHIPPED_POT = noSettingsItem("chipped_pot");
    public static final CRACKED_POT = noSettingsItem("cracked_pot");
    public static final MASTERPIECE_TEACUP = noSettingsItem("masterpiece_teacup");
    public static final UNREMARKABLE_TEACUP = noSettingsItem("unremarkable_teacup");
    public static final SWEET_APPLE = compostableItem("sweet_apple", foodItem(4, 0.3f), 0.65f)
    public static final TART_APPLE = compostableItem("tart_apple", foodItem(4, 0.3f), 0.65f)
    public static final SYRUPY_APPLE = compostableItem("syrupy_apple", foodItem(4, 0.3f), 0.65f)
    public static final GALARICA_CUFF = noSettingsItem("galarica_cuff");
    public static final GALARICA_WREATH = noSettingsItem("galarica_wreath");
    public static final BLACK_AUGURITE = noSettingsItem("black_augurite");
    public static final PEAT_BLOCK = noSettingsItem("peat_block");
    public static final RAZOR_CLAW = noSettingsItem("razor_claw");
    public static final RAZOR_FANG = noSettingsItem("razor_fang");
    public static final AUSPICIOUS_ARMOR = heldItem("auspicious_armor");
    public static final MALICIOUS_ARMOR = heldItem("malicious_armor");
    public static final SHELL_HELMET = heldItem("shell_helmet");
    public static final METAL_ALLOY = noSettingsItem("metal_alloy");
    public static final SCROLL_OF_DARKNESS = noSettingsItem("scroll_of_darkness");
    public static final SCROLL_OF_WATERS = noSettingsItem("scroll_of_waters");

    private static final berries = mutableMapOf<ResourceLocation, BerryItem>();
    // Plants
    public static final CHERI_BERRY = berryItem("cheri", StatusCuringBerryItem(CobblemonBlocks.CHERI_BERRY, Statuses.PARALYSIS));
    public static final CHESTO_BERRY = berryItem("chesto", StatusCuringBerryItem(CobblemonBlocks.CHESTO_BERRY, Statuses.SLEEP));
    public static final PECHA_BERRY = berryItem("pecha", StatusCuringBerryItem(CobblemonBlocks.PECHA_BERRY, Statuses.POISON, Statuses.POISON_BADLY));
    public static final RAWST_BERRY = berryItem("rawst", StatusCuringBerryItem(CobblemonBlocks.RAWST_BERRY, Statuses.BURN));
    public static final ASPEAR_BERRY = berryItem("aspear", StatusCuringBerryItem(CobblemonBlocks.ASPEAR_BERRY, Statuses.FROZEN));
    public static final LEPPA_BERRY = berryItem("leppa", PPRestoringBerryItem(CobblemonBlocks.LEPPA_BERRY); { CobblemonMechanics.berries.ppRestoreAmount });
    public static final ORAN_BERRY = berryItem("oran", HealingBerryItem(CobblemonBlocks.ORAN_BERRY); { CobblemonMechanics.berries.oranRestoreAmount });
    public static final PERSIM_BERRY = berryItem("persim", StatusCuringBerryItem(CobblemonBlocks.PERSIM_BERRY, Statuses.CONFUSE));
    public static final LUM_BERRY = berryItem("lum", StatusCuringBerryItem(CobblemonBlocks.LUM_BERRY, *(Statuses.getPersistentStatuses().toTypedArray() + Statuses.CONFUSE)));
    public static final SITRUS_BERRY = berryItem("sitrus", HealingBerryItem(CobblemonBlocks.SITRUS_BERRY); { CobblemonMechanics.berries.sitrusHealAmount });
    public static final EGGANT_BERRY = berryItem("eggant", StatusCuringBerryItem(CobblemonBlocks.EGGANT_BERRY, Statuses.ATTRACT));
    public static final FIGY_BERRY = berryItem("figy", PortionHealingBerryItem(CobblemonBlocks.FIGY_BERRY, true) { CobblemonMechanics.berries.portionHealRatio });
    public static final WIKI_BERRY = berryItem("wiki", PortionHealingBerryItem(CobblemonBlocks.WIKI_BERRY, true) { CobblemonMechanics.berries.portionHealRatio });
    public static final MAGO_BERRY = berryItem("mago", PortionHealingBerryItem(CobblemonBlocks.MAGO_BERRY, true) { CobblemonMechanics.berries.portionHealRatio });
    public static final AGUAV_BERRY = berryItem("aguav", PortionHealingBerryItem(CobblemonBlocks.AGUAV_BERRY, true) { CobblemonMechanics.berries.portionHealRatio });
    public static final IAPAPA_BERRY = berryItem("iapapa", PortionHealingBerryItem(CobblemonBlocks.IAPAPA_BERRY, true) { CobblemonMechanics.berries.portionHealRatio });
    public static final RAZZ_BERRY = berryItem("razz", CobblemonBlocks.RAZZ_BERRY);
    public static final BLUK_BERRY = berryItem("bluk", CobblemonBlocks.BLUK_BERRY);
    public static final NANAB_BERRY = berryItem("nanab", CobblemonBlocks.NANAB_BERRY);
    public static final WEPEAR_BERRY = berryItem("wepear", CobblemonBlocks.WEPEAR_BERRY);
    public static final PINAP_BERRY = berryItem("pinap", CobblemonBlocks.PINAP_BERRY);
    public static final POMEG_BERRY = berryItem("pomeg", FriendshipRaisingBerryItem(CobblemonBlocks.POMEG_BERRY, Stats.HP));
    public static final KELPSY_BERRY = berryItem("kelpsy", FriendshipRaisingBerryItem(CobblemonBlocks.KELPSY_BERRY, Stats.ATTACK));
    public static final QUALOT_BERRY = berryItem("qualot", FriendshipRaisingBerryItem(CobblemonBlocks.QUALOT_BERRY, Stats.DEFENCE));
    public static final HONDEW_BERRY = berryItem("hondew", FriendshipRaisingBerryItem(CobblemonBlocks.HONDEW_BERRY, Stats.SPECIAL_ATTACK));
    public static final GREPA_BERRY = berryItem("grepa", FriendshipRaisingBerryItem(CobblemonBlocks.GREPA_BERRY, Stats.SPECIAL_DEFENCE));
    public static final TAMATO_BERRY = berryItem("tamato", FriendshipRaisingBerryItem(CobblemonBlocks.TAMATO_BERRY, Stats.SPEED));
    public static final TOUGA_BERRY = berryItem("touga", CobblemonBlocks.TOUGA_BERRY);
    public static final CORNN_BERRY = berryItem("cornn", CobblemonBlocks.CORNN_BERRY);
    public static final MAGOST_BERRY = berryItem("magost", CobblemonBlocks.MAGOST_BERRY);
    public static final RABUTA_BERRY = berryItem("rabuta", CobblemonBlocks.RABUTA_BERRY);
    public static final NOMEL_BERRY = berryItem("nomel", CobblemonBlocks.NOMEL_BERRY);
    public static final SPELON_BERRY = berryItem("spelon", CobblemonBlocks.SPELON_BERRY);
    public static final PAMTRE_BERRY = berryItem("pamtre", CobblemonBlocks.PAMTRE_BERRY);
    public static final WATMEL_BERRY = berryItem("watmel", CobblemonBlocks.WATMEL_BERRY);
    public static final DURIN_BERRY = berryItem("durin", CobblemonBlocks.DURIN_BERRY);
    public static final BELUE_BERRY = berryItem("belue", CobblemonBlocks.BELUE_BERRY);
    public static final OCCA_BERRY = berryItem("occa", CobblemonBlocks.OCCA_BERRY);
    public static final PASSHO_BERRY = berryItem("passho", CobblemonBlocks.PASSHO_BERRY);
    public static final WACAN_BERRY = berryItem("wacan", CobblemonBlocks.WACAN_BERRY);
    public static final RINDO_BERRY = berryItem("rindo", CobblemonBlocks.RINDO_BERRY);
    public static final YACHE_BERRY = berryItem("yache", CobblemonBlocks.YACHE_BERRY);
    public static final CHOPLE_BERRY = berryItem("chople", CobblemonBlocks.CHOPLE_BERRY);
    public static final KEBIA_BERRY = berryItem("kebia", CobblemonBlocks.KEBIA_BERRY);
    public static final SHUCA_BERRY = berryItem("shuca", CobblemonBlocks.SHUCA_BERRY);
    public static final COBA_BERRY = berryItem("coba", CobblemonBlocks.COBA_BERRY);
    public static final PAYAPA_BERRY = berryItem("payapa", CobblemonBlocks.PAYAPA_BERRY);
    public static final TANGA_BERRY = berryItem("tanga", CobblemonBlocks.TANGA_BERRY);
    public static final CHARTI_BERRY = berryItem("charti", CobblemonBlocks.CHARTI_BERRY);
    public static final KASIB_BERRY = berryItem("kasib", CobblemonBlocks.KASIB_BERRY);
    public static final HABAN_BERRY = berryItem("haban", CobblemonBlocks.HABAN_BERRY);
    public static final COLBUR_BERRY = berryItem("colbur", CobblemonBlocks.COLBUR_BERRY);
    public static final BABIRI_BERRY = berryItem("babiri", CobblemonBlocks.BABIRI_BERRY);
    public static final CHILAN_BERRY = berryItem("chilan", CobblemonBlocks.CHILAN_BERRY);
    public static final LIECHI_BERRY = berryItem("liechi", CobblemonBlocks.LIECHI_BERRY);
    public static final GANLON_BERRY = berryItem("ganlon", CobblemonBlocks.GANLON_BERRY);
    public static final SALAC_BERRY = berryItem("salac", CobblemonBlocks.SALAC_BERRY);
    public static final PETAYA_BERRY = berryItem("petaya", CobblemonBlocks.PETAYA_BERRY);
    public static final APICOT_BERRY = berryItem("apicot", CobblemonBlocks.APICOT_BERRY);
    public static final LANSAT_BERRY = berryItem("lansat", CobblemonBlocks.LANSAT_BERRY);
    public static final STARF_BERRY = berryItem("starf", CobblemonBlocks.STARF_BERRY);
    public static final ENIGMA_BERRY = berryItem("enigma", CobblemonBlocks.ENIGMA_BERRY);
    public static final MICLE_BERRY = berryItem("micle", CobblemonBlocks.MICLE_BERRY);
    public static final CUSTAP_BERRY = berryItem("custap", CobblemonBlocks.CUSTAP_BERRY);
    public static final JABOCA_BERRY = berryItem("jaboca", CobblemonBlocks.JABOCA_BERRY);
    public static final ROWAP_BERRY = berryItem("rowap", CobblemonBlocks.ROWAP_BERRY);
    public static final ROSELI_BERRY = berryItem("roseli", CobblemonBlocks.ROSELI_BERRY);
    public static final KEE_BERRY = berryItem("kee", CobblemonBlocks.KEE_BERRY);
    public static final MARANGA_BERRY = berryItem("maranga", CobblemonBlocks.MARANGA_BERRY);
    public static final HOPO_BERRY = berryItem("hopo", PPRestoringBerryItem(CobblemonBlocks.HOPO_BERRY); { CobblemonMechanics.berries.ppRestoreAmount });

    public static final BERRY_JUICE = this.create("berry_juice", BerryJuiceItem());

    public static final GALARICA_NUTS = compostableItemNameBlockItem("galarica_nuts", CobblemonBlocks.GALARICA_NUT_BUSH);

    // Hyper Training Items
    public static final hyperTrainingItems = mutableListOf<HyperTrainingItem>();
    public static final HEALTH_CANDY = hyperTrainingItem("health_candy", 1, setOf(Stats.HP), 0..IVs.MAX_VALUE);
    public static final MIGHTY_CANDY = hyperTrainingItem("mighty_candy", 1, setOf(Stats.ATTACK), 0..IVs.MAX_VALUE);
    public static final TOUGH_CANDY = hyperTrainingItem("tough_candy", 1, setOf(Stats.DEFENCE), 0..IVs.MAX_VALUE);
    public static final SMART_CANDY = hyperTrainingItem("smart_candy", 1, setOf(Stats.SPECIAL_ATTACK), 0..IVs.MAX_VALUE);
    public static final COURAGE_CANDY = hyperTrainingItem("courage_candy", 1, setOf(Stats.SPECIAL_DEFENCE), 0..IVs.MAX_VALUE);
    public static final QUICK_CANDY = hyperTrainingItem("quick_candy", 1, setOf(Stats.SPEED), 0..IVs.MAX_VALUE);
    public static final SICKLY_CANDY = hyperTrainingItem("sickly_candy", -1, setOf(Stats.HP), 0..IVs.MAX_VALUE);
    public static final WEAK_CANDY = hyperTrainingItem("weak_candy", -1, setOf(Stats.ATTACK), 0..IVs.MAX_VALUE);
    public static final BRITTLE_CANDY = hyperTrainingItem("brittle_candy", -1, setOf(Stats.DEFENCE), 0..IVs.MAX_VALUE);
    public static final NUMB_CANDY = hyperTrainingItem("numb_candy", -1, setOf(Stats.SPECIAL_ATTACK), 0..IVs.MAX_VALUE);
    public static final COWARD_CANDY = hyperTrainingItem("coward_candy", -1, setOf(Stats.SPECIAL_DEFENCE), 0..IVs.MAX_VALUE);
    public static final SLOW_CANDY = hyperTrainingItem("slow_candy", -1, setOf(Stats.SPEED), 0..IVs.MAX_VALUE);

    // Medicine
    public static final RARE_CANDY = candyItem("rare_candy", Rarity.RARE) { _, pokemon -> pokemon.getExperienceToNextLevel() };
    public static final EXPERIENCE_CANDY_XS = candyItem("exp_candy_xs"); { _, _ -> CandyItem.DEFAULT_XS_CANDY_YIELD };
    public static final EXPERIENCE_CANDY_S = candyItem("exp_candy_s"); { _, _ -> CandyItem.DEFAULT_S_CANDY_YIELD };
    public static final EXPERIENCE_CANDY_M = candyItem("exp_candy_m"); { _, _ -> CandyItem.DEFAULT_M_CANDY_YIELD };
    public static final EXPERIENCE_CANDY_L = candyItem("exp_candy_l"); { _, _ -> CandyItem.DEFAULT_L_CANDY_YIELD };
    public static final EXPERIENCE_CANDY_XL = candyItem("exp_candy_xl"); { _, _ -> CandyItem.DEFAULT_XL_CANDY_YIELD };
    public static final CALCIUM = create("calcium", VitaminItem(Stats.SPECIAL_ATTACK));
    public static final CARBOS = create("carbos", VitaminItem(Stats.SPEED));
    public static final HP_UP = create("hp_up", VitaminItem(Stats.HP));
    public static final IRON = create("iron", VitaminItem(Stats.DEFENCE));
    public static final PROTEIN = create("protein", VitaminItem(Stats.ATTACK));
    public static final ZINC = create("zinc", VitaminItem(Stats.SPECIAL_DEFENCE));
    public static final HEALTH_MOCHI = create("health_mochi", MochiItem(Stats.HP));
    public static final MUSCLE_MOCHI = create("muscle_mochi", MochiItem(Stats.ATTACK));
    public static final RESIST_MOCHI = create("resist_mochi", MochiItem(Stats.DEFENCE));
    public static final GENIUS_MOCHI = create("genius_mochi", MochiItem(Stats.SPECIAL_ATTACK));
    public static final CLEVER_MOCHI = create("clever_mochi", MochiItem(Stats.SPECIAL_DEFENCE));
    public static final SWIFT_MOCHI = create("swift_mochi", MochiItem(Stats.SPEED));
    public static final FRESH_START_MOCHI = create("fresh_start_mochi", FreshStartMochiItem());
    public static final GENIUS_FEATHER = create("genius_feather", FeatherItem(Stats.SPECIAL_ATTACK));
    public static final SWIFT_FEATHER = create("swift_feather", FeatherItem(Stats.SPEED));
    public static final HEALTH_FEATHER = create("health_feather", FeatherItem(Stats.HP));
    public static final RESIST_FEATHER = create("resist_feather", FeatherItem(Stats.DEFENCE));
    public static final MUSCLE_FEATHER = create("muscle_feather", FeatherItem(Stats.ATTACK));
    public static final CLEVER_FEATHER = create("clever_feather", FeatherItem(Stats.SPECIAL_DEFENCE));
    public static final MEDICINAL_LEEK = heldItem("medicinal_leek", MedicinalLeekItem(CobblemonBlocks.MEDICINAL_LEEK, Item.Properties().food(FoodProperties.Builder().fast().nutrition(1).saturationModifier(0.2f).build())), "leek");
    public static final ROASTED_LEEK = compostableItem("roasted_leek", CobblemonItem(Item.Properties().food(FoodProperties.Builder().fast().nutrition(3).saturationModifier(0.2f).build())), 0.85f)
    public static final VIVICHOKE_DIP = create("vivichoke_dip", object : CobblemonItem(Properties().stacksTo(1).food(FoodProperties.Builder().nutrition(10).saturationModifier(0.6F).effect(MobEffectInstance(MobEffects.ABSORPTION, 900, 0), 1F).alwaysEdible().usingConvertsTo(Items.BOWL).build())) {
        override fun finishUsingItem(ItemStack stack, Level world, user: LivingEntity): ItemStack {
            user.removeAllEffects()
            return super.finishUsingItem(stack, world, user)
        }
    })
    public static final ENERGY_ROOT = compostableItem("energy_root", EnergyRootItem(CobblemonBlocks.ENERGY_ROOT, Properties()), 0.65f);
    public static final REVIVAL_HERB = compostableItem("revival_herb", RevivalHerbItem(CobblemonBlocks.REVIVAL_HERB));
    public static final PEP_UP_FLOWER = compostableBlockItem("pep_up_flower", CobblemonBlocks.PEP_UP_FLOWER);
    public static final MEDICINAL_BREW = noSettingsItem("medicinal_brew");
    public static final REMEDY = compostableItem("remedy", RemedyItem(RemedyItem.NORMAL), 0.65f);
    public static final FINE_REMEDY = compostableItem("fine_remedy", RemedyItem(RemedyItem.FINE), 0.85f);
    public static final SUPERB_REMEDY = compostableItem("superb_remedy", RemedyItem(RemedyItem.SUPERB), 1f);
    public static final MOOMOO_MILK = create("moomoo_milk", MoomooMilk(Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo((16))));

    public static final POTION = create("potion", PotionItem(PotionType.POTION));
    public static final SUPER_POTION = create("super_potion", PotionItem(PotionType.SUPER_POTION));
    public static final HYPER_POTION = create("hyper_potion", PotionItem(PotionType.HYPER_POTION));
    public static final MAX_POTION = create("max_potion", PotionItem(PotionType.MAX_POTION));
    public static final FULL_RESTORE = create("full_restore", PotionItem(PotionType.FULL_RESTORE));

    public static final HEAL_POWDER = create("heal_powder", HealPowderItem());
    public static final LEEK_AND_POTATO_STEW = create("leek_and_potato_stew", CobblemonItem(Item.Properties().food(FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).usingConvertsTo(Items.BOWL).build()).stacksTo(1)));
    public static final REVIVE = create("revive", ReviveItem(max = false));
    public static final MAX_REVIVE = create("max_revive", ReviveItem(max = true));
    public static final PP_UP = create("pp_up", PPUpItem(1));
    public static final PP_MAX = create("pp_max", PPUpItem(3));

    public static final RED_MINT_SEEDS = mintSeed("red", MintType.RED.getCropBlock());
    public static final RED_MINT_LEAF = mintLeaf("red", MintLeafItem(MintType.RED));
    public static final BLUE_MINT_SEEDS = mintSeed("blue", MintType.BLUE.getCropBlock());
    public static final BLUE_MINT_LEAF = mintLeaf("blue", MintLeafItem(MintType.BLUE));
    public static final CYAN_MINT_SEEDS = mintSeed("cyan", MintType.CYAN.getCropBlock());
    public static final CYAN_MINT_LEAF = mintLeaf("cyan", MintLeafItem(MintType.CYAN));
    public static final PINK_MINT_SEEDS = mintSeed("pink", MintType.PINK.getCropBlock());
    public static final PINK_MINT_LEAF = mintLeaf("pink", MintLeafItem(MintType.PINK));
    public static final GREEN_MINT_SEEDS = mintSeed("green", MintType.GREEN.getCropBlock());
    public static final GREEN_MINT_LEAF = mintLeaf("green", MintLeafItem(MintType.GREEN));
    public static final WHITE_MINT_SEEDS = mintSeed("white", MintType.WHITE.getCropBlock());
    public static final WHITE_MINT_LEAF = mintLeaf("white", MintLeafItem(MintType.WHITE));

    public static final mints = mutableMapOf<String, MintItem>();

    public static final LONELY_MINT = mintItem("lonely_mint", MintItem(Natures.LONELY));
    public static final ADAMANT_MINT = mintItem("adamant_mint", MintItem(Natures.ADAMANT));
    public static final NAUGHTY_MINT = mintItem("naughty_mint", MintItem(Natures.NAUGHTY));
    public static final BRAVE_MINT = mintItem("brave_mint", MintItem(Natures.BRAVE));
    public static final BOLD_MINT = mintItem("bold_mint", MintItem(Natures.BOLD));
    public static final IMPISH_MINT = mintItem("impish_mint", MintItem(Natures.IMPISH));
    public static final LAX_MINT = mintItem("lax_mint", MintItem(Natures.LAX));
    public static final RELAXED_MINT = mintItem("relaxed_mint", MintItem(Natures.RELAXED));
    public static final MODEST_MINT = mintItem("modest_mint", MintItem(Natures.MODEST));
    public static final MILD_MINT = mintItem("mild_mint", MintItem(Natures.MILD));
    public static final RASH_MINT = mintItem("rash_mint", MintItem(Natures.RASH));
    public static final QUIET_MINT = mintItem("quiet_mint", MintItem(Natures.QUIET));
    public static final CALM_MINT = mintItem("calm_mint", MintItem(Natures.CALM));
    public static final GENTLE_MINT = mintItem("gentle_mint", MintItem(Natures.GENTLE));
    public static final CAREFUL_MINT = mintItem("careful_mint", MintItem(Natures.CAREFUL));
    public static final SASSY_MINT = mintItem("sassy_mint", MintItem(Natures.SASSY));
    public static final TIMID_MINT = mintItem("timid_mint", MintItem(Natures.TIMID));
    public static final HASTY_MINT = mintItem("hasty_mint", MintItem(Natures.HASTY));
    public static final JOLLY_MINT = mintItem("jolly_mint", MintItem(Natures.JOLLY));
    public static final NAIVE_MINT = mintItem("naive_mint", MintItem(Natures.NAIVE));
    public static final SERIOUS_MINT = mintItem("serious_mint", MintItem(Natures.SERIOUS));

    public static final X_ACCURACY = create("x_${Stats.ACCURACY.identifier.path}", XStatItem(Stats.ACCURACY));
    public static final X_ATTACK = create("x_${Stats.ATTACK.identifier.path}", XStatItem(Stats.ATTACK));
    public static final X_DEFENSE = create("x_${Stats.DEFENCE.identifier.path}", XStatItem(Stats.DEFENCE));
    public static final X_SP_ATK = create("x_${Stats.SPECIAL_ATTACK.identifier.path}", XStatItem(Stats.SPECIAL_ATTACK));
    public static final X_SP_DEF = create("x_${Stats.SPECIAL_DEFENCE.identifier.path}", XStatItem(Stats.SPECIAL_DEFENCE));
    public static final X_SPEED = create("x_${Stats.SPEED.identifier.path}", XStatItem(Stats.SPEED));

    public static final DIRE_HIT = create("dire_hit", DireHitItem());
    public static final GUARD_SPEC = create("guard_spec", GuardSpecItem());

    public static final BURN_HEAL = create("burn_heal", StatusCureItem("item.cobblemon.burn_heal", Statuses.BURN));
    public static final PARALYZE_HEAL = create("paralyze_heal", StatusCureItem("item.cobblemon.paralyze_heal", Statuses.PARALYSIS));
    public static final ICE_HEAL = create("ice_heal", StatusCureItem("item.cobblemon.ice_heal", Statuses.FROZEN));
    public static final ANTIDOTE = create("antidote", StatusCureItem("item.cobblemon.antidote", Statuses.POISON, Statuses.POISON_BADLY));
    public static final AWAKENING = create("awakening", StatusCureItem("item.cobblemon.awakening", Statuses.SLEEP));

    public static final FULL_HEAL = create("full_heal", StatusCureItem("item.cobblemon.full_heal"));

    public static final ETHER = create("ether", EtherItem(max = false));
    public static final MAX_ETHER = create("max_ether", EtherItem(max = true));
    public static final ELIXIR = create("elixir", ElixirItem(max = false));
    public static final MAX_ELIXIR = create("max_elixir", ElixirItem(max = true));

    public static final ABILITY_CAPSULE = this.create("ability_capsule", AbilityChangeItem(AbilityChanger.COMMON_ABILITY));
    public static final ABILITY_PATCH = this.create("ability_patch", AbilityChangeItem(AbilityChanger.HIDDEN_ABILITY));

    /**
     * Evolution Ores and Stones
     */
    public static final DAWN_STONE_ORE = blockItem("dawn_stone_ore", CobblemonBlocks.DAWN_STONE_ORE);
    public static final DUSK_STONE_ORE = blockItem("dusk_stone_ore", CobblemonBlocks.DUSK_STONE_ORE);
    public static final FIRE_STONE_ORE = blockItem("fire_stone_ore", CobblemonBlocks.FIRE_STONE_ORE);
    public static final ICE_STONE_ORE = blockItem("ice_stone_ore", CobblemonBlocks.ICE_STONE_ORE);
    public static final LEAF_STONE_ORE = blockItem("leaf_stone_ore", CobblemonBlocks.LEAF_STONE_ORE);
    public static final MOON_STONE_ORE = blockItem("moon_stone_ore", CobblemonBlocks.MOON_STONE_ORE);
    public static final SHINY_STONE_ORE = blockItem("shiny_stone_ore", CobblemonBlocks.SHINY_STONE_ORE);
    public static final SUN_STONE_ORE = blockItem("sun_stone_ore", CobblemonBlocks.SUN_STONE_ORE);
    public static final TERRACOTTA_SUN_STONE_ORE = blockItem("terracotta_sun_stone_ore", CobblemonBlocks.TERRACOTTA_SUN_STONE_ORE);
    public static final THUNDER_STONE_ORE = blockItem("thunder_stone_ore", CobblemonBlocks.THUNDER_STONE_ORE);
    public static final WATER_STONE_ORE = blockItem("water_stone_ore", CobblemonBlocks.WATER_STONE_ORE);
    public static final DEEPSLATE_DAWN_STONE_ORE = blockItem("deepslate_dawn_stone_ore", CobblemonBlocks.DEEPSLATE_DAWN_STONE_ORE);
    public static final DEEPSLATE_DUSK_STONE_ORE = blockItem("deepslate_dusk_stone_ore", CobblemonBlocks.DEEPSLATE_DUSK_STONE_ORE);
    public static final DEEPSLATE_FIRE_STONE_ORE = blockItem("deepslate_fire_stone_ore", CobblemonBlocks.DEEPSLATE_FIRE_STONE_ORE);
    public static final DEEPSLATE_ICE_STONE_ORE = blockItem("deepslate_ice_stone_ore", CobblemonBlocks.DEEPSLATE_ICE_STONE_ORE);
    public static final DEEPSLATE_LEAF_STONE_ORE = blockItem("deepslate_leaf_stone_ore", CobblemonBlocks.DEEPSLATE_LEAF_STONE_ORE);
    public static final DEEPSLATE_MOON_STONE_ORE = blockItem("deepslate_moon_stone_ore", CobblemonBlocks.DEEPSLATE_MOON_STONE_ORE);
    public static final DEEPSLATE_SHINY_STONE_ORE = blockItem("deepslate_shiny_stone_ore", CobblemonBlocks.DEEPSLATE_SHINY_STONE_ORE);
    public static final DEEPSLATE_SUN_STONE_ORE = blockItem("deepslate_sun_stone_ore", CobblemonBlocks.DEEPSLATE_SUN_STONE_ORE);
    public static final DEEPSLATE_THUNDER_STONE_ORE = blockItem("deepslate_thunder_stone_ore", CobblemonBlocks.DEEPSLATE_THUNDER_STONE_ORE);
    public static final DEEPSLATE_WATER_STONE_ORE = blockItem("deepslate_water_stone_ore", CobblemonBlocks.DEEPSLATE_WATER_STONE_ORE);
    public static final DRIPSTONE_MOON_STONE_ORE = blockItem("dripstone_moon_stone_ore", CobblemonBlocks.DRIPSTONE_MOON_STONE_ORE);
    public static final NETHER_FIRE_STONE_ORE = blockItem("nether_fire_stone_ore", CobblemonBlocks.NETHER_FIRE_STONE_ORE);

    public static final DAWN_STONE = noSettingsItem("dawn_stone");
    public static final DUSK_STONE = noSettingsItem("dusk_stone");
    public static final FIRE_STONE = noSettingsItem("fire_stone");
    public static final ICE_STONE = noSettingsItem("ice_stone");
    public static final LEAF_STONE = noSettingsItem("leaf_stone");
    public static final MOON_STONE = noSettingsItem("moon_stone");
    public static final SHINY_STONE = noSettingsItem("shiny_stone");
    public static final SUN_STONE = noSettingsItem("sun_stone");
    public static final THUNDER_STONE = noSettingsItem("thunder_stone");
    public static final WATER_STONE = noSettingsItem("water_stone");

    public static final wearables = mutableListOf<WearableItem>()
    //Wearable items (these items should have a corresponding 3D model)
    public static final BLACK_GLASSES = wearableItem("black_glasses");
    public static final CHOICE_BAND = wearableItem("choice_band");
    public static final CHOICE_SPECS = wearableItem("choice_specs");
    public static final EXP_SHARE = wearableItem("exp_share");
    public static final FOCUS_BAND = wearableItem("focus_band");
    public static final KINGS_ROCK = wearableItem("kings_rock");
    public static final MUSCLE_BAND = wearableItem("muscle_band");
    public static final ROCKY_HELMET = wearableItem("rocky_helmet");
    public static final SAFETY_GOGGLES = wearableItem("safety_goggles");
    public static final WISE_GLASSES = wearableItem("wise_glasses");

    // Held Items
    public static final ABILITY_SHIELD = heldItem("ability_shield");
    public static final ABSORB_BULB = heldItem("absorb_bulb");
    public static final AIR_BALLOON = heldItem("air_balloon");
    public static final ASSAULT_VEST = heldItem("assault_vest");
    public static final BIG_ROOT = compostableBlockItem("big_root", CobblemonBlocks.BIG_ROOT, 0.30f)
    public static final BINDING_BAND = heldItem("binding_band");
    public static final BLACK_BELT = heldItem("black_belt");
    public static final BLACK_SLUDGE = heldItem("black_sludge");
    public static final BLUNDER_POLICY = heldItem("blunder_policy");
    public static final CELL_BATTERY = heldItem("cell_battery");
    public static final CHARCOAL = heldItem("charcoal_stick", remappedName = "charcoal");
    public static final CHOICE_SCARF = heldItem("choice_scarf");
    public static final CLEANSE_TAG = heldItem("cleanse_tag");
    public static final CLEAR_AMULET = heldItem("clear_amulet");
    public static final COVERT_CLOAK = heldItem("covert_cloak");
    public static final DESTINY_KNOT = heldItem("destiny_knot");
    public static final DRAGON_FANG = heldItem("dragon_fang");
    public static final EJECT_BUTTON = heldItem("eject_button");
    public static final EJECT_PACK = heldItem("eject_pack");
    public static final ELECTRIC_SEED = heldItem("electric_seed");
    public static final EVERSTONE = heldItem("everstone");
    public static final EVIOLITE = heldItem("eviolite");
    public static final EXPERT_BELT = heldItem("expert_belt");
    public static final FAIRY_FEATHER = heldItem("fairy_feather");
    public static final FLAME_ORB = heldItem("flame_orb");
    public static final FLOAT_STONE = heldItem("float_stone");
    public static final FOCUS_SASH = heldItem("focus_sash");
    public static final GRASSY_SEED = heldItem("grassy_seed");
    public static final GRIP_CLAW = heldItem("grip_claw");
    public static final HARD_STONE = heldItem("hard_stone");
    public static final HEAVY_DUTY_BOOTS = heldItem("heavy_duty_boots");
    public static final IRON_BALL = heldItem("iron_ball");
    public static final LAGGING_TAIL = heldItem("lagging_tail");
    public static final LEFTOVERS = compostableHeldItem("leftovers", null, 0.50f);
    public static final LIFE_ORB = heldItem("life_orb");
    public static final LIGHT_BALL = heldItem("light_ball");
    public static final LIGHT_CLAY = heldItem("light_clay");
    public static final LOADED_DICE = heldItem("loaded_dice");
    public static final LUCKY_EGG = heldItem("lucky_egg");
    public static final LUMINOUS_MOSS = heldItem("luminous_moss");
    public static final MAGNET = heldItem("magnet");
    public static final METRONOME = heldItem("metronome");
    public static final MIRACLE_SEED = heldItem("miracle_seed");
    public static final MYSTIC_WATER = heldItem("mystic_water");
    public static final NEVER_MELT_ICE = heldItem("never_melt_ice");
    public static final POISON_BARB = heldItem("poison_barb");
    public static final POWER_ANKLET = heldItem("power_anklet");
    public static final POWER_BAND = heldItem("power_band");
    public static final POWER_BELT = heldItem("power_belt");
    public static final POWER_BRACER = heldItem("power_bracer");
    public static final POWER_LENS = heldItem("power_lens");
    public static final POWER_WEIGHT = heldItem("power_weight");
    public static final PSYCHIC_SEED = heldItem("psychic_seed");
    public static final PROTECTIVE_PADS = heldItem("protective_pads");
    public static final PUNCHING_GLOVE = heldItem("punching_glove");
    public static final QUICK_CLAW = heldItem("quick_claw");
    public static final RED_CARD = heldItem("red_card");
    public static final RING_TARGET = heldItem("ring_target");
    public static final ROOM_SERVICE = heldItem("room_service");
    public static final SCOPE_LENS = heldItem("scope_lens");
    public static final SHARP_BEAK = heldItem("sharp_beak");
    public static final SHED_SHELL = heldItem("shed_shell");
    public static final SHELL_BELL = heldItem("shell_bell");
    public static final SILK_SCARF = heldItem("silk_scarf");
    public static final SILVER_POWDER = heldItem("silver_powder");
    public static final SOFT_SAND = heldItem("soft_sand");
    public static final SPELL_TAG = heldItem("spell_tag");
    public static final SMOKE_BALL = heldItem("smoke_ball");
    public static final SOOTHE_BELL = heldItem("soothe_bell");
    public static final STICKY_BARB = heldItem("sticky_barb");
    public static final TERRAIN_EXTENDER = heldItem("terrain_extender");
    public static final THROAT_SPRAY = heldItem("throat_spray");
    public static final TOXIC_ORB = heldItem("toxic_orb");
    public static final TWISTED_SPOON = heldItem("twisted_spoon");
    public static final UTILITY_UMBRELLA = heldItem("utility_umbrella");
    public static final WEAKNESS_POLICY = heldItem("weakness_policy");
    public static final WIDE_LENS = heldItem("wide_lens");
    public static final ZOOM_LENS = heldItem("zoom_lens");
    public static final MENTAL_HERB = compostableHeldItem("mental_herb", null, 0.85F);
    public static final MIRROR_HERB = compostableHeldItem("mirror_herb", null, 0.85F);
    public static final MISTY_SEED = heldItem("misty_seed");
    public static final POWER_HERB = compostableHeldItem("power_herb", null, 0.85F);
    public static final WHITE_HERB = compostableHeldItem("white_herb", null, 0.85F);
    public static final BRIGHT_POWDER = heldItem("bright_powder");
    public static final METAL_POWDER = heldItem("metal_powder");
    public static final QUICK_POWDER = heldItem("quick_powder");
    public static final DAMP_ROCK = heldItem("damp_rock");
    public static final HEAT_ROCK = heldItem("heat_rock");
    public static final SMOOTH_ROCK = heldItem("smooth_rock");
    public static final ICY_ROCK = heldItem("icy_rock");

    // Mulch
    public static final MULCH_BASE = noSettingsItem("mulch_base");
    public static final COARSE_MULCH = mulchItem("coarse_mulch", MulchVariant.COARSE);
    public static final GROWTH_MULCH = mulchItem("growth_mulch", MulchVariant.GROWTH);
    public static final HUMID_MULCH = mulchItem("humid_mulch", MulchVariant.HUMID);
    public static final LOAMY_MULCH = mulchItem("loamy_mulch", MulchVariant.LOAMY);
    public static final PEAT_MULCH = mulchItem("peat_mulch", MulchVariant.PEAT);
    public static final RICH_MULCH = mulchItem("rich_mulch", MulchVariant.RICH);
    public static final SANDY_MULCH = mulchItem("sandy_mulch", MulchVariant.SANDY);
    public static final SURPRISE_MULCH = mulchItem("surprise_mulch", MulchVariant.SURPRISE);

    // Archaeology
    public static final ARMOR_FOSSIL = itemWithRarity("armor_fossil", Rarity.UNCOMMON);
    public static final FOSSILIZED_BIRD = itemWithRarity("fossilized_bird", Rarity.UNCOMMON);
    public static final CLAW_FOSSIL = itemWithRarity("claw_fossil", Rarity.UNCOMMON);
    public static final COVER_FOSSIL = itemWithRarity("cover_fossil", Rarity.UNCOMMON);
    public static final FOSSILIZED_DINO = itemWithRarity("fossilized_dino", Rarity.UNCOMMON);
    public static final DOME_FOSSIL = itemWithRarity("dome_fossil", Rarity.UNCOMMON);
    public static final FOSSILIZED_DRAKE = itemWithRarity("fossilized_drake", Rarity.UNCOMMON);
    public static final FOSSILIZED_FISH = itemWithRarity("fossilized_fish", Rarity.UNCOMMON);
    public static final HELIX_FOSSIL = itemWithRarity("helix_fossil", Rarity.UNCOMMON);
    public static final JAW_FOSSIL = itemWithRarity("jaw_fossil", Rarity.UNCOMMON);
    public static final OLD_AMBER_FOSSIL = itemWithRarity("old_amber_fossil", Rarity.UNCOMMON);
    public static final PLUME_FOSSIL = itemWithRarity("plume_fossil", Rarity.UNCOMMON);
    public static final ROOT_FOSSIL = itemWithRarity("root_fossil", Rarity.UNCOMMON);
    public static final SAIL_FOSSIL = itemWithRarity("sail_fossil", Rarity.UNCOMMON);
    public static final SKULL_FOSSIL = itemWithRarity("skull_fossil", Rarity.UNCOMMON);

    public static final BYGONE_SHERD = itemWithRarity("bygone_sherd", Rarity.UNCOMMON);
    public static final CAPTURE_SHERD = itemWithRarity("capture_sherd",Rarity.UNCOMMON);
    public static final DOME_SHERD = itemWithRarity("dome_sherd", Rarity.UNCOMMON);
    public static final HELIX_SHERD = itemWithRarity("helix_sherd", Rarity.UNCOMMON);
    public static final NOSTALGIC_SHERD = itemWithRarity("nostalgic_sherd",Rarity.UNCOMMON);
    public static final SUSPICIOUS_SHERD = itemWithRarity("suspicious_sherd", Rarity.UNCOMMON);

    public static final TUMBLESTONE = this.create("tumblestone", TumblestoneItem(Item.Properties(), CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE));
    public static final BLACK_TUMBLESTONE = this.create("black_tumblestone", TumblestoneItem(Item.Properties(), CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE));
    public static final SKY_TUMBLESTONE = this.create("sky_tumblestone", TumblestoneItem(Item.Properties(), CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE));

    public static final SMALL_BUDDING_TUMBLESTONE = blockItem("small_budding_tumblestone", CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE);
    public static final MEDIUM_BUDDING_TUMBLESTONE = blockItem("medium_budding_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE);
    public static final LARGE_BUDDING_TUMBLESTONE = blockItem("large_budding_tumblestone", CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE);
    public static final TUMBLESTONE_CLUSTER = blockItem("tumblestone_cluster", CobblemonBlocks.TUMBLESTONE_CLUSTER);

    public static final SMALL_BUDDING_SKY_TUMBLESTONE = blockItem("small_budding_sky_tumblestone", CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE);
    public static final MEDIUM_BUDDING_SKY_TUMBLESTONE = blockItem("medium_budding_sky_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE);
    public static final LARGE_BUDDING_SKY_TUMBLESTONE = blockItem("large_budding_sky_tumblestone", CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE);
    public static final SKY_TUMBLESTONE_CLUSTER = blockItem("sky_tumblestone_cluster", CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER);

    public static final SMALL_BUDDING_BLACK_TUMBLESTONE = blockItem("small_budding_black_tumblestone", CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE);
    public static final MEDIUM_BUDDING_BLACK_TUMBLESTONE = blockItem("medium_budding_black_tumblestone", CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE);
    public static final LARGE_BUDDING_BLACK_TUMBLESTONE = blockItem("large_budding_black_tumblestone", CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE);
    public static final BLACK_TUMBLESTONE_CLUSTER = blockItem("black_tumblestone_cluster", CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER);

    public static final TUMBLESTONE_BLOCK = blockItem("tumblestone_block", CobblemonBlocks.TUMBLESTONE_BLOCK);
    public static final SKY_TUMBLESTONE_BLOCK = blockItem("sky_tumblestone_block", CobblemonBlocks.SKY_TUMBLESTONE_BLOCK);
    public static final BLACK_TUMBLESTONE_BLOCK = blockItem("black_tumblestone_block", CobblemonBlocks.BLACK_TUMBLESTONE_BLOCK);
    public static final POKEROD_SMITHING_TEMPLATE = create("pokerod_smithing_template", SmithingTemplateItem(Component.translatable("item.minecraft.fishing_rod");.blue(), Component.translatable("item.cobblemon.smithing_template.pokerod.ingredients");.blue(), Component.translatable("upgrade.cobblemon.pokerod");.gray(), Component.translatable("item.cobblemon.smithing_template.pokerod.base_slot_description"), Component.translatable("item.cobblemon.smithing_template.pokerod.additions_slot_description"), listOf(cobblemonResource("item/empty_slot_fishing_rod")), listOf(cobblemonResource("item/empty_slot_pokeball"))));

    public static final POLISHED_TUMBLESTONE = blockItem("polished_tumblestone", CobblemonBlocks.POLISHED_TUMBLESTONE);
    public static final POLISHED_TUMBLESTONE_STAIRS = blockItem("polished_tumblestone_stairs", CobblemonBlocks.POLISHED_TUMBLESTONE_STAIRS);
    public static final POLISHED_TUMBLESTONE_SLAB = blockItem("polished_tumblestone_slab", CobblemonBlocks.POLISHED_TUMBLESTONE_SLAB);
    public static final POLISHED_TUMBLESTONE_WALL = blockItem("polished_tumblestone_wall", CobblemonBlocks.POLISHED_TUMBLESTONE_WALL);
    public static final CHISELED_POLISHED_TUMBLESTONE = blockItem("chiseled_polished_tumblestone", CobblemonBlocks.CHISELED_POLISHED_TUMBLESTONE);
    public static final SMOOTH_TUMBLESTONE = blockItem("smooth_tumblestone", CobblemonBlocks.SMOOTH_TUMBLESTONE);
    public static final SMOOTH_TUMBLESTONE_STAIRS = blockItem("smooth_tumblestone_stairs", CobblemonBlocks.SMOOTH_TUMBLESTONE_STAIRS);
    public static final SMOOTH_TUMBLESTONE_SLAB = blockItem("smooth_tumblestone_slab", CobblemonBlocks.SMOOTH_TUMBLESTONE_SLAB);
    public static final TUMBLESTONE_BRICKS = blockItem("tumblestone_bricks", CobblemonBlocks.TUMBLESTONE_BRICKS);
    public static final TUMBLESTONE_BRICK_STAIRS = blockItem("tumblestone_brick_stairs", CobblemonBlocks.TUMBLESTONE_BRICK_STAIRS);
    public static final TUMBLESTONE_BRICK_SLAB = blockItem("tumblestone_brick_slab", CobblemonBlocks.TUMBLESTONE_BRICK_SLAB);
    public static final TUMBLESTONE_BRICK_WALL = blockItem("tumblestone_brick_wall", CobblemonBlocks.TUMBLESTONE_BRICK_WALL);
    public static final CHISELED_TUMBLESTONE_BRICKS = blockItem("chiseled_tumblestone_bricks", CobblemonBlocks.CHISELED_TUMBLESTONE_BRICKS);
    public static final POLISHED_SKY_TUMBLESTONE = blockItem("polished_sky_tumblestone", CobblemonBlocks.POLISHED_SKY_TUMBLESTONE);
    public static final POLISHED_SKY_TUMBLESTONE_STAIRS = blockItem("polished_sky_tumblestone_stairs", CobblemonBlocks.POLISHED_SKY_TUMBLESTONE_STAIRS);
    public static final POLISHED_SKY_TUMBLESTONE_SLAB = blockItem("polished_sky_tumblestone_slab", CobblemonBlocks.POLISHED_SKY_TUMBLESTONE_SLAB);
    public static final POLISHED_SKY_TUMBLESTONE_WALL = blockItem("polished_sky_tumblestone_wall", CobblemonBlocks.POLISHED_SKY_TUMBLESTONE_WALL);
    public static final CHISELED_POLISHED_SKY_TUMBLESTONE = blockItem("chiseled_polished_sky_tumblestone", CobblemonBlocks.CHISELED_POLISHED_SKY_TUMBLESTONE);
    public static final SMOOTH_SKY_TUMBLESTONE = blockItem("smooth_sky_tumblestone", CobblemonBlocks.SMOOTH_SKY_TUMBLESTONE);
    public static final SMOOTH_SKY_TUMBLESTONE_STAIRS = blockItem("smooth_sky_tumblestone_stairs", CobblemonBlocks.SMOOTH_SKY_TUMBLESTONE_STAIRS);
    public static final SMOOTH_SKY_TUMBLESTONE_SLAB = blockItem("smooth_sky_tumblestone_slab", CobblemonBlocks.SMOOTH_SKY_TUMBLESTONE_SLAB);
    public static final SKY_TUMBLESTONE_BRICKS = blockItem("sky_tumblestone_bricks", CobblemonBlocks.SKY_TUMBLESTONE_BRICKS);
    public static final SKY_TUMBLESTONE_BRICK_STAIRS = blockItem("sky_tumblestone_brick_stairs", CobblemonBlocks.SKY_TUMBLESTONE_BRICK_STAIRS);
    public static final SKY_TUMBLESTONE_BRICK_SLAB = blockItem("sky_tumblestone_brick_slab", CobblemonBlocks.SKY_TUMBLESTONE_BRICK_SLAB);
    public static final SKY_TUMBLESTONE_BRICK_WALL = blockItem("sky_tumblestone_brick_wall", CobblemonBlocks.SKY_TUMBLESTONE_BRICK_WALL);
    public static final CHISELED_SKY_TUMBLESTONE_BRICKS = blockItem("chiseled_sky_tumblestone_bricks", CobblemonBlocks.CHISELED_SKY_TUMBLESTONE_BRICKS);
    public static final POLISHED_BLACK_TUMBLESTONE = blockItem("polished_black_tumblestone", CobblemonBlocks.POLISHED_BLACK_TUMBLESTONE);
    public static final POLISHED_BLACK_TUMBLESTONE_STAIRS = blockItem("polished_black_tumblestone_stairs", CobblemonBlocks.POLISHED_BLACK_TUMBLESTONE_STAIRS);
    public static final POLISHED_BLACK_TUMBLESTONE_SLAB = blockItem("polished_black_tumblestone_slab", CobblemonBlocks.POLISHED_BLACK_TUMBLESTONE_SLAB);
    public static final POLISHED_BLACK_TUMBLESTONE_WALL = blockItem("polished_black_tumblestone_wall", CobblemonBlocks.POLISHED_BLACK_TUMBLESTONE_WALL);
    public static final CHISELED_POLISHED_BLACK_TUMBLESTONE = blockItem("chiseled_polished_black_tumblestone", CobblemonBlocks.CHISELED_POLISHED_BLACK_TUMBLESTONE);
    public static final SMOOTH_BLACK_TUMBLESTONE = blockItem("smooth_black_tumblestone", CobblemonBlocks.SMOOTH_BLACK_TUMBLESTONE);
    public static final SMOOTH_BLACK_TUMBLESTONE_STAIRS = blockItem("smooth_black_tumblestone_stairs", CobblemonBlocks.SMOOTH_BLACK_TUMBLESTONE_STAIRS);
    public static final SMOOTH_BLACK_TUMBLESTONE_SLAB = blockItem("smooth_black_tumblestone_slab", CobblemonBlocks.SMOOTH_BLACK_TUMBLESTONE_SLAB);
    public static final BLACK_TUMBLESTONE_BRICKS = blockItem("black_tumblestone_bricks", CobblemonBlocks.BLACK_TUMBLESTONE_BRICKS);
    public static final BLACK_TUMBLESTONE_BRICK_STAIRS = blockItem("black_tumblestone_brick_stairs", CobblemonBlocks.BLACK_TUMBLESTONE_BRICK_STAIRS);
    public static final BLACK_TUMBLESTONE_BRICK_SLAB = blockItem("black_tumblestone_brick_slab", CobblemonBlocks.BLACK_TUMBLESTONE_BRICK_SLAB);
    public static final BLACK_TUMBLESTONE_BRICK_WALL = blockItem("black_tumblestone_brick_wall", CobblemonBlocks.BLACK_TUMBLESTONE_BRICK_WALL);
    public static final CHISELED_BLACK_TUMBLESTONE_BRICKS = blockItem("chiseled_black_tumblestone_bricks", CobblemonBlocks.CHISELED_BLACK_TUMBLESTONE_BRICKS);

    public static final FIRE_STONE_BLOCK = blockItem("fire_stone_block", CobblemonBlocks.FIRE_STONE_BLOCK)
    public static final WATER_STONE_BLOCK = blockItem("water_stone_block", CobblemonBlocks.WATER_STONE_BLOCK)
    public static final THUNDER_STONE_BLOCK = blockItem("thunder_stone_block", CobblemonBlocks.THUNDER_STONE_BLOCK)
    public static final LEAF_STONE_BLOCK = blockItem("leaf_stone_block", CobblemonBlocks.LEAF_STONE_BLOCK)
    public static final ICE_STONE_BLOCK = blockItem("ice_stone_block", CobblemonBlocks.ICE_STONE_BLOCK)
    public static final SUN_STONE_BLOCK = blockItem("sun_stone_block", CobblemonBlocks.SUN_STONE_BLOCK)
    public static final MOON_STONE_BLOCK = blockItem("moon_stone_block", CobblemonBlocks.MOON_STONE_BLOCK)
    public static final SHINY_STONE_BLOCK = blockItem("shiny_stone_block", CobblemonBlocks.SHINY_STONE_BLOCK)
    public static final DAWN_STONE_BLOCK = blockItem("dawn_stone_block", CobblemonBlocks.DAWN_STONE_BLOCK)
    public static final DUSK_STONE_BLOCK = blockItem("dusk_stone_block", CobblemonBlocks.DUSK_STONE_BLOCK)

    public static final AUTOMATON_ARMOR_TRIM_SMITHING_TEMPLATE: SmithingTemplateItem = this.create("automaton_armor_trim_smithing_template", SmithingTemplateItem.createArmorTrimTemplate(CobblemonArmorTrims.AUTOMATON));

    public static final pokeRods = mutableListOf<PokerodItem>();
    public static final POKE_ROD = pokerodItem(cobblemonResource("poke_rod"));
    public static final CITRINE_ROD = pokerodItem(cobblemonResource("citrine_rod"));
    public static final VERDANT_ROD = pokerodItem(cobblemonResource("verdant_rod"));
    public static final AZURE_ROD = pokerodItem(cobblemonResource("azure_rod"));
    public static final ROSEATE_ROD = pokerodItem(cobblemonResource("roseate_rod"));
    public static final SLATE_ROD = pokerodItem(cobblemonResource("slate_rod"));
    public static final PREMIER_ROD = pokerodItem(cobblemonResource("premier_rod"));
    public static final GREAT_ROD = pokerodItem(cobblemonResource("great_rod"));
    public static final ULTRA_ROD = pokerodItem(cobblemonResource("ultra_rod"));
    public static final SAFARI_ROD = pokerodItem(cobblemonResource("safari_rod"));
    public static final FAST_ROD = pokerodItem(cobblemonResource("fast_rod"));
    public static final LEVEL_ROD = pokerodItem(cobblemonResource("level_rod"));
    public static final LURE_ROD = pokerodItem(cobblemonResource("lure_rod"));
    public static final HEAVY_ROD = pokerodItem(cobblemonResource("heavy_rod"));
    public static final LOVE_ROD = pokerodItem(cobblemonResource("love_rod"));
    public static final FRIEND_ROD = pokerodItem(cobblemonResource("friend_rod"));
    public static final MOON_ROD = pokerodItem(cobblemonResource("moon_rod"));
    public static final SPORT_ROD = pokerodItem(cobblemonResource("sport_rod"));
    public static final PARK_ROD = pokerodItem(cobblemonResource("park_rod"));
    public static final NET_ROD = pokerodItem(cobblemonResource("net_rod"));
    public static final DIVE_ROD = pokerodItem(cobblemonResource("dive_rod"));
    public static final NEST_ROD = pokerodItem(cobblemonResource("nest_rod"));
    public static final REPEAT_ROD = pokerodItem(cobblemonResource("repeat_rod"));
    public static final TIMER_ROD = pokerodItem(cobblemonResource("timer_rod"));
    public static final LUXURY_ROD = pokerodItem(cobblemonResource("luxury_rod"));
    public static final DUSK_ROD = pokerodItem(cobblemonResource("dusk_rod"));
    public static final HEAL_ROD = pokerodItem(cobblemonResource("heal_rod"));
    public static final QUICK_ROD = pokerodItem(cobblemonResource("quick_rod"));
    public static final DREAM_ROD = pokerodItem(cobblemonResource("dream_rod"));
    public static final BEAST_ROD = pokerodItem(cobblemonResource("beast_rod"), Rarity.RARE);
    public static final MASTER_ROD = pokerodItem(cobblemonResource("master_rod"), Rarity.EPIC);
    public static final CHERISH_ROD = pokerodItem(cobblemonResource("cherish_rod"), Rarity.EPIC);
    public static final ANCIENT_POKE_ROD = pokerodItem(cobblemonResource("ancient_poke_rod"));
    public static final ANCIENT_CITRINE_ROD = pokerodItem(cobblemonResource("ancient_citrine_rod"));
    public static final ANCIENT_VERDANT_ROD = pokerodItem(cobblemonResource("ancient_verdant_rod"));
    public static final ANCIENT_AZURE_ROD = pokerodItem(cobblemonResource("ancient_azure_rod"));
    public static final ANCIENT_ROSEATE_ROD = pokerodItem(cobblemonResource("ancient_roseate_rod"));
    public static final ANCIENT_SLATE_ROD = pokerodItem(cobblemonResource("ancient_slate_rod"));
    public static final ANCIENT_IVORY_ROD = pokerodItem(cobblemonResource("ancient_ivory_rod"));
    public static final ANCIENT_GREAT_ROD = pokerodItem(cobblemonResource("ancient_great_rod"));
    public static final ANCIENT_ULTRA_ROD = pokerodItem(cobblemonResource("ancient_ultra_rod"));
    public static final ANCIENT_FEATHER_ROD = pokerodItem(cobblemonResource("ancient_feather_rod"));
    public static final ANCIENT_WING_ROD = pokerodItem(cobblemonResource("ancient_wing_rod"));
    public static final ANCIENT_JET_ROD = pokerodItem(cobblemonResource("ancient_jet_rod"));
    public static final ANCIENT_HEAVY_ROD = pokerodItem(cobblemonResource("ancient_heavy_rod"));
    public static final ANCIENT_LEADEN_ROD = pokerodItem(cobblemonResource("ancient_leaden_rod"));
    public static final ANCIENT_GIGATON_ROD = pokerodItem(cobblemonResource("ancient_gigaton_rod"));
    public static final ANCIENT_ORIGIN_ROD = pokerodItem(cobblemonResource("ancient_origin_rod"), Rarity.EPIC);

    // Misc
    public static final POKEMON_MODEL = this.create("pokemon_model", PokemonItem());
    public static final RELIC_COIN = noSettingsItem("relic_coin");
    public static final RELIC_COIN_POUCH = blockItem("relic_coin_pouch", CobblemonBlocks.RELIC_COIN_POUCH);
    public static final RELIC_COIN_SACK = blockItem("relic_coin_sack", CobblemonBlocks.RELIC_COIN_SACK);

    // Type Gems
    public static final NORMAL_GEM = noSettingsItem("normal_gem");
    public static final FIRE_GEM = noSettingsItem("fire_gem");
    public static final WATER_GEM = noSettingsItem("water_gem");
    public static final GRASS_GEM = noSettingsItem("grass_gem");
    public static final ELECTRIC_GEM = noSettingsItem("electric_gem");
    public static final ICE_GEM = noSettingsItem("ice_gem");
    public static final FIGHTING_GEM = noSettingsItem("fighting_gem");
    public static final POISON_GEM = noSettingsItem("poison_gem");
    public static final GROUND_GEM = noSettingsItem("ground_gem");
    public static final FLYING_GEM = noSettingsItem("flying_gem");
    public static final PSYCHIC_GEM = noSettingsItem("psychic_gem");
    public static final BUG_GEM = noSettingsItem("bug_gem");
    public static final ROCK_GEM = noSettingsItem("rock_gem");
    public static final GHOST_GEM = noSettingsItem("ghost_gem");
    public static final DRAGON_GEM = noSettingsItem("dragon_gem");
    public static final DARK_GEM = noSettingsItem("dark_gem");
    public static final STEEL_GEM = noSettingsItem("steel_gem");
    public static final FAIRY_GEM = noSettingsItem("fairy_gem");
    public static final BINDING_SOIL = blockItem("binding_soil", CobblemonBlocks.BINDING_SOIL);

    private fun blockItem(String name, Block block, rarity: Rarity = Rarity.COMMON): BlockItem = this.create(name, BlockItem(block, Item.Properties().rarity(rarity)))

    private fun itemNameBlockItem(String name, Block block, rarity: Rarity = Rarity.COMMON): ItemNameBlockItem = this.create(name, ItemNameBlockItem(block, Item.Properties().rarity(rarity)))

    private fun noSettingsItem(String name): CobblemonItem = this.create(name, CobblemonItem(Item.Properties()))

    private fun itemWithRarity(String name, rarity: Rarity): CobblemonItem = this.create(name, CobblemonItem(Item.Properties().rarity(rarity)))
    
    fun berries() = this.berries.toMap()

    private fun mulchItem(String name, mulchVariant: MulchVariant): MulchItem = this.create(name, MulchItem(mulchVariant))

    private fun pokeBallItem(pokeBall: PokeBall): PokeBallItem {
       public static final item = create(pokeBall.name.path, PokeBallItem(pokeBall))
        pokeBall.item = item
        pokeBalls.add(item)
        return item
    }

    private fun candyItem(String name, rarity: Rarity = Rarity.COMMON, calculator: CandyItem.Calculator): CandyItem  = this.create(name, CandyItem(rarity, calculator))

    private fun pokerodItem(pokeRodResourceLocation id, rarity: Rarity = Rarity.COMMON): PokerodItem {
       public static final settings = Item.Properties().stacksTo(1).durability(256).rarity(rarity)
       public static final item = create(pokeRodId.path, PokerodItem(pokeRodId, settings))
        pokeRods.add(item)
        return item
    }

    private fun pokedexItem(type: PokedexType): PokedexItem {
       public static final item = create("pokedex_${type.name.lowercase()}", PokedexItem(type))
        pokedexes.add(item)
        return item
    }

    private fun wearableItem(String name, heldItemRemappedName: String? = null): CobblemonItem = create(
        name,
        WearableItem(name).also {
            wearables.add(it)
            if (heldItemRemappedName != null) {
                CobblemonHeldItemManager.registerRemap(it, heldItemRemappedName)
            }
        }
    )

    private fun campfirePotItem(Block block, color: CampfirePotColor): CampfirePotItem {
       public static final item = create("campfire_pot_${color.suffix}", CampfirePotItem(block, color))
        campfire_pots.add(item)
        return item
    }

    private fun aprijuiceItem(type: Apricorn): AprijuiceItem {
       public static final item = create("aprijuice_${type.name.lowercase()}", AprijuiceItem(type))
        aprijuices.add(item)
        return item
    }

    private fun pokepuffItem(String name): PokePuffItem {
       public static final item = create("poke_puff", PokePuffItem())
        return item
    }

    private fun regionalFoodItem(
        String name,
        stacksTo: Int,
        nutrition: Int,
        saturationModifier: Float,
        alwaysEdible: Boolean = false,
        convertsToOnUse: ItemStack? = null
    ): RegionalFoodItem {
       public static final foodPropertiesBuilder = FoodProperties.Builder()
            .nutrition(nutrition)
            .saturationModifier(saturationModifier)

        if (alwaysEdible == true) {
            foodPropertiesBuilder.alwaysEdible()
        }

        if (convertsToOnUse != null && !convertsToOnUse.isEmpty) {
            foodPropertiesBuilder.usingConvertsTo(convertsToOnUse.item)
        }

       public static final properties = Item.Properties()
            .stacksTo(stacksTo)
            .food(foodPropertiesBuilder.build())

        return create(name, RegionalFoodItem(properties))
    }

    private fun heldItem(String name, remappedName: String? = null): CobblemonItem = create(
        name,
        CobblemonItem(Item.Properties()).also {
            if (remappedName != null) {
                CobblemonHeldItemManager.registerRemap(it, remappedName)
            }
        }
    )
    private fun heldItem(String name, item: Item, remappedName: String? = null) = create(
        name = name,
        entry = item.also {
            remappedName?.let { remappedName ->
                CobblemonHeldItemManager.registerRemap(it, remappedName)
            }
        }
    )

    private fun compostable(item: Item, increaseLevelChance: Float) = Cobblemon.implementation.registerCompostable(item, increaseLevelChance)

    private fun berryItem(String name, berryBlock: BerryBlock): BerryItem {
       public static final finalName = "${name}_berry"
       public static final item = this.create(finalName, BerryItem(berryBlock))
        compostable(item, .65f)
        this.berries[cobblemonResource(finalName)] = item
        return item
    }

    private fun berryItem(String name, berryItem: BerryItem): BerryItem {
       public static final finalName = "${name}_berry"
       public static final item = this.create(finalName, berryItem)
        compostable(item, .65f)
        this.berries[cobblemonResource(finalName)] = item
        return item
    }

    private fun mintItem(String name, mintItem: MintItem): MintItem {
       public static final item = this.create(name, mintItem)
        mints[item.nature.displayName] = item
        compostable(item, .95f)
        return item
    }

    private fun hyperTrainingItem(String name, increaseAmount: Int, targetStats: Set<Stat>,public static finalidIntRange range): HyperTrainingItem {
       public static final item = this.create(name, HyperTrainingItem(increaseAmount, targetStats,public static finalidRange))
        hyperTrainingItems.add(item)
        return item
    }

    private fun apricornItem(String name, apricornItem: ApricornItem): ApricornItem {
       public static final finalName = "${name}_apricorn"
       public static final item = this.create(finalName, apricornItem)
        compostable(item, .65f)
        return item
    }

    private fun apricornSeedItem(String name, apricornSeedItem: ApricornSeedItem): ApricornSeedItem {
       public static final finalName = "${name}_apricorn_seed"
       public static final item = this.create(finalName, apricornSeedItem)
        compostable(item, .30f)
        return item
    }


    private fun mintSeed(String name, mintBlock: MintBlock): Item {
       public static final finalName = "${name}_mint_seeds"
       public static final item = this.blockItem(finalName, mintBlock)
        compostable(item, .30f)
        return item
    }

    private fun mintLeaf(String name, mintLeafItem: MintLeafItem): MintLeafItem {
       public static final finalName = "${name}_mint_leaf"
       public static final item = this.create(finalName, mintLeafItem)
        compostable(item, .50f)
        return item
    }

    private fun compostableItem(String name, item: Item? = null, increaseLevelChance: Float = .65f): Item {
       public static final createdItem = this.create(name, item ?: CobblemonItem(Item.Properties()))
        compostable(createdItem, increaseLevelChance)
        return createdItem
    }

    private fun compostableHeldItem(String name, remappedName: String? = null, increaseLevelChance: Float = .65f): CobblemonItem {
       public static final createdItem = heldItem(name, remappedName)
        compostable(createdItem, increaseLevelChance)
        return createdItem
    }

    private fun compostableBlockItem(String name, Block block, increaseLevelChance: Float = .85f): Item {
       public static final createdItem = this.blockItem(name, block)
        compostable(createdItem, increaseLevelChance)
        return createdItem
    }

    private fun compostableItemNameBlockItem(String name, Block block, increaseLevelChance: Float = .30f): Item {
       public static final createdItem = this.itemNameBlockItem(name, block)
        compostable(createdItem, increaseLevelChance)
        return createdItem
    }

    private fun foodItem(nutrition: Int, saturationModifier: Float): Item {
        return CobblemonItem(Item.Properties().food(FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build()))
    }
}
