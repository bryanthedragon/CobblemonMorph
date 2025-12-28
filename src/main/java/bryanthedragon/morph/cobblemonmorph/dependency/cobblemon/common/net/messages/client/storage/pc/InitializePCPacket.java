/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Initializes a client side representation of a PC. It is given the ID, the number of boxes,
 * and whether overflow has occurred.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.InitializePCHandler].
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
class InitializePCPacket internal constructor(val storeID: UUID, val boxCount: Int, val hasOverflowed: Boolean) : NetworkPacket<InitializePCPacket> {

    override val id = ID

    constructor(pc: PCStore): this(pc.uuid, pc.boxes.size, pc.backupStore.any())

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(storeID)
        buffer.writeSizedInt(IntSize.U_SHORT, boxCount)
        buffer.writeBoolean(hasOverflowed)
    }

    companion object {
        val ID = cobblemonResource("initialize_pc")
        fun decode(buffer: RegistryFriendlyByteBuf) = InitializePCPacket(buffer.readUUID(), buffer.readSizedInt(IntSize.U_SHORT), buffer.readBoolean())
    }
}