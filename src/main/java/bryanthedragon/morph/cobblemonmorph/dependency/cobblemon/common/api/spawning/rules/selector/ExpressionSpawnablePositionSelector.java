/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveBoolean

class ExpressionSpawnablePositionSelector : SpawnablePositionSelector {
    @Transient
    val runtime = MoLangRuntime().setup()
    var expression = "true".asExpressionLike()

    override fun selects(spawnablePosition: SpawnablePosition): Boolean {
        runtime.environment.setSimpleVariable("spawnable_position", spawnablePosition.getOrSetupStruct())
        return runtime.resolveBoolean(expression)
    }
}