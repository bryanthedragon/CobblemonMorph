/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Notifies a player that they must close their PC GUI. If the [storeID] property is non-null, then
 * it will only close the PC GUI if it was opened for that specific PC.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.ClosePCHandler]
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
class ClosePCPacket(val storeID: UUID?) : NetworkPacket<ClosePCPacket> {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeNullable(this.storeID) { pb, value -> pb.writeUUID(value) }
    }

    companion object {
        val ID = cobblemonResource("close_pc")
        fun decode(buffer: RegistryFriendlyByteBuf): ClosePCPacket = ClosePCPacket(buffer.readNullable { it.readUUID() })
    }
}