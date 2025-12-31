/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update.SetMarkingsHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import io.netty.buffer.ByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Handled by [SetMarkingsHandler].
 */
public class SetMarkingsPacket(val UUID uuid, val markings: List<Int>, val isParty: Boolean = true) : NetworkPacket<SetMarkingsPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(uuid)
        buffer.writeCollection(markings) { pb, value -> pb.writeInt(value) }
        buffer.writeBoolean(isParty)
    }

    final class Companion {
        val ID = cobblemonResource("set_markings")
        fun decode(RegistryFriendlyByteBuf buffer): SetMarkingsPacket {
            val uuid = buffer.readUUID()
            val markings = buffer.readList(ByteBuf::readInt).toList()
            val isParty = buffer.readBoolean()
            return SetMarkingsPacket(uuid, markings, isParty)
        }
    }
}
