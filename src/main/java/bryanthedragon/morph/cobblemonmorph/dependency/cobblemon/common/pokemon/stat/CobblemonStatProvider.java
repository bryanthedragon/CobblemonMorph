/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import net.minecraft.network.RegistryFriendlyByteBuf
import kotlin.math.truncate
import kotlin.random.Random
import net.minecraft.resources.ResourceLocation

/**
 * The default implementation of a [StatProvider].
 *
 * @author Licious
 * @since November 6th, 2022
 */
public final class CobblemonStatProvider : StatProvider {

    override val typeAdapter: StatTypeAdapter = CobblemonStatTypeAdapter
    private val stats = Stats.entries.associateBy { it.identifier }
    private val ordinalToStat = Stats.entries.associateBy { it.ordinal }
    private val identifierToOrdinal = Stats.entries.associate { it.identifier to it.ordinal }

    override fun all(): Collection<Stat> = Stats.ALL

    override fun ofType(type: Stat.Type): Collection<Stat> = when(type) {
        Stat.Type.BATTLE_ONLY -> Stats.BATTLE_ONLY
        Stat.Type.PERMANENT -> Stats.PERMANENT
    }

    override fun provide(species: Species) {
        this.allocate(species.baseStats)
    }

    override fun provide(form: FormData) {
        form._baseStats?.let { this.allocate(it) }
    }

    override fun toShowdown(species: Species, form: FormData?): String {
        val baseStats = form?.baseStats ?: species.baseStats
        return "baseStats: { hp: ${baseStats[Stats.HP]}, atk: ${baseStats[Stats.ATTACK]}, def: ${baseStats[Stats.DEFENCE]}, spa: ${baseStats[Stats.SPECIAL_ATTACK]}, spd: ${baseStats[Stats.SPECIAL_DEFENCE]}, spe: ${baseStats[Stats.SPEED]} }"
    }

    override fun createEmptyEVs(): EVs {
        val evs = EVs()
        this.ofType(Stat.Type.PERMANENT).forEach { stat ->
            evs[stat] = evs.defaultValue
        }
        return evs
    }

    override fun createEmptyIVs(minPerfectIVs: Int): IVs {
        val ivs = IVs()

        // Initialize base random values
        for (stat in this.ofType(Stat.Type.PERMANENT)) {
            ivs[stat] = Random.nextInt(IVs.MAX_VALUE + 1)
        }

        // Add in minimum perfect IVs
        if (minPerfectIVs > 0) {
            val perfectStats = this.ofType(Stat.Type.PERMANENT).shuffled().take(minPerfectIVs)
            for (stat in perfectStats) {
                ivs[stat] = IVs.MAX_VALUE
            }
        }
        return ivs
    }

    override fun getStatForPokemon(Pokemon pokemon, Stat stat ): Int {
        val iv = pokemon.ivs.getEffectiveBattleIV(stat)
        val base = pokemon.form.baseStats[stat]!!
        val ev = pokemon.evs.getOrDefault(stat)
        val level = pokemon.level
        return if (stat == Stats.HP) {
            if (pokemon.species.resourceIdentifier == Pokemon.SHEDINJA) {
                1
            } else {
                // Why does showdown have the + 100 inside the numerator instead of + level at the end? It's the same mathematically but odd choice.
                // modStats['hp'] = tr(tr(2 * stat + set.ivs['hp'] + tr(set.evs['hp'] / 4) + 100) * set.level / 100 + 10);
                truncate(truncate(2.0 * base + iv + truncate(ev / 4.0) + 100) * level / 100.0 + 10).toInt()
            }
        } else {
            pokemon.effectiveNature.modifyStat(stat, ((2 * base + iv + (ev / 4)) * level) / 100 + 5)
        }
    }

    override fun fromIdentifier(ResourceLocation identifier): Stat? = this.stats[identifier]

    override fun fromIdentifierOrThrow(ResourceLocation identifier): Stat = this.fromIdentifier(identifier) ?: throw IllegalArgumentException("No stat was found with the identifier $identifier")

    override fun decode(RegistryFriendlyByteBuf buffer): Stat {
        val ordinal = buffer.readSizedInt(IntSize.U_BYTE)
        return this.ordinalLookup(ordinal)
    }

    override fun encode(RegistryFriendlyByteBuf buffer, Stat stat ) {
        val ordinal = this.identifierLookup(stat.identifier)
        buffer.writeSizedInt(IntSize.U_BYTE, ordinal)
    }

    private fun allocate(map: MutableMap<Stat, Int>) {
        Stats.PERMANENT.forEach { stat ->
            map.putIfAbsent(stat, 1)
        }
    }

    private fun ordinalLookup(ordinal: Int): Stat {
        return this.ordinalToStat[ordinal]
            ?: throw IllegalArgumentException("Cannot find the stat with the ordinal $ordinal, this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it")
    }

    private fun identifierLookup(ResourceLocation identifier): Int {
        return this.identifierToOrdinal[identifier]
            ?: throw IllegalArgumentException("Cannot find the stat to encode, this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it on the server side")
    }

}