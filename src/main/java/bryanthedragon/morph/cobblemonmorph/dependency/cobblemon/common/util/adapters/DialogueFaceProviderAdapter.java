/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.PlayerDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type

/**
 * Adapter that tries to compose something that will produce a face. JSON primitives and arrays are treated as expressions.
 *
 * If the JSON object has a "type" field with the value "player", it will be deserialized as a [PlayerDialogueFaceProvider].
 *
 * Otherwise, it will be deserialized as an [ArtificialDialogueFaceProvider].
 *
 * @author Hiroku
 * @since January 1st, 2024
 */
public final class DialogueFaceProviderAdapter : JsonDeserializer<DialogueFaceProvider> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): DialogueFaceProvider {
        return when (json) {
            is JsonPrimitive -> ExpressionLikeDialogueFaceProvider(json.asString.asExpressionLike())
            is JsonArray -> ExpressionLikeDialogueFaceProvider(json.asJsonArray.map { it.asString }.asExpressionLike())
            else -> {
                val jsonObject = json.asJsonObject
                if (jsonObject.get("type")?.asString == "player") {
                    context.deserialize(json, PlayerDialogueFaceProvider.class)
                } else {
                    context.deserialize(json, ArtificialDialogueFaceProvider.class)
                }
            }
        }
    }
}