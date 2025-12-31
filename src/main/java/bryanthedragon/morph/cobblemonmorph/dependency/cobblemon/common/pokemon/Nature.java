/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Flavour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.CodecUtils
import com.mojang.serialization.Codec
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth.floor

public class Nature(
    val name: ResourceLocation,
    val displayName: String,
    val increasedStat: Stat?,
    val decreasedStat: Stat?,
    val favouriteFlavour: Flavour?,
    val dislikedFlavour: Flavour?
) {
    fun modifyStat(Stat stat , Int value): Int {
        return when (stat) {
            increasedStat -> floor(value * 1.1)
            decreasedStat -> floor(value * 0.9)
            else -> value
        }
    }

    final class Companion {
        @JvmStatic
        val BY_IDENTIFIER_CODEC: Codec<Nature> = CodecUtils.createByIdentifierCodec(
            Natures::getNature,
            Nature::name
        ) { identifier -> "No nature for ID $identifier" }
    }
}