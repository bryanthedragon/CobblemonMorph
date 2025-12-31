/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeExpressionLike
import net.minecraft.network.RegistryFriendlyByteBuf

public class BerriesMechanic(
    val portionHealRatio: ExpressionLike = "0.33".asExpressionLike(),
    val sitrusHealAmount: ExpressionLike = "v.pokemon.max_hp * 0.33".asExpressionLike(),
    val friendshipRaiseAmount: ExpressionLike = "v.pokemon.friendship < 100 ? 10 : (v.pokemon.friendship < 200 ? 5 : 1)".asExpressionLike(),
    val evLowerAmount: ExpressionLike = "10".asExpressionLike(),
    val ppRestoreAmount: ExpressionLike = "10".asExpressionLike(),
    val oranRestoreAmount: ExpressionLike = "10".asExpressionLike(),
) {

    internal fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeExpressionLike(portionHealRatio)
        buffer.writeExpressionLike(sitrusHealAmount)
        buffer.writeExpressionLike(friendshipRaiseAmount)
        buffer.writeExpressionLike(evLowerAmount)
        buffer.writeExpressionLike(ppRestoreAmount)
        buffer.writeExpressionLike(oranRestoreAmount)
    }

    final class Companion {
        internal fun decode(RegistryFriendlyByteBuf buffer): BerriesMechanic {
            val mechanic = BerriesMechanic(
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
            )

            return mechanic
        }
    }
}