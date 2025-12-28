/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.debug

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class RequestOpenRidingStatsDebugGUIPacket : NetworkPacket<RequestOpenRidingStatsDebugGUIPacket> {
    override val id = ID

    companion object {
        val ID = cobblemonResource("request_open_riding_stats_debug_gui")

        fun decode(buffer: RegistryFriendlyByteBuf): RequestOpenRidingStatsDebugGUIPacket {
            return RequestOpenRidingStatsDebugGUIPacket()
        }
    }

    override fun encode(buf: RegistryFriendlyByteBuf) {}
}