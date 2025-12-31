/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Base packet class for moving a Pokémon from one position to another in the same store.
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
abstract class MoveClientPokemonPacket<T : StorePosition, N : NetworkPacket<N>>(
    val UUID storeID,
    val UUID pokemonId,
    val newPosition: T
) : NetworkPacket<N> {
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(this.storeID)
        buffer.writeUUID(this.pokemonID)
        encodePosition(buffer, this.newPosition)
    }
    abstract fun encodePosition(RegistryFriendlyByteBuf buffer, position: T)
}