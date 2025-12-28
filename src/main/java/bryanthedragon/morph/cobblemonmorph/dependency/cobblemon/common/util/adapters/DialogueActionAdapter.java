/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
final class DialogueActionAdapter : JsonDeserializer<DialogueAction> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogueAction {
        return when (json) {
            is JsonObject -> {
                val typeId = json.get("type").asString
                val clazz = DialogueAction.types[typeId] ?: throw IllegalArgumentException("Unknown dialogue action type $typeId")
                context.deserialize(json, clazz)
            }
            is JsonArray -> ExpressionLikeDialogueAction(json.asList().map { it.asString }.asExpressionLike())
            else -> ExpressionLikeDialogueAction(json.asString.asExpressionLike())
        }
    }
}