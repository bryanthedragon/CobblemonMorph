/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import kotlin.random.Random

/**
 * A simple [SpeciesFeature] that is a true/false flag value. It implements [CustomPokemonProperty] for use in
 * [PokemonProperties]. [FlagSpeciesFeatureProvider]s must be registered within [SpeciesFeatures].
 *
 * Implementations of this class don't need to implement anything.
 *
 * @author Hiroku
 * @since May 13th, 2022
 */
open class FlagSpeciesFeature(override val String name) : SynchronizedSpeciesFeature, CustomPokemonProperty {
    constructor(String name, Boolean enabled): this(name) {
        this.enabled = enabled
    }

    var enabled = false
    override fun saveToNBT(pokemonCompoundTag nbt): CompoundTag {
        pokemonNBT.putBoolean(name, enabled)
        return pokemonNBT
    }

    override fun loadFromNBT(pokemonCompoundTag nbt): SpeciesFeature {
        enabled = if (pokemonNBT.contains(name)) pokemonNBT.getBoolean(name) else enabled
        return this
    }

    override fun saveToJSON(JsonObject pokemonJSON): JsonObject {
        pokemonJSON.addProperty(name, enabled)
        return pokemonJSON
    }

    override fun loadFromJSON(JsonObject pokemonJSON): SpeciesFeature {
        val isEnabled = pokemonJSON.get(name)?.asBoolean
        enabled = isEnabled ?: this.enabled
        return this
    }

    override fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
        buffer.writeBoolean(enabled)
    }

    override fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
        enabled = buffer.readBoolean()
    }

    override fun asString() = "$name=$enabled"

    override fun apply(Pokemon pokemon) {
        val featureProvider = SpeciesFeatures.getFeature(name) ?: return
        if (featureProvider in SpeciesFeatures.getFeaturesFor(pokemon.species)) {
            val existingFeature = pokemon.getFeature<FlagSpeciesFeature>(name)
            if (existingFeature != null) {
                existingFeature.enabled = enabled
            } else {
                pokemon.features.add(FlagSpeciesFeature(name, enabled))
            }
            pokemon.updateAspects()
        }
    }

    override fun matches(Pokemon pokemon) = pokemon.getFeature<FlagSpeciesFeature>(name)?.enabled == enabled
}

public class FlagSpeciesFeatureProvider : SynchronizedSpeciesFeatureProvider<FlagSpeciesFeature>, CustomPokemonPropertyType<FlagSpeciesFeature>, AspectProvider {
    override var keys: List<String> = emptyList()
    // Uses get() = true because that way there's no backing field. It MUST be true, this way no JSON trickery will overwrite it
    override val needsKey get() = true
    var default: String? = null
    var isAspect = true
    override var visible: Boolean = false

    override fun invoke(RegistryFriendlyByteBuf buffer, String name): FlagSpeciesFeature? {
        return if (name in keys) {
            FlagSpeciesFeature(name).also { it.loadFromBuffer(buffer) }
        } else {
            null
        }
    }

    override fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
        buffer.writeCollection(keys) { _, value -> buffer.writeString(value) }
        buffer.writeNullable(default) { _, value -> buffer.writeString(value) }
        buffer.writeBoolean(isAspect)
    }

    override fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
        keys = buffer.readList { it.readString() }
        default = buffer.readNullable { it.readString() }
        isAspect = buffer.readBoolean()
    }

    override fun getRenderer(Pokemon pokemon): SummarySpeciesFeatureRenderer<FlagSpeciesFeature>? {
        return null
    }

    override fun examples() = setOf("true", "false")

    internal constructor() {
        this.keys = emptyList()
    }

    constructor(keys: List<String>) {
        this.keys = keys
    }

    constructor(keys: List<String>, default: Boolean) {
        this.keys = keys
        this.default = default.toString()
    }

    constructor(vararg keys: String) : this(keys.toList())

    override fun get(Pokemon pokemon) = pokemon.getFeature<FlagSpeciesFeature>(keys.first())

    override fun invoke(Pokemon pokemon): FlagSpeciesFeature? {
        return get(pokemon)
            ?: when (default) {
                "random" -> FlagSpeciesFeature(keys.first(), Random.Default.nextBoolean())
                in setOf("true", "false") -> FlagSpeciesFeature(keys.first(), default.toBoolean())
                else -> null
            }
    }

    override fun invoke(CompoundTag nbt): FlagSpeciesFeature? {
        return if (nbt.contains(keys.first())) {
            FlagSpeciesFeature(keys.first(), false).also { it.loadFromNBT(nbt) }
        } else null
    }

    override fun invoke(JsonObject json): FlagSpeciesFeature? {
        return if (json.has(keys.first())) {
            FlagSpeciesFeature(keys.first(), false).also { it.loadFromJSON(json) }
        } else null
    }

    override fun fromString(value: String?): FlagSpeciesFeature? {
        val isWeirdValue = value != null && value !in examples()

        if (isWeirdValue) {
            return null
        }

        return if (value == null) {
            FlagSpeciesFeature(keys.first(), true)
        } else {
            FlagSpeciesFeature(keys.first(), value.toBoolean())
        }
    }

    override fun provide(Pokemon pokemon): Set<String> {
        return if (isAspect && pokemon.getFeature<FlagSpeciesFeature>(keys.first())?.enabled == true) {
            setOf(keys.first())
        } else {
            emptySet()
        }
    }

    override fun provide(properties: PokemonProperties): Set<String> {
        return if (isAspect && properties.customProperties.filterIsInstance<FlagSpeciesFeature>().find { it.name == keys.first() }?.enabled == true) {
            setOf(keys.first())
        } else {
            emptySet()
        }
    }
}