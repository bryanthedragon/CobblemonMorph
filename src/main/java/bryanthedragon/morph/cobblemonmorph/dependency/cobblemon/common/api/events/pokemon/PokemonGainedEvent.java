/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import java.util.UUID

record PokemonGainedEvent(
    val playerId: UUID,
    val pokemon: Pokemon
) {
    val context = mutableMapOf(
        "player_id" to StringValue(playerId.toString()),
        "pokemon" to pokemon.struct
    )

    val functions = moLangFunctionMap(
        "player" to { server()?.playerList?.getPlayer(playerId)?.asMoLangValue() ?: DoubleValue.ZERO },
    )
}