/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue

class ThunderstruckBehaviour {
    val rotateFeatures = mutableListOf<RotateFeature>()

    // shorthand of ‘should run special code in thunderHit’
    fun isSpecial(): Boolean = rotateFeatures.isNotEmpty()

    @Transient
    val struct = ObjectValue(this).also {
        it.addFunction("has_rotating_features") { this.rotateFeatures.isNotEmpty() }
    }

    record RotateFeature(val key: String, val chain: List<String>) {
        @Transient
        val struct = ObjectValue(this)
    }
}