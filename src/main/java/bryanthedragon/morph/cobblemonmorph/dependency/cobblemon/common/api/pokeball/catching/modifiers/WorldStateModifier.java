/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

/**
 * A [CatchRateModifier] that resolves the catch rate based on the Entity attached to the target Pokémon.
 *
 * @property calculator Responsible for resolving the catch rate dynamically based on the given params.
 *
 * @author Licious
 * @since May 7th, 2022
 */
open class WorldStateModifier(private val calculator: (LivingEntity thrower, entity: PokemonEntity) -> Float) : CatchRateModifier {

    override fun value(LivingEntity thrower, Pokemon pokemon): Float {
        val entity = pokemon.entity ?: return 1F
        return this.calculator(thrower, entity)
    }

    override fun behavior(LivingEntity thrower, Pokemon pokemon): CatchRateModifier.Behavior = CatchRateModifier.Behavior.MULTIPLY

    override fun isValid(LivingEntity thrower, Pokemon pokemon): Boolean = true

    override fun modifyCatchRate(currentFloat catchRate, LivingEntity thrower, Pokemon pokemon): Float = this.behavior(thrower, pokemon).mutator(currentCatchRate, this.value(thrower, pokemon))

}
