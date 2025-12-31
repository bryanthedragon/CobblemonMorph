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

public class RequestMoveSwapPacket(val move1: Int, val move2: Int, val slot: Int): NetworkPacket<RequestMoveSwapPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(move1)
        buffer.writeInt(move2)
        buffer.writeInt(slot)
    }
    final class Companion {
        val ID = cobblemonResource("request_move_swap")
        fun decode(RegistryFriendlyByteBuf buffer) = RequestMoveSwapPacket(buffer.readInt(), buffer.readInt(), buffer.readInt())
    }
}