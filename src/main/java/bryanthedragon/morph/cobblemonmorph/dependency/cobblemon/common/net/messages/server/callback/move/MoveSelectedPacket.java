/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet sent to the server when the player has responded to a move selection callback.
 *
 * @author Hiroku
 * @since June 30th, 2023
 */
public class MoveSelectedPacket(val UUID uuid, val Int index) : NetworkPacket<MoveSelectedPacket> {
    final class Companion {
        val ID = cobblemonResource("move_selected")
        fun decode(RegistryFriendlyByteBuf buffer) = MoveSelectedPacket(buffer.readUUID(), buffer.readSizedInt(IntSize.U_BYTE))
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(uuid)
        buffer.writeSizedInt(IntSize.U_BYTE, index)
    }
}