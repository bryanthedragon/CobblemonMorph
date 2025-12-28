/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.healing

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HealingSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.HealingBerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PotionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Event that is fired when a Pokemon is healed.
 * @param pokemon The Pokemon that is being healed.
 * @param amount The amount of healing that is being applied. If -1, the Pokemon is fully healed.
 * @param source The HealingSource that is being used to heal the Pokemon.
 *
 * @see HealingSource
 */
class PokemonHealedEvent(
    val pokemon: Pokemon,
    var amount: Int = -1,
    val source: HealingSource = HealingSource.Force
) : Cancelable() {
    fun isFullHeal() = amount == -1
    fun isHealed() = amount > 0

    val context = mutableMapOf(
        "pokemon" to pokemon.struct,
        "amount" to DoubleValue(amount),
        "source" to StringValue(source.toString())
    )

    val functions = moLangFunctionMap(
        cancelFunc
    )
}