/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress

/**
 * An [Requirement] which requires a specific [amount] of recoil without fainting in order to pass.
 * It keeps track of progress through [RecoilEvolutionProgress].
 *
 * @param amount The requirement amount of recoil
 *
 * @author Licious
 * @since January 27th, 2022
 */
public class RecoilRequirement(amount: Int) : Requirement {

    constructor() : this(0)

    /**
     * The requirement amount of recoil
     */
    val amount: Int = amount

    override fun check(Pokemon pokemon): Boolean = pokemon.evolutionProxy.current()
        .progress()
        .filterIsInstance<RecoilEvolutionProgress>()
        .any { progress -> progress.currentProgress().recoil >= this.amount }

    final class Companion {
        const val ADAPTER_VARIANT = "recoil"
    }

}