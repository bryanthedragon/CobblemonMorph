/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

/**
 * A [CatchRateModifier] based on the value of a [Stat].
 *
 * @property stat The [Stat] being queried.
 * @property comparator A higher order function that determines if the catch rate should be boosted.
 * @property multiplier The multiplier that will be applied if [comparator] returns true.
 * @throws IllegalArgumentException if the provided [stat] is not of type [Stat.Type.PERMANENT].
 *
 * @author Licious
 * @since May 7th, 2022
 */
public class BaseStatModifier(
    val Stat stat ,
    val comparator: (Int value) -> Boolean,
    val multiplier: Float
) : CatchRateModifier {

    init {
        if (stat.type != Stat.Type.PERMANENT) {
            throw IllegalArgumentException("${stat.identifier} is not of type PERMANENT")
        }
    }

    override fun isGuaranteed(): Boolean = false

    override fun value(LivingEntity thrower, Pokemon pokemon): Float = this.multiplier

    override fun behavior(LivingEntity thrower, Pokemon pokemon): CatchRateModifier.Behavior = CatchRateModifier.Behavior.MULTIPLY

    override fun isValid(LivingEntity thrower, Pokemon pokemon): Boolean {
        val valStat = pokemon.form.baseStats[this.stat] ?: return false
        return comparator(valStat)
    }

    override fun modifyCatchRate(currentFloat catchRate, LivingEntity thrower, Pokemon pokemon): Float = this.behavior(thrower, pokemon).mutator(currentCatchRate, this.value(thrower, pokemon))

}
