/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Swaps two Pokémon in the client side representation of a store. Works for party and PCs.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.SwapClientPokemonHandler].
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
public class SwapClientPokemonPacket internal constructor(val storeIsParty: Boolean, val UUID storeID, val pokemonID1: UUID, val pokemonID2: UUID) : NetworkPacket<SwapClientPokemonPacket> {

    override val id = ID

    constructor(store: PokemonStore<*>, pokemonID1: UUID, pokemonID2: UUID): this(store is PartyStore, store.uuid, pokemonID1, pokemonID2)

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(storeIsParty)
        buffer.writeUUID(storeID)
        buffer.writeUUID(pokemonID1)
        buffer.writeUUID(pokemonID2)
    }

    final class Companion {
        val ID = cobblemonResource("swap_client_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = SwapClientPokemonPacket(buffer.readBoolean(), buffer.readUUID(), buffer.readUUID(), buffer.readUUID())
    }
}