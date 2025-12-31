/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf


/**
 * Informs the client about a position swap occurring in the battle.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleSwapPokemonHandler].
 *
 * @author JazzMcNade
 * @since  March 5th, 2024
 */
public class BattleSwapPokemonPacket(val pnx: String) : NetworkPacket<BattleSwapPokemonPacket> {

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(pnx)
    }

    final class Companion {
        val ID = cobblemonResource("battle_swap_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleSwapPokemonPacket(buffer.readString())
    }
}