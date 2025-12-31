/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

/**
 * Spawn bucket adapter that searches the config for a matching named element to deserialize. The serialized form
 * is simply the name of the bucket.
 *
 * @author Hiroku
 * @since June 20th, 2022
 */
public final class SpawnBucketAdapter : JsonDeserializer<SpawnBucket>, JsonSerializer<SpawnBucket> {
    override fun serialize(bucket: SpawnBucket, Type type, JsonSerializationContext ctx) = JsonPrimitive(bucket.name)
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): SpawnBucket {
        return Cobblemon.bestSpawner.config.buckets.find { it.name == json.asString }
            ?: throw IllegalStateException("Spawn referred to invalid spawn bucket: ${json.asString}. Is it missing from the config?")
    }
}