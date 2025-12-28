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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Tells the participants that the capture on the specified Pokémon has finished.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureEndHandler].
 *
 * @author Hiroku
 * @since July 2nd, 2022
 */
class BattleCaptureEndPacket(val targetPNX: String, val succeeded: Boolean) : NetworkPacket<BattleCaptureEndPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeString(targetPNX)
        buffer.writeBoolean(succeeded)
    }
    companion object {
        val ID = cobblemonResource("battle_capture_end")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleCaptureEndPacket(buffer.readString(), buffer.readBoolean())
    }
}