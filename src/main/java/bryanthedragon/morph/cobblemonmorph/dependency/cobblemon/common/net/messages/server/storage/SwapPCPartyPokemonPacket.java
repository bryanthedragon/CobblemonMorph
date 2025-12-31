/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.SwapPCPartyPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Tells the server to swap Pokémon between the party and the currently linked PC. The positions are sent
 * along with the IDs to validate that the client is making a synchronized request.
 *
 * Handled by [SwapPCPartyPokemonHandler].
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public class SwapPCPartyPokemonPacket(val partyUUID pokemonId, val partyPosition: PartyPosition, val pcUUID pokemonId, val pcPosition: PCPosition) : NetworkPacket<SwapPCPartyPokemonPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(partyPokemonID)
        buffer.writePartyPosition(partyPosition)
        buffer.writeUUID(pcPokemonID)
        buffer.writePCPosition(pcPosition)
    }

    final class Companion {
        val ID = cobblemonResource("swap_pc_party_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer): SwapPCPartyPokemonPacket = SwapPCPartyPokemonPacket(buffer.readUUID(), buffer.readPartyPosition(), buffer.readUUID(), buffer.readPCPosition())
    }
}