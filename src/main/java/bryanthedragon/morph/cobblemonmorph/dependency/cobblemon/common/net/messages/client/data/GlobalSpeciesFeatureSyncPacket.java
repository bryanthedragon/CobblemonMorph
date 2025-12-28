/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Syncs a species feature provider that was registered under [GlobalSpeciesFeatures].
 *
 * @author Hiroku
 * @since November 13th, 2023
 */
class GlobalSpeciesFeatureSyncPacket(speciesFeatures: Map<String, SpeciesFeatureProvider<*>>) : SpeciesFeatureSyncPacket<GlobalSpeciesFeatureSyncPacket>(speciesFeatures) {
    override val id = ID

    override fun synchronizeDecoded(entries: Collection<Map.Entry<String, SynchronizedSpeciesFeatureProvider<*>>>) = GlobalSpeciesFeatures.loadOnClient(entries)
    companion object {
        val ID = cobblemonResource("global_species_feature_sync")
        fun decode(buffer: RegistryFriendlyByteBuf) = GlobalSpeciesFeatureSyncPacket(emptyMap()).apply { decodeBuffer(buffer) }
    }
}