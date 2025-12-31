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
 * Packet sent when a PokéBall capturing a battle Pokémon just shook. The direction is included.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureShakeHandler].
 *
 * @author Hiroku
 * @since July 3rd, 2022
 */
public class BattleCaptureShakePacket(val targetPNX: String, val direction: Boolean) : NetworkPacket<BattleCaptureShakePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(targetPNX)
        buffer.writeBoolean(direction)
    }
    final class Companion {
        val ID = cobblemonResource("battle_capture_shake")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleCaptureShakePacket(buffer.readString(), buffer.readBoolean())
    }
}