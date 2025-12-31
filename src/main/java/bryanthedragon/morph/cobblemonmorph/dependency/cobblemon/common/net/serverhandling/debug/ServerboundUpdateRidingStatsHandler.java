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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug.ServerboundUpdateRidingStatsPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class ServerboundUpdateRidingStatsHandler : ServerNetworkPacketHandler<ServerboundUpdateRidingStatsPacket> {

    override fun handle(packet: ServerboundUpdateRidingStatsPacket, server: MinecraftServer, ServerPlayer player) {
        if (!Cobblemon.config.enableDebugKeys) return
        if (!Cobblemon.permissionValidator.hasPermission(player, USE_RIDING_STATS_DEBUG)) return

        val entity = player.level().getEntity(packet.entity) ?: return
        if (entity !is PokemonEntity) return
        if (entity.controllingPassenger != player) return

        entity.overrideRideStat(packet.ridingStyle, RidingStat.SPEED, packet.speed)
        entity.overrideRideStat(packet.ridingStyle, RidingStat.ACCELERATION, packet.acceleration)
        entity.overrideRideStat(packet.ridingStyle, RidingStat.SKILL, packet.skill)
        entity.overrideRideStat(packet.ridingStyle, RidingStat.JUMP, packet.jump)
        entity.overrideRideStat(packet.ridingStyle, RidingStat.STAMINA, packet.stamina)
    }

}
