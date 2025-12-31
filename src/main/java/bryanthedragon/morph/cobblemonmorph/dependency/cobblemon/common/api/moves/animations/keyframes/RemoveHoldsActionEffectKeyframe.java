/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.delayedFuture
import java.util.concurrent.CompletableFuture

/**
 * An [ActionEffectKeyframe] that clears some holds from the context, potentially marking the effect as
 * complete as far as any blocked actions are concerned. If no holds are specified, then all holds are
 * removed.
 *
 * @author Hiroku
 * @since October 27th, 2023
 */
public class RemoveHoldsActionEffectKeyframe : ActionEffectKeyframe {
    val delay = 0F
    val holds = mutableSetOf<String>()
    override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
        return delayedFuture(seconds = delay).thenApply {
            if (holds.isEmpty()) {
                context.holds.clear()
            } else {
                context.holds.removeAll(holds)
            }
        }
    }
}