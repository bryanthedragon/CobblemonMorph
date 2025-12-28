/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawningZone
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

/**
 * An area in which to slice out a [SpawningZone].
 *
 * @author Hiroku
 * @since February 5th, 2022
 */
record SpawningZoneInput(
    val cause: SpawnCause,
    val world: ServerLevel,
    val baseX: Int,
    val baseY: Int,
    val baseZ: Int,
    val length: Int,
    val height: Int,
    val width: Int
) {
    fun getCenter(): Vec3 =
        Vec3(
            baseX + length / 2.0,
            baseY + height / 2.0,
            baseZ + width / 2.0
        )
}