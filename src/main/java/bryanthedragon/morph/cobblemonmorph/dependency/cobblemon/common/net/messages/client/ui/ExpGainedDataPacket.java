/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

class ExpGainedDataPacket(
    val pokemonUUID: UUID,
    val oldLevel: Int?,
    val expGained: Int,
    val countOfMovesLearned: Int
): NetworkPacket<ExpGainedDataPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(pokemonUUID)
        buffer.writeNullable(oldLevel) { pb, v -> pb.writeInt(v) }
        buffer.writeInt(expGained)
        buffer.writeInt(countOfMovesLearned)
    }

    companion object {
        val ID = cobblemonResource("exp_gained_data")
        fun decode(buffer: RegistryFriendlyByteBuf) = ExpGainedDataPacket(
            buffer.readUUID(),
            buffer.readNullable { buffer.readInt() },
            buffer.readInt(),
            buffer.readInt()
        )
    }
}