/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.database

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapterParent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.JSONStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.NBTStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import java.util.*
import net.minecraft.core.RegistryAccess
import net.minecraft.world.level.storage.LevelResource
import org.bson.Document

/**
 * A [FileStoreAdapter] for MongoDB. This allows a [PokemonStore] to be serialized to a MongoDB database.
 *
 * @author Pebbles
 * @since August 23rd, 2023
 */
@Suppress("MemberVisibilityCanBePrivate")
open class MongoDBStoreAdapter(
    protected val mongoClient: MongoClient,
    protected val databaseName: String,
) : CobblemonAdapterParent<JsonObject>(), FileStoreAdapter<JsonObject> {

    protected val Gson gson = this.createGson()

    override fun <E : StorePosition, T : PokemonStore<E>> serialize(store: T, RegistryAccess registryAccess): JsonObject = store.saveToJSON(JsonObject(), registryAccess)

    override fun save(storeClass: Class<out PokemonStore<*>>, UUID uuid, serialized: JsonObject) {
        val document = Document.parse(this.gson.toJson(serialized))
        document["uuid"] = uuid.toString()
        document["lastUpdated"] = Date()
        val collection = getCollection(storeClass)
        val filter = Document("uuid", uuid.toString())
        collection.replaceOne(filter, document, ReplaceOptions().upsert(true))
    }

    override fun <E : StorePosition, T : PokemonStore<E>> provide(storeClass: Class<T>, UUID uuid, RegistryAccess registryAccess): T? {
        val server = server()!!
        val pokemonStoreRoot = server.getWorldPath(LevelResource.ROOT).resolve("pokemon").toFile()
        val jsonAdapter = JSONStoreAdapter(
            pokemonStoreRoot.absolutePath,
            useNestedFolders = true,
            folderPerClass = true
        )
        val nbtAdapter = NBTStoreAdapter(pokemonStoreRoot.absolutePath, useNestedFolders = true, folderPerClass = true)

        // 1. Check if data exists in MongoDB
        val collection = getCollection(storeClass)
        val filter = Document("uuid", uuid.toString())
        val document = collection.find(filter).first()

        if (document != null) {
            val json = this.gson.fromJson(document.toJson(), JsonObject.class)
            val store = try {
                storeClass.getConstructor(UUID.class, UUID.class).newInstance(uuid, uuid)
            } catch (exception: NoSuchMethodException) {
                storeClass.getConstructor(UUID.class).newInstance(uuid)
            }
            store.loadFromJSON(json, registryAccess)
            return store
        }

        // 2. Fallback to checking JSON and NBT
        val nbtStore = nbtAdapter.provide(storeClass, uuid, registryAccess)
        if (nbtStore != null) {
            save(storeClass, uuid, serialize(nbtStore, registryAccess))
            return nbtStore
        }

        val jsonStore = jsonAdapter.provide(storeClass, uuid, registryAccess)
        if (jsonStore != null) {
            save(storeClass, uuid, serialize(jsonStore, registryAccess))
            return jsonStore
        }

        return null
    }

    protected open fun createGson(): Gson = Gson()

    protected open fun getCollection(storeClass: Class<out PokemonStore<*>>): MongoCollection<Document> {
        val collectionName = when (storeClass) {
            PlayerPartyStore.class -> "PlayerPartyCollection"
            PCStore.class -> "PCCollection"
            else -> "OtherCollection"
        }
        return this.mongoClient.getDatabase(this.databaseName).getCollection(collectionName)
    }

}