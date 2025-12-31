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
 * Gives the client the true details of their team in the battle. This is so that switch choices can be made with
 * full details.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleSetTeamPokemonHandler].
 *
 * @author Hiroku
 * @since June 6th, 2022
 */
public class BattleSetTeamPokemonPacket(val team: List<Pokemon>) : NetworkPacket<BattleSetTeamPokemonPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeCollection(this.team) { _, pokemon -> Pokemon.S2C_CODEC.encode(buffer, pokemon) }
    }
    final class Companion {
        val ID = cobblemonResource("battle_set_team")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleSetTeamPokemonPacket(buffer.readList { _ -> Pokemon.S2C_CODEC.decode(buffer) })
    }
}