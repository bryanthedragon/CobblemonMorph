/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
public final class MulchVariantAdapter : JsonDeserializer<MulchVariant> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): MulchVariant {
        val mulchName = json.asString.lowercase()
        return MulchVariant.values().filter { it.name.lowercase() == mulchName }.first()
    }
}
