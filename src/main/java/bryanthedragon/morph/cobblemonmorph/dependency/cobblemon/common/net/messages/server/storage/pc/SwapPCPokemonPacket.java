/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.SwapPCPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Tells the server to swap two Pokémon in the PC linked to the player.
 *
 * Handled by [SwapPCPokemonHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class SwapPCPokemonPacket(val pokemon1ID: UUID, val position1: PCPosition, val pokemon2ID: UUID, val position2: PCPosition) : NetworkPacket<SwapPCPokemonPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemon1ID)
        buffer.writePCPosition(position1)
        buffer.writeUUID(pokemon2ID)
        buffer.writePCPosition(position2)
    }

    final class Companion {
        val ID = cobblemonResource("swap_pc_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = SwapPCPokemonPacket(buffer.readUUID(), buffer.readPCPosition(), buffer.readUUID(), buffer.readPCPosition())
    }
}