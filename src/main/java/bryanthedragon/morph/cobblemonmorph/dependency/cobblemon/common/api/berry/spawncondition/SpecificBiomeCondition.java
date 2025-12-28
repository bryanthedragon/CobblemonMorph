/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.core.Holder
import net.minecraft.tags.TagKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome

class SpecificBiomeCondition : BerrySpawnCondition {
    lateinit var biome: TagKey<Biome>
    val minGroveSize: Int = 1
    val maxGroveSize: Int = 1

    override fun canSpawn(berry: Berry, biome: Holder<Biome>) = biome.`is`(this.biome)
    override fun getGroveSize(random: RandomSource) = random.nextIntBetweenInclusive(minGroveSize, maxGroveSize)

    companion object {
        val ID = cobblemonResource("specific_biome")
    }
}