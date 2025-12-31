/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCPartyProvider
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
public final class NPCPartyProviderAdapter : JsonDeserializer<NPCPartyProvider> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): NPCPartyProvider {
        val typeName = if (json.isJsonPrimitive) json.asString else json.asJsonObject.get("type").asString
        return NPCPartyProvider.types[typeName]?.invoke(typeName)?.also { it.loadFromJSON(json) }
            ?: throw IllegalStateException("Unable to find party provider by type: $typeName")
    }
}