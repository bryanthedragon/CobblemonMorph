/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;

import kotlin.jvm.JvmField;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

/**
 * Contains all the advancement criteria in Cobblemon.
 *
 * @author Licious
 * @since October 26th, 2022
 */
public final class CobblemonCriteria extends PlatformRegistry<Registry<CriterionTrigger<?>>, ResourceKey<Registry<CriterionTrigger<?>>>, CriterionTrigger<?>>
{
    public final  CriterionTrigger<?> PICK_STARTER = create("pick_starter", SimpleCriterionTrigger(PokemonCriterion.CODEC));

    public final  CriterionTrigger<?> CATCH_POKEMON = create("catch_pokemon", SimpleCriterionTrigger(CaughtPokemonCriterion.CODEC));

    public final  CriterionTrigger<?> CATCH_SHINY_POKEMON = create("catch_shiny_pokemon", SimpleCriterionTrigger(CountableCriterion.CODEC));

    public final  CriterionTrigger<?> EGG_COLLECT = create("eggs_collected", SimpleCriterionTrigger(CountableCriterion.CODEC));

    public final  CriterionTrigger<?> EGG_HATCH = create("eggs_hatched", SimpleCriterionTrigger(CountableCriterion.CODEC));

    public final  CriterionTrigger<?> EVOLVE_POKEMON = create("pokemon_evolved", SimpleCriterionTrigger(EvolvePokemonCriterion.CODEC));

    public final  CriterionTrigger<?> WIN_BATTLE = create("battles_won", SimpleCriterionTrigger(BattleCountableCriterion.CODEC));

    public final  CriterionTrigger<?> DEFEAT_POKEMON = create("pokemon_defeated", SimpleCriterionTrigger(CountableCriterion.CODEC));

    public final  CriterionTrigger<?> COLLECT_ASPECT = create("aspects_collected", SimpleCriterionTrigger(AspectCriterion.CODEC));

    public final  CriterionTrigger<?> POKEMON_INTERACT = create("pokemon_interact", SimpleCriterionTrigger(PokemonInteractCriterion.CODEC));

    public final  CriterionTrigger<?> PARTY_CHECK = create("party", SimpleCriterionTrigger(PartyCheckCriterion.CODEC));

    public final  CriterionTrigger<?> LEVEL_UP = create("level_up", SimpleCriterionTrigger(LevelUpCriterion.CODEC));

    public final  CriterionTrigger<?> PASTURE_USE = create("pasture_use", SimpleCriterionTrigger(PokemonCriterion.CODEC));

    public final  CriterionTrigger<?> RESURRECT_POKEMON = create("resurrect_pokemon", SimpleCriterionTrigger(PokemonCriterion.CODEC));

    public final  CriterionTrigger<?> TRADE_POKEMON = create("trade_pokemon", SimpleCriterionTrigger(TradePokemonCriterion.CODEC));

    public final  CriterionTrigger<?> CAST_POKE_ROD = create("cast_poke_rod", SimpleCriterionTrigger(CastPokeRodCriterionCondition.CODEC));

    public final  CriterionTrigger<?> REEL_IN_POKEMON = create("reel_in_pokemon", SimpleCriterionTrigger(ReelInPokemonCriterionCondition.CODEC));

    // Advancement criteria for [grow_tumblestone.json]
    public final  CriterionTrigger<?> PLANT_TUMBLESTONE = create("plant_tumblestone", SimpleCriterionTrigger(PlantTumblestoneCriterion.CODEC));

    public final  CriterionTrigger<?> RIDING_STAT_BOOST = create("riding_stat_boost", SimpleCriterionTrigger(RidingStatBoostCriterion.CODEC));


    @Override
    public Registry<CriterionTrigger<?>> getRegistry() {
        return BuiltInRegistries.TRIGGER_TYPES;
    }

    @Override
    public ResourceKey<Registry<CriterionTrigger<?>>> getResourceKey() {
        return Registries.TRIGGER_TYPE;
    }

    private CobblemonCriteria() {}
}