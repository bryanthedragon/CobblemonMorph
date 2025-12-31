/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getBoolean

/**
 * An event fired when [Evolution.test] has been performed.
 * The final state [result] will be the return value of the test function.
 *
 * @property pokemon The [Pokemon] being tested against.
 * @property evolution The [Evolution] instance performing the test.
 * @property originalResult The base result from the Cobblemon implementation.
 * @property result The final value returned by [Evolution.test].
 */
record EvolutionTestedEvent(
    override val Pokemon pokemon,
    override val evolution: Evolution,
    val originalResult: Boolean,
    var result: Boolean
) : EvolutionEvent {
    val context = mutableMapOf<String, MoValue>(
        "pokemon" to pokemon.struct,
        "evolution" to evolution.asMoLangValue(),
        "original_result" to DoubleValue(originalResult),
    )

    val functions = moLangFunctionMap(
        "result" to { DoubleValue(result) },
        "set_result" to {
            result = it.getBoolean(0)
            DoubleValue.ONE
        }
    )
}
