/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class OpenRidingStatsDebugGUIPacket : NetworkPacket<OpenRidingStatsDebugGUIPacket> {
    override val id = ID

    final class Companion {
        val ID = cobblemonResource("open_riding_stats_debug_gui")

        fun decode(RegistryFriendlyByteBuf buffer): OpenRidingStatsDebugGUIPacket {
            return OpenRidingStatsDebugGUIPacket()
        }
    }

    override fun encode(RegistryFriendlyByteBuf buf) {}
}