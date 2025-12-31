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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent

public class NicknameUpdatePacket(pokemon: () -> Pokemon?, value: MutableComponent?): SingleUpdatePacket<MutableComponent?, NicknameUpdatePacket>(pokemon, value) {
    override val id = ID

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(value) { _, v -> buffer.writeText(v) }
    }

    override fun set(Pokemon pokemon, value: MutableComponent?) { pokemon.nickname = value }

    final class Companion {
        val ID = cobblemonResource("nickname_update")
        fun decode(RegistryFriendlyByteBuf buffer): NicknameUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val nickname = buffer.readNullable { buffer.readText().copy() }
            return NicknameUpdatePacket(pokemon, nickname)
        }
    }

}