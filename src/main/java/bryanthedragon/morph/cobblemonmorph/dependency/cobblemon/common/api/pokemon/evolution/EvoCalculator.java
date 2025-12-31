/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import java.util.Map;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

/**
 * Responsible for resolving EV yield after each battle.
 * For default implementation see [Generation8EvCalculator].
 *
 * @author Licious
 * @since October 31st, 2022
 */
public interface EvoCalculator {

    /**
     * TODO
     *
     * @param battlePokemon
     * @return
     */
    Map<Stat, Int> calculate(BattlePokemon battlePokemon) {
        val total = hashMapOf<Stat, Int>();
        battlePokemon.facedOpponents.filter { it.health == 0 }.forEach { opponent -> val results = this.calculate(battlePokemon, opponent); results.forEach { (stat, value) -> var newValue = total[stat] ?: 0; newValue += value; total[stat] = newValue; }}
        return total;
    }

    /**
     * TODO
     *
     * @param battlePokemon
     * @param opponentPokemon
     * @return
     */
    Map<Stat, Int> calculate(BattlePokemon battlePokemon, BattlePokemon opponentPokemon)

}
