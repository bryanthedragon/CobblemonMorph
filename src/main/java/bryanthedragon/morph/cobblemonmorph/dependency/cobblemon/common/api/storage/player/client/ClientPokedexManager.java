/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.AbstractPokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.SpeciesDexRecord
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

class ClientPokedexManager(
    override val speciesRecords: MutableMap<ResourceLocation, SpeciesDexRecord>,
) : AbstractPokedexManager(), ClientInstancedPlayerData {
    override fun encode(buf: RegistryFriendlyByteBuf) {
        buf.writeMap(
            speciesRecords,
            { _, key -> buf.writeString(key.toString()) },
            { _, value -> value.encode(buf) }
        )
    }

    companion object {
        fun decode(buf: RegistryFriendlyByteBuf): SetClientPlayerDataPacket {
            val map = buf.readMap(
                { buf.readString().asIdentifierDefaultingNamespace() },
                { SpeciesDexRecord().also { it.decode(buf) } }
            )
            return SetClientPlayerDataPacket(PlayerInstancedDataStoreTypes.POKEDEX, ClientPokedexManager(map))
        }

        fun runAction(data: ClientInstancedPlayerData) {
            if (data !is ClientPokedexManager) return
            CobblemonClient.clientPokedexData = data
        }

        fun runIncremental(data: ClientInstancedPlayerData) {
            if (data !is ClientPokedexManager) return
            CobblemonClient.clientPokedexData.speciesRecords.putAll(data.speciesRecords)
            CobblemonClient.clientPokedexData.clearCalculatedValues()
        }
    }
}