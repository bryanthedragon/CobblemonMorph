/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueNoInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
public final class DialogueInputAdapter : JsonDeserializer<DialogueInput> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): DialogueInput {
        if (json.isJsonPrimitive || json.isJsonArray) {
            return DialogueNoInput(action = context.deserialize(json, DialogueAction.class))
        }

        val obj = json.asJsonObject
        val typeId = obj.get("type").asString
        return when (typeId) {
            "text" -> context.deserialize(obj, DialogueTextInput.class)
            "auto-continue" -> context.deserialize(obj, DialogueAutoContinueInput.class)
            "option" -> context.deserialize(obj, DialogueOptionSetInput.class)
            "none" -> context.deserialize(obj, DialogueNoInput.class)
            else -> throw JsonParseException("Unknown dialogue input type $typeId")
        }
    }
}