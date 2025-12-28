/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.FoodComponent
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

record Food(
        val hunger: Int = 0,
        val saturation: Float = 0f,
) {
    companion object {
        val CODEC: Codec<Food> = RecordCodecBuilder.create { builder ->
            builder.group(
                    Codec.INT.optionalFieldOf("hunger", 0).forGetter { it.hunger },
                    Codec.FLOAT.optionalFieldOf("saturation", 0f).forGetter { it.saturation },
            ).apply(builder, ::Food)
        }
    }

    fun toComponent(): FoodComponent = FoodComponent(hunger, saturation)
}