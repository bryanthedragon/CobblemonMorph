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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.RidingStyle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug.ServerboundUpdateRidingSettingsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class ServerboundUpdateRidingSettingsHandler : ServerNetworkPacketHandler<ServerboundUpdateRidingSettingsPacket> {

    override fun handle(packet: ServerboundUpdateRidingSettingsPacket, server: MinecraftServer, player: ServerPlayer) {
        if (!Cobblemon.config.enableDebugKeys) return
        if (!Cobblemon.permissionValidator.hasPermission(player, USE_RIDING_STATS_DEBUG)) return

        val entity = player.level().getEntity(packet.entity) ?: return
        if (entity !is PokemonEntity) return
        if (entity.controllingPassenger != player) return
        this.modifyRideSettingsExpression(entity, packet.ridingStyle, packet.variable, packet.expression)
    }

    internal fun modifyRideSettingsExpression(vehicle: PokemonEntity, ridingStyle: RidingStyle, variable: String, expression: String) {
        val rideSettings = vehicle.ridingController?.behaviours?.get(ridingStyle) ?: return
        val clazz = rideSettings.javaClass
        val field = clazz.declaredFields.firstOrNull { it.name == variable } ?: return
        field.isAccessible = true
        field.set(rideSettings, expression.asExpression())
    }

}
