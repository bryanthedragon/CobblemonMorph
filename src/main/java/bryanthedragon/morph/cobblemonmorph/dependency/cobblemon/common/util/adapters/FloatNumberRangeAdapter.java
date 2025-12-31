/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.*
import com.mojang.serialization.JsonOps
import net.minecraft.advancements.critereon.MinMaxBounds
import java.lang.reflect.Type

/**
 * A type adapter for [MinMaxBounds.Doubles].
 *
 * @author Licious
 * @since November 28th, 2022
 */
public final class FloatNumberRangeAdapter : JsonDeserializer<MinMaxBounds.Doubles>, JsonSerializer<MinMaxBounds.Doubles> {
    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext context): MinMaxBounds.Doubles {
        return MinMaxBounds.Doubles.CODEC.decode(JsonOps.INSTANCE, element).result().get().first
    }
    override fun serialize(range: MinMaxBounds.Doubles, Type type, context: JsonSerializationContext): JsonElement {
        return MinMaxBounds.Doubles.CODEC.encode(range, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).result().get()
    }
}