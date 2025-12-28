/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Updates the client about an [ActiveBattlePokemon] that was hidden by an illusion and has just been revealed during a battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleReplacePokemonHandler].
 *
 * @param realPokemon The
 * @author Segfault Guy
 * @since March 30th, 2024
 */
class BattleReplacePokemonPacket(val pnx: String, val realPokemon: BattleInitializePacket.ActiveBattlePokemonDTO, val isAlly: Boolean) : NetworkPacket<BattleReplacePokemonPacket> {

    override val id = ID

    constructor(pnx: String, realPokemon: BattlePokemon, isAlly: Boolean) :
        this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.fromPokemon(realPokemon, isAlly), isAlly)

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeString(pnx)
        realPokemon.saveToBuffer(buffer)
        buffer.writeBoolean(isAlly)
    }

    companion object {
        val ID = cobblemonResource("battle_replace_pokemon")
        fun decode(buffer: RegistryFriendlyByteBuf) = BattleReplacePokemonPacket(buffer.readString(), BattleInitializePacket.ActiveBattlePokemonDTO.loadFromBuffer(buffer), buffer.readBoolean())
    }
}