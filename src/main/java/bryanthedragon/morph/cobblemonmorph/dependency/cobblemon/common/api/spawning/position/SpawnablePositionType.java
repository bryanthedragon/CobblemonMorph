/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.SpawnablePositionCalculator

/**
 * A [SpawnablePosition] type that has been registered. Don't instantiate this
 * yourself because you're probably doing something wrong. Look at [SpawnablePosition.register].
 *
 * A default condition type name is provided for when a [SpawnDetail] is being interpreted
 * with a specific spawnable position - conditions in that detail can specify a condition
 * class, or if unspecified it will use the one here for convenience.
 *
 * Calculators for a custom spawnable position should be registered using [SpawnablePositionCalculator.register].
 *
 * @author Hiroku
 * @since January 31st, 2022
 */
record SpawnablePositionType<T : SpawnablePosition>(
    val name: String,
    val clazz: Class<T>,
    val defaultCondition: String
) {
    fun getWeight() = Cobblemon.bestSpawner.config.spawnablePositionTypeWeights[name] ?: 1F
}