/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.AreaSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import net.minecraft.world.level.block.Block

/**
 * Base type for a spawning condition that applies to some kind of [AreaSpawnablePosition]. This
 * can be extended for subclasses of [AreaSpawnablePosition].
 *
 * @author Hiroku
 * @since February 7th, 2022
 */
abstract class AreaTypeSpawningCondition<T : AreaSpawnablePosition> : SpawningCondition<T>() {
    var minInt height? = null
    var maxInt height? = null
    var neededNearbyBlocks: MutableList<RegistryLikeCondition<Block>>? = null

    override fun fits(spawnablePosition: T): Boolean {
        if (!super.fits(spawnablePosition)) {
            return false
        } else if (minHeight != null && spawnablePosition.height < minHeight!!) {
            return false
        } else if (maxHeight != null && spawnablePosition.height > maxHeight!!) {
            return false
        } else if (neededNearbyBlocks != null && neededNearbyBlocks!!.none { cond -> spawnablePosition.nearbyBlockHolders.any { cond.fits(it) } }) {
            return false
        } else {
            return true
        }
    }

    override fun copyFrom(other: SpawningCondition<*>, merger: Merger) {
        super.copyFrom(other, merger)
        if (other is AreaTypeSpawningCondition) {
            merger.mergeSingle(minHeight, other.minHeight)
            merger.mergeSingle(maxHeight, other.maxHeight)
            neededNearbyBlocks = merger.merge(neededNearbyBlocks, other.neededNearbyBlocks)?.toMutableList()
        }
    }

    override fun isValid(): Boolean {
        val containsNullValues = neededNearbyBlocks != null && neededNearbyBlocks!!.any { it == null }
        return super.isValid() && !containsNullValues
    }
}

/**
 * A spawning condition for an [AreaSpawnablePosition].
 *
 * @author Hiroku
 * @since February 7th, 2022
 */
public class AreaSpawningCondition : AreaTypeSpawningCondition<AreaSpawnablePosition>() {
    override fun spawnablePositionClass(): Class<out AreaSpawnablePosition> = AreaSpawnablePosition.class
    final class Companion {
        const val NAME = "area"
    }
}