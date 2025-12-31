/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class RemediesMechanic {
    val remedies = mutableMapOf<String, RemedyEntry>()

    internal fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeMap(this.remedies,
            { _, key -> buffer.writeString(key) },
            { _, entry ->
                buffer.writeExpressionLike(entry.healingAmount)
                buffer.writeExpressionLike(entry.friendshipDrop)
            }
        )
    }

    final class Companion {
        internal fun decode(RegistryFriendlyByteBuf buffer): RemediesMechanic {
            val mechanic = RemediesMechanic()

            val decodedRemedies = buffer.readMap(
                { buffer.readString() },
                {
                    val healingExpression = buffer.readExpressionLike()
                    val friendshipExpression = buffer.readExpressionLike()
                    RemedyEntry(healingExpression, friendshipExpression)
                }
            )

            mechanic.remedies.clear()
            mechanic.remedies.putAll(decodedRemedies)

            return mechanic
        }
    }

    fun getHealingAmount(type: String, MoLangRuntime runtime, default: Int = 20) = remedies[type]?.let { runtime.resolveInt(it.healingAmount) } ?: default
    fun getFriendshipDrop(type: String, MoLangRuntime runtime, default: Int = 0) = remedies[type]?.let { runtime.resolveInt(it.friendshipDrop) } ?: default
}

record RemedyEntry(val healingAmount: ExpressionLike, val friendshipDrop: ExpressionLike)