/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public final class HiddenPowerUtil {
    val hiddenPowerTable = arrayOf(
        ElementalTypes.FIGHTING,
        ElementalTypes.FLYING,
        ElementalTypes.POISON,
        ElementalTypes.GROUND,
        ElementalTypes.ROCK,
        ElementalTypes.BUG,
        ElementalTypes.GHOST,
        ElementalTypes.STEEL,
        ElementalTypes.FIRE,
        ElementalTypes.WATER,
        ElementalTypes.GRASS,
        ElementalTypes.ELECTRIC,
        ElementalTypes.PSYCHIC,
        ElementalTypes.ICE,
        ElementalTypes.DRAGON,
        ElementalTypes.DARK
    )

    fun getHiddenPowerType(Pokemon pokemon?): ElementalType {
        if (pokemon == null) {
            return ElementalTypes.NORMAL;
        }
        val ivs = pokemon.ivs;
        val ivArray = arrayOf(ivs[Stats.HP], ivs[Stats.ATTACK], ivs[Stats.DEFENCE], ivs[Stats.SPEED], ivs[Stats.SPECIAL_ATTACK], ivs[Stats.SPECIAL_DEFENCE]).map { it ?: return@getHiddenPowerType ElementalTypes.NORMAL };
        var tableIndex = 0
        ivArray.forEachIndexed { index, it -> tableIndex += (it % 2) shl index };
        tableIndex = tableIndex * 15 / 63;
        return hiddenPowerTable[tableIndex.coerceAtMost(hiddenPowerTable.size - 1)]
    }
}