/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.MoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePartyPosition
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Moves a Pokémon from one party place to another on the client side.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.MoveClientPartyPokemonHandler]
 *
 * @author Hiroku
 * @since November 29th, 2021
 */
public class MoveClientPartyPokemonPacket(UUID storeID, UUID pokemonId, newPosition: PartyPosition) : MoveClientPokemonPacket<PartyPosition, MoveClientPartyPokemonPacket>(storeID, pokemonID, newPosition) {
    override val id = ID
    override fun encodePosition(RegistryFriendlyByteBuf buffer, position: PartyPosition) = buffer.writePartyPosition(newPosition)
    final class Companion {
        val ID = cobblemonResource("move_client_party_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = MoveClientPartyPokemonPacket(buffer.readUUID(), buffer.readUUID(), buffer.readPartyPosition())
    }
}