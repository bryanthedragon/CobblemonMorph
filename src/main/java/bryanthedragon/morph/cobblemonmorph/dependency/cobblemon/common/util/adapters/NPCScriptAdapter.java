/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mojang.datafixers.util.Either
import java.lang.reflect.Type
import net.minecraft.resources.ResourceLocation
public final class NPCScriptAdapter : JsonDeserializer<Either<ResourceLocation, ExpressionLike>> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext context): Either<ResourceLocation, ExpressionLike> {
        return if (json.isJsonPrimitive) {
            try {
                val identifier = json.asString.asIdentifierDefaultingNamespace()
                Either.left(identifier)
            } catch (Exception exception) {
                Either.right(json.asString.asExpressionLike())
            }
        } else {
            Either.right(context.deserialize(json, ExpressionLike.class))
        }
    }
}