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
 * Informs the client that a Pokémon's health has changed. Executes a tile animation.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleHealthChangeHandler].
 *
 * @author Hiroku
 * @since June 5th, 2022
 */
public class BattleHealthChangePacket(val pnx: String, val newHealth: Float, val newMaxHealth: Float? = null) : NetworkPacket<BattleHealthChangePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(pnx)
        buffer.writeFloat(newHealth)
        buffer.writeNullable(newMaxHealth) { _, newMaxHealth -> buffer.writeFloat(newMaxHealth) }
    }

    final class Companion {
        val ID = cobblemonResource("battle_health_change")
        fun decode(RegistryFriendlyByteBuf buffer) = BattleHealthChangePacket(buffer.readString(), buffer.readFloat(), buffer.readNullable { buffer.readFloat() })
    }
}