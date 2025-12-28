/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class RequestMoveSwapPacket(val move1: Int, val move2: Int, val slot: Int): NetworkPacket<RequestMoveSwapPacket> {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(move1)
        buffer.writeInt(move2)
        buffer.writeInt(slot)
    }
    companion object {
        val ID = cobblemonResource("request_move_swap")
        fun decode(buffer: RegistryFriendlyByteBuf) = RequestMoveSwapPacket(buffer.readInt(), buffer.readInt(), buffer.readInt())
    }
}