/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.InstancedPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientInstancedPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet to update some [InstancedPlayerData] on the client
 *
 * @author Hiroku, Apion
 * @since August 1st, 2022
 */
public class SetClientPlayerDataPacket(
    val type: PlayerInstancedDataStoreType,
    val playerData: ClientInstancedPlayerData,
    var isIncremental: Boolean = false
) : NetworkPacket<SetClientPlayerDataPacket> {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeResourceLocation(type.id)
        buffer.writeBoolean(isIncremental)
        playerData.encode(buffer)
    }

    final class Companion {
        val ID = cobblemonResource("set_client_playerdata")
        fun decode(RegistryFriendlyByteBuf buffer): SetClientPlayerDataPacket {
            val typeId = buffer.readResourceLocation()
            val type = PlayerInstancedDataStoreTypes.getTypeById(typeId) ?: throw IllegalArgumentException("Unknown player data type $typeId")
            val isIncremental = buffer.readBoolean()
            val result = type.decoder.invoke(buffer)
            result.isIncremental = isIncremental
            return result
        }
    }
}