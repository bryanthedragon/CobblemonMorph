/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Updates the current health of the Pokémon
 *
 * @author Hiroku
 * @since February 12, 2022
 */
public class HealthUpdatePacket(pokemon: () -> Pokemon?, Int value) : IntUpdatePacket<HealthUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun getSize() = IntSize.U_SHORT
    override fun set(Pokemon pokemon, Int value) {
        pokemon.currentHealth = value
    }
    final class Companion {
        val ID = cobblemonResource("health_update")
        fun decode(RegistryFriendlyByteBuf buffer) = HealthUpdatePacket(decodePokemon(buffer), buffer.readSizedInt(IntSize.U_SHORT))
    }
}