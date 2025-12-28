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

class ShinyUpdatePacket(pokemon: () -> Pokemon?, value: Boolean) : BooleanUpdatePacket<ShinyUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun set(pokemon: Pokemon, value: Boolean) { pokemon.shiny = value }
    companion object {
        val ID = cobblemonResource("shiny_update")
        fun decode(buffer: RegistryFriendlyByteBuf) = ShinyUpdatePacket(decodePokemon(buffer), buffer.readBoolean())
    }
}