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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.AbstractPokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.FormDexRecord
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.PokedexEntryProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokedexEntityData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import java.util.UUID

/**
 * Event that fires when a Pokémon's information is gained or updated in the Pokédex.
 */
sealed interface PokedexDataChangedEvent {
    val dataSource: PokedexEntityData
    val knowledge: PokedexEntryProgress
    val UUID playerUUID
    val record: FormDexRecord

    val pokedexManager: AbstractPokedexManager
        get() = record.speciesDexRecord.pokedexManager

    fun getContext(): MutableMap<String, MoValue> {
        return mutableMapOf(
            "player" to (playerUUID.getPlayer()?.asMoLangValue() ?: DoubleValue.ZERO),
            "pokemon" to dataSource.pokemon.struct,
            "disguise" to (dataSource.disguise?.struct ?: DoubleValue.ZERO),
            "knowledge" to StringValue(knowledge.name.lowercase()),
            "pokedex" to pokedexManager.struct
        )
    }

    class Pre(
        override val dataSource: PokedexEntityData,
        override val knowledge: PokedexEntryProgress,
        override val UUID playerUUID,
        override val record: FormDexRecord
    ) : PokedexDataChangedEvent, Cancelable() {
        val functions = moLangFunctionMap(cancelFunc)
    }

    class Post(
        override val dataSource: PokedexEntityData,
        override val knowledge: PokedexEntryProgress,
        override val UUID playerUUID,
        override val record: FormDexRecord
    ) : PokedexDataChangedEvent
}
