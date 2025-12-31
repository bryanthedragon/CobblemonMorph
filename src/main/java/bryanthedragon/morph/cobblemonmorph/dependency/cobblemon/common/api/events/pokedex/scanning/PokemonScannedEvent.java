/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokedex.scanning

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokedexEntityData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.ScannableEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

record PokemonScannedEvent(val ServerPlayer player, val scannedPokemonEntityData: PokedexEntityData, val scannedEntity: ScannableEntity) {
    val isOwned: Boolean
        get() = scannedPokemonEntityData.pokemon.getOwnerUUID() == player.uuid

    val context: MutableMap<String, MoValue> = mutableMapOf(
        "player" to player.asMoLangValue(),
        "pokemon" to scannedPokemonEntityData.pokemon.struct,
        "disguise" to (scannedPokemonEntityData.disguise?.struct ?: DoubleValue.ZERO),
        "entity" to when (scannedEntity) {
            is LivingEntity -> scannedEntity.asMostSpecificMoLangValue()
            else -> DoubleValue.ZERO
        },
        "is_owned" to DoubleValue(if (isOwned) 1.0 else 0.0)
    )
}