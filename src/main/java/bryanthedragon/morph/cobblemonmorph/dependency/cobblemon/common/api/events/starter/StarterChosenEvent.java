/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import net.minecraft.server.level.ServerPlayer

/**
 * Event fired when a starter Pokémon is chosen.
 *
 * @author Hiroku
 * @since August 1st, 2022
 */
record StarterChosenEvent(val ServerPlayer player, val properties: PokemonProperties, var Pokemon pokemon) : Cancelable() {
    /**
     * Returns a context map for use in MoLang functions.
     */
    fun getContext(): MutableMap<String, MoValue> {
        return mutableMapOf(
            "player" to (player.asMoLangValue() ?: DoubleValue.ZERO),
            "pokemon" to pokemon.struct
        )
    }

    /**
     * A map of MoLang functions that can be used in this event.
     */
    val functions = moLangFunctionMap(
        cancelFunc
    )
}