/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.MoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Moves a Pokémon from one part of a PC to another on the client side.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.MoveClientPCPokemonHandler].
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
class MoveClientPCPokemonPacket(storeID: UUID, pokemonID: UUID, newPosition: PCPosition) : MoveClientPokemonPacket<PCPosition, MoveClientPCPokemonPacket>(storeID, pokemonID, newPosition) {
    override val id = ID
    override fun encodePosition(buffer: RegistryFriendlyByteBuf, position: PCPosition) = buffer.writePCPosition(position)
    companion object {
        val ID = cobblemonResource("move_client_pc_pokemon")
        fun decode(buffer: RegistryFriendlyByteBuf) = MoveClientPCPokemonPacket(buffer.readUUID(), buffer.readUUID(), buffer.readPCPosition())
    }
}