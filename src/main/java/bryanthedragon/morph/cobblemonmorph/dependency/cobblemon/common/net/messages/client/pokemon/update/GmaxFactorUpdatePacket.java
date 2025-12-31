/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Updates whether the Pokémon has the Gigantamax factor.
 *
 * @author Segfault Guy
 * @since July 27, 2023
 */
public class GmaxFactorUpdatePacket(pokemon: () -> Pokemon?, value: Boolean) : BooleanUpdatePacket<GmaxFactorUpdatePacket>(pokemon, value) {
    override val id = ID

    override fun set(Pokemon pokemon, value: Boolean) {
        pokemon.gmaxFactor = value
    }

    final class Companion {
        val ID = cobblemonResource("gmax_factor_update")
        fun decode(RegistryFriendlyByteBuf buffer) = GmaxFactorUpdatePacket(decodePokemon(buffer), buffer.readBoolean())
    }
}