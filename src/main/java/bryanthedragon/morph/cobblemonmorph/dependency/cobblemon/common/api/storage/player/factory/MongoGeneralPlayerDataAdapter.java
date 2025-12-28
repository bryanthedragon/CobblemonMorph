/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.GeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.MongoPlayerDataBackend
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.removeIf
import com.mongodb.client.MongoClient
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayer
import java.util.UUID

class MongoPlayerDataStoreFactory(mongoClient: MongoClient, databaseName: String) : PlayerInstancedDataFactory<GeneralPlayerData> {

    private val cache = mutableMapOf<UUID, GeneralPlayerData>()
    private val adapter = MongoPlayerDataBackend(mongoClient, databaseName)

    override fun setup(server: MinecraftServer) {
        TODO("Not yet implemented")
    }

    override fun getForPlayer(playerId: UUID): GeneralPlayerData {
        TODO("Not yet implemented")
    }

    override fun saveAll() {
        cache.forEach { (_, pd) -> adapter.save(pd) }
        cache.removeIf { (uuid, _) -> uuid.getPlayer() == null }
    }

    override fun saveSingle(playerId: UUID) {
        adapter.save(getForPlayer(playerId))
    }

    override fun onPlayerDisconnect(player: ServerPlayer) {
        cache.remove(player.uuid)
    }

    override fun sendToPlayer(player: ServerPlayer) {
        player.sendPacket(SetClientPlayerDataPacket(getForPlayer(player)))
    }

}

 */
