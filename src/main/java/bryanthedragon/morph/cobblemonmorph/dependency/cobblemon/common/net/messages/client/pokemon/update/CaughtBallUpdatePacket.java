/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

class CaughtBallUpdatePacket(pokemon: () -> Pokemon?, value: PokeBall): SingleUpdatePacket<PokeBall, CaughtBallUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        buffer.writeIdentifier(this.value.name)
    }

    override fun set(pokemon: Pokemon, value: PokeBall) {
        pokemon.caughtBall = value
    }

    companion object {
        val ID = cobblemonResource("caught_ball_update")
        fun decode(buffer: RegistryFriendlyByteBuf): CaughtBallUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val pokeBall = PokeBalls.getPokeBall(buffer.readIdentifier()) ?: PokeBalls.POKE_BALL
            return CaughtBallUpdatePacket(pokemon, pokeBall)
        }
    }
}