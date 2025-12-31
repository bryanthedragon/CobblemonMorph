/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
public final class DamageCategoryAdapter: JsonSerializer<DamageCategory>, JsonDeserializer<DamageCategory> {

    override fun serialize(src: DamageCategory, typeOfT srcype, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.name)
    }

    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): DamageCategory {
        return DamageCategories.getOrException(json.asString)
    }
}