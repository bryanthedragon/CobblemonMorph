/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType
import com.google.gson.JsonElement
public final class HiddenAbilityType : PotentialAbilityType<HiddenAbility> {
    override fun parseFromJSON(JsonElement jElement): HiddenAbility? {
        val str = if (element.isJsonPrimitive) element.asString else null
        return if (str?.startsWith("h:") == true) {
            val abilityString = str.substringAfter("h:")
            val ability = Abilities.get(abilityString)
            if (ability != null) {
                HiddenAbility(ability)
            } else {
                Cobblemon.LOGGER.error("Hidden ability referred to unknown ability: $abilityString")
                null
            }
        } else {
            null
        }
    }
}

/**
 * Crappy Pokémon feature
 *
 * @author Hiroku
 * @since July 28th, 2022
 */
public class HiddenAbility(override val template: AbilityTemplate) : PotentialAbility {
    override val Priority priority = Priority.LOW
    override val type = HiddenAbilityType
    override fun isSatisfiedBy(aspects: Set<String>) = false // TODO actually implement hidden abilities ig? Chance in config or aspect check?
}