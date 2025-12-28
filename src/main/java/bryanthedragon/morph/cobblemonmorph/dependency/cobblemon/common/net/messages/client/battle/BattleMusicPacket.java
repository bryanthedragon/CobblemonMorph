/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Instructs a client what SoundEvent to play during a battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleMusicHandler].
 *
 * @property music The location of the sound to play during a battle. If null, gracefully ends the currently playing sound.
 * @property restartExisting If true and the location of [music] matches the currently playing sound, will restart from the beginning.
 *
 * @author Segfault Guy
 * @since April 20th, 2023
 */
class BattleMusicPacket(
    var music: ResourceLocation? = null,
    var volume: Float = 1.0f,
    var pitch: Float = 1.0f,
    var restartExisting: Boolean = true
) : NetworkPacket<BattleMusicPacket> {

    companion object {
        val ID = cobblemonResource("battle_music")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleMusicPacket(
            music = buffer.readNullable { it.readIdentifier() },
            volume = buffer.readFloat(),
            pitch = buffer.readFloat(),
            restartExisting = buffer.readBoolean()
        )
    }

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeNullable(music) { buf, value -> buf.writeIdentifier(value) }
        buffer.writeFloat(volume)
        buffer.writeFloat(pitch)
        buffer.writeBoolean(restartExisting)
    }
}