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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike

public class PauseActionEffectKeyframe : ConditionalActionEffectKeyframe() {
    val pause = "1".asExpressionLike()
    override fun playWhenTrue(context: ActionEffectContext) = delayedFuture(seconds = pause.resolveFloat(context.runtime))
}