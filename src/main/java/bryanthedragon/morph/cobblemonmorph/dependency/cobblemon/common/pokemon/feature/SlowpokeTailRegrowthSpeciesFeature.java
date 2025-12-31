/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.TickingSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.provider.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.jitterDropItem
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel

public class SlowpokeTailRegrowthSpeciesFeature(var regrowthSeconds: Int = 0) : SpeciesFeature, CustomPokemonProperty, TickingSpeciesFeature {
    override val String name = NAME

    override fun saveToNBT(pokemonCompoundTag nbt): CompoundTag {
        if (regrowthSeconds > 0) {
            pokemonNBT.putInt(DataKeys.TAIL_REGROWTH_SECONDS, regrowthSeconds)
        }
        return pokemonNBT
    }

    override fun loadFromNBT(pokemonCompoundTag nbt): SpeciesFeature {
        regrowthSeconds = pokemonNBT.getInt(DataKeys.TAIL_REGROWTH_SECONDS)
        return this
    }

    override fun saveToJSON(JsonObject pokemonJSON): JsonObject {
        if (regrowthSeconds > 0) {
            pokemonJSON.addProperty(DataKeys.TAIL_REGROWTH_SECONDS, regrowthSeconds)
        }
        return pokemonJSON
    }

    override fun loadFromJSON(JsonObject pokemonJSON): SpeciesFeature {
        if (pokemonJSON.has(DataKeys.TAIL_REGROWTH_SECONDS)) {
            regrowthSeconds = pokemonJSON.get(DataKeys.TAIL_REGROWTH_SECONDS).asInt
        }
        return this
    }

    override fun asString(): String {
        return "$name=$regrowthSeconds"
    }

    override fun apply(Pokemon pokemon) {
        pokemon.features.removeIf { it.name == NAME }
        pokemon.features.add(this)
        pokemon.updateAspects()
    }

    override fun matches(Pokemon pokemon): Boolean {
        return (pokemon.getFeature(NAME) as? SlowpokeTailRegrowthSpeciesFeature)?.regrowthSeconds == regrowthSeconds
    }

    fun onShear(pokemonEntity: PokemonEntity) {
        this.regrowthSeconds = CobblemonMechanics.slowpokeTails.regrowthSeconds
        val itemEntity = pokemonEntity.spawnAtLocation(CobblemonItems.TASTY_TAIL) ?: return
        pokemonEntity.pokemon.updateAspects()
        pokemonEntity.pokemon.markFeatureDirty(this)
        pokemonEntity.jitterDropItem(itemEntity)
    }

    override fun onSecondPassed(
        ServerLevel world,
        Pokemon pokemon,
        entity: PokemonEntity?
    ) {
        if (regrowthSeconds <= 0) return
        if (CobblemonMechanics.slowpokeTails.onlyRegrowWhenSentOut && entity == null) return
        // if they're sent out and parameter entity is null, the party ticker is running this - leave it for the entity delegate ticker
        if (entity == null && pokemon.entity != null) return

        regrowthSeconds--
        pokemon.updateAspects()
        pokemon.markFeatureDirty(this)
    }

    final class Companion {
        const val NAME = "slowpoke_tail_regrowth"
    }
}
public final class SlowpokeTailRegrowthSpeciesFeatureProvider: SpeciesFeatureProvider<SlowpokeTailRegrowthSpeciesFeature>, CustomPokemonPropertyType<SlowpokeTailRegrowthSpeciesFeature>, AspectProvider {
    override val keys: Iterable<String> = setOf(SlowpokeTailRegrowthSpeciesFeature.NAME)
    override val needsKey: Boolean = true

    fun getFromPokemon(Pokemon pokemon): SlowpokeTailRegrowthSpeciesFeature? {
        if (!CobblemonMechanics.slowpokeTails.canShearSlowpoke) {
            return null
        }
        return pokemon.features.find { it.name == SlowpokeTailRegrowthSpeciesFeature.NAME }
            ?.let { return it as SlowpokeTailRegrowthSpeciesFeature }
    }

    override fun invoke(Pokemon pokemon): SlowpokeTailRegrowthSpeciesFeature {
        return getFromPokemon(pokemon)
            ?: SlowpokeTailRegrowthSpeciesFeature()
    }

    override fun invoke(CompoundTag nbt): SlowpokeTailRegrowthSpeciesFeature? {
        return if (nbt.contains(DataKeys.TAIL_REGROWTH_SECONDS)) {
            SlowpokeTailRegrowthSpeciesFeature().also { it.loadFromNBT(nbt) }
        } else null
    }

    override fun invoke(JsonObject json): SlowpokeTailRegrowthSpeciesFeature? {
        return if (json.has(DataKeys.TAIL_REGROWTH_SECONDS)) {
            SlowpokeTailRegrowthSpeciesFeature().also { it.loadFromJSON(json) }
        } else null
    }

    override fun fromString(value: String?): SlowpokeTailRegrowthSpeciesFeature? {
        val mechanic = CobblemonMechanics.slowpokeTails
        return if (value == null) {
            SlowpokeTailRegrowthSpeciesFeature(regrowthSeconds = mechanic.regrowthSeconds)
        } else if (value.isInt()) {
            SlowpokeTailRegrowthSpeciesFeature(regrowthSeconds = value.toInt())
        } else {
            null
        }
    }

    override fun examples() = listOf(CobblemonMechanics.slowpokeTails.regrowthSeconds.toString())

    override fun provide(Pokemon pokemon): Set<String> {
        val mechanic = CobblemonMechanics.slowpokeTails
        val regrowthSeconds = pokemon.getFeature<SlowpokeTailRegrowthSpeciesFeature>(SlowpokeTailRegrowthSpeciesFeature.NAME)?.regrowthSeconds ?: return emptySet()
        return mechanic.getAspects(regrowthSeconds)
    }

    override fun provide(properties: PokemonProperties): Set<String> {
        val mechanic = CobblemonMechanics.slowpokeTails
        val regrowthSeconds = properties.customProperties.filterIsInstance<SlowpokeTailRegrowthSpeciesFeature>().firstOrNull()?.regrowthSeconds ?: return emptySet()
        return mechanic.getAspects(regrowthSeconds)
    }
}