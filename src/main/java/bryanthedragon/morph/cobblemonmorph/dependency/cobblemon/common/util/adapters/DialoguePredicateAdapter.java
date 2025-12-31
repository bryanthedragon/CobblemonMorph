/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
public final class DialoguePredicateAdapter : JsonDeserializer<DialoguePredicate> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): DialoguePredicate {
        return when (json) {
            is JsonObject -> {
                val typeId = json.get("type").asString
                val clazz = DialoguePredicate.types[typeId] ?: throw IllegalArgumentException("Unknown dialogue predicate type $typeId")
                context.deserialize(json, clazz)
            }
            is JsonArray -> ExpressionLikeDialoguePredicate(json.asList().map { it.asString }.asExpressionLike())
            else -> ExpressionLikeDialoguePredicate(json.asString.asExpressionLike())
        }
    }
}