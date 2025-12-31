/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Packet sent to the client to tell it to simulate animations on the client and generate the most logical
 * positions for the hitboxes.
 *
 * @author Hiroku
 * @since March 12th, 2025
 */
public class CalculateSeatPositionsPacket(val speciesResourceLocation identifier, val aspects: Set<String>, val poseType: PoseType) : NetworkPacket<CalculateSeatPositionsPacket> {
    override val ResourceLocation id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeResourceLocation(speciesIdentifier)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
        buffer.writeString(poseType.name)
    }

    final class Companion {
        val ID = cobblemonResource("calculate_seat_positions")

        fun decode(RegistryFriendlyByteBuf buffer): CalculateSeatPositionsPacket {
            return CalculateSeatPositionsPacket(
                buffer.readResourceLocation(),
                buffer.readList { _ -> buffer.readString() }.toSet(),
                PoseType.valueOf(buffer.readString().uppercase()),
            )
        }
    }
}