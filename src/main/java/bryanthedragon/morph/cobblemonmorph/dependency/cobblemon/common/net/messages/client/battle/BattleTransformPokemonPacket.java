/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Updates the client about an [ActiveBattlePokemon] that has changed due to transformation during a battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleTransformPokemonHandler].
 *
 * @author Segfault Guy
 * @since March 26th, 2024
 */
public class BattleTransformPokemonPacket(val pnx: String, val updatedPokemon: BattleInitializePacket.ActiveBattlePokemonDTO, val isAlly: Boolean) : NetworkPacket<BattleTransformPokemonPacket> {

    override val id = ID

    // form changes
    constructor(pnx: String, updatedPokemon: BattlePokemon, isAlly: Boolean) :
        this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.fromPokemon(updatedPokemon, isAlly), isAlly)

    // transform
    constructor(pnx: String, updatedPokemon: BattlePokemon, mock: PokemonProperties, isAlly: Boolean) :
            this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.fromMock(updatedPokemon, isAlly, mock), isAlly)

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(pnx)
        updatedPokemon.saveToBuffer(buffer)
        buffer.writeBoolean(isAlly)
    }

    final class Companion {
        val ID = cobblemonResource("battle_transform_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleTransformPokemonPacket(buffer.readString(), BattleInitializePacket.ActiveBattlePokemonDTO.loadFromBuffer(buffer), buffer.readBoolean())
    }
}