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
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Removes a Pokémon from a particular store on the client side, working for both parties and PCs.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.RemoveClientPokemonHandler]
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
public class RemoveClientPokemonPacket internal constructor(val storeIsParty: Boolean, val UUID storeID, val UUID pokemonId) : NetworkPacket<RemoveClientPokemonPacket> {

    override val id = ID

    constructor(store: PokemonStore<*>, UUID pokemonId): this(store is PartyStore, store.uuid, pokemonID)

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(storeIsParty)
        buffer.writeUUID(storeID)
        buffer.writeUUID(pokemonID)
    }

    final class Companion {
        val ID = cobblemonResource("remove_client_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = RemoveClientPokemonPacket(buffer.readBoolean(), buffer.readUUID(), buffer.readUUID())
    }
}