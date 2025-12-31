/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import net.minecraft.network.RegistryFriendlyByteBuf

public class NatureUpdatePacket(pokemon: () -> Pokemon?, val nature: Nature?, val minted: Boolean) : PokemonUpdatePacket<NatureUpdatePacket>(pokemon) {

    override val id = ID

    override fun encodeDetails(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(nature) { _, v -> buffer.writeIdentifier(v.name) }
        buffer.writeBoolean(this.minted)
    }

    override fun applyToPokemon() {
        // Check for removing mint
        if (minted && nature == null) {
            pokemon()!!.mintedNature = null
            return
        } else {
            // Validate the nature locally
            if (nature == null) {
                LOGGER.warn("A null nature was attempted to be put onto: '$pokemon'")
                return
            }

            // Check which nature to modify
            if (!minted) {
                pokemon()!!.nature = nature
            } else {
                pokemon()!!.mintedNature = nature
            }
        }
    }

    final class Companion {
        val ID = cobblemonResource("nature_update")
        fun decode(RegistryFriendlyByteBuf buffer) = NatureUpdatePacket(decodePokemon(buffer), buffer.readNullable { Natures.getNature(buffer.readIdentifier()) }, buffer.readBoolean())
    }

}