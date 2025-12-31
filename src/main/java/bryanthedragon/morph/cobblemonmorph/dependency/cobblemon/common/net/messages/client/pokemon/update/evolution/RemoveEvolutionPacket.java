/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class RemoveEvolutionPacket(pokemon: () -> Pokemon?, EvolutionDisplay value) extends SingleUpdatePacket<EvolutionDisplay, RemoveEvolutionPacket>(pokemon, value) {

    private ID id;

    constructor(Pokemon pokemon, value: Evolution, RegistryAccess registryAccess) : this({ pokemon }, value.convertToDisplay(pokemon, registryAccess))

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        this.value.encode(buffer)
    }

    override fun set(Pokemon pokemon, EvolutionDisplay value) {
        pokemon.evolutionProxy.client().remove(value)
    }

    public final class Companion {
        val ID = cobblemonResource("remove_evolution")

        fun decode(RegistryFriendlyByteBuf buffer) = RemoveEvolutionPacket(decodePokemon(buffer), decodeDisplay(buffer))

    }

}