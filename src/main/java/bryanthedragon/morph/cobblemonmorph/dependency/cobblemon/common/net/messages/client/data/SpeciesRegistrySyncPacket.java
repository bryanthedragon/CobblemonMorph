/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

// We do not need to know every single attribute as a client, as such, we only sync the aspects that matter
public class SpeciesRegistrySyncPacket(species: Collection<Species>) : DataRegistrySyncPacket<Species, SpeciesRegistrySyncPacket>(species) {

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Species) {
        try {
            buffer.writeIdentifier(entry.resourceIdentifier)
            entry.encode(buffer)
        } catch (Exception e) {
            Cobblemon.LOGGER.error("Caught exception encoding the species {}", entry.resourceIdentifier, e)
        }
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Species? {
        val identifier = buffer.readIdentifier()
        val species = Species()
        species.resourceIdentifier = identifier
        return try {
            species.decode(buffer)
            species
        } catch (Exception e) {
            Cobblemon.LOGGER.error("Caught exception decoding the species {}", identifier, e)
            null
        }
    }

    override fun synchronizeDecoded(entries: Collection<Species>) {
        PokemonSpecies.reload(entries.associateBy { it.resourceIdentifier })
    }

    final class Companion {
        val ID = cobblemonResource("species_sync")
        fun decode(RegistryFriendlyByteBuf buffer): SpeciesRegistrySyncPacket = SpeciesRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}