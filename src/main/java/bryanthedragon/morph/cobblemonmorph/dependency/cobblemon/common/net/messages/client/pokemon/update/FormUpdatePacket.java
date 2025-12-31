/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class FormUpdatePacket(pokemon: () -> Pokemon?, form: FormData) : SingleUpdatePacket<FormData, FormUpdatePacket>(pokemon, form) {
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        this.value.encode(buffer)
    }

    override fun set(Pokemon pokemon, value: FormData) {
        pokemon.form = value
    }

    override val ResourceLocation id = ID

    final class Companion {
        val ResourceLocation id = cobblemonResource("packets/form-update")
        fun decode(RegistryFriendlyByteBuf buffer): FormUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val form = FormData()
            form.species = pokemon()!!.species
            form.decode(buffer)
            return FormUpdatePacket(pokemon, form)
        }
    }
}