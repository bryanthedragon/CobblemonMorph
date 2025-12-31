/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueGibber
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class DialogueGibberDTO(
    val graduallyShowText: Boolean,
    val allowSkip: Boolean,
    val step: Int,
    val interval: Float,
    val minPitch: Float,
    val maxPitch: Float,
    val minVolume: Float,
    val maxVolume: Float,
    val sounds: List<ResourceLocation>
) {
    constructor(gibber: DialogueGibber): this(
        graduallyShowText = gibber.graduallyShowText,
        allowSkip = gibber.allowSkip,
        step = gibber.step,
        interval = gibber.interval.toFloat(),
        minPitch = gibber.minPitch,
        maxPitch = gibber.maxPitch,
        minVolume = gibber.minVolume,
        maxVolume = gibber.maxVolume,
        sounds = gibber.sounds
    )

    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(graduallyShowText)
        buffer.writeBoolean(allowSkip)
        buffer.writeInt(step)
        buffer.writeFloat(interval)
        buffer.writeFloat(minPitch)
        buffer.writeFloat(maxPitch)
        buffer.writeFloat(minVolume)
        buffer.writeFloat(maxVolume)
        buffer.writeCollection(sounds) { _, value -> buffer.writeIdentifier(value) }
    }

    final class Companion {
        fun decode(RegistryFriendlyByteBuf buffer): DialogueGibberDTO {
            val graduallyShowText = buffer.readBoolean()
            val allowSkip = buffer.readBoolean()
            val step = buffer.readInt()
            val interval = buffer.readFloat()
            val minPitch = buffer.readFloat()
            val maxPitch = buffer.readFloat()
            val minVolume = buffer.readFloat()
            val maxVolume = buffer.readFloat()
            val sounds = buffer.readList { it.readIdentifier() }
            return DialogueGibberDTO(graduallyShowText, allowSkip, step, interval, minPitch, maxPitch, minVolume, maxVolume, sounds)
        }
    }
}