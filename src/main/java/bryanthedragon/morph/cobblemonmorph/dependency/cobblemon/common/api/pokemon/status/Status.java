/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import net.minecraft.resources.ResourceLocation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.CodecUtils
import com.mojang.serialization.Codec

/**
 * Represents the base of a status
 *
 * @author Deltric
 */
open class Status(
    val name: ResourceLocation,
    val showdownName: String = "",
    val applyMessage: String,
    val removeMessage: String
) {
    fun getActionEffect() = ActionEffects.actionEffects[name]

    final class Companion {
        /**
         * A [Codec] for [Status].
         */
        @JvmStatic
        val CODEC: Codec<Status> = CodecUtils.createByIdentifierCodec(
            Statuses::getStatus,
            Status::name
        ) { identifier -> "No Status for ID $identifier" }
    }
}