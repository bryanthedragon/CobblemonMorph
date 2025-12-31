/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
public final class SpawnRuleComponentAdapter : JsonDeserializer<SpawnRuleComponent> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): SpawnRuleComponent {
        json as JsonObject
        val type = json.get("type").asString
        val clazz = SpawnRuleComponent.types[type] ?: throw IllegalArgumentException("Unknown spawn rule component type: $type")
        return ctx.deserialize(json, clazz)
    }
}