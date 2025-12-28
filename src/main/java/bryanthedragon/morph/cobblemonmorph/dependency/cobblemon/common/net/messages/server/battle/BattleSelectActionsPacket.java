/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

class BattleSelectActionsPacket(val battleId: UUID, val showdownActionResponses: List<ShowdownActionResponse>) : NetworkPacket<BattleSelectActionsPacket> {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(battleId)
        buffer.writeSizedInt(IntSize.U_BYTE, showdownActionResponses.size)
        showdownActionResponses.forEach { it.saveToBuffer(buffer) }
    }

    companion object {
        val ID = cobblemonResource("battle_select_actions")
        fun decode(buffer: RegistryFriendlyByteBuf): BattleSelectActionsPacket {
            val battleId = buffer.readUUID()
            val responses = mutableListOf<ShowdownActionResponse>()
            repeat(times = buffer.readSizedInt(IntSize.U_BYTE)) {
                responses.add(ShowdownActionResponse.loadFromBuffer(buffer))
            }
            return BattleSelectActionsPacket(battleId, responses)
        }
    }

}