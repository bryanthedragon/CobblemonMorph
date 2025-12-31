/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.runtime.value.MoValue
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
public final class MoValueAdapter : JsonSerializer<MoValue>, JsonDeserializer<MoValue> {
    override fun serialize(src: MoValue, typeOfT srcype, context: JsonSerializationContext): JsonElement {
        return MoValue.writeToJson(src) ?: JsonObject()
    }

    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): MoValue {
        return MoValue.of(json)
    }
}