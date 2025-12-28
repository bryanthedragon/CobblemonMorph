/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.debug

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions.USE_RIDING_STATS_DEBUG
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.debug.RequestOpenRidingStatsDebugGUIPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug.OpenRidingStatsDebugGUIPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class RequestOpenRidingStatsDebugGUIHandler : ServerNetworkPacketHandler<RequestOpenRidingStatsDebugGUIPacket> {
    override fun handle(
        packet: RequestOpenRidingStatsDebugGUIPacket,
        server: MinecraftServer,
        player: ServerPlayer
    ) {
        if (!Cobblemon.config.enableDebugKeys) {
            player.sendSystemMessage(lang("requires_debug_keys").red())
            return
        }

        if (!Cobblemon.permissionValidator.hasPermission(player, USE_RIDING_STATS_DEBUG)) {
            player.sendSystemMessage(lang("requires_permission").red())
            return
        }

        OpenRidingStatsDebugGUIPacket().sendToPlayer(player)
    }
}