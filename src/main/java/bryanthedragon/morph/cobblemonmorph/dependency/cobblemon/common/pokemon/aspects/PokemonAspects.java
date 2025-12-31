/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCosmeticItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.SingleConditionalAspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Characteristic
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getMemorySafely
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server

val SHINY_ASPECT = object : SingleConditionalAspectProvider {
    override val aspect = "shiny"
    override fun meetsCondition(Pokemon pokemon) = pokemon.shiny
    override fun meetsCondition(pokemonProperties: PokemonProperties) = pokemonProperties.shiny == true
}

val GENDER_ASPECT = object : AspectProvider {
    fun getAspectsForGender(gender: Gender) = setOf(
        when (gender) {
            Gender.MALE -> "male"
            Gender.FEMALE -> "female"
            Gender.GENDERLESS -> "genderless"
        }
    )

    override fun provide(Pokemon pokemon) = getAspectsForGender(pokemon.gender)
    override fun provide(properties: PokemonProperties) = properties.gender?.let { getAspectsForGender(it) } ?: emptySet()
}

val COSMETIC_SLOT_ASPECT = object : AspectProvider {
    override fun provide(Pokemon pokemon): Set<String> {
        val server = server() ?: return emptySet()
        if (pokemon.cosmeticItem.isEmpty) {
            return emptySet()
        }
        val cosmeticItem = CobblemonCosmeticItems.findValidCosmeticForPokemonAndItem(server.registryAccess(), pokemon, pokemon.cosmeticItem)
        return cosmeticItem?.aspects?.toSet() ?: emptySet()
    }

    override fun provide(properties: PokemonProperties) = emptySet<String>()
}

val CHARACTERISTIC_RAINBOW_ASPECT = object : AspectProvider {
    fun calculateColourAspect(nature: Nature, characteristic: Characteristic): String? {
        return when (nature.increasedStat to characteristic.relevantStat) {
            (Stats.ATTACK to Stats.ATTACK),
            (Stats.ATTACK to Stats.HP),
            (null to Stats.ATTACK),
            (Stats.SPEED to Stats.DEFENCE),
            (Stats.DEFENCE to Stats.SPEED)
                 -> "rainbow-red"

            (Stats.ATTACK to Stats.DEFENCE),
            (Stats.DEFENCE to Stats.ATTACK)
                -> "rainbow-orange"

            (Stats.DEFENCE to Stats.DEFENCE),
            (Stats.DEFENCE to Stats.HP),
            (null to Stats.DEFENCE),
            (Stats.ATTACK to Stats.SPECIAL_DEFENCE),
            (Stats.SPECIAL_DEFENCE to Stats.ATTACK)
                -> "rainbow-yellow"

            (Stats.DEFENCE to Stats.SPECIAL_DEFENCE),
            (Stats.SPECIAL_DEFENCE to Stats.DEFENCE)
                -> "rainbow-lime"

            (Stats.SPECIAL_DEFENCE to Stats.SPECIAL_DEFENCE),
            (Stats.SPECIAL_DEFENCE to Stats.HP),
            (null to Stats.SPECIAL_DEFENCE),
            (Stats.DEFENCE to Stats.SPECIAL_ATTACK),
            (Stats.SPECIAL_ATTACK to Stats.DEFENCE)
                -> "rainbow-green"

            (Stats.SPECIAL_DEFENCE to Stats.SPECIAL_ATTACK),
            (Stats.SPECIAL_ATTACK to Stats.SPECIAL_DEFENCE) 
                -> "rainbow-cyan"

            (Stats.SPECIAL_ATTACK to Stats.SPECIAL_ATTACK),
            (Stats.SPECIAL_ATTACK to Stats.HP),
            (null to Stats.SPECIAL_ATTACK),
            (Stats.SPECIAL_DEFENCE to Stats.SPEED),
            (Stats.SPEED to Stats.SPECIAL_DEFENCE) 
                -> "rainbow-blue"

            (Stats.SPECIAL_ATTACK to Stats.SPEED),
            (Stats.SPEED to Stats.SPECIAL_ATTACK) 
                -> "rainbow-purple"

            (Stats.SPEED to Stats.SPEED),
            (Stats.SPEED to Stats.HP),
            (null to Stats.SPEED),
            (Stats.SPECIAL_ATTACK to Stats.ATTACK),
            (Stats.ATTACK to Stats.SPECIAL_ATTACK)
                -> "rainbow-magenta"

            (Stats.SPEED to Stats.ATTACK),
            (Stats.ATTACK to Stats.SPEED),
                -> "rainbow-pink"

            (null to Stats.HP) 
                -> "rainbow-light-blue"

            else -> null
        }
    }

    override fun provide(Pokemon pokemon): Set<String> {
        if (!pokemon.form.behaviour.characteristicRainbow) {
            return emptySet()
        }
        val char = pokemon.characteristic
        return setOf(calculateColourAspect(pokemon.nature, char) ?: return emptySet())
    }

    override fun provide(properties: PokemonProperties): Set<String> = emptySet()
}
