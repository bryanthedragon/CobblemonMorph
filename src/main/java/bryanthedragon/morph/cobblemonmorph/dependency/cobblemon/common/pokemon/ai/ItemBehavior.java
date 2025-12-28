/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asArrayValue

class ItemBehavior {
    private val desiredItems = listOf<ObtainableItem>()

    @Transient
    val struct = ObjectValue(this).also {
        it.addFunction("pickup_items") {
            return@addFunction desiredItems.asArrayValue(ObtainableItem::struct)
        }
    }
}