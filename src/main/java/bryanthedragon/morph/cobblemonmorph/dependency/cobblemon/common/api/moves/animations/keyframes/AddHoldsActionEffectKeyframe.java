/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import java.util.concurrent.CompletableFuture

/**
 * An [ActionEffectKeyframe] that adds some holds from the context for future code to recognize as blocking some
 * functionality from proceeding.
 *
 * @author Hiroku
 * @since October 27th, 2023
 */
public class AddHoldsActionEffectKeyframe : ActionEffectKeyframe {
    val holds = mutableSetOf<String>()
    override fun play(context: ActionEffectContext): CompletableFuture<Unit> {
        context.holds.addAll(holds)
        return CompletableFuture.completedFuture(Unit)
    }
}