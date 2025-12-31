/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.detector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawningZone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawningZoneGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningZoneInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.IncenseSweetDetector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SaccharineLogSlatheredDetector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningZoneInput
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState

/**
 * Prospects for [SpawningZoneInfluence] in a world at a given position. Occurs as part of a
 * [SpawningZoneGenerator] scanning a part of the world while formulating a [SpawningZone].
 *
 * @author Hiroku
 * @since March 9th, 2025
 */
public interface SpawningInfluenceDetector {
    final class Companion {
        @JvmStatic
        val detectors = mutableSetOf<SpawningInfluenceDetector>(
            SaccharineLogSlatheredDetector,
            IncenseSweetDetector
        )
    }

    fun detectFromInput(spawner: Spawner, input: SpawningZoneInput) : List<SpawningZoneInfluence>
    fun detectFromBlock(ServerLevel world, (BlockPos pos, blockBlockState state): List<SpawningZoneInfluence>
}