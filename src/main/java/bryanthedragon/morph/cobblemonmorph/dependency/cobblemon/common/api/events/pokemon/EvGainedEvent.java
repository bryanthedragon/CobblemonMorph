/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ifIsType

/**
 * Fired during EV mutation.
 *
 * @see [Pre]
 * @see [Post]
 */
public interface EvGainedEvent {

    /**
     * The [Stat] having EVs added to itself.
     */
    val Stat stat 

    /**
     * @see EvSource.pokemon
     */
    val pokemon: Pokemon get() = this.source.pokemon

    /**
     * The [EvSource] that fired this event.
     */
    val source: EvSource

    /**
     * Fired before EV mutation occurs, cancelling will cause the mutation to be flagged as not successful (returns 0).
     *
     * @property stat See [EvGainedEvent.stat].
     * @property amount The amount of EVs that will be gained.
     * @property source See [EvGainedEvent.source].
     */
    class Pre(override val Stat stat , var amount: Int, override val source: EvSource
    ) : EvGainedEvent, Cancelable() {
        val context = mutableMapOf(
            "pokemon" to source.pokemon.struct,
            "stat" to StringValue(stat.identifier.toString()),
            "amount" to DoubleValue(amount.toDouble()),
            "source" to StringValue(
                when (source) {
                    is BattleEvSource -> "battle"
                    is ItemEvSource -> "interaction"
                    is SidemodEvSource -> source.sidemodId
                    else -> "unknown"
                }
            )
        )
        val functions = moLangFunctionMap(
            cancelFunc,
            "set_amount" to {
                amount = it.getInt(0)
                DoubleValue.ONE
            }
        )
    }

    /**
     * Fired after EV mutation occurs, this is purely for notification purposes.
     *
     * @property stat See [EvGainedEvent.stat].
     * @property amount The final amount of EVs gained.
     * @property source See [EvGainedEvent.source].
     */
    class Post(override val Stat stat , val amount: Int, override val source: EvSource) : EvGainedEvent {
        val context = mutableMapOf(
            "pokemon" to source.pokemon.struct,
            "stat" to StringValue(stat.identifier.toString()),
            "amount" to DoubleValue(amount.toDouble()),
            "source" to StringValue(
                when (source) {
                    is BattleEvSource -> "battle"
                    is ItemEvSource -> "interaction"
                    is SidemodEvSource -> source.sidemodId
                    else -> "unknown"
                }
            )
        )
    }

}