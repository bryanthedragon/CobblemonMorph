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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class OriginalTrainerUpdatePacket(pokemon: () -> Pokemon?, userString name?) : SingleUpdatePacket<String?, OriginalTrainerUpdatePacket>(pokemon, username) {
    override val id = ID

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(this.value) { _, v -> buffer.writeString(v) }
    }

    override fun set(Pokemon pokemon, value: String?) {
        pokemon.originalTrainerName = value
    }

    final class Companion {
        val ID = cobblemonResource("original_trainer_update")
        fun decode(RegistryFriendlyByteBuf buffer): OriginalTrainerUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val originalTrainer = buffer.readNullable { buffer.readString() }
            return OriginalTrainerUpdatePacket(pokemon, originalTrainer)
        }
    }
}