/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

public class SpeciesUpdatePacket(pokemon: () -> Pokemon?, value: Species) : SingleUpdatePacket<Species, SpeciesUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(this.value.resourceIdentifier)
    }

    override fun set(Pokemon pokemon, value: Species) {
        pokemon.species = value
    }

    final class Companion {
        val ID = cobblemonResource("species_update")
        fun decode(RegistryFriendlyByteBuf buffer): SpeciesUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val species = PokemonSpecies.getByIdentifier(buffer.readIdentifier())!!
            return SpeciesUpdatePacket(pokemon, species)
        }
    }

}