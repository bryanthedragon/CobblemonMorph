/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the client to give a player a total update of one of their battle
 * Pokémon's data. Unlike other update packets this gives complete and private data,
 * unaffected by the 'fog of war' in battles (knowing all the moves, for example).
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleUpdateTeamPokemonHandler].
 *
 * @author Hiroku
 * @since August 27th, 2022
 */
class BattleUpdateTeamPokemonPacket(val pokemon: Pokemon) : NetworkPacket<BattleUpdateTeamPokemonPacket>, UnsplittablePacket {
    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        Pokemon.S2C_CODEC.encode(buffer, this.pokemon)
    }
    companion object {
        val ID = cobblemonResource("battle_update_team")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleUpdateTeamPokemonPacket(Pokemon.S2C_CODEC.decode(buffer))
    }
}