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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress

/**
 * An [Requirement] for a certain amount of critical hits in a single battle.
 *
 * @param amount The amount of critical hits required.
 *
 * @author Licious
 * @since October 2nd, 2022
 */
@Suppress("unused", "CanBePrimaryConstructorProperty")
public class BattleCriticalHitsRequirement(amount: Int) : Requirement {

    constructor() : this(0)

    /**
     * The amount of critical hits required.
     */
    val amount = amount

    override fun check(Pokemon pokemon): Boolean = pokemon.evolutionProxy.current()
        .progress()
        .filterIsInstance<LastBattleCriticalHitsEvolutionProgress>()
        .any { progress -> progress.currentProgress().amount >= this.amount }

    final class Companion {
        const val ADAPTER_VARIANT = "battle_critical_hits"
    }

}