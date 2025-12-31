/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ConditionalSpawnablePositionSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnablePositionSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnablePositionSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
public final class SpawnablePositionSelectorAdapter : JsonDeserializer<SpawnablePositionSelector> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): SpawnablePositionSelector {
        return if (json.isJsonPrimitive || json.isJsonArray) {
            val expression = if (json.isJsonPrimitive) json.asString.asExpressionLike() else (json as JsonArray).asExpressionLike()
            ExpressionSpawnablePositionSelector().also { it.expression = expression }
        } else {
            json as JsonObject
            val type = json.get("type")?.asString ?: return ctx.deserialize(json, ConditionalSpawnablePositionSelector.class)
            val clazz = SpawnablePositionSelector.types[type] ?: throw IllegalArgumentException("Unknown spawn detail selector type: $type")
            ctx.deserialize(json, clazz)
        }
    }
}