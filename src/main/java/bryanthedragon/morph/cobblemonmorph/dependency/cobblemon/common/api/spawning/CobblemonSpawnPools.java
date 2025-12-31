/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BiomePrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BucketPrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawnablePositionTypePrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider
import net.minecraft.server.MinecraftServer

/**
 * A collection of all of Cobblemon's general-purpose [SpawnPool]s. These
 * are referenced by Cobblemon spawner implementations. Updating these will update
 * the spawns across the entire mod.
 *
 * @author Hiroku
 * @since February 10th, 2022
 */
public final class CobblemonSpawnPools {
    /** [SpawnPool] used for standard world spawning. */
    lateinit var WORLD_SPAWN_POOL: SpawnPool

    fun load() {
        WORLD_SPAWN_POOL = CobblemonDataProvider.register(SpawnPool("world")
            .addPrecalculators(
                SpawnablePositionTypePrecalculation,
                BucketPrecalculation,
                BiomePrecalculation
            ), reloadable = true
        )
    }

    fun onServerLoad(server: MinecraftServer) {
        Cobblemon.LOGGER.info("Optimizing spawn pools...")
        WORLD_SPAWN_POOL.onServerLoad(server)
    }
}