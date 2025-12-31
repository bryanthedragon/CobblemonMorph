/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.NameExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getString
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mojang.datafixers.util.Either
import java.lang.reflect.Type

/**
 * Deserializes what is either an Expression or an object describing a MoLang variable that will be put on an entity.
 *
 * @author Hiroku
 * @since December 28th, 2024
 */
public final class ExpressionOrEntityVariableAdapter : JsonDeserializer<Either<Expression, MoLangConfigVariable>> {
    override fun deserialize(
        JsonElement json,
        typeOfT: Type,
        JsonDeserializationContext context
    ): Either<Expression, MoLangConfigVariable> {
        return if (json.isJsonObject) {
            Either.right(context.deserialize(json, MoLangConfigVariable.class))
        } else {
            val expression = context.deserialize<Expression>(json, Expression.class)
            if (expression is NameExpression) {
                // In this case, it was PROBABLY an attempt at doing a string and it all went horribly wrong.
                // Interpret it as a string of the original expression.
                if (expression.names.size == 1 || expression.names.first() !in listOf("q", "query", "c", "context", "v", "variable", "m", "math")) {
                    return Either.left(("'" + expression.getString() + "'").asExpression())
                }
            }
            Either.left(expression)
        }
    }
}