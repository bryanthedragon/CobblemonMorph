/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeExpressionLike;

import net.minecraft.network.RegistryFriendlyByteBuf;

public class PotionsMechanic(val potionRestoreAmount: ExpressionLike = "60".asExpressionLike(), val superPotionRestoreAmount: ExpressionLike = "100".asExpressionLike(), val hyperPotionRestoreAmount: ExpressionLike = "150".asExpressionLike()) {
    internal fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeExpressionLike(potionRestoreAmount)
        buffer.writeExpressionLike(superPotionRestoreAmount)
        buffer.writeExpressionLike(hyperPotionRestoreAmount)
    }

    final class Companion {
        internal fun decode(RegistryFriendlyByteBuf buffer): PotionsMechanic {
            val mechanic = PotionsMechanic(
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
                buffer.readExpressionLike(),
            )

            return mechanic
        }
    }
}