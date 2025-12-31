/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import net.minecraft.server.level.ServerPlayer

public class CollectEggEvent (
    val egg : PokemonProperties,
    val maleParent : Pokemon,
    val femaleParent : Pokemon,
    val player : ServerPlayer
) : Cancelable() {
    val context = mutableMapOf(
        "male_parent" to maleParent.struct,
        "female_parent" to femaleParent.struct,
        "player" to (player.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO)
    )
    val functions = moLangFunctionMap(
        cancelFunc
    )
}
