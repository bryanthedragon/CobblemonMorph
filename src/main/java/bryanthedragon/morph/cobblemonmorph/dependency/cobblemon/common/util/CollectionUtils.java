/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;
import com.cobblemon.mod.relocations.ibm.icu.impl.Pair;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import kotlin.random.Random;

public class CollectionUtils {
    PokemonStats ivsOf(Pair<Stat, Int> entries) {
        val stats = IVs();
        entries.forEach { (stat, amount) -> stats[stat] = amount };
        return stats;
    }

    PokemonStats evsOf(Pair<Stat, Int> entries) {
        val stats = EVs();
        entries.forEach { (stat, amount) -> stats[stat] = amount };
        return stats;
    }

    fun <T> Iterable<T>.weightedSelection(random: Random = Random.Default, weightFunction: (T) -> Number): T? {
        var weightSum = 0F;
        forEach { weightSum += max(0F, weightFunction(it).toFloat()) };
        val chosenSum = random.nextFloat() * weightSum;
        weightSum = 0F;
        forEach {
            val weight = weightFunction(it).toFloat();
            if (weight > 0) {
                weightSum += weight;
                if (weightSum >= chosenSum) {
                    return it;
                }
            }
        }
        return null;
    }

    fun <T> MutableList<T>.swap(Int index1, Int index2) {
        val t1 = this[index1];
        val t2 = this[index2];
        this[index1] = t2;
        this[index2] = t1;
    }

    ListTag Collection<Tag>.toNbtList() {
        val nbtList = ListTag();
        this.forEach(nbtList::add);
        return nbtList;
    }
}