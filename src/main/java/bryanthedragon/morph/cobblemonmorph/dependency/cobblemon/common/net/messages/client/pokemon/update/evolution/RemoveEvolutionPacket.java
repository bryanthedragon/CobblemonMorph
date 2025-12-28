/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket.Companion.convertToDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket.Companion.decodeDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket.Companion.encode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.core.RegistryAccess
import net.minecraft.network.RegistryFriendlyByteBuf

class RemoveEvolutionPacket(pokemon: () -> Pokemon?, value: EvolutionDisplay) : SingleUpdatePacket<EvolutionDisplay, RemoveEvolutionPacket>(pokemon, value) {

    override val id = ID

    constructor(pokemon: Pokemon, value: Evolution, registryAccess: RegistryAccess) : this({ pokemon }, value.convertToDisplay(pokemon, registryAccess))

    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        this.value.encode(buffer)
    }

    override fun set(pokemon: Pokemon, value: EvolutionDisplay) {
        pokemon.evolutionProxy.client().remove(value)
    }

    companion object {
        val ID = cobblemonResource("remove_evolution")

        fun decode(buffer: RegistryFriendlyByteBuf) = RemoveEvolutionPacket(decodePokemon(buffer), decodeDisplay(buffer))

    }

}