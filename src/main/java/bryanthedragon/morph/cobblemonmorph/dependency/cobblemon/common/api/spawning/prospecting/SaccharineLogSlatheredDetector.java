/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonPoiTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SaccharineLogSlatheredInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpatialSpawningZoneInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningZoneInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.detector.SpawningInfluenceDetector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningZoneInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.pow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.toBlockPos
import kotlin.math.ceil
import kotlin.math.sqrt
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.ai.village.poi.PoiManager
import net.minecraft.world.entity.ai.village.poi.PoiType
import net.minecraft.world.level.block.state.BlockState
public final class SaccharineLogSlatheredDetector : SpawningInfluenceDetector {
    @JvmField
    val RANGE: Int = 32

    override fun detectFromInput(spawner: Spawner, input: SpawningZoneInput): MutableList<SpawningZoneInfluence> {
        val world = input.world
        val listOfInfluences = mutableListOf<SpawningZoneInfluence>()

        val searchRange = RANGE + ceil(sqrt(((input.length pow 2) + (input.width pow 2)).toDouble())).toInt()
        val centerPos = input.getCenter().toBlockPos()

        val honeyLogPositions = world.poiManager.findAll(
            { holder: Holder<PoiType> -> holder.`is`(CobblemonPoiTypes.SACCHARINE_LOG_SLATHERED_KEY) },
            { true },
            centerPos,
            searchRange,
            PoiManager.Occupancy.ANY
        ).toList()

        for (pos in honeyLogPositions) {
            listOfInfluences.add(SpatialSpawningZoneInfluence(pos, radius = RANGE.toFloat(), SaccharineLogSlatheredInfluence(pos)))
        }

        return listOfInfluences
    }

    override fun detectFromBlock(
        ServerLevel world,
        (BlockPos pos,
        blockBlockState state
    ): List<SpawningZoneInfluence> = emptyList()
}
