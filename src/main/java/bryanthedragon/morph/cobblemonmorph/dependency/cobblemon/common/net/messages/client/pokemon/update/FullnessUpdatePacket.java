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

public class FullnessUpdatePacket(pokemon: () -> Pokemon?, Int value) : IntUpdatePacket<FullnessUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun getSize() = IntSize.U_BYTE
    override fun set(Pokemon pokemon, Int value) {
        pokemon.currentFullness = value
    }

    final class Companion {
        val ID = cobblemonResource("fullness_update")
        fun decode(RegistryFriendlyByteBuf buffer) = FullnessUpdatePacket(decodePokemon(buffer), buffer.readSizedInt(IntSize.U_BYTE))
    }
}