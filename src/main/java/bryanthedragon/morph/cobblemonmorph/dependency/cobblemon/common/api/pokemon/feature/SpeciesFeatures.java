/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.StandardSpeciesFeatureSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpeciesFeatureProviderAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.phys.Vec3

/**
 * A registry for [SpeciesFeatureProvider]s. This is the backbone of species-specific data such as
 * variation information and counters used for some evolution types.
 *
 * Providers are loaded from JSON but can also be added programmatically. Species feature providers
 * that are registered via code will replace same-name features that were drawn from assets.
 *
 * If a species feature provider implements [CustomPokemonPropertyType] then it will be automatically
 * registered appropriately, as it will with aspects if it implements [AspectProvider].
 *
 * @author Hiroku
 * @since November 30th, 2022
 */
public final class SpeciesFeatures : JsonDataRegistry<SpeciesFeatureProvider<*>> {
    override val id = cobblemonResource("species_features")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<SpeciesFeatures>()

    val types = mutableMapOf<String, Class<out SpeciesFeatureProvider<*>>>()

    private val codeFeatures = mutableMapOf<String, SpeciesFeatureProvider<*>>()
    private val resourceFeatures = mutableMapOf<String, SpeciesFeatureProvider<*>>()
    override val Gson gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(SpeciesFeatureProvider.class, SpeciesFeatureProviderAdapter)
        .registerTypeAdapter(Vec3.class, Vec3dAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .create()
    override val typeToken: TypeToken<SpeciesFeatureProvider<*>> = TypeToken.get(SpeciesFeatureProvider.class)
    override val String resourcePath = "species_features"

    override fun sync(ServerPlayer player) {
        player.sendPacket(StandardSpeciesFeatureSyncPacket(codeFeatures + resourceFeatures))
    }

    override fun reload(data: Map<ResourceLocation, SpeciesFeatureProvider<*>>) {
        resourceFeatures.keys.toList().forEach(this::unregister)
        data.forEach(this::registerFromAssets)
    }

    @JvmStatic
    fun getCodeFeature(String name) = codeFeatures[name]
    @JvmStatic
    fun getResourceFeature(String name) = resourceFeatures[name]
    @JvmStatic
    fun getFeature(String name) = getCodeFeature(name) ?: getResourceFeature(name) ?: GlobalSpeciesFeatures.getFeature(name)
    fun loadOnClient(entries: Collection<Map.Entry<String, SpeciesFeatureProvider<*>>>) {
        codeFeatures.putAll(entries.map { it.toPair() })
    }

    @JvmStatic
    fun getFeatures() = (resourceFeatures.keys + codeFeatures.keys).mapNotNull(this::getFeature)
    @JvmStatic
    fun getFeaturesFor(species: Species): List<SpeciesFeatureProvider<*>> {
        val mentionedFeatures = species.features.mapNotNull(this::getFeature)
        val globalFeatures = GlobalSpeciesFeatures.getFeatures()
        val assignedFeatures = SpeciesFeatureAssignments.getFeatures(species).mapNotNull(this::getFeature)

        return (mentionedFeatures + globalFeatures + assignedFeatures).distinct()
    }

    private fun register(String name, provider: SpeciesFeatureProvider<*>, isCoded: Boolean) {
        val mapping = if (isCoded) codeFeatures else resourceFeatures
        if (provider is AspectProvider) {
            AspectProvider.register(provider)
        }
        if (provider is CustomPokemonPropertyType<*>) {
            CustomPokemonProperty.register(provider)
        }
        mapping[name] = provider
    }

    @JvmStatic
    fun register(String name, provider: SpeciesFeatureProvider<*>) = register(name, provider, isCoded = true)
    private fun registerFromAssets(ResourceLocation identifier, provider: SpeciesFeatureProvider<*>) = register(identifier.path, provider, isCoded = false)

    @JvmStatic
    fun unregister(String name) {
        var coded = true
        val value = getResourceFeature(name)?.also { coded = false } ?: getCodeFeature(name) ?: return
        if (value is AspectProvider) {
            AspectProvider.unregister(value)
        }
        if (value is CustomPokemonPropertyType<*>) {
            CustomPokemonProperty.unregister(value)
        }
        val mapping = if (coded) codeFeatures else resourceFeatures
        mapping.remove(name)
    }
}