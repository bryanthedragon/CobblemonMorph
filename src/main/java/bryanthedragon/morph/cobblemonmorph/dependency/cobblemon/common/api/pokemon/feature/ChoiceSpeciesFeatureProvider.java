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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.substitute
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * A [SpeciesFeatureProvider] which is a string value selected from a fixed list of choices. Parameters exist
 * to change default behaviour, aspects, and the available choices. Choices must be lowercase.
 *
 * @author Hiroku
 * @since November 30th, 2022
 */
open class ChoiceSpeciesFeatureProvider(
    override var keys: List<String>,
    var default: String? = null,
    var choices: List<String> = listOf(),
    var isAspect: Boolean = true,
    var aspectFormat: String = "{{choice}}"
) : SynchronizedSpeciesFeatureProvider<StringSpeciesFeature>, CustomPokemonPropertyType<StringSpeciesFeature>, AspectProvider {
    override var needsKey = true
    override var visible = false
    fun getAspect(feature: StringSpeciesFeature) = aspectFormat.substitute("choice", feature.value)

    override fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
        buffer.writeCollection(keys) { _, value -> buffer.writeString(value) }
        buffer.writeNullable(default) { _, value -> buffer.writeString(value) }
        buffer.writeCollection(choices) { _, value -> buffer.writeString(value) }
        buffer.writeBoolean(isAspect)
        buffer.writeString(aspectFormat)
        buffer.writeBoolean(needsKey)
    }

    override fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
        keys = buffer.readList { buffer.readString() }
        default = buffer.readNullable { buffer.readString() }
        choices = buffer.readList { buffer.readString() }
        isAspect = buffer.readBoolean()
        aspectFormat = buffer.readString()
        needsKey = buffer.readBoolean()
    }

    override fun getRenderer(Pokemon pokemon): SummarySpeciesFeatureRenderer<StringSpeciesFeature>? {
        return null
    }

    override fun invoke(RegistryFriendlyByteBuf buffer, String name): StringSpeciesFeature? {
        return if (name in keys) {
            StringSpeciesFeature(name, "").also { it.loadFromBuffer(buffer) }
        } else {
            null
        }
    }

    fun getAllAspects(): MutableList<String> {
        val aspects = choices.toMutableList()
        choices.forEach {
            aspects[choices.indexOf(it)] = (aspectFormat.substitute("choice", it))
        }
        return aspects
    }

    override fun examples() = choices

    internal constructor(): this(emptyList())

    override fun get(Pokemon pokemon) = pokemon.getFeature<StringSpeciesFeature>(keys.first())

    override fun invoke(Pokemon pokemon): StringSpeciesFeature? {
        val existing = get(pokemon)
        return if (existing != null && existing.value in choices) {
            existing
        } else {
            val value = if (default in choices) {
                default!!
            } else if (default == "random") {
                // If it's mandatory, but they provided no value and no default, give it a random value.
                choices.randomOrNull() ?: throw IllegalStateException("The 'choices' list is empty for species feature provider: ${keys.joinToString()}")
            } else {
                return null
            }

            fromString(value)
        }
    }

    override fun invoke(CompoundTag nbt): StringSpeciesFeature? {
        return if (nbt.contains(keys.first())) {
            StringSpeciesFeature(keys.first(), "").also { it.loadFromNBT(nbt) }
        } else null
    }

    override fun invoke(JsonObject json): StringSpeciesFeature? {
        return if (json.has(keys.first())) {
            StringSpeciesFeature(keys.first(), "").also { it.loadFromJSON(json) }
        } else null
    }

    override fun fromString(value: String?): StringSpeciesFeature? {
        val lower = value?.lowercase()
        if (lower == null || lower !in choices) {
            return null
        }

        return StringSpeciesFeature(keys.first(), lower)
    }

    override fun provide(Pokemon pokemon): Set<String> {
        return if (isAspect) {
            get(pokemon)?.let { setOf(getAspect(it)) } ?: emptySet()
        } else {
            emptySet()
        }
    }

    override fun provide(properties: PokemonProperties): Set<String> {
        return if (isAspect) {
            val feature = properties.customProperties.filterIsInstance<StringSpeciesFeature>().find { it.name == keys.first() }
            if (feature != null) {
                setOf(getAspect(feature))
            } else {
                emptySet()
            }
        } else {
            emptySet()
        }
    }
}