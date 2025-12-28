/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePCPosition
import java.util.UUID
import net.minecraft.core.RegistryAccess
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Sets a specific Pokémon in a specific slot of the client-side representation of a PC.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.SetPCPokemonHandler].
 *
 * @author Hiroku
 * @since June 18th, 2022
 */
class SetPCPokemonPacket internal constructor(val storeID: UUID, val storePosition: PCPosition, val pokemon: (RegistryAccess) -> Pokemon) : NetworkPacket<SetPCPokemonPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(this.storeID)
        buffer.writePCPosition(this.storePosition)
        Pokemon.S2C_CODEC.encode(buffer, this.pokemon(buffer.registryAccess()))
    }

    companion object {
        val ID = cobblemonResource("set_pc_pokemon")
        fun decode(buffer: RegistryFriendlyByteBuf): SetPCPokemonPacket {
            val uuid = buffer.readUUID()
            val position = buffer.readPCPosition()
            val bufferCache = buffer.readBytes(buffer.readableBytes())
            return SetPCPokemonPacket(
                storeID = uuid,
                storePosition = position
            ) {
                val pokemon = Pokemon.S2C_CODEC.decode(RegistryFriendlyByteBuf(bufferCache, it))
                bufferCache.release()
                pokemon
            }
        }
    }
}