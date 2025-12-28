/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Used to indicate that a capture is being started in a battle. This is
 * to show the capture in the battle overlay.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureStartHandler].
 *
 * @author Hiroku
 * @since July 2nd, 2022
 */
class BattleCaptureStartPacket(val pokeBallType: ResourceLocation, val aspects: Set<String>, val targetPNX: String) : NetworkPacket<BattleCaptureStartPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeIdentifier(pokeBallType)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
        buffer.writeString(targetPNX)
    }
    companion object {
        val ID = cobblemonResource("battle_capture_start")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleCaptureStartPacket(buffer.readIdentifier(), buffer.readList { it.readString() }.toSet(), buffer.readString())
    }
}