/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf

class GenderUpdatePacket(pokemon: () -> Pokemon?, value: Gender): SingleUpdatePacket<Gender, GenderUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        buffer.writeEnumConstant(this.value)
    }

    override fun set(pokemon: Pokemon, value: Gender) {
        pokemon.gender = value
    }

    companion object {
        val ID = cobblemonResource("gender_update")
        fun decode(buffer: RegistryFriendlyByteBuf): GenderUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val gender = buffer.readEnumConstant(Gender::class.java)
            return GenderUpdatePacket(pokemon, gender)
        }
    }
}