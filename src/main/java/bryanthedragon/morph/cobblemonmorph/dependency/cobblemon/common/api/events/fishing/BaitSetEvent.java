/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.fishing

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import net.minecraft.world.item.ItemStack

/**
 * Event that is fired when a bait is set on a fishing rod.
 * @param rod The ItemStack of the rod that the bait is set on.
 * @param bait The ItemStack of the bait that is set on the rod.
 */
class BaitSetEvent(val rod: ItemStack, val bait: ItemStack) : Cancelable() {
    val context = server()?.let { server ->
        mutableMapOf(
            "rod" to rod.asMoLangValue(server.registryAccess()),
            "bait" to bait.asMoLangValue(server.registryAccess())
        )
    } ?: emptyMap()
    val functions = moLangFunctionMap(
        cancelFunc
    )
}