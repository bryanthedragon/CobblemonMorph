/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokedex.scanner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class StartScanningPacket(val targetedId: Int, val zoomLevel: Int) : NetworkPacket<StartScanningPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(targetedId)
        buffer.writeInt(zoomLevel)
    }

    companion object {
        val ID = cobblemonResource("start_scanning_packet")

        fun decode(buffer: RegistryFriendlyByteBuf): StartScanningPacket {
            val targetId = buffer.readInt()
            val zoomAmount = buffer.readInt()
            return StartScanningPacket(targetId, zoomAmount)
        }
    }
}