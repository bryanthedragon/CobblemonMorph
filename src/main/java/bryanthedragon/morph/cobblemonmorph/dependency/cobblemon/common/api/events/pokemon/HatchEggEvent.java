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

public interface HatchEggEvent {
    val ServerPlayer player

    record Pre(var egg : PokemonProperties, override val ServerPlayer player) : HatchEggEvent, Cancelable() {
        val context = mutableMapOf(
            "player" to (player.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO)
        )
        val functions = moLangFunctionMap(
            cancelFunc
        )
    }

    record Post(override var ServerPlayer player, val pokemon : Pokemon) : HatchEggEvent {
        val context = mutableMapOf(
            "player" to (player.uuid.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO)
        )
    }
}
