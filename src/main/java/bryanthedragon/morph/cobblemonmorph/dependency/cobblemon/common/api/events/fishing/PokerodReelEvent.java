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
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

/**
 * Event that is fired when a fishing rod is reeled.
 * @param rod The ItemStack of the rod that is being reeled.
 */
class PokerodReelEvent(val player: Player, val rod: ItemStack) : Cancelable() {
    val context = mutableMapOf(
        "player" to player.asMoLangValue(),
        "rod" to rod.asMoLangValue(server()!!.registryAccess())
    )

    val functions = moLangFunctionMap(
        cancelFunc
    )
}