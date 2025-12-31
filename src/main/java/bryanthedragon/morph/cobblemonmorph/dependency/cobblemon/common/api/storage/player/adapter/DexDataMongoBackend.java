/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.PokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CodecBackedAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoClient
import java.util.UUID

/**
 * A [PlayerDataStoreBackend] for [PokedexManager]
 *
 * @author Pebbles
 * @since October 26, 2024
 */

public class DexDataMongoBackend(mongoClient: MongoClient, databaseName: String, collectionName: String) :
    MongoBackedPlayerDataStoreBackend<PokedexManager>(mongoClient, databaseName, collectionName, PlayerInstancedDataStoreTypes.POKEDEX) {
    override val gson = GsonBuilder()
        .registerTypeAdapter(PokedexManager.class, CodecBackedAdapter(PokedexManager.CODEC))
        .create()
    override val classToken = TypeToken.get(PokedexManager.class)
    override val defaultData = defaultDataFunc

    override fun initialize(store: PokedexManager) {
        store.initialize()
    }

    final class Companion {
        val defaultDataFunc = { UUID uuid ->
            PokedexManager(uuid, mutableMapOf())
        }
    }

}