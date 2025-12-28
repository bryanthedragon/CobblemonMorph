/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.RemoveSpectatorPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class RemoveSpectatorHandler : ServerNetworkPacketHandler<RemoveSpectatorPacket> {
    override fun handle(
        packet: RemoveSpectatorPacket,
        server: MinecraftServer,
        player: ServerPlayer
    ) {
        BattleRegistry.getBattle(packet.battleId)?.spectators?.remove(player.uuid)
    }

}