/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.BiomeAccessor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState

/**
 * A [GrowthFactor] that is active based on the downfall property of a [Biome].
 *
 * @property range The range of possible [Biome.getDownfall] values.
 * @property bonusYield The range of possible bonus values.
 *
 * @author Licious
 * @since December 2nd, 2022
 */
public class BiomeDownfallGrowthFactor(
    val range: MinMaxBounds.Doubles,
    val bonusYield: IntRange
) : GrowthFactor {

    override fun validateArguments() {
        if (this.bonusYield.first < 0 || this.bonusYield.last < 0) {
            throw IllegalArgumentException("$ID bonusYield must be a positive range")
        }
    }

    override fun isValid(Level worldReader, BlockState state, (BlockPos pos): Boolean {
        val biome = world.getBiome(pos).value() as BiomeAccessor
        return this.range.matches(biome.climateSettings.downfall.toDouble())
    }

    override fun yield() = this.bonusYield.random()

    override fun minYield() = this.bonusYield.first

    override fun maxYield() = this.bonusYield.last

    final class Companion {

        val ID = cobblemonResource("biome_downfall")

    }

}