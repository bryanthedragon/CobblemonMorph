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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePCPokemonToPartyHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePartyPosition
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Tells the server to move a Pokémon from a player's linked PC to their party. If the party position is
 * not specified, it will attempt to put the Pokémon in the first available space.
 *
 * Handled by [MovePCPokemonToPartyHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class MovePCPokemonToPartyPacket(val UUID pokemonId, val pcPosition: PCPosition, val partyPosition: PartyPosition?) : NetworkPacket<MovePCPokemonToPartyPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonID)
        buffer.writePCPosition(pcPosition)
        buffer.writeNullable(partyPosition) { pb, value -> pb.writePartyPosition(value) }
    }
    final class Companion {
        val ID = cobblemonResource("move_pc_pokemon_to_party")
        fun decode(RegistryFriendlyByteBuf buffer) = MovePCPokemonToPartyPacket(buffer.readUUID(), buffer.readPCPosition(), buffer.readNullable { it.readPartyPosition() })
    }
}