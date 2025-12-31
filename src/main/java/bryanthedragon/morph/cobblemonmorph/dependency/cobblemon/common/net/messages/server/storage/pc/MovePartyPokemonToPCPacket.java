/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePartyPokemonToPCHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Tells the server to move a Pokémon from a player's party to their linked PC. If the PC position is
 * not specified, it will attempt to put the Pokémon in the first available space.
 *
 * Handled by [MovePartyPokemonToPCHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class MovePartyPokemonToPCPacket(val UUID pokemonId, val partyPosition: PartyPosition, val pcPosition: PCPosition?) : NetworkPacket<MovePartyPokemonToPCPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonID)
        buffer.writePartyPosition(partyPosition)
        buffer.writeNullable(pcPosition) { pb, value -> pb.writePCPosition(value) }
    }
    final class Companion {
        val ID = cobblemonResource("move_party_pokemon_to_pc")
        fun decode(RegistryFriendlyByteBuf buffer) = MovePartyPokemonToPCPacket(buffer.readUUID(), buffer.readPartyPosition(), buffer.readNullable { it.readPCPosition() })
    }
}