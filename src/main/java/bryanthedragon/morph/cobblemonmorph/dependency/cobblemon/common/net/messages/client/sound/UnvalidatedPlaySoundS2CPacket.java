/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.sounds.SoundSource
import net.minecraft.resources.ResourceLocation

/**
 * A class meant to mimic [PlaySoundS2CPacket] without validating the Sound Event registry.
 * This should only be used for our dynamic sounds such as Pokémon ambience.
 *
 * @author Licious
 * @since December 29th, 2022
 */
public class UnvalidatedPlaySoundS2CPacket(
    var sound: ResourceLocation,
    var SoundSource category,
    var x: Double,
    var y: Double,
    var z: Double,
    var Float volume,
    var Float pitch
) : NetworkPacket<UnvalidatedPlaySoundS2CPacket> {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(this.sound)
        buffer.writeEnumConstant(this.category)
        buffer.writeDouble(this.x)
        buffer.writeDouble(this.y)
        buffer.writeDouble(this.z)
        buffer.writeFloat(this.volume)
        buffer.writeFloat(this.pitch)
    }

    final class Companion {
        val ID = cobblemonResource("unvalidated_play_sound")
        fun decode(RegistryFriendlyByteBuf buffer) = UnvalidatedPlaySoundS2CPacket(
            buffer.readIdentifier(),
            buffer.readEnumConstant(SoundSource.class),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readFloat(),
            buffer.readFloat()
        )
    }
}