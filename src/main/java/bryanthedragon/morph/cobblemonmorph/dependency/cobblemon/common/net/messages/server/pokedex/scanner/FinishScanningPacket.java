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

public class FinishScanningPacket(val targetedId: Int, val zoomLevel: Int) : NetworkPacket<FinishScanningPacket> {
    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(targetedId)
        buffer.writeInt(zoomLevel)
    }

    final class Companion {
        val ID = cobblemonResource("finish_scanning_packet")

        fun decode(RegistryFriendlyByteBuf buffer): FinishScanningPacket {
            val targetId = buffer.readInt()
            val zoomAmount = buffer.readInt()
            return FinishScanningPacket(targetId, zoomAmount)
        }
    }
}