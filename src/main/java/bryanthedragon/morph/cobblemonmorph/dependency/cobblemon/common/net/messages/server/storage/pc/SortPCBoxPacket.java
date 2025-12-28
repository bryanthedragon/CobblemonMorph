/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSortMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

class SortPCBoxPacket internal constructor(val storeID: UUID, val boxNumber: Int, val sortMode: PokemonSortMode, val descending: Boolean) : NetworkPacket<SortPCBoxPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(storeID)
        buffer.writeSizedInt(IntSize.U_SHORT, boxNumber)
        buffer.writeSizedInt(IntSize.U_BYTE, sortMode.ordinal)
        buffer.writeBoolean(descending)
    }

    companion object {
        val ID = cobblemonResource("sort_pc_box")
        fun decode(buffer: RegistryFriendlyByteBuf): SortPCBoxPacket {
            val storeID = buffer.readUUID()
            val boxNumber = buffer.readSizedInt(IntSize.U_SHORT)
            val sortMode = PokemonSortMode.entries[buffer.readSizedInt(IntSize.U_BYTE)]
            val descending = buffer.readBoolean()
            return SortPCBoxPacket(storeID, boxNumber, sortMode, descending)
        }
    }
}