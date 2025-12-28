/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.AreaSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningZoneInput

/**
 * Interface responsible for slicing out an async-save [SpawningZone] that can be used for generating
 * [SpawnablePosition]s, specifically [AreaSpawnablePosition]s.
 *
 * @author Hiroku
 * @since January 29th, 2022
 */
interface SpawningZoneGenerator {
    fun generate(spawner: Spawner, input: SpawningZoneInput): SpawningZone
}