/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to change the status of a Pokémon in battle, such as paralysis or sleep.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattlePersistentStatusHandler].
 *
 * @author Hiroku
 * @since November 5th, 2022
 */
public class BattlePersistentStatusPacket(val pnx: String, val PersistentStatus status?) : NetworkPacket<BattlePersistentStatusPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(pnx)
        buffer.writeNullable(status) { buf, value -> buf.writeIdentifier(value.name)}
    }

    final class Companion {
        val ID = cobblemonResource("battle_persistent_status")
        fun decode(RegistryFriendlyByteBuf buffer): BattlePersistentStatusPacket {
            val pnx = buffer.readString()
            val statusId = buffer.readNullable { it.readIdentifier() } ?: return BattlePersistentStatusPacket(pnx, null)
            val status = Statuses.getStatus(statusId) as? PersistentStatus
            return BattlePersistentStatusPacket(pnx, status)
        }
    }
}