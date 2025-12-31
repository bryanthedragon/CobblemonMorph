/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf


/**
 * Informs the client about a switch occurring in the battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleSwitchPokemonHandler].
 *
 * @author Hiroku
 * @since June 6th, 2022
 */
public class BattleSwitchPokemonPacket(val pnx: String, val newPokemon: BattleInitializePacket.ActiveBattlePokemonDTO, val isAlly: Boolean) : NetworkPacket<BattleSwitchPokemonPacket> {

    override val id = ID

    constructor(pnx: String, newPokemon: BattlePokemon, isAlly: Boolean, illusion: BattlePokemon?) :
        this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.fromPokemon(newPokemon, isAlly, illusion), isAlly)

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(pnx)
        newPokemon.saveToBuffer(buffer)
        buffer.writeBoolean(isAlly)
    }

    final class Companion {
        val ID = cobblemonResource("battle_switch_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleSwitchPokemonPacket(buffer.readString(), BattleInitializePacket.ActiveBattlePokemonDTO.loadFromBuffer(buffer), buffer.readBoolean())
    }
}