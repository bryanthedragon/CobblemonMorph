/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons.handler;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.string.StringSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons.CobblemonSeason;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import java.util.*;

/**
 * A season. You know the ones.
 *
 * @author Hiroku
 * @since November 25th, 2022
 */
public final class SeasonFeatureHandler {
    fun updateSeason(Pokemon pokemon, Level worldAccessor, BlockPos pos) {
        updateSeason(pokemon, Cobblemon.seasonResolver(world, pos))
    }

    fun updateSeason(Pokemon pokemon, CobblemonSeason season) {
        val feature = pokemon.getFeature<StringSpeciesFeature>(SEASON) ?: return;
        val currentSeason = feature.value;
        val newSeason = season.name.lowercase();
        if (currentSeason != newSeason && newSeason != null) {
            feature.value = newSeason;
            pokemon.updateAspects();
            pokemon.markFeatureDirty(feature);
        }
    }
}