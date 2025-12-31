/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import net.minecraft.server.level.ServerLevel
import net.minecraft.core.BlockPos

/**
 * A spawner that works within a fixed area. Instances provide the center point and the radii and then the spawner
 * can be told to [run] and it will spawn Pokémon within that area.
 *
 * @author Hiroku
 * @since February 5th, 2022
 */
open class FixedAreaSpawner(
    String name,
    spawnPool: SpawnPool,
    val ServerLevel world,
    val BlockPos position,
    val horizontalRadius: Int,
    val verticalRadius: Int,
    maxPokemonPerChunk: Float = Cobblemon.config.pokemonPerChunk
) : BasicSpawner(name, spawnPool, maxPokemonPerChunk) {
    fun run(cause: SpawnCause, maxSpawns: Int? = null): List<Any> {
        return runForArea(
            zoneInput = getZoneInput(cause),
            maxSpawns = maxSpawns
        )
    }

    fun getZoneInput(cause: SpawnCause): SpawningZoneInput {
        val basePos = position.offset(-horizontalRadius, -verticalRadius, -horizontalRadius)
        return SpawningZoneInput(
            cause = cause,
            world = world,
            baseX = basePos.x,
            baseY = basePos.y,
            baseZ = basePos.z,
            length = horizontalRadius * 2 + 1,
            height = verticalRadius * 2 + 1,
            width = horizontalRadius * 2 + 1
        )
    }
}