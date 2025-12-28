/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Updates the state of a particular [SynchronizedSpeciesFeature].
 *
 * @author Hiroku
 * @since November 13th, 2023
 */
class SpeciesFeatureUpdatePacket(pokemon: () -> Pokemon?, val species: ResourceLocation, speciesFeature: SynchronizedSpeciesFeature) : SingleUpdatePacket<SynchronizedSpeciesFeature, SpeciesFeatureUpdatePacket>(pokemon, speciesFeature) {
    companion object {
        val ID = cobblemonResource("species_feature_update")
        fun decode(buffer: RegistryFriendlyByteBuf): SpeciesFeatureUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val speciesIdentifier = buffer.readIdentifier()
            val species = PokemonSpecies.getByIdentifier(speciesIdentifier)
                ?: throw IllegalStateException("Pokémon unable to be found during species feature update packet: $speciesIdentifier")
            val speciesFeatureName = buffer.readString()
            val featureProviders = SpeciesFeatures.getFeaturesFor(species).filterIsInstance<SynchronizedSpeciesFeatureProvider<*>>()
            val feature = featureProviders.firstNotNullOfOrNull { it(buffer, speciesFeatureName) } as? SynchronizedSpeciesFeature
                ?: throw IllegalArgumentException("Couldn't find a feature provider to deserialize this feature. Something's wrong.")
            return SpeciesFeatureUpdatePacket(pokemon, speciesIdentifier, feature)
        }
    }

    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        buffer.writeIdentifier(species)
        buffer.writeString(value.name)
        value.saveToBuffer(buffer, toClient = true)
    }

    override fun set(pokemon: Pokemon, value: SynchronizedSpeciesFeature) {
        pokemon.features.removeIf { it.name == value.name }
        pokemon.features.add(value)
    }
}