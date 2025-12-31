/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.provider.SpeciesFeatureProvider;

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Adapter for [SpeciesFeatureProvider]s. This will rely on a 'type' property, which will be used as a lookup in
 * [SpeciesFeatures.types].
 *
 * @author Hiroku
 * @since November 30th, 2022
 */
public final class SpeciesFeatureProviderAdapter : JsonDeserializer<SpeciesFeatureProvider<*>> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): SpeciesFeatureProvider<*> {
        val typeName = json.asJsonObject.get("type").asString
        val clazz = SpeciesFeatures.types[typeName] ?: throw IllegalArgumentException("No type registered in SpeciesFeatures for name: $typeName")
        return ctx.deserialize(json, clazz)
    }
}