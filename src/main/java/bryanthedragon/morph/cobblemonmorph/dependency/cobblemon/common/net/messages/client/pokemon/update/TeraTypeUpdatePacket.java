/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Updates the Tera Type of the Pokémon.
 *
 * @author Segfault Guy
 * @since July 19, 2023
 */
public class TeraTypeUpdatePacket(pokemon: () -> Pokemon?, value: TeraType) : SingleUpdatePacket<TeraType, TeraTypeUpdatePacket>(pokemon, value) {
    override val id = ID

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(value.id)
    }

    override fun set(Pokemon pokemon, value: TeraType) {
        pokemon.teraType = value
    }

    final class Companion {
        val ID = cobblemonResource("tera_type_update")
        fun decode(RegistryFriendlyByteBuf buffer) = TeraTypeUpdatePacket(decodePokemon(buffer), TeraTypes.get(buffer.readIdentifier())!!)
    }
}