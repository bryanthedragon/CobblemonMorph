/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.createDuplicateRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat

public class WalkBehaviour {
    val canWalk = true
    val avoidsLand = false
    var walkSpeed: Expression = "0.35".asExpression()

    @Transient
    val struct = ObjectValue(this).also {
        it.addFunction("can_walk") { DoubleValue(canWalk) }
        it.addFunction("avoids_land") { DoubleValue(avoidsLand) }
        it.addFunction("walk_speed") { it.environment.createDuplicateRuntime().resolveFloat(walkSpeed) }
    }
}