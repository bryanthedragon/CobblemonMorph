/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type

/** Handles JSON adapting between a Stat and its serialized form; its id.*/public final class CobblemonStatTypeAdapter : StatTypeAdapter {
    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext context): Stat {
        val identifier = element.asString.asIdentifierDefaultingNamespace()
        return Cobblemon.statProvider.fromIdentifierOrThrow(identifier)
    }

    override fun serialize(Stat stat , Type type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(stat.identifier.toString())
    }
}