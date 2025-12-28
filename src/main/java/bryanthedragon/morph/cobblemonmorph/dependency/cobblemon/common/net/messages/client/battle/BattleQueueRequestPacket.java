/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Informs the client about the specific battle request that will be made of them at the next upkeep
 * or turn transition. The request isn't immediately displayed, request instructions come significantly
 * before the showdown request that indicates that a choice must be made.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleQueueRequestHandler].
 *
 * @author Hiroku
 * @since May 22nd, 2022
 */
class BattleQueueRequestPacket(val request: ShowdownActionRequest): NetworkPacket<BattleQueueRequestPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        request.saveToBuffer(buffer)
    }
    companion object {
        val ID = cobblemonResource("battle_queue_request")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleQueueRequestPacket(ShowdownActionRequest().loadFromBuffer(buffer))
    }
}