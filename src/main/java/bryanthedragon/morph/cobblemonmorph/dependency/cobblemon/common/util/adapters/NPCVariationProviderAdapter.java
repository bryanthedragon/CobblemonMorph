/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.NPCVariationProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.WeightedAspect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.WeightedNPCVariationProvider
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Standard map adapter for [NPCVariationProvider]. If the value is an array it will assume that
 * it's a [WeightedNPCVariationProvider] for convenience's sake.
 *
 * @author Hiroku
 * @since August 11th, 2024
 */
public final class NPCVariationProviderAdapter : JsonDeserializer<NPCVariationProvider> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext ctx): NPCVariationProvider {
        if (json.isJsonArray) {
            val provider = WeightedNPCVariationProvider()
            provider.options = json.asJsonArray.map { ctx.deserialize(it, WeightedAspect.class) }
            return provider
        }

        val type = json.asJsonObject.get("type").asString
        val clazz = NPCVariationProvider.types[type] ?: throw IllegalArgumentException("Unknown NPCVariationProvider type: $type")
        return ctx.deserialize(json, clazz)
    }
}