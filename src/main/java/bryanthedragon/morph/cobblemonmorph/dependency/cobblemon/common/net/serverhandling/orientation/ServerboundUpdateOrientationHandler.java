/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.orientation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.OrientationControllable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.orientation.ServerboundUpdateOrientationPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class ServerboundUpdateOrientationHandler : ServerNetworkPacketHandler<ServerboundUpdateOrientationPacket> {
    override fun handle(packet: ServerboundUpdateOrientationPacket, server: MinecraftServer, player: ServerPlayer) {
        val entity = player.level().getEntity(packet.entity) ?: return

        if (entity !is PokemonEntity) return
        if (entity.controllingPassenger != player) return
        if (entity !is OrientationControllable) return

        entity.orientationController.updateOrientation { _ -> packet.orientation }
    }
}
