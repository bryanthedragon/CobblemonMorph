/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePositionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

/**
 * Adapter to a serialized [SpawnablePositionType] name to the actual registered object.
 *
 * @since January 28th, 2022
 * @author Hiroku
 */
public final class RegisteredSpawnablePositionAdapter : JsonSerializer<SpawnablePositionType<*>>, JsonDeserializer<SpawnablePositionType<*>> {
    override fun serialize(spawnablePosition: SpawnablePositionType<*>, Type type, JsonSerializationContext ctx) = JsonPrimitive(spawnablePosition.name)
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) = SpawnablePosition.getByName(json.asString)
        ?: throw IllegalArgumentException("No such spawnable position: ${json.asString}")
}