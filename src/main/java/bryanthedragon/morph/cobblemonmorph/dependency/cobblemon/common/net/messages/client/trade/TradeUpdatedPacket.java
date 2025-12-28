/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet sent to the client when the other player updates their offered Pokémon.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeUpdatedHandler]
 *
 * @author Hiroku
 * @since March 5th, 2023
 */
class TradeUpdatedPacket(val playerId: UUID, val pokemon: Pokemon?) : NetworkPacket<TradeUpdatedPacket>, UnsplittablePacket {
    companion object {
        val ID = cobblemonResource("trade_updated")
        fun decode(buffer: RegistryFriendlyByteBuf) = TradeUpdatedPacket(buffer.readUUID(), buffer.readNullable { Pokemon.S2C_CODEC.decode(buffer) })
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(playerId)
        buffer.writeNullable(pokemon) { _, pokemon -> Pokemon.S2C_CODEC.encode(buffer, pokemon) }
    }
}