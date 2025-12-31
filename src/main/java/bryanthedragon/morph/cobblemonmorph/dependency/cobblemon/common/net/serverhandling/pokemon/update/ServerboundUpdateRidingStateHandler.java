/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.ServerboundUpdateRidingStatePacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class ServerboundUpdateRidingStateHandler : ServerNetworkPacketHandler<ServerboundUpdateRidingStatePacket> {

    override fun handle(packet: ServerboundUpdateRidingStatePacket, server: MinecraftServer, ServerPlayer player) {
        val entity = player.level().getEntity(packet.entity) ?: return
        if (entity !is PokemonEntity) return
        if (entity.controllingPassenger != player) return
        val buffer = packet.data ?: return
        if (entity.ridingController?.context?.settings?.key != packet.behaviour) {
            entity.ridingController?.changeBehaviour(packet.behaviour)
        }
        entity.ridingController?.context?.state?.decode(buffer)
    }

}
