/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
final class SpawnDetailSelectorAdapter : JsonDeserializer<SpawnDetailSelector> {
    override fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnDetailSelector {
        return if (json.isJsonPrimitive || json.isJsonArray) {
            val expression = if (json.isJsonPrimitive) json.asString.asExpressionLike() else (json as JsonArray).asExpressionLike()
            ExpressionSpawnDetailSelector().also { it.expression = expression }
        } else {
            json as JsonObject
            val type = json.get("type").asString
            val clazz = SpawnDetailSelector.types[type] ?: throw IllegalArgumentException("Unknown spawn detail selector type: $type")
            ctx.deserialize(json, clazz)
        }
    }
}