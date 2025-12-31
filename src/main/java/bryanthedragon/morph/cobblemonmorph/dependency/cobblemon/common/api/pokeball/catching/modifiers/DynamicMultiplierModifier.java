/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

/**
 * A [CatchRateModifier] that just applies a basic multiplier if a [condition] is met.
 *
 * @property multiplier A lambda that determines value of the multiplier.
 * @property condition A lambda that checks if a [Pokemon] can have this multiplier applied.
 *
 * @author Licious
 * @since January 29th, 2023
 */
public class DynamicMultiplierModifier(private val multiplier: (LivingEntity thrower, Pokemon pokemon) -> Float, private val condition: (LivingEntity thrower, Pokemon pokemon) -> Boolean) : CatchRateModifier {

    override fun isGuaranteed(): Boolean = false

    override fun value(LivingEntity thrower, Pokemon pokemon): Float = this.multiplier(thrower, pokemon)

    override fun behavior(LivingEntity thrower, Pokemon pokemon): CatchRateModifier.Behavior = CatchRateModifier.Behavior.MULTIPLY

    override fun isValid(LivingEntity thrower, Pokemon pokemon): Boolean = this.condition(thrower, pokemon)

    override fun modifyCatchRate(currentFloat catchRate, LivingEntity thrower, Pokemon pokemon): Float {
        return if(this.isValid(thrower, pokemon)) {
            currentCatchRate * this.behavior(thrower, pokemon).mutator(currentCatchRate, this.value(thrower, pokemon))
        } else {
            currentCatchRate
        }
    }
}