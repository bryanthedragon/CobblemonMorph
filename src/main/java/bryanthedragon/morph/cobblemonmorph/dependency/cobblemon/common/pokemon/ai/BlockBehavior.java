/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue

public class BlockBehavior {
    private val immuneToSweetBerryBushBlock = false
    private val canStandOnPowderSnow = false
    val canPathThroughSaccLeaves = false
    @Transient
    val struct = ObjectValue(this).also {
        it.addFunction("immune_to_sweet_berry_bush_block") { DoubleValue(immuneToSweetBerryBushBlock) }
        it.addFunction("can_stand_on_powder_snow") { DoubleValue(canStandOnPowderSnow) }
        it.addFunction("can_path_through_sacc_leaves") { DoubleValue(canPathThroughSaccLeaves) }
    }
}