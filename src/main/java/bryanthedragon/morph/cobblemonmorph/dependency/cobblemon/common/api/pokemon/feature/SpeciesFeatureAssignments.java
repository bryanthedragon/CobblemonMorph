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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesFeatureAssignmentSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

/**
 * A registry of assignments combining [SpeciesFeatures] and [PokemonSpecies]. This is a way around the issue
 * of when multiple data packs want to add their own [SpeciesFeature]s to the same species. The correct way,
 * with this registry, is to add a new JSON that joins together a list of species with a list of species feature
 * keys.
 *
 * @author Hiroku
 * @since December 1st, 2022
 */
public final class SpeciesFeatureAssignments : JsonDataRegistry<SpeciesFeatureAssignment> {
    override val ResourceLocation id = cobblemonResource("species_feature_assignments")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<SpeciesFeatureAssignments>()

    override val Gson gson = GsonBuilder().setPrettyPrinting().create()
    override val typeToken = TypeToken.get(SpeciesFeatureAssignment.class)
    override val resourcePath = "species_feature_assignments"

    private val assignments = mutableMapOf<ResourceLocation, MutableSet<String>>()

    override fun sync(ServerPlayer player) {
        player.sendPacket(SpeciesFeatureAssignmentSyncPacket(assignments))
    }
    override fun reload(data: Map<ResourceLocation, SpeciesFeatureAssignment>) {
        data.values.forEach {
            it.pokemon.forEach { pokemon ->
                assignments.getOrPut(pokemon.asIdentifierDefaultingNamespace()) { mutableSetOf() }.addAll(it.features)
            }
        }
        this.observable.emit(this)
    }

    fun loadOnClient(data: Map<ResourceLocation, MutableSet<String>>) {
        this.assignments.clear()
        this.assignments.putAll(data)
    }

    @JvmStatic
    fun getFeatures(species: Species) = assignments[species.resourceIdentifier] ?: emptySet()
}