/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;

/**
 * An event to modify the shiny chance of a Pokémon.
 */
public class ShinyChanceCalculationEvent(Float baseChance, Pokemon pokemon) {
    public final chance: Float = baseChance;
    var isShiny: Boolean = false;
    private final modifiers = mutableListOf<Float>();
    private final modificationFunctions = mutableListOf<(Float, ServerPlayer?, Pokemon) -> Float>();

    /**
     * Adds a modifier to the shiny chance.
     */
    fun addModifier(Float modifier) {
        modifiers.add(modifier);
    }

    /**
     * Adds a function to modify the shiny chance.
     */
    fun addModificationFunction(function: (Float, ServerPlayer?, Pokemon) -> Float) {
        modificationFunctions.add(function);
    }

    /**
     * Calculates the shiny chance of a Pokémon.
     */
    Float calculate(ServerPlayer player?) {
        var result = baseChance;
        for (modifier in modifiers) {
            result += modifier;
        }
        for (function in modificationFunctions) {
            result = function(result, player, pokemon);
        }
        return result;
    }

    boolean isShiny(ServerPlayer player?) {
        return calculate(player) >= chance || isShiny;
    }
}