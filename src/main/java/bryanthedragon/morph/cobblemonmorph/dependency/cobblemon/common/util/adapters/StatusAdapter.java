/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.google.gson.*
import java.lang.reflect.Type
public final class StatusAdapter : JsonDeserializer<Status>, JsonSerializer<Status> {
    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext context): Status {
        val id = element.asString.asIdentifierDefaultingNamespace()
        val status = Statuses.getStatus(id)
        return status ?: throw IllegalArgumentException("There is no status with the ID $id")
    }

    override fun serialize(status: Status, Type type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(status.name.toString())
    }
}