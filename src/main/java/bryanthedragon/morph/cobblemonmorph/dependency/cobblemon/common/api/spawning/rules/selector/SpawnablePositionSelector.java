/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition

public interface SpawnablePositionSelector {
    final class Companion {
        val types = mutableMapOf<String, Class<out SpawnablePositionSelector>>()

        inline fun <reified T : SpawnablePositionSelector> register(type: String) {
            types[type] = T.class
        }
    }

    fun selects(spawnablePosition: SpawnablePosition): Boolean
}