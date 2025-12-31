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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Fired during a Hyper Training mutation.
 *
 * @see [Pre]
 * @see [Post]
 */
public interface HyperTrainedIvEvent {

    /**
     * The [Stat] having whose IV is being hyper-trained.
     */
    val Stat stat 

    /**
     * The [Pokemon] being hyper-trained.
     */
    val Pokemon pokemon

    /**
     * Fired before Hyper Training occurs, cancelling will cause the mutation to be flagged as not successful.
     */
    class Pre(override val Pokemon pokemon,  override val Stat stat , var Int value) : HyperTrainedIvEvent, Cancelable() {
        val context = mutableMapOf(
            "pokemon" to pokemon.struct,
            "stat" to StringValue(stat.toString()),
            "value" to DoubleValue(value)
        )
        val functions = moLangFunctionMap(cancelFunc)
    }

    /**
     * Fired after Hyper Training occurs, this is purely for notification purposes.
     */
    class Post(override val Pokemon pokemon, override val Stat stat , val Int value) : HyperTrainedIvEvent {
        val context = mutableMapOf(
            "pokemon" to pokemon.struct,
            "stat" to StringValue(stat.toString()),
            "value" to DoubleValue(value)
        )
    }
}