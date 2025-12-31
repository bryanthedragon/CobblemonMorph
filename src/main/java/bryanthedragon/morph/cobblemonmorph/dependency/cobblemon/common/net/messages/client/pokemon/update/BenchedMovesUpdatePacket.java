/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class BenchedMovesUpdatePacket(pokemon: () -> Pokemon?, value: BenchedMoves): SingleUpdatePacket<BenchedMoves, BenchedMovesUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        this.value.saveToBuffer(buffer)
    }

    override fun set(Pokemon pokemon, value: BenchedMoves) {
        pokemon.benchedMoves.copyFrom(value)
    }

    final class Companion {
        val ID = cobblemonResource("benched_moves_update")
        fun decode(RegistryFriendlyByteBuf buffer): BenchedMovesUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val benchedMoves = BenchedMoves().apply { loadFromBuffer(buffer) }
            return BenchedMovesUpdatePacket(pokemon, benchedMoves)
        }
    }
}