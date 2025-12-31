/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityTypeChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType

/**
 * Represents a change operation for a [Pokemon.ability].
 * This is backed by the associated [PotentialAbility].
 *
 * @param T The type of [PotentialAbility].
 */
public interface AbilityChanger<T : PotentialAbility> {

    /**
     * The [PotentialAbilityType] of type [T].
     */
    val type: PotentialAbilityType<T>

    /**
     * Collects all the possible targets for the [type] and the given [pokemon].
     *
     * @param pokemon The [Pokemon] being queried.
     * @return A set containing all their legal abilities associated with [type], can be empty.
     */
    fun queryPossible(Pokemon pokemon): Set<Pair<AbilityTemplate, Priority>>

    /**
     * Attempts to update the ability of the given [pokemon].
     *
     * @param pokemon The [Pokemon] attempting to change an ability.
     * @return If the operation was successful.
     */
    fun performChange(Pokemon pokemon): Boolean

    /**
     * Checks if the current [PotentialAbilityType] of a [Pokemon.ability] is possible to swap from with this.
     *
     * @param type The [PotentialAbilityType] or null if the ability is forced and/or illegal.
     * @return If the operation can be performed.
     */
    fun canChangeFrom(type: PotentialAbilityType<*>?): Boolean

    final class Companion {

        /**
         * An implementation of [AbilityChanger] for [CommonAbility].
         *
         * While this can be used in custom data scenarios this functions most "game-like" if the Pokémon only has 2 common abilities.
         */
        @JvmStatic
        val COMMON_ABILITY: AbilityChanger<CommonAbility> = AbilityTypeChanger(CommonAbilityType) { other -> other == CommonAbilityType }

        /**
         * An implementation of [AbilityChanger] for [HiddenAbility].
         *
         * While this can be used in custom data scenarios this functions most "game-like" if the Pokémon only has 1 hidden ability.
         */
        @JvmStatic
        val HIDDEN_ABILITY: AbilityChanger<HiddenAbility> = AbilityTypeChanger(HiddenAbilityType) { other -> other == CommonAbilityType || other == HiddenAbilityType }

    }

}