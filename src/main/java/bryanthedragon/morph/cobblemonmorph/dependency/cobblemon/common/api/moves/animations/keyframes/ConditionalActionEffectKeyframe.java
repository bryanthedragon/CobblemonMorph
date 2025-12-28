/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import java.util.concurrent.CompletableFuture

abstract class ConditionalActionEffectKeyframe : ActionEffectKeyframe {
    var condition: ExpressionLike = "true".asExpressionLike()
    override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
        return if (condition.resolveBoolean(context.runtime)) {
            playWhenTrue(context)
        } else {
            skip()
        }
    }

    abstract fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit>
}