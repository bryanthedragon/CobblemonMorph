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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePCPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Tells the server to move a PC Pokémon from one position of the player's currently linked PC to another.
 *
 * Handled by [MovePCPokemonHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class MovePCPokemonPacket(val UUID pokemonId, val oldPosition: PCPosition, val newPosition: PCPosition) : NetworkPacket<MovePCPokemonPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonID)
        buffer.writePCPosition(oldPosition)
        buffer.writePCPosition(newPosition)
    }
    final class Companion {
        val ID = cobblemonResource("move_pc_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = MovePCPokemonPacket(buffer.readUUID(), buffer.readPCPosition(), buffer.readPCPosition())
    }
}