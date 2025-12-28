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

class AddEvolutionPacket(pokemon: () -> Pokemon?, value: EvolutionDisplay) : SingleUpdatePacket<EvolutionDisplay, AddEvolutionPacket>(pokemon, value) {

    override val id = ID

    constructor(pokemon: Pokemon, value: Evolution, registryAccess: RegistryAccess) : this({ pokemon }, value.convertToDisplay(pokemon, registryAccess))

    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        this.value.encode(buffer)
    }

    override fun set(pokemon: Pokemon, value: EvolutionDisplay) {
        pokemon.evolutionProxy.client().add(value)
    }

    companion object {

        val ID = cobblemonResource("add_evolution")

        fun decode(buffer: RegistryFriendlyByteBuf) = AddEvolutionPacket(decodePokemon(buffer), decodeDisplay(buffer))

        internal fun Evolution.convertToDisplay(pokemon: Pokemon, registryAccess: RegistryAccess): EvolutionDisplay {
            val result = pokemon.clone(registryAccess = registryAccess)
            this.result.apply(result)
            val expectedDisplay = CobblemonEvolutionDisplay(this.id, result)
            val event = EvolutionDisplayEvent(result, expectedDisplay, this)
            CobblemonEvents.EVOLUTION_DISPLAY.post(event)
            return event.display
        }

        internal fun EvolutionDisplay.encode(buffer: RegistryFriendlyByteBuf) {
            buffer.writeString(this.id)
            buffer.writeIdentifier(this.species.resourceIdentifier)
            buffer.writeCollection(this.aspects) { pb, value -> pb.writeString(value) }
        }

        internal fun decodeDisplay(buffer: RegistryFriendlyByteBuf): EvolutionDisplay {
            val id = buffer.readString()
            val speciesIdentifier = buffer.readIdentifier()
            val species = PokemonSpecies.getByIdentifier(speciesIdentifier)
                ?: throw IllegalArgumentException("Cannot resolve species from $speciesIdentifier")
            val aspects = buffer.readList(ByteBuf::readString).toSet()
            return CobblemonEvolutionDisplay(id, species, aspects)
        }
    }
}