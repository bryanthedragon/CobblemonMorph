/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.fishing

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.resources.ResourceLocation

/**
 * Event to register code-based functions for bait effects.
 * @see SpawnBait.Effect
 * @see SpawnBait.Effects
 */
class BaitEffectFunctionRegistryEvent {
    val functions = mutableMapOf<ResourceLocation, (PokemonEntity, SpawnBait.Effect) -> Unit>()

    fun registerFunction(id: ResourceLocation, function: (PokemonEntity, SpawnBait.Effect) -> Unit) {
        functions[id] = function
    }
}