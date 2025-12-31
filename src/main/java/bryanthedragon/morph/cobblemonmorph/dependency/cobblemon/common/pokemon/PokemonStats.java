/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;



import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo.Map;

import com.bedrockk.molang.runtime.value.DoubleValue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt;

import kotlin.ranges.IntRange;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Holds a mapping from a Stat to value that should be reducible to a short for NBT and net.
 */
public abstract class PokemonStats extends Iterable<Map.Entry<Stat, Int>> {
    abstract val IntRange acceptableRange;
    abstract val Int defaultValue;
    override fun iterator() = stats.entries.iterator()

    val struct = ObjectValue(this).also {
        for (stat in Stats.PERMANENT) {
            it.addFunction(stat.showdownId) { DoubleValue(this.getOrDefault(stat)) }
        }
    }

    /** Run whenever anything changes. */
    var changeFunction: (PokemonStats) -> Unit = {}

    protected val stats = mutableMapOf<Stat, Int>()
    private var emit = true

    fun doWithoutEmitting(action: () -> Unit) {
        emit = false
        action()
        emit = true
    }

    fun doThenEmit(action: () -> Unit) {
        doWithoutEmitting(action)
        update()
    }

    fun update() {
        if (emit) {
            changeFunction(this)
        }
    }

    operator fun get(key: Stat) = stats[key]
    open operator fun set(key: Stat, Int value) {
        if (this.canSet(key, value)) {
            stats[key] = value
            update()
        }
    }

    protected open fun canSet(Stat stat , Int value) = value in acceptableRange

    fun getOrDefault(Stat stat ) = this[stat] ?: this.defaultValue

    fun total(): Int = stats.values.sum()
}