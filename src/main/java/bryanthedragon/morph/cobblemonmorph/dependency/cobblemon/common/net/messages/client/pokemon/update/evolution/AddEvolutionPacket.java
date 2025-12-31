/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionDisplayEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.CobblemonEvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import io.netty.buffer.ByteBuf
import net.minecraft.core.RegistryAccess
import net.minecraft.network.RegistryFriendlyByteBuf

public class AddEvolutionPacket(pokemon: () -> Pokemon?, EvolutionDisplay value) : SingleUpdatePacket<EvolutionDisplay, AddEvolutionPacket>(pokemon, value) {

    override val id = ID

    constructor(Pokemon pokemon, value: Evolution, RegistryAccess registryAccess) : this({ pokemon }, value.convertToDisplay(pokemon, registryAccess))

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        this.value.encode(buffer)
    }

    override fun set(Pokemon pokemon, EvolutionDisplay value) {
        pokemon.evolutionProxy.client().add(value)
    }

    final class Companion {

        val ID = cobblemonResource("add_evolution")

        fun decode(RegistryFriendlyByteBuf buffer) = AddEvolutionPacket(decodePokemon(buffer), decodeDisplay(buffer))

        internal fun Evolution.convertToDisplay(Pokemon pokemon, RegistryAccess registryAccess): EvolutionDisplay {
            val result = pokemon.clone(registryAccess = registryAccess)
            this.result.apply(result)
            val expectedDisplay = CobblemonEvolutionDisplay(this.id, result)
            val event = EvolutionDisplayEvent(result, expectedDisplay, this)
            CobblemonEvents.EVOLUTION_DISPLAY.post(event)
            return event.display
        }

        internal fun EvolutionDisplay.encode(RegistryFriendlyByteBuf buffer) {
            buffer.writeString(this.id)
            buffer.writeIdentifier(this.species.resourceIdentifier)
            buffer.writeCollection(this.aspects) { pb, value -> pb.writeString(value) }
        }

        internal fun decodeDisplay(RegistryFriendlyByteBuf buffer): EvolutionDisplay {
            val id = buffer.readString()
            val speciesIdentifier = buffer.readIdentifier()
            val species = PokemonSpecies.getByIdentifier(speciesIdentifier)
                ?: throw IllegalArgumentException("Cannot resolve species from $speciesIdentifier")
            val aspects = buffer.readList(ByteBuf::readString).toSet()
            return CobblemonEvolutionDisplay(id, species, aspects)
        }
    }
}